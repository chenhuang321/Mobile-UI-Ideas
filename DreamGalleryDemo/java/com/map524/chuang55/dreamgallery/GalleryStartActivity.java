package com.map524.chuang55.dreamgallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.map524.chuang55.util.VisitStatus;

/**
 * Created by Chen Huang on 20:58, Nov 8, 2016
 */

public class GalleryStartActivity extends Activity {

    final private String TAG = "GalleryStartActivity";
    private VisitStatus       mVisitStatus;
    private Context           appContext;
    private TextView          last_time, last_ip, title1, title2;
    private Button            mBtnContinue;
    private SharedPreferences status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_profile);
        Log.d(TAG, "onCreate invoked!");
        mVisitStatus = new VisitStatus();
        appContext   = getApplicationContext();
        status       = getSharedPreferences(VisitStatus.IP_KEY, MODE_PRIVATE);
        status       = getSharedPreferences(VisitStatus.TIME_KEY, MODE_PRIVATE);
        title1       = (TextView) findViewById(R.id.last_time);
        title2       = (TextView) findViewById(R.id.last_ipv4);
        last_time    = (TextView) findViewById(R.id.time_output);
        last_ip      = (TextView) findViewById(R.id.addr_output);
        mBtnContinue = (Button) findViewById(R.id.btn_submit_infor);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart invoked!");
        setVisibilityAll(View.VISIBLE);
        onTextViewBlankFilled();
        mBtnContinue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setVisibilityAll(View.GONE);
                refreshStatus();
                Intent i = new Intent(GalleryStartActivity.this, GalleryMainActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onPause() {
        Editor editor = status.edit();
        editor.putString(VisitStatus.TIME_KEY, mVisitStatus.getTime());
        editor.putString(VisitStatus.IP_KEY, mVisitStatus.getIpAddress());
        editor.apply();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setVisibilityAll(View.VISIBLE);
        Toast.makeText(appContext, "Welcome back!", Toast.LENGTH_LONG).show();
        mVisitStatus.setIpAddress(status.getString(VisitStatus.IP_KEY, ""));
        mVisitStatus.setTime(status.getString(VisitStatus.TIME_KEY, ""));
        onTextViewBlankFilled();
    }

    public void refreshStatus() {
        mVisitStatus.refreshTime();
        mVisitStatus.refreshIPAddress();
        onTextViewBlankFilled();
    }

    public void onTextViewBlankFilled() {
        last_time.setText(mVisitStatus.getTime());
        last_ip  .setText(mVisitStatus.getIpAddress());
    }

    public void setVisibilityAll(int option) {
        last_time   .setVisibility(option);
        last_ip     .setVisibility(option);
        mBtnContinue.setVisibility(option);
        title1      .setVisibility(option);
        title2      .setVisibility(option);
    }
}
