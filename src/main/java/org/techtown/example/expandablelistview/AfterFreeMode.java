package org.techtown.example.expandablelistview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AfterFreeMode extends Activity {
    private TextView afm_txtV1, afm_txtV2, afm_txtV3;
    private TextView afm_per1,afm_per2,afm_per3;
    private OKHttpConnection okHttpConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.after_free_mode);

        afm_txtV1 = findViewById(R.id.afm_pose1);
        afm_txtV2 = findViewById(R.id.afm_pose2);
        afm_txtV3 = findViewById(R.id.afm_pose3);

        afm_per1 = findViewById(R.id.afm_percentage1);
        afm_per2 = findViewById(R.id.afm_percentage2);
        afm_per3 = findViewById(R.id.afm_percentage3);

        setData();

    }

    public void setData(){
/*
        List<String> score_list = new ArrayList<>();
        List<String> pose_list = new ArrayList<>();

        score_list = OKHttpConnection.score_list;
        pose_list = OKHttpConnection.pose_list;

*/
        afm_per1.setText("warrior");
        afm_per2.setText("cow");
        afm_per3.setText("cobra");

        afm_txtV1.setText("95.30%");
        afm_txtV2.setText("94.41%");
        afm_txtV3.setText("98.83%");

    }
}