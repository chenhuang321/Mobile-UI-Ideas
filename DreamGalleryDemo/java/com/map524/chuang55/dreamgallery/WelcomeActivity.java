package com.map524.chuang55.dreamgallery;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.map524.chuang55.util.EmailLoader;

public class WelcomeActivity extends Activity {

    Button loginBtn,signUpBtn,homeBtn;
    EditText customInputEmail,customInputPsw;
    WebView mVideoFirebaseAuth;
    final int maxAttemptNum = 5;
    // Get the number of max limited time of password attempt input
    TextView countDownView;
    int counter = maxAttemptNum; // Get integer num for counting down
    final String TAG = getClass().toString();
    public static final String email_key = "Email";
    public static final String domain_key = "Domain";
    public static final String password_key = "Password";
    public static final String mMyPreference = "MyPreference";
    SharedPreferences sharedpreferences;
    FirebaseAuth mAuth;
    boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mAuth = FirebaseAuth.getInstance();

        // Buttons
        loginBtn          = (Button) findViewById(R.id.button);
        signUpBtn         = (Button) findViewById(R.id.button2);
        homeBtn           = (Button) findViewById(R.id.home_page);

        // Text views
        countDownView     = (TextView) findViewById(R.id.textView3);
        countDownView.setVisibility(View.GONE);

        customInputEmail  = (EditText) findViewById(R.id.editText);
        customInputPsw    = (EditText) findViewById(R.id.editText2);

        mVideoFirebaseAuth = (WebView) findViewById(R.id.video_firebase_auth);

        homeBtn.setVisibility(View.GONE);
        mVideoFirebaseAuth.setVisibility(View.GONE);

        sharedpreferences = getSharedPreferences(mMyPreference, Context.MODE_PRIVATE);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail, mPassword;
                // Retrieves user inputs
                mEmail = customInputEmail.getText().toString();
                mPassword = customInputPsw.getText().toString();

                // trims the input
                mEmail = mEmail.trim();
                mPassword = mPassword.trim();

                if(!isValidInput(mEmail, mPassword))
                    return;

                mAuth.signInWithEmailAndPassword(mEmail, mPassword)
                    .addOnCompleteListener(WelcomeActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("TAG", "signInWithEmail:onComplete:" + task.isSuccessful());
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            boolean isSuccess = task.isSuccessful();
                            if (!isSuccess) {
                                onValidPassword(false);
                            } else {
                                Log.w("TAG", "signInWithEmail", task.getException());
                                onValidPassword(true);
                                loginBtn.setVisibility(View.GONE);
                                signUpBtn.setVisibility(View.GONE);
                                // Bug fixed: Customer edit the two values before click the button
                                customInputEmail.setVisibility(View.GONE);
                                customInputPsw  .setVisibility(View.GONE);
                                countDownView   .setVisibility(View.GONE);
                                counter = maxAttemptNum;
                                homeBtn.setVisibility(View.VISIBLE);
                                mVideoFirebaseAuth.setVisibility(View.VISIBLE);
                                playSrcVideo(mVideoFirebaseAuth);
                                isLogin = true;
                            }
                        } // onComplete end
                }); // mAuth.signInWithEmailAndPassword ends
            } // onClick of loginBtn ends
        }); // Login listener ends

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail, mPassword;
                // Retrieves user inputs
                mEmail = customInputEmail.getText().toString();
                mPassword = customInputPsw.getText().toString();

                // trims the input
                mEmail = mEmail.trim();
                mPassword = mPassword.trim();

                if(!isValidInput(mEmail, mPassword))
                    return;

                mAuth.createUserWithEmailAndPassword(mEmail, mPassword)
                    .addOnCompleteListener(WelcomeActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("TAG", "signInWithEmail:onComplete:" + task.isSuccessful());
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            boolean isSuccess = task.isSuccessful();
                            if (isSuccess) {
                                Log.w("TAG", "CreateUserWithEmail", task.getException());
                                Toast.makeText(WelcomeActivity.this, "Account created!",
                                        Toast.LENGTH_SHORT).show();
                                onValidPassword(true);
                                loginBtn.setVisibility(View.GONE);
                                signUpBtn.setVisibility(View.GONE);
                                customInputEmail.setVisibility(View.GONE);
                                customInputPsw  .setVisibility(View.GONE);
                                homeBtn.setVisibility(View.VISIBLE);
                            }
                        }
                    }); // mAuth.signInWithEmailAndPassword ends
            } // onClick of loginBtn ends
        }); // Sign up listener ends

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail, mPassword;
                // Retrieves user inputs
                mEmail = customInputEmail.getText().toString();
                mPassword = customInputPsw.getText().toString();
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(email_key, mEmail.substring(0, mEmail.indexOf("@")));
                editor.putString(domain_key, mEmail.substring(mEmail.indexOf("@")));
                editor.putString(password_key, mPassword);
                editor.apply(); // changed commit method to apply
                // these elements will show up again when onPause() invokes
                loginBtn        .setVisibility(View.VISIBLE);
                signUpBtn       .setVisibility(View.VISIBLE);
                customInputEmail.setVisibility(View.VISIBLE);
                customInputPsw  .setVisibility(View.VISIBLE);
                customInputEmail.setText("");
                customInputPsw  .setText("");
                homeBtn         .setVisibility(View.GONE);
                mVideoFirebaseAuth.setVisibility(View.GONE);
                isLogin = false;
                // Go to next activity
                Intent intent = new Intent(WelcomeActivity.this, GalleryActivity.class);
                startActivity(intent);
            }
        });
    } // onCreate ends

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        // You can get some help information on this main activity
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()) {
            case R.id.about:
                // A description toast about this app will show up
                Log.d(TAG, "You clicked the about option!");
                Toast.makeText(getApplicationContext(),getString(R.string.intro_app),
                        Toast.LENGTH_LONG).show();
                return true;
            case R.id.help:
                // Opens a Gmail page with the my seneca email address
                Log.d(TAG, "You clicked the help option!");
                // Done: Help option with Email sending prompt
                if(!isLogin) {
                    Toast.makeText(getApplicationContext(), R.string.err_not_login,
                            Toast.LENGTH_LONG).show();
                } else {
                    String address = sharedpreferences.getString(email_key, "") +
                            sharedpreferences.getString(domain_key, "");
                    // Explicit intent: Jump to another Activity
                    Intent intent = new Intent(WelcomeActivity.this, HelpActivity.class);
                    intent.putExtra("Host Email", address);
                    startActivity(intent);
                }
                return true;
            case R.id.close:
                onCloseDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onCloseDialog() {
        String yes = getResources().getString(R.string.alert_option_yes);
        String nah = getResources().getString(R.string.alert_option_nah);
        String question = getResources().getString(R.string.alert_value_close);
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.leave_alert)
                .setTitle(getString(R.string.alert_title_close))
                .setMessage(question)
                .setPositiveButton(yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton(nah, null)
                .show();
    }

    @Override
    public void onBackPressed() {
        onCloseDialog();
    }

    public void onValidPassword(boolean valid) {
        if (!valid) {
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.toast_error_invalid_password),
                    Toast.LENGTH_SHORT).show();
            countDownView.setVisibility(View.VISIBLE);
            countDownView.setTextColor(Color.RED);
            counter = counter - 1;
            countDownView.setText(String.valueOf(counter)); // Display the attempt time left
            if (counter == 0) {
                loginBtn.setEnabled(false);
                countDownView.setText(": - (");
                loginBtn.setText(getString(R.string.login_issue_button_details));
            }
        } else {
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.toast_welcome),
                    Toast.LENGTH_SHORT).show();
        }
    } // onValidPassword ends

    public boolean isValidInput(String email, String password) {
        Log.d("Email: ", email);
        EmailLoader emailLoader = new EmailLoader(email);
        if(email.isEmpty()) {
            Toast.makeText(WelcomeActivity.this, R.string.err_null_email,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password.isEmpty()) {
            Toast.makeText(WelcomeActivity.this, R.string.err_null_pass,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!emailLoader.isValid()) {
            Toast.makeText(WelcomeActivity.this, R.string.err_not_email,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    } // function isValidInput ends

    @SuppressLint("SetJavaScriptEnabled")
    public void playSrcVideo(WebView webView) {
        webView.setNetworkAvailable(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://firebase.google.com/docs/auth/");
    }
} // Activity WelcomeActivity ends
