package org.techtown.example.expandablelistview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/** A basic Camera preview class */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mHolder;
    private Camera mCamera;
    private int mCameraFacing;
    static byte[] imagebytes;
    static Bitmap imageBitmap;
    //세로 (90,270) 가로 (0,0)
    private int preview_orientation, picture_dgree;
    Camera.Parameters parameters;

    public CameraPreview(Context context, Camera camera, int cameraFacing,int orientation, int rotation) {
        super(context);
        mCamera = camera;
        this.preview_orientation = orientation;
        this.picture_dgree = rotation;
        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);

        mCameraFacing = cameraFacing;
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }


    //CameraPreview가 실행됨
    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            //카메라 세팅
            parameters = mCamera.getParameters();

            //프리뷰위에 띄울 이미지
            //각 기기마다 호환되는 프리뷰, 사진의 크기가 다르기 때문에 Log에 띄워서 확인 후 설정
            /*if(parameters != null){
                List<Camera.Size> pictureSizeList = parameters.getSupportedPictureSizes();
                for (Camera.Size size : pictureSizeList) {        //지원하는 사진 크기

                    Log.e("==PictureSize==", "width : " + size.width + "  height : " + size.height);
                }

                List<Camera.Size> previewSizeList = parameters.getSupportedPreviewSizes();
                for (Camera.Size size : previewSizeList) {        //지원하는 프리뷰 크기

                    Log.e("==PreviewSize==", "width : " + size.width + "  height : " + size.height);
                }
            }*/

            parameters.setPictureSize(720,480);
            mCamera = Camera.open(mCameraFacing);
            mCamera.setParameters(parameters);
            mCamera.setDisplayOrientation(preview_orientation);
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();

        } catch (IOException e) {
            Log.d("camera Error", "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {

        // empty. Take care of releasing the Camera preview in your activity.
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            // mCamera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (Exception e) {
            Log.d("camera Error", "Error starting camera preview: " + e.getMessage());
        }
    }


    //촬영
    public void capture() {
        if (mCamera != null) {

            //셔터음 X
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(mCameraFacing, info);
            if (info.canDisableShutterSound) {
                mCamera.enableShutterSound(false);
            }

            mCamera.takePicture(null, rawCallback, jpegCallback);

            try {
                //촬영 끝나고도 preview 유지
                parameters.setPictureSize(720,480);
                mCamera = Camera.open(mCameraFacing);
                mCamera.setParameters(parameters);
                mCamera.setDisplayOrientation(preview_orientation);
                mCamera.setPreviewDisplay(mHolder);
                mCamera.startPreview();
            } catch (IOException e) {
                Log.d("camera Error", "Error setting camera preview: " + e.getMessage());
            }
        }
    }

    Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {
        public void onShutter() {
        }
    };

    Camera.PictureCallback rawCallback = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
        }
    };

    Camera.PictureCallback jpegCallback = new Camera.PictureCallback() {
        public void onPictureTaken(final byte[] data, Camera camera) {
            //찍힌 이미지 변수에 저장
            imagebytes = data;
            imageBitmap = BitmapFactory.decodeByteArray(imagebytes,0, imagebytes.length);
        }
    };


    //돌아간 사진 제대로 저장
    private Bitmap rotateImage(Bitmap source) {
        Matrix matrix = new Matrix();
        //세로 사진 저장
        // matrix.postRotate(270);
        matrix.postRotate(picture_dgree);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    public Bitmap getBitmap(){
        Log.d("seul","imageBitmap in CameraPreview is "+imageBitmap);
        Bitmap imageBitmap1 = rotateImage(imageBitmap);
        return imageBitmap1;
    }


}