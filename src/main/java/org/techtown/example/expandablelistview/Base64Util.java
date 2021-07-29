package org.techtown.example.expandablelistview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Base64Util {

    //Django 서버와 통신하기 위해 파일을 세팅할 때 사용하는 함수


    //django서버로 전송할 때 이미지파일을 encoding
    public static String encode(byte[] bytes) throws UnsupportedEncodingException{
        return Base64.getEncoder().encodeToString(bytes);
    }

    //django서버에서 파일을 base64.encode해준 이미지 파일을 받은 후 decoding
    public static byte[] decode(String str) {
        return Base64.getDecoder().decode(str);
    }


    public static byte[] bitmapToByteArray (Bitmap $bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        $bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    
    public static Bitmap byteArrayToBitmap(byte[] bytes){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 100,100);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap top1Image=BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);

        return top1Image;
    }

    //bitmap 사이즈 줄이는 함수
    public static int calculateInSampleSize( BitmapFactory.Options options, int reqWidth, int reqHeight){
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }


}
