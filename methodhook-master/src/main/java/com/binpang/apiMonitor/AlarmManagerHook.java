package com.binpang.apiMonitor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.os.WorkSource;

import com.binpang.methodhook.FindMethod;
import com.binpang.methodhook.HookParam;
import com.binpang.methodhook.Logger;


public class AlarmManagerHook extends ApiMonitorHook {

	@Override
	public void startHook() {
		
		Method setImplmethod = FindMethod.findMethod(
				"android.app.AlarmManager", ClassLoader.getSystemClassLoader(),
				"setImpl",int.class,long.class,long.class,long.class,PendingIntent.class,WorkSource.class);
		hookhelper.hookMethod(setImplmethod, new AbstractBahaviorHookCallBack() {
			
			@Override
			public void descParam(HookParam param) {
				Logger.logI("The Alarm Information:");
				PendingIntent intent = (PendingIntent) param.args[4];
				descPendingIntent(intent);
				Logger.logI("TriggerAtMillis = "+param.args[1]);
				Logger.logI("windowMillis = "+param.args[2]);
				Logger.logI("intervalMillis = "+param.args[3]);

			}
		});
		
	}
	
	private void descPendingIntent(PendingIntent pintent){
		Method getIntentMethod = FindMethod.findMethod(
				"android.app.PendingIntent", ClassLoader.getSystemClassLoader(),
				"getIntent");
		try {
			Intent intent = (Intent) getIntentMethod.invoke(pintent, new Object[]{});
			ComponentName cn = intent.getComponent();
			if(cn != null){
				Logger.logI("The ComponentName = "+cn.getPackageName()+"/"+cn.getClassName());
			}
			Logger.logI("The Intent Action = "+intent.getAction());
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
