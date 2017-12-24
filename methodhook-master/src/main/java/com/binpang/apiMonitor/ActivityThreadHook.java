package com.binpang.apiMonitor;

import java.lang.reflect.Method;




import com.binpang.methodhook.FindMethod;
import com.binpang.methodhook.HookParam;
import com.binpang.methodhook.Logger;

public class ActivityThreadHook extends ApiMonitorHook {

	@Override
	public void startHook() {
		// TODO Auto-generated method stub
		try {
			Class receiverDataClass = Class.forName("android.app.ActivityThread$ReceiverData");
			if (receiverDataClass != null) {
				Method handleReceiverMethod = FindMethod.findMethod("android.app.ActivityThread", ClassLoader.getSystemClassLoader(),
						"handleReceiver", receiverDataClass);
				hookhelper.hookMethod(handleReceiverMethod, new AbstractBahaviorHookCallBack() {

					@Override
					public void descParam(HookParam param) {
						Logger.logI("The Receiver Information:");
						Object data = param.args[0];
						Logger.logI(data.toString());
						
					}
				});
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
