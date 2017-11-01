package com.song.wallpaper.d;

/**
 * Created by lyz on 2017/8/11.
 */

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class i {
  private static final char[] a =
      { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };

  // ERROR //
  public static String a(String paramString) {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: iconst_0
    //   3: istore_2
    //   4: aload_0
    //   5: invokestatic 35	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   8: ifeq +10 -> 18
    //   11: ldc 37
    //   13: astore 16
    //   15: aload 16
    //   17: areturn
    //   18: aload_0
    //   19: invokestatic 42	com/csii/framework/d/a:a	(Ljava/lang/String;)[B
    //   22: astore 8
    //   24: getstatic 47	com/csii/framework/d/g:a	Ljava/lang/String;
    //   27: invokestatic 51	com/csii/framework/d/i:b	(Ljava/lang/String;)Ljava/security/interfaces/RSAPublicKey;
    //   30: astore 9
    //   32: new 53	java/security/spec/RSAPrivateKeySpec
    //   35: dup
    //   36: aload 9
    //   38: invokeinterface 59 1 0
    //   43: aload 9
    //   45: invokeinterface 62 1 0
    //   50: invokespecial 66	java/security/spec/RSAPrivateKeySpec:<init>	(Ljava/math/BigInteger;Ljava/math/BigInteger;)V
    //   53: astore 10
    //   55: ldc 68
    //   57: invokestatic 74	java/security/KeyFactory:getInstance	(Ljava/lang/String;)Ljava/security/KeyFactory;
    //   60: aload 10
    //   62: invokevirtual 78	java/security/KeyFactory:generatePrivate	(Ljava/security/spec/KeySpec;)Ljava/security/PrivateKey;
    //   65: astore 11
    //   67: ldc 80
    //   69: invokestatic 85	javax/crypto/Cipher:getInstance	(Ljava/lang/String;)Ljavax/crypto/Cipher;
    //   72: astore 12
    //   74: aload 12
    //   76: iconst_2
    //   77: aload 11
    //   79: invokevirtual 89	javax/crypto/Cipher:init	(ILjava/security/Key;)V
    //   82: aload 8
    //   84: arraylength
    //   85: istore 13
    //   87: new 91	java/io/ByteArrayOutputStream
    //   90: dup
    //   91: invokespecial 93	java/io/ByteArrayOutputStream:<init>	()V
    //   94: astore 6
    //   96: iconst_0
    //   97: istore 14
    //   99: iload 13
    //   101: iload_2
    //   102: isub
    //   103: ifle +74 -> 177
    //   106: iload 13
    //   108: iload_2
    //   109: isub
    //   110: sipush 256
    //   113: if_icmple +47 -> 160
    //   116: aload 12
    //   118: aload 8
    //   120: iload_2
    //   121: sipush 256
    //   124: invokevirtual 97	javax/crypto/Cipher:doFinal	([BII)[B
    //   127: astore 18
    //   129: aload 6
    //   131: aload 18
    //   133: iconst_0
    //   134: aload 18
    //   136: arraylength
    //   137: invokevirtual 101	java/io/ByteArrayOutputStream:write	([BII)V
    //   140: iload 14
    //   142: iconst_1
    //   143: iadd
    //   144: istore 19
    //   146: iload 19
    //   148: sipush 256
    //   151: imul
    //   152: istore_2
    //   153: iload 19
    //   155: istore 14
    //   157: goto -58 -> 99
    //   160: aload 12
    //   162: aload 8
    //   164: iload_2
    //   165: iload 13
    //   167: iload_2
    //   168: isub
    //   169: invokevirtual 97	javax/crypto/Cipher:doFinal	([BII)[B
    //   172: astore 18
    //   174: goto -45 -> 129
    //   177: aload 6
    //   179: invokevirtual 105	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   182: astore 15
    //   184: aload 6
    //   186: invokevirtual 108	java/io/ByteArrayOutputStream:close	()V
    //   189: new 110	java/lang/String
    //   192: dup
    //   193: aload 15
    //   195: invokespecial 113	java/lang/String:<init>	([B)V
    //   198: astore 16
    //   200: aload 6
    //   202: ifnull -187 -> 15
    //   205: aload 6
    //   207: invokevirtual 108	java/io/ByteArrayOutputStream:close	()V
    //   210: aload 16
    //   212: areturn
    //   213: astore 17
    //   215: aload 17
    //   217: invokevirtual 116	java/lang/Exception:printStackTrace	()V
    //   220: aload 16
    //   222: areturn
    //   223: astore 5
    //   225: aconst_null
    //   226: astore 6
    //   228: aload 5
    //   230: invokevirtual 116	java/lang/Exception:printStackTrace	()V
    //   233: aload 6
    //   235: ifnull +8 -> 243
    //   238: aload 6
    //   240: invokevirtual 108	java/io/ByteArrayOutputStream:close	()V
    //   243: aconst_null
    //   244: areturn
    //   245: astore 7
    //   247: aload 7
    //   249: invokevirtual 116	java/lang/Exception:printStackTrace	()V
    //   252: goto -9 -> 243
    //   255: astore_3
    //   256: aload_1
    //   257: ifnull +7 -> 264
    //   260: aload_1
    //   261: invokevirtual 108	java/io/ByteArrayOutputStream:close	()V
    //   264: aload_3
    //   265: athrow
    //   266: astore 4
    //   268: aload 4
    //   270: invokevirtual 116	java/lang/Exception:printStackTrace	()V
    //   273: goto -9 -> 264
    //   276: astore_3
    //   277: aload 6
    //   279: astore_1
    //   280: goto -24 -> 256
    //   283: astore 5
    //   285: goto -57 -> 228
    //
    // Exception table:
    //   from	to	target	type
    //   205	210	213	java/lang/Exception
    //   18	96	223	java/lang/Exception
    //   238	243	245	java/lang/Exception
    //   18	96	255	finally
    //   260	264	266	java/lang/Exception
    //   116	129	276	finally
    //   129	140	276	finally
    //   160	174	276	finally
    //   177	200	276	finally
    //   228	233	276	finally
    //   116	129	283	java/lang/Exception
    //   129	140	283	java/lang/Exception
    //   160	174	283	java/lang/Exception
    //   177	200	283	java/lang/Exception
  }

  public static byte[] a(RSAPublicKey paramRSAPublicKey, byte[] paramArrayOfByte) {
    if (paramRSAPublicKey == null) {
      System.out.println("加密公钥为空, 请设置");
      return null;
    }
    try {
      Cipher localCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
      localCipher.init(1, paramRSAPublicKey);
      byte[] arrayOfByte = localCipher.doFinal(paramArrayOfByte);
      return arrayOfByte;
    } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
      localNoSuchAlgorithmException.printStackTrace();
      return null;
    } catch (NoSuchPaddingException localNoSuchPaddingException) {
      localNoSuchPaddingException.printStackTrace();
      return null;
    } catch (InvalidKeyException localInvalidKeyException) {
      localInvalidKeyException.printStackTrace();
      return null;
    } catch (IllegalBlockSizeException localIllegalBlockSizeException) {
      localIllegalBlockSizeException.printStackTrace();
      return null;
    } catch (BadPaddingException localBadPaddingException) {
      localBadPaddingException.printStackTrace();
    }
    return null;
  }

  public static RSAPublicKey b(String paramString) {
    try {
      byte[] arrayOfByte = a.a(paramString);
      RSAPublicKey localRSAPublicKey = (RSAPublicKey) KeyFactory.getInstance("RSA")
          .generatePublic(new X509EncodedKeySpec(arrayOfByte));
      return localRSAPublicKey;
    } catch (Exception localException) {
      localException.printStackTrace();
    }
    return null;
  }
}
