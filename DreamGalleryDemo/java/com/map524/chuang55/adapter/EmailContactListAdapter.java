package com.map524.chuang55.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.map524.chuang55.dreamgallery.R;
import com.map524.chuang55.util.EmailContact;

import java.util.List;

/**
 * Created by Chen Huang on 22:46, Oct 27, 2016
 */

public class EmailContactListAdapter extends ArrayAdapter<EmailContact> {

    private Context this_context;
    private class ContactLayoutHolder {
        TextView name;
        TextView title;
        TextView email;
    }

    public EmailContactListAdapter( Context context, int srcId ,List<EmailContact> items ) {
        super( context, srcId, items );
        this_context = context;
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ContactLayoutHolder holder; // Set an empty container of url information
        EmailContact emailContact = getItem(position);

        LayoutInflater mInflater =
                (LayoutInflater) this_context.getSystemService(
                        Activity.LAYOUT_INFLATER_SERVICE );

        //the view holder pattern: reuse existing views of ListView items
        if (convertView == null) { // the old/recycled view does NOT exist
            convertView = mInflater.inflate(R.layout.contact_listview, parent, false);
            holder = new ContactLayoutHolder();

            holder.name =  (TextView) convertView.findViewById( R.id.contact_full_name );
            holder.title = (TextView) convertView.findViewById( R.id.contact_job_title );
            holder.email = (TextView) convertView.findViewById (R.id.contact_email_address );
            convertView.setTag(holder); // associates the view holder with the new View
        }
        else
            holder = (ContactLayoutHolder) convertView.getTag(); // retrieves the view holder of
        // MAP data to the inner views of the ListView item
        //  - update the data objects referenced by the view holder
        assert emailContact != null; // Email should not be null
        holder.name .setText( emailContact.getFullName() );
        holder.title.setText( emailContact.getJobTitle() );
        holder.email.setText( emailContact.getEmail() );
        return convertView;
    }
}
