package com.example.wongeun.memento.editor.presenter;

import com.example.wongeun.memento.editor.model.VideoItem;
import com.example.wongeun.memento.editor.model.VideoItemAdapter;

import java.util.ArrayList;

/**
 * Created by wongeun on 1/15/18.
 */

public interface VideoItemManagePresneter {
    interface VideoItemManageListener{
         void videoItemMangeListener();
    }

    void addVideoItem(VideoItem videoItem);

    void removeVideoItem(VideoItem videoItem);

    ArrayList<VideoItem> getVideoItemsList();

    VideoItemAdapter getVideoItemAdapter();

}
