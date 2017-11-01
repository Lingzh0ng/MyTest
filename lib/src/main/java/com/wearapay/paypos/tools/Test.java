package com.wearapay.paypos.tools;

import com.wearapay.paypos.tools.Utils;
public class Test {

	public static void main(String[] args) {
		String str = "sSL = ENC(STRXOR(0182 sSequenceCounter 00000000,0000000000000000),4953445F5343505F4B5332305F4B4943)";
		System.out.println(Utils.handleLineTxt(str));
	}
}