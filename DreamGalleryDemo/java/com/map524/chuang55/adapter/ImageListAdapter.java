package com.map524.chuang55.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.map524.chuang55.dreamgallery.R;
import com.map524.chuang55.image.ImageRowItem;

import java.util.List;

// Black Board Documents: MAP524 ListView Example

public class ImageListAdapter extends ArrayAdapter<ImageRowItem> {

    private Context context;

    public ImageListAdapter(Context context, int resourceId,
                            List<ImageRowItem> items ) {
        super(context, resourceId, items);
        this.context = context;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView txtDesc;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        ImageRowItem rowItem = getItem(position); // retrieve from the List (data set)

        LayoutInflater mInflater =
                (LayoutInflater) context.getSystemService(
                        Activity.LAYOUT_INFLATER_SERVICE );

        if (convertView == null) { // the old/recycled view does NOT exist

            convertView = mInflater.inflate(R.layout.image_listview, null);
            holder = new ViewHolder();

            holder.txtDesc = (TextView) convertView.findViewById( R.id.descr );
            holder.txtTitle = (TextView) convertView.findViewById( R.id.title );
            holder.imageView = (ImageView) convertView.findViewById (R.id.icon );
            convertView.setTag(holder); // associate the view holder with the new View
        }
        else
            holder = (ViewHolder) convertView.getTag(); // retrieve the view holder of
        // the old/recycled view

        // MAP data to the inner views of the ListView item
        //  - update the data objects referenced by the view holder
        holder.txtDesc.setText( rowItem.getDesc() );
        holder.txtTitle.setText( rowItem.getTitle() );
        holder.imageView.setImageResource(rowItem.getImgId());

        return convertView;
    }

}

