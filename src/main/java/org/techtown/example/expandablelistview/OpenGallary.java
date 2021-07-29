package org.techtown.example.expandablelistview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import java.io.IOException;

public class OpenGallary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_gallary);
        openGallary();
    }

    public void openGallary(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==101){
            Uri photoUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),photoUri);
                byte[] bytes = Base64Util.bitmapToByteArray(bitmap);
                String imageString = Base64Util.encode(bytes);
                Log.d("seul",imageString    );
                OKHttpConnection okHttpConnection = new OKHttpConnection();
                okHttpConnection.sendToServer_singlePose(imageString);
            } catch (IOException e) {
                e.printStackTrace();
            }



        }
    }

}