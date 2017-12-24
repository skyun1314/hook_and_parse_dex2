package com.binpang.methodhook;

import de.robv.android.xposed.XposedBridge;
import android.util.Log;

public class Logger {

	private static String tag = "FM";
	private static String tag1 = "DetailInform";
	public synchronized static void logI(String s)
	{
		Log.i(tag1,s);
	}
	
	public synchronized static void logD(String s)
	{
		Log.i(tag,s);
	}
}
