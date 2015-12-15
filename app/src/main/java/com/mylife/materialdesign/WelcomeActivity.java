package com.mylife.materialdesign;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;

/**
 * Created by whx on 2015/10/14.
 */
public class WelcomeActivity extends Activity {

    protected final int GOTO_MAIN_ACTIVITY = 0;
    private final int GOTO_GUIDE_ACTIVITY = 1;
    public static final String PREFS_NAME = "share";
    public static final String FIRST_RUN = "isFirstRun";


    private boolean isFirst;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.welcome);

        SharedPreferences sharedPreferences = this.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        isFirst = sharedPreferences.getBoolean(FIRST_RUN, true);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(isFirst){
            mHandler.sendEmptyMessageDelayed(GOTO_GUIDE_ACTIVITY, 1000);
            editor.putBoolean("isFirstRun", false);
            editor.commit();
        }else{

            mHandler.sendEmptyMessageDelayed(GOTO_MAIN_ACTIVITY, 2000);
        }

    }
    Handler mHandler = new Handler(new Callback() {

        public boolean handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case GOTO_MAIN_ACTIVITY:
                    Intent intent = new Intent();
                    intent.setClass(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case GOTO_GUIDE_ACTIVITY:
                    Intent intent1 = new Intent();
                    intent1.setClass(WelcomeActivity.this, GuideActivity.class);
                    startActivity(intent1);
                    finish();
                    break;
                default:
                    break;
            }
            return true;

        }
    });

}
