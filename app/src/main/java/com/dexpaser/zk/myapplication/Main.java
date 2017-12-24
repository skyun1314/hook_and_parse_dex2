package com.dexpaser.zk.myapplication;

/**
 * Created by zk on 2017/6/10.
 */

import android.content.pm.ApplicationInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.saurik.substrate.MS;

import java.lang.reflect.Method;

import dalvik.system.DexFile;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Main implements IXposedHookLoadPackage {

    static void initialize() {
        // 1. hook 对应的类
        MS.hookClassLoad("android.content.res.Resources", new MS.ClassLoadHook() {
            @Override
            public void classLoaded(Class<?> Resources) {
                // 2. hook对应方法
                // 2.1 获取老的方法对象
                Method getColor = null;
                // public int getColor(@ColorRes int id) throws NotFoundException
                try {
                    getColor = Resources.getMethod("getColor", Integer.TYPE);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }

                if (getColor == null) {
                    return;
                }

                // 2.2 指定新的方法
                final MS.MethodPointer old = new MS.MethodPointer();
                MS.hookMethod(Resources, getColor, new MS.MethodHook() {
                    @Override
                    public Object invoked(Object res, Object... args) throws Throwable {
                        // 调用原方法
                        int color = (int) old.invoke(res, args);
                        // 修改方法返回值，指定新的颜色
                        return color & ~0x0000ff00 | 0x00ff0000;
                    }
                }, old);
            }
        });


    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {

        XposedHelpers.findAndHookMethod("java.security.Signature",null,"verify", byte[].class,new XC_MethodHook(){
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Log.e("wodelog","disabled verifysignature......"+param.getResult());

                Exception exception=new Exception("print trace");
                exception.printStackTrace();


                param.setResult(Boolean.TRUE);
            }
        });


       /* hook1(loadPackageParam);
        hook2(loadPackageParam);
        hook3(loadPackageParam);
        hook4(loadPackageParam);*/

       // HookLogin.HookImp(loadPackageParam);
        //HookSettings.HookImp(loadPackageParam);
        hookRes.hookMyRes(loadPackageParam);


    }

    private void hook1(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        // 判断是否是要Hook的包名
        String packageName = loadPackageParam.packageName;
        if (!packageName.equals("com.bluelesson.testphoneinfo")) {
            return;
        }
        XposedBridge.log("可以 hook了");
        // 可以Hook了
        XposedHelpers.findAndHookMethod(TelephonyManager.class, "getDeviceId", new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                return "我的手机我说了算";
            }
        });
    }


    private void hook4(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        // 判断是否是要Hook的包名
        final String packageName = loadPackageParam.packageName;

        XposedBridge.log("可以 hook openDexFileNative了");
        // 可以Hook了
        XposedHelpers.findAndHookMethod(DexFile.class, "openDexFileNative",String.class, String.class, int.class, new XC_MethodHook() {

            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);

            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Object result = param.getResult();


                long[]aaa=(long[])result;
                for (int i = 0; i < aaa.length; i++) {
                    Log.e("wodelog",packageName+"-----------openDexFileNative---------------"+aaa[i]);
                }

            }
        });

    }




    private void hook3(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        // 判断是否是要Hook的包名


        /*
        ActivityThread activityThread;
        ApplicationInfo aInfo;
        CompatibilityInfo compatInfo;
        ActivityThread mainThread;
        ClassLoader baseLoader
        boolean securityViolation;
        boolean includeCode*/
      /*  android.app.ActivityThread;

        android.content.pm.ApplicationInfo;
        android.content.res.CompatibilityInfo;
        java.lang.ClassLoader;
        boolean;
        boolean
*/

        String packageName = loadPackageParam.packageName;
        if (!packageName.equals("com.example.nativedex")) {
            return;
        }
        Class ActivityThread = XposedHelpers.findClass("android.app.ActivityThread", loadPackageParam.classLoader);
        Class CompatibilityInfo = XposedHelpers.findClass("android.content.res.CompatibilityInfo", loadPackageParam.classLoader);
        Class LoadedApk = XposedHelpers.findClass("android.app.LoadedApk", loadPackageParam.classLoader);
        Class Configuration = XposedHelpers.findClass("android.content.res.Configuration", loadPackageParam.classLoader);

        XposedBridge.log("可以 hook了");
        // 可以Hook了
        XposedHelpers.findAndHookConstructor("android.app.LoadedApk", loadPackageParam.classLoader,
                ActivityThread, ApplicationInfo.class, CompatibilityInfo, ClassLoader.class, boolean.class, boolean.class,


                new XC_MethodHook() {

                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        XposedBridge.log("我搞不出啦啊1");
                        XposedBridge.log("arg2:" + param.args[1]);
                    }


                });


        XposedHelpers.findAndHookConstructor("android.app.LoadedApk", loadPackageParam.classLoader,
                ActivityThread,


                new XC_MethodHook() {

                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        XposedBridge.log("我搞不出啦啊2");
                        XposedBridge.log("arg2:" + param.args[0]);
                    }


                });

        XposedHelpers.findAndHookMethod("android.app.ActivityThread", loadPackageParam.classLoader,"getTopLevelResources"
                ,String.class,int.class,Configuration,LoadedApk,


                new XC_MethodHook() {

                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        XposedBridge.log("我搞不出啦啊3");
                        XposedBridge.log("arg2:" + param.args[0]);
                        XposedBridge.log("arg2:" + param.args[1]);
                        XposedBridge.log("arg2:" + param.args[2]);
                        XposedBridge.log("arg2:" + param.args[3]);
                    }


                });
    }

    private void hook2(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        // 判断是否是要Hook的包名
        String packageName = loadPackageParam.packageName;
        if (!packageName.equals("com15pb.crackme02")) {
            return;
        }
        XposedBridge.log("可以 hook了");
        // 可以Hook了
        XposedHelpers.findAndHookMethod("com15pb.crackme02.MainActivity",
                loadPackageParam.classLoader,
                "CheckRegister", String.class, String.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        XposedBridge.log("arg1:" + param.args[0]);
                        XposedBridge.log("arg2:" + param.args[1]);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);

                        param.setResult(true);

                    }
                }
        );
    }
}
