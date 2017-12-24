package com.binpang.apiMonitor;

import java.io.File;
import java.lang.reflect.Method;

import com.binpang.methodhook.FindMethod;
import com.binpang.methodhook.HookParam;
import com.binpang.methodhook.Logger;

public class RuntimeHook extends ApiMonitorHook {

	@Override
	public void startHook() {

		Method execmethod =  FindMethod.findMethod(
				"java.lang.Runtime", ClassLoader.getSystemClassLoader(),
				"exec", String[].class,String[].class,File.class);
		hookhelper.hookMethod(execmethod, new AbstractBahaviorHookCallBack() {			
			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				Logger.logI("Create New Process ->");
				String[] progs = (String[]) param.args[0];
				for(int i=0 ;i <progs.length; i++){
				   Logger.logI("Command" + i + " = "+progs[i]);
				}
			}
		});
		
	}

}
