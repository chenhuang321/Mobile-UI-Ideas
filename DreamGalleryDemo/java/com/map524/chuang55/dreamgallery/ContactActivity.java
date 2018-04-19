package com.map524.chuang55.dreamgallery;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.map524.chuang55.adapter.EmailContactListAdapter;
import com.map524.chuang55.util.EmailContact;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chen Huang on Oct 27, 2016
 * Implementation: A listview page displays contact and associated to Email app
 */

public class ContactActivity extends Activity {

    String hostEmail;
    public static final String TAG = "ContactActivity";
    Context mContext;
    List<String> contactLines;
    List<EmailContact> contactList;
    EmailContactListAdapter mListAdapter;
    ListView contactListView;
    String srcFilename;
    final int max_line = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        contactListView = (ListView) findViewById(R.id.contact_list);
        // Set context
        mContext = getApplicationContext();
        // Set sender tag for sending an email
        hostEmail = getIntent().getStringExtra(WelcomeActivity.email_key);
        if(hostEmail == null) {
            throw new IllegalArgumentException("Email data is required!");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        srcFilename = hostEmail + ".txt";
        Log.d(TAG, "onStart invoked!");
        System.out.println("Waiting for contact list to come in...");
        // Display contact information in the ListView layout element
        InputStream is = null;
        try {
            is = getApplicationContext().openFileInput(srcFilename);
        } catch (FileNotFoundException e) {
            System.err.println(e.getClass().toString() + ": No file found!");
        }
        assert is != null;
        InputStreamReader ISR = new InputStreamReader(is);
        contactLines = new ArrayList<>(max_line);
        contactLines = readLines(ISR);
        contactList = new ArrayList<>(max_line);
        // List converter
        convertContactFromString(contactLines, contactList);
        Log.d(TAG, "convertContactFromString invoke: true");
        mListAdapter = new EmailContactListAdapter(mContext, R.layout.contact_listview,
                contactList);
        Log.d(TAG, "mListAdapter initializing: finish!");
        contactListView.setAdapter(mListAdapter);
        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View clickView,
                                    final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ContactActivity.this);
                builder.setTitle("Pick an action")
                        .setItems(R.array.contact_action_array, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        Intent email = new Intent(Intent.ACTION_SEND);
                                        EmailContact emailContact = contactList.get(position);
                                        email.putExtra(Intent.EXTRA_EMAIL, new String[]{
                                                emailContact.getEmail()
                                        });
                                        email.setType("message/rfc822");
                                        startActivity(Intent.createChooser(email, getString(R.string.email_select_app)));
                                        break;
                                    case 1:
                                        //deleteContactFromList(position, contactList);
                                        contactList.remove(position);
                                        mListAdapter.notifyDataSetChanged();
                                        Toast.makeText(getApplicationContext(), "Deleted!",
                                                Toast.LENGTH_SHORT).show();
                                        addContactToLocal(contactList, srcFilename);
                                        break;
                                    default: break;
                                }
                            }
                        }).show();
            }
        });
        closeInputStream(is);
    }

    public void addContact(EditText firstName, EditText lastName, EditText jobTitle,
                           EditText email) {
        EmailContact emailContact = new EmailContact(
                firstName.getText().toString(),
                lastName.getText().toString(),
                jobTitle.getText().toString(),
                email.getText().toString()
        ); // Constructor ends
        if(emailContact.isValid()) {
            System.out.println("Email contact was added!");
            contactList.add(emailContact);
            mListAdapter.notifyDataSetChanged();
            addContactToLocal(contactList, srcFilename);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contact_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final ViewGroup null_parent = null;
        switch (item.getItemId()) {
            case R.id.add_contact:
                // Dialog page to input information
                LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                View promptView = layoutInflater.inflate(R.layout.prompt_contact, null_parent);
                AlertDialog.Builder promptBuilder = new AlertDialog.Builder(
                        this);
                promptBuilder.setView(promptView);
                System.out.println("Add view success!");
                final EditText name1 = (EditText) promptView.findViewById(R.id.edit_name1);
                final EditText name2 = (EditText) promptView.findViewById(R.id.edit_name2);
                final EditText title = (EditText) promptView.findViewById(R.id.edit_title);
                final EditText email = (EditText) promptView.findViewById(R.id.edit_email);
                // set dialog message
                promptBuilder
                    .setCancelable(false)
                    .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // get user input and set it to result
                            System.out.println("Input Get!");
                            boolean isEmptyName1 = isEmptyInput(name1);
                            boolean isEmptyEmail = isEmptyInput(email);
                            System.out.println(isEmptyName1 + " " + isEmptyEmail);
                            if(!isEmptyEmail && !isEmptyName1) {
                                System.out.println("addContact invoked!");
                                addContact(name1, name2, title, email);
                            }
                        }
                    }) // Positive button ends
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            dialog.cancel();
                        }
                    } // Negative button ends
                    );
                System.out.println("Build view success!");
                // create alert dialog
                AlertDialog alertDialog = promptBuilder.create();
                System.out.println("alertDialog created!");
                alertDialog.show();
                System.out.println("alertDialog show!");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean isEmptyInput(EditText editText) {
        return editText.getText().toString().trim().isEmpty();
    }

    public List<String> readLines(InputStreamReader in) {
        BufferedReader br = new BufferedReader(in);
        // you should estimate buffer size
        List<String> lines = new ArrayList<>();
        try {
            for (int i = 0; i < max_line; ++i)
                lines.add( br.readLine() );
        }
        catch (Exception e) { e.printStackTrace(); }
        return lines;
    }

    public void convertContactFromString(List<String> lines, List<EmailContact> emailContacts) {
        if(lines.size() <= 0) return;
        if(emailContacts.size() != 0)
            emailContacts.clear();
        for(int i = 0; i < lines.size(); i++) {
            if(lines.get(i) == null)
                return;
            String tmp = lines.get(i).substring(lines.get(i).indexOf(";") + 1);
            String name = lines.get(i).substring(0, lines.get(i).indexOf(";"));
            String title = tmp.substring(0, tmp.indexOf(";"));
            String email = tmp.substring(tmp.indexOf(";") + 1);
            EmailContact tmpContact = new EmailContact();
            tmpContact.setName(name);
            tmpContact.setEmail(email);
            tmpContact.setJobTitle(title);
            emailContacts.add(tmpContact);
        }
    }

    public void closeInputStream(InputStream is) {
        try { is.close(); }
        catch ( IOException e ) { e.printStackTrace(); }
    }

    public void addContactToLocal(List<EmailContact> contacts, final String filename) {
        writeToFile(contacts, filename);
    }

    private void writeToFile(List<EmailContact> dataList, final String filename) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    getApplicationContext().openFileOutput
                            ( filename, Context.MODE_PRIVATE )
            );
            for(EmailContact data : dataList) {
                outputStreamWriter.write(data.toString());
                outputStreamWriter.write("\n");
            }
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
