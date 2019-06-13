package com.dhruva.iiser;

import android.os.*;
import android.app.Activity;
import android.widget.CheckBox;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.content.SharedPreferences;
import android.content.Intent;
import android.net.Uri;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
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
		initialize(_savedInstanceState);
		initializeLogic();
	}

	private void initialize(Bundle _savedInstanceState) {

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
				dialog.setMessage("Some courses require more frequent visits than others; hence, we have also provided a shortcut to a specific course. The course ID of your chosen course can be found by opening to the Moodle page of your course and looking for the id in the URL (web address) of the page. For example, if your course URL is\nhttp://14.139.227.202/moodle/enrol/index.php?id=499\nthen the ID is 499.");
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
				dialog.setMessage("This App is an attempt to make life at IISER a tad bit more manageable. It contains the links to useful IISER resources. As these resources involve signing in, it becomes a necessity for me to say that we do not collect or store any data on any server (we don't have any). All password data is stored only on your phone. The app is merely a one-click link to where you want to go.\n\nFeatures of the App-\n\nEmergency Numbers - Under Construction\n\nMoodle - Moodle is a Learning Platform or course management system (CMS). We also provide automatic sign-in.\nSpecial Course - Some courses require more frequent visits than others; hence, we have also provided a shortcut to a specific course. Head to the Settings page to change the ID.\n\nWebmail - The mailing system of IISERM.\n\nERP - ERP is a business process management software that allows us to automate many back-office functions related to technology, services, and human resources.\n\nKoha - The library management system. Here you will find electronic material and other library-related tasks.\n\nFeedback - An essential part of the IISER education system is the feedback session conducted at the end of the Semester. Under Construction.\n\nWiFi password - This allows you to send a pre-drafted email requesting for a password for the Student's WiFi.\n\nBooks Drive Link - A Drive link to almost all the required material for the First Year. Please keep in mind that this is NOT an official repository and may be discontinued in the future.\n\nWebmail Settings - Due to the old setup of the Webmail that IISERM uses, your Email account DOESN'T automatically get added to your Gmail App. So, it takes a little more effort to add it. The following steps help you to do just that.\n\nSettings - Change Settings like Moodle Username and Password.\n\nThis app is under constant development and requires as much help as you can provide it. If you have any bouquets or brickbats or would like to help in the development of the app, do not hesitate to send it to ms18163@iisermohali.ac.in.\n\nBuilt by Dhruva Sambrani.");
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