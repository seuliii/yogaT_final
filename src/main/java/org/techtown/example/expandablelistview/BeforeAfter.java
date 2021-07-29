package org.techtown.example.expandablelistview;



import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BeforeAfter extends Activity{

    //layout
    private ImageView beforeImg,afterImg;
    private ImageView imgGif;   //loading 화면
    private TextView txt_select_pose;
    private Spinner spinner_select_pose;

    //bring response image and text
    private String responseData;
    private OKHttpConnection okHttpConnection;
    private Gson gson;
    private List<Bitmap> imageList;
    private ImagesDTO images;
    private String pose_name;
    private String name;

    //object
    private BackgroundThread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.before_after);

        //init
        beforeImg = findViewById(R.id.beforeImg);
        afterImg = findViewById(R.id.afterImg);
        txt_select_pose = findViewById(R.id.txt_select_pose);
        imgGif = findViewById(R.id.img_loading3);
        spinner_select_pose = findViewById(R.id.spinner_select_pose);

        okHttpConnection = new OKHttpConnection();
        thread = new BackgroundThread();

        //사용자의 이름을 받아서 서버에 전송하기 위해 getIntent로 데이터를 받아옴
        Intent intent = getIntent();
        name = intent.getExtras().getString("name");

        //spinner(dropdown) init
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.pose_array,android.R.layout.simple_spinner_dropdown_item);
        spinner_select_pose.setAdapter(adapter);
        spinner_select_pose.setEnabled(false);

        //spinner 같은 경우 아이템이 기본적으로 설정되어 있기 떄문에 textView를 따로 만들어서 클릭하게 만든다
        //기본적으로 선택되어 있는 경우, 첫번째로 선택된 아이템이 서버로 전송되기 때문에 설정
        txt_select_pose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //text를 선택하면 spinner에서 아이템을 고를 수 있도록 함
                spinner_select_pose.setEnabled(true);

                //spinner에 선택된 아이템이 변경되면
                spinner_select_pose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        pose_name=spinner_select_pose.getSelectedItem().toString();

                        //서버로 전송할 항목 (로그인 후 설정된 사용자의 이름, 스피너에서 고른 포즈이름)
                        //여러개의 항목을 전송해야하기 때문에 json객체로 넘김
                        JSONObject json = new JSONObject();
                        try {
                            json.put("name",name);
                            json.put("pose_name",pose_name);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //서버 전송
                        okHttpConnection.sendToServer_BandA(getOKHttpCallback(),json);
                        //서버에서 데이터를 받아오는데 걸리는 시간이 있기떄문에 쓰레드와 핸들러를 통해서 로딩시간을 세팅
                        thread.start();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });
    }

    //background에서 thread로 handler가 계속 돌면서 imageList를 받아옴
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(imageList != null){
                //이미지를 받아오게 되면 loading이미지로 설정된 imgGif를 안보이게 설정 후 이미지리스트를 세팅
                imgGif.setVisibility(View.GONE);
                beforeImg.setImageBitmap(imageList.get(0));
                afterImg.setImageBitmap(imageList.get(1));

                //이미지를 받아오면 쓰레드를 멈추게 한다(아니면 계속해서 서버에서 데이터를 받게됨)
                thread.stopThread(false);
            }else{
                Glide.with(BeforeAfter.this)
                        .asGif()    //GIF 로딩
                        .load(R.raw.loading3)   //Image url
                        .placeholder(R.drawable.loading3)  //animted progress bar
                        .diskCacheStrategy(DiskCacheStrategy.NONE) //Dont store cache   // RESOURCE 으로 바꿨을떄 Glide에서 캐싱한 리소스와 로드할 리소스가 같을때 캐싱된 리소스 사용
                        .into(imgGif);
            }
        }
    };

    //response 객체 받아오는 callback 함수
    //callback함수를 메인에서 선언할 경우, 조금 더 response의 시간을 늘릴 수 있다
    private Callback getOKHttpCallback() {
        Callback OKHttpCallback = new Callback() {

            //서버에 연결이 되어 있지않거나 이상이 있는 경우, 로그에 이상을 나타냄
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("seul", "Error Message : " + e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //django에서 response 객체에 담겨있는 것을 안드로이드에서 String 변수에 담아냄
                responseData = response.body().string();

                //response가 json형식이기 떄문에 gson을 사용해서 imagesDTO에 받아옴
                //gson은 DTO를 생성하게되면 자동으로 parsing
                gson = new Gson();
                images = gson.fromJson(responseData,ImagesDTO.class);

                imageList = new ArrayList<>();
                //image 변환
                //django에서 String으로 된 이미지를 받아온 후 Base64Util class를 통해 byte[]로 decode
                //byte[]를 bitmap으로 변환
                for(int i=0; i<images.getImages().size(); i++){
                    String top3ImgStr = images.getImages().get(i);
                    byte[] bytes = Base64Util.decode(top3ImgStr);
                    Bitmap imagesInTop3 = Base64Util.byteArrayToBitmap(bytes);
                    imageList.add(imagesInTop3);
                }
            }
        };
        return OKHttpCallback;
    }//getOKHttpCallback


    class BackgroundThread extends Thread {

        //thread를 멈추게 하기위한 코드
        private boolean running = false;
        BackgroundThread(){
            running = true;
        }
        public void stopThread(boolean running){
            this.running = running;
        }


        @Override
        public void run() {
            while(running){
                Bundle bundle = new Bundle();
                bundle.putInt("value", 1);
                Message msg = handler.obtainMessage();
                msg.setData(bundle);
                handler.sendMessage(msg);

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }//background Thread
}