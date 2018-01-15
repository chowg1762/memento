package com.example.wongeun.memento.utils;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioTrack;
import android.media.MediaCodec;
import android.media.MediaCodecList;
import android.media.MediaCrypto;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaRecorder;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Surface;

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

/**
 * Created by wongeun on 1/3/18.
 */

/**
 * Etract audio from video
 */

public class Converter {

    private static FFmpeg ffmpeg;

    Converter(){

    }

    public static void initFFMpeg(Context context){
        ffmpeg = FFmpeg.getInstance(context);
    }

    public static void loadFFMpegBinary(){
        try {

            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override
                public void onFailure() {
                    //showUnsupportedExceptionDialog();
                    Log.d("loadFFMpegBinary", "load fail");
                }
            });
        } catch (FFmpegNotSupportedException e) {
            //showUnsupportedExceptionDialog();
        }
    }

    public static void execFFmpegBinary(final String[] command) {
        try {

            ffmpeg.execute(command, new ExecuteBinaryResponseHandler() {
                @Override
                public void onFailure(String s) {
                    Log.d("execFFmpegBinary", "fail");
                }

                @Override
                public void onSuccess(String s) {
                    Log.d("execFFmpegBinary", "success : "+s);
                }

                @Override
                public void onProgress(String s) {
                    Log.d("execFFmpegBinary", "progress :" + s);
                }

                @Override
                public void onStart() {
                    Log.d("execFFmpegBinary", "start");
                }

                @Override
                public void onFinish() {
                    Log.d("execFFmpegBinary", "finish ");
                }
            });

        } catch (FFmpegCommandAlreadyRunningException e) {
            // do nothing for now
            Log.d("execFFmpegBinary", "Exception");
        }
    }

}
