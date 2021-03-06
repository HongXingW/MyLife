package com.mylife.materialdesign;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by whx on 2015/9/29.
 */
public class PopMenu {
    private ArrayList<String> itemList;
    private Context context;
    private PopupWindow popupWindow ;
    private ListView listView;
    //private OnItemClickListener listener;


    public PopMenu(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;

        itemList = new ArrayList<String>(5);

        View view = LayoutInflater.from(context).inflate(R.layout.popmenu, null);

        //���� listview
        listView = (ListView)view.findViewById(R.id.listView);
        listView.setAdapter(new PopAdapter());
        listView.setFocusableInTouchMode(true);
        listView.setFocusable(true);

        popupWindow = new PopupWindow(view, 180,
                ViewGroup.LayoutParams.WRAP_CONTENT);
//        popupWindow = new PopupWindow(view,
//                context.getResources().getDimensionPixelSize(R.dimen.popmenu_width),
//                ViewGroup.LayoutParams.WRAP_CONTENT);
        //点击其他地方及返回按钮消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(000000));
        popupWindow.setAnimationStyle(R.style.Animation_AppCompat_DropDownUp);
    }

    //���ò˵�����������
    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        //this.listener = listener;
        listView.setOnItemClickListener(listener);
    }

    //������Ӳ˵���
    public void addItems(String[] items) {
        for (String s : items)
            itemList.add(s);
    }

    //������Ӳ˵���
    public void addItem(String item) {
        itemList.add(item);
    }

    //����ʽ ���� pop�˵� parent ���½�
    public void showAsDropDown(View parent) {
        popupWindow.showAsDropDown(parent, MainActivity.SCREEN_WIDTH - 50,
                //��֤�ߴ��Ǹ�����Ļ�����ܶ�����
                context.getResources().getDimensionPixelSize(R.dimen.popmenu_yoff));

        // ʹ��ۼ�
        popupWindow.setFocusable(true);
        // ����������������ʧ
        popupWindow.setOutsideTouchable(true);
        //ˢ��״̬
        popupWindow.update();
    }

    //���ز˵�
    public void dismiss() {
        popupWindow.dismiss();
    }

    // ������
    private final class PopAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return itemList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return itemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.pomenu_item, null);
                holder = new ViewHolder();

                convertView.setTag(holder);

                holder.groupItem = (TextView) convertView.findViewById(R.id.textView);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.groupItem.setText(itemList.get(position));

            return convertView;
        }

        private final class ViewHolder {
            TextView groupItem;
        }
    }
}
