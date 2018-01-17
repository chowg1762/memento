package com.example.wongeun.memento.mainview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wongeun.memento.R;

import java.util.ArrayList;

/**
 * Created by wongeun on 12/18/17.
 */

public class VideoListAdapter extends RecyclerView.Adapter {

    private ArrayList<WorkItem> workItems;
    private OnThumbnailTouchListener thumbnailTouchListener;
    private OnConvertButtonClickListener convertButtonClickListener;

    public VideoListAdapter(ArrayList<WorkItem> workItems, OnThumbnailTouchListener thumbnailTouchListener, OnConvertButtonClickListener convertButtonClickListener){

        this.workItems = workItems;
        this.thumbnailTouchListener = thumbnailTouchListener;
        this.convertButtonClickListener = convertButtonClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View thumbnailItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_thumbnail_list_view, parent, false);
        ThumbnailViewHolder viewHolder = new ThumbnailViewHolder(thumbnailItem);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        ThumbnailViewHolder tvh = (ThumbnailViewHolder)holder;


        tvh.thumbnailImage.setImageBitmap(workItems.get(position).getThumbnailImage());
        tvh.thumbnailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                thumbnailTouchListener.onThumbnailTouch(position);

            }
        });

        tvh.convertButton.setOnClickListener(new  View.OnClickListener(){
            public void onClick(View view){
                convertButtonClickListener.onClick(position);
            }
        });

        tvh.textView.setText(workItems.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return workItems.size();
    }

    class ThumbnailViewHolder extends RecyclerView.ViewHolder  {

        ImageView thumbnailImage;
        TextView textView;
        Button convertButton;

        public ThumbnailViewHolder(View itemView) {

            super(itemView);
            this.thumbnailImage = (ImageView) itemView.findViewById(R.id.video_thumbnail);
            this.textView = (TextView) itemView.findViewById(R.id.video_thumbnail_title);
            this.convertButton = (Button) itemView.findViewById(R.id.video_thumbnail_convert_button);

        }
    }
}
