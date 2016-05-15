package com.mylife.materialdesign;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mylife.tools.HttpUtils;
import com.mylife.tools.SVProgressHUD;
import com.mylife.tools.URLContacts;

import org.apache.http.Header;

import java.util.Arrays;


public class LoginFragment extends Fragment {

    private EditText userName,passWord;
    private Button loginBtn;
    private InputMethodManager imm;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        userName = (EditText)rootView.findViewById(R.id.userName);
        passWord = (EditText)rootView.findViewById(R.id.password);

        imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        loginBtn = (Button)rootView.findViewById(R.id.login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userName.getText().toString();
                String password = passWord.getText().toString();

                login(username,password);
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }

    private boolean login(String username,String password){

        RequestParams params=new RequestParams();
        params.put("username",username);
        params.put("password",password);

        HttpUtils.post(URLContacts.LOGIN_URL,params,new AsyncHttpResponseHandler(){

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                if(new String(bytes).equals("1")){

                    imm.hideSoftInputFromWindow(passWord.getWindowToken(), 0);
                    SVProgressHUD.showSuccessWithStatus(getActivity(), "登录成功！");
                    MainActivity.fragment = new MainFragment();
                }else{
                    imm.hideSoftInputFromWindow(passWord.getWindowToken(),0);
                    SVProgressHUD.showErrorWithStatus(getActivity(), "用户名或密码错误", SVProgressHUD.SVProgressHUDMaskType.GradientCancel);
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                imm.hideSoftInputFromWindow(passWord.getWindowToken(),0);
                SVProgressHUD.showInfoWithStatus(getActivity(), "无网络连接", SVProgressHUD.SVProgressHUDMaskType.None);
            }
        });
        return false;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
