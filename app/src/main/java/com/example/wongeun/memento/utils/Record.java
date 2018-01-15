package com.example.wongeun.memento.utils;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.IOException;

/**
 * Created by wongeun on 1/5/18.
 */

public class Record {

    MediaRecorder mRecorder;
    MediaPlayer mPlayer;

    public Record(MediaPlayer mPlayer, MediaRecorder mRecorder){
        this.mRecorder = mRecorder;
        this.mPlayer = mPlayer;
    }

    public Record(){

    }

    public void onRecord(boolean start, String fileName){
        if (start) {
            startRecording(fileName);
        } else {
            stopRecording();
        }
    }

    public void onPlay(boolean start, String fileName) {
        if (start) {
            startPlaying(fileName);
        } else {
            stopPlaying();
        }
    }


    private void startPlaying(String mFileName){
        mPlayer = new MediaPlayer();

        try {
            Log.d("startPlaying", "playing");
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e("logE", "prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    private void startRecording(String mFileName) {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mRecorder.setOutputFile(mFileName.substring(0,mFileName.length()-1)+"3");

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e("startRecording", "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }


}
