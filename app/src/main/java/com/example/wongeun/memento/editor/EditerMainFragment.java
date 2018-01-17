package com.example.wongeun.memento.editor;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.wongeun.memento.R;
import com.example.wongeun.memento.editor.model.VideoItem;
import com.example.wongeun.memento.editor.model.VideoItemAdapter;
import com.example.wongeun.memento.editor.view.VideoListFragment;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by wongeun on 1/14/18.
 */

public class EditerMainFragment extends Fragment implements Serializable {

    private static final String VIDEO_INTENT = "video/*";
    private static final int REQUEST_TAKE_GALLERY_VIDEO = 3;


    ImageView addNewVideoButton;
    ImageView trimVideoButton;
    ImageView extractAudioButton;

    String filemanagerstring;
    String selectedImagePath;

    ArrayList<VideoItem> videoItems = new ArrayList<VideoItem>();
    VideoItemAdapter videoItemAdapter;
    VideoListFragment videoListFragment;


    View.OnClickListener onAddNewVideoButtonClickListner = new View.OnClickListener() {
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

        View view = inflater.inflate(R.layout.editor_fragment,
                container, false);
        initUI(view);

        return view;

    }

    private void initUI(@Nullable View view){

        addNewVideoButton = (ImageView) view.findViewById(R.id.add_new_video_button);
        trimVideoButton = (ImageView)  view.findViewById(R.id.trim_button);
        extractAudioButton =  (ImageView) view.findViewById(R.id.audio_extract_button);

        addNewVideoButton.setOnClickListener(onAddNewVideoButtonClickListner);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Bundle argument = new Bundle();

        argument.putSerializable("videoItems", videoItems);
         videoListFragment = new VideoListFragment();

        videoListFragment.setArguments(argument);

        fragmentTransaction.replace(R.id.video_recycler_view, videoListFragment);
        fragmentTransaction.commit();

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){

//        if(resultCode == Activity.RESULT_OK){
//
//            if(requestCode == REQUEST_TAKE_GALLERY_VIDEO){
//
//                Uri selectedImageUri = data.getData();
//
//                filemanagerstring = selectedImageUri.getPath();
//                selectedImagePath = getPath(selectedImageUri);
//
//                Bitmap thumbnailImage = ThumbnailUtils.createVideoThumbnail(selectedImagePath, MediaStore.Images.Thumbnails.MINI_KIND);
//
//                VideoItem newVideo = new VideoItem(thumbnailImage);
//
//                videoItems.add(newVideo);
//               // Log.d("onActivityResult", "Size : " + videoItems.size());
//               // videoListFragment.refreshAdapter();
//            }
//        }
    }

    private String getPath(Uri uri){
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);

        if(cursor!=null){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else{
            return null;
        }
    }

}
