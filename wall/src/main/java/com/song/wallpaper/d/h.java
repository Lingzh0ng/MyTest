package com.song.wallpaper.d;

/**
 * Created by lyz on 2017/8/11.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.widget.Toast;
import com.csii.framework.c.a.b;
import com.csii.framework.callback.CallBackIntent;
import com.csii.framework.callback.CallBackString;
import com.csii.framework.callback.CallBackUri;
import com.csii.framework.entity.PluginEntity;
import com.csii.framework.intf.ActivityInterface;
import com.csii.framework.view.CSIIActionSheetDialog;
import com.csii.framework.view.CSIIActionSheetDialog.a;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;


public class h
{
  private static h a = null;

  public static h a()
  {
    if (a == null)
      a = new h();
    return a;
  }

  @SuppressLint({"NewApi"})
  private String a(Context paramContext, Uri paramUri)
  {
    e.b("PhotoUtil", "getPath--uri:" + paramUri);
    boolean bool1;
    if (Build.VERSION.SDK_INT >= 19)
      bool1 = true;
    Object localObject;
    while (true)
    {
      if ((!bool1) || (!DocumentsContract.isDocumentUri(paramContext, paramUri)))
        break label701;
      e.b("PhotoUtil", "isKitKat:" + bool1);
      if (a(paramUri))
      {
        e.b("PhotoUtil", "getPath:isExternalStorageDocument");
        String str3 = DocumentsContract.getDocumentId(paramUri);
        e.b("PhotoUtil", "getPath:isExternalStorageDocument-docId-" + str3);
        String[] arrayOfString3 = str3.split(":");
        String str4 = arrayOfString3[0];
        e.b("PhotoUtil", "getPath:isExternalStorageDocument-type-" + str4);
        e.b("PhotoUtil", "getPath:isExternalStorageDocument-else:" + Environment.getExternalStorageDirectory() + "/" + arrayOfString3[1]);
        StorageManager localStorageManager = (StorageManager)paramContext.getSystemService("storage");
        try
        {
          String[] arrayOfString4 = (String[])localStorageManager.getClass().getMethod("getVolumePaths", new Class[0]).invoke(localStorageManager, new Object[0]);
          for (int i = 0; ; i++)
          {
            int j = arrayOfString4.length;
            localObject = null;
            if (i < j)
            {
              String str5 = arrayOfString4[i] + "/" + arrayOfString3[1];
              e.b("PhotoUtil", "isExternalStorageDocument--filepath:" + str5);
              boolean bool3 = new File(str5).exists();
              if (bool3)
                localObject = str5;
            }
            else
            {
              return localObject;
              bool1 = false;
              break;
            }
          }
        }
        catch (IllegalAccessException localIllegalAccessException)
        {
          e.a("PhotoUtil", "getPath:" + localIllegalAccessException.getMessage());
          return null;
        }
        catch (IllegalArgumentException localIllegalArgumentException)
        {
          e.a("PhotoUtil", "getPath:" + localIllegalArgumentException.getMessage());
          return null;
        }
        catch (InvocationTargetException localInvocationTargetException)
        {
          e.a("PhotoUtil", "getPath:" + localInvocationTargetException.getMessage());
          return null;
        }
        catch (NoSuchMethodException localNoSuchMethodException)
        {
          e.a("PhotoUtil", "getPath:" + localNoSuchMethodException.getMessage());
          return null;
        }
      }
    }
    if (b(paramUri))
    {
      e.b("PhotoUtil", "getPath:isDownloadsDocument");
      String str2 = DocumentsContract.getDocumentId(paramUri);
      return a(paramContext, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(str2).longValue()), null, null);
    }
    String[] arrayOfString1;
    String str1;
    Uri localUri;
    if (c(paramUri))
    {
      e.b("PhotoUtil", "getPath:isMediaDocument");
      arrayOfString1 = DocumentsContract.getDocumentId(paramUri).split(":");
      str1 = arrayOfString1[0];
      if ("image".equals(str1))
        localUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    }
    while (true)
    {
      String[] arrayOfString2 = new String[1];
      arrayOfString2[0] = arrayOfString1[1];
      return a(paramContext, localUri, "_id=?", arrayOfString2);
      if ("video".equals(str1))
      {
        localUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
      }
      else
      {
        if ("audio".equals(str1))
        {
          localUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
          continue;
          if ("content".equalsIgnoreCase(paramUri.getScheme()))
          {
            e.b("PhotoUtil", "content");
            if (d(paramUri))
              return paramUri.getLastPathSegment();
            return a(paramContext, paramUri, null, null);
          }
          boolean bool2 = "file".equalsIgnoreCase(paramUri.getScheme());
          localObject = null;
          if (!bool2)
            break;
          e.b("PhotoUtil", "file");
          return paramUri.getPath();
          label701: if ("content".equalsIgnoreCase(paramUri.getScheme()))
          {
            e.b("PhotoUtil", "content");
            if (d(paramUri))
              return paramUri.getLastPathSegment();
            return a(paramContext, paramUri, null, null);
          }
          if ("file".equalsIgnoreCase(paramUri.getScheme()))
          {
            e.b("PhotoUtil", "file");
            return paramUri.getPath();
          }
          e.a("PhotoUtil", "uri类型不在范围之内！");
          return null;
        }
        localUri = null;
      }
    }
  }

  // ERROR //
  private String a(Context paramContext, Uri paramUri, String paramString, String[] paramArrayOfString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 5
    //   3: iconst_1
    //   4: anewarray 87	java/lang/String
    //   7: dup
    //   8: iconst_0
    //   9: ldc 238
    //   11: aastore
    //   12: astore 6
    //   14: aload_1
    //   15: invokevirtual 242	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   18: aload_2
    //   19: aload 6
    //   21: aload_3
    //   22: aload 4
    //   24: aconst_null
    //   25: invokevirtual 248	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   28: astore 8
    //   30: aload 8
    //   32: ifnull +46 -> 78
    //   35: aload 8
    //   37: invokeinterface 253 1 0
    //   42: ifeq +36 -> 78
    //   45: aload 8
    //   47: aload 8
    //   49: ldc 238
    //   51: invokeinterface 257 2 0
    //   56: invokeinterface 261 2 0
    //   61: astore 9
    //   63: aload 8
    //   65: ifnull +10 -> 75
    //   68: aload 8
    //   70: invokeinterface 264 1 0
    //   75: aload 9
    //   77: areturn
    //   78: aload 8
    //   80: ifnull +10 -> 90
    //   83: aload 8
    //   85: invokeinterface 264 1 0
    //   90: aconst_null
    //   91: areturn
    //   92: astore 7
    //   94: aload 5
    //   96: ifnull +10 -> 106
    //   99: aload 5
    //   101: invokeinterface 264 1 0
    //   106: aload 7
    //   108: athrow
    //   109: astore 7
    //   111: aload 8
    //   113: astore 5
    //   115: goto -21 -> 94
    //
    // Exception table:
    //   from	to	target	type
    //   14	30	92	finally
    //   35	63	109	finally
  }

  @SuppressLint({"InlinedApi"})
  private void a(final Activity paramActivity, final CallBackUri paramCallBackUri)
  {
    e.b("PhotoUtil", "getPicture--" + Build.VERSION.SDK_INT);
    ActivityInterface localActivityInterface;
    if (b.a("PhotoUtil", paramActivity))
    {
      localActivityInterface = (ActivityInterface)paramActivity;
      if (Build.VERSION.SDK_INT < 20)
      {
        Intent localIntent1 = new Intent();
        localIntent1.setType("image/*");
        localIntent1.setAction("android.intent.action.GET_CONTENT");
        localActivityInterface.startActivityForResult(localIntent1, new CallBackIntent()
        {
          public void onResult(Intent paramAnonymousIntent)
          {
            if (paramAnonymousIntent != null)
            {
              paramCallBackUri.onResult(paramAnonymousIntent.getData());
              return;
            }
            e.a("PhotoUtil", "相册返回Intent为空！");
          }
        });
      }
    }
    else
    {
      e.a("PhotoUtil", "ActivityInterface接口转换失败！");
      return;
    }
    Intent localIntent2 = new Intent();
    localIntent2.setType("image/*");
    localIntent2.setAction("android.intent.action.OPEN_DOCUMENT");
    localActivityInterface.startActivityForResult(localIntent2, new CallBackIntent()
    {
      public void onResult(Intent paramAnonymousIntent)
      {
        if (paramAnonymousIntent != null)
        {
          e.b("PhotoUtil", "getPicture:" + paramAnonymousIntent.getData());
          String str = h.a(h.this, paramActivity, paramAnonymousIntent.getData());
          e.b("PhotoUtil", "文件url：" + str);
          paramCallBackUri.onResult(Uri.fromFile(new File(str)));
          return;
        }
        e.a("PhotoUtil", "相册返回Intent为空！");
      }
    });
  }

  private void a(ActivityInterface paramActivityInterface, Uri paramUri, final CallBackString paramCallBackString)
  {
    final String str = Environment.getExternalStorageDirectory() + "/" + System.currentTimeMillis() + ".png";
    Intent localIntent = new Intent("com.android.camera.action.CROP");
    localIntent.setDataAndType(paramUri, "image/*");
    localIntent.putExtra("crop", "true");
    localIntent.putExtra("aspectX", 1);
    localIntent.putExtra("aspectY", 1);
    localIntent.putExtra("outputX", 320);
    localIntent.putExtra("outputY", 320);
    localIntent.putExtra("scale", true);
    localIntent.putExtra("output", Uri.fromFile(new File(str)));
    localIntent.putExtra("return-data", false);
    localIntent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
    localIntent.putExtra("noFaceDetection", true);
    paramActivityInterface.startActivityForResult(localIntent, new CallBackIntent()
    {
      public void onResult(Intent paramAnonymousIntent)
      {
        paramCallBackString.onResult(str);
      }
    });
  }

  private void a(ActivityInterface paramActivityInterface, final CallBackString paramCallBackString)
  {
    Intent localIntent = new Intent("android.media.action.IMAGE_CAPTURE");
    String str1 = System.currentTimeMillis() + ".jpg";
    final String str2 = Environment.getExternalStorageDirectory() + "/" + str1;
    localIntent.putExtra("output", Uri.fromFile(new File(str2)));
    paramActivityInterface.startActivityForResult(localIntent, new CallBackIntent()
    {
      public void onResult(Intent paramAnonymousIntent)
      {
        e.b("PhotoUtil", "拍照结束");
        paramCallBackString.onResult(str2);
      }
    });
  }

  // ERROR //
  private void a(String paramString)
  {
    // Byte code:
    //   0: iconst_1
    //   1: istore_2
    //   2: ldc 34
    //   4: new 36	java/lang/StringBuilder
    //   7: dup
    //   8: invokespecial 37	java/lang/StringBuilder:<init>	()V
    //   11: ldc_w 408
    //   14: invokevirtual 43	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   17: aload_1
    //   18: invokevirtual 43	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   21: invokevirtual 50	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   24: invokestatic 55	com/csii/framework/d/e:b	(Ljava/lang/String;Ljava/lang/String;)V
    //   27: new 410	android/graphics/BitmapFactory$Options
    //   30: dup
    //   31: invokespecial 411	android/graphics/BitmapFactory$Options:<init>	()V
    //   34: astore_3
    //   35: aload_3
    //   36: iload_2
    //   37: putfield 415	android/graphics/BitmapFactory$Options:inJustDecodeBounds	Z
    //   40: aload_1
    //   41: aload_3
    //   42: invokestatic 421	android/graphics/BitmapFactory:decodeFile	(Ljava/lang/String;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   45: pop
    //   46: aload_3
    //   47: iconst_0
    //   48: putfield 415	android/graphics/BitmapFactory$Options:inJustDecodeBounds	Z
    //   51: aload_3
    //   52: getfield 424	android/graphics/BitmapFactory$Options:outWidth	I
    //   55: istore 5
    //   57: aload_3
    //   58: getfield 427	android/graphics/BitmapFactory$Options:outHeight	I
    //   61: istore 6
    //   63: iload 5
    //   65: iload 6
    //   67: if_icmplt +107 -> 174
    //   70: iload 5
    //   72: i2f
    //   73: ldc_w 428
    //   76: fcmpl
    //   77: ifle +97 -> 174
    //   80: aload_3
    //   81: getfield 424	android/graphics/BitmapFactory$Options:outWidth	I
    //   84: i2f
    //   85: ldc_w 428
    //   88: fdiv
    //   89: f2i
    //   90: istore 7
    //   92: iload 7
    //   94: ifgt +224 -> 318
    //   97: aload_3
    //   98: iload_2
    //   99: putfield 431	android/graphics/BitmapFactory$Options:inSampleSize	I
    //   102: aload_1
    //   103: aload_3
    //   104: invokestatic 421	android/graphics/BitmapFactory:decodeFile	(Ljava/lang/String;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   107: astore 8
    //   109: new 137	java/io/File
    //   112: dup
    //   113: aload_1
    //   114: invokespecial 140	java/io/File:<init>	(Ljava/lang/String;)V
    //   117: astore 9
    //   119: aload 9
    //   121: invokevirtual 144	java/io/File:exists	()Z
    //   124: ifeq +82 -> 206
    //   127: aload 9
    //   129: invokevirtual 434	java/io/File:delete	()Z
    //   132: pop
    //   133: aload 9
    //   135: invokevirtual 437	java/io/File:createNewFile	()Z
    //   138: pop
    //   139: new 439	java/io/FileOutputStream
    //   142: dup
    //   143: aload 9
    //   145: invokespecial 442	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   148: astore 12
    //   150: aload 8
    //   152: getstatic 445	android/graphics/Bitmap$CompressFormat:JPEG	Landroid/graphics/Bitmap$CompressFormat;
    //   155: bipush 80
    //   157: aload 12
    //   159: invokevirtual 451	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   162: pop
    //   163: aload 12
    //   165: invokevirtual 454	java/io/FileOutputStream:flush	()V
    //   168: aload 12
    //   170: invokevirtual 455	java/io/FileOutputStream:close	()V
    //   173: return
    //   174: iload 5
    //   176: iload 6
    //   178: if_icmpge +146 -> 324
    //   181: iload 6
    //   183: i2f
    //   184: ldc_w 456
    //   187: fcmpl
    //   188: ifle +136 -> 324
    //   191: aload_3
    //   192: getfield 427	android/graphics/BitmapFactory$Options:outHeight	I
    //   195: i2f
    //   196: ldc_w 456
    //   199: fdiv
    //   200: f2i
    //   201: istore 7
    //   203: goto -111 -> 92
    //   206: ldc 34
    //   208: new 36	java/lang/StringBuilder
    //   211: dup
    //   212: invokespecial 37	java/lang/StringBuilder:<init>	()V
    //   215: ldc_w 458
    //   218: invokevirtual 43	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   221: aload_1
    //   222: invokevirtual 43	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   225: invokevirtual 50	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   228: invokestatic 151	com/csii/framework/d/e:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   231: return
    //   232: astore 11
    //   234: aload 11
    //   236: invokevirtual 461	java/io/IOException:printStackTrace	()V
    //   239: goto -100 -> 139
    //   242: astore 14
    //   244: ldc 34
    //   246: new 36	java/lang/StringBuilder
    //   249: dup
    //   250: invokespecial 37	java/lang/StringBuilder:<init>	()V
    //   253: ldc_w 463
    //   256: invokevirtual 43	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   259: aload_1
    //   260: invokevirtual 43	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   263: invokevirtual 50	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   266: invokestatic 151	com/csii/framework/d/e:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   269: ldc 34
    //   271: aload 14
    //   273: invokevirtual 464	java/io/FileNotFoundException:getMessage	()Ljava/lang/String;
    //   276: invokestatic 151	com/csii/framework/d/e:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   279: return
    //   280: astore 13
    //   282: ldc 34
    //   284: new 36	java/lang/StringBuilder
    //   287: dup
    //   288: invokespecial 37	java/lang/StringBuilder:<init>	()V
    //   291: ldc_w 466
    //   294: invokevirtual 43	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   297: aload_1
    //   298: invokevirtual 43	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   301: invokevirtual 50	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   304: invokestatic 151	com/csii/framework/d/e:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   307: ldc 34
    //   309: aload 13
    //   311: invokevirtual 467	java/io/IOException:getMessage	()Ljava/lang/String;
    //   314: invokestatic 151	com/csii/framework/d/e:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   317: return
    //   318: iload 7
    //   320: istore_2
    //   321: goto -224 -> 97
    //   324: iload_2
    //   325: istore 7
    //   327: goto -235 -> 92
    //
    // Exception table:
    //   from	to	target	type
    //   133	139	232	java/io/IOException
    //   139	173	242	java/io/FileNotFoundException
    //   139	173	280	java/io/IOException
  }

  private void a(String paramString1, String paramString2, boolean paramBoolean)
  {
    int i = 0;
    e.b("PhotoUtil", "copy文件：oldPath----" + paramString1);
    e.b("PhotoUtil", "copy文件：newPath----" + paramString2);
    while (true)
    {
      File localFile;
      FileInputStream localFileInputStream;
      try
      {
        localFile = new File(paramString1);
        if (!localFile.exists())
          break;
        localFileInputStream = new FileInputStream(paramString1);
        FileOutputStream localFileOutputStream = new FileOutputStream(paramString2);
        byte[] arrayOfByte = new byte[5120];
        int j = localFileInputStream.read(arrayOfByte);
        if (j != -1)
        {
          i += j;
          localFileOutputStream.write(arrayOfByte, 0, j);
          continue;
        }
      }
      catch (Exception localException)
      {
        e.a("PhotoUtil", "复制单个文件操作出错");
        e.a("PhotoUtil", localException.getMessage());
        return;
      }
      localFileInputStream.close();
      if (paramBoolean)
      {
        localFile.delete();
        return;
      }
    }
    e.a("PhotoUtil", "文件不存在：" + paramString1);
  }

  private boolean a(Uri paramUri)
  {
    return "com.android.externalstorage.documents".equals(paramUri.getAuthority());
  }

  private File b(String paramString)
  {
    File localFile = new File("/data/data/" + paramString + "/" + "CaptureImageCache");
    if ((localFile != null) && (!localFile.exists()))
      localFile.mkdirs();
    return localFile;
  }

  private boolean b(Uri paramUri)
  {
    return "com.android.providers.downloads.documents".equals(paramUri.getAuthority());
  }

  private boolean c(Uri paramUri)
  {
    return "com.android.providers.media.documents".equals(paramUri.getAuthority());
  }

  private static boolean d(Uri paramUri)
  {
    return "com.google.android.apps.photos.content".equals(paramUri.getAuthority());
  }

  public void a(final PluginEntity paramPluginEntity, final boolean paramBoolean, final CallBackString paramCallBackString)
  {
    final ActivityInterface localActivityInterface;
    String[] arrayOfString1;
    String str;
    String[] arrayOfString2;
    if (b.a("PhotoUtil", paramPluginEntity.getActivity()))
    {
      localActivityInterface = (ActivityInterface)paramPluginEntity.getActivity();
      arrayOfString1 = new String[] { "拍照", "相册获取" };
      Object localObject = paramPluginEntity.getParams();
      if (localObject == null)
        break label183;
      str = localObject.toString();
      if (!"photograph".equals(str))
        break label124;
      arrayOfString2 = new String[] { "拍照" };
    }
    while (true)
      if (!Environment.getExternalStorageState().equals("mounted"))
      {
        e.a("PhotoUtil", "capture:SD卡不存在，无法进行拍照操作！");
        Toast.makeText(paramPluginEntity.getActivity(), "SD卡不存在，无法进行拍照操作！", 0).show();
        return;
        e.a("PhotoUtil", "ActivityInterface接口转换失败！");
        return;
        label124: if ("photo".equals(str))
          arrayOfString2 = new String[] { "相册获取" };
      }
      else
      {
        new CSIIActionSheetDialog(paramPluginEntity.getActivity()).a(arrayOfString2, new CSIIActionSheetDialog.a()
        {
          public void onItemClick(int paramAnonymousInt)
          {
            switch (paramAnonymousInt)
            {
              default:
                return;
              case 1:
                e.b("PhotoUtil", "选择拍照");
                h.a(h.this, localActivityInterface, new CallBackString()
                {
                  public void onResult(String paramAnonymous2String)
                  {
                    final File localFile = new File(paramAnonymous2String);
                    if (!localFile.exists())
                    {
                      e.a("PhotoUtil", "拍照获取图片失败，文件不存在！");
                      return;
                    }
                    if (h.1.this.b)
                    {
                      h.a(h.this, h.1.this.a, Uri.fromFile(localFile), new CallBackString()
                    {
                      public void onResult(String paramAnonymous3String)
                      {
                        localFile.delete();
                        String str = h.a(h.this, h.1.this.c.getActivity().getPackageName()).getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg";
                        h.a(h.this, paramAnonymous3String, str, true);
                        h.1.this.d.onResult(str);
                      }
                    });
                      return;
                    }
                    String str = h.a(h.this, h.1.this.c.getActivity().getPackageName()).getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg";
                    h.a(h.this, paramAnonymous2String, str, true);
                    h.b(h.this, str);
                    h.1.this.d.onResult(str);
                  }
                });
                return;
              case 2:
            }
            e.b("PhotoUtil", "选择相册");
            h.a(h.this, paramPluginEntity.getActivity(), new CallBackUri()
            {
              public void onResult(Uri paramAnonymous2Uri)
              {
                e.b("PhotoUtil", "选择相册callback:isCrop:" + h.1.this.b);
                e.b("PhotoUtil", "选择相册callback:uri:" + paramAnonymous2Uri);
                if (paramAnonymous2Uri == null)
                {
                  e.a("PhotoUtil", "获取图片uri地址为空！");
                  return;
                }
                if (h.1.this.b)
                {
                  h.a(h.this, h.1.this.a, paramAnonymous2Uri, new CallBackString()
                {
                  public void onResult(String paramAnonymous3String)
                  {
                    String str = h.a(h.this, h.1.this.c.getActivity().getPackageName()).getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg";
                    h.a(h.this, paramAnonymous3String, str, false);
                    h.1.this.d.onResult(str);
                  }
                });
                  return;
                }
                String str = h.a(h.this, h.1.this.c.getActivity().getPackageName()).getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg";
                h.a(h.this, h.a(h.this, h.1.this.c.getActivity(), paramAnonymous2Uri), str, false);
                h.b(h.this, str);
                h.1.this.d.onResult(str);
              }
            });
          }
        }).b();
        return;
        label183: arrayOfString2 = arrayOfString1;
      }
  }
}