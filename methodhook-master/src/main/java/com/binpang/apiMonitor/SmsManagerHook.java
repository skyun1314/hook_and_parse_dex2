package com.binpang.apiMonitor;

import java.lang.reflect.Method;
import java.util.ArrayList;

import android.app.PendingIntent;
import android.util.Base64;




import com.binpang.methodhook.FindMethod;
import com.binpang.methodhook.HookParam;
import com.binpang.methodhook.Logger;


public class SmsManagerHook extends ApiMonitorHook {

	@Override
	public void startHook() {
		
		Method sendTextMessagemethod =  FindMethod.findMethod(
				"android.telephony.SmsManager", ClassLoader.getSystemClassLoader(),
				"sendTextMessage", String.class,String.class,String.class,PendingIntent.class,PendingIntent.class);
		hookhelper.hookMethod(sendTextMessagemethod, new AbstractBahaviorHookCallBack() {
			
			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				Logger.logI("Send SMS ->");
				String dstNumber = (String)param.args[0];
				String content = (String)param.args[2];
				Logger.logI("SMS DestNumber:"+dstNumber);
				Logger.logI("SMS Content:"+content);
			}
		});
		
		Method getAllMessagesFromIccmethod =  FindMethod.findMethod(
				"android.telephony.SmsManager", ClassLoader.getSystemClassLoader(),
				"getAllMessagesFromIcc");
		hookhelper.hookMethod(getAllMessagesFromIccmethod, new AbstractBahaviorHookCallBack() {
			
			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				Logger.logI("Read SMS From Icc ->");
			}
		});
		
		Method sendDataMessagemethod =  FindMethod.findMethod(
				"android.telephony.SmsManager", ClassLoader.getSystemClassLoader(),
				"sendDataMessage",String.class,String.class,short.class,byte[].class,PendingIntent.class,PendingIntent.class);
		hookhelper.hookMethod(sendDataMessagemethod, new AbstractBahaviorHookCallBack() {
			
			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				Logger.logI("Send Data SMS ->");
				String dstNumber = (String)param.args[0];
				short port = (Short)param.args[2];
				String content = Base64.encodeToString((byte[]) param.args[3],0);
				Logger.logI("SMS DestNumber:"+dstNumber);
				Logger.logI("SMS destinationPort:"+port);
				Logger.logI("SMS Base64 Content:"+content);
			}
		});
		
		Method sendMultipartTextMessagemethod =  FindMethod.findMethod(
				"android.telephony.SmsManager", ClassLoader.getSystemClassLoader(),
				"sendMultipartTextMessage",String.class,String.class,ArrayList.class,ArrayList.class,ArrayList.class);
		hookhelper.hookMethod(sendMultipartTextMessagemethod, new AbstractBahaviorHookCallBack() {
			
			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				Logger.logI("Send Multipart SMS ->");
				String dstNumber = (String)param.args[0];
				ArrayList<String> sms = (ArrayList<String>) param.args[2];
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<sms.size(); i++){
					sb.append(sms.get(i));
				}
				Logger.logI("SMS DestNumber:"+dstNumber);
				Logger.logI("SMS Content:"+sb.toString());
			}
		});
		
		
	}

}
