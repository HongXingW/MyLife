package com.mylife.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by whx on 2015/10/14.
 */
public class ViewPagerAdapter extends PagerAdapter{

    //界面列表
    private List<View> views;

    public ViewPagerAdapter(List<View> views){

        this.views = views;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    //    @Override
//    public void destroyItem(View container, int position, Object object) {
//
//        ((ViewPager)container).removeView(views.get(position));
//        //super.destroyItem(container, position, object);
//    }
    //初始化position位置的界面
//    @Override
//    public Object instantiateItem(View container, int position) {
//
//        ((ViewPager)container).addView(views.get(position), 0);
//        return views.get(position);
//    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
        //Log.i("-----------",position+"");
        return views.get(position);
    }

    @Override
    public int getCount() {
        if (views != null)
        {
            return views.size();
        }
        return 0;
    }

    //判断是否由对象生成界面
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }
}
