package com.example.wongeun.memento.utils;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

/**
 * Created by wongeun on 1/4/18.
 */

public class GetPermission {

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;


    private static boolean permissionToRecordAccepted = false;

    public static boolean getPermision(int requestCode, @NonNull int[] grantResults){
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;


        }
        return permissionToRecordAccepted;
    }
}
