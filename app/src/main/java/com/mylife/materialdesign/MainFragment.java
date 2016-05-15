package com.mylife.materialdesign;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.mylife.adapter.DataAdapter;
import com.mylife.model.PictureData;
import com.mylife.tools.GetDataUtil;
import com.mylife.tools.URLContacts;

import java.util.ArrayList;

/**
 * Created by whx on 2015/9/30.
 */
public class MainFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private static final int INIT=0;
    private static final int REFRESH = 1;
    private static final int LOAD = 2;

    private SwipeRefreshLayout mSwipeLayout;
    private ListView mListView;
    private DataAdapter dataAdapter;
    private ArrayList<PictureData> list;

    public MainFragment() {
        // Required empty public constructor
    }
    private Handler dataHandler = new Handler() {
        @SuppressWarnings("unchecked")
        public void handleMessage(Message msg){
            if (msg.obj == null){
                //loadrRelativeLayout.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "网络异常,请检查！", Toast.LENGTH_SHORT).show();

            } else {
                if (msg.what == INIT){
                    //loadrRelativeLayout.setVisibility(View.GONE);
                    //dataLinearLayout.setVisibility(View.VISIBLE);
                    list.clear();
                    list = (ArrayList<PictureData>) msg.obj;
                    dataAdapter.setDatas(list);
                    mListView.setAdapter(dataAdapter);
                    dataAdapter.notifyDataSetChanged();
                }
                if (msg.what == REFRESH) {

                    list.clear();
                    list =(ArrayList<PictureData>) msg.obj;
                    dataAdapter.setDatas(list);
                    mListView.setAdapter(dataAdapter);
                    //pullToRefreshManager.refreshFinish(PullToRefreshLayout.SUCCEED);
                    mSwipeLayout.setRefreshing(false);
                    dataAdapter.notifyDataSetChanged();
                }
                if (msg.what == LOAD) {
                    list.addAll((ArrayList<PictureData>) msg.obj);
                    dataAdapter.setDatas(list);
                    mListView.setAdapter(dataAdapter);
                    mListView.setSelection(20);
                    //pullToLoadManager.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    dataAdapter.notifyDataSetChanged();
                }
            }
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main1, container, false);

        mSwipeLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swiperefresh);
        mListView = (ListView)rootView.findViewById(R.id.main_list);
        dataAdapter = new DataAdapter(getActivity());

        mSwipeLayout.setOnRefreshListener(this);
//        mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
//                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        list = new ArrayList<>();

        init();
        // Inflate the layout for this fragment
        return rootView;
    }

    private void init(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.obj = GetDataUtil.getPictureData(URLContacts.DATA_URL);
                msg.what = INIT;
                dataHandler.sendMessage(msg);
            }
        }).start();
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.obj = GetDataUtil.getPictureData(URLContacts.DATA_URL);
                msg.what = REFRESH;
                dataHandler.sendMessage(msg);
            }
        }).start();
    }
}
