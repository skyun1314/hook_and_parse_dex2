package com.example.hookdeviceinfo;

import android.content.ContentResolver;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Arrays;

import dalvik.system.DexFile;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by zk on 2017/12/17.
 */

public class HTool {


    public static String GetCatValue(String str) {

        return null;
    }

    public static byte[] GetCatValue_byte(String str) {
        return null;
    }

    public static void XHookMethod(final String class_name, ClassLoader classLoader, final String fun_name, Object value) {


        XposedHelpers.findAndHookMethod(class_name, classLoader, fun_name, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                if(param.getResult()!=null){

                    if(param.getResult().getClass().equals(byte[].class)){
                        Log.e("wodelog", Arrays.toString((byte[]) param.getResult()));
                    }else{
                        Log.e("wodelog","class_name:"+class_name+"    fun_name:"+fun_name+":"+param.getResult().toString());
                    }


                }

            }
        });

    }


    public static void XHookMethod_getString(final String class_name, ClassLoader classLoader, Object value) {


        //修改ANDROID_ID
        XposedHelpers.findAndHookMethod(class_name, classLoader, "getString",
                ContentResolver.class,String.class,
                new XC_MethodHook() {
                    //此处会根据传入的String参数 判断返回值 其中包括比较关键的数据就是android_id
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        if(param.getResult()!=null){
                            Log.e("wodelog","class_name:"+class_name+"   fun_name:getString"+":"+param.getResult().toString());
                        }

                    }
                });

    }


    public static void XHookMethod_exec(final String class_name, ClassLoader classLoader, Object value) {


        //修改ANDROID_ID
        XposedHelpers.findAndHookMethod(class_name, classLoader, "exec",
                String.class, String[].class, File.class,
                new XC_MethodHook() {
                    //此处会根据传入的String参数 判断返回值 其中包括比较关键的数据就是android_id
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);

                        if(param.getResult()!=null){
                            Log.e("wodelog","class_name:"+class_name+"    fun_name:exec"+":"+param.getResult().toString());
                        }

                        String progs = (String) param.args[0];
                            Log.e("wodelog","Command"  + " = "+progs);

                        Process process= (Process) param.getResult();
                        BufferedReader br=new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String line=null;
                        while ((line=br.readLine())!=null){
                            Log.e("wodelog","exec_return"+line);
                        }

                    }
                });

    }


}
