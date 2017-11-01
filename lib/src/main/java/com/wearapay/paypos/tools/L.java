package com.wearapay.paypos.tools;

public class L {

	public static void o(Object obj, String msg) {
		o(obj.getClass().getSimpleName() + " " + msg);
	}

	public static void o(String msg) {
		System.out.println(msg);
	}
}
