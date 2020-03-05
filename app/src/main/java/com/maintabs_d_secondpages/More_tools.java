package com.maintabs_d_secondpages;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.jack.isafety.R;
import com.jack.specialEffects.ThreeDSlidingLayout;

public class More_tools extends Activity {

    /**
     * 侧滑布局对象，用于通过手指滑动将左侧的菜单布局进行显示或隐藏。
     */
    private ThreeDSlidingLayout slidingLayout;

    /**
     * menu按钮，点击按钮展示左侧布局，再点击一次隐藏左侧布局。
     */
    private Button menuButton;

    /**
     * 放在content布局中的ListView。
     */
    private ListView contentListView;

    /**
     * 作用于contentListView的适配器。
     */
    private ArrayAdapter<String> contentListAdapter;

    /**
     * 用于填充contentListAdapter的数据源。
     */
    private String[] contentItems = {"指南针", "温度计", "日历",
            "闹钟", "天气", "录音机", "计算器",
            "出行运动", "镜子", "音乐", "视频",
            "博客", "清理", "学习", "其他",
            "更多精彩"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_tools);

        slidingLayout = (ThreeDSlidingLayout) findViewById(R.id.more_tools_slidingLayout);
        menuButton = (Button) findViewById(R.id.more_tools_menuButton);
        contentListView = (ListView) findViewById(R.id.more_tools_contentList);
        contentListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                contentItems);
        contentListView.setAdapter(contentListAdapter);
        // 将监听滑动事件绑定在contentListView上
        slidingLayout.setScrollEvent(contentListView);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (slidingLayout.isLeftLayoutVisible()) {
                    slidingLayout.scrollToRightLayout();
                } else {
                    slidingLayout.scrollToLeftLayout();
                }
            }
        });
        contentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = contentItems[position];
                Toast.makeText(More_tools.this, text, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
