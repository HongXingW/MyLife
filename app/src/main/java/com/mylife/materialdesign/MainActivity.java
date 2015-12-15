package com.mylife.materialdesign;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.mylife.draw.DoodleActivity;
import com.mylife.draw.DoodleView;
import com.mylife.write_note.WriteNote;

import java.io.File;

public class MainActivity extends ActionBarActivity implements FragmentDrawer.FragmentDrawerListener {

    public static int SCREEN_WIDTH,SCREEN_HEIGHT;
    private Toolbar mToolbar;
    private FragmentDrawer fragmentDrawer;
    private PopMenu popMenu;
    private long exitTime = 0;
    Fragment fragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SCREEN_HEIGHT = this.getWindowManager().getDefaultDisplay().getHeight();
        SCREEN_WIDTH = this.getWindowManager().getDefaultDisplay().getWidth();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        File destDir = new File(SharedClass.path);
        if (!destDir.exists()) {
            destDir.mkdirs();
//            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        }else{
//            Toast.makeText(this, "what the fuck", Toast.LENGTH_SHORT).show();
        }
        fragmentDrawer = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        fragmentDrawer.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        fragmentDrawer.setDrawerListener(this);

        popMenu = new PopMenu(MainActivity.this);
        popMenu.addItems(new String[]{getString(R.string.take_movie), getString(R.string.take_photos), getString(R.string.write_notes)});
        //
        popMenu.setOnItemClickListener(popmenuItemClickListener);

//        ImageButton menuButton;
//        menuButton = (ImageButton)findViewById(R.id.menuButton);
//        menuButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                popMenu.showAsDropDown(mToolbar);
//            }
//        });

        // display the first navigation drawer view on app launch
        displayView(5);
    }

    AdapterView.OnItemClickListener popmenuItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            Log.d("--------------" , position+"");
            switch (position){
                case 0:
                    Intent intent0 = new Intent();
                    intent0.setClass(MainActivity.this, DoodleActivity.class);
                    startActivity(intent0);
                    break;
                case 1:
                    break;
                case 2:
                    Intent intent2 = new Intent();
                    intent2.setClass(MainActivity.this, WriteNote.class);
                    startActivity(intent2);
                    break;
            }
            popMenu.dismiss();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_search){
            popMenu.showAsDropDown(mToolbar);
        }
        return true;
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(fragment instanceof MainFragment){
                //双击返回退出
                exitApp();
            }else{
                displayView(5);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
    @Override
    public void onBackPressed() {

    }

    private void displayView(int position) {

        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new LoginFragment();
                title = getString(R.string.nav_item_login);
                break;
            case 1:
                fragment = new RegisterFragment();
                title = getString(R.string.nav_item_regist);
                break;
            case 2:
                fragment = new AboutFragment();
                title = getString(R.string.nav_item_about);
                break;
            case 3:
                fragment = new SettingFragment();
                title = getString(R.string.nav_item_setting);
                break;
            case 4:
                fragment = new AboutFragment();
                title = getString(R.string.nav_item_exit);
                break;
            default:
                fragment = new MainFragment();
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }
}