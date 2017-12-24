package com.binpang.apiMonitor;

import java.lang.reflect.Method;
import java.util.Iterator;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;




import com.binpang.methodhook.FindMethod;
import com.binpang.methodhook.HookParam;
import com.binpang.methodhook.Logger;

public class ContextImplHook extends ApiMonitorHook{
	
	@Override
	public void startHook() {
		// TODO Auto-generated method stub
		Method registerReceivermethod =  FindMethod.findMethod(
				"android.app.ContextImpl", ClassLoader.getSystemClassLoader(),
				"registerReceiver", BroadcastReceiver.class,IntentFilter.class);
		hookhelper.hookMethod(registerReceivermethod, new AbstractBahaviorHookCallBack() {
			
			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub				
				Logger.logI("Register BroatcastReceiver");
				Logger.logI("The BroatcastReceiver ClassName = "+param.args[0].getClass().toString());
				if(param.args[1] != null){
				   String intentstr = descIntentFilter((IntentFilter) param.args[1]);
				   Logger.logI("Intent Action = ["+intentstr+"]");
				}
			}
		});
	}
	
	public String descIntentFilter(IntentFilter intentFilter){
		StringBuilder sb = new StringBuilder(); 
		Iterator<String> actions =intentFilter.actionsIterator();
		String action = null;
		while(actions.hasNext()){
			action = actions.next();
			sb.append(action+",");
		}
		return sb.toString();
		
	}

}
