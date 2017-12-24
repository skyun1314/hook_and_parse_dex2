package com.dexpaser.zk.myapplication;

import android.content.res.Resources;
import android.view.View;

import java.lang.reflect.Method;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookSettings {

    public static void HookImp(final XC_LoadPackage.LoadPackageParam lpparam) {
        if (
                true
                ) {

            try {
                XposedHelpers.findAndHookMethod("android.view.View", lpparam.classLoader,
                        "toString", new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param)
                                    throws Throwable {


                                char v11 = ',';
                                String text = ".";
                                View view = (View) param.thisObject;
                                StringBuilder v2 = new StringBuilder(228);
                                String name = view.getClass().getName();
                                v2.append(name);
                                v2.append('{');

                                int[] location = new int[2];
                                view.getLocationOnScreen(location);
                                v2.append(location[0]);
                                v2.append(v11);
                                v2.append(location[1]);
                                v2.append(v11);
                                v2.append(view.getWidth());
                                v2.append(v11);
                                v2.append(view.getHeight());
                                v2.append(v11);
                                char v9 = 'V';
                                switch (view.getVisibility()) {
                                    case 0: {
                                        v2.append(v9); //�ɼ�
                                        break;
                                    }
                                    case 4: {
                                        v2.append('I');  //���ɼ�
                                        break;
                                    }
                                    case 8: {
                                        v2.append('G');//���ɼ�
                                        break;
                                    }
                                    default: {
                                        v2.append(text);//δ֪
                                        break;
                                    }
                                }
                                v2.append(v11);
                                try {
                                    Method method = view.getClass().getMethod("getText");
                                    text = (String) method.invoke(view);
                                    v2.append(text);
                                } catch (Throwable e) {
                                }

                                int v1 = view.getId();
                                if (v1 != -1) {
                                    Resources v4 = view.getResources();
                                    if (v4 != null) {
                                        String v3 = "";
                                        switch (-16777216 & v1) {
                                            case 16777216:
                                                v3 = "android";
                                                break;
                                            case 2130706432:
                                                v3 = "app";
                                                break;
                                            default: {
                                                try {
                                                    v3 = v4.getResourcePackageName(v1);
                                                } catch (Resources.NotFoundException v6_1) {
                                                    v3 = "unknow";
                                                }
                                            }
                                        }
                                        String v5 = v4.getResourceTypeName(v1);
                                        String v0 = v4.getResourceEntryName(v1);
                                        v2.append(v11);
                                        v2.append(v3);
                                        v2.append(":");
                                        v2.append(v5);
                                        v2.append("/");
                                        v2.append(v0);
                                    }
                                } else {
                                    v2.append(v11);
                                }
                                v2.append("}");
                                param.setResult(v2.toString());

                            }
                        });
            } catch (Exception e1) {
                return;
            }


        }
    }
}
