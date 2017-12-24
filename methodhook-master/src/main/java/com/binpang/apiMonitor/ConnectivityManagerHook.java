package com.binpang.apiMonitor;

import java.lang.reflect.Method;






import com.binpang.methodhook.FindMethod;
import com.binpang.methodhook.HookParam;
import com.binpang.methodhook.Logger;

public class ConnectivityManagerHook extends ApiMonitorHook {

	@Override
	public void startHook() {
		
		Method setMobileDataEnabledmethod = FindMethod.findMethod(
				"android.net.ConnectivityManager", ClassLoader.getSystemClassLoader(),
				"setMobileDataEnabled",boolean.class);
		hookhelper.hookMethod(setMobileDataEnabledmethod, new AbstractBahaviorHookCallBack() {
			
			@Override
			public void descParam(HookParam param) {
				boolean status = (Boolean) param.args[0];
				Logger.logI("Set MobileDataEnabled = "+status);
			}
		});
		
	}

}
