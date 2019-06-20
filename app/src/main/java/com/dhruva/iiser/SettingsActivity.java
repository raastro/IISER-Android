package com.dhruva.iiser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SettingsActivity extends Activity {


	private CheckBox moodlesignin;
	private CheckBox appsignin;
	private CheckBox oldweb;
	private EditText un;
	private EditText pw;
	private EditText splcourse;

	private SharedPreferences shared;
	private Intent i = new Intent();
	private AlertDialog.Builder dialog;

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.settings);
		initialize();
		initializeLogic();
	}

	private void initialize() {

		appsignin = findViewById(R.id.appsignin);
		moodlesignin = findViewById(R.id.moodlesignin);
		oldweb = findViewById(R.id.oldweb);
		Button updateinfo = findViewById(R.id.updateinfo);
		un = findViewById(R.id.un);
		pw = findViewById(R.id.pw);
		splcourse = findViewById(R.id.splcourse);
		ImageView courseinfo = findViewById(R.id.courseinfo);
		Button appupdate = findViewById(R.id.appupdate);
		ImageView appinfo = findViewById(R.id.appinfo);
		shared = getSharedPreferences("shared", Activity.MODE_PRIVATE);
		dialog = new AlertDialog.Builder(this);

		updateinfo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				shared.edit().putString("pswd", pw.getText().toString()).apply();
				shared.edit().putString("usnm", un.getText().toString()).apply();
				shared.edit().putString("splcourse", splcourse.getText().toString()).apply();
				shared.edit().putBoolean("useold", oldweb.isChecked()).apply();
				shared.edit().putBoolean("moodlesignin", moodlesignin.isChecked()).apply();
				shared.edit().putBoolean("appsignin", appsignin.isChecked()).apply();
				Toast.makeText(getApplicationContext(), "Info Updated!", Toast.LENGTH_SHORT).show();
			}
		});

		courseinfo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				dialog.setMessage(getResources().getString(R.string.moodleSplCourseInfo));
				dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {

					}
				});
				dialog.create().show();
			}
		});

		appupdate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setAction(Intent.ACTION_VIEW);
				i.setData(Uri.parse("https://drive.google.com/drive/folders/1RUnruJILBjbDXUyKMgqoNXoY3RGbgo8i?usp=sharing"));
				startActivity(i);
			}
		});

		appinfo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				dialog.setMessage(getResources().getString(R.string.appInfoString));
				dialog.setPositiveButton("Close", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
					}
				});
				dialog.setNegativeButton("Feedback", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						i.setAction(Intent.ACTION_VIEW);
						i.setData(Uri.parse("mailto:ms18163?subject=IISER%20App%20Feedback"));
						startActivity(i);
					}
				});
				dialog.create().show();
			}
		});
	}

	private void initializeLogic() {
		un.setText(shared.getString("usnm", ""));
		pw.setText(shared.getString("pswd", ""));
		splcourse.setText(shared.getString("splcourse", ""));
		oldweb.setChecked(shared.getBoolean("useold", false));
		moodlesignin.setChecked(shared.getBoolean("moodlesignin", false));
		appsignin.setChecked(shared.getBoolean("appsignin", false));
	}
}