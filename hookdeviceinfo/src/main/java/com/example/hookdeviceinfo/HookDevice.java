package com.example.hookdeviceinfo;

import android.content.ContentResolver;
import android.os.Build;
import android.util.Log;

import java.io.File;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by zk on 2017/12/17.
 */

public class HookDevice implements IXposedHookLoadPackage {


    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam mLpp) throws Throwable {
        // 判断是否是要Hook的包名
        HTool.XHookMethod(android.telephony.TelephonyManager.class.getName(), mLpp.classLoader, "getDeviceId", HTool.GetCatValue("imei"));
        HTool.XHookMethod("com.android.internal.telephony.PhoneSubInfo", mLpp.classLoader, "getDeviceId", HTool.GetCatValue("imei"));
        HTool.XHookMethod("com.android.internal.telephony.IPhoneSubInfo$Stub$Proxy", mLpp.classLoader, "getDeviceId", HTool.GetCatValue("imei"));



        HTool.XHookMethod(android.telephony.TelephonyManager.class.getName(), mLpp.classLoader, "getSimSerialNumber", HTool.GetCatValue("simserial"));

//WIFI信息
        HTool.XHookMethod(android.net.wifi.WifiInfo.class.getName(), mLpp.classLoader, "getMacAddress", HTool.GetCatValue("wifimac"));


        HTool.XHookMethod(java.net.NetworkInterface.class.getName(), mLpp.classLoader, "getHardwareAddress", HTool.GetCatValue_byte("xxx"));


        XposedHelpers.findAndHookMethod("android.os.SystemProperties", mLpp.classLoader, "native_get", new Object[]{String.class, String.class,
                new XC_MethodHook() {
                    //为了防止某些APP跳过Build类 而直接使用SystemProperties.native_get获得参数
                }});


        XposedHelpers.setStaticObjectField(android.os.Build.class, "SERIAL", Build.SERIAL+";haha");


        //修改ANDROID_ID   //此处会根据传入的String参数 判断返回值 其中包括比较关键的数据就是android_id

        HTool.XHookMethod_getString(android.provider.Settings.Secure.class.getName(), mLpp.classLoader,   HTool.GetCatValue("xx"));


        //防止APP使用Runtime.exec方式获取一些特定的系统属性
        //一些APP从JAVA层获得到了数据 还会从shell(native)层获得一些更底层的数据 来判断用户的合法性
        //经常用到的有 cat、getprop、ifconfig等等命令，当exec执行这些命令后 往往会返回一些手机的真实信息
        //因为框架和处理方式不同，...部分此处根据自己需求，编写重定向返回值的过程...

       HTool.XHookMethod_exec(Runtime.class.getName(), mLpp.classLoader,   HTool.GetCatValue("xx"));


    }



}
