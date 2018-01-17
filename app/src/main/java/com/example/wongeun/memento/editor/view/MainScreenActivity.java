package com.example.wongeun.memento.editor.view;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.v7.app.AppCompatActivity;

import com.example.wongeun.memento.R;
import com.example.wongeun.memento.editor.model.VideoItem;
import com.example.wongeun.memento.editor.model.VideoItemAdapter;
import com.example.wongeun.memento.editor.presenter.VideoItemManagePresenterImplement;
import com.example.wongeun.memento.editor.view.BottomFragment;
import com.example.wongeun.memento.editor.view.TopFragment;
import com.example.wongeun.memento.editor.view.VideoListFragment;
import com.example.wongeun.memento.mainview.OnConvertButtonClickListener;
import com.example.wongeun.memento.mainview.WorkItem;
import com.example.wongeun.memento.utils.Converter;
import com.example.wongeun.memento.utils.GetPermission;
import com.example.wongeun.memento.utils.Record;

import java.util.ArrayList;


/**
 * Created by wongeun on 12/18/17.
 */


public class MainScreenActivity extends AppCompatActivity {
    private static final int SELECT_PICTURE = 1;
    private static final int SELECT_AUDIO = 2;
    private static final int REQUEST_TAKE_GALLERY_VIDEO = 3;

    private static final String CMD_FFMPEG_START = "-version";
    private static final String CMD_HEAD = "-y -i ";
    private static final String CMD_STRICT = " -vn -c:a copy -f adts ";
    private static final String SPACE = " ";
    private static final String AUDIO_FORMAT = "aac";
    private static final String VIDEO_INTENT = "video/*";


    private VideoItemAdapter videoItemAdapter;
    private String filemanagerstring;
    private String selectedImagePath;

    private Record myRecord;

    private String[] permissions = {Manifest.permission.RECORD_AUDIO};

    VideoItemManagePresenterImplement videoItemManagePresenterImp;


    private OnConvertButtonClickListener convertButtonClickListener = new OnConvertButtonClickListener() {

        @Override
        public void onClick(int position) {
            //Converter.convertVideoToAudio(workItems.get(position));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        ActivityCompat.requestPermissions(this, permissions, 200);
//        Converter.initFFMpeg(this);
//        Converter.loadFFMpegBinary();
        initUI();

        myRecord = new Record();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (!GetPermission.getPermision(requestCode, grantResults)) {
            finish();
        }

    }

    private void initUI() {

        videoItemManagePresenterImp = new VideoItemManagePresenterImplement();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FragmentInterface topFragment = new TopFragment();
        FragmentInterface bodyFragment = new VideoListFragment();
        FragmentInterface bottomFragment = new BottomFragment();

        topFragment.setVideoItemManagePresenterImp(videoItemManagePresenterImp);
        bodyFragment.setVideoItemManagePresenterImp(videoItemManagePresenterImp);
        bottomFragment.setVideoItemManagePresenterImp(videoItemManagePresenterImp);

        fragmentTransaction.add(R.id.top_fragment, (Fragment) topFragment);
        fragmentTransaction.add(R.id.main_container, (Fragment) bodyFragment);
        fragmentTransaction.add(R.id.bottom_fragment, (Fragment) bottomFragment);

        fragmentTransaction.commit();

    }
}


