package com.binpang.apiMonitor;

import java.lang.reflect.Method;

import android.telephony.PhoneStateListener;

import com.binpang.methodhook.FindMethod;
import com.binpang.methodhook.HookParam;
import com.binpang.methodhook.Logger;

public class TelephonyManagerHook extends ApiMonitorHook {

	@Override
	public void startHook() {
		
		Method getLine1Numbermethod =  FindMethod.findMethod(
				"android.telephony.TelephonyManager", ClassLoader.getSystemClassLoader(),
				"getLine1Number");
		hookhelper.hookMethod(getLine1Numbermethod, new AbstractBahaviorHookCallBack() {
			
			@Override
			public void descParam(HookParam param) {
				Logger.logI("Read PhoneNumber ->");
			}
		});
		
		Method listenMethod =  FindMethod.findMethod(
				"android.telephony.TelephonyManager", ClassLoader.getSystemClassLoader(),
				"listen", PhoneStateListener.class,int.class);
		hookhelper.hookMethod(listenMethod, new AbstractBahaviorHookCallBack() {
			
			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				Logger.logI("Listen Telephone State Change ->");
				Logger.logI("PhoneStateListener ClassName = "+param.args[0].getClass().getName());
				int event =  (Integer) param.args[1];
				if((event&PhoneStateListener.LISTEN_CELL_LOCATION) != 0){
					Logger.logI("Listen Enent = "+"LISTEN_CELL_LOCATION");
				}
				if((event&PhoneStateListener.LISTEN_SIGNAL_STRENGTHS) != 0){
					Logger.logI("Listen Enent = "+"LISTEN_SIGNAL_STRENGTHS");
				}
				if((event&PhoneStateListener.LISTEN_CALL_STATE) != 0){
					Logger.logI("Listen Enent = "+"LISTEN_CALL_STATE");
				}
				if((event&PhoneStateListener.LISTEN_DATA_CONNECTION_STATE) != 0){
					Logger.logI("Listen Enent = "+"LISTEN_DATA_CONNECTION_STATE");
				}
				if((event&PhoneStateListener.LISTEN_CELL_LOCATION) != 0){
					Logger.logI("Listen Enent = "+"LISTEN_SERVICE_STATE");
				}
				
			}
		});
		
	}

}
