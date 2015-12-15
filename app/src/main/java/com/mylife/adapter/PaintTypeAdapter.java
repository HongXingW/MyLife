package com.mylife.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mylife.materialdesign.R;

/**
 * Created by whx on 2015/11/5.
 */
public class PaintTypeAdapter extends BaseAdapter{

    private int[] images = {R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,
            R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher};
    private String[] types = {"任意线","直线","实心圆","空心圆","实心矩形","空心矩形"};
    private Context context;

    public PaintTypeAdapter(){

    }

    public PaintTypeAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return types.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = View.inflate(context,R.layout.paint_type_item,null);
        ImageView imageView = (ImageView)view.findViewById(R.id.paint_item_image);
        TextView textView = (TextView)view.findViewById(R.id.paint_item_name);
        imageView.setImageResource(images[position]);
        textView.setText(types[position]);

        return view;
    }
}
