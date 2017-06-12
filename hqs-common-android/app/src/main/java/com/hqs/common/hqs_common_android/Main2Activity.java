package com.hqs.common.hqs_common_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getSupportActionBar().setTitle("main2");
    }

    public void buttonClick(View view){
        Intent intent = new Intent(this, Main3Activity.class);
        startActivity(intent);
        finish();
    }
}
