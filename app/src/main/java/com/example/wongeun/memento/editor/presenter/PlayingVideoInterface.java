package com.example.wongeun.memento.editor.presenter;

import android.net.Uri;

/**
 * Created by wongeun on 1/16/18.
 */

public interface PlayingVideoInterface {
    void setMediaSource(Uri videoURI);
    void playVideo();
    void pauseVideo();

}
