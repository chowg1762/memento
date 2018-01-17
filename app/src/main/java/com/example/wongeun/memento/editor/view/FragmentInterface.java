package com.example.wongeun.memento.editor.view;

import com.example.wongeun.memento.editor.presenter.VideoItemManagePresenterImplement;

/**
 * Created by wongeun on 1/16/18.
 */

public interface FragmentInterface {

    void setVideoItemManagePresenterImp(VideoItemManagePresenterImplement videoItemManagePresenterImp);

    VideoItemManagePresenterImplement getVideoItemManagePresenterImp();
}
