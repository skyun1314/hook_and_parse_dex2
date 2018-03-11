package com.example.hook_wx;

import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.util.Arrays;
import java.util.Map;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by zk on 2017/12/17.
 */

public class HookDevice implements IXposedHookLoadPackage {
    //provided
    private static final String WechatPackageName = "com.tencent.mm";

    public static String wodetag = "wodelog";


    public static void show_msg(String message, ContentValues contentValues, String methead_name) {
        if (message.equals("message")) {
            Log.i(wodetag, "  ");
            Log.i(wodetag, methead_name + "  :start-----------------");
            for (Map.Entry<String, Object> item : contentValues.valueSet()) {
                Log.i(wodetag, "   ContentValues:" + item.getKey() + " -> " + item.getValue().toString());
            }
            Log.i(wodetag, methead_name + " :end-----------------");
        }
    }


    public static void hook_SQLiteDatabase(Class hookclass) {
        XposedHelpers.findAndHookMethod(hookclass, "updateWithOnConflict",
                String.class, ContentValues.class, String.class, String[].class, int.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        ContentValues contentValues = (ContentValues) param.args[1];
                        String message = (String) param.args[0];
                        if (message == null) {
                            return;
                        }

                        show_msg(message, contentValues, "updateWithOnConflict");
                        if (message.equals("message")&&((String)contentValues.get("content")).contains(" 撤回了一条消息")&&((int)contentValues.get("type"))==10000) {
                            param.args[0]="";
                            Cursor cursor = (Cursor) XposedHelpers.callMethod(param.thisObject, "rawQuery", "select * from message where msgId=?", new String[]{(contentValues.get("msgId") + "")});
                            cursor.moveToNext();
                            Log.i(wodetag, "  ");
                            Log.i(wodetag, "cursor1  :start-----------------");
                            ContentValues contentValues1 = new ContentValues();
                            for (int i = 0; i < cursor.getColumnCount(); i++) {
                                String columnName = cursor.getColumnName(i);
                                Object haha;
                                if(columnName.equals("msgId")||columnName.equals("msgSvrId")||columnName.equals("type")||columnName.equals("status")
                                        ||columnName.equals("isSend")||columnName.equals("isShowTimer")||columnName.equals("createTime")||columnName.equals("talkerId")
                                        ||columnName.equals("bizChatId")||columnName.equals("msgSeq")||columnName.equals("flag")){
                                    long  string = cursor.getLong(i);
                                    if(columnName.equals("msgId")||columnName.equals("createTime")){
                                        string+=1;
                                    }

                                    if(columnName.equals("type")){
                                        string=10000;
                                    }
                                    haha=string;
                                    contentValues1.put(columnName, string);
                                }else if(columnName.equals("lvbuffer")){
                                    byte[]  string = cursor.getBlob(i);
                                    haha=string;
                                    contentValues1.put(columnName, string);
                                }else{
                                    String  string = cursor.getString(i);
                                    haha=string;
                                    if(string==null){
                                        continue;
                                    }
                                    if(columnName.equals("content")){
                                        string="尝试撤回消息";
                                    }
                                    contentValues1.put(columnName,  string);
                                }

                                Log.e(wodetag, "   ContentValues:" + columnName + " -> " + haha);
                            }

                            Log.i(wodetag, "cursor1  :end-----------------");
                            Log.e(wodetag, "   ");
                            Log.i(wodetag, "cursor2  :start-----------------");
                            for (Map.Entry<String, Object> item : contentValues1.valueSet()) {
                                Log.i(wodetag, "   ContentValues:" + item.getKey() + " -> " + item.getValue().toString());
                            }
                            Log.i(wodetag, "cursor2  :end-----------------");
                            XposedHelpers.callMethod(param.thisObject, "insertWithOnConflict", "message", "msgId", contentValues1, 0);

                        }
                    }
                });
        XposedHelpers.findAndHookMethod(hookclass, "insertWithOnConflict",
                String.class, String.class, ContentValues.class, int.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        ContentValues contentValues = (ContentValues) param.args[2];
                        String message1 = (String) param.args[0];

                        if (message1.equals("message")) {
                            String string = (String) param.args[1];
                            int int1 = (int) param.args[3];
                            Log.e(wodetag, "message1" + message1 + "; string:" + string + "; int1:" + int1);
                        }

                        show_msg(message1, contentValues, "insertWithOnConflict");
                    }
                });
    }

    public static void hook_SQLiteStatement(Class hookclass, Class SQLiteDatabase) {

        XposedHelpers.findAndHookConstructor(hookclass, SQLiteDatabase, String.class, Object[].class, new XC_MethodHook() {


            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);

                String args1 = (String) param.args[1];
                Object[] args2 = (Object[]) param.args[2];
                String args2_tostring = Arrays.toString(args2);

                if (args2 != null && "UPDATE message SET content=?,msgId=?,type=? WHERE msgId=?".equals(args1) && ((String) args2[0]).contains("撤回了一条消息") && ((int) args2[2]) == 10000) {
                    Log.i(wodetag, "把更新语句改成查询语句1");
                    param.args[1] = "select * from message where msgId=?";
                    param.args[2] = new Object[]{args2[3]};

                    //   String simpleQueryForString = (String) XposedHelpers.callMethod(param.getResult(), "simpleQueryForString");
                    //  Log.e(wodetag, "查询到语句:" + simpleQueryForString);
                }


                if (args1.contains(" message(") || args1.contains("UPDATE message")) {
                    Log.i(wodetag, "    ");
                    Log.i(wodetag, "SQLiteStatement:");
                    Log.i(wodetag, "args1:" + args1);
                    Log.i(wodetag, "args2:" + args2_tostring);


                    new Throwable("wodelog").printStackTrace();
                    ;
                }


            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                String args1 = (String) param.args[1];
                Object[] args2 = (Object[]) param.args[2];
                String args2_tostring = Arrays.toString(args2);

                if (param.args[1].equals("select * from message where msgId=?") && ((Object[]) param.args[2]).length == 1) {
                    Log.i(wodetag, "把更新语句改成查询语句2");

                    if (param.thisObject != null) {
                        String simpleQueryForString = (String) XposedHelpers.callMethod(param.thisObject, "simpleQueryForString");
                        Log.e(wodetag, "查询到语句1:" + simpleQueryForString);
                    } else {
                        Log.e(wodetag, "查询到语句2:" + "param.getResult() 是空的");
                    }
                }
            }


        });
    }


    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam mLpp) throws Throwable {
        String pkgName = mLpp.packageName;
        if (!pkgName.equals(WechatPackageName)) {
            return;
        }


        XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                ClassLoader cl = ((Context) param.args[0]).getClassLoader();

                try {
                    Class<?> SQLiteDatabase = cl.loadClass("com.tencent.wcdb.database.SQLiteDatabase");
                    hook_SQLiteDatabase(SQLiteDatabase);
                } catch (Exception e) {
                    Log.e(wodetag, "loadClass报错" + e.getMessage());
                }

               /* try {
                    Class<?> SQLiteStatement = cl.loadClass("com.tencent.wcdb.database.SQLiteStatement");
                    Class<?> SQLiteDatabase = cl.loadClass("com.tencent.wcdb.database.SQLiteDatabase");
                    hook_SQLiteStatement(SQLiteStatement, SQLiteDatabase);
                } catch (Exception e) {
                    Log.e(wodetag, "loadClass报错" + e.getMessage());
                }*/
            }
        });


    }


}
