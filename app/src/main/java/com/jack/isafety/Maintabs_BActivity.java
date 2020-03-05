package com.jack.isafety;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import static com.jack.service.BaseService.mSocket;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.LocationSource;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.socket.emitter.Emitter;

public class Maintabs_BActivity extends AppCompatActivity implements LocationSource, AMapLocationListener {

    public MapView mMapView = null;
    AMap aMap;
    public MyLocationStyle myLocationStyle;
    private UiSettings mUiSettings;//定义一个UiSettings对象
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明mListener对象，定位监听器
    private LocationSource.OnLocationChangedListener mListener = null;
    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;
    private double jingdu;
    private double weidu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().show();
        }
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_maintabs__b);


        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);

        myLocationStyle = new MyLocationStyle();

        setMap();



        Button btn_protect = findViewById(R.id.protect);
        Button btn_daohang = findViewById(R.id.daohang);


        //导航功能
       btn_protect.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {

//               Intent intent = new Intent(Maintabs_BActivity.this, Maintabs_AActivity.class);
//               startActivity(intent);

                Intent intent = getPackageManager().getLaunchIntentForPackage("com.autonavi.minimap");
// 这里如果intent为空，就说名没有安装要跳转的应用嘛
                if (intent != null) {
                    // 这里跟Activity传递参数一样的嘛，不要担心怎么传递参数，还有接收参数也是跟Activity和Activity传参数一样
//                    intent.putExtra("name", "Liu xiang");
//                    intent.putExtra("birthday", "1983-7-13");
                    startActivity(intent);
                } else {
                    // 没有安装要跳转的app应用，提醒一下
                    Toast.makeText(getApplicationContext(), "哟，赶紧下载安装高德地图吧~", Toast.LENGTH_LONG).show();
                }

                ////shouhu
//                aMapLocation.getLocationType();
//                double a = aMapLocation.getLatitude();//获取纬度
//                Log.i("纬度", String.valueOf(a));
//                double b =  aMapLocation.getLongitude();//获取经度
//                String  friend="2508074836@qq.com";
//                sendLocation(String.valueOf(a),String.valueOf(b),friend);




            }
        });


       //守护功能
        btn_daohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1=new Intent(Maintabs_BActivity.this, FriendListActivity.class);
                startActivity(intent1);

                Toast.makeText(getApplicationContext(), "选择你的小天使！", Toast.LENGTH_LONG).show();



                LatLng latLng = new LatLng(jingdu,weidu);

                final Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title("北京").snippet("DefaultMarker"));

                marker.setIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(),R.mipmap.safergo)));
                marker.setTitle("好朋友");


            }
        });

        mSocket.on("protectResult",getLocation);

    }

    protected void setMap() {

        if (aMap == null) {
            aMap = mMapView.getMap();
        }

        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        //连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false

        aMap.showIndoorMap(true);//显示室内地图

        mUiSettings = aMap.getUiSettings();

        mUiSettings.setCompassEnabled(true);//指南针

        //定位按钮
        aMap.setLocationSource(this);//通过aMap对象设置定位数据源的监听
        mUiSettings.setMyLocationButtonEnabled(true); //显示默认的定位按钮
        aMap.setMyLocationEnabled(true);// 可触发定位并显示当前位置

        location();

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title("路径规划");
        markerOptions.position(new LatLng(jingdu, weidu));

        Marker marker = aMap.addMarker(markerOptions);
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intent = new Intent();
                intent.setClass(Maintabs_BActivity.this, WalkRouteCalculateActivity.class);
                intent.putExtra("jd", jingdu);
                intent.putExtra("wd", weidu);
                startActivity(intent);
                return false;
            }
        });
    }

    private void location() {

        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为Hight_Accuracy高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

    }

    @Override
    public void activate(LocationSource.OnLocationChangedListener listener) {
        mListener = listener;
    }

    @Override
    public void deactivate() {
        mListener = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
        //mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
    private static final int LOCATION_CODE = 1;

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            LocationManager lm = (LocationManager) Maintabs_BActivity.this.getSystemService(Maintabs_BActivity.this.LOCATION_SERVICE);
            boolean ok = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (ok) {//开了定位服务
                if (ContextCompat.checkSelfPermission(Maintabs_BActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    Log.e("BRG", "没有权限");
                    // 没有权限，申请权限。
                    // 申请授权。
                    ActivityCompat.requestPermissions(Maintabs_BActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_CODE);
                    //  Toast.makeText(getActivity(), "没有权限", Toast.LENGTH_SHORT).show();

                } else {

                    // 有权限了，去放肆吧。
                    // Toast.makeText(getActivity(), "有权限", Toast.LENGTH_SHORT).show();
                }
            } else {
                //                Log.e("BRG", "系统检测到未开启GPS定位服务");
                //                Toast.makeText(Maintabs_BActivity.this, "系统检测到未开启GPS定位服务", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, 1315);
            }
        }
        if (aMapLocation.getErrorCode() == 0) {
            //定位成功回调信息，设置相关消息
            aMapLocation.getLocationType();//获取
            double a = aMapLocation.getLatitude();//获取纬度
            Log.i("纬度", String.valueOf(a));
            double b =  aMapLocation.getLongitude();//获取经度
            //            LatLng latLng = new LatLng(jingdu,weidu);
//
//            final Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title("北京").snippet("DefaultMarker"));
             String  friend="2508074836@qq.com";
//            marker.setIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
//                    .decodeResource(getResources(),R.mipmap.safergo)));
//            marker.setTitle("好朋友");

       sendLocation(String.valueOf(a),String.valueOf(b),friend);






//            LatLng latLng = new LatLng(jingdu,weidu);
//
//            final Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title("北京").snippet("DefaultMarker"));
//
//            marker.setIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
//                    .decodeResource(getResources(),R.mipmap.safergo)));
//            marker.setTitle("好朋友");



            Log.i("Longitude", String.valueOf(aMapLocation.getLongitude()));
            aMapLocation.getAccuracy();//获取精度信息
            @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(aMapLocation.getTime());
            df.format(date);//定位时间
            aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
            aMapLocation.getCountry();//国家信息
            aMapLocation.getProvince();//省信息
            aMapLocation.getCity();//城市信息
            aMapLocation.getDistrict();//城区信息
            aMapLocation.getStreet();//街道信息
            aMapLocation.getStreetNum();//街道门牌号信息
            aMapLocation.getCityCode();//城市编码
            aMapLocation.getAdCode();//地区编码

            jingdu = aMapLocation.getLongitude();
            weidu = aMapLocation.getLatitude();

            // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
            if (isFirstLoc) {
                //设置缩放级别
                aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                //将地图移动到定位点
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));
                //点击定位按钮 能够将地图的中心移动到定位点
                mListener.onLocationChanged(aMapLocation);
                //获取定位信息
                StringBuffer buffer = new StringBuffer();
                buffer.append(aMapLocation.getCountry() + ""
                        + aMapLocation.getProvince() + ""
                        + aMapLocation.getCity() + ""
                        + aMapLocation.getProvince() + ""
                        + aMapLocation.getDistrict() + ""
                        + aMapLocation.getStreet() + ""
                        + aMapLocation.getStreetNum());
                Toast.makeText(getApplicationContext(), buffer.toString(), Toast.LENGTH_LONG).show();
                isFirstLoc = false;
            }
        } else {
            //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
            Log.e("AmapError", "location Error, ErrCode:"
                    + aMapLocation.getErrorCode() + ", errInfo:"
                    + aMapLocation.getErrorInfo());
            //                Toast.makeText(getApplicationContext(), "定位失败", Toast.LENGTH_LONG).show();

        }
    }


    public void quanxian(){
        LocationManager lm = (LocationManager) Maintabs_BActivity.this.getSystemService(Maintabs_BActivity.this.LOCATION_SERVICE);
        boolean ok = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (ok) {//开了定位服务
            if (ContextCompat.checkSelfPermission(Maintabs_BActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                // Log.e("BRG","没有权限");
                // 没有权限，申请权限。
                // 申请授权。
                ActivityCompat.requestPermissions(Maintabs_BActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_CODE);
                //Toast.makeText(getActivity(), "没有权限", Toast.LENGTH_SHORT).show();

            } else {

                // 有权限了，去放肆吧。
                // Toast.makeText(getActivity(), "有权限", Toast.LENGTH_SHORT).show();
            }
        } else {
                //            Log.e("BRG","系统检测到未开启GPS定位服务");
                //            Toast.makeText(Maintabs_BActivity.this, "系统检测到未开启GPS定位服务", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 1315);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被用户同意。
                } else {
                    // 权限被用户拒绝了。
                    // Toast.makeText(Maintabs_BActivity.this, "定位权限被禁止，相关地图功能无法使用！",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void sendLocation(String jingDu, String weiDu, String to){
        Map<String,String> map = new HashMap<>();
//        map.put("from",from);
        map.put("to",to);
        map.put("jingdu",jingDu);
        map.put("weidu",weiDu);

        JSONObject jsonObject = JSONObject.fromObject(map);
        mSocket.emit("protect", jsonObject);

    }

    private Emitter.Listener getLocation = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    org.json.JSONObject data = null;
                    try {
                        data = new org.json.JSONObject((String)args[0]);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        jingdu = Double.parseDouble(data.getString("jingdu"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        weidu = Double.parseDouble(data.getString("weidu"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        String from = data.getString("from");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    Log.e("wjf", String.valueOf(jingdu));
                }            });

        }
    };
}

