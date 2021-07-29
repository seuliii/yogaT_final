package org.techtown.example.expandablelistview;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity implements ExpandableListView.OnChildClickListener {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    //Expandable list view collapse
    private int lastExpandedPosition = -1;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    //    TextView textView;
    TextView beginner;
    static Context context;
    private TextView editTextTextPersonName;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);

        //서버 연결되면 풀어줘
        Intent intent = getIntent();
        name = intent.getExtras().getString("name");

        editTextTextPersonName.setText(name + "님 환영합니다");



        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.expandable_list_view1);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        //리스트뷰 기본 아이콘 표시 여부
        expListView.setGroupIndicator(null);
        expListView.setOnChildClickListener(this);

        checkPermission();

        //make groupView collapse when another group view is clicked
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if(lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                    expListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("코스 선택");
        listDataHeader.add("효능별 자세");
        listDataHeader.add("내 통계");

        // Adding child data
        List<String> courseSelection = new ArrayList<String>();
        courseSelection.add("앉아서 하는 요가");
        courseSelection.add("서서하는 요가");
        courseSelection.add("프리 모드");

        List<String> singlePose = new ArrayList<String>();
        singlePose.add("허리 통증");
        singlePose.add("혈액 순환");
        singlePose.add("다이어트");


        List<String> myData = new ArrayList<String>();
        myData.add("Top 3");
        myData.add("Worst 3");
        myData.add("Before & After");


        listDataChild.put(listDataHeader.get(0), courseSelection); // Header, Child data
        listDataChild.put(listDataHeader.get(1), singlePose);
        listDataChild.put(listDataHeader.get(2), myData);
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long id) {
        final String selected = (String) listAdapter.getChild(groupPosition, childPosition);

//        Intent intent;

        switch (selected) {
            case "앉아서 하는 요가":

                //대화상자 dialog fragment
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("안내");
                builder.setMessage("앉아서 하는 요가를 실행하시겠습니까?");
                builder.setIcon(android.R.drawable.ic_dialog_alert);

                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String message = "예 버튼이 눌렸습니다. ";
//                        textView.setText(message);
                        Toast.makeText(MainActivity.this,
                                "예 버튼이 눌렀습니다",
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, SittingPose.class);
                        intent.putExtra("name",name);
                        startActivity(intent);
                    }
                });
                builder.setNeutralButton("취소", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        String message = "취소 버튼이 눌렸습니다. ";
//                        textView.setText(message);
                    }
                });
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        String message = "아니오 버튼이 눌렸습니다. ";
//                        textView.setText(message);
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

                break;


            case "서서하는 요가": {
                //대화상자 dialog fragment
                AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);

                builder2.setTitle("안내");
                builder2.setMessage("서서하는 요가를 실행하시겠습니까?");
                builder2.setIcon(android.R.drawable.ic_dialog_alert);

                builder2.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String message = "예 버튼이 눌렸습니다. ";
//                        textView.setText(message);
                        Toast.makeText(MainActivity.this,
                                "예 버튼이 눌렀습니다",
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, StandingPose.class);
                        intent.putExtra("name",name);
                        startActivity(intent);
                    }
                });
                builder2.setNeutralButton("취소", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        String message = "취소 버튼이 눌렸습니다. ";
//                        textView.setText(message);
                    }
                });
                builder2.setNegativeButton("아니오", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        String message = "아니오 버튼이 눌렸습니다. ";
//                        textView.setText(message);
                    }
                });

                AlertDialog dialog2 = builder2.create();
                dialog2.show();

            }
            break;

            case "프리 모드": {
                //대화상자 dialog fragment
                AlertDialog.Builder builder4 = new AlertDialog.Builder(MainActivity.this);

                builder4.setTitle("안내");
                builder4.setMessage("프리 모드를 실행하시겠습니까?");
                builder4.setIcon(android.R.drawable.ic_dialog_alert);

                builder4.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String message = "예 버튼이 눌렸습니다. ";
//                        textView.setText(message);
                        Toast.makeText(MainActivity.this,
                                "예 버튼이 눌렀습니다",
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, FreeMode.class);
                        startActivity(intent);
                    }
                });
                builder4.setNeutralButton("취소", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        String message = "취소 버튼이 눌렸습니다. ";
//                        textView.setText(message);
                    }
                });
                builder4.setNegativeButton("아니오", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        String message = "아니오 버튼이 눌렸습니다. ";
//                        textView.setText(message);
                    }
                });

                AlertDialog dialog4 = builder4.create();
                dialog4.show();

            }
            break;


            case "허리 통증": {

                Intent intent = new Intent(MainActivity.this, BackPainReliever.class);
                startActivity(intent);
            }
            break;


            case "혈액 순환": {
                        Intent intent = new Intent(MainActivity.this, Blood_circulation.class);
                        startActivity(intent);
                    }
            break;


            case "다이어트": {
                //대화상자 dialog fragment

                        Intent intent = new Intent(MainActivity.this, Diet.class);
                        startActivity(intent);
                    }
            break;


            case "Top 3": {
                Intent intent = new Intent(MainActivity.this, Top3.class);
                intent.putExtra("name",name);
                startActivity(intent);
            }
            break;


            case "Worst 3": {
                Intent intent = new Intent(MainActivity.this, Worst3.class);
                intent.putExtra("name",name);
                startActivity(intent);
            }
            break;


            case "Before & After": {
                Intent intent = new Intent(MainActivity.this, BeforeAfter.class);
                intent.putExtra("name",name);
                startActivity(intent);
            }
            break;
        }
        return true;
    }

    private void checkPermission(){
        //권한 허가 (onCreate메소드 안에서 호출해야 함)
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(MainActivity.this, "권한 허가", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "권한 거부\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }

        };

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setRationaleMessage("카메라 권한이 필요합니다")
                .setDeniedMessage("카메라 권한을 거부하셨습니다.\n 본 앱을 이용하실 수 없습니다.")
                .setPermissions(Manifest.permission.CAMERA)
                .check();

    }
}
