package com.binpang.apiMonitor;

import java.lang.reflect.Method;

import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.ShutterCallback;




import com.binpang.methodhook.FindMethod;
import com.binpang.methodhook.HookParam;
import com.binpang.methodhook.Logger;

public class CameraHook extends ApiMonitorHook {

	@Override
	public void startHook() {
		// TODO Auto-generated method stub
		Method takePictureMethod = FindMethod.findMethod(
				"android.hardware.Camera", ClassLoader.getSystemClassLoader(),
				"takePicture",ShutterCallback.class,PictureCallback.class,PictureCallback.class,PictureCallback.class);
		hookhelper.hookMethod(takePictureMethod, new AbstractBahaviorHookCallBack() {
			
			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				Logger.logI("Camera take a picture->");
			}
		});
		
		Method setPreviewCallbackMethod = FindMethod.findMethod(
				"android.hardware.Camera", ClassLoader.getSystemClassLoader(),
				"setPreviewCallback",PreviewCallback.class);
		hookhelper.hookMethod(setPreviewCallbackMethod, new AbstractBahaviorHookCallBack() {
			
			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				Logger.logI("Camera setPreview ->");
			}
		});
		
		Method setPreviewCallbackWithBufferMethod = FindMethod.findMethod(
				"android.hardware.Camera", ClassLoader.getSystemClassLoader(),
				"setPreviewCallbackWithBuffer",PreviewCallback.class);
		hookhelper.hookMethod(setPreviewCallbackWithBufferMethod, new AbstractBahaviorHookCallBack() {
			
			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				Logger.logI("Camera setPreview ->");
			}
		});
		
		Method setOneShotPreviewCallbackMethod = FindMethod.findMethod(
				"android.hardware.Camera", ClassLoader.getSystemClassLoader(),
				"setOneShotPreviewCallback",PreviewCallback.class);
		hookhelper.hookMethod(setOneShotPreviewCallbackMethod, new AbstractBahaviorHookCallBack() {
			
			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				Logger.logI("Camera setPreview ->");
			}
		});
	}

}
