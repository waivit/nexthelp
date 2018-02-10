package com.myfcm.NextHealth;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt_gettoken = (Button) findViewById(R.id.bt_gettoken);
        TextView token_id = (TextView) findViewById(R.id.token_id);
        EditText token2 = (EditText) findViewById(R.id.Token2 );
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            String tmp = "you have 1 message";
            token_id.setText(bundle.getString("text"));
        }

    }

    public void bt_gettoken(View view)
    {
        TextView token_id = (TextView) findViewById(R.id.token_id);
        token_id.setText(FirebaseInstanceId.getInstance().getToken());
        EditText token2 = (EditText) findViewById(R.id.Token2 );
        token2.setText( FirebaseInstanceId.getInstance().getToken() );

        Log.d("token", FirebaseInstanceId.getInstance().getToken());
        //Toast toast = Toast.makeText(getApplicationContext(),"you have pressed get token button.",Toast.LENGTH_SHORT);
        //toast.show();
    }
}
