package com.mylife.write_note;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mylife.adapter.FaceAdapter;
import com.mylife.adapter.FacePageAdeapter;
import com.mylife.biaoqing.CirclePageIndicator;
import com.mylife.biaoqing.JazzyViewPager;
import com.mylife.biaoqing.MyApplication;
import com.mylife.materialdesign.R;
import com.mylife.biaoqing.JazzyViewPager.TransitionEffect;
import com.mylife.materialdesign.SharedClass;
import com.mylife.tools.HashMapSerialize;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by whx on 2015/10/21.
 */
public class WriteNote extends ActionBarActivity {

    private Toolbar mToolbar;
    private ImageButton biaoQingButton,addButton;
    private LinearLayout biaoqingLinearLayout = null;
    private boolean isBiaoQingShow = false;
    private EditText editText;
    private JazzyViewPager biaoqingViewPager = null;
    private int currentPage = 0;
    private TransitionEffect mEffects[] = { TransitionEffect.Standard,
            TransitionEffect.Tablet, TransitionEffect.CubeIn,
            TransitionEffect.CubeOut, TransitionEffect.FlipVertical,
            TransitionEffect.FlipHorizontal, TransitionEffect.Stack,
            TransitionEffect.ZoomIn, TransitionEffect.ZoomOut,
            TransitionEffect.RotateUp, TransitionEffect.RotateDown,
            TransitionEffect.Accordion, };
    private List<String> keys = null;
    private InputMethodManager imm;
    private  String fileName,content;
    private String filePath = SharedClass.path+"/note";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_note);

        File destDir = new File(filePath);
        if (!destDir.exists()) {
            destDir.mkdirs();
//            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        }else{
//            Toast.makeText(this, "what the fuck", Toast.LENGTH_SHORT).show();
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar1);
        //mToolbar.setNavigationIcon(R.drawable.ic_profile);
        mToolbar.setTitle("写笔记~");
        setSupportActionBar(mToolbar);

        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
       setView();

    }
    private void setView(){

        editText = (EditText)findViewById(R.id.write_note_editor);

        biaoQingButton = (ImageButton)findViewById(R.id.biaoqing_button);
        biaoQingButton.setOnClickListener(new BiaoQingButtonListener());
        addButton = (ImageButton)findViewById(R.id.add_button);
        addButton.setOnClickListener(new AddContentButtonListener());

        biaoqingViewPager = (JazzyViewPager)findViewById(R.id.biaoqing_pager);
        biaoqingLinearLayout = (LinearLayout)findViewById(R.id.showBiaoQing);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBiaoQingShow) {
                    biaoqingLinearLayout.setVisibility(View.GONE);
                }
            }
        });
        initData();
        initFacePage();
    }
    private void initData() {
        Set<String> keySet = MyApplication.getInstance().getFaceMap().keySet();
        keys = new ArrayList<String>();
        keys.addAll(keySet);
    }
    private void initFacePage() {
        // TODO Auto-generated method stub
        List<View> lv = new ArrayList<>();
        for (int i = 0; i < MyApplication.NUM_PAGE; ++i) {
            lv.add(getGridView(i));
        }
        FacePageAdeapter adapter = new FacePageAdeapter(lv, biaoqingViewPager);
        biaoqingViewPager.setAdapter(adapter);
        biaoqingViewPager.setCurrentItem(currentPage);
        biaoqingViewPager.setTransitionEffect(mEffects[(int) Math.random()
                % mEffects.length]);
        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(biaoqingViewPager);
        adapter.notifyDataSetChanged();
        biaoqingLinearLayout.setVisibility(View.GONE);
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                if (arg0 < 0) {
                    currentPage = MyApplication.NUM_PAGE;
                } else if (arg0 > MyApplication.NUM_PAGE) {
                    currentPage = 0;
                } else {
                    currentPage = arg0;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // do nothing
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // do nothing
            }
        });

    }
    private GridView getGridView(int i) {
        // TODO Auto-generated method stub
        GridView gv = new GridView(this);
        gv.setNumColumns(7);
        gv.setSelector(new ColorDrawable(Color.TRANSPARENT));// ����GridViewĬ�ϵ��Ч��
        gv.setBackgroundColor(Color.TRANSPARENT);
        gv.setCacheColorHint(Color.TRANSPARENT);
        gv.setHorizontalSpacing(1);
        gv.setVerticalSpacing(1);
        gv.setVerticalScrollBarEnabled(false);
        gv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        gv.setGravity(Gravity.CENTER);
        gv.setAdapter(new FaceAdapter(this, i));
        gv.setOnTouchListener(forbidenScroll());
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                if (arg2 == MyApplication.NUM) {// ɾ������λ��
                    int selection = editText.getSelectionStart();
                    String text = editText.getText().toString();
                    if (selection > 0) {
                        String text2 = text.substring(selection - 1);
                        if ("]".equals(text2)) {
                            int start = text.lastIndexOf("[");
                            int end = selection;
                            editText.getText().delete(start, end);
                            return;
                        }
                        editText.getText().delete(selection - 1, selection);
                    }
                } else {
                    int count = currentPage * MyApplication.NUM + arg2;
                    // ע�͵Ĳ��֣���EditText����ʾ�ַ���
                    // String ori = msgEt.getText().toString();
                    // int index = msgEt.getSelectionStart();
                    // StringBuilder stringBuilder = new StringBuilder(ori);
                    // stringBuilder.insert(index, keys.get(count));
                    // msgEt.setText(stringBuilder.toString());
                    // msgEt.setSelection(index + keys.get(count).length());

                    // �����ⲿ�֣���EditText����ʾ����
                    Bitmap bitmap = BitmapFactory.decodeResource(
                            getResources(), (Integer) MyApplication
                                    .getInstance().getFaceMap().values()
                                    .toArray()[count]);
                    if (bitmap != null) {
                        int rawHeigh = bitmap.getHeight();
                        int rawWidth = bitmap.getHeight();
                        int newHeight = 40;
                        int newWidth = 40;
                        // ������������
                        float heightScale = ((float) newHeight) / rawHeigh;
                        float widthScale = ((float) newWidth) / rawWidth;
                        // �½�������
                        Matrix matrix = new Matrix();
                        matrix.postScale(heightScale, widthScale);
                        // ����ͼƬ����ת�Ƕ�
                        // matrix.postRotate(-30);
                        // ����ͼƬ����б
                        // matrix.postSkew(0.1f, 0.1f);
                        // ��ͼƬ��Сѹ��
                        // ѹ����ͼƬ�Ŀ�͸��Լ�kB��С����仯
                        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                                rawWidth, rawHeigh, matrix, true);
                        ImageSpan imageSpan = new ImageSpan(WriteNote.this,
                                newBitmap);
                        String emojiStr = keys.get(count);
                        SpannableString spannableString = new SpannableString(
                                emojiStr);
                        spannableString.setSpan(imageSpan,
                                emojiStr.indexOf('['),
                                emojiStr.indexOf(']') + 1,
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        editText.append(spannableString);
                    } else {
                        String ori = editText.getText().toString();
                        int index = editText.getSelectionStart();
                        StringBuilder stringBuilder = new StringBuilder(ori);
                        stringBuilder.insert(index, keys.get(count));
                        editText.setText(stringBuilder.toString());
                        editText.setSelection(index + keys.get(count).length());
                    }
                }
            }
        });
        return gv;
    }
    private View.OnTouchListener forbidenScroll() {
        return new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    return true;
                }
                return false;
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.save_note_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.save_note){
            saveNote();
        }
        return true;
    }
    private void saveNote(){

        if(!isBiaoQingShow){
            biaoqingLinearLayout.setVisibility(View.GONE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(),0);
        }
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
                content = editText.getText().toString();

                if(null == fileName || "".equals(fileName)){
                    SimpleDateFormat formatter   =  new  SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                    Date curDate  = new Date(System.currentTimeMillis());//获取当前时间
                    fileName = formatter.format(curDate);
                }
//                Log.d("-------------------",fileName+"::"+content);
                HashMap<String,String> file = new HashMap<>();
                file.put(fileName,content);

                try
                {
                    byte[] data = HashMapSerialize.serialize(file);
                    FileOutputStream outStream = new FileOutputStream(filePath+"/"+fileName,true);
                    //OutputStreamWriter writer = new OutputStreamWriter(outStream, Charset.forName("unicode"));
                    outStream.write(data);
//                    writer.write("\n");
//                    writer.flush();
//                    writer.close();//记得关闭

                    outStream.close();
                    Toast.makeText(WriteNote.this, "保存成功", Toast.LENGTH_SHORT).show();
                    dlg.dismiss();
                }
                catch (Exception e)
                {
                    Log.i("---------------------",e.getMessage());
//                    Toast.makeText(WriteNote.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
    class BiaoQingButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            if(isBiaoQingShow){
//                imm.showSoftInputFromInputMethod(editText.getWindowToken(), 0);
                biaoqingLinearLayout.setVisibility(View.GONE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);

                isBiaoQingShow = false;
            }else {
                imm.hideSoftInputFromWindow(editText.getWindowToken(),0);

                biaoqingLinearLayout.setVisibility(View.VISIBLE);
                isBiaoQingShow = true;
            }
        }
    }
    class AddContentButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {

        }
    }
}
