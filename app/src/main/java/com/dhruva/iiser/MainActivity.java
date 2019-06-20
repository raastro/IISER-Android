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
	private Intent activityChanger = new Intent();
	private SharedPreferences shared;
	private Intent i = new Intent();

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize();
		initializeLogic();
	}

	private void initialize() {

		Button emerno = findViewById(R.id.emerno);
		Button erp = findViewById(R.id.erp);
		Button koha = findViewById(R.id.koha);
		Button jupyter = findViewById(R.id.jupyter);
		Button webmail = findViewById(R.id.webmail);
		Button feedback = findViewById(R.id.feedback);
		Button iisermap = findViewById(R.id.iisermap);
		Button moodle = findViewById(R.id.moodle);
		Button downloadForms = findViewById(R.id.downloadForms);
		splcourse = findViewById(R.id.splcourse);
		ImageView wifipass = findViewById(R.id.wifipass);
		ImageView drive = findViewById(R.id.drive);
		ImageView mailinfo = findViewById(R.id.mailinfo);
		ImageView settings = findViewById(R.id.settings);
		ImageView upi = findViewById(R.id.upi);
		shared = getSharedPreferences("shared", Activity.MODE_PRIVATE);

		emerno.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				activityChanger.setAction(Intent.ACTION_VIEW);
				activityChanger.setClass(getApplicationContext(), EmergencyActivity.class);
				startActivity(activityChanger);
			}
		});

		erp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setAction(Intent.ACTION_VIEW);
				i.setData(Uri.parse("http://erp.iisermohali.ac.in/login.action"));
				startActivity(i);
			}
		});

		koha.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setAction(Intent.ACTION_VIEW);
				i.setData(Uri.parse("http://library.iisermohali.ac.in/cgi-bin/koha/opac-user.pl"));
				startActivity(i);
			}
		});

		jupyter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setAction(Intent.ACTION_VIEW);
				i.setData(Uri.parse("https://172.16.2.171:7040/hub/login"));
				startActivity(i);
			}
		});

		webmail.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (shared.getBoolean("useold", false)) {
					i.setAction(Intent.ACTION_VIEW);
					i.setData(Uri.parse("https://webmail.iisermohali.ac.in/src/login.php"));
					startActivity(i);
				} else {
					i.setAction(Intent.ACTION_VIEW);
					i.setData(Uri.parse("https://210.212.36.70"));
					startActivity(i);
				}
			}
		});

		downloadForms.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				activityChanger.setAction(Intent.ACTION_VIEW);
				activityChanger.setClass(getApplicationContext(), formDownloadActivity.class);
				startActivity(activityChanger);
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
					Toast.makeText(getApplicationContext(), "Signing you in, this may take a few moments", Toast.LENGTH_SHORT).show();
					htmlCode = getResources().getString(R.string.postData).replace("USRNM", shared.getString("usnm", "")).replace("PSWD", shared.getString("pswd", ""));
					activityChanger.setAction(Intent.ACTION_VIEW);
					activityChanger.setClass(getApplicationContext(), WebwiewActivity.class);
					activityChanger.putExtra("html", htmlCode);
					startActivity(activityChanger);
				} else {
					i.setAction(Intent.ACTION_VIEW);
					i.setData(Uri.parse("http://14.139.227.202/moodle/"));
					startActivity(i);
				}
			}
		});

		splcourse.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setAction(Intent.ACTION_VIEW);
				i.setData(Uri.parse("http://14.139.227.202/moodle/course/view.php?id=IDNUM".replace("IDNUMBER", shared.getString("splcourse", ""))));
				startActivity(i);
			}
		});

		wifipass.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setAction(Intent.ACTION_VIEW);
				i.setData(Uri.parse("mailto:vishalkaushik@iisermohali.ac.in?subject=" + getResources().getString(R.string.emailMessage)));
				startActivity(i);
			}
		});


		drive.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setAction(Intent.ACTION_VIEW);
				i.setData(Uri.parse("https://drive.google.com/drive/folders/1_zSY8mJgIlGPXUFTQQdyZGaOr1M8WfvK?usp=sharing"));
				startActivity(i);
			}
		});

		mailinfo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setAction(Intent.ACTION_VIEW);
				i.setClass(getApplicationContext(), InfoActivity.class);
				startActivity(i);
			}
		});

		upi.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				activityChanger.setAction(Intent.ACTION_VIEW);
				activityChanger.setClass(getApplicationContext(), UPIActivity.class);
				startActivity(activityChanger);
			}
		});
		settings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				activityChanger.setAction(Intent.ACTION_VIEW);
				activityChanger.setClass(getApplicationContext(), SettingsActivity.class);
				startActivity(activityChanger);
			}
		});
	}

	private void initializeLogic() {
	}

	@Override
	public void onStart() {
		super.onStart();
		if (Objects.equals(shared.getString("splcourse", ""), "")) {
			splcourse.setVisibility(View.GONE);
		} else {
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
