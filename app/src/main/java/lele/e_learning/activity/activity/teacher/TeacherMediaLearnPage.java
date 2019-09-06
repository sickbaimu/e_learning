package lele.e_learning.activity.activity.teacher;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import java.lang.reflect.Method;
import java.util.Date;

import lele.e_learning.R;
import lele.e_learning.activity.tools.ClientUser;
import lele.e_learning.activity.tools.HttpCallbackListener;
import lele.e_learning.activity.tools.HttpUtil;

public class TeacherMediaLearnPage extends AppCompatActivity {

    private WebView wvBookPlay;
    private FrameLayout flVideoContainer;
    String url;
    Date beginTime,endTime;
    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);
        beginTime = new Date(System.currentTimeMillis());

        url = getIntent().getStringExtra("item");
        wvBookPlay = findViewById(R.id.wvBookPlay);
        flVideoContainer = findViewById(R.id.flVideoContainer);

        wvBookPlay.getSettings().setJavaScriptEnabled(true);
        wvBookPlay.getSettings().setUseWideViewPort(true);
        wvBookPlay.getSettings().setLoadWithOverviewMode(true);
        wvBookPlay.getSettings().setAllowFileAccess(true);
        wvBookPlay.getSettings().setSupportZoom(true);
        wvBookPlay.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        try {
            if (Build.VERSION.SDK_INT >= 16) {
                Class<?> clazz = wvBookPlay.getSettings().getClass();
                Method method = clazz.getMethod("setAllowUniversalAccessFromFileURLs", boolean.class);
                if (method != null) {
                    method.invoke(wvBookPlay.getSettings(), true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        wvBookPlay.getSettings().setPluginState(WebSettings.PluginState.ON);
        wvBookPlay.getSettings().setDomStorageEnabled(true);// 必须保留，否则无法播放优酷视频，其他的OK
        wvBookPlay.setWebChromeClient(new MyWebChromeClient());// 重写一下，有的时候可能会出现问题

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            wvBookPlay.getSettings().setMixedContentMode(wvBookPlay.getSettings().MIXED_CONTENT_ALWAYS_ALLOW);
        }

        wvBookPlay.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                try{
                    if(url.startsWith("acfun://")||url.startsWith("youku://")||url.startsWith("tudou://")||url.startsWith("bilibili://")||url.startsWith("bilibili://video/ uri")){
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    }
                }catch (Exception e){
                    return false;
                }
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });

        CookieManager cookieManager = CookieManager.getInstance();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("android");

        cookieManager.setCookie(url, stringBuffer.toString());
        cookieManager.setAcceptCookie(true);

        wvBookPlay.loadUrl(url);
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        switch (config.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                break;
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        CustomViewCallback mCallback;
        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            Log.i("ToVmp","onShowCustomView");
            fullScreen();

            wvBookPlay.setVisibility(View.GONE);
            flVideoContainer.setVisibility(View.VISIBLE);
            flVideoContainer.addView(view);
            mCallback = callback;
            super.onShowCustomView(view, callback);
        }

        @Override
        public void onHideCustomView() {
            Log.i("ToVmp","onHideCustomView");
            fullScreen();

            wvBookPlay.setVisibility(View.VISIBLE);
            flVideoContainer.setVisibility(View.GONE);
            flVideoContainer.removeAllViews();
            super.onHideCustomView();

        }
    }

    private void fullScreen() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            Log.i("ToVmp","横屏");
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            Log.i("ToVmp","竖屏");
        }
    }

    @Override
    protected void onDestroy() {
        endTime =  new Date(System.currentTimeMillis());
        long time = endTime.getTime() - beginTime.getTime();
        int point = (int)time/120000;
        HttpUtil.sendHttpRequest("AddPoint?userID="+ ClientUser.getId()+"&point="+point+"&type=MediaTime", new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {

            }

            @Override
            public void onError(Exception e) {

            }
        });
        if (wvBookPlay != null) {
            wvBookPlay.destroy();
        }
        super.onDestroy();
    }

}
