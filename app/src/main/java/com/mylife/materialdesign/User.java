package com.mylife.materialdesign;

import android.graphics.Bitmap;

/**
 * Created by whx on 2015/10/20.
 */
public class User {

    private String userName;
    private String passWord;
    private String rgEmail;
    private Bitmap touXiang;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getRgEmail() {
        return rgEmail;
    }

    public void setRgEmail(String rgEmail) {
        this.rgEmail = rgEmail;
    }

    public Bitmap getTouXiang() {
        return touXiang;
    }

    public void setTouXiang(Bitmap touXiang) {
        this.touXiang = touXiang;
    }




}
