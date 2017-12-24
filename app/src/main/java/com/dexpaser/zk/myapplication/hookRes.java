package com.dexpaser.zk.myapplication;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by zk on 2017/8/24.
 */

public class hookRes {
    public static void setPermissions(String recordPath){
        Runtime chg = Runtime.getRuntime();
        try {
            chg.exec("chmod -R 777" + " " + recordPath).waitFor();
        } catch (Exception e) {
            Log.e("wodelog", "chmod failed! path = " + recordPath);
        }
    }
    public static String copyDex(String dexName,Context context) {


        //
        try {
            AssetManager assetManager = context.getAssets();
            InputStream open = assetManager.open(dexName);
            String outputPath ="/data/data/"+"com.bluemobi.jxqz"+"/files"  + File.separator + dexName;
            FileOutputStream fileOutputStream = new FileOutputStream(outputPath);

            byte b[] = new byte[1024];
            int len = 0;
            while ((len = open.read(b)) != -1) {
                fileOutputStream.write(b, 0, len);
            }



            fileOutputStream.close();
            setPermissions(outputPath);
            return outputPath;

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void replaceClassLoader(Context context) {
        copyDex("res.zip",context);

        try {
            Class<?> aClass = Class.forName("android.app.ActivityThread");
            Class<?> aClass1 = Class.forName("android.app.LoadedApk");
            String ResPath = "/data/data/"+"com.bluemobi.jxqz"+"/files" + File.separator + "res.zip";

            Object currentActivityThread = aClass.getMethod("currentActivityThread").invoke(new Object[]{});
            Field mPackages = aClass.getDeclaredField("mPackages");
            mPackages.setAccessible(true);
            Map map = (Map) mPackages.get(currentActivityThread);
            WeakReference o = (WeakReference) map.get("com.bluemobi.jxqz");
            Object loadedapk = o.get();
            Field mClassLoader = aClass1.getDeclaredField("mClassLoader");
            mClassLoader.setAccessible(true);


            Field mResDir = aClass1.getDeclaredField("mResDir");
            Field mResources = aClass1.getDeclaredField("mResources");
            mResDir.setAccessible(true);
            mResources.setAccessible(true);

            mResDir.set(loadedapk, ResPath);
            Resources resources = loadResources(ResPath,context);
            mResources.set(loadedapk, resources);


            Log.e("wodelog", "haha");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Resources loadResources(String mDexPath, Context context) {
        AssetManager assetManager = null;
        try {
             assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, mDexPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Resources superRes = context.getResources();
        Resources  mResources = new Resources(assetManager, superRes.getDisplayMetrics(),
                superRes.getConfiguration());
       // mTheme = mResources.newTheme();
        //mTheme.setTo(super.getTheme());
        return mResources;
    }


    public static void enumDeclaredFields(Class userClass) {
        Log.e("wodelog","enumDeclaredFields开始");



        Field[] declaredFields = userClass.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            Field con = declaredFields[i];    //取出第i个构造方法。
            System.out.print( con.getType().getName()+"  ");
            System.out.print(Modifier.toString(con.getModifiers()));
            //---- 打印该构造方法的前缀修饰符
            System.out.print(" " + con.getName() + "(");  //打印该构造方法的名字

            con.setAccessible(true);


            System.out.println(")");
        }
        Log.e("wodelog","enumDeclaredFields结束");
    }


    public static boolean isCanRun=true;

    public static void enumFields(Class userClass) {
        Log.e("wodelog","enumFields开始");
        Field[] declaredFields = userClass.getFields();
        for (int i = 0; i < declaredFields.length; i++) {
            Field con = declaredFields[i];    //取出第i个构造方法。
            System.out.print( con.getType().getName()+"  ");
            System.out.print(Modifier.toString(con.getModifiers()));
            //---- 打印该构造方法的前缀修饰符
            System.out.print(" " + con.getName() + "(");  //打印该构造方法的名字

            con.setAccessible(true);


            System.out.println(")");
        }
        Log.e("wodelog","enumFields结束");
    }

    public static void hookMyRes(final XC_LoadPackage.LoadPackageParam lpparam) {
        // 判断是否是要Hook的包名
        final String packageName = lpparam.packageName;

        if (packageName.equals("com.bluemobi.jxqz")) {




            XposedHelpers.findAndHookMethod("com.tinkerpatch.sdk.loader.TinkerPatchReflectApplication", lpparam.classLoader, "attachBaseContext", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {


                    if(isCanRun){
                        isCanRun=false;
                        XposedBridge.log("可以 hook res");
                        Context context= (Context) param.args[0];
                        replaceClassLoader(context);;

                    }


                }
            });

         /*

           XposedHelpers.findAndHookMethod(Resources.class.getName(), lpparam.classLoader, "loadXmlResourceParser", int.class, String.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    XposedBridge.log("我进入loadXmlResourceParser了");

                   // enumDeclaredFields(Resources.class);

                  //  Field mResourcesImpl = Resources.class.getDeclaredField("mResourcesImpl");
                    //mResourcesImpl.setAccessible(true);


                    /*Context context= (Context) param.getResult();

        }
    });


          Class LoadedApk= XposedHelpers.findClass("android.app.LoadedApk",lpparam.classLoader);
           Class ActivityThread= XposedHelpers.findClass("android.app.ActivityThread",lpparam.classLoader);
           Class ApplicationInfo= XposedHelpers.findClass("android.content.pm.ApplicationInfo",lpparam.classLoader);
           Class CompatibilityInfo= XposedHelpers.findClass("android.content.res.CompatibilityInfo",lpparam.classLoader);

            XposedHelpers.findAndHookConstructor(LoadedApk,ActivityThread, ApplicationInfo,
                    CompatibilityInfo, ClassLoader.class,
                    boolean.class, boolean.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            XposedBridge.log("我进入LoadedApk了");
                        }
                    });
*/

/*

            });



            */


        }

    }


}
