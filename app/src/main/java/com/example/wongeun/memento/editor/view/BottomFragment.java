package com.example.wongeun.memento.editor.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wongeun.memento.R;
import com.example.wongeun.memento.editor.presenter.VideoItemManagePresenterImplement;

/**
 * Created by wongeun on 1/15/18.
 */

public class BottomFragment extends Fragment implements FragmentInterface{

    private VideoItemManagePresenterImplement videoItemManagePresenterImp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bottom_fragment,
                container, false);

        return view;
    }

    public void setVideoItemManagePresenterImp(VideoItemManagePresenterImplement videoItemManagePresenterImp){
        this.videoItemManagePresenterImp = videoItemManagePresenterImp;
    }

    public VideoItemManagePresenterImplement getVideoItemManagePresenterImp(){
        return this.videoItemManagePresenterImp;
    }
}
