package com.example.wongeun.memento.editor.model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.wongeun.memento.R;
import com.example.wongeun.memento.editor.view.VideoTouchThumbnailListener;

import java.util.ArrayList;

/**
 * Created by wongeun on 1/15/18.
 */

public class VideoItemAdapter extends RecyclerView.Adapter {
    private ArrayList<VideoItem> videoItems;
    VideoTouchThumbnailListener thumbnailTouchListener;

    public VideoItemAdapter(ArrayList<VideoItem> videoItems, VideoTouchThumbnailListener thumbnailTouchListener){

        this.videoItems = videoItems;
        this.thumbnailTouchListener = thumbnailTouchListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View thumbnailItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_thumbnail_item, parent, false);
        VideoItemAdapter.ThumbnailHolder viewHolder = new VideoItemAdapter.ThumbnailHolder(thumbnailItem);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        VideoItemAdapter.ThumbnailHolder tvh = (VideoItemAdapter.ThumbnailHolder)holder;


        tvh.thumbnailImage.setImageBitmap(videoItems.get(position).getVideoThumbnail());
        tvh.thumbnailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                thumbnailTouchListener.onTouch(position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return videoItems.size();
    }

    class ThumbnailHolder extends RecyclerView.ViewHolder  {

        ImageView thumbnailImage;

        public ThumbnailHolder(View itemView) {
            super(itemView);
            this.thumbnailImage = (ImageView) itemView.findViewById(R.id.video_thumbnail_item);
        }
    }
}
