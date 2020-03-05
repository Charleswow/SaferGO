package com.jack.isafety;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.gongwen.marqueen.SimpleMF;
import com.gongwen.marqueen.SimpleMarqueeView;
import com.gongwen.marqueen.util.OnItemClickListener;
import com.maintabs_d_secondpages.HelpActivity;
import com.miantabs_d_share.EditTextDialog;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class  Maintabs_CActivity extends AppCompatActivity {



    private LinearLayout Header_Subway;
    private LinearLayout Header_Bus;
    private LinearLayout Header_Plane;
    private LinearLayout Header_Other;

    private TextView Header_Show,Body_Show;

    //相机相关
    public static final int VIDEO_REQUEST = 0;// 录像
    public static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    public static final int CROP_PHOTO = 2;  //相册

    private Uri imageUri;
    public static File tempFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintabs__c);

        initView();

        setListeners();

        //跑马灯
        final List<String> datas = Arrays.asList( "安全出行，幸福你我", "道路千万条，安全第一条", "行车不规范，亲人两行泪");
//SimpleMarqueeView<T>，SimpleMF<T>：泛型T指定其填充的数据类型，比如String，Spanned等
        SimpleMarqueeView<String> marqueeView = (SimpleMarqueeView) findViewById(R.id.marqueeView);
        SimpleMF<String> marqueeFactory = new SimpleMF(this);
        marqueeFactory.setData(datas);
        marqueeView.setMarqueeFactory(marqueeFactory);
        marqueeView.startFlipping();

        marqueeView.setOnItemClickListener(new OnItemClickListener<TextView, String>() {
            @Override
            public void onItemClickListener(TextView mView, String mData, int mPosition) {
                /**
                 * 注意：
                 * 当MarqueeView有子View时，mView：当前显示的子View，mData：mView所填充的数据，mPosition：mView的索引
                 * 当MarqueeView无子View时，mView：null，mData：null，mPosition：－1
                 */
                Toast.makeText(Maintabs_CActivity.this, "暂无通知", Toast.LENGTH_SHORT).show();
            }
        });


        //listview_item的添加
        //素材数组的构建
        int[] Listview_item_logo=new int[]{
                R.drawable.maintabs_c_pic2, R.drawable.maintabs_c_pic1, R.drawable.maintabs_c_pic4

        };
        int[] Listview_item_header=new int[]{
                R.mipmap.safergo,R.mipmap.safergo,R.mipmap.safergo
        };
        String[] Listview_item_title=new String[]{
                "关于SaferGo使用说明","安全出行女生篇","安全手册"
        };
        String[] Listview_item_content=new String[]{
                "SafeGo帮助说明","出行安全需谨慎，注意事项你要明","各种安全事故汇总"
        };

        List<Map<String,Object>> List_item=new ArrayList<Map<String,Object>>();
        for(int i=0;i<Listview_item_logo.length;i++){
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("main_listview_item_logo",Listview_item_logo[i]);
            map.put("main_listview_item_header",Listview_item_header[i]);
            map.put("main_listview_item_title",Listview_item_title[i]);
            map.put("main_listview_item_content",Listview_item_content[i]);
            List_item.add(map);
        }

        //配置适配器
        SimpleAdapter adapter=new SimpleAdapter(this,List_item, R.layout.maintabs_c_listview_item,new String[]{"main_listview_item_logo","main_listview_item_header","main_listview_item_title","main_listview_item_content"},
                new int[]{R.id.c_list_image, R.id.c_list_header,R.id.c_list_title,R.id.c_list_content});
        final ListView mMain_listview=findViewById(R.id.maintabs_c_listview);
        mMain_listview.setAdapter(adapter);

        //监听点击
        mMain_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> map= (Map<String, Object>) parent.getItemAtPosition(position);

                Toast.makeText(Maintabs_CActivity.this, map.get("main_listview_item_title").toString(), Toast.LENGTH_SHORT).show();

                switch (position){
                    case 0:
                        Intent intent=new Intent(Maintabs_CActivity.this, HelpActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1=new Intent(Maintabs_CActivity.this, Main_C_ListItem1.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2=new Intent(Maintabs_CActivity.this, Main_C_ListItem2.class);
                        startActivity(intent2);
                        break;
                }

            }
        });


        //浮动菜单
        BoomMenuButton bmb=findViewById(R.id.bmb);

        SimpleCircleButton.Builder builder = new SimpleCircleButton.Builder()
                .normalImageRes(R.drawable.maintabs_c_write)
                .imageRect(new Rect(Util.dp2px(20), Util.dp2px(20), Util.dp2px(60), Util.dp2px(60)))
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        // When the boom-button corresponding this builder is clicked.
                        shareDialog();

                    }
                });
        bmb.addBuilder(builder);

        SimpleCircleButton.Builder builder1 = new SimpleCircleButton.Builder()
                .normalImageRes(R.drawable.maintabs_c_collect)
                .imageRect(new Rect(Util.dp2px(20), Util.dp2px(20), Util.dp2px(60), Util.dp2px(60)));
        bmb.addBuilder(builder1);

        SimpleCircleButton.Builder builder2 = new SimpleCircleButton.Builder();
        builder2.normalImageRes(R.drawable.maintabs_c_camera);
        builder2.imageRect(new Rect(Util.dp2px(20), Util.dp2px(20), Util.dp2px(60), Util.dp2px(60)));
        builder2.listener(new OnBMClickListener() {
            @Override
            public void onBoomButtonClick(int index) {


            }
        });
        bmb.addBuilder(builder2);

        SimpleCircleButton.Builder builder3 = new SimpleCircleButton.Builder()
                .normalImageRes(R.drawable.maintabs_c_locate)
                .imageRect(new Rect(Util.dp2px(16), Util.dp2px(16), Util.dp2px(65), Util.dp2px(65)))
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        // When the boom-button corresponding this builder is clicked.
                        Intent intent=new Intent(Maintabs_CActivity.this,Maintabs_BActivity.class);
                        startActivity(intent);

                    }
                });
        bmb.addBuilder(builder3);

    }


    //初始化
    private void initView(){

        Header_Subway=findViewById(R.id.maintabs_c_body_header_2);
        Header_Bus=findViewById(R.id.maintabs_c_body_header_1);
        Header_Plane=findViewById(R.id.maintabs_c_body_header_3);
        Header_Other=findViewById(R.id.maintabs_c_body_header_4);

        Header_Show=findViewById(R.id.miantabs_c_body_body_show);
        Body_Show=findViewById(R.id.miantabs_c_body_body_show2);

    }


    //点击跳转
    private void setListeners(){
        Maintabs_CActivity.Onclick onclick=new Maintabs_CActivity.Onclick();

        Header_Subway.setOnClickListener(onclick);
        Header_Bus.setOnClickListener(onclick);
        Header_Plane.setOnClickListener(onclick);
        Header_Other.setOnClickListener(onclick);
        Header_Show.setOnClickListener(onclick);
        Body_Show.setOnClickListener(onclick);

    }


    //点击接口类
    class Onclick implements View.OnClickListener{
        @Override
        public void onClick(View v){
            Intent intent=null;
            switch (v.getId()){
                case R.id.maintabs_c_body_header_2:
                    intent=new Intent(Maintabs_CActivity.this,com.maintabs_secondpages.Maintabs_c_subway.class);
                    startActivity(intent);
                    break;
                case R.id.maintabs_c_body_header_1:
                    intent=new Intent(Maintabs_CActivity.this,com.maintabs_secondpages.Maintabs_c_bus.class);
                    startActivity(intent);
                    break;
                case R.id.maintabs_c_body_header_3:
                    intent=new Intent(Maintabs_CActivity.this,com.maintabs_secondpages.Maintabs_c_plane.class);
                    startActivity(intent);
                    break;
                case R.id.maintabs_c_body_header_4:
                    //Toast.makeText(Maintabs_CActivity.this, "尽请期待", Toast.LENGTH_SHORT).show();
                    intent=new Intent(Maintabs_CActivity.this,com.maintabs_secondpages.Maintabs_c_others.class);
                    startActivity(intent);
                    break;
                case R.id.miantabs_c_body_body_show:
                    //intent=new Intent(Maintabs_CActivity.this,com.maintabs_secondpages.Maintabs_c_plane.class);
                    Toast.makeText(Maintabs_CActivity.this,"尽请期待", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.miantabs_c_body_body_show2:
                    //intent=new Intent(Maintabs_CActivity.this,com.maintabs_secondpages.Maintabs_c_plane.class);
                    Toast.makeText(Maintabs_CActivity.this,"更多精彩", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    }

    //写动态界面
    private void shareDialog() {
        /*ShareBottomDialog dialog = new ShareBottomDialog();
        dialog.show(getSupportFragmentManager());*/
        EditTextDialog dialog = new EditTextDialog();
        dialog.show(getSupportFragmentManager());
    }


    //打开相机
    public void openCamera(Activity activity) {
        //獲取系統版本
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        // 激活相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                    "yyyy_MM_dd_HH_mm_ss");
            String filename = timeStampFormat.format(new Date());
            tempFile = new File(Environment.getExternalStorageDirectory(),
                    filename + ".jpg");
            if (currentapiVersion < 24) {
                // 从文件中创建uri
                imageUri = Uri.fromFile(tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            } else {
                //兼容android7.0 使用共享文件的形式
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
                //检查是否有存储权限，以免崩溃
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    Toast.makeText(this, "请开启存储权限", Toast.LENGTH_SHORT).show();
                    return;
                }
                imageUri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            }
        }
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
        activity.startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
    }

    /*
     * 判断sdcard是否被挂载
     */
    public static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    //请求码反馈
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_REQUEST_CAREMA://调用相机
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CROP_PHOTO); // 启动裁剪程序
                }
                break;
        }
    }

}
