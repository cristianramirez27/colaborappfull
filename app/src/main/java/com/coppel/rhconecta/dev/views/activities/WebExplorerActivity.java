package com.coppel.rhconecta.dev.views.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.utils.ShareUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.VISIBLE;
import static com.coppel.rhconecta.dev.business.Configuration.LinksNavigation.KEY_URL;

public class WebExplorerActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = getClass().getSimpleName();

    @BindView(R.id.toolbarWeb)
    View layoutToolbar;
    @BindView(R.id.btn_back)
    ImageView mButtonBack;
    @BindView(R.id.btn_forward)
    ImageView mButtonForward;
    @BindView(R.id.btn_share)
    ImageView mButtonShare;
    @BindView(R.id.web_view)
    WebView mWebView;
    private String url;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_explorer);
        initViews();
        setToolbar();
       if(getIntent().hasExtra(KEY_URL)){
           renderWebPage();
        } else  {
           finish();
       }
    }

    protected void initViews() {
        ButterKnife.bind(this);
        mButtonBack.setOnClickListener(this);
        mButtonForward.setOnClickListener(this);
        mButtonShare.setOnClickListener(this);
    }

    private void setToolbar(){
        toolbar  = (Toolbar) layoutToolbar.findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorBackgroundCoppelBlanco));
        ImageView imgBack = (ImageView) layoutToolbar.findViewById(R.id.imgBack);
        imgBack.setImageResource(R.mipmap.wv_close_icon);
        imgBack.setVisibility(VISIBLE);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    // Custom method to render a web page
    protected void renderWebPage() {
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // Do something on page loading started
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if(mWebView.canGoBack()) {
                    mButtonBack.setEnabled(true);
                    mButtonBack.setImageResource(R.mipmap.wv_left_selected_icon);
                } else {
                    mButtonBack.setEnabled(false);
                    mButtonBack.setImageResource(R.mipmap.wv_left_icon);
                }
                if(mWebView.canGoForward()) {
                    mButtonForward.setEnabled(true);
                    mButtonForward.setImageResource(R.mipmap.wv_right_selected_icon);
                } else {
                    mButtonForward.setEnabled(false);
                    mButtonForward.setImageResource(R.mipmap.wv_righ_icon);
                }
            }

        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int newProgress) {
            }
        });
        // Disable the javascript because can introduce XSS vulnerabilities
        mWebView.getSettings().setJavaScriptEnabled(false);
        url = getIntent().getStringExtra(KEY_URL);
        mWebView.loadUrl(getIntent().getStringExtra(KEY_URL));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.btn_back:
                if (mWebView.canGoBack())
                    mWebView.goBack();
                    break;
            case  R.id.btn_forward:
                if (mWebView.canGoForward())
                    mWebView.goForward();
                break;
            case R.id.btn_share:
                if(url != null && !url.isEmpty())
                    ShareUtil.shareString(this, url);
                break;
        }
    }

    @Override
    public void onBackPressed(){
        if(mWebView.canGoBack()){
            mWebView.goBack();
        }else {
            super.onBackPressed();
        }
    }
}

