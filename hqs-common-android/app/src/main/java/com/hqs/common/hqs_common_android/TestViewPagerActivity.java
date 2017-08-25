package com.hqs.common.hqs_common_android;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hqs.common.utils.ColorUtil;
import com.hqs.common.utils.Log;
import com.hqs.common.view.QSViewPager;

import org.w3c.dom.Text;

public class TestViewPagerActivity extends AppCompatActivity {


    private QSViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view_pager);

        this.viewPager = (QSViewPager) findViewById(R.id.viewPager);

        this.viewPager.setAdapter(new PagerAdapter() {


            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                RelativeLayout relativeLayout = new RelativeLayout(container.getContext());
                relativeLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                relativeLayout.setBackgroundColor(ColorUtil.randomColor());

//                TextView textView = new TextView(relativeLayout.getContext());
//                relativeLayout.addView(textView);
//
//                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) textView.getLayoutParams();
//                params.addRule(RelativeLayout.CENTER_IN_PARENT);
//                textView.setLayoutParams(params);

                Log.print(relativeLayout);
                container.addView(relativeLayout);
                return relativeLayout;
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {

            }
        });
    }
}
