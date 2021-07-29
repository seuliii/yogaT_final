package org.techtown.example.expandablelistview;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BackPainReliever extends Activity implements View.OnClickListener{

    private ImageView backImg1,backImg2,backImg3,backImg4,backImg5;

    private RelativeLayout relativeLayout1;
    private RelativeLayout relativeLayout2;
    private RelativeLayout relativeLayout3;
    private RelativeLayout relativeLayout4;
    private RelativeLayout relativeLayout5;

    private TextView ex1,ex2,ex3,ex4,ex5;
    private TextView name1,name2,name3,name4,name5;

    private TextView singlePoseTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_pose);

        backImg1 = findViewById(R.id.singlePose1);
        backImg2 = findViewById(R.id.singlePose2);
        backImg3 = findViewById(R.id.singlePose3);
        backImg4 = findViewById(R.id.singlePose4);
        backImg5 = findViewById(R.id.singlePose5);
        relativeLayout1 = (RelativeLayout) findViewById(R.id.rlt_layout_singlePose1);
        relativeLayout2 = (RelativeLayout) findViewById(R.id.rlt_layout_singlePose2);
        relativeLayout3 = (RelativeLayout) findViewById(R.id.rlt_layout_singlePose3);
        relativeLayout4 = (RelativeLayout) findViewById(R.id.rlt_layout_singlePose4);
        relativeLayout5 = (RelativeLayout) findViewById(R.id.rlt_layout_singlePose5);
        ex1 = findViewById(R.id.singlePose_explain1);
        ex2 = findViewById(R.id.singlePose_explain2);
        ex3 = findViewById(R.id.singlePose_explain3);
        ex4 = findViewById(R.id.singlePose_explain4);
        ex5 = findViewById(R.id.singlePose_explain5);
        name1 = findViewById(R.id.singlePose_name1);
        name2 = findViewById(R.id.singlePose_name2);
        name3 = findViewById(R.id.singlePose_name3);
        name4 = findViewById(R.id.singlePose_name4);
        name5 = findViewById(R.id.singlePose_name5);
        singlePoseTitle = findViewById(R.id.singlePoseTitle);


        backImg1.setImageResource(R.drawable.cat);
        backImg2.setImageResource(R.drawable.cobra);
        backImg3.setImageResource(R.drawable.dog);
        backImg4.setImageResource(R.drawable.down_dog);
        backImg5.setImageResource(R.drawable.bridge);


        singlePoseTitle.setText("허리 통증");
        ex1.setText(getString(R.string.cat_benefit));
        ex2.setText(getString(R.string.cobra_benefit));
        ex3.setText(getString(R.string.dog_benefit));
        ex4.setText(getString(R.string.dowDog_benefit));
        ex5.setText(getString(R.string.bridge_benefit));
        
        name1.setText("고양이 자세");
        name2.setText("코브라 자세");
        name3.setText("강아지 자세");
        name4.setText("다운독 자세");
        name5.setText("교각 자세");


        relativeLayout1.setOnClickListener(this);
        relativeLayout2.setOnClickListener(this);
        relativeLayout3.setOnClickListener(this);
        relativeLayout4.setOnClickListener(this);
        relativeLayout5.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //relative layout click listener 주기
        if(v.getId() == R.id.rlt_layout_singlePose1){
            Intent intent = new Intent(BackPainReliever.this, SinglePose.class);
            intent.putExtra("resId", R.drawable.cat);
            intent.putExtra("poseInfo",getString(R.string.cat));
            startActivity(intent);
        }

        else if(v.getId() == R.id.rlt_layout_singlePose2){
            Intent intent = new Intent(BackPainReliever.this, SinglePose.class);
            intent.putExtra("resId", R.drawable.cobra);
            intent.putExtra("poseInfo",getString(R.string.cobra));
            startActivity(intent);
        }
        else if(v.getId() == R.id.rlt_layout_singlePose3){
            Intent intent = new Intent(BackPainReliever.this, SinglePose.class);
            intent.putExtra("resId", R.drawable.dog);
            intent.putExtra("poseInfo",getString(R.string.dog));
            startActivity(intent);
        }
        else if(v.getId() == R.id. rlt_layout_singlePose4){
            Intent intent = new Intent(BackPainReliever.this, SinglePose.class);
            intent.putExtra("resId", R.drawable.down_dog);
            intent.putExtra("poseInfo",getString(R.string.downDog));
            startActivity(intent);
        }
        else if(v.getId() == R.id.rlt_layout_singlePose5){
            Intent intent = new Intent(BackPainReliever.this, SinglePose.class);
            intent.putExtra("resId", R.drawable.bridge);
            intent.putExtra("poseInfo",getString(R.string.bridge));
            startActivity(intent);
        }

    }
}