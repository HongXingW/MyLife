package com.mylife.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylife.materialdesign.R;
import com.mylife.model.SettingItem;

import java.util.Collections;
import java.util.List;

/**
 * Created by whx on 2015/10/8.
 */
public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.SetViewHolder>{

    List<SettingItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public SettingAdapter(Context context,List<SettingItem> data){

        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public SetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        int viewLocation = parent.getChildCount();
        if(viewLocation == 0){
            view = inflater.inflate(R.layout.setitem,parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(SetViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class SetViewHolder extends RecyclerView.ViewHolder{

        public SetViewHolder(View itemView) {
            super(itemView);
        }
    }
}
