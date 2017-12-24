package com.binpang.apiMonitor;

import java.lang.reflect.Method;

import com.binpang.methodhook.FindMethod;
import com.binpang.methodhook.HookParam;
import com.binpang.methodhook.Logger;



public class AccountManagerHook extends ApiMonitorHook {

	
	@Override
	public void startHook() {
		
		Method getAccountsMethod = FindMethod.findMethod(
				"android.accounts.AccountManager", ClassLoader.getSystemClassLoader(),
				"getAccounts");
		hookhelper.hookMethod(getAccountsMethod, new AbstractBahaviorHookCallBack() {
			
			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				Logger.logI("Get Account ->");
			}
		});	
		
		Method getAccountsByTypeMethod = FindMethod.findMethod(
				"android.accounts.AccountManager", ClassLoader.getSystemClassLoader(),
				"getAccountsByType",String.class);
		hookhelper.hookMethod(getAccountsByTypeMethod, new AbstractBahaviorHookCallBack() {
			
			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				String type = (String) param.args[0];
				Logger.logI("Get Account By Type ->");
				Logger.logI("type :" +type);
			}
		});	
	}

}
