package com.binpang.apiMonitor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.binpang.methodhook.FindMethod;
import com.binpang.methodhook.HookParam;
import com.binpang.methodhook.Logger;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.database.ContentObserver;
import android.net.Uri;
import android.text.TextUtils;
;

public class ContentResolverHook extends ApiMonitorHook {

	private static final String[] privacyUris = { "content://com.android.contacts", "content://sms", "content://mms-sms", "content://contacts/",
			"content://call_log", "content://browser/bookmarks" };

	private boolean isSensitiveUri(Uri uri) {
		String url = uri.toString().toLowerCase();
		Logger.logI(url);
		for (int i = 0; i < privacyUris.length; i++) {
			if (url.startsWith(privacyUris[i])) {
				return true;
			}
		}
		return false;
	}

	private String concatenateStringArray(String[] array, String splitstr) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			if (i == array.length - 1)
				sb.append(array[i]);
			else
				sb.append(array[i] + splitstr);
		}
		return sb.toString();
	}

	private String concatenateQuery(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		StringBuilder sb = new StringBuilder("select ");
		if (projection == null) {
			sb.append("* ");
		} else {
			sb.append(concatenateStringArray(projection, ","));
		}
		sb.append(" from [" + uri.toString() + "] ");
		if (!TextUtils.isEmpty(selection)) {
			sb.append(" where ");
			if (selectionArgs == null) {
				sb.append(selection);
			} else {
				String selectstr = selection;
				for (int i = 0; i < selectionArgs.length; i++) {
					selectstr = selectstr.replaceFirst("?", selectionArgs[i]);
				}
				sb.append(selectstr);
			}
		}
		if (!TextUtils.isEmpty(sortOrder))
			sb.append(" order by " + sortOrder);
		return sb.toString();
	}

	private String concatenateInsert(Uri uri, ContentValues cv) {
		StringBuilder sb = new StringBuilder();
		sb.append(" insert into ");
		sb.append("[" + uri.toString() + "]");
		sb.append(" ( ");
		String[] keysArray = new String[cv.size()];
		keysArray = this.getContentValuesKeySet(cv).toArray(keysArray);
		sb.append(concatenateStringArray(keysArray, ","));
		sb.append(" ) ");
		sb.append(" values (");
		for (int i = 0; i < keysArray.length; i++) {
			if (i == keysArray.length - 1)
				sb.append(" " + cv.get(keysArray[i]));
			else
				sb.append(" " + cv.get(keysArray[i]) + ",");
		}
		sb.append(" )");
		return sb.toString();
	}

	private String concatenateDelete(Uri uri, String selection, String[] selectionArgs) {
		StringBuilder sb = new StringBuilder();
		sb.append(" delete from ");
		sb.append("[" + uri.toString() + "]");
		if (!TextUtils.isEmpty(selection)) {
			sb.append(" where ");
			if (selectionArgs == null)
				sb.append(selection);
			else {
				String selectstr = selection;
				for (int i = 0; i < selectionArgs.length; i++) {
					selectstr = selectstr.replaceFirst("?", selectionArgs[i]);
				}
				sb.append(selectstr);
			}
		}
		return sb.toString();
	}

	private String concatenateUpdate(Uri uri, ContentValues cv, String selection, String[] selectionArgs) {
		StringBuilder sb = new StringBuilder();
		sb.append(" update ");
		sb.append("[" + uri.toString() + "]");
		sb.append(" set ");
		String[] keysArray = (String[]) this.getContentValuesKeySet(cv).toArray();
		for (int i = 0; i < keysArray.length; i++) {
			if (i == keysArray.length - 1)
				sb.append(" " + keysArray[i] + "=" + cv.get(keysArray[i]));
			else
				sb.append(" " + keysArray[i] + "=" + cv.get(keysArray[i]) + ", ");
		}
		if (!TextUtils.isEmpty(selection)) {
			sb.append(" where ");
			if (selectionArgs == null)
				sb.append(selection);
			else {
				String selectstr = selection;
				for (int i = 0; i < selectionArgs.length; i++) {
					selectstr = selectstr.replaceFirst("?", selectionArgs[i]);
				}
				sb.append(selectstr);
			}
		}
		return sb.toString();
	}

	@Override
	public void startHook() {

		Method querymethod = FindMethod.findMethod("android.content.ContentResolver", ClassLoader.getSystemClassLoader(), "query", Uri.class,
				String[].class, String.class, String[].class, String.class);
		hookhelper.hookMethod(querymethod, new AbstractBahaviorHookCallBack() {

			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				Uri uri = (Uri) param.args[0];
				if (isSensitiveUri(uri)) {
					Logger.logI("Read ContentProvider -> Uri = " + uri.toString());
					String queryStr = concatenateQuery(uri, (String[]) param.args[1], (String) param.args[2], (String[]) param.args[3],
							(String) param.args[4]);
					Logger.logI("Query SQL = " + queryStr);
				}
			}
		});

		Method registerContentObservermethod = FindMethod.findMethod("android.content.ContentResolver", ClassLoader.getSystemClassLoader(),
				"registerContentObserver", Uri.class, boolean.class, ContentObserver.class, int.class);
		hookhelper.hookMethod(registerContentObservermethod, new AbstractBahaviorHookCallBack() {

			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				Uri uri = (Uri) param.args[0];
				if (isSensitiveUri(uri)) {
					Logger.logI("Register ContentProvider Change -> Uri = " + uri.toString());
					Logger.logI("ContentObserver ClassName =" + param.args[1].getClass().toString());
				}
			}
		});

		Method insertmethod = FindMethod.findMethod("android.content.ContentResolver", ClassLoader.getSystemClassLoader(), "insert",
				Uri.class, ContentValues.class);
		hookhelper.hookMethod(insertmethod, new AbstractBahaviorHookCallBack() {

			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				Uri uri = (Uri) param.args[0];
				if (isSensitiveUri(uri)) {
					Logger.logI("Insert ContentProvider -> Uri = " + uri.toString());
					ContentValues cv = (ContentValues) param.args[1];
					String insertStr = concatenateInsert(uri, cv);
					Logger.logI("Insert SQL = " + insertStr);
				}
			}
		});

		Method bulkInsertmethod = FindMethod.findMethod("android.content.ContentResolver", ClassLoader.getSystemClassLoader(), "bulkInsert",
				Uri.class, ContentValues[].class);
		hookhelper.hookMethod(bulkInsertmethod, new AbstractBahaviorHookCallBack() {

			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				Uri uri = (Uri) param.args[0];
				if (isSensitiveUri(uri)) {
					ContentValues[] cv = (ContentValues[]) param.args[1];
					Logger.logI("Bulk Insert ContentProvider -> Uri = " + uri.toString());
					String insertStr = null;
					for (int i = 0; i < cv.length; i++) {
						insertStr = concatenateInsert(uri, cv[i]);
						Logger.logI("Insert " + i + " SQL = " + insertStr);
					}
				}
			}
		});

		Method deletemethod = FindMethod.findMethod("android.content.ContentResolver", ClassLoader.getSystemClassLoader(), "delete",
				Uri.class, String.class, String[].class);
		hookhelper.hookMethod(deletemethod, new AbstractBahaviorHookCallBack() {

			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				Uri uri = (Uri) param.args[0];
				if (isSensitiveUri(uri)) {
					Logger.logI("Delete ContentProvider -> uri= " + uri.toString());
					String deleteStr = concatenateDelete(uri, (String) param.args[1], (String[]) param.args[2]);
					Logger.logI("Delete SQL = " + deleteStr);
				}

			}
		});

		Method updatemethod = FindMethod.findMethod("android.content.ContentResolver", ClassLoader.getSystemClassLoader(), "update",
				Uri.class, ContentValues.class, String.class, String[].class);
		hookhelper.hookMethod(updatemethod, new AbstractBahaviorHookCallBack() {

			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				Uri uri = (Uri) param.args[0];
				if (isSensitiveUri(uri)) {
					Logger.logI("Update ContentProvider -> uri=" + uri.toString());
					String updateStr = concatenateUpdate(uri, (ContentValues) param.args[1], (String) param.args[2], (String[]) param.args[3]);
					Logger.logI("Update SQL = " + updateStr);
				}
			}
		});

		Method applyBatchMethod = FindMethod.findMethod("android.content.ContentResolver", ClassLoader.getSystemClassLoader(),
				"applyBatch", String.class, ArrayList.class);
		hookhelper.hookMethod(applyBatchMethod, new AbstractBahaviorHookCallBack() {

			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				ArrayList<ContentProviderOperation> opts = (ArrayList<ContentProviderOperation>) param.args[1];
				for(int i=0; i< opts.size(); i++){
					Logger.logI("Batch SQL = " + descContentProviderOperation(opts.get(i)));
				}
				
			}
		});

	}
	
	private Set<String> getContentValuesKeySet(ContentValues cv){
		HashMap<String,Object> mValue =  (HashMap<String,Object>) FindMethod.getFieldOjbect("android.content.ContentValues", cv, "mValues");
		return mValue.keySet();
	} 

	private final static int TYPE_INSERT = 1;
	private final static int TYPE_UPDATE = 2;
	private final static int TYPE_DELETE = 3;

	private String descContentProviderOperation(ContentProviderOperation opt) {
		String sqlstr = null;
		int mType = FindMethod.getFieldInt("android.content.ContentProviderOperation", opt, "mType");
		switch (mType) {
		  case TYPE_INSERT:
		  {
			  Uri uri = (Uri) FindMethod.getFieldOjbect("android.content.ContentProviderOperation", opt, "mUri");
			  ContentValues cv = (ContentValues) FindMethod.getFieldOjbect("android.content.ContentProviderOperation", opt, "mValues");
			  sqlstr = concatenateInsert(uri, cv);
			  break;
		  }

		  case TYPE_UPDATE:
		  {
			  Uri uri = (Uri) FindMethod.getFieldOjbect("android.content.ContentProviderOperation", opt, "mUri");
			  ContentValues cv = (ContentValues) FindMethod.getFieldOjbect("android.content.ContentProviderOperation", opt, "mValues");
			  String selection = (String) FindMethod.getFieldOjbect("android.content.ContentProviderOperation", opt, "mSelection");
			  String[] selectionArgs = (String[]) FindMethod.getFieldOjbect("android.content.ContentProviderOperation", opt, "mSelectionArgs");
			  sqlstr = concatenateUpdate(uri, cv, selection, selectionArgs);
			  break;
		  }
			  
		  case TYPE_DELETE:
			  Uri uri = (Uri) FindMethod.getFieldOjbect("android.content.ContentProviderOperation", opt, "mUri");
			  String selection = (String) FindMethod.getFieldOjbect("android.content.ContentProviderOperation", opt, "mSelection");
			  String[] selectionArgs = (String[]) FindMethod.getFieldOjbect("android.content.ContentProviderOperation", opt, "mSelectionArgs");
			  sqlstr = concatenateDelete(uri, selection, selectionArgs);
			  break;

		}
		return sqlstr;
	}

}
