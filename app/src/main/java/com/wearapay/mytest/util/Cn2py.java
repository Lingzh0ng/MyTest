package com.wearapay.mytest.util;

import android.util.Log;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created by lyz on 2016/12/16.
 */
public class Cn2py {
  public static String converterToFirstSpell(String chines){
    String pinyinName = "";
    char[] nameChar = chines.toCharArray();
    HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
    defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
    defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    for (int i = 0; i < nameChar.length; i++) {
      if (nameChar[i] > 128) {
        try {
          pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0].charAt(0);
        } catch (BadHanyuPinyinOutputFormatCombination e) {
          e.printStackTrace();
        }
      }else{
        pinyinName += nameChar[i];
      }
    }
    return pinyinName;
  }


  /**
   * Translate chinese to pinyin for the given string.
   */
  public static String getPinyin(String str) {
    if (str == null) {
      return null;
    }
    StringBuilder sb = new StringBuilder();
    String temp;
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      temp = getCharPinyin(c);
      if (temp == null) {
        sb.append(c);
      } else {
        sb.append(" " + temp + " ");
      }
    }
    return sb.toString().trim();
  }

  /**
   * Get pin yin for a char.
   * If the given char is not chinese character, return null.
   * If the given char is a chinese character, return its pinyin.
   */
  public static String getCharPinyin(char c) {
    HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
    format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
    String[] pinyin;
    try {
      pinyin = PinyinHelper.toHanyuPinyinStringArray(c, format);
    } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
      Log.e("PayPOSCommonConsts.TAG", badHanyuPinyinOutputFormatCombination.getMessage(),
          badHanyuPinyinOutputFormatCombination);
      return null;
    }
    if (pinyin == null) return null;
    return pinyin[0];
  }
}
