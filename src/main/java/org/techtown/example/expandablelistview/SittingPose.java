package org.techtown.example.expandablelistview;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;


import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import java.util.Timer;
import java.util.TimerTask;


//***************8타이머이벤트 전에 tts뿌리기 sleep준 후 사진 찍기

public class SittingPose extends AppCompatActivity implements View.OnClickListener {

    //MainActivity
    static Context context;
    private TimerTask tt;

    //send image to Django
    static String imageString;
    private String responseData;
    private byte[] pictureByteArr;
    private Bitmap imageBitmap;
    private String pose_name;

    //layout
    private ImageView captureBtn, stopBtn, imageView;

    //object
    private OKHttpConnection okHttpConnection;
    private TextToSpeech_yogat tts;


    //camera preview
    private Camera mCamera;
    private int mCameraFacing;
    private RelativeLayout preview;
    private CameraPreview mCameraView;

    private Animation animFadeIn, animFadeOut;
    private CountDownTimer countDownTimer;
    private String name;
    private SoundPool soundPool;
    private int sound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.beginner);


        //init
        mCamera = getCameraInstance();
        context = getApplicationContext();
        okHttpConnection = new OKHttpConnection();
        tts = new TextToSpeech_yogat(this);
        animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);
        animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_out);
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
        sound = soundPool.load(this,R.raw.mixdown_second,1);


        //layout init
        imageView = findViewById(R.id.imageView);
        preview = findViewById(R.id.preview);
        captureBtn = findViewById(R.id.BeginnercaptureBtn);
        stopBtn = findViewById(R.id.stopBtnBeginner);

        Intent intent = getIntent();
        name = intent.getExtras().getString("name");


        //카메라 프리뷰
        //mCameraFacing = 전면 or 후면을 결정
        mCameraFacing = (mCameraFacing == Camera.CameraInfo.CAMERA_FACING_BACK) ?
                Camera.CameraInfo.CAMERA_FACING_FRONT
                : Camera.CameraInfo.CAMERA_FACING_BACK;
        mCameraView = new CameraPreview(this, mCamera, mCameraFacing, 0, 0);

        preview.addView(mCameraView);
        imageView.setAlpha(150); //이미지 투명도
        imageView.bringToFront();   //imageView 최상단으로 올리기
        stopBtn.bringToFront();
        captureBtn.bringToFront();


        //촬영 버튼
        captureBtn.setOnClickListener(this);
        //정지 버튼
        stopBtn.setOnClickListener(this);
        //handler = new MainHandler();
    }   //onCreate


    //Django서버에 이미지를 전송
    public void sendToServer() {
        try {

            //http통신에선 데이터를 주고 받을 때 String형식을 통해 주고받을 수 있음
            //Base64를 통해서 byteArray를 String으로 변환해서 전송
            imageBitmap =  mCameraView.getBitmap();
            pictureByteArr = Base64Util.bitmapToByteArray(imageBitmap);
            imageString = Base64Util.encode(pictureByteArr);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        JSONObject json = new JSONObject();
        try {
            json.put("imageString",imageString);
            json.put("pose_name",pose_name);
            json.put("name",name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        okHttpConnection.sendToServer_main(json);
        responseData = OKHttpConnection.responseData;
        Log.d("seul","responseData in sendToServer method in Main is" + responseData);


    }

    public Camera getCameraInstance() {
        Camera c = null;
        try {
            // attempt to get a Camera instance
            c = Camera.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // returns null if camera is unavailable
        return c;
    }


    //timer가 끝난 후 사진 촬영
    public void timerInTakingPictures(){
        //timer가 실행될 때 해야할 일
        tt = new TimerTask() {
            @Override
            public void run() {
                mCameraView.capture();
                sendToServer();
                Log.d("seul","responseData in Timer is " + responseData  );

                tts.sayText(responseData);
            }
        };
        //timer 설정 (해야할 일 , 몇 초후에 시작할지, 타이머 주기)
        Timer timer = new Timer();
        timer.schedule(tt,10*1000,10*1000);
    }


    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.BeginnercaptureBtn){
            Toast.makeText(SittingPose.this,"자세를 잡아주세요",Toast.LENGTH_LONG).show();
            timerInTakingPictures();
            //지속시간, 카운트다운 시간
            countDownTimer = new CountDownTimer(30*1000, 30*1000) {
                public void onTick(long millisUntilFinished) {
                    imageView.startAnimation(animFadeIn);
                    imageView.setImageResource(R.drawable.cobra);
                    tts.sayText(getString(R.string.cobra));
                    pose_name = "cobra";
                }

                public void onFinish() {
                    imageView.startAnimation(animFadeOut);
                    try {
                        Thread.sleep(8*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(SittingPose.this,"다음 자세로 넘어갑니다",Toast.LENGTH_LONG).show();

                    soundPool.play(sound,1f,1f,0,0,1f);
                    try {
                        Thread.sleep(2*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    imageView.startAnimation(animFadeIn);
                    imageView.setImageResource(R.drawable.balancing_table);
                    tts.sayText(getString(R.string.balancing_table));
                    pose_name = "balancing_table";
                }
            }.start();

        }else if(v.getId() == R.id.stopBtnBeginner){

            AlertDialog.Builder builder = new AlertDialog.Builder(SittingPose.this);
            builder.setMessage("요가를 종료하시겠습니까?");
            builder.setIcon(android.R.drawable.ic_dialog_alert);

            builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    SittingPose.super.onBackPressed();
                    //촬영 종료
                    tt.cancel();

                }
            });
            builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {}
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}

