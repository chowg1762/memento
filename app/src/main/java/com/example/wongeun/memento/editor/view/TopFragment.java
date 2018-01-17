package com.example.wongeun.memento.editor.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.wongeun.memento.R;
import com.example.wongeun.memento.editor.model.VideoItem;
import com.example.wongeun.memento.editor.presenter.Subject;
import com.example.wongeun.memento.editor.presenter.VideoItemManagePresenterImplement;
import com.example.wongeun.memento.utils.FileManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Observer;

/**
 * Created by wongeun on 1/15/18.
 */

public class TopFragment extends Fragment implements FragmentInterface{
    private static final String VIDEO_INTENT = "video/*";
    private static final int REQUEST_TAKE_GALLERY_VIDEO = 3;

    private ImageView addNewVideoButton;
    private ArrayList<VideoItem> videoItems;
    private String filemanagerstring;
    private String selectedImagePath;

    private VideoItemManagePresenterImplement videoItemManagePresenterImp;

    private View.OnClickListener buttonOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent newVideoIntet = new Intent();
            newVideoIntet.setType(VIDEO_INTENT);
            newVideoIntet.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(newVideoIntet,"Select Video"),REQUEST_TAKE_GALLERY_VIDEO);

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.top_fragment,
                container, false);

        addNewVideoButton = (ImageView)view.findViewById(R.id.add_new_video_button);
        addNewVideoButton.setOnClickListener(buttonOnclickListener);

        return view;

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){

        if(resultCode == Activity.RESULT_OK){

            if(requestCode == REQUEST_TAKE_GALLERY_VIDEO){

                videoItemManagePresenterImp.takeNewVideo(data, getActivity());
//                Uri selectedImageUri = data.getData();
//
//                filemanagerstring = selectedImageUri.getPath();
//                selectedImagePath = FileManager.getAbsoultePath(selectedImageUri, getActivity());
//
//                Bitmap thumbnailImage = MyThumbnailUtils.createVideoThumbnail(selectedImagePath, MediaStore.Images.Thumbnails.MINI_KIND);
//
//                VideoItem newVideo = new VideoItem(thumbnailImage);
//
//                videoItems.add(newVideo);
            }
        }
    }

    public void setVideoItemManagePresenterImp(VideoItemManagePresenterImplement videoItemManagePresenterImp){
        this.videoItemManagePresenterImp = videoItemManagePresenterImp;
    }

    public VideoItemManagePresenterImplement getVideoItemManagePresenterImp(){
        return this.videoItemManagePresenterImp;
    }

}
