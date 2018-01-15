package com.example.wongeun.memento.utils;

import android.os.Environment;
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
}
