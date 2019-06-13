package com.dhruva.iiser;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

public class WebwiewActivity extends Activity {


	private WebView webview;
	private ProgressBar loading;

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.webwiew);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	private void initialize(Bundle _savedInstanceState) {

		webview = findViewById(R.id.webview);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setSupportZoom(true);
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
		webview.loadUrl(getIntent().getStringExtra("html"));
	}
}
