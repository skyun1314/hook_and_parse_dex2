package com.example.hookdeviceinfo;

import android.content.ContentResolver;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FilterOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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

    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
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
                if (param.getResult() != null) {

                    if (param.getResult().getClass().equals(byte[].class)) {
                        XposedBridge.log(parseByte2HexStr((byte[]) param.getResult()));
                    } else {
                        XposedBridge.log("class_name:" + class_name + "    fun_name:" + fun_name + ":" + param.getResult().toString());
                    }


                }

            }
        });

    }


    public static void XHookMethod_getString(final String class_name, ClassLoader classLoader, Object value) {


        //修改ANDROID_ID
        XposedHelpers.findAndHookMethod(class_name, classLoader, "getString",
                ContentResolver.class, String.class,
                new XC_MethodHook() {
                    //此处会根据传入的String参数 判断返回值 其中包括比较关键的数据就是android_id
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        if (param.getResult() != null) {
                            XposedBridge.log("class_name:" + class_name + "   fun_name:getString(" + param.args[1] + "):" + param.getResult().toString());
                        }

                    }
                });

    }


    public static void XHookMethod_exec(final String class_name, final ClassLoader classLoader, Object value) {


        //修改ANDROID_ID
        XposedHelpers.findAndHookMethod(class_name, classLoader, "exec",
                String.class, String[].class, File.class,
                new XC_MethodHook() {
                    //此处会根据传入的String参数 判断返回值 其中包括比较关键的数据就是android_id
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);


                        String progs = (String) param.args[0];
                        XposedBridge.log("Command" + " = " + progs);

                        Process process = (Process) param.getResult();

                        if (process == null) {
                            return;
                        }

                        if(progs.equals("su")||progs.equals("/system/bin/sh")||progs.equals("/system/xbin/su")||progs.equals("sh")){
                            XposedHelpers.findAndHookMethod(DataOutputStream.class.getName(), classLoader, "writeBytes",String.class,
                                            new XC_MethodHook() {
                                        @Override
                                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                            super.afterHookedMethod(param);
                                            XposedBridge.log("Command" + " = " + param.args[0]);
                                        }
                                    });




                            XposedHelpers.findAndHookMethod(OutputStream.class.getName(), classLoader, "write",byte[].class,
                                    new XC_MethodHook() {
                                        @Override
                                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                            super.afterHookedMethod(param);
                                            XposedBridge.log("Command" + " = " + new String((byte[]) param.args[0]));
                                        }
                                    });

                        }else{
                            InputStream input = process.getInputStream();

                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            byte[] buffer = new byte[1024];
                            int len;
                            while ((len = input.read(buffer)) > -1) {
                                baos.write(buffer, 0, len);
                            }
                            baos.flush();
                            InputStream stream1 = new ByteArrayInputStream(baos.toByteArray());
                            final InputStream stream2 = new ByteArrayInputStream(baos.toByteArray());


                            BufferedReader br = new BufferedReader(new InputStreamReader(stream1));
                            String line = null;
                            while ((line = br.readLine()) != null) {
                                XposedBridge.log("exec_return" + line);
                            }
                            XposedHelpers.findAndHookMethod("java.lang.ProcessManager$ProcessImpl", classLoader, "getInputStream",
                                    new XC_MethodHook() {
                                        @Override
                                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                            super.afterHookedMethod(param);
                                            param.setResult(stream2);
                                        }
                                    });

                        }


                    }
                });

    }


}
