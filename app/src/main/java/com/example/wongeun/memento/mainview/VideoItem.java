package com.example.wongeun.memento.mainview;

import android.graphics.Bitmap;

/**
 * Created by wongeun on 12/18/17.
 */

public class VideoItem {

    private Bitmap thumbnailImage;
    private String title;
    private boolean playing = false;

    public VideoItem(Bitmap thumbnailImage, String title){
        this.thumbnailImage = thumbnailImage;
        this.title = title;
    }

    public void setThumbnailImage(Bitmap thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getThumbnailImage() {
        return thumbnailImage;
    }

    public String getTitle() {
        return title;
    }

    public boolean isPlaying(){
        return playing;
    }

    public void setPlayed(boolean playing){
        this.playing = playing;
    }
}
