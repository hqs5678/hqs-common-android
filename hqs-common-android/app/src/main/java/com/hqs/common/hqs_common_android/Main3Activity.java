package com.hqs.common.hqs_common_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hqs.common.app.AppCallbackProcessor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        getSupportActionBar().setTitle("main3");
    }

    public void buttonClick(View view){

        HashMap<String, Object> map = new HashMap<>();
        map.put("title", "jfdskjfkjkfdjsakjfkdsajfkdsaj");
        AppCallbackProcessor.call(map);
        finish();
    }

}
