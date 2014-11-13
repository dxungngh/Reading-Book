package com.daniel.readingbook.batramsaunamngayhonnhan.widget;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.daniel.readingbook.batramsaunamngayhonnhan.R;

/**
 * Created by daniel_nguyen on 6/26/14.
 */
public class ErrorDialog extends Dialog {
    private static ErrorDialog mErrorDialog;
    private Button mCloseButton;
    private String mCloseButtonTitle;
    private View.OnClickListener mListener;
    private String mTitle, mMessage;
    private TextView mMessageTextView, mTitleTextView;

    private static final String TAG = ErrorDialog.class.getSimpleName();

    public static ErrorDialog getInstance(Activity activity, String title, String message,
                                          String closeButtonTitle, View.OnClickListener listener) {
        if (mErrorDialog == null) {
            mErrorDialog = new ErrorDialog(activity, title, message, closeButtonTitle, listener);
        }
        return mErrorDialog;
    }

    private ErrorDialog(Activity activity, String title, String message, String closeButtonTitle,
                        View.OnClickListener listener) {
        super(activity);
        mCloseButtonTitle = closeButtonTitle;
        mMessage = message;
        mTitle = title;
        mListener = listener;
    }

    private void drawComponentView() {
        try {
            setCancelable(true);
            Log.i(TAG, mMessage);
            mMessageTextView.setText(Html.fromHtml(mMessage));
            if (!TextUtils.isEmpty(mTitle)) {
                mTitleTextView.setText(mTitle);
                mTitleTextView.setVisibility(View.VISIBLE);
            } else {
                mTitleTextView.setVisibility(View.GONE);
            }
            mCloseButton.setText(mCloseButtonTitle);
        } catch (Exception e) {
            Log.e(TAG, "drawComponentView", e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_error);
        setComponentView();
        drawComponentView();
        setCloseListener();
    }

    @Override
    protected void onStop() {
        mErrorDialog = null;
    }

    private void setCloseListener() {
        mCloseButton.setOnClickListener(mListener);
    }

    private void setComponentView() {
        mTitleTextView = (TextView) findViewById(R.id.dialog_error_title);
        mMessageTextView = (TextView) findViewById(R.id.dialog_error_message);
        mCloseButton = (Button) findViewById(R.id.dialog_error_close);
    }
}