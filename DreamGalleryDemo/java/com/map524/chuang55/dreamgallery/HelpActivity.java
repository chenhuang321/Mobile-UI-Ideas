package com.map524.chuang55.dreamgallery;

/**
 * Created by Chen Huang on Oct 30, 2016
 * Option to send email to the author of the app
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HelpActivity extends Activity {

    final private String TAG = "HelpActivity";
    private String emailReceiver;
    String emailSender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        emailReceiver = getString(R.string.auth_email);
        Intent intent = getIntent();
        emailSender = intent.getStringExtra("Host Email");
        Log.d(TAG, "onCreate invoked!");

        final EditText msg_input = (EditText) findViewById(R.id.edit_msg);
        Button startBtn = (Button) findViewById(R.id.sendEmail);
        startBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendEmail(emailReceiver, msg_input.getText().toString());
            }
        });
    }
    protected void sendEmail(String receiver, String message) {
        Log.d(TAG, "sendEmail method invoked!");
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{ receiver });
        email.putExtra(Intent.EXTRA_SUBJECT, "Need help on my Dream Gallery app");
        email.putExtra(Intent.EXTRA_TEXT, message);
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, getString(R.string.email_select_app)));
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Toast.makeText(getApplicationContext(), "Thank you for using help email service!",
                Toast.LENGTH_LONG).show();
    }
}