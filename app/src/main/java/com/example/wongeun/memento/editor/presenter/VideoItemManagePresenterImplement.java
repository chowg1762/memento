package com.example.wongeun.memento.editor.presenter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.wongeun.memento.editor.model.VideoItem;
import com.example.wongeun.memento.editor.model.VideoItemAdapter;
import com.example.wongeun.memento.editor.view.VideoTouchThumbnailListener;
import com.example.wongeun.memento.mainview.OnThumbnailTouchListener;
import com.example.wongeun.memento.utils.FileManager;
import com.example.wongeun.memento.utils.MyThumbnailUtils;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.DataSource;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by wongeun on 1/15/18.
 */

public class VideoItemManagePresenterImplement implements VideoItemManagePresneter {
    private ArrayList<VideoItem> videoItems;
    private VideoItemAdapter videoItemAdapter;
    private VideoItem playingVideo;

    private DataSource.Factory dataSourceFactory;
    private SimpleExoPlayer player;

    VideoTouchThumbnailListener thumbnailClickListner = new VideoTouchThumbnailListener() {

        @Override
        public void onTouch(int position) {
            playingVideo = videoItems.get(position);

            Log.e("thumbnailClickListner", "playing video : "+playingVideo.getSelectedImageUri()+"");
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(playingVideo.getSelectedImageUri());
            Log.e("thumbnailClickListner", "video Src : "+videoSource);

            player.prepare(videoSource);
        }

    };


    public VideoItemManagePresenterImplement(){
        this.videoItems = new ArrayList<VideoItem>();
        videoItemAdapter = new VideoItemAdapter(videoItems, thumbnailClickListner);
    }


    @Override
    public void addVideoItem(VideoItem videoItem) {
        videoItems.add(videoItem);
    }

    @Override
    public void removeVideoItem(VideoItem videoItem) {
        int pos = 0;
        for(VideoItem v : videoItems){

            if(v.equals(videoItem)){
                videoItems.remove(pos);
            }
            pos++;
        }
    }

    public void refreshData(){
        videoItemAdapter.notifyDataSetChanged();
    }

    @Override
    public ArrayList<VideoItem> getVideoItemsList() {
        return videoItems;
    }

    @Override
    public VideoItemAdapter getVideoItemAdapter() {
        return videoItemAdapter;
    }

    public void takeNewVideo(Intent data, Activity activity){
        Uri selectedImageUri = data.getData();

        //String filemanagerstring = selectedImageUri.getPath();
        String selectedImagePath = FileManager.getAbsoultePath(selectedImageUri, activity);

        Bitmap thumbnailImage = ThumbnailUtils.createVideoThumbnail(selectedImagePath, MediaStore.Images.Thumbnails.MINI_KIND);

        VideoItem newVideo = new VideoItem(MyThumbnailUtils.getResizedThumbnail(thumbnailImage, 250), selectedImageUri);

        addVideoItem(newVideo);
        refreshData();
    }

    public void setMediaSource(DataSource.Factory dataSourceFactory, SimpleExoPlayer player){
        this.dataSourceFactory = dataSourceFactory;
        this.player = player;
    }

}
