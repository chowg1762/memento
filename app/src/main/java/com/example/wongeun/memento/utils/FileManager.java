package com.example.wongeun.memento.utils;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;

/**
 * Created by wongeun on 1/6/18.
 */

public class FileManager {
    public static void makeMementoFolder(){
        File folder = new File(getHomeDir());
        boolean success = true;

        if (!folder.exists()) {
            success = folder.mkdirs();
        }

    }

    public static String getHomeDir(){
        return Environment.getExternalStorageDirectory() + File.separator + "Memento";
    }

    public static String getAbsoultePath(Uri uri, Activity activity){
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = activity.getContentResolver().query(uri, projection, null, null, null);

        if(cursor!=null){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else{
            return null;
        }
    }
}
