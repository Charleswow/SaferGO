package com.jack.isafety;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapCalcRouteResult;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapModelCross;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.autonavi.tbt.TrafficFacilityInfo;

import java.util.ArrayList;
import java.util.List;

public class WalkRouteCalculateActivity extends AppCompatActivity implements AMapNaviListener,AMapNaviViewListener {

    protected AMapNaviView mAMapNaviView;
    protected AMapNavi mAMapNavi;
    //    protected TTSController mTtsManager;
    protected NaviLatLng mEndLatlng = new NaviLatLng(39.90403,116.407525);
    protected NaviLatLng mStartLatlng;
    protected final List<NaviLatLng> sList = new ArrayList<NaviLatLng>();
    protected final List<NaviLatLng> eList = new ArrayList<NaviLatLng>();

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_navi);

        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

        Intent intent=getIntent();
        double jd = intent.getDoubleExtra("jd", 0);
        double wd = intent.getDoubleExtra("wd",0);
        mStartLatlng = new NaviLatLng(wd,jd);

        mAMapNaviView = findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.setAMapNaviViewListener(this);
        mAMapNaviView.setNaviMode(AMapNaviView.NORTH_UP_MODE);

        mAMapNavi = AMapNavi.getInstance(getApplicationContext());
        mAMapNavi.addAMapNaviListener(this);
        mAMapNavi.setEmulatorNaviSpeed(60);
        sList.add(mStartLatlng);
        eList.add(mEndLatlng);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mAMapNaviView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAMapNaviView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAMapNaviView.onDestroy();
        //since 1.6.0 不再在naviview destroy的时候自动执行AMapNavi.stopNavi();请自行执行
        mAMapNavi.stopNavi();
        mAMapNavi.destroy();
    }

    @Override
    public void onInitNaviFailure() {
        Toast.makeText(this, "init navi Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInitNaviSuccess() {
        //初始化成功
        mAMapNavi.calculateWalkRoute(this.mStartLatlng,this.mEndLatlng);
    }

    @Override
    public void onStartNavi(int type) {
        //开始导航回调
    }

    @Override
    public void onTrafficStatusUpdate() {

    }

    @Override
    public void onLocationChange(AMapNaviLocation location) {
        //当前位置回调
    }

    @Override
    public void onGetNavigationText(int type, String text) {
        //播报类型和播报文字回调
    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onEndEmulatorNavi() {
        //结束模拟导航
    }

    @Override
    public void onArriveDestination() {
        //到达目的地
    }

    @Override
    public void onCalculateRouteSuccess(int[] a){
        mAMapNavi.startNavi(NaviType.GPS);
    }

    @Override
    public void onCalculateRouteFailure(int errorInfo) {
        //路线计算失败
        Log.e("dm", "--------------------------------------------");
        Log.i("dm", "路线计算失败：错误码=" + errorInfo + ",Error Message= " + ErrorInfo.getError(errorInfo));
        Log.i("dm", "错误码详细链接见：http://lbs.amap.com/api/android-navi-sdk/guide/tools/errorcode/");
        Log.e("dm", "--------------------------------------------");
        Toast.makeText(this, "errorInfo：" + errorInfo + ",Message：" + ErrorInfo.getError(errorInfo), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onReCalculateRouteForYaw() {
        //偏航后重新计算路线回调
    }

    @Override
    public void onReCalculateRouteForTrafficJam() {
        //拥堵后重新计算路线回调
    }

    @Override
    public void onArrivedWayPoint(int wayID) {
        //到达途径点
    }

    @Override
    public void onGpsOpenStatus(boolean enabled) {
        //GPS开关状态回调
    }

    @Override
    public void onNaviSetting() {
        //底部导航设置点击回调
    }

    @Override
    public void onNaviMapMode(int isLock) {
        //地图的模式，锁屏或锁车
    }

    @Override
    public void onNaviCancel() {
        finish();
    }


    @Override
    public void onNaviTurnClick() {
        //转弯view的点击回调
    }

    @Override
    public void onNextRoadClick() {
        //下一个道路View点击回调
    }


    @Override
    public void onScanViewButtonClick() {
        //全览按钮点击回调
    }

    @Deprecated
    @Override
    public void onNaviInfoUpdated(AMapNaviInfo naviInfo) {
        //过时
    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapCameraInfos) {

    }

    @Override
    public void updateIntervalCameraInfo(AMapNaviCameraInfo aMapNaviCameraInfo, AMapNaviCameraInfo aMapNaviCameraInfo1, int i) {

    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] amapServiceAreaInfos) {

    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviinfo) {
        //导航过程中的信息更新，请看NaviInfo的具体说明
    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {
        //显示转弯回调
    }

    @Override
    public void hideCross() {
        //隐藏转弯回调
    }

    @Override
    public void showModeCross(AMapModelCross aMapModelCross) {

    }

    @Override
    public void hideModeCross() {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] laneInfos, byte[] laneBackgroundInfo, byte[] laneRecommendedInfo) {
        //显示车道信息

    }

    @Override
    public void showLaneInfo(AMapLaneInfo aMapLaneInfo) {

    }

    @Override
    public void hideLaneInfo() {
        //隐藏车道信息
    }



    @Override
    public void notifyParallelRoad(int i) {
        if (i == 0) {
            Toast.makeText(this, "当前在主辅路过渡", Toast.LENGTH_SHORT).show();
            Log.d("wlx", "当前在主辅路过渡");
            return;
        }
        if (i == 1) {
            Toast.makeText(this, "当前在主路", Toast.LENGTH_SHORT).show();

            Log.d("wlx", "当前在主路");
            return;
        }
        if (i == 2) {
            Toast.makeText(this, "当前在辅路", Toast.LENGTH_SHORT).show();

            Log.d("wlx", "当前在辅路");
        }
    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {
        //更新交通设施信息
    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {

    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {
        //更新巡航模式的统计信息
    }


    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {
        //更新巡航模式的拥堵信息
    }

    @Override
    public void onPlayRing(int i) {

    }

    @Override
    public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {

    }

    @Override
    public void onCalculateRouteFailure(AMapCalcRouteResult aMapCalcRouteResult) {

    }


    @Override
    public void onLockMap(boolean isLock) {
        //锁地图状态发生变化时回调
    }

    @Override
    public void onNaviViewLoaded() {
        Log.d("wlx", "导航页面加载成功");
        Log.d("wlx", "请不要使用AMapNaviView.getMap().setOnMapLoadedListener();会overwrite导航SDK内部画线逻辑");
    }

    @Override
    public boolean onNaviBackClick() {
        return false;
    }
}
