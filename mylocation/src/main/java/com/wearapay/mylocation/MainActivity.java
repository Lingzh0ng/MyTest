package com.wearapay.mylocation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

  private TextView tvjd;
  private TextView tvwd;
  private TextView tvTime;
  private TextView tvWay;
  private TextView tvStatus;
  private ListView lv;
  private LocationManager locationManager;
  private LocationListener locationListener;
  private ArrayList<Address> list = new ArrayList<>();
  private ArrayList<String> strings = new ArrayList<>();
  private MyAdapter adapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initView();
    initLocationService();
    if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      // TODO: Consider calling
      //    ActivityCompat#requestPermissions
      // here to request the missing permissions, and then overriding
      //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
      //                                          int[] grantResults)
      // to handle the case where the user grants the permission. See the documentation
      // for ActivityCompat#requestPermissions for more details.
      ActivityCompat.requestPermissions(this, new String[] { ACCESS_FINE_LOCATION }, 1);

    } else {

      getLocation();
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      getLocation();
      Toast.makeText(this, "同意", Toast.LENGTH_SHORT).show();
    } else {
      Toast.makeText(this, "拒绝", Toast.LENGTH_SHORT).show();
    }
  }

  private void initView() {
    tvjd = (TextView) findViewById(R.id.tvjd);
    tvwd = (TextView) findViewById(R.id.tvwd);
    tvTime = (TextView) findViewById(R.id.tvTime);
    tvWay = (TextView) findViewById(R.id.tvWay);
    tvStatus = (TextView) findViewById(R.id.tvStatus);
    lv = (ListView) findViewById(R.id.lv);
    adapter = new MyAdapter();
    lv.setAdapter(adapter);
    lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
      @Override
      public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        list.clear();
        strings.clear();
        adapter.notifyDataSetChanged();
        return true;
      }
    });
  }

  private void initLocationService() {
    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    //GPS状态为可见时
    //GPS状态为服务区外时
    //GPS状态为暂停服务时
    locationListener = new LocationListener() {
      @Override public void onStatusChanged(String provider, int status, Bundle arg2) {
        switch (status) {
          //GPS状态为可见时
          case LocationProvider.AVAILABLE:
            System.out.println("ccc 当前GPS状态为可见状态");
            break;
          //GPS状态为服务区外时
          case LocationProvider.OUT_OF_SERVICE:
            System.out.println("ccc 当前GPS状态为服务区外状态");
            break;
          //GPS状态为暂停服务时
          case LocationProvider.TEMPORARILY_UNAVAILABLE:
            System.out.println("ccc 当前GPS状态为暂停服务状态");
            break;
        }
      }

      @Override public void onProviderEnabled(String provider) {
        System.out.println("ccc onProviderEnabled provider" + provider);
      }

      @Override public void onProviderDisabled(String provider) {
        System.out.println("ccc onProviderDisabled provider" + provider);
      }

      @Override public void onLocationChanged(Location location) {
        sendLocation(location);
      }
    };
  }

  private void getLocation() {
    List<String> providers = locationManager.getAllProviders();
    List<String> providers1 = locationManager.getProviders(true);
    System.out.println("ccc 获取所有可用的位置提供器 " + providers.toString());
    System.out.println("ccc 获取所有可用的位置提供器1 " + providers1.toString());
    if (getLocationInformation(providers, LocationManager.GPS_PROVIDER)) return;
    if (getLocationInformation(providers, LocationManager.NETWORK_PROVIDER)) return;
    getLocationFormPassive(providers);
  }

  private boolean getLocationInformation(final List<String> providers,
      final String locationProvider) {
    if (providers.contains(locationProvider) && locationManager.isProviderEnabled(
        locationProvider)) {
      System.out.println("ccc " + locationProvider + " start");

      if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
          != PackageManager.PERMISSION_GRANTED
          && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
          != PackageManager.PERMISSION_GRANTED) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        return false;
      }
      locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
      tvStatus.setText("正在定位");
      tvWay.setText(locationProvider);
      handler.postDelayed(new Runnable() {
        @Override public void run() {
          System.out.println("ccc " + locationProvider + " 定位超时");
          removeLocationListener();
          if (LocationManager.GPS_PROVIDER.equals(locationProvider)) {
            System.out.println("ccc 检查 NETWORK location");
            getLocationInformation(providers, LocationManager.NETWORK_PROVIDER);
          } else {
            getLocationFormPassive(providers);
          }
        }
      }, 60000);
      return true;
    }
    return false;
  }

  private static Handler handler = new Handler(Looper.getMainLooper());

  private void getLocationFormPassive(List<String> providers) {
    Location location;
    if (providers.contains(LocationManager.PASSIVE_PROVIDER)) {
      //如果是PASSIVE
      System.out.println("ccc PASSIVE start");
      if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
          != PackageManager.PERMISSION_GRANTED
          && ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION)
          != PackageManager.PERMISSION_GRANTED) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        return;
      }
      location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
      if (location != null) {
        System.out.println("ccc PASSIVE success");
        sendLocation(location);
      } else {
        System.out.println("ccc PASSIVE failure");
      }
    }
    //getLocation();
  }

  private void sendLocation(Location location) {
    System.out.println(location.getLatitude() + "   " + location.getLongitude());
    try {
      List<Address> fromLocation =
          new Geocoder(MainActivity.this).getFromLocation(location.getLatitude(),
              location.getLongitude(), 3);
      if (fromLocation != null && fromLocation.size() > 0) {
        Address address = fromLocation.get(0);
        list.add(address);
        strings.add(location.getProvider()
            + " : "
            + location.getLatitude()
            + "   "
            + location.getLongitude());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    adapter.notifyDataSetChanged();
    tvjd.setText(location.getLongitude() + "");
    tvwd.setText(location.getLatitude() + "");
    String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(location.getTime());
    //String utcTimeStr = getUTCTimeStr();
    //System.out.println(utcTimeStr);
    //String localTimeFromUTC = getLocalTimeFromUTC(utcTimeStr);
    //System.out.println(localTimeFromUTC);
    tvTime.setText(format);
  }

  private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

  public static String getLocalTimeFromUTC(String UTCTime) {
    java.util.Date UTCDate = null;
    String localTimeStr = null;
    try {
      UTCDate = format.parse(UTCTime);
      format.setTimeZone(TimeZone.getTimeZone("GMT-8"));
      localTimeStr = format.format(UTCDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return localTimeStr;
  }

  private void removeLocationListener() {
    if (locationManager != null) {
      //移除监听器
      locationManager.removeUpdates(locationListener);
      tvStatus.setText("停止定位");
    }
    handler.removeCallbacksAndMessages(null);
  }

  public static String getUTCTimeStr() {
    StringBuffer UTCTimeBuffer = new StringBuffer();
    // 1、取得本地时间：
    Calendar cal = Calendar.getInstance();
    // 2、取得时间偏移量：
    int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
    // 3、取得夏令时差：
    int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
    // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
    cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH) + 1;
    int day = cal.get(Calendar.DAY_OF_MONTH);
    int hour = cal.get(Calendar.HOUR_OF_DAY);
    int minute = cal.get(Calendar.MINUTE);
    UTCTimeBuffer.append(year).append("-").append(month).append("-").append(day);
    UTCTimeBuffer.append(" ").append(hour).append(":").append(minute);
    try {
      format.parse(UTCTimeBuffer.toString());
      return UTCTimeBuffer.toString();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }

  private class MyAdapter extends BaseAdapter {

    @Override public int getCount() {
      return strings.size();
    }

    @Override public Object getItem(int position) {
      return null;
    }

    @Override public long getItemId(int position) {
      return 0;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
      if (convertView == null) {
        convertView = View.inflate(MainActivity.this, R.layout.item, null);
      }
      Address address = list.get(position);
      TextView tvInfo = (TextView) convertView.findViewById(R.id.tvInfo);
      TextView tvCity = (TextView) convertView.findViewById(R.id.tvCity);
      tvInfo.setText(strings.get(position));
      tvCity.setText(address.toString());
      return convertView;
    }
  }
}
