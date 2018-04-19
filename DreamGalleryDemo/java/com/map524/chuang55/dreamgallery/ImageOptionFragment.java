package com.map524.chuang55.dreamgallery;


import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.map524.chuang55.util.GalleryBoard;

/**
 * @author Chen Huang
 * @version 2016.11.10
 */
public class ImageOptionFragment extends ListFragment {

    GalleryBoard mGalleryBoard;
    boolean mDualPane;
    int     mCurCheckedPosition;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckedPosition);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(getActivity(), "You done!", Toast.LENGTH_LONG).show();
        // TODO: Details manager
    }
}
