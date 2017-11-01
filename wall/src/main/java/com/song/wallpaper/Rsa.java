package com.song.wallpaper;

import com.song.wallpaper.d.a;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by lyz on 2017/8/11.
 */

public class Rsa {

  private static final byte[] as;
  private static final char[] b;

  static {
    int i = 0;
    as = new byte['?'];
    b = new char[64];
    for (int j = 0; j < 128; j++) {
      as[j] = -1;
    }
    for (int k = 90; k >= 65; k--) {
      as[k] = ((byte) (k - 65));
    }
    for (int m = 122; m >= 97; m--) {
      as[m] = ((byte) (26 + (m - 97)));
    }
    for (int n = 57; n >= 48; n--) {
      as[n] = ((byte) (52 + (n - 48)));
    }
    as[43] = 62;
    as[47] = 63;
    for (int i1 = 0; i1 <= 25; i1++) {
      b[i1] = ((char) (i1 + 65));
    }
    int i2 = 26;
    for (int i3 = 0; i2 <= 51; i3++) {
      b[i2] = ((char) (i3 + 97));
      i2++;
    }
    int i4 = 52;
    while (i4 <= 61) {
      b[i4] = ((char) (i + 48));
      i4++;
      i++;
    }
    b[62] = '+';
    b[63] = '/';
  }



  public static byte[] a(RSAPublicKey paramRSAPublicKey, byte[] paramArrayOfByte)
  {
    if (paramRSAPublicKey == null)
    {
      System.out.println("加密公钥为空, 请设置");
      return null;
    }
    try
    {
      Cipher localCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
      localCipher.init(1, paramRSAPublicKey);
      byte[] arrayOfByte = localCipher.doFinal(paramArrayOfByte);
      return arrayOfByte;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      localNoSuchAlgorithmException.printStackTrace();
      return null;
    }
    catch (NoSuchPaddingException localNoSuchPaddingException)
    {
      localNoSuchPaddingException.printStackTrace();
      return null;
    }
    catch (InvalidKeyException localInvalidKeyException)
    {
      localInvalidKeyException.printStackTrace();
      return null;
    }
    catch (IllegalBlockSizeException localIllegalBlockSizeException)
    {
      localIllegalBlockSizeException.printStackTrace();
      return null;
    }
    catch (BadPaddingException localBadPaddingException)
    {
      localBadPaddingException.printStackTrace();
    }
    return null;
  }


  private static int a(char[] paramArrayOfChar) {
    int i = 0;
    if (paramArrayOfChar == null) {
      return i;
    }
    int j = paramArrayOfChar.length;
    int k = 0;
    //label13:
    //int m = 0;
    do {
      if (k < j) {
        if (a(paramArrayOfChar[k])) {
          return i;
        }
        i ++ ;
        paramArrayOfChar[i] = paramArrayOfChar[k];
      }
      k++;
    } while (true);
    //if (k < j) {
    //  if (as(paramArrayOfChar[k])) {
    //    break label47;
    //  }
    //  m = i + 1;
    //  paramArrayOfChar[i] = paramArrayOfChar[k];
    //}
    //for (; ; ) {
    //  k++;
    //  i = m;
    //  break label13;
    //  break;
    //  label47:
    //  m = i;
    //}
  }
  private static boolean a(char paramChar) {
    return (paramChar == ' ') || (paramChar == '\r') || (paramChar == '\n') || (paramChar == '\t');
  }

  private static boolean b(char paramChar) {
    return paramChar == '=';
  }

  private static boolean c(char paramChar) {
    return (paramChar < '?') && (as[paramChar] != -1);
  }


  public static RSAPublicKey b(String paramString)
  {
    try
    {
      byte[] arrayOfByte = a.a(paramString);
      RSAPublicKey localRSAPublicKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(arrayOfByte));
      return localRSAPublicKey;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public static String a(byte[] paramArrayOfByte) {
    int i = 0;
    if (paramArrayOfByte == null) {
      return null;
    }
    int j = 8 * paramArrayOfByte.length;
    if (j == 0) {
      return "";
    }
    int k = j % 24;
    int m = j / 24;
    int n;
    char[] arrayOfChar;
    int i1;
    int i2;
    label56:
    int i19;
    int i21;
    int i22;
    int i23;
    int i24;
    int i25;
    int i26;
    label127:
    int i27;
    if (k != 0) {
      n = m + 1;
      arrayOfChar = new char[n * 4];
      i1 = 0;
      i2 = 0;
      if (i1 >= m) {
        break label302;
      }
      int i18 = i + 1;
      i19 = paramArrayOfByte[i];
      int i20 = i18 + 1;
      i21 = paramArrayOfByte[i18];
      i22 = i20 + 1;
      i23 = paramArrayOfByte[i20];
      i24 = (byte) (i21 & 0xF);
      i25 = (byte) (i19 & 0x3);
      if ((i19 & 0xFFFFFF80) != 0) {
        break label259;
      }
      i26 = (byte) (i19 >> 2);
      if ((i21 & 0xFFFFFF80) != 0) {
        break label273;
      }
      i27 = (byte) (i21 >> 4);
      label142:
      if ((i23 & 0xFFFFFF80) != 0) {
        break label287;
      }
    }
    label259:
    label273:
    label287:
    for (int i28 = (byte) (i23 >> 6); ; i28 = (byte) (0xFC ^ i23 >> 6)) {
      int i29 = i2 + 1;
      arrayOfChar[i2] = b[i26];
      int i30 = i29 + 1;
      arrayOfChar[i29] = b[(i27 | i25 << 4)];
      int i31 = i30 + 1;
      arrayOfChar[i30] = b[(i28 | i24 << 2)];
      int i32 = i31 + 1;
      arrayOfChar[i31] = b[(i23 & 0x3F)];
      i1++;
      i2 = i32;
      i = i22;
      break label56;
      n = m;
      break;
      i26 = (byte) (0xC0 ^ i19 >> 2);
      break label127;
      i27 = (byte) (0xF0 ^ i21 >> 4);
      break label142;
    }
    label302:
    if (k == 8) {
      i12 = paramArrayOfByte[i];
      i13 = (byte) (i12 & 0x3);
      if ((i12 & 0xFFFFFF80) == 0) {
        i14 = (byte) (i12 >> 2);
        i15 = i2 + 1;
        arrayOfChar[i2] = b[i14];
        i16 = i15 + 1;
        arrayOfChar[i15] = b[(i13 << 4)];
        i17 = i16 + 1;
        arrayOfChar[i16] = '=';
        (i17 + 1);
        arrayOfChar[i17] = '=';
      }
    }
    while (k != 16) {
      for (; ; ) {
        int i12;
        int i13;
        int i15;
        int i16;
        int i17;
        return new String(arrayOfChar);
        int i14 = (byte) (0xC0 ^ i12 >> 2);
      }
    }
    int i3 = paramArrayOfByte[i];
    int i4 = paramArrayOfByte[(i + 1)];
    int i5 = (byte) (i4 & 0xF);
    int i6 = (byte) (i3 & 0x3);
    int i7;
    if ((i3 & 0xFFFFFF80) == 0) {
      i7 = (byte) (i3 >> 2);
      label468:
      if ((i4 & 0xFFFFFF80) != 0) {
        break label570;
      }
    }
    label570:
    for (int i8 = (byte) (i4 >> 4); ; i8 = (byte) (0xF0 ^ i4 >> 4)) {
      int i9 = i2 + 1;
      arrayOfChar[i2] = b[i7];
      int i10 = i9 + 1;
      arrayOfChar[i9] = b[(i8 | i6 << 4)];
      int i11 = i10 + 1;
      arrayOfChar[i10] = b[(i5 << 2)];
      (i11 + 1);
      arrayOfChar[i11] = '=';
      break;
      i7 = (byte) (0xC0 ^ i3 >> 2);
      break label468;
    }
  }
}
