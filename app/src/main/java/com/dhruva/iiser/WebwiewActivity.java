package com.dhruva.iiser;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public class WebwiewActivity extends Activity {


    private WebView webview;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.webwiew);
        initialize();
        initializeLogic();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initialize() {
        final Activity activity = this;
        webview = findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setDisplayZoomControls(false);
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webview.getSettings().setAppCacheEnabled(false);
        webview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);

        Button back = findViewById(R.id.back);
        loading = findViewById(R.id.loading);
        Button forward = findViewById(R.id.forward);

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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                webview.goBack();
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                webview.goForward();
            }
        });
    }

    private void initializeLogic() {
        webview.loadUrl(getIntent().getStringExtra("url"));
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
}
