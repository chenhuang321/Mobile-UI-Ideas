package com.map524.chuang55.util;

import android.content.Context;

import com.map524.chuang55.dreamgallery.GalleryMainActivity;
import com.map524.chuang55.image.ImageFileLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Chen Huang
 * @version 2016.11.10
 */

public class GalleryBoard {

    private List<ImageFileLoader> mImageFileLoaders;
    private String ImagePathName;
    private int    numOfImages;
    public static final int max_img = 1000;

    public GalleryBoard() {
        mImageFileLoaders = new ArrayList<>();
        ImagePathName     = GalleryMainActivity.login_name + "_img_path.txt";
        numOfImages       = 0;
    }

    public void readfromFile(Context context) throws IOException {
        InputStream inputStream = context.openFileInput(ImagePathName);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        mImageFileLoaders = new ArrayList<>(max_img);
        mImageFileLoaders = readLines(inputStreamReader);
        inputStream.close();
    }

    public void writeToFile(Context context) throws IOException {
        OutputStream outputStream = context.openFileOutput(ImagePathName,
                Context.MODE_PRIVATE);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        writeLines(outputStreamWriter);
        System.out.println("writeToFile invoked!");
        outputStream.close();
    }

    public List<ImageFileLoader> getImageFileLoadders() {
        return mImageFileLoaders;
    }

    public String getImagePathName() {
        return ImagePathName;
    }

    public int getNumOfImages() {
        return numOfImages;
    }

    public void addImageByMembers(String name, String date, String desc) {
        String tmpDate = date;
        ImageFileLoader img = new ImageFileLoader();
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        if(name.isEmpty())
            return;
        if(date.isEmpty())
            tmpDate = dateFormat.toString();
        img.setFileName(name);
        img.setUpdateDateTime(tmpDate);
        img.setDesc(desc.isEmpty()? "No Description" : desc);
    }

    @Override
    public String toString() {
        String out = "";
        if(numOfImages > 0) {
            for(ImageFileLoader img : mImageFileLoaders) {
                out += img.toString() + "\n";
            }
        }
        return out;
    }

    private List<ImageFileLoader> readLines(InputStreamReader in) {
        BufferedReader br = new BufferedReader(in);
        // you should estimate buffer size
        List<ImageFileLoader> images = new ArrayList<>(max_img);
        try {
            for (int i = 0; i < max_img; i++) {
                ImageFileLoader obj = new ImageFileLoader();
                if(!br.readLine().isEmpty())
                    obj.readLine(br.readLine());
            }
        }
        catch (Exception e) { e.printStackTrace(); }
        return images;
    }

    private void writeLines(OutputStreamWriter out) {
        try {
            out.write(toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
