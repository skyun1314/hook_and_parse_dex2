package com.binpang.apiMonitor;

import java.lang.reflect.Method;

import android.app.Notification;

import com.binpang.methodhook.FindMethod;
import com.binpang.methodhook.HookParam;
import com.binpang.methodhook.Logger;

public class NotificationManagerHook extends ApiMonitorHook {

	@Override
	public void startHook() {
		// TODO Auto-generated method stub
		Method notifyMethod = FindMethod.findMethod("android.app.NotificationManager", ClassLoader.getSystemClassLoader(), "notify",int.class,Notification.class);
		hookhelper.hookMethod(notifyMethod, new AbstractBahaviorHookCallBack() {
			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				Notification notification = (Notification) param.args[1];
				Logger.logI("Send Notification ->"); 
				Logger.logI(notification.toString()); 
			}
		});
	}

}
