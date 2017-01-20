package com.hqs.common.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hqs.R;
import com.hqs.common.utils.ViewUtil;

/**
 * Created by apple on 2016/10/27.
 */

public class DialogView extends RelativeLayout {

    private Context context;
    private RelativeLayout contentView;
    private OnDialogClickListener dialogClickListener;

    public Dialog dialog;
    public Button leftButton;
    public Button rightButton;
    public TextView tvMessage;
    private TextView tvDivider;

    public DialogView(Context context) {
        super(context);
        this.context = context;
        setup();
    }


    private void setup(){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog);
        }
        else{
            builder = new AlertDialog.Builder(context);
        }

        LayoutInflater inflater = LayoutInflater.from(context);
        contentView = (RelativeLayout) inflater.inflate(R.layout.dialog_cancelable, null);
        builder.setView(contentView);
        builder.setCancelable(false);

        leftButton = (Button) contentView.findViewById(R.id.btn_left);
        rightButton = (Button) contentView.findViewById(R.id.btn_right);
        tvMessage = (TextView) contentView.findViewById(R.id.tv_message);
        tvDivider = (TextView) contentView.findViewById(R.id.tv_divider1);

        ViewUtil.setRoundCornerToView(leftButton, 0, Color.GRAY, Color.WHITE);
        ViewUtil.setRoundCornerToView(rightButton, 0, Color.GRAY, Color.WHITE);

        dialog = builder.create();
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogClickListener != null){
                    dialogClickListener.onClickLeftButton();
                }
                dialog.dismiss();
            }
        });
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogClickListener != null){
                    dialogClickListener.onClickRightButton();
                }
                dialog.dismiss();
            }
        });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if (dialogClickListener != null){
                    dialogClickListener.onClickLeftButton();
                }
            }
        });
    }

    public void setSingleButtonMode(){
        leftButton.setVisibility(View.GONE);
        tvDivider.setVisibility(View.GONE);

        LayoutParams layoutParams = (LayoutParams) rightButton.getLayoutParams();
        layoutParams.addRule(ALIGN_PARENT_LEFT, TRUE);
        rightButton.setLayoutParams(layoutParams);
    }

    public void show(String message, OnDialogClickListener onDialogClickListener){
        if(dialog != null){
            tvMessage.setText(message);
            dialogClickListener = onDialogClickListener;
            dialog.show();
        }
    }

    public void dismiss(){
        if (dialog != null){
            dialog.dismiss();
            dialog = null;
        }
    }

    public OnDialogClickListener getOnDialogClickListener() {
        return dialogClickListener;
    }

    public interface OnDialogClickListener{
        void onClickRightButton();
        void onClickLeftButton();
    }

    public void setCancelable(boolean cancelable) {
        if (dialog != null) {
            dialog.setCancelable(cancelable);
        }
    }
}
