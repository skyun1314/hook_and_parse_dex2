package com.binpang.apiMonitor;

import java.lang.reflect.Method;




import com.binpang.methodhook.FindMethod;
import com.binpang.methodhook.HookParam;
import com.binpang.methodhook.Logger;

public class AudioRecordHook extends ApiMonitorHook {

	@Override
	public void startHook() {
		// TODO Auto-generated method stub
		Method startRecordingMethod = FindMethod.findMethod(
				"android.media.AudioRecord", ClassLoader.getSystemClassLoader(),
				"startRecording");
		hookhelper.hookMethod(startRecordingMethod, new AbstractBahaviorHookCallBack() {
			
			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				Logger.logI("Audio Recording ->");
			}
		});
		
	}

}
