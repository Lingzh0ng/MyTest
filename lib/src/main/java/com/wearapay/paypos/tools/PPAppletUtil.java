package com.wearapay.paypos.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PPAppletUtil {

	public static void main(String[] args) {
		new PPAppletUtil().translate();
	}

	private String sourceFilePath = "C:\\Users\\Administrator\\Desktop\\";
	// private String sourceFileName = "applet.txt";
	private String sourceFileName = "aaa.txt";
	// private String sourceFileName = "applet_mNFC.txt";

	private String destFilePath = "C:\\Users\\Administrator\\Desktop\\";
	private String destFileName = "TransportationCardApplet1";
	// private String destFileName = "mNFCApplet";

	private String encoding = "UTF-8";

	private String off1 = "\t";
	private String end1 = "\n";
	private String off2 = off1 + off1;
	private String end2 = end1 + end1;

	private Set<String> stringSet = new HashSet<>();

	private void translate() {
		// String input =
		// "sSL = ENC(STRXOR(0182 sSequenceCounter 00000000,0000000000000000),4953445F5343505F4B5332305F4B4943)";
		// String input =
		// "sData1=ENC(3031323334353637 ,sSKUdek) ENC(38393A3B3C3D3E3F,sSKUdek)";

		// Pattern p2 = Pattern.compile("\\s*=\\s*");
		// Matcher m2 = p2.matcher(input);
		// input = m2.replaceAll("=");
		// L.o(this, "input : " + input);
		//
		// L.o(this, "handleMehtod : " + Utils.handleMultyMehtods(input));

		// if (true) {
		// return;
		// }

		L.o(this, "=================== translate start");

		try {
			File file = new File(sourceFilePath + sourceFileName);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);

				File fileOut = new File(destFilePath + destFileName + ".java");
				FileWriter fw = new FileWriter(fileOut);
				String off = "\t\t";
				String end = "\n";
				String lineTxt = null;

				createClassStart(fw);
				createParams(fw);
				appendStart(fw);

				while ((lineTxt = bufferedReader.readLine()) != null) {
					String sourceString;
					if (lineTxt.trim().length() > 0) {
						sourceString = off + "//" + lineTxt + end;
					} else {
						sourceString = off + lineTxt + end;
					}
					fw.append(sourceString);

					lineTxt = lineTxt.trim();
					// 多个空格替换成一个空格
					Pattern p = Pattern.compile("\\s+");
					Matcher m = p.matcher(lineTxt);
					lineTxt = m.replaceAll(" ");

					// 去掉等号两边的空格
					p = Pattern.compile("\\s*=\\s*");
					m = p.matcher(lineTxt);
					lineTxt = m.replaceAll("=");

					// 去掉逗号两边的空格
					p = Pattern.compile("\\s*,\\s*");
					m = p.matcher(lineTxt);
					lineTxt = m.replaceAll(",");

					// 去掉冒号两边的空格
					p = Pattern.compile("\\s*:\\s*");
					m = p.matcher(lineTxt);
					lineTxt = m.replaceAll(":");

					// L.o(this, lineTxt);
					String handleStirng = null;
					if (lineTxt.startsWith(";") || lineTxt.startsWith("insert")) {
						// 注释
					} else if (lineTxt.startsWith("?")) {
						// executor and log
						handleStirng = lineTxt.substring(1);
					} else if (lineTxt.startsWith("string")) {
						// String
						String params[] = lineTxt.substring(7).split(",");
						lineTxt = "";
						for (String param : params) {
							lineTxt += off;
							if (stringSet.contains(param)) {
								lineTxt += "//";
							} else {
								stringSet.add(param);
							}
							lineTxt += "String " + param + "=\"\";" + end;
						}
						fw.append(lineTxt);
					} else if (lineTxt.startsWith("if")
							|| lineTxt.endsWith(":")) {
						// goto
						fw.append("// TODO please handle by yourself !!!"
								+ " ========================================================================================"
								+ end);
						if (lineTxt.startsWith("if last !=")
								&& lineTxt.endsWith(" then goto \"err\"")) {
							int endIndex = lineTxt
									.indexOf(" then goto \"err\"");
							String params = lineTxt.substring(10, endIndex);
							fw.append(off + "if(!" + params.trim()
									+ ".equals(LAST)) {" + end + off + off
									+ "return false;" + end + off + "}" + end);
						}
					} else {
						if (lineTxt.length() > 0) {
							handleStirng = lineTxt;
						}
					}

					if (handleStirng != null) {
						handleLine(handleStirng, fw);
					}
				}

				appendEnd(fw);
				createClassEnd(fw);

				read.close();

				fw.close();
			} else {
				L.o(this, "找不到指定的文件1");
			}
		} catch (Exception e) {
			L.o(this, "读取文件内容出错2");
			e.printStackTrace();
		}

		L.o(this, "=================== translate end");
	}

	private String offTab = "";

	private void handleLine(String params, FileWriter fw) {
		params = Utils.handleLineTxt(params);
		try {
			fw.append(offTab + params);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ==============================================================================================
	private void createParams(FileWriter fw) {
		try {
			fw.append(off1 + "private String LAST;" + end1);
			fw.append(off1 + "private ApduResult apduResult;" + end2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void appendStart(FileWriter fw) {
		try {
			fw.append(off1
					+ "public boolean writeApplet() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, NoSuchProviderException {"
					+ end2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void appendEnd(FileWriter fw) {
		try {
			fw.append(end1 + off2 + "return true;" + end1);
			fw.append(off1 + "}" + end2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createClassStart(FileWriter fw) {
		try {
			fw.append("package com.wearapay.paypos.zte.applet;" + end2);
			fw.append("import com.wearapay.paypos.bluetooth.zte.IWearapayService;"
					+ end1);
			fw.append("import com.wearapay.paypos.bluetooth.zte.model.ApduResult;"
					+ end1);
			fw.append("import com.wearapay.paypos.zte.utils.Utils;" + end1);
			fw.append("import java.security.InvalidKeyException;" + end1);
			fw.append("import java.security.NoSuchAlgorithmException;" + end1);
			fw.append("import java.security.NoSuchProviderException;" + end1);
			fw.append("import java.security.spec.InvalidKeySpecException;"
					+ end1);
			fw.append("import javax.crypto.BadPaddingException;" + end1);
			fw.append("import javax.crypto.IllegalBlockSizeException;" + end1);
			fw.append("import javax.crypto.NoSuchPaddingException;" + end2);
			fw.append("public class " + destFileName
					+ " extends PPBaseApplet {" + end1);
			fw.append(off1 + "public " + destFileName
					+ "(IWearapayService wearapayBLEService) {" + end1);
			fw.append(off2 + "super(wearapayBLEService);" + end1);
			fw.append(off1 + "}" + end2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createClassEnd(FileWriter fw) {
		try {
			fw.append("}" + end1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
