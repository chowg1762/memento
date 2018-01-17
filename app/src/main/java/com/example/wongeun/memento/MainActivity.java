package com.example.wongeun.memento;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.wongeun.memento.R;
import com.example.wongeun.memento.editor.EditerMainFragment;
import com.example.wongeun.memento.mainview.OnConvertButtonClickListener;
import com.example.wongeun.memento.mainview.OnThumbnailTouchListener;
import com.example.wongeun.memento.mainview.VideoListAdapter;
import com.example.wongeun.memento.mainview.WorkItem;
import com.example.wongeun.memento.utils.Converter;
import com.example.wongeun.memento.utils.FileManager;
import com.example.wongeun.memento.utils.GetPermission;
import com.example.wongeun.memento.utils.Record;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by wongeun on 12/18/17.
 */


public class MainActivity extends AppCompatActivity {
    private static final int SELECT_PICTURE = 1;
    private static final int SELECT_AUDIO = 2;
    private static final int REQUEST_TAKE_GALLERY_VIDEO = 3;

    private static final String CMD_FFMPEG_START = "-version";
    private static final String CMD_HEAD = "-y -i ";
    private static final String CMD_STRICT = " -vn -c:a copy -f adts ";
    private static final String SPACE = " ";
    private static final String AUDIO_FORMAT = "aac";
    private static final String VIDEO_INTENT = "video/*";


    ArrayList<WorkItem> workItems = new ArrayList<WorkItem>();

    RecyclerView videoList;
    VideoListAdapter videoListAdapter;
    LinearLayoutManager recyclerviewLayoutManager;
    ImageView chooseVideoButton;

    Record myRecord;

    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

    View.OnClickListener newVideoButtonOnclickListener = new View.OnClickListener(){
        public void onClick(View view){

            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Create a New Video");
            alertDialog.setMessage("Would you like to create a new video?");

            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent mainScreenIntent = new Intent("mainscreen");
                            startActivity(mainScreenIntent);
                        }
                    });

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

            alertDialog.show();

        }
    };

    OnThumbnailTouchListener thumbnailClickListner = new OnThumbnailTouchListener() {

        @Override
        public void onThumbnailTouch(int position) {

            if(workItems.get(position).isPlaying()){
                workItems.get(position).setPlayed(false);
            }else {
                workItems.get(position).setPlayed(true);
            }

            myRecord.onPlay(workItems.get(position).isPlaying(), workItems.get(position).getTitle());
            myRecord.onRecord(workItems.get(position).isPlaying(), workItems.get(position).getTitle());
        }

    };

    OnConvertButtonClickListener convertButtonClickListener = new OnConvertButtonClickListener() {

        @Override
        public void onClick(int position) {
            String cmd = CMD_HEAD + workItems.get(position).getTitle()+ CMD_STRICT;

            String[] fileDir = workItems.get(position).getTitle().split(File.separator);
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
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, permissions, 200);

        FileManager.makeMementoFolder();

        Converter.initFFMpeg(this);
        Converter.loadFFMpegBinary();

        videoList = (RecyclerView) findViewById(R.id.video_thumbnail_view);
        videoListAdapter = new VideoListAdapter(workItems, thumbnailClickListner, convertButtonClickListener);
        recyclerviewLayoutManager = new LinearLayoutManager(this);

        videoList.setLayoutManager(recyclerviewLayoutManager);
        videoList.setAdapter(videoListAdapter);

        chooseVideoButton = (ImageView) findViewById(R.id.choose_video_btn);
        chooseVideoButton.setOnClickListener(newVideoButtonOnclickListener);

        myRecord = new Record();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        //super.onActivityResult(requestCode,resultCode, data);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(!GetPermission.getPermision(requestCode, grantResults)){
            finish();
        }

    }

}
