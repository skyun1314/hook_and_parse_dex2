package com.binpang.apiMonitor;

import java.lang.reflect.Method;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.binpang.methodhook.FindMethod;
import com.binpang.methodhook.HookParam;
import com.binpang.methodhook.Logger;

public class PackageManagerHook extends ApiMonitorHook {

	@Override
	public void startHook() {

		Method setComponentEnableSettingmethod =  FindMethod.findMethod("android.app.ApplicationPackageManager",
				ClassLoader.getSystemClassLoader(), "setComponentEnabledSetting", ComponentName.class, int.class, int.class);
		hookhelper.hookMethod(setComponentEnableSettingmethod, new AbstractBahaviorHookCallBack() {

			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				ComponentName cn = (ComponentName) param.args[0];
				int newState = (Integer) param.args[1];
				Logger.logI("Set ComponentEnabled ->");
				Logger.logI("The Component ClassName: " + cn.getPackageName() + "/" + cn.getClassName());
				if (newState == PackageManager.COMPONENT_ENABLED_STATE_DISABLED)
					Logger.logI("Component New State = " + "COMPONENT_ENABLED_STATE_DISABLED");
				if (newState == PackageManager.COMPONENT_ENABLED_STATE_ENABLED)
					Logger.logI("Component New State = " + "COMPONENT_ENABLED_STATE_ENABLED");
				if (newState == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER)
					Logger.logI("Component New State = " + "COMPONENT_ENABLED_STATE_DISABLED_USER");
				if (newState == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT)
					Logger.logI("Component New State = " + "COMPONENT_ENABLED_STATE_DEFAULT");
			}
		});

		Method installPackagemethod = null;
		try {
			installPackagemethod =  FindMethod.findMethod("android.app.ApplicationPackageManager", ClassLoader.getSystemClassLoader(),
					"installPackage", Uri.class, Class.forName("android.content.pm.IPackageInstallObserver"), int.class, String.class);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		hookhelper.hookMethod(installPackagemethod, new AbstractBahaviorHookCallBack() {

			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				Uri uri = (Uri) param.args[0];
				Logger.logI("Slient Install APK ->");
				Logger.logI("The APK URL = " + uri.toString());
			}
		});

		Method deletePackagemethod = null;
		try {
			deletePackagemethod =  FindMethod.findMethod("android.app.ApplicationPackageManager", ClassLoader.getSystemClassLoader(),
					"deletePackage", String.class, Class.forName("android.content.pm.IPackageDeleteObserver"), int.class);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		hookhelper.hookMethod(deletePackagemethod, new AbstractBahaviorHookCallBack() {

			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				String packagename = (String) param.args[0];
				Logger.logI("Slient UnInstall APK ->");
				Logger.logI("The APK PackageName = " + packagename);
			}
		});

		Method getInstalledPackagesMethod =  FindMethod.findMethod("android.app.ApplicationPackageManager",
				ClassLoader.getSystemClassLoader(), "getInstalledPackages", int.class, int.class);
		hookhelper.hookMethod(getInstalledPackagesMethod, new AbstractBahaviorHookCallBack() {
			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				Logger.logI("Query Installed Apps ->");
			}
		});
	}

}
