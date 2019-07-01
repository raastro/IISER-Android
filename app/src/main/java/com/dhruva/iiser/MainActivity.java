package com.dhruva.iiser;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends Activity {


	private String htmlCode = "";
	private Button splcourse;
	private SharedPreferences shared;

	//Two intents otherwise external links don't work as setClass is not be cleared.
	private Intent activityIntent = new Intent();
	private Intent linkIntent = new Intent();

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize();
	}

	private void initialize() {
		//Define view objects
		Button emerno = findViewById(R.id.emerno);
		Button erp = findViewById(R.id.erp);
		Button koha = findViewById(R.id.koha);
		Button jupyter = findViewById(R.id.jupyter);
		Button webmail = findViewById(R.id.webmail);
		Button feedback = findViewById(R.id.feedback);
		Button iisermap = findViewById(R.id.iisermap);
		Button moodle = findViewById(R.id.moodle);
		final Button downloadForms = findViewById(R.id.downloadForms);
		splcourse = findViewById(R.id.splcourse);
		ImageView wifipass = findViewById(R.id.wifipass);
		ImageView drive = findViewById(R.id.drive);
		ImageView mailinfo = findViewById(R.id.mailinfo);
		ImageView settings = findViewById(R.id.settings);
		ImageView upi = findViewById(R.id.upi);
		ImageView manthan = findViewById(R.id.manthan);
		shared = getSharedPreferences("shared", Activity.MODE_PRIVATE);


		//Set onclick listeners
		emerno.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				activityIntent.setAction(Intent.ACTION_VIEW);
				activityIntent.setClass(getApplicationContext(), EmergencyActivity.class);
				startActivity(activityIntent);
			}
		});

		erp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				linkIntent.setAction(Intent.ACTION_VIEW);
				linkIntent.setData(Uri.parse("http://erp.iisermohali.ac.in/login.action"));
				startActivity(linkIntent);
			}
		});

		koha.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				linkIntent.setAction(Intent.ACTION_VIEW);
				linkIntent.setData(Uri.parse("http://library.iisermohali.ac.in/cgi-bin/koha/opac-user.pl"));
				startActivity(linkIntent);
			}
		});

		jupyter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				linkIntent.setAction(Intent.ACTION_VIEW);
				linkIntent.setData(Uri.parse("https://172.16.2.171:7040/hub/login"));
				startActivity(linkIntent);
			}
		});

		webmail.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (shared.getBoolean("useold", false)) {
					//use old
					linkIntent.setAction(Intent.ACTION_VIEW);
					linkIntent.setData(Uri.parse("https://webmail.iisermohali.ac.in/src/login.php"));
					startActivity(linkIntent);
				} else {
					//use new
					linkIntent.setAction(Intent.ACTION_VIEW);
					linkIntent.setData(Uri.parse("https://210.212.36.70"));
					startActivity(linkIntent);
				}
			}
		});

		downloadForms.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				activityIntent.setAction(Intent.ACTION_VIEW);
				activityIntent.setClass(getApplicationContext(), formDownloadActivity.class);
				startActivity(activityIntent);
			}
		});

		feedback.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Toast.makeText(getApplicationContext(), "Under construction", Toast.LENGTH_SHORT).show();
			}
		});

		iisermap.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Toast.makeText(getApplicationContext(), "Under construction", Toast.LENGTH_SHORT).show();
			}
		});

		moodle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (shared.getBoolean("moodlesignin", false)) {
					Toast.makeText(getApplicationContext(),
							"Signing you in, this may take a few moments",
							Toast.LENGTH_SHORT).show();
					htmlCode = getResources().getString(R.string.postData)
							.replace("USRNM", shared.getString("usnm", ""))
							.replace("PSWD", shared.getString("pswd", ""));
					activityIntent.setAction(Intent.ACTION_VIEW);
					activityIntent.setClass(getApplicationContext(), WebwiewActivity.class);
					activityIntent.putExtra("html", htmlCode);
					startActivity(activityIntent);
				} else {
					linkIntent.setAction(Intent.ACTION_VIEW);
					linkIntent.setData(Uri.parse("http://14.139.227.202/moodle/"));
					startActivity(linkIntent);
				}
			}
		});

		splcourse.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				linkIntent.setAction(Intent.ACTION_VIEW);
				linkIntent.setData(Uri.parse("http://14.139.227.202/moodle/course/view.php?id=IDNUM"
						.replace("IDNUMBER", shared.getString("splcourse", ""))));
				startActivity(linkIntent);
			}
		});

		wifipass.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				linkIntent.setAction(Intent.ACTION_VIEW);
				linkIntent.setData(Uri.parse("mailto:vishalkaushik@iisermohali.ac.in?subject="
						+ getResources().getString(R.string.emailMessage)));
				startActivity(linkIntent);
			}
		});


		drive.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				linkIntent.setAction(Intent.ACTION_VIEW);
				linkIntent.setData(Uri.parse("https://drive.google.com/drive/folders/1_zSY8mJgIlGPXUFTQQdyZGaOr1M8WfvK?usp=sharing"));
				startActivity(linkIntent);
			}
		});

		mailinfo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				activityIntent.setAction(Intent.ACTION_VIEW);
				activityIntent.setClass(getApplicationContext(), InfoActivity.class);
				startActivity(activityIntent);
			}
		});

		upi.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				activityIntent.setAction(Intent.ACTION_VIEW);
				activityIntent.setClass(getApplicationContext(), UPIActivity.class);
				startActivity(activityIntent);
			}
		});
		manthan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				linkIntent.setAction(Intent.ACTION_VIEW);
				linkIntent.setData(Uri.parse("https://drive.google.com/open?id=1_MV6gHVLx8mx2K9BePctQI1mlkM93XAX"));
				startActivity(linkIntent);
			}
		});
		settings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				activityIntent.setAction(Intent.ACTION_VIEW);
				activityIntent.setClass(getApplicationContext(), SettingsActivity.class);
				startActivity(activityIntent);
			}
		});
	}


	@Override
	public void onStart() {
		super.onStart();
		if (Objects.equals(shared.getString("splcourse", ""), "")) {
			//No spl course selected
			splcourse.setVisibility(View.GONE);
		} else {
			//Spl Course selected
			splcourse.setText(shared.getString("splcourse", ""));
			splcourse.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onBackPressed() {
		Toast.makeText(getApplicationContext(), "Thanks for using!", Toast.LENGTH_SHORT).show();
		finish();
	}
}
