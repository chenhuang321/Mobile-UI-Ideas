package com.map524.chuang55.dreamgallery;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * @author Chen Huang
 * @version 2016.11.10
 */

public class GalleryMainActivity extends Activity {

    SharedPreferences s;
    public static String login_name = "guest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_gallery_main);
        s = getSharedPreferences(WelcomeActivity.mMyPreference, Context.MODE_PRIVATE);
        login_name = s.getString(WelcomeActivity.email_key, "guest");
    }
}
