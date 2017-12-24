package com.binpang.apiMonitor;

import java.lang.reflect.Method;

import com.binpang.methodhook.FindMethod;
import com.binpang.methodhook.HookParam;
import com.binpang.methodhook.Logger;

public class LocationHook extends ApiMonitorHook{

	@Override
	public void startHook() {
		// TODO Auto-generated method stub
		Method getLocationMethod = FindMethod.findMethod(
				"android.location.LocationManager", ClassLoader.getSystemClassLoader(),
				"getAccounts");
		hookhelper.hookMethod(getLocationMethod, new AbstractBahaviorHookCallBack() {
			
			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				Logger.logI("Get Location");
			}
		});	
	}

}
