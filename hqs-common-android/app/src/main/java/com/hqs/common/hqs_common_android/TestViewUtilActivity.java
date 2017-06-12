package com.hqs.common.hqs_common_android;

import android.graphics.RectF;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.hqs.common.utils.Log;
import com.hqs.common.utils.ViewUtil;

public class TestViewUtilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view_util);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        ViewUtil.getViewRect(imageView, new ViewUtil.OnViewRectCallBack() {
            @Override
            public void onRect(RectF rectF) {
                Log.print(rectF );
                Log.print(rectF.width());
                Log.print(rectF.height());
            }
        });

    }

}
