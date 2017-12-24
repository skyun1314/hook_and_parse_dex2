package com.dexpaser.zk.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Map;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by zk on 2017/8/24.
 */

public class hookCookie {









    public static int mCookie = 0;
    public static boolean isCanRun = true;
    public static void replaceClassLoader(final String my_packageName, ClassLoader classLoader) {
        try {
            Class<?> aClass = Class.forName("android.app.ActivityThread");
            Class<?> aClass1 = Class.forName("android.app.LoadedApk");

            Object currentActivityThread = aClass.getMethod("currentActivityThread").invoke(new Object[]{});


            Field mPackages = aClass.getDeclaredField("mPackages");
            mPackages.setAccessible(true);
            Map map = (Map) mPackages.get(currentActivityThread);
            WeakReference o = (WeakReference) map.get(my_packageName);
            Object loadedapk = o.get();
            Field mClassLoader = aClass1.getDeclaredField("mClassLoader");
            mClassLoader.setAccessible(true);


            // Object classLoader = mClassLoader.get(loadedapk);
            Class clzBaseDexClassLoader = Class.forName("dalvik.system.BaseDexClassLoader");
            Class clzDexPathList = Class.forName("dalvik.system.DexPathList");
            Field field_pathList = clzBaseDexClassLoader.getDeclaredField("pathList");
            field_pathList.setAccessible(true);
            Object dexPathList = field_pathList.get(classLoader);
            Field field_dexElements = clzDexPathList.getDeclaredField("dexElements");
            field_dexElements.setAccessible(true);
            Class clzElement = Class.forName("dalvik.system.DexPathList$Element");
            Object dexElemennts = field_dexElements.get(dexPathList);

            //int cookie=MmClassLoader.getcookie();
            int length = Array.getLength(dexElemennts);
            Log.e("wodelog", "dexElemennts_lenth: " + length);


            for (int i = 1; i < length; i++) {
                Object ele = Array.get(dexElemennts, i);
                Log.e("wodelog", "dexElemennt[" + i + "]" + ele);
                try {
                    Field field_dexFile = clzElement.getDeclaredField("dexFile");
                    field_dexFile.setAccessible(true);
                    Object dexFile = field_dexFile.get(ele);

                    Class clzDexFile = Class.forName("dalvik.system.DexFile");
                    Field field_mcookie = clzDexFile.getDeclaredField("mCookie");
                    field_mcookie.setAccessible(true);
                    //field_mcookie.set(dexFile, mCookie);
                    mCookie = (int) field_mcookie.get(dexFile);
                    //  XposedBridge.log("cookie: "+o1);
                    Log.e("wodelog", "cookie: " + mCookie);

                    MainActivity.aaattachBaseContext(mCookie, my_packageName);
                } catch (Exception e) {
                    Log.e("wodelog", "Exception: " + e.toString());
                    e.printStackTrace();
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hookCookie(final XC_LoadPackage.LoadPackageParam lpparam) {
        // 判断是否是要Hook的包名
        final String packageName = lpparam.packageName;

        StringBuffer sb = null;
        try {
            File file = new File("/sdcard/tuoke.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String readline = "";
            sb = new StringBuffer();
            while ((readline = br.readLine()) != null) {
                System.out.println("readline:" + readline);
                sb.append(readline);
            }
            br.close();
            System.out.println("读取成功：" + sb.toString());
        } catch (Exception e) {
            Log.e("wodelog","文件读取失败");
        }

        if (sb == null) {
            return;
        }

        String result = sb.toString();


        String[] split = result.split("::");


        final String my_packageName = split[0];
        final String protect_Application = split[1];
        final String Main_Activity = split[2];

        /*XposedHelpers.findAndHookMethod("android.app.Activity", lpparam.classLoader, "finish", new XC_MethodReplacement() {

            @Override
            protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                XposedBridge.log("阻止finish");
                return null;
            }
        });*/

        // 可以Hook了
        if (packageName.equals(my_packageName)) {
            XposedBridge.log("可以 hook cookie");

            XposedHelpers.findAndHookMethod("android.content.ContextWrapper", lpparam.classLoader, "attachBaseContext", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    XposedBridge.log("我进入父类attachBaseContext了");
                    Context context = (Context) param.args[0];

                    final ClassLoader finalClassLoader = context.getClassLoader();
                    XposedHelpers.findAndHookMethod(Main_Activity, finalClassLoader, "onCreate", Bundle.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            XposedBridge.log("我进入真正的onCreate了：" + Main_Activity);
                            if(isCanRun==true){
                                isCanRun=false;
                                replaceClassLoader(my_packageName, finalClassLoader);
                            }

                        }
                    });

                }
            });


        }

    }

    static {
         System.load("/data/data/com.dexpaser.zk.myapplication/lib/libzkjg-lib.so");
    }

}
