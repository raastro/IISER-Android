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
				Toast.makeText(getApplicationContext(), "Under construction", Toast.LENGTH_SHORT).show();
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
					htmlCode = "data:text/html, <html><head> <title>Helper page </title></head><body id=\"page-login-index\" onload=\"click()\"> <script type=\"text/javascript\">function click(){document.getElementById(\"loginbtn\").click();}</script> <div class=\"loginpanel\"> <div class=\"subcontent loginsub\"> <form action=\"http://14.139.227.202/moodle/login/index.php\" method=\"post\" id=\"login\" style = \"display:none;\"> <div class=\"form-input\"> <input type=\"text\" name=\"username\" id=\"username\" size=\"15\" value=\"USRNM\"> <input type=\"password\" name=\"password\" id=\"password\" size=\"15\" value=\"PSWD\"> </div><div class=\"rememberpass\"> <input type=\"checkbox\" name=\"rememberusername\" id=\"rememberusername\" value=\"1\" checked=\"checked\"> <label for=\"rememberusername\">Remember username </label> </div><input type=\"submit\" id=\"loginbtn\" value=\"Log in\"> </form> </div></div></body></html>\n".replace("USRNM", shared.getString("usnm", "")).replace("PSWD", shared.getString("pswd", ""));
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
				i.setData(Uri.parse("mailto:vishalkaushik@iisermohali.ac.in?subject=Request%20for%20new%20wifi%20password&body=Dear%20Sir%2C%0AI%20would%20like%20to%20request%20a%20new%20Wifi%20Password%20for%20my%20new%20device.%0A%0AThank%20You"));
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
				activityChanger.setClass(getApplicationContext(), upiPayment.class);
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
		if (shared.getString("splcourse", "").equals("")) {
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
