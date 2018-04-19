package com.map524.chuang55.dreamgallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.map524.chuang55.adapter.ImageListAdapter;
import com.map524.chuang55.image.ImageRowItem;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;


public class GalleryActivity extends Activity {

    SharedPreferences mPreference;
    final String TAG = "GalleryActivity";
    final String mMyPreferenceTag = "MyPreference";
    Context currentContext;
    TextView login_status;
    ListView img_list;
    String email;
    List<ImageRowItem> imageGroup;
    int[] url_list;
    String[] titleArray;
    String[] descArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        currentContext = getApplicationContext();

        // Initialize list containers
        img_list = (ListView) findViewById(R.id.image_list);
        img_list.setVisibility(View.GONE);


        mPreference = getSharedPreferences(mMyPreferenceTag, Context.MODE_PRIVATE);
        email = mPreference.getString("Email", "");
        Log.d(TAG, email); // Debug for retrieved information

        login_status = (TextView) findViewById(R.id.layout_login_status);
        login_status.setText(MessageFormat.format("{0}{1}",
                getString(R.string.infor_loggin_status), email));

        final Button isReadyBtn = (Button) findViewById(R.id.ready_btn);
        isReadyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_status.setVisibility(View.GONE);
                isReadyBtn.setVisibility(View.GONE);
                homePageSetUp();
                startGalleryPage(true);
            }
        }); // isReadyBtn ends
    }

    public void homePageSetUp() {
        imageGroup = new ArrayList<>();
        // Initialize the menu with images and text
        loadHomePageMenuIcons();
        titleArray = getResources().getStringArray(R.array.IconTitle);
        descArray  = getResources().getStringArray(R.array.IconDesc);
        // Import data into the xml file
        for(int i = 0; i < url_list.length; i++) {
            // Load menu icons into the pocket
            imageGroup.add(new ImageRowItem(url_list[i], titleArray[i], descArray[i]));
        }
        ImageListAdapter imageListAdapter =
            new ImageListAdapter(
                currentContext, R.layout.image_listview, imageGroup
            );
        img_list.setAdapter(imageListAdapter);
        img_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View clickView,
                                    int position, long id) {
                /** ListView interface
                 0: App Guide
                 1: My Gallery
                 2: My Message
                 3: My Contact
                 4: My Journal
                 5: Log Out
                 */
                final String[] desc_list = getResources().getStringArray(R.array.IconDesc);
                if(position >= 0 && position <= 5)
                    Toast.makeText(getApplicationContext(), desc_list[position],
                            Toast.LENGTH_LONG).show();
                switch(position) {
                    case 0:
                        // TODO
                        break;
                    case 1:
                        Intent galleryEntryIntent = new Intent(GalleryActivity.this,
                                GalleryStartActivity.class);
                        startActivity(galleryEntryIntent);
                        Log.d(TAG, "New Activity invoked!");
                        break;
                    case 2:
                        // TODO
                        break;
                    case 3:
                        // Another activity will show up
                        Intent intentBundle = new Intent(GalleryActivity.this, ContactActivity.class);
                        intentBundle.putExtra(WelcomeActivity.email_key,
                                mPreference.getString(WelcomeActivity.email_key, ""));
                        startActivity(intentBundle);
                        break;
                    case 4:
                        // TODO
                        break;
                    case 5:
                        mPreference.edit().remove(WelcomeActivity.email_key).apply();
                        mPreference.edit().remove(WelcomeActivity.password_key).apply();
                        finish();
                        break;
                    default:
                        break;
                }

            }
        });
    }

    public void startGalleryPage(boolean is_ready) {
        if(is_ready) {
            // Display the gallery listview with items and information
            img_list.setVisibility(View.VISIBLE);
        }
    }

    public void loadHomePageMenuIcons() {
        url_list = new int[] {
                R.drawable.public_img, R.drawable.personal_img,
                R.drawable.profile,    R.drawable.my_contact,
                R.drawable.journal,    R.drawable.logout
        };
    }
}
