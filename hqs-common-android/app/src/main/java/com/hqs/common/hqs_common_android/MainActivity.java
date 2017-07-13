package com.hqs.common.hqs_common_android;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hqs.common.utils.ActivityUtil;
import com.hqs.common.utils.DensityUtils;
import com.hqs.common.utils.Log;
import com.hqs.common.utils.ViewUtil;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.setActivityFullScreen(this);
        ActivityUtil.hideActionBar(this);
        setContentView(R.layout.activity_main);



        ViewGroup vg = ViewUtil.getRootView(this);
        Log.print(vg.toString());

        final ArrayList<String> titles = new ArrayList<String>();

        titles.add("0 view utils get view position");
        titles.add("1 app call back");

        ListView lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(new BaseAdapter() {


            @Override
            public int getCount() {
                return titles.size();
            }

            @Override
            public Object getItem(int position) {
                return titles.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                RelativeLayout relativeLayout;
                TextView tv;
                if (convertView == null && convertView instanceof RelativeLayout == false) {
                    relativeLayout = new RelativeLayout(MainActivity.this);
                    tv = new TextView(MainActivity.this);


                    int margin = DensityUtils.dp2px(MainActivity.this, 20);

                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                    layoutParams.topMargin = margin;
                    layoutParams.bottomMargin = margin;
                    tv.setLayoutParams(layoutParams);

                    relativeLayout.addView(tv);
                } else {
                    relativeLayout = (RelativeLayout) convertView;
                    tv = (TextView) relativeLayout.getChildAt(0);
                }

                tv.setText(titles.get(position));

                return relativeLayout;
            }
        });





        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(TestViewUtilActivity.class);
                        break;

                    case 1:
                        startActivity(Main1Activity.class);
                        break;




                    default:
                }
            }
        });
    }

    public void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void startActivity(Class cls){
        Intent intent = new Intent(MainActivity.this, cls);
        startActivity(intent);
    }
}
