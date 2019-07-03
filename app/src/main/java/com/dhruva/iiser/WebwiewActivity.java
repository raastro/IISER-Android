package com.dhruva.iiser;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import java.util.HashSet;

public class WebwiewActivity extends Activity {

    private WebView webview;
    private ProgressBar loading;
    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_webwiew);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
        initialize();
        initializeLogic();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onStop() {
        super.onStop();
        webview.clearCache(true);
        finish();
    }

    @SuppressLint({"SetJavaScriptEnabled", "ClickableViewAccessibility"})
    private void initialize() {
        final Activity activity = this;
        final myGestures gestures = new myGestures();
        webview = findViewById(R.id.webview);
        loading = findViewById(R.id.loading);

        if (getSharedPreferences("shared", Activity.MODE_PRIVATE).getBoolean("useCoachMarks", true)) {
            final Dialog coachMarks = new Dialog(this);
            coachMarks.requestWindowFeature(Window.FEATURE_NO_TITLE);
            coachMarks.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            coachMarks.setContentView(R.layout.custom_coach_mark);
            coachMarks.setCanceledOnTouchOutside(true);
            //for dismissing anywhere you touch
            View masterView = coachMarks.findViewById(R.id.coach_mark_master_view);
            masterView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    coachMarks.dismiss();
                }
            });
            coachMarks.show();
        }

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView _param1, String _param2, Bitmap _param3) {
                loading.setVisibility(View.VISIBLE);
                super.onPageStarted(_param1, _param2, _param3);
            }

            @Override
            public void onPageFinished(WebView _param1, String _param2) {
                loading.setVisibility(View.GONE);
                super.onPageFinished(_param1, _param2);
            }

            @Override
            public void onReceivedSslError(WebView view, @NonNull final SslErrorHandler handler, @NonNull SslError error) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                String message = "SSL Certificate error.";
                if (error.getPrimaryError() == SslError.SSL_UNTRUSTED) {
                    message += "The certificate authority is not trusted.";
                } else {
                    handler.cancel();
                    return;
                }
                message += " Do you want to continue anyway?";
                builder.setTitle("SSL Certificate Error");
                builder.setMessage(message);
                builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.proceed();
                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.cancel();
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        webview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                HashSet<Integer> results = gestures.getGestures(event);
                if (results.contains(myGestures.NONE)) {
                    return false; //Touch not consumed
                } else {
                    if (results.contains(myGestures.SWIPE_UP_DOWN))
                        webview.reload();
                    if (results.contains(myGestures.CUT_DOWN))
                        finish();
                    if (results.contains(myGestures.CUT_RIGHT))
                        webview.goForward();
                    if (results.contains(myGestures.CUT_LEFT))
                        webview.goBack();
                    return true; //Touch Consumed
                }
            }
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initializeLogic() {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setDisplayZoomControls(false);
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webview.getSettings().setAppCacheEnabled(false);
        webview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);

        webview.loadUrl(getIntent().getStringExtra("url"));

    }
}
