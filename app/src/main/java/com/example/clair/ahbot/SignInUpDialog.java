package com.example.clair.ahbot;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;


public class SignInUpDialog extends Dialog{
    public Activity activity;
    public Dialog dialog;
    RelativeLayout RlLoginDialog;
    TextView TvLoginTitle,TvForgotPw,TvNoAcc,TvSignInUp,TvSignInUpWith;
    EditText EtCfmPw,EtPw,EtEmailPhone,EtUsername;
    Button BtnGoogle,BtnLoginLogout,BtnFb;
    boolean SignInView=true;
    //region firebase auth
    private FirebaseAuth mAuth;
    AuthCredential credentialGoogle;
    AuthCredential credentialEmail;
    AuthCredential credentialFb;
    RelativeLayout activity_main;
    private GoogleSignInClient mGoogleSignInClient;
    //Snackbar snackbar;
    String EmailNum,Email,Num,Password,CfmPw;

    String reNum="/^[689]\\d{7}$/";
    //endregion
    MainActivity m;

    public SignInUpDialog(Activity a){
        super(a);
        this.activity=a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m=new MainActivity();
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
        activity_main=findViewById(R.id.rlMainActivity);
        TvSignInUp.setOnClickListener(mListener);
        TvForgotPw.setOnClickListener(mListener);
        BtnFb.setOnClickListener(mListener);
        BtnGoogle.setOnClickListener(mListener);
        BtnLoginLogout.setOnClickListener(mListener);



        mAuth=FirebaseAuth.getInstance();

        //region Instantiate fb login button
//        BtnFb.setReadPermissions("email", "public_profile");
//        BtnFb.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Log.d(TAG, "facebook:onSuccess:" + loginResult);
//                handleFacebookAccessToken(loginResult.getAccessToken());
//            }
//
//            @Override
//            public void onCancel() {
//                Log.d(TAG, "facebook:onCancel");
//                // ...
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                Log.d(TAG, "facebook:onError", error);
//                // ...
//            }
//        });

        //endregion

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
                        EmailNum=EtEmailPhone.getText().toString();
                        Password=EtPw.getText().toString();
                        //Todo: Phone number authentication
                        if(EmailNum.matches(reNum)){
                            Num=EmailNum;
                            //Snackbar.make(activity_main,"Phone number authentication is currently unavailable.",//Snackbar.LENGTH_SHORT);
                        }
                        else{
                            Email=EmailNum;
                            //credentialEmail= EmailAuthProvider.getCredential(EtEmailPhone.getText().toString(),EtPw.getText().toString());
                            loginEmail(mAuth,Email, Password);
                        }
                        break;
                    case R.id.btn_SignInFb:
                        //Todo: Sign in with fb
//                        credentialFb = FacebookAuthProvider.getCredential(token.getToken());
                        break;
                    case R.id.btn_SignInGoogle:
                        //Todo: Sign in with google
//                        credentialGoogle = GoogleAuthProvider.getCredential(googleIdToken, null);
//
//                        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                                .requestIdToken(getContext().getResources().getString(R.string.GoogleWebClientID))
//                                .requestEmail()
//                                .build();
//
//                        mGoogleSignInClient= GoogleSignIn.getClient(activity,gso);
//
//                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(activity.getIntent());
//                        try {
//                            // Google Sign In was successful, authenticate with Firebase
//                            GoogleSignInAccount account = task.getResult(ApiException.class);
//                            firebaseAuthWithGoogle(account);
//                        } catch (ApiException e) {
//                            // Google Sign In failed, update UI appropriately
//                            Log.w(TAG, "Google sign in failed", e);
//                        }
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
                        EmailNum=EtEmailPhone.getText().toString();
                        Password=EtPw.getText().toString();
                        CfmPw=EtCfmPw.getText().toString();
                        //Phone Sign Up
                        if(EmailNum.matches(reNum)){
                            Num=EmailNum;
                        }
                        //Email Sign Up
                        else {
                            Email=EmailNum;
                            if (Password.equals(CfmPw))
                                signUpUser(EtUsername.getText().toString(), Email, Password);
                            else {

                               Snackbar.make(dialog.findViewById(R.id.rlMainActivity), "Password does not match", Snackbar.LENGTH_SHORT).show();
                                //snackbar.show();
                            }
                        }
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
            }
        }
    };

    public void loginEmail(FirebaseAuth fFirebaseAuth,String email, final String password){
        fFirebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof FirebaseAuthException) {
                            ((FirebaseAuthException) e).getErrorCode();
                            Log.d("error",e.getMessage());
                            //Snackbar.make(activity_main,e.getMessage(),Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
        fFirebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            if (password.length() < 6) {
                                //Snackbar.make(activity_main,"Password must be more than 6 characters",Snackbar.LENGTH_SHORT).show();
                                //Snackbar.show();
                            } else {
                                //Snackbar.make(activity_main,"Invalid Email or Password",Snackbar.LENGTH_SHORT).show();
                                //Snackbar.show();
                            }
                        } else {
                            //Snackbar.make(activity_main,"Sign in Successfully",Snackbar.LENGTH_SHORT).show();
                            //Snackbar.show();
                            //dismiss();
                        }
                    }
                });
    }


    private void signUpUser(final String username, String email, String password){
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Snackbar.make(activity.findViewById(R.id.rlMainActivity),"Error"+task.getException(),Snackbar.LENGTH_SHORT).show();
                        }
                        else{
                            //todo: put username in firebase
                            Snackbar.make(activity_main,"Sign Up Successfully, Welcome "+username,Snackbar.LENGTH_SHORT).show();
                            dismiss();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }
}





