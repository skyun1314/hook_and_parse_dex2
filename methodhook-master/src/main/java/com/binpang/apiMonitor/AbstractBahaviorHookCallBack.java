package com.binpang.apiMonitor;

import com.binpang.methodhook.HookParam;
import com.binpang.methodhook.Logger;
import com.binpang.methodhook.MethodHookCallBack;



public abstract class AbstractBahaviorHookCallBack extends MethodHookCallBack {

	@Override
	public void beforeHookedMethod(HookParam param) {
		// TODO Auto-generated method stub
		/*int length = param.args.length;
		Object[] m = param.args;
		String args = "/";
		for(int i = 0; i < length;i++)
		{
			args+=(String)m[i]+"/";
		}*/
		Logger.logD("Invoke "+ param.method.getDeclaringClass().getName()+"->"+param.method.getName());
		this.descParam(param);
		//this.printStackInfo();
	}

	@Override
	public void afterHookedMethod(HookParam param) {
		// TODO Auto-generated method stub
		//Logger.log_behavior("End Invoke "+ param.method.toString());
	}
	
	private void printStackInfo(){
		Throwable ex = new Throwable();
		StackTraceElement[] stackElements = ex.getStackTrace();
		if(stackElements != null){
			StackTraceElement st;
			for(int i=0; i<stackElements.length; i++){
				st = stackElements[i];
				if(st.getClassName().startsWith("com.android.binpang")||st.getClassName().startsWith("de.robv.android.xposed.XposedBridge"))
					continue;
				Logger.logD(st.getClassName()+":"+st.getMethodName()+":"+st.getFileName()+":"+st.getLineNumber());
			}
		}
	}
	
	public abstract void descParam(HookParam param);


}
