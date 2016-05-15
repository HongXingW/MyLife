package com.mylife.materialdesign;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class RegisterFragment extends Fragment {

    private EditText userName,passWord,qrpassWord,email,yanzhengma;
    private Button registerBtn,cancelBtn;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        userName = (EditText)rootView.findViewById(R.id.userNameText);
        passWord = (EditText)rootView.findViewById(R.id.passwordText);
        qrpassWord = (EditText)rootView.findViewById(R.id.passwordSureText);
        email = (EditText)rootView.findViewById(R.id.emailText);
        yanzhengma = (EditText)rootView.findViewById(R.id.yanzhengText);

        final String username = userName.getText().toString();
        final String password = passWord.getText().toString();
        final String qrpassword = passWord.getText().toString();
        final String email = passWord.getText().toString();
        final String yanzhengma = passWord.getText().toString();

        registerBtn = (Button)rootView.findViewById(R.id.registerbtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qrpassword.equals(password)){
                    registerMethod(username,password,email,yanzhengma);
                }else{

                }

            }
        });

        cancelBtn = (Button)rootView.findViewById(R.id.clearbtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }

    private void registerMethod(String username,String password,String email,String yanzhengma){


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
