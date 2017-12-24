package com.binpang.apiMonitor;

import java.io.FileDescriptor;
import java.lang.reflect.Method;




import com.binpang.methodhook.FindMethod;
import com.binpang.methodhook.HookParam;
import com.binpang.methodhook.Logger;

public class MediaRecorderHook extends ApiMonitorHook {

	@Override
	public void startHook() {

		Method startmethod =  FindMethod.findMethod(
				"android.media.MediaRecorder", ClassLoader.getSystemClassLoader(),
				"start");
		hookhelper.hookMethod(startmethod, new AbstractBahaviorHookCallBack() {
			
			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				Logger.logI("Media Record: Start ->");			
				String mPath = (String)FindMethod.getFieldOjbect("android.media.MediaRecorder", param.thisObject, "mPath");
				if(mPath != null)
				   Logger.logI("Save Path: ->" +mPath);
				else{
					FileDescriptor mFd = (FileDescriptor) FindMethod.getFieldOjbect("android.media.MediaRecorder", param.thisObject, "mFd");
					Logger.logI("Save Path: ->" +mFd.toString());
				}
			}
		});
		
		Method stopmethod =  FindMethod.findMethod(
				"android.media.MediaRecorder", ClassLoader.getSystemClassLoader(),
				"stop");
		hookhelper.hookMethod(stopmethod, new AbstractBahaviorHookCallBack() {
			
			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				Logger.logI("Media Record: Stop ->");			
			}
		});
		
		
		Method autiomethod =  FindMethod.findMethod(
				"android.media.MediaRecorder", ClassLoader.getSystemClassLoader(),
				"setAudioSource");
		hookhelper.hookMethod(autiomethod, new AbstractBahaviorHookCallBack() {
			
			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				Logger.logI("Media Record: Start");			
				
				}
			
		});
		
		Method videomethod =  FindMethod.findMethod(
				"android.media.MediaRecorder", ClassLoader.getSystemClassLoader(),
				"setAudioSource");
		hookhelper.hookMethod(videomethod, new AbstractBahaviorHookCallBack() {
			
			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				Logger.logI("Video Record: Start");			
				
				}
			
		});
		
	}

}
