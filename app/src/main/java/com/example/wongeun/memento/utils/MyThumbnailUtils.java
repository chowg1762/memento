package com.example.wongeun.memento.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by wongeun on 1/16/18.
 */

public class MyThumbnailUtils {

    public static Bitmap getResizedThumbnail(@NonNull Bitmap bitmap, int maxResolution) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = width;
        int newHeight = height;
        float rate;

        if (maxResolution < width) {
            rate = maxResolution / (float) width;
            newHeight = (int) (height * rate);
            newWidth = maxResolution;
        }

        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
    }
}
