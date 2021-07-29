package org.techtown.example.expandablelistview;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Blood_circulation extends Activity implements View.OnClickListener{


    private ImageView bloodImg1,bloodImg2,bloodImg3,bloodImg4,bloodImg5;
    private int resId;
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

        bloodImg1 = findViewById(R.id.singlePose1);
        bloodImg2 = findViewById(R.id.singlePose2);
        bloodImg3 = findViewById(R.id.singlePose3);
        bloodImg4 = findViewById(R.id.singlePose4);
        bloodImg5 = findViewById(R.id.singlePose5);
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



        bloodImg1.setImageResource(R.drawable.bridge);
        bloodImg2.setImageResource(R.drawable.down_dog);
        bloodImg3.setImageResource(R.drawable.pigeon);
        bloodImg4.setImageResource(R.drawable.triangle);
        bloodImg5.setImageResource(R.drawable.king_dancer);

        singlePoseTitle.setText("혈액순환");
        ex1.setText(getString(R.string.bridge_benefit));
        ex2.setText(getString(R.string.dowDog_benefit));
        ex3.setText(getString(R.string.pigeon_benefit));
        ex4.setText(getString(R.string.triangle_benefit));
        ex5.setText(getString(R.string.kingDancer_benefit));

        name1.setText("교각 자세");
        name2.setText("다운독 자세");
        name3.setText("비둘기 자세");
        name4.setText("삼각 자세");
        name5.setText("선활 자세");



        relativeLayout1.setOnClickListener(this);
        relativeLayout2.setOnClickListener(this);
        relativeLayout3.setOnClickListener(this);
        relativeLayout4.setOnClickListener(this);
        relativeLayout5.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.rlt_layout_singlePose1){
            Intent intent = new Intent(Blood_circulation.this, SinglePose.class);
            intent.putExtra("resId", R.drawable.bridge);
            intent.putExtra("poseInfo",getString(R.string.bridge));
            startActivity(intent);
        }else if(v.getId() == R.id.rlt_layout_singlePose2){
            Intent intent = new Intent(Blood_circulation.this, SinglePose.class);
            intent.putExtra("resId", R.drawable.down_dog);
            intent.putExtra("poseInfo",getString(R.string.downDog));
            startActivity(intent);
        }else if(v.getId() == R.id.rlt_layout_singlePose3){
            Intent intent = new Intent(Blood_circulation.this, SinglePose.class);
            intent.putExtra("resId", R.drawable.pigeon);
            intent.putExtra("poseInfo",getString(R.string.pigeon));
            startActivity(intent);
        }else if(v.getId() == R.id.rlt_layout_singlePose4){
            Intent intent = new Intent(Blood_circulation.this, SinglePose.class);
            intent.putExtra("resId", R.drawable.triangle);
            intent.putExtra("poseInfo",getString(R.string.triangle));
            startActivity(intent);
        }else if(v.getId() == R.id.rlt_layout_singlePose5){
            Intent intent = new Intent(Blood_circulation.this, SinglePose.class);
            intent.putExtra("resId", R.drawable.king_dancer);
            intent.putExtra("poseInfo",getString(R.string.kingDancer));
            startActivity(intent);
        }

    }
}