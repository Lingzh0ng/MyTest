package com.song.wallpaper.d;

/**
 * Created by lyz on 2017/8/11.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import com.csii.network.Network;
import com.csii.network.gson.Gson;
import com.csii.network.okhttp.Request;
import com.csii.network.okhttp.callback.ResultBitmapCallback;
import com.csii.network.okhttp.callback.ResultCallback;
import com.csii.network.okhttp.callback.ResultStreamCallback;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class g
{
  public static String A = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwQRVJAmcYTlueOoh7ov+U0Qg+1AF3TeTEc68dumj7rCXINwz5BO9ADnAdBcdAiNtylja1L8Y227PF6vo1p38AhCfnqvzsThTyBQPaMi0kVAL5MUZH7Z5AX0Idi/bO64Wm70xe2iKqIYwLK+H/thGjK9G5D6n7vwXfTSS7zJ6Kz2fn1FNrpioHBS53zS0e5odrgIfC9XHCJec9EHSjm0Op7mqCuoejSV7vv4Z9Ltz/EKB83lB9RR1fkOYWiqm/R77+gjQBJOGLa1Lm8Qtr+V+VQN0h61jbK15A4qXGTFBCoiKynAPrA9D5cbc8/xRa6WZuR29ERYV5f+5EAKR1g8qRwIDAQAB";
  private static g b;
  private c c = null;
  private Context d;

  public g(Context paramContext)
  {
    this.d = paramContext.getApplicationContext();
  }

  public static g a(Context paramContext)
  {
    if (b == null)
      b = new g(paramContext);
    return b;
  }

  private Map<String, String> a(JSONObject paramJSONObject)
  {
    HashMap localHashMap = new HashMap();
    if (paramJSONObject == null)
      return localHashMap;
    Iterator localIterator = paramJSONObject.keys();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localHashMap.put(str, paramJSONObject.optString(str));
    }
    return localHashMap;
  }

  public static JSONObject a(Map<String, String> paramMap)
  {
    JSONObject localJSONObject = new JSONObject();
    if (paramMap == null)
      return localJSONObject;
    Iterator localIterator = paramMap.keySet().iterator();
    while (true)
    {
      String str;
      if (localIterator.hasNext())
        str = (String)localIterator.next();
      try
      {
        if ((((String)paramMap.get(str)).startsWith("[")) && (((String)paramMap.get(str)).endsWith("]")))
        {
          localJSONObject.put(str, new JSONArray((String)paramMap.get(str)));
        }
        else if ((((String)paramMap.get(str)).startsWith("{")) && (((String)paramMap.get(str)).endsWith("}")))
        {
          localJSONObject.put(str, new JSONObject((String)paramMap.get(str)));
        }
        else
        {
          localJSONObject.put(str, paramMap.get(str));
          continue;
          return localJSONObject;
        }
      }
      catch (JSONException localJSONException)
      {
      }
    }
  }

  private void b(Map<String, String> paramMap)
  {
    if (paramMap == null)
      paramMap = new HashMap();
    try
    {
      String str = d.c();
      paramMap.put("AppId", "100000");
      paramMap.put("TransTime", str);
      paramMap.put("MerchantId", "00000000000000000040");
      paramMap.put("MerchantSeq", ((String)paramMap.get("MerchantId")).substring(-4 + ((String)paramMap.get("MerchantId")).length()) + str + j.a(UUID.randomUUID().toString().replaceAll("-", "")));
      paramMap.put("DeviceId", Settings.Secure.getString(this.d.getContentResolver(), "android_id"));
      paramMap.put("SystemName", "android" + Build.VERSION.RELEASE);
      paramMap.put("Version", this.d.getPackageManager().getPackageInfo(this.d.getPackageName(), 0).versionName);
      paramMap.put("DeviceModel", Build.MODEL);
      TreeMap localTreeMap = new TreeMap(paramMap);
      Gson localGson = new Gson();
      e.b(g.class.getSimpleName(), "json======" + localGson.toJson(localTreeMap));
      byte[] arrayOfByte = j.a(localGson.toJson(localTreeMap)).getBytes("UTF-8");
      paramMap.put("ReqSignature", a.a(i.a(i.b(A), arrayOfByte)));
      if ((paramMap.containsKey("CardNo")) && (!TextUtils.isEmpty((CharSequence)paramMap.get("CardNo"))))
      {
        e.b(g.class.getSimpleName(), "CardNo加密前:" + (String)paramMap.get("CardNo"));
        paramMap.put("CardNo", a.a(i.a(i.b(A), ((String)paramMap.get("CardNo")).getBytes("utf-8"))));
        e.b(g.class.getSimpleName(), "CardNo加密后:" + (String)paramMap.get("CardNo"));
      }
      if ((paramMap.containsKey("Mobile")) && (!TextUtils.isEmpty((CharSequence)paramMap.get("Mobile"))))
      {
        e.b(g.class.getSimpleName(), "Mobile加密前:" + (String)paramMap.get("Mobile"));
        paramMap.put("Mobile", a.a(i.a(i.b(a), ((String)paramMap.get("Mobile")).getBytes("utf-8"))));
        e.b(g.class.getSimpleName(), "Mobile加密后:" + (String)paramMap.get("Mobile"));
      }
      if ((paramMap.containsKey("IdCode")) && (!TextUtils.isEmpty((CharSequence)paramMap.get("IdCode"))))
      {
        e.b(g.class.getSimpleName(), "IdCode加密前:" + (String)paramMap.get("IdCode"));
        paramMap.put("IdCode", a.a(i.a(i.b(a), ((String)paramMap.get("IdCode")).getBytes("utf-8"))));
        e.b(g.class.getSimpleName(), "IdCode加密后:" + (String)paramMap.get("IdCode"));
      }
      if ((paramMap.containsKey("AccNo")) && (!TextUtils.isEmpty((CharSequence)paramMap.get("AccNo"))))
      {
        e.b(g.class.getSimpleName(), "AccNo加密前:" + (String)paramMap.get("AccNo"));
        paramMap.put("AccNo", a.a(i.a(i.b(a), ((String)paramMap.get("AccNo")).getBytes("utf-8"))));
        e.b(g.class.getSimpleName(), "AccNo加密后:" + (String)paramMap.get("AccNo"));
      }
      return;
    }
    catch (Exception localException)
    {
      e.a(g.class.getSimpleName(), localException.getMessage());
    }
  }

  public void a(Object paramObject)
  {
    Network.getInstance(this.d).cancelRequest(paramObject);
  }

  public void a(final String paramString, Object paramObject, Map<String, String> paramMap, final a parama)
  {
    if (!paramString.contains("http"))
    {
      if (paramString.contains("/"))
        paramString = b.e + paramString.substring(1 + paramString.lastIndexOf("/"), paramString.length());
    }
    else
    {
      if (paramMap != null)
        break label123;
      paramMap = new HashMap();
      b(paramMap);
    }
    while (true)
    {
      Network.getInstance(this.d).requestGetAsyn(paramString, paramObject, paramMap, new ResultCallback()
      {
        public void a(String paramAnonymousString)
        {
          if (g.a(g.this) != null)
            paramAnonymousString = g.a(g.this).a(paramString, paramAnonymousString);
          parama.onSuccess(paramAnonymousString);
        }

        public void onError(Request paramAnonymousRequest, Exception paramAnonymousException)
        {
          parama.onError(paramAnonymousException.getMessage());
        }
      });
      return;
      paramString = b.e + paramString;
      break;
      label123: b(paramMap);
      if (this.c != null)
        paramMap = a(this.c.a(paramString, a(paramMap)));
    }
  }

  public void a(String paramString, Object paramObject, Map<String, String> paramMap, ResultStreamCallback paramResultStreamCallback)
  {
    if (paramMap == null)
      paramMap = new HashMap();
    while (true)
    {
      Network.getInstance(this.d).requestGetStreamAsyn(paramString, paramObject, paramMap, paramResultStreamCallback);
      return;
      if (this.c != null)
        paramMap = a(this.c.a(paramString, a(paramMap)));
    }
  }

  public void b(final String paramString, Object paramObject, Map<String, String> paramMap, final a parama)
  {
    if (!paramString.contains("http"))
    {
      if (paramString.contains("/"))
        paramString = b.e + paramString.substring(1 + paramString.lastIndexOf("/"), paramString.length());
    }
    else
    {
      if (paramMap != null)
        break label123;
      paramMap = new HashMap();
      b(paramMap);
    }
    while (true)
    {
      Network.getInstance(this.d).requestPostAsyn(paramString, paramObject, paramMap, new ResultCallback()
      {
        public void a(String paramAnonymousString)
        {
          if (g.a(g.this) != null)
            paramAnonymousString = g.a(g.this).a(paramString, paramAnonymousString);
          parama.onSuccess(paramAnonymousString);
        }

        public void onError(Request paramAnonymousRequest, Exception paramAnonymousException)
        {
          parama.onError(paramAnonymousException.getMessage());
        }
      });
      return;
      paramString = b.e + paramString;
      break;
      label123: b(paramMap);
      if (this.c != null)
        paramMap = a(this.c.a(paramString, a(paramMap)));
    }
  }

  public void c(String paramString, Object paramObject, Map<String, String> paramMap, final a parama)
  {
    if (!paramString.contains("http"))
    {
      if (paramString.contains("/"))
        paramString = b.e + paramString.substring(1 + paramString.lastIndexOf("/"), paramString.length());
    }
    else
    {
      if (paramMap != null)
        break label117;
      paramMap = new HashMap();
    }
    while (true)
    {
      Network.getInstance(this.d).requestPostBitmapAsyn(paramString, paramObject, paramMap, new ResultBitmapCallback()
      {
        public void onError(Request paramAnonymousRequest, Exception paramAnonymousException)
        {
          parama.onError(paramAnonymousException.getMessage());
        }

        public void onResponse(Bitmap paramAnonymousBitmap)
        {
          parama.onSuccess(paramAnonymousBitmap);
        }
      });
      return;
      paramString = b.e + paramString;
      break;
      label117: if (this.c != null)
        paramMap = a(this.c.a(paramString, a(paramMap)));
    }
  }

  public void d(String paramString, Object paramObject, Map<String, String> paramMap, final a parama)
  {
    if (!paramString.contains("http"))
      if (!paramString.contains("/"))
        break label108;
    label108: for (paramString = b.e + paramString.substring(1 + paramString.lastIndexOf("/"), paramString.length()); ; paramString = b.e + paramString)
    {
      if (paramMap == null)
        paramMap = new HashMap();
      b(paramMap);
      String str = a(paramMap).toString();
      Network.getInstance(this.d).requestPostAsyn(paramString, paramObject, str, new ResultCallback()
      {
        public void a(String paramAnonymousString)
        {
          parama.onSuccess(paramAnonymousString);
        }

        public void onError(Request paramAnonymousRequest, Exception paramAnonymousException)
        {
          parama.onError(paramAnonymousException.getMessage());
        }
      });
      return;
    }
  }

  public static abstract interface a
  {
    public abstract void onError(Object paramObject);

    public abstract void onSuccess(Object paramObject);
  }
}