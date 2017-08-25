package com.hqs.common.hqs_common_android;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hqs.common.utils.ScreenUtils;
import com.hqs.common.view.QSViewPager;

public class TestViewPagerActivity extends AppCompatActivity {


    private QSViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view_pager);

        this.viewPager = (QSViewPager) findViewById(R.id.viewPager);

        this.viewPager.setAdapter(new PagerAdapter() {

            Object viewWillDestroy;

            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                if (viewWillDestroy != null && viewWillDestroy instanceof RelativeLayout){
                    container.addView((View) viewWillDestroy);

                    TextView tv = (TextView) ((RelativeLayout) viewWillDestroy).findViewWithTag(viewWillDestroy);
                    tv.setText(position + " - " + (position * ScreenUtils.screenW(getApplicationContext())));


                    Object object = viewWillDestroy;
                    viewWillDestroy = null;
                    return object;
                }
                RelativeLayout relativeLayout = new RelativeLayout(container.getContext());
                relativeLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                relativeLayout.setBackgroundColor(Color.WHITE);

                TextView textView = new TextView(relativeLayout.getContext());
                relativeLayout.addView(textView);

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) textView.getLayoutParams();
                params.addRule(RelativeLayout.CENTER_IN_PARENT);
                textView.setLayoutParams(params);

                textView.setTextSize(30 * ScreenUtils.density(relativeLayout.getContext()));
                textView.setText(position + " - " + (position * ScreenUtils.screenW(getApplicationContext())));

                textView.setTag(relativeLayout);

//                Log.print(relativeLayout);
                container.addView(relativeLayout);
                return relativeLayout;
            }

            @Override
            public int getCount() {
                return 10;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                viewWillDestroy = object;
                container.removeView((View) object);
            }
        });
    }

    public void buttonClick(View view){
        viewPager.scrollBy(100, 0);


    }

}
