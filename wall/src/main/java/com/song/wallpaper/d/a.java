package com.song.wallpaper.d;

public final class a {
  private static final byte[] a;
  private static final char[] b;

  static {
    int i = 0;
    a = new byte['?'];
    b = new char[64];
    for (int j = 0; j < 128; j++) {
      a[j] = -1;
    }
    for (int k = 90; k >= 65; k--) {
      a[k] = ((byte) (k - 65));
    }
    for (int m = 122; m >= 97; m--) {
      a[m] = ((byte) (26 + (m - 97)));
    }
    for (int n = 57; n >= 48; n--) {
      a[n] = ((byte) (52 + (n - 48)));
    }
    a[43] = 62;
    a[47] = 63;
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

  private static int a(char[] paramArrayOfChar) {
    int i = 0;
    if (paramArrayOfChar == null) {
      return i;
    }
    int j = paramArrayOfChar.length;
    int k = 0;
    label13:
    int m;
    if (k < j) {
      if (a(paramArrayOfChar[k])) {
        break label47;
      }
      m = i + 1;
      paramArrayOfChar[i] = paramArrayOfChar[k];
    }
    for (; ; ) {
      k++;
      i = m;
      break label13;
      break;
      label47:
      m = i;
    }
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

  private static boolean a(char paramChar) {
    return (paramChar == ' ') || (paramChar == '\r') || (paramChar == '\n') || (paramChar == '\t');
  }

  public static byte[] a(String paramString) {
    if (paramString == null) {
    }
    byte[] arrayOfByte1;
    int m;
    int n;
    label244:
    int i3;
    int i4;
    char c3;
    char c4;
    int i6;
    do {
      do {
        do {
          char[] arrayOfChar;
          char c1;
          int i2;
          char c2;
          do {
            int i1;
            do {
              int i;
              do {
                return null;
                arrayOfChar = paramString.toCharArray();
                i = a(arrayOfChar);
              } while (i % 4 != 0);
              int j = i / 4;
              if (j == 0) {
                return new byte[0];
              }
              arrayOfByte1 = new byte[j * 3];
              int k = 0;
              m = 0;
              for (n = 0; ; n++) {
                if (n >= j - 1) {
                  break label244;
                }
                int i12 = k + 1;
                char c5 = arrayOfChar[k];
                if (!c(c5)) {
                  break;
                }
                int i13 = i12 + 1;
                char c6 = arrayOfChar[i12];
                if (!c(c6)) {
                  break;
                }
                int i14 = i13 + 1;
                char c7 = arrayOfChar[i13];
                if (!c(c7)) {
                  break;
                }
                k = i14 + 1;
                char c8 = arrayOfChar[i14];
                if (!c(c8)) {
                  break;
                }
                int i15 = a[c5];
                int i16 = a[c6];
                int i17 = a[c7];
                int i18 = a[c8];
                int i19 = m + 1;
                arrayOfByte1[m] = ((byte) (i15 << 2 | i16 >> 4));
                int i20 = i19 + 1;
                arrayOfByte1[i19] = ((byte) ((i16 & 0xF) << 4 | 0xF & i17 >> 2));
                m = i20 + 1;
                arrayOfByte1[i20] = ((byte) (i18 | i17 << 6));
              }
              i1 = k + 1;
              c1 = arrayOfChar[k];
            } while (!c(c1));
            i2 = i1 + 1;
            c2 = arrayOfChar[i1];
          } while (!c(c2));
          i3 = a[c1];
          i4 = a[c2];
          int i5 = i2 + 1;
          c3 = arrayOfChar[i2];
          (i5 + 1);
          c4 = arrayOfChar[i5];
          if ((c(c3)) && (c(c4))) {
            break label503;
          }
          if ((!b(c3)) || (!b(c4))) {
            break;
          }
        } while ((i4 & 0xF) != 0);
        byte[] arrayOfByte3 = new byte[1 + n * 3];
        System.arraycopy(arrayOfByte1, 0, arrayOfByte3, 0, n * 3);
        arrayOfByte3[m] = ((byte) (i3 << 2 | i4 >> 4));
        return arrayOfByte3;
      } while ((b(c3)) || (!b(c4)));
      i6 = a[c3];
    } while ((i6 & 0x3) != 0);
    byte[] arrayOfByte2 = new byte[2 + n * 3];
    System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, n * 3);
    int i7 = m + 1;
    arrayOfByte2[m] = ((byte) (i3 << 2 | i4 >> 4));
    arrayOfByte2[i7] = ((byte) ((i4 & 0xF) << 4 | 0xF & i6 >> 2));
    return arrayOfByte2;
    label503:
    int i8 = a[c3];
    int i9 = a[c4];
    int i10 = m + 1;
    arrayOfByte1[m] = ((byte) (i3 << 2 | i4 >> 4));
    int i11 = i10 + 1;
    arrayOfByte1[i10] = ((byte) ((i4 & 0xF) << 4 | 0xF & i8 >> 2));
    (i11 + 1);
    arrayOfByte1[i11] = ((byte) (i9 | i8 << 6));
    return arrayOfByte1;
  }

  private static boolean b(char paramChar) {
    return paramChar == '=';
  }

  private static boolean c(char paramChar) {
    return (paramChar < '?') && (a[paramChar] != -1);
  }
}
