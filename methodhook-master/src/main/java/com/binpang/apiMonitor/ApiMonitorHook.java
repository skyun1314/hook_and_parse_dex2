package com.binpang.apiMonitor;

import com.binpang.methodhook.HookHelperFacktory;
import com.binpang.methodhook.HookHelperInterface;




public abstract class ApiMonitorHook {
	
   protected HookHelperInterface hookhelper = HookHelperFacktory.getHookHelper();
   public static class InvokeInfo{
	   private long invokeAtTime;
	   private String className;
	   private String methodName;
	   private Object[] argv;
	   private Object result;
	   private Object invokeState;
   }
   public abstract void startHook();
    
}
