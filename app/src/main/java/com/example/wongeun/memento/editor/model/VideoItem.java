package com.example.wongeun.memento.editor.model;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by wongeun on 1/15/18.
 * model
 */


public class VideoItem {
    private Bitmap videoThumbnail;
    private String videoPath;
    private Uri selectedImageUri;

    public VideoItem(Bitmap videoThumbnail, Uri selectedImageUri){
        this.videoThumbnail = videoThumbnail;
        this.selectedImageUri = selectedImageUri;
    }

    public VideoItem(Bitmap videoThumbnail, String videoPath){
        this.videoThumbnail = videoThumbnail;
        this.videoPath = videoPath;
    }

    public void setVideoThumbnail(Bitmap videoThumbnail){
        this.videoThumbnail = videoThumbnail;
    }

    public Bitmap getVideoThumbnail(){
        return this.videoThumbnail;
    }

    public void setVideoPath(String videoPath){
        this.videoPath = videoPath;
    }

    public String getVideoPath(){
        return videoPath;
    }

    public Uri getSelectedImageUri() {
        return selectedImageUri;
    }

    public void setSelectedImageUri(Uri selectedImageUri) {
        this.selectedImageUri = selectedImageUri;
    }
}
