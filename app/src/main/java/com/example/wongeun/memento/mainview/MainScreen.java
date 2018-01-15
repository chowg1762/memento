package com.example.wongeun.memento.mainview;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wongeun.memento.MainActivity;
import com.example.wongeun.memento.R;
import com.example.wongeun.memento.utils.Converter;
import com.example.wongeun.memento.utils.FileManager;
import com.example.wongeun.memento.utils.GetPermission;
import com.example.wongeun.memento.utils.Record;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by wongeun on 12/18/17.
 */

public class MainScreen extends AppCompatActivity {
    private static final int SELECT_PICTURE = 1;
    private static final int SELECT_AUDIO = 2;
    private static final int REQUEST_TAKE_GALLERY_VIDEO = 3;

    private static final String CMD_FFMPEG_START = "-version";
    private static final String CMD_HEAD = "-y -i ";
    private static final String CMD_STRICT = " -vn -c:a copy -f adts ";
    private static final String SPACE = " ";
    private static final String AUDIO_FORMAT = "aac";
    private static final String VIDEO_INTENT = "video/*";


    ArrayList<VideoItem> videoItems = new ArrayList<VideoItem>();

    RecyclerView videoList;
    VideoListAdapter videoListAdapter;
    LinearLayoutManager recyclerviewLayoutManager;
    ImageView chooseVideoButton;

    String filemanagerstring;
    String selectedImagePath;

    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;

    Record myRecord;

    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

    View.OnClickListener buttonOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent newVideoIntet = new Intent();
            newVideoIntet.setType(VIDEO_INTENT);
            newVideoIntet.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(newVideoIntet,"Select Video"),REQUEST_TAKE_GALLERY_VIDEO);

        }
    };

    OnThumbnailTouchListener thumbnailClickListner = new OnThumbnailTouchListener() {

        @Override
        public void onThumbnailTouch(int position) {

            if(videoItems.get(position).isPlaying()){
                videoItems.get(position).setPlayed(false);
            }else {
                videoItems.get(position).setPlayed(true);
            }

            myRecord.onPlay(videoItems.get(position).isPlaying(), videoItems.get(position).getTitle());
            myRecord.onRecord(videoItems.get(position).isPlaying(), videoItems.get(position).getTitle());
        }

    };

    OnConvertButtonClickListener convertButtonClickListener = new OnConvertButtonClickListener() {

        @Override
        public void onClick(int position) {
            String cmd = CMD_HEAD +videoItems.get(position).getTitle()+ CMD_STRICT;

            String[] fileDir = videoItems.get(position).getTitle().split(File.separator);
            String fileName = fileDir[fileDir.length-1];
            String out_audio_file = FileManager.getHomeDir()+ File.separator+ fileName.substring(0, fileName.length()-3)+AUDIO_FORMAT;

            cmd = cmd+out_audio_file;

            String[] command = cmd.split(SPACE);

            Converter.execFFmpegBinary(CMD_FFMPEG_START.split(SPACE));
            Converter.execFFmpegBinary(command);

            File output = new File(out_audio_file);
            try {
                FileInputStream inputStream = new FileInputStream(output);

                int content;
                while((content = inputStream.read())!=-1){
                    Log.d("check", content+"");
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        ActivityCompat.requestPermissions(this, permissions, 200);

        FileManager.makeMementoFolder();

        Converter.initFFMpeg(this);
        Converter.loadFFMpegBinary();

        videoList = (RecyclerView) findViewById(R.id.video_thumbnail_view);
        videoListAdapter = new VideoListAdapter(videoItems, thumbnailClickListner, convertButtonClickListener);
        recyclerviewLayoutManager = new LinearLayoutManager(this);

        videoList.setLayoutManager(recyclerviewLayoutManager);
        videoList.setAdapter(videoListAdapter);

        chooseVideoButton = (ImageView) findViewById(R.id.choose_video_btn);
        chooseVideoButton.setOnClickListener(buttonOnclickListener);

        myRecord = new Record();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){

        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_TAKE_GALLERY_VIDEO){
                Uri selectedImageUri = data.getData();

                filemanagerstring = selectedImageUri.getPath();
                selectedImagePath = getPath(selectedImageUri);

                Bitmap thumbnailImage = ThumbnailUtils.createVideoThumbnail(selectedImagePath, MediaStore.Images.Thumbnails.MINI_KIND);

                VideoItem newVideo = new VideoItem(thumbnailImage, selectedImagePath);

                videoItems.add(newVideo);

                videoListAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(!GetPermission.getPermision(requestCode, grantResults)){
            finish();
        }

    }

    private String getPath(Uri uri){
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        if(cursor!=null){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else{
            return null;
        }
    }



}
