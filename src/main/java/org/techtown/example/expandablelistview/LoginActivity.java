package org.techtown.example.expandablelistview;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText et_email, et_pass;
    private Button btn_login, btn_register;
    private TextView emailCheck, pwdCheck;

    private String email;
    private String password;
    private OKHttpConnection okHttpConnection;
    private JSONObject jObject;
    private String responseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //init
        et_email = findViewById( R.id.et_email );
        et_pass = findViewById( R.id.et_pass );
        btn_register = findViewById( R.id.btn_register );
        btn_login = findViewById( R.id.btn_login );
        emailCheck = findViewById(R.id.emailCheck);
        pwdCheck = findViewById(R.id.pwdCheck);

        okHttpConnection = new OKHttpConnection();


        //onClick
        btn_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_register) {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);

        } else if (v.getId() == R.id.btn_login) {
            emailCheck.setText("");
            pwdCheck.setText("");

            email = et_email.getText().toString();
            password = et_pass.getText().toString();

            //email??? password ?????????????????? ?????? json Object??? ????????? ????????? ??????
            jObject = new JSONObject();
            try {
                jObject.put("email", email);
                jObject.put("password", password);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            okHttpConnection.sendToServer_Login(getOKHttpCallback(), jObject);

            //???????????? ?????????????????? ?????? response??? ??????????????? ????????? mainThread??? 0.2?????? ??????
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            if(responseData == null){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                if (responseData.equals("1") || responseData == "1") {

                    emailCheck.setText("???????????? ?????? ??????????????????");
                    Log.d("seul","response 1?????????");
                    //???????????? ???????????? textview??? ??? ????????? ?????? ??? focus??? ?????????
                    et_email.setText("");
                    et_email.requestFocus();
                } else if (responseData.equals("2") || responseData == "2") {
                    pwdCheck.setText("??????????????? ?????? ??????????????????");
                    et_pass.setText("");
                    et_pass.requestFocus();
                } else {
                    //????????? ??????
                    //name??? response????????? ?????????
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("name", responseData);
                    startActivity(intent);
                }
            }
        }
    }

    private Callback getOKHttpCallback() {
        Callback OKHttpCallback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("seul", "Error Message : " + e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseData = response.body().string();
                Log.d("seul", "responseData in OkhttpConnection : " + responseData);

            }
        };
        return OKHttpCallback;
    }

}