package com.daniel.readingbook.book.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.daniel.readingbook.book.Config;
import com.daniel.readingbook.huongmattuakhoisuong.R;
import com.daniel.readingbook.book.database.MyDatabaseHelper;
import com.daniel.readingbook.book.database.table.Book;
import com.startapp.android.publish.SDKAdPreferences;
import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.StartAppSDK;

public class IntroductionActivity extends Activity {
    private static final String TAG = IntroductionActivity.class.getSimpleName();

    private Book mBook;
    private String mContent;
    private WebView mIntroductionContentWebView;
    private Button mReadBookButton;
    private StartAppAd mStartAppAd = new StartAppAd(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addStartApp();
        setContentView(R.layout.activity_introduction);
        StartAppAd.showSlider(this);

        initData();
        setComponentView();
        drawComponentView();
        setReadBookListener();
    }

    @Override
    public void onPause() {
        super.onPause();
        mStartAppAd.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mStartAppAd.onResume();
    }

    private void addStartApp() {
        StartAppSDK.init(
            this,
            Config.App.DEVELOPER_ID,
            Config.App.APP_ID,
            new SDKAdPreferences()
                .setAge(20)
                .setGender(SDKAdPreferences.Gender.FEMALE));
    }

    private void drawComponentView() {
        int textSize = (int) getResources().getDimension(R.dimen.default_text);

        mIntroductionContentWebView.getSettings().setJavaScriptEnabled(false);
        mIntroductionContentWebView.loadDataWithBaseURL(null, mContent, "text/html", "UTF-8", null);
        mIntroductionContentWebView.scrollTo(0, 0);
        mIntroductionContentWebView.getSettings().setDefaultFontSize(textSize);
    }

    private void initData() {
        mBook = MyDatabaseHelper.getMyDatabase(this).getBook();
        mContent = getString(R.string.author) + mBook.getAuthor() + "<br/>" + getString(R.string.type) + mBook.getType() + "<br/><br/><br/>" + mBook.getOverview();
    }

    private void setComponentView() {
        mIntroductionContentWebView = (WebView) findViewById(R.id.introduction_content);
        mReadBookButton = (Button) findViewById(R.id.introduction_read_book);
    }

    private void setReadBookListener() {
        mReadBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntroductionActivity.this, IndexActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}