package com.mylife.materialdesign;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.mylife.adapter.ViewPagerAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by whx on 2015/10/19.
 */
public class GuideActivity extends Activity implements ViewPager.OnPageChangeListener,View.OnClickListener{

    //引导图片资源
    private static final int[] guidPictures = { R.drawable.page1,
            R.drawable.page2, R.drawable.page3,
            R.drawable.page4 };

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private List<View> views;
    private ImageButton startButton;
    //记录当前选中位置
    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guid);
        views = new ArrayList<View>();

        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        ImageView iv;View v;
        //初始化引导图片列表
        for(int i=0; i<guidPictures.length-1; i++) {
            v = LayoutInflater.from(this).inflate(R.layout.guid_view,viewPager,false);
            iv = (ImageView)v.findViewById(R.id.ivPic);
            iv.setImageResource(guidPictures[i]);
            views.add(v);
        }
        v = LayoutInflater.from(this).inflate(R.layout.guid_last_view,viewPager,false);
        startButton = (ImageButton)v.findViewById(R.id.ibStart);
        startButton.setOnClickListener(this);
        views.add(v);


        //初始化Adapter
        viewPagerAdapter = new ViewPagerAdapter(views);
        viewPager.setAdapter(viewPagerAdapter);
        //绑定回调
        viewPager.setOnPageChangeListener(this);

        //mHandler.sendEmptyMessageDelayed(GOTO_MAIN_ACTIVITY, 2000);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(GuideActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
