package com.bzt.inputview.bottominputview;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/************************************************************************
 *@Project: InputView
 *@Package_Name: com.bzt.inputview.inputview
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2018/2/10 
 *@Copyright:(C)2018 苏州百智通信息技术有限公司. All rights reserved. 
 *************************************************************************/
public class InputView extends LinearLayout {
    private static final int TEXT_SIZE = 16;
    private static final int BTN_SIZE = 16;

    private Context mContext;
    private OnInputCallBack callBack;
    private TypedArray typedArray;

    /*---------------属性---------------*/
    private int defTextColor = Color.rgb(51, 51, 51);
    //    private int defHintColor = Color.rgb(51, 51, 51);
    private int defBtnColor = Color.rgb(51, 51, 51);
    private int defBtnDefaultColor = Color.rgb(137, 137, 137);
    private int defInputBackgroundColor = Color.rgb(255, 255, 255);
    private String hint;

    private TextView tvContent;
    private TextView tvSend;
    /*---------------输入部分---------------*/
    private RelativeLayout rlComment;
    private View vTransparent;
    private EditText etContent;
    private TextView tvSendPop;
    private int textColor;
    private int btnColor;
    private int btnDefaultColor;
    private int inputBgColor;
    private int textSize;
    private int btnSize;


    public InputView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.input);

        LayoutInflater.from(context).inflate(R.layout.input_view, this);
        initView();
        initEvent();
    }

    private void initView() {
        tvContent = findViewById(R.id.tv_content);
        tvSend = findViewById(R.id.tv_send);
        /*---------------输入部分---------------*/
        rlComment = findViewById(R.id.rl_comment);
        vTransparent = findViewById(R.id.v_transparent);
        etContent = findViewById(R.id.et_content);
        tvSendPop = findViewById(R.id.tv_send_pop);

        hint = typedArray.getString(R.styleable.input_hint);
        textColor = typedArray.getColor(R.styleable.input_textColor, defTextColor);
        btnColor = typedArray.getColor(R.styleable.input_btnColor, defBtnColor);
        btnDefaultColor = typedArray.getColor(R.styleable.input_btnDefaultColor, defBtnDefaultColor);
        inputBgColor = typedArray.getColor(R.styleable.input_inputBackgroundColor, defInputBackgroundColor);
//        textSize = (int)typedArray.getDimension(R.styleable.input_textSize, TEXT_SIZE);
//        btnSize = (int)typedArray.getDimension(R.styleable.input_btnSize, BTN_SIZE);

        tvContent.setHint(hint);
        tvContent.setTextColor(textColor);
//        tvContent.setTextSize(textSize);
        etContent.setHint(hint);
        etContent.setTextColor(textColor);
//        etContent.setTextSize(textSize);
        tvSend.setTextColor(btnDefaultColor);
//        tvSend.setTextSize(btnSize);
        tvSendPop.setTextColor(btnDefaultColor);
//        tvSendPop.setTextSize(btnSize);


        ((Activity) mContext).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        etContent.clearFocus();
    }

    private void initEvent() {
        tvContent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                updateEditTextBodyVisible(View.VISIBLE);
            }
        });

        vTransparent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                updateEditTextBodyVisible(View.GONE);
            }
        });

        tvSend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = etContent.getText().toString();
                if (!TextUtils.isEmpty(msg)) {
                    callBack.onInput(msg);
                }
            }
        });

        tvSendPop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = etContent.getText().toString();
                if (!TextUtils.isEmpty(msg)) {
                    callBack.onInput(msg);
                }
            }
        });

        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(etContent.getText())) {
                    tvSendPop.setTextColor(btnColor);
                    tvSend.setTextColor(btnColor);
                } else {
                    tvSendPop.setTextColor(btnDefaultColor);
                    tvSend.setTextColor(btnDefaultColor);
                }

                tvContent.setText(etContent.getText());

            }
        });
    }

    //控制评论框显隐
    private void updateEditTextBodyVisible(int visibility) {
        rlComment.setVisibility(visibility);
        vTransparent.setVisibility(visibility);

        if (View.VISIBLE == visibility) {
            etContent.requestFocus();
            //弹出键盘
            showSoftInput(etContent.getContext(), etContent);

        } else if (View.GONE == visibility) {
            //隐藏键盘
            hideSoftInput(etContent.getContext(), etContent);
        }
    }

    public static void showSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        //imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    public static void hideSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }

    public OnInputCallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(OnInputCallBack callBack) {
        this.callBack = callBack;
    }

    public interface OnInputCallBack {
        void onInput(String content);
    }


}
