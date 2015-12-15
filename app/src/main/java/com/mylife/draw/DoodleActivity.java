package com.mylife.draw;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mylife.adapter.ImageAdapter;
import com.mylife.adapter.PaintTypeAdapter;
import com.mylife.materialdesign.R;
import com.mylife.materialdesign.SharedClass;
import com.mylife.tools.HashMapSerialize;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by whx on 2015/10/29.
 */
public class DoodleActivity extends ActionBarActivity implements View.OnClickListener,
        SeekBar.OnSeekBarChangeListener{

    private Toolbar mToolbar;
    private String filePath = SharedClass.path+"/doodle";
    private DoodleView doodleView;
    private ImageButton canvasColor,paintType,paintColor,earse,addPicture;
    private String fileName;
    private int position=20;
    private int paint_color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw_picture);

        File destDir = new File(filePath);
        if (!destDir.exists()) {
            destDir.mkdirs();
//            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        }else{
//            Toast.makeText(this, "what the fuck", Toast.LENGTH_SHORT).show();
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar2);
        //mToolbar.setNavigationIcon(R.drawable.ic_profile);
        mToolbar.setTitle("任性画~");
        setSupportActionBar(mToolbar);

        initContent();

    }

    private void initContent(){

        doodleView = (DoodleView)findViewById(R.id.draw_picture_view);
        //初始画笔粗度
        doodleView.setSize(dip2px(5));

        canvasColor = (ImageButton)findViewById(R.id.canvas_color);
        canvasColor.setOnClickListener(this);

        paintType = (ImageButton)findViewById(R.id.paint_type);
        paintType.setOnClickListener(this);

        paintColor = (ImageButton)findViewById(R.id.paint_color);
        paintColor.setOnClickListener(this);

        earse = (ImageButton)findViewById(R.id.earse);
        earse.setOnClickListener(this);

        addPicture = (ImageButton)findViewById(R.id.add_picture);
        addPicture.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.canvas_color:
                showCanvasColorDialog();
                break;
            case R.id.paint_color:
                showPaintColorDialog();
                break;
            case R.id.paint_type:
                showPaintTypeDialog();
                break;
            case R.id.earse:
                break;
            case R.id.add_picture:
                break;
        }
    }

    private void showCanvasColorDialog(){

        final AlertDialog dlg = new AlertDialog.Builder(this).create();
        //dlg.setContentView(R.layout.alert_dialog);
        dlg.show();
        final Window window = dlg.getWindow();
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
        window.setContentView(R.layout.canvas_color_choose);


        GridView gridView = (GridView)window.findViewById(R.id.gridview);

        gridView.setAdapter(new ImageAdapter(this));

        //单击GridView元素的响应
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        doodleView.setCanvasColor(0x46, 0x46, 0x46);
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                }
            }
        });

    }
    private void showPaintTypeDialog(){

        final AlertDialog dlg = new AlertDialog.Builder(this).create();
        //dlg.setContentView(R.layout.alert_dialog);
        dlg.show();
        final Window window = dlg.getWindow();
        // 设置窗口的内容页面,xml文件中定义view内容
        window.setContentView(R.layout.paint_type_choose);

        ListView listView = (ListView)window.findViewById(R.id.paint_listview);
        PaintTypeAdapter adapter = new PaintTypeAdapter(this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(DoodleActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                switch (position){
                    case 0:
                        break;
                    case 1:
                        doodleView.setType(DoodleView.ActionType.Line);
                        break;
                    case 2:
                        doodleView.setType(DoodleView.ActionType.FilledCircle);
                        break;
                    case 3:
                        doodleView.setType(DoodleView.ActionType.Circle);
                        break;
                    case 4:
                        doodleView.setType(DoodleView.ActionType.FillecRect);
                        break;
                    case 5:
                        doodleView.setType(DoodleView.ActionType.Rect);
                        break;
                }
            }
        });

        SeekBar painterCudu = (SeekBar)window.findViewById(R.id.paint_size);
        painterCudu.setOnSeekBarChangeListener(this);
        painterCudu.setProgress(position);
    }
    private void showPaintColorDialog(){

        final AlertDialog dlg = new AlertDialog.Builder(this).create();
        //dlg.setContentView(R.layout.alert_dialog);
        dlg.show();
        final Window window = dlg.getWindow();
        // 设置窗口的内容页面,xml文件中定义view内容
        window.setContentView(R.layout.paint_color_choose);

        ColorPickView pickView = (ColorPickView)window.findViewById(R.id.color_pick_view);
        final ImageView colorView = (ImageView)window.findViewById(R.id.pick_color);

        pickView.setOnColorChangedListener(new ColorPickView.OnColorChangedListener() {
            @Override
            public void onColorChange(int color) {

                colorView.setBackgroundColor(color);
                paint_color = color;
//                Log.d("----------------", color + "");
            }
        });

        Button chooseColor = (Button)window.findViewById(R.id.choose_color);
        chooseColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doodleView.setColor(paint_color);
                dlg.dismiss();
            }
        });

        Button cancelBtn = (Button)window.findViewById(R.id.cancel_choose_color);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return doodleView.onTouchEvent(event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.save_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.save_note){
            savePicture();
        }
        return true;
    }

    /**
     * 粗度单位由dp转为px
     * @param dpValue 值
     * @return
     */
    private int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    @Override
    public void onBackPressed() {
        if (!doodleView.back()) {
            super.onBackPressed();
        }
    }
    private void savePicture(){

        final AlertDialog dlg = new AlertDialog.Builder(this).create();
        //dlg.setContentView(R.layout.alert_dialog);
        dlg.show();
        final Window window = dlg.getWindow();
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
        window.setContentView(R.layout.alert_dialog);
        //据说下面这两行代码能让Alertdialog中的EditText显示软键盘
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        TextView title = (TextView)window.findViewById(R.id.alert_title);
        title.setText("起个名吧");

        final EditText fileNameText = (EditText)window.findViewById(R.id.note_file_name);

        Button saveNoteBtn = (Button)window.findViewById(R.id.save_note_btn);
        Button cancelBtn = (Button)window.findViewById(R.id.cancel_save);
        saveNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileName = fileNameText.getText().toString();

                if(null == fileName || "".equals(fileName)){
                    SimpleDateFormat formatter   =  new  SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                    Date curDate  = new Date(System.currentTimeMillis());//获取当前时间
                    fileName = formatter.format(curDate);
                }
//                Log.d("-------------------",fileName+"::"+content);

                if(savePicByPNG(doodleView.getBitmap(),filePath+"/"+fileName+".jpeg")){
                    Toast.makeText(DoodleActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                    dlg.dismiss();
                }else {
                    Toast.makeText(DoodleActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                    dlg.dismiss();
                }

            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });
    }

    private boolean savePicByPNG(Bitmap b, String filePath) {
        FileOutputStream fos = null;
        try {
//			if (!new File(filePath).exists()) {
//				new File(filePath).createNewFile();
//			}
            fos = new FileOutputStream(filePath);
            if (null != fos) {
                b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
                return true;
            }
        } catch (IOException e) {
           Log.i("-----Exception",e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

//        Log.i("------------------",(progress/5+1)+"");
        doodleView.setSize((progress/5+1));
        position = progress;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

}
