package com.android.ict.seneca.androidpocketguide;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class SecondActivity extends Activity {

    private String[] vocabularies;
    String vocabulary_ck;
    final String class_name = "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        vocabularies = getResources().getStringArray(R.array.vocabulary_list);

        // manage a list of suggestions (an array of strings) for AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_dropdown_item_1line, vocabularies);

        AutoCompleteTextView textView =
                (AutoCompleteTextView) findViewById(R.id.vocabulary_list);

        textView.setThreshold(2); // minimum number of characters to be typed
        // case-insensitive

        textView.setAdapter(adapter); // connect the list of suggestions to the View object

        Button bt = (Button)findViewById(R.id.bt1);
        bt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent data = new Intent();
                EditText vocab_ck =
                        (EditText) findViewById(R.id.vocabulary_list);
                data.setData( Uri.parse(       // create a Uri object
                        // - parameter: an RFC 2396-compliant, encoded URI
                        vocab_ck.getText().toString()));
                setResult(RESULT_OK, data);
                vocabulary_ck = data.getData().toString();
                String def = getDefinition(vocabulary_ck);

                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;

                TextView textView =(TextView) findViewById(R.id.defintion);
                textView.setText(" Vocabulary: " + vocabulary_ck + "\n" + " Meaning: " + def);

                Toast toast = Toast.makeText(context, "You have clicked the button!", duration);
                toast.show();
            }
        });

    }


    private String getDefinition(String arg) {
        String result;
        switch (arg) {
            case "activity":
                result = "An Activity is an application component that provides a screen with \n" +
                        " which users can interact in order to do something."; break;
            case "AVD":
                result = "An Android Virtual Device (AVD) definition lets you define the characteristics " +
                        "of an Android phone, tablet, Android Wear, or Android TV device that you want to simulate " +
                        "in the Android Emulator."; break;
            case "ART":
                result = "The Android runtime (ART) is the default runtime for devices running Android 5.0 (API level 21) " +
                        "and higher."; break;
            case "Dalvik":
                result = "Dalvik is a discontinued process virtual machine (VM) in Google's Android operating system that " +
                        "executes applications written for Android. "; break;
            case "intent":
                result = "An intent is an abstract description of an operation to be performed. It can be used with startActivity " +
                        "to launch an Activity"; break;
            case "intent filter":
                result = "An intent filter declares the capabilities of its parent component — what an activity or service can do and " +
                        "what types of broadcasts a receiver can handle."; break;
            case "explicit intent":
                result = "Android Explicit intent specifies the component to be invoked from activity."; break;
            case "implicit intent":
                result = "When we work with implicit intents, we generally specify the action that we want to be performed and optionally " +
                        "some data required for that action. "; break;
            case "Logcat":
                result = "Logcat is a command-line tool that dumps a log of system messages, including stack traces when the device throws " +
                        "an error and messages that you have written from your app with the Log class."; break;
            case "bundle":
                result = "Bundle is most often used for passing data through various Activities. Provides putType() and getType() methods " +
                        "for storing and retrieving data from it."; break;
            case "Gradle":
                result = "In Android Studio, Gradle is a custom build tool used to build android packages (apk files) by managing dependencies " +
                        "and providing custom build logic. APK file (Android Application package) is a specially formatted zip file which contains."; break;
            case "Android Device Monitor":
                result = "Android Device Monitor is a standalone tool that provides a UI for several Android application debugging and analysis tools. " +
                        "Android Device Monitor doesn't require installation of an integrated development environment, such as Android Studio, and encapsulates the following tools:"; break;
            case "SDK manager":
                result = "The Android SDK Manager provides the SDK tools, platforms, and other components you need to develop your apps."; break;
            case "minSdkVersion":
                result = "An integer designating the minimum API Level required for the application to run."; break;
            default:
                result = "Can't find your message!";
                break;

        }
        return result;
    }
}
