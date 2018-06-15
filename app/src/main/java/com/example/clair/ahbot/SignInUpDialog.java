package com.example.clair.ahbot;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class SignInUpDialog extends Dialog{
    public Activity activity;
    public Dialog dialog;
    RelativeLayout RlLoginDialog;
    TextView TvLoginTitle,TvForgotPw,TvNoAcc,TvSignInUp,TvSignInUpWith;
    EditText EtCfmPw,EtPw,EtEmailPhone,EtUsername;
    Button BtnGoogle,BtnLoginLogout,BtnFb;
    boolean SignInView=true;

    public SignInUpDialog(Activity a){
        super(a);
        this.activity=a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_login);
        RlLoginDialog=findViewById(R.id.rlLoginDialog);
        TvLoginTitle=findViewById(R.id.tvLoginTitle);
        TvForgotPw=findViewById(R.id.tvForgotPassword);
        TvNoAcc=findViewById(R.id.tvNoAcc);
        TvSignInUp=findViewById(R.id.tvSignInUp);
        TvSignInUpWith=findViewById(R.id.tvSignInUpWith);
        EtEmailPhone=findViewById(R.id.etEmailPhone);
        EtUsername=findViewById(R.id.etUsername);
        EtPw=findViewById(R.id.etPassword);
        EtCfmPw=findViewById(R.id.etCfmPassword);
        BtnLoginLogout=findViewById(R.id.btnLoginSignUp);
        BtnFb=findViewById(R.id.btn_SignInFb);
        BtnGoogle=findViewById(R.id.btn_SignInGoogle);
        TvSignInUp.setOnClickListener(mListener);
    }
    private View.OnClickListener mListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (SignInView) {
                switch (v.getId()) {
                    case R.id.tvForgotPassword:
                        //TODO: forget password logic
                        break;
                    case R.id.tvSignInUp:
                        SignInView = false;
                        TvLoginTitle.setText(R.string.SignUp);
                        EtUsername.setVisibility(View.VISIBLE);
                        EtCfmPw.setVisibility(View.VISIBLE);
                        TvForgotPw.setVisibility(View.GONE);
                        BtnLoginLogout.setText(R.string.SignUp);
                        TvSignInUpWith.setText(R.string.SignUpWith);
                        TvNoAcc.setText(R.string.AlreadyHaveAnAcc);
                        TvSignInUp.setText(R.string.SignIn);
                        break;
                    case R.id.btnLoginSignUp:
                        //Todo: Sign in code
                        break;
                    case R.id.btn_SignInFb:
                        //Todo: Sign in using fb code
                        break;
                    case R.id.btn_SignInGoogle:
                        //Todo: Sign in using google code
                        break;
                    default:
                        //Todo: Dunno What to do
                        break;

                }
            } else {
                switch (v.getId()) {
                    case R.id.tvSignInUp:
                        SignInView = true;
                        TvLoginTitle.setText(R.string.SignIn);
                        EtUsername.setVisibility(View.GONE);
                        EtCfmPw.setVisibility(View.GONE);
                        TvForgotPw.setVisibility(View.VISIBLE);
                        BtnLoginLogout.setText(R.string.SignIn);
                        TvSignInUpWith.setText(R.string.SignInWith);
                        TvNoAcc.setText(R.string.DontHaveAnAccount);
                        TvSignInUp.setText(R.string.SignUp);
                        break;
                    case R.id.btnLoginSignUp:
                        //Todo: Sign up code
                        break;
                    case R.id.btn_SignInFb:
                        //Todo: Sign up using fb code
                        break;
                    case R.id.btn_SignInGoogle:
                        //Todo: Sign up using google code
                        break;
                    default:
                        //Todo: Dunno What to do
                        break;

                }
//                dismiss();
            }
        }
    };
}





