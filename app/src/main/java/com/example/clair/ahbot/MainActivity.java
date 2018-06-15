package com.example.clair.ahbot;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    //region firebase database stuff
//    private FirebaseDatabase fFirebaseDatabase;
//    private DatabaseReference fDatabaseReference;
//    private ChildEventListener fChildEventListener;
//    Firestore f;
    //endregion

    //region firebase auth stuff
    private FirebaseAuth fFirebaseAuth;
    private FirebaseAuth.AuthStateListener fAuthStateListener;
    //endregion

    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        //region auth
        fFirebaseAuth= FirebaseAuth.getInstance();
        fAuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user==null){
                    SignInUpDialog signInUpDialog=new SignInUpDialog(MainActivity.this);
                    signInUpDialog.setCancelable(false);

                    signInUpDialog.show();
                }
            }
        };
        //endregion
    }
    @Override
    protected void onStart() {
        super.onStart();
//        MainActivity r=this;
//        f=new Firestore(r);
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(fAuthStateListener!=null){
            fFirebaseAuth.removeAuthStateListener(fAuthStateListener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
       fFirebaseAuth.addAuthStateListener(fAuthStateListener);
    }
//region menu
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_schedule:
                //TODO: create schedule page
                return true;
            case R.id.menu_settings:
                //TODO: create settings page
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //endregion

}
