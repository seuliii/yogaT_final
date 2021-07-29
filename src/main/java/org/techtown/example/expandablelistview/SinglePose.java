package org.techtown.example.expandablelistview;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import java.util.Timer;
import java.util.TimerTask;

public class SinglePose extends Activity implements View.OnClickListener {

    //MainActivity
    static Context context;
    private TimerTask tt;

    //layout
    private ImageView captureBtn,stopBtn,imageView;

    //object
    private TextToSpeech_yogat tts;
    CountDownTimer countDownTimer;

    //camera preview
    private Camera mCamera;
    private int mCameraFacing;
    private RelativeLayout preview;
    private CameraPreview mCameraView;

    private int resId;
    private String poseInfo;
    Animation animFadeIn, animFadeOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beginner);

        //init
        mCamera = getCameraInstance();
        context = getApplicationContext();
        tts = new TextToSpeech_yogat(SinglePose.this);
        animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);

        //layout init
        imageView = findViewById(R.id.imageView);
        preview = findViewById(R.id.preview);
        captureBtn = findViewById(R.id.BeginnercaptureBtn);
        stopBtn = findViewById(R.id.stopBtnBeginner);

        //포즈별 intent
        Intent intent = getIntent();
        resId = intent.getExtras().getInt("resId");
        poseInfo = intent.getExtras().getString("poseInfo");

        //카메라 프리뷰
        //mCameraFacing = 전면 or 후면을 결정
        mCameraFacing = (mCameraFacing == Camera.CameraInfo.CAMERA_FACING_BACK) ?
                Camera.CameraInfo.CAMERA_FACING_FRONT
                : Camera.CameraInfo.CAMERA_FACING_BACK;
        mCameraView = new CameraPreview(this, mCamera, mCameraFacing, 90, 270);


        preview.addView(mCameraView);
        imageView.setAlpha(95); //이미지 투명도
        imageView.bringToFront();   //imageView 최상단으로 올리기
        captureBtn.bringToFront();
        stopBtn.bringToFront();
        imageView.setImageResource(resId);

        //촬영 버튼
        captureBtn.setOnClickListener(this);
        //정지 버튼
        stopBtn.setOnClickListener(this);

    }   //onCreate


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


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.BeginnercaptureBtn) {
            Toast.makeText(SinglePose.this, "자세를 잡아주세요", Toast.LENGTH_LONG).show();
            //지속시간, 카운트다운 시간
            countDownTimer = new CountDownTimer(1000*60, 1000) {

                public void onTick(long millisUntilFinished) {}

                @Override
                public void onFinish() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SinglePose.this);

                    builder.setMessage("자세가 종료되었습니다.");
                    builder.setIcon(android.R.drawable.ic_dialog_alert);

                    builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            SinglePose.super.onRestart();
                        }

                    });
                    builder.setNegativeButton("돌아가기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SinglePose.super.onBackPressed();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }


            }.start();

            tts.sayText(poseInfo);

        }
    }
}