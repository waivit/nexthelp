package com.myfcm.NextHealth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;

public class Landing extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing);
        Log.d("Debug_log", "new view");
        //EditText token2 = (EditText) findViewById(R.id.UID );
    }

    public void gettoken(View view)
    {
        EditText token = (EditText) findViewById(R.id.mytoken );
        token.setText( FirebaseInstanceId.getInstance().getToken() );
        //Toast toast = Toast.makeText(getApplicationContext(),"you have pressed get token button.",Toast.LENGTH_SHORT);
        //toast.show();
    }

    public void signout(View view) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        Intent intent = new Intent(Landing.this, Signin.class);
        startActivity(intent);
        finish();
    }
}
