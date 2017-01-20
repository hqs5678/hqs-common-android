package com.hqs.common.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hqs.common.R;
import com.hqs.common.utils.ViewUtil;

import java.lang.ref.WeakReference;

/**
 * Created by apple on 2016/10/27.
 */

public class QDialog {

    private Context context;
    private RelativeLayout contentView;
    private OnDialogClickListener dialogClickListener;

    public Button leftButton;
    public Button rightButton;
    public TextView tvMessage;
    private TextView tvDivider0;
    private TextView tvDivider1;

    private boolean cancelable;
    private WeakReference<Activity> dialogActivity;

    public QDialog(Context context) {
        this.context = context;
        setup();
    }


    private void setup() {

        LayoutInflater inflater = LayoutInflater.from(context);
        contentView = (RelativeLayout) inflater.inflate(R.layout.dialog_cancelable, null);

        leftButton = (Button) contentView.findViewById(R.id.btn_left);
        rightButton = (Button) contentView.findViewById(R.id.btn_right);
        tvMessage = (TextView) contentView.findViewById(R.id.tv_message);
        tvDivider1 = (TextView) contentView.findViewById(R.id.tv_divider1);
        tvDivider0 = (TextView) contentView.findViewById(R.id.tv_divider);

        ViewUtil.setRoundCornerToView(leftButton, 0, Color.GRAY, Color.WHITE);
        ViewUtil.setRoundCornerToView(rightButton, 0, Color.GRAY, Color.WHITE);


    }

    public void setSingleButtonMode() {
        leftButton.setVisibility(View.GONE);
        tvDivider1.setVisibility(View.GONE);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rightButton.getLayoutParams();
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        rightButton.setLayoutParams(layoutParams);
    }

    public void show(String message, OnDialogClickListener onDialogClickListener) {
        tvMessage.setText(message);
        dialogClickListener = onDialogClickListener;

        Intent intent = new Intent(context, DialogActivity.class);
        context.startActivity(intent);
    }

    public void dismiss() {
        if (dialogActivity != null){
            Activity activity = dialogActivity.get();
            if (activity != null){
                activity.finish();
                if (dialogClickListener != null) {
                    dialogClickListener.onCancel();
                }
                dialogActivity = null;
            }
        }
    }

    public OnDialogClickListener getOnDialogClickListener() {
        return dialogClickListener;
    }

    public void setDividerHeight(int h) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tvDivider0.getLayoutParams();
        params.height = h;
        tvDivider0.setLayoutParams(params);

        params = (RelativeLayout.LayoutParams) tvDivider1.getLayoutParams();
        params.width = h;
        tvDivider1.setLayoutParams(params);
    }

    public void setDividerColor(int color) {
        tvDivider0.setBackgroundColor(color);
        tvDivider1.setBackgroundColor(color);
    }

    public interface OnDialogClickListener {
        void onClickRightButton();
        void onClickLeftButton();
        void onCancel();
    }

    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    public class DialogActivity extends Activity {


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            dialogActivity = new WeakReference<Activity>(this);

            RelativeLayout relativeLayout = new RelativeLayout(this);
            this.setContentView(relativeLayout);

            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cancelable) {
                        if (dialogClickListener != null) {
                            dialogClickListener.onCancel();
                        }
                        finish();
                    }
                }
            });

            relativeLayout.addView(contentView);

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            contentView.setLayoutParams(layoutParams);


            leftButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialogClickListener != null) {
                        dialogClickListener.onClickLeftButton();
                    }
                    finish();
                }
            });
            rightButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialogClickListener != null) {
                        dialogClickListener.onClickRightButton();
                    }
                    finish();
                }
            });
        }
    }
}
