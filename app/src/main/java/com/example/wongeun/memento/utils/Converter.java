package com.example.wongeun.memento.utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.media.AudioAttributes;
import android.media.AudioTrack;
import android.media.MediaCodec;
import android.media.MediaCodecList;
import android.media.MediaCrypto;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaRecorder;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Surface;

import com.example.wongeun.memento.mainview.WorkItem;
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

/**
 * Created by wongeun on 1/3/18.
 */

/**
 * Etract audio from video
 */

public class Converter {

    private static final String CMD_FFMPEG_START = "-version";
    private static final String CMD_HEAD = "-y -i ";
    private static final String CMD_STRICT = " -vn -c:a copy -f adts ";
    private static final String SPACE = " ";
    private static final String AUDIO_FORMAT = "aac";
    private static final String VIDEO_INTENT = "video/*";

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

    public static void convertVideoToAudio(WorkItem item){
        String cmd = CMD_HEAD + item.getTitle()+ CMD_STRICT;

        String[] fileDir =item.getTitle().split(File.separator);
        String fileName = fileDir[fileDir.length-1];
        String out_audio_file = FileManager.getHomeDir()+ File.separator+ fileName.substring(0, fileName.length()-3)+AUDIO_FORMAT;

        cmd = cmd+out_audio_file;

        String[] command = cmd.split(SPACE);

        Converter.execFFmpegBinary(CMD_FFMPEG_START.split(SPACE));
        Converter.execFFmpegBinary(command);

        File output = new File(out_audio_file);
        try {
            FileInputStream inputStream = new FileInputStream(output);

            int content;
            while((content = inputStream.read())!=-1){
                Log.d("check", content+"");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
