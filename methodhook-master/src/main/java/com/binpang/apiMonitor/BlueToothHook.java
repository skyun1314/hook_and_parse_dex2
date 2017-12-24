package com.binpang.apiMonitor;

import java.lang.reflect.Method;

import com.binpang.methodhook.FindMethod;
import com.binpang.methodhook.HookParam;
import com.binpang.methodhook.Logger;

public class BlueToothHook extends ApiMonitorHook{

	@Override
	public void startHook() {
		// TODO Auto-generated method stub
		

		Method openBluetoothmethod =  FindMethod.findMethod(
				"android.bluetooth.BluetoothAdapter", ClassLoader.getSystemClassLoader(),
				"getLine1Number");
		hookhelper.hookMethod(openBluetoothmethod, new AbstractBahaviorHookCallBack() {
			
			@Override
			public void descParam(HookParam param) {
				Logger.logI("Open the BlueTooth");
			}
		});
		
		Method connectBluetooth =  FindMethod.findMethod(
				"android.bluetooth.BluetoothSocket", ClassLoader.getSystemClassLoader(),
				"connect");
		hookhelper.hookMethod(connectBluetooth, new AbstractBahaviorHookCallBack() {
			
			@Override
			public void descParam(HookParam param) {
				Logger.logI("Connect the blueTooth");
			}
		});
	}

}
