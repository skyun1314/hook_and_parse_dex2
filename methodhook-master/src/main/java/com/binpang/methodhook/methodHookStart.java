package com.binpang.methodhook;

import com.binpang.apiMonitor.ApiMonitorHookManager;

import android.content.pm.ApplicationInfo;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class methodHookStart implements IXposedHookLoadPackage{

	//private String HOOKPACKAGENAME1 = "com.binpang.apiMonitor";
	//private String HOOKPACKAGENAME2 = "com.binpang.methodhook";
	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		
		// TODO Auto-generated method stub
		//if(lpparam.appInfo == null || 
		//		(lpparam.appInfo.flags & (ApplicationInfo.FLAG_SYSTEM | ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)) !=0){
		//	return;
		//}else 
		//if(lpparam.isFirstApplication){
			//Logger.logI("hello");
		  //Logger.logI("the package = "+lpparam.packageName +" has hook");
		  //Logger.logI("the app target id = "+android.os.Process.myPid());
		  ApiMonitorHookManager.getInstance().startMonitor();
		//}else{
			
		//}
	}
	}

	
