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

import androidx.annotation.NonNull;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Objects;

public class MainActivity extends Activity {


    @NonNull
    private String htmlCode = "";
    private Button splcourse;
    private EncryptedSharedPreferences secret;
    private SharedPreferences userPreferences;

    @NonNull
    private Intent activityIntent = new Intent();

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        //Define view objects
        Button emerno = findViewById(R.id.emerno);
        Button erp = findViewById(R.id.erp);
        Button koha = findViewById(R.id.koha);
        Button jupyter = findViewById(R.id.jupyter);
        final Button webmail = findViewById(R.id.webmail);
        Button feedback = findViewById(R.id.feedback);
        Button iisermap = findViewById(R.id.iisermap);
        Button moodle = findViewById(R.id.moodle);
        final Button downloadForms = findViewById(R.id.downloadForms);
        splcourse = findViewById(R.id.splcourse);
        ImageView wifipass = findViewById(R.id.wifipass);
        ImageView drive = findViewById(R.id.drive);
        ImageView settings = findViewById(R.id.settings);
        ImageView upi = findViewById(R.id.upi);
        ImageView manthan = findViewById(R.id.manthan);
        userPreferences = getSharedPreferences("userPreferences", Activity.MODE_PRIVATE);
        try {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            secret = (EncryptedSharedPreferences) EncryptedSharedPreferences.create(
                    "secrets",
                    masterKeyAlias,
                    this,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


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
                if (userPreferences.getBoolean("serviceSignin", false)) {
                    activityIntent.setAction(Intent.ACTION_VIEW);
                    activityIntent.setClass(getApplicationContext(), webwiewActivity.class);
                    activityIntent.putExtra("url", "http://erp.iisermohali.ac.in/login.action?" +
                            "appUser.userId=" + secret.getString("usnm", "") +
                            "&appUser.passwd=" + secret.getString("pswd", ""));
                    startActivity(activityIntent);
                } else {
                    activityIntent.setAction(Intent.ACTION_VIEW);
                    activityIntent.setClass(getApplicationContext(), webwiewActivity.class);
                    activityIntent.putExtra("url", "http://erp.iisermohali.ac.in/login.action");
                    startActivity(activityIntent);
                }
            }
        });

        koha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (userPreferences.getBoolean("serviceSignin", false)) {
                    activityIntent.setAction(Intent.ACTION_VIEW);
                    activityIntent.setClass(getApplicationContext(), webwiewActivity.class);
                    activityIntent.putExtra("url",
                            "http://library.iisermohali.ac.in/cgi-bin/koha/opac-user.pl/?" +
                                    "koha_login_context=opac" +
                                    "&userid=" + secret.getString("usnm", "") +
                                    "&password=" + secret.getString("pswd", ""));
                    startActivity(activityIntent);
                } else {
                    activityIntent.setAction(Intent.ACTION_VIEW);
                    activityIntent.setClass(getApplicationContext(), webwiewActivity.class);
                    activityIntent.putExtra("url",
                            "http://library.iisermohali.ac.in/cgi-bin/koha/opac-user.pl");
                    startActivity(activityIntent);
                }
            }
        });

        jupyter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (userPreferences.getBoolean("serviceSignin", false)) {
                    Toast.makeText(getApplicationContext(),
                            "Signing you in, this may take a few moments",
                            Toast.LENGTH_SHORT).show();
                    htmlCode = getResources().getString(R.string.jupyterPostData)
                            .replace("USRNM",
                                    Objects.requireNonNull(secret.getString("usnm", "")))
                            .replace("PSWD",
                                    Objects.requireNonNull(secret.getString("pswd", "")));
                    activityIntent.setAction(Intent.ACTION_VIEW);
                    activityIntent.setClass(getApplicationContext(), webwiewActivity.class);
                    activityIntent.putExtra("url", htmlCode);
                    startActivity(activityIntent);
                } else {
                    activityIntent.setAction(Intent.ACTION_VIEW);
                    activityIntent.setClass(getApplicationContext(), webwiewActivity.class);
                    activityIntent.putExtra("url",
                            "https://172.16.2.171:7040/hub/login");
                    startActivity(activityIntent);
                }
            }
        });

        webmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (userPreferences.getBoolean("useold", false)) {
                    //use old
                    activityIntent.setAction(Intent.ACTION_VIEW);
                    activityIntent.setClass(getApplicationContext(), webwiewActivity.class);
                    activityIntent.putExtra("url", "https://webmail.iisermohali.ac.in/src/login.php");
                    startActivity(activityIntent);
                } else {
                    //use new
                    activityIntent.setAction(Intent.ACTION_VIEW);
                    activityIntent.setClass(getApplicationContext(), webwiewActivity.class);
                    activityIntent.putExtra("url", "https://210.212.36.70");
                    startActivity(activityIntent);
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
                if (userPreferences.getBoolean("serviceSignin", false)) {
                    Toast.makeText(getApplicationContext(),
                            "Signing you in, this may take a few moments",
                            Toast.LENGTH_SHORT).show();
                    htmlCode = getResources().getString(R.string.moodlePostData)
                            .replace("USRNM",
                                    Objects.requireNonNull(secret.getString("usnm", "")))
                            .replace("PSWD",
                                    Objects.requireNonNull(secret.getString("pswd", "")));
                    activityIntent.setAction(Intent.ACTION_VIEW);
                    activityIntent.setClass(getApplicationContext(), webwiewActivity.class);
                    activityIntent.putExtra("url", htmlCode);
                    startActivity(activityIntent);
                } else {
                    activityIntent.setAction(Intent.ACTION_VIEW);
                    activityIntent.setClass(getApplicationContext(), webwiewActivity.class);
                    activityIntent.putExtra("url", "http://14.139.227.202/moodle/");
                    startActivity(activityIntent);
                }
            }
        });

        splcourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                activityIntent.setAction(Intent.ACTION_VIEW);
                activityIntent.setClass(getApplicationContext(), webwiewActivity.class);
                activityIntent.putExtra("url", "http://14.139.227.202/moodle/course/view.php?id=IDNUM"
                        .replace("IDNUMBER", Objects.requireNonNull(userPreferences.getString("splcourse", ""))));
                startActivity(activityIntent);
            }
        });

        wifipass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                //can't use webview
                Intent mail = new Intent(Intent.ACTION_VIEW);
                mail.setData(
                        Uri.parse("mailto:vishalkaushik@iisermohali.ac.in?subject="
                                + getResources().getString(R.string.emailMessage)));
                startActivity(mail);
            }
        });


        drive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (userPreferences.getBoolean("driveInApp", true)) {
                    activityIntent.setAction(Intent.ACTION_VIEW);
                    activityIntent.setClass(getApplicationContext(), webwiewActivity.class);
                    activityIntent.putExtra("url", getResources().getString(R.string.driveLink));
                    startActivity(activityIntent);
                } else {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(getResources().getString(R.string.driveLink)));
                    startActivity(i);
                }
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
                if (userPreferences.getBoolean("driveInApp", true)) {
                    activityIntent.setAction(Intent.ACTION_VIEW);
                    activityIntent.setClass(getApplicationContext(), webwiewActivity.class);
                    activityIntent.putExtra("url", getResources().getString(R.string.manthanLink));
                    startActivity(activityIntent);
                } else {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(getResources().getString(R.string.manthanLink)));
                    startActivity(i);
                }

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
        if (Objects.equals(userPreferences.getString("splcourse", ""), "")) {
            //No spl course selected
            splcourse.setVisibility(View.GONE);
        } else {
            //Spl Course selected
            splcourse.setText(userPreferences.getString("splcourse", ""));
            splcourse.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Thanks for using!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
