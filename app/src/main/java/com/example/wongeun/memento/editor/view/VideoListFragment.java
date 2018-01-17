package com.example.wongeun.memento.editor.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wongeun.memento.R;
import com.example.wongeun.memento.editor.model.VideoItem;
import com.example.wongeun.memento.editor.model.VideoItemAdapter;
import com.example.wongeun.memento.editor.presenter.VideoItemManagePresenterImplement;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

/**
 * Created by wongeun on 1/15/18.
 */

public class VideoListFragment extends Fragment implements FragmentInterface{

    private ArrayList<VideoItem> videoItems;
    private RecyclerView videoThumbnailListView;

    private VideoItemManagePresenterImplement videoItemManagePresenterImp;
    private LinearLayoutManager recyclerviewLayoutManager;

    Handler mainHandler = new Handler();
    BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
    TrackSelection.Factory videoTrackSelectionFactory =
            new AdaptiveTrackSelection.Factory(bandwidthMeter);
    TrackSelector trackSelector =
            new DefaultTrackSelector(videoTrackSelectionFactory);


    SimpleExoPlayerView simpleExoPlayerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_list, container, false);
        initUI(view);

        return view;
    }

    private void initUI(View view){
        //ExoPlayer
        SimpleExoPlayer player =
                ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);

        simpleExoPlayerView = view.findViewById(R.id.fragment_video_list_video_view);
        simpleExoPlayerView.setPlayer(player);

        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getActivity(),
                Util.getUserAgent(getActivity(), "Memento"), bandwidthMeter);
        videoItemManagePresenterImp.setMediaSource(dataSourceFactory, player);

        videoThumbnailListView = (RecyclerView) view.findViewById(R.id.video_thumbnail_recycler_view);
        recyclerviewLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        videoThumbnailListView.setLayoutManager(recyclerviewLayoutManager);
        videoThumbnailListView.setAdapter(videoItemManagePresenterImp.getVideoItemAdapter());

    }


    public void setVideoItemManagePresenterImp(VideoItemManagePresenterImplement videoItemManagePresenterImp){
        this.videoItemManagePresenterImp = videoItemManagePresenterImp;
    }

    public VideoItemManagePresenterImplement getVideoItemManagePresenterImp(){
        return this.videoItemManagePresenterImp;
    }
}
