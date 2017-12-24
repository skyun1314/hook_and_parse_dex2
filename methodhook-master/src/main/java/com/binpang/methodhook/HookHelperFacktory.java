package com.binpang.methodhook;

public class HookHelperFacktory {
	
	private static HookHelperInterface hookHelper = new XposeHookHelperImpl();
	
	public static HookHelperInterface getHookHelper(){
		return hookHelper;
	}

}
