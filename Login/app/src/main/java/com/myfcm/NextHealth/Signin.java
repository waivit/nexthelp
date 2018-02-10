package com.myfcm.NextHealth;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.facebook.FacebookSdk;

public class Signin extends AppCompatActivity {
    private FirebaseAuth mAuth;
    SignInButton btnSignIn;
    public Button login_action,bt_signout;
    public EditText txt_email, txt_password;
    //public ProgressBar progressBar;
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        int secondsDelayed = 1;
        new Handler().postDelayed( new Runnable() {
            public void run() {
                //
                setContentView(R.layout.signin);
                //startActivity(new Intent(Splash.this, ActivityB.class));
                //finish();
            }
        }, secondsDelayed * 3500);







        Log.d( "Debug_log", "start" );
        login_action = (Button)findViewById(R.id.emaillogin);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            Toast.makeText(getApplicationContext(), "OK, you already logged in!", Toast.LENGTH_SHORT).show();
            //Intent for check Session
            Intent intent = new Intent(Signin.this, Landing.class);
            Log.d( "Debug_log", "on Create (check)" );
            startActivity(intent);
            finish();
        }

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        Log.d("Tag", "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        //showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("tag", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            //Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }

                });
    }

    public void emaillogin(View view){
        Log.d( "Debug_log","Pressed" );
        txt_email =(EditText)findViewById( R.id.email );
        String email = txt_email.getText().toString();
        txt_password =(EditText)findViewById( R.id.password );
        String password = txt_password.getText().toString();
        Log.d( "Debug_log","get data" );

        if (TextUtils.isEmpty(email)) {
            Log.d( "Debug_log","email-empty" );
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }
        ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        //authenticate user

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Debug_log", "signInWithEmail:success//////////////////////////");
                            Toast.makeText(Signin.this, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);

                            Intent intent = new Intent(Signin.this, Landing.class);
                            Log.d( "Debug_log", "on Create (check)" );
                            startActivity(intent);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("Debug_log", "signInWithEmail:failure", task.getException());
                            Toast.makeText(Signin.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                            progressBar.setVisibility(View.INVISIBLE);
                            updateUI(null);
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        //Check if user is signed in (non-null) and update UI accordingly.

        Log.d( "Debug_log", "on start" );
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        Button bt_signout = (Button)findViewById( R.id.bt_signout );

        if (mAuth.getCurrentUser() != null)
        {
            Intent intent = new Intent(Signin.this, Landing.class);
            Log.d( "Debug_log", "on start(check)" );
            startActivity(intent);
            finish();
        }
        Toast.makeText(Signin.this, "Authentication failed.",
                Toast.LENGTH_SHORT).show();
    }

    private void updateUI(FirebaseUser currentUser) {
        //UpdateUI
        Log.d( "Debug_log", "//UpdateUI///////////////////////////////" );
        }
    }