package com.mylife.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mylife.materialdesign.R;
import com.mylife.model.PictureData;
import com.mylife.tools.ImageCache;

import java.util.ArrayList;

/**
 * Created by whx on 2016/2/3.
 */
public class DataAdapter extends BaseAdapter{

    private final static String IMAGEHOME = "http://10.60.108.39:520/mylife/res/";

    private Context mContext;
    private ArrayList<PictureData> datas;
    private LruCache<String, Bitmap> lruCache;

    public DataAdapter(Context context){
        mContext = context;
        lruCache = ImageCache.GetLruCache(mContext);
    }
    public void setDatas(ArrayList<PictureData> datas){
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Log.d("----------","test");
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_item,null);

            viewHolder.mImage = (ImageView)convertView.findViewById(R.id.m_image);
            viewHolder.mTextView = (TextView)convertView.findViewById(R.id.m_text);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        PictureData pictureData = datas.get(position);
        viewHolder.mTextView.setText(pictureData.getText());

        viewHolder.mImage.setImageResource(R.drawable.defaultcovers);

        new ImageCache(mContext, lruCache, viewHolder.mImage, IMAGEHOME
                + pictureData.getPicture(), "Mylife", 100, 100);

        Log.d("----------------",IMAGEHOME + pictureData.getPicture());
        return convertView;
    }

    private class ViewHolder{
        public ImageView mImage;
        public TextView mTextView;
    }
}
