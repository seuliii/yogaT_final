package org.techtown.example.expandablelistview;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;

public class FreeMode extends Activity {

    //MainActivity
    static Context context;
    private TimerTask tt;

    //send image to Django
    static String imageString;
    private String responseData;
    private byte[] pictureByteArr;
    private Bitmap imageBitmap;
    private Intent intent;

    //layout
    private ImageView imageView,captureBtn,stopBtn;
    private TextView free_txt;
    private Button free_confirm;
    private Dialog dialog;

    //object
    private OKHttpConnection okHttpConnection;
    //camera preview
    private Camera mCamera;
    private int mCameraFacing;
    private RelativeLayout preview;
    private CameraPreview mCameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beginner);

        //init
        mCamera = getCameraInstance();
        context = getApplicationContext();
        okHttpConnection = new OKHttpConnection();
        intent = new Intent();

        //layout init
        imageView = findViewById(R.id.imageView);
        preview = findViewById(R.id.preview);
        captureBtn = findViewById(R.id.BeginnercaptureBtn);
        stopBtn = findViewById(R.id.stopBtnBeginner);


        //프리모드 안내하기 위한 customDialog
        dialog = new Dialog(FreeMode.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.free_dialog);
        showFreeDialog();



        //카메라 프리뷰
        //mCameraFacing = 전면 or 후면을 결정
        mCameraFacing = (mCameraFacing == Camera.CameraInfo.CAMERA_FACING_BACK) ?
                Camera.CameraInfo.CAMERA_FACING_FRONT
                : Camera.CameraInfo.CAMERA_FACING_BACK;
        //가로모드(0,0)
        mCameraView = new CameraPreview(this, mCamera, mCameraFacing,0,0);
        preview.addView(mCameraView);

        //프리뷰위에 버튼을 세팅
        imageView.bringToFront();
        stopBtn.bringToFront();
        captureBtn.bringToFront();

        //촬영 버튼
        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerInTakingPictures();
                Toast.makeText(FreeMode.this,"촬영이 시작됩니다",Toast.LENGTH_SHORT).show();

            }
        });

        //정지 버튼
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //타이머 해제

                AlertDialog.Builder builder = new AlertDialog.Builder(FreeMode.this);
                builder.setMessage("요가를 종료하시겠습니까?");
                builder.setIcon(android.R.drawable.ic_dialog_alert);

                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        //촬영 종료

                        Intent intent = new Intent(FreeMode.this, AfterFreeMode.class);
                        tt.cancel();
                        Log.d("seul","score list:"+OKHttpConnection.score_list);
                        startActivity(intent);

                    }
                });
                builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }


    public void showFreeDialog(){
        free_txt = dialog.findViewById(R.id.free_txt);
        free_confirm = dialog.findViewById(R.id.free_confirm);

        free_txt.setText("프리모드는 원하는 자세를 취한 뒤 기존 자세들과의 일치율이 어느정도인지를 보여주는 기능입니다." +
                "원하는 자세를 취해주세요");
        
        
        dialog.show();

        free_confirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    //Django서버에 이미지를 전송
    public void sendToServer() {
        try {

            //http통신에선 데이터를 주고 받을 때 String형식을 통해 주고받을 수 있음
            //Base64를 통해서 byteArray를 String으로 변환해서 전송
            imageBitmap =  mCameraView.getBitmap();
            pictureByteArr = bitmapToByteArray(imageBitmap);
            imageString = Base64Util.encode(pictureByteArr);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        okHttpConnection.sendToServer_freemode(imageString);

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
                //사진촬영
                mCameraView.capture();
                //사진 촬영 후 사진 받아오기
                pictureByteArr = CameraPreview.imagebytes;
                //서버에 전송
                sendToServer();
                Log.d("seul","responseData in Timer is " + responseData  );
            }
        };
        //timer 설정 (해야할 일 , 몇 초후에 시작할지, 타이머 주기)
        Timer timer = new Timer();
        //10초 후에 timerTask실행, 10초마다 실행
        timer.schedule(tt,10*1000,10*1000);
    }



    //bitmap의 사진을 byte[]로 변환 후 String으로 변환->전송
    public byte[] bitmapToByteArray (Bitmap $bitmap){

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        $bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

}