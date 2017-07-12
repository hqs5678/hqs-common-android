package com.hqs.common.view;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

/**
 * Created by super on 2017/7/3.
 */

public class SelectableTextView extends AppCompatEditText {

    public SelectableTextView(Context context) {
        super(context);
        initView();
    }

    public SelectableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public void initView(){
        this.setBackgroundDrawable(null);
        this.setCursorVisible(false);
        this.setTextIsSelectable(true);

    }

    @Override
    protected boolean getDefaultEditable() {
        return false;
    }
}
