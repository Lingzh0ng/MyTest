package com.wearapay.paypos.tools;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	
	//public static void main(String[] args) {
	//	String str = "sSL = ENC(STRXOR(0182 sSequenceCounter 00000000,0000000000000000),4953445F5343505F4B5332305F4B4943)";
	//	System.out.println(Utils.handleLineTxt(str));
	//}

	private static final String commandRegexStarts = "^[A-Ha-h0-9].*";
	private static final String commandRegexAll = "^[A-Ha-h0-9]+$";

	private static final String paramRegexStarts = "^[A-Za-z].*";
	private static final String methodStarts = "[A-Za-z_]+\\(.*";

	public static String handleLineTxt(String lineTxt) {
		if (lineTxt == null) {
			return lineTxt;
		}

		String str = lineTxt;

		String paramsString = "";
		while (true) {
			Pattern patter = Pattern.compile(methodStarts);
			Matcher matcher = patter.matcher(str);
			if (matcher.find()) {
				int startMethod = matcher.start();

				paramsString += str.substring(0, startMethod);
				int endMethod = 0;
				int leftCount = 0;
				boolean find = false;
				for (int i = startMethod; i < str.length(); i++) {
					if (str.charAt(i) == '(') {
						++leftCount;
					} else if (str.charAt(i) == ')') {
						--leftCount;
						endMethod = i;
						find = leftCount == 0;
					}
					if (find) {
						String s = str.substring(startMethod, endMethod + 1);
						s = handleMehtod(s);
						paramsString += s;

						str = str.substring(endMethod + 1);
						break;
					}
				}
			} else {
				paramsString += str;
				break;
			}
		}

		String off2 = "\t\t";
		String end1 = "\n";

		// 赋值操作
		String[] results = paramsString.split("=");
		if (results.length == 2) {
			// L.o(paramsString);
			paramsString = results[0] + "=" + Utils.mergeParams(results[1]);
		}

		// 发送命令
		if (paramsString.contains(";judge:sw=")) {
			results = paramsString.split(";judge:sw=");
			paramsString = "apduResult=sendCommand("
					+ Utils.mergeParams(results[0]) + ");" + end1;
			paramsString += off2
					+ "if(apduResult == null || apduResult.getStatusCode() == null || !"
					+ results[1]
					+ ".equals(Utils.bytes2HexString(apduResult.getStatusCode(), false))) {"
					+ end1;
			paramsString += off2 + "\treturn false;" + end1;
			paramsString += off2 + "}" + end1;
			paramsString += off2 + "if(apduResult != null) {" + end1;
			paramsString += off2 + "\tLAST=apduResult.getResult();" + end1;
			paramsString += off2 + "} else {" + end1;
			paramsString += off2 + "\tLAST=null;" + end1;
			paramsString += off2 + "}";
		}

		// sApduDownload sCMAC 或者 0084000004
		if (!paramsString.trim().startsWith("apduResult")
				&& (paramsString.trim().contains(" ") || Pattern.matches(
						Utils.commandRegexAll, paramsString))) {
			paramsString = "apduResult=sendCommand("
					+ Utils.mergeParams(paramsString.trim()) + ");" + end1;
			paramsString += off2 + "LAST=apduResult.getResult();";
		}

		paramsString = paramsString.replaceAll("last", "LAST");
		if (!paramsString.endsWith(";") && !paramsString.endsWith("}")) {
			paramsString += ";";
		}

		paramsString = off2 + paramsString + end1;

		return paramsString;
	}

	public static String handleMehtod(String method) {
		if (method == null) {
			return method;
		}

		// L.o("---- handleMehtod=" + method);
		if (method.contains("(") && method.contains(")")) {
			int start = method.indexOf("(");
			int end = method.lastIndexOf(")");
			String newPatch = method.substring(0, start + 1);
			String str = method.substring(start + 1, end);

			String paramsString = "";
			int lastIndex = 0;
			while (lastIndex < str.length()) {
				Pattern patter = Pattern.compile(methodStarts);
				Matcher matcher = patter.matcher(str);
				if (matcher.find()) {
					int startMethod = matcher.start();

					paramsString += str.substring(lastIndex, startMethod);
					int endMethod = 0;
					int leftCount = 0;
					boolean find = false;
					for (int i = startMethod; i < str.length(); i++) {
						if (str.charAt(i) == '(') {
							++leftCount;
						} else if (str.charAt(i) == ')') {
							--leftCount;
							endMethod = i;
							find = leftCount == 0;
						}
						if (find) {
							String s = str
									.substring(startMethod, endMethod + 1);
							s = handleMehtod(s);
							paramsString += s;

							str = str.substring(endMethod + 1);
							lastIndex = 0;
							break;
						}
					}
				} else {
					paramsString += str;
					break;
				}
			}

			newPatch += handleParams(paramsString);

			newPatch += ")";
			return newPatch;
		} else {
			return method;
		}
	}

	// byte组数 和 变量 以空格分开，使其合并
	// 84D800814b 01 8010 sData1, 03 --> 84D800814b018010,sData1,03
	private static String handleParams(String str) {
		// L.o("1---- handleParams=" + str);
		str = str.trim();
		String[] params = str.split(",");
		String newPatch = "";
		boolean isFirst = true;
		for (String p : params) {
			p = mergeParams(p);
			if (isFirst) {
				isFirst = false;
				newPatch += p;
			} else {
				newPatch += "," + p;
			}
		}

		// L.o("2---- handleParams=" + newPatch);
		return newPatch;
	}

	// byte组数 和 变量 以空格分开，使其合并
	// 84D800814b 01 8010 sData1 --> 84D800814b018010 sData1
	public static String mergeParams(String str) {
		// L.o("1---- mergeParams=" + str);
		str = str.trim();
		String[] params = str.split(" ");

		String temp = "";
		ArrayList<String> list = new ArrayList<>();
		for (String patch : params) {
			if (patch != null) {
				boolean isCommand = Pattern.matches(Utils.commandRegexAll,
						patch);
				if (isCommand) {
					temp += patch;
				} else {
					if (temp.length() > 0) {
						temp = "\"" + temp + "\"";
						list.add(temp);
					}
					list.add(patch);
					temp = "";
				}
			}
		}
		if (temp.length() > 0) {
			temp = "\"" + temp + "\"";
			list.add(temp);
		}

		StringBuilder sb = new StringBuilder();

		for (String s : list) {
			// L.o(s);
			sb.append(s).append("+");
		}

		String result = sb.toString();
		result = result.substring(0, result.length() - 1);

		return result;

	}

}
