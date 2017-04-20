package com.hqs.common.hqs_common_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hqs.common.app.AppCallbackProcessor;
import com.hqs.common.utils.Log;

import java.util.HashMap;
import java.util.Map;

public class Main1Activity extends AppCompatActivity {


    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        getSupportActionBar().setTitle("main1");

        tv = (TextView) findViewById(R.id.text);
    }

    public void buttonClick(View view){

        AppCallbackProcessor.addCallback(new AppCallbackProcessor.AppCallback() {
            @Override
            public void call(Map<String, Object> params) {
                tv.setText(params.get("title").toString());
            }

            @Override
            public void call(Map<String, Object> params, String callbackId) {
                
            }
        });
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
//        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.print("main1 onDestroy");
    }
}
