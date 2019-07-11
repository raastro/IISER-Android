package com.dhruva.iiser;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;


public class Activity_Settings extends Activity {


    private CheckBox serviceSignin;
    private CheckBox appsignin;
    private CheckBox oldweb;
    private CheckBox coach;
    private CheckBox driveHandler;
    private EditText un;
    private EditText epw;
    private EditText apw;
    private EditText mpw;
    private EditText kpw;
    private EditText splcourse;
    private LinearLayout appsigninLayout;
    private LinearLayout serviceSigninLayout;
    private SharedPreferences userPreferences;
    EncryptedSharedPreferences secret = null;
    @NonNull
    private Intent i = new Intent();
    private AlertDialog.Builder dialog;
    private CheckBox getPerm;
    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_settings);
        initialize();
        initializeLogic();
    }

    private void initialize() {

        appsignin = findViewById(R.id.appsignin);
        appsigninLayout = findViewById(R.id.appsigninLayout);
        serviceSignin = findViewById(R.id.serviceSignin);
        serviceSigninLayout = findViewById(R.id.serviceSigninLayout);
        oldweb = findViewById(R.id.oldweb);
        un = findViewById(R.id.un);
        epw = findViewById(R.id.epw);
        apw = findViewById(R.id.apw);
        mpw = findViewById(R.id.mpw);
        kpw = findViewById(R.id.kpw);
        splcourse = findViewById(R.id.splcourse);
        ImageView courseinfo = findViewById(R.id.courseinfo);
        Button appupdate = findViewById(R.id.appupdate);
        ImageView appinfo = findViewById(R.id.appinfo);
        TextView mailInfo = findViewById(R.id.mailinfo);
        coach = findViewById(R.id.useCoachMarks);
        driveHandler = findViewById(R.id.driveHandler);
        userPreferences = getSharedPreferences("userPreferences", Activity.MODE_PRIVATE);
        getPerm = findViewById(R.id.getperm);
        Button issue = findViewById(R.id.issues);
        dialog = new AlertDialog.Builder(this);

        //Get EncryptedSharedPreferences
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED ||
                        !ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE))) {
            getPerm.setVisibility(View.GONE);
        }

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
        appsignin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    appsigninLayout.setVisibility(View.VISIBLE);
                } else {
                    appsigninLayout.setVisibility(View.GONE);
                }
            }
        });
        serviceSignin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    serviceSigninLayout.setVisibility(View.VISIBLE);
                } else {
                    serviceSigninLayout.setVisibility(View.GONE);
                }
            }
        });
        appupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                i.setAction(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://icon_drive.google.com/icon_drive/folders/1RUnruJILBjbDXUyKMgqoNXoY3RGbgo8i?usp=sharing"));
                startActivity(i);
            }
        });

        issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.setAction(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://gitreports.com/issue/DhruvaSambrani/IISER-Android"));
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

        getPerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    Toast.makeText(getApplicationContext(), "You are on an old OS. " +
                            "Go to App activity_settings to grant permission.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mailInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityIntent = new Intent();
                activityIntent.setAction(Intent.ACTION_VIEW);
                activityIntent.setClass(getApplicationContext(), Activity_Info.class);
                startActivity(activityIntent);
            }
        });
    }

    private void initializeLogic() {
        un.setText(secret.getString("usnm", ""));
        mpw.setText(secret.getString("mpw", ""));
        apw.setText(secret.getString("apw", ""));
        epw.setText(secret.getString("epw", ""));
        kpw.setText(secret.getString("kpw", ""));
        splcourse.setText(userPreferences.getString("splcourse", ""));
        oldweb.setChecked(userPreferences.getBoolean("useold", false));
        serviceSignin.setChecked(userPreferences.getBoolean("serviceSignin", false));
        appsignin.setChecked(secret.getBoolean("appsignin", false));
        coach.setChecked(userPreferences.getBoolean("useCoachMarks", true));
        driveHandler.setChecked(userPreferences.getBoolean("driveInApp", true));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Permission was granted, Yay!
            Toast.makeText(getApplicationContext(), "Permission was Granted.", Toast.LENGTH_SHORT).show();
        } else {
            // Permission was not granted!
            getPerm.setChecked(false);
            Toast.makeText(getApplicationContext(), "Permission was not Granted.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Info Updated!", Toast.LENGTH_SHORT).show();

        userPreferences.edit().putString("splcourse", splcourse.getText().toString()).apply();
        userPreferences.edit().putBoolean("useold", oldweb.isChecked()).apply();
        userPreferences.edit().putBoolean("serviceSignin", serviceSignin.isChecked()).apply();
        userPreferences.edit().putBoolean("useCoachMarks", coach.isChecked()).apply();
        userPreferences.edit().putBoolean("driveInApp", driveHandler.isChecked()).apply();

        secret.edit().putString("usnm", un.getText().toString()).apply();
        secret.edit().putString("mpw", mpw.getText().toString()).apply();
        secret.edit().putString("apw", apw.getText().toString()).apply();
        secret.edit().putString("epw", epw.getText().toString()).apply();
        secret.edit().putString("kpw", kpw.getText().toString()).apply();
        secret.edit().putBoolean("appsignin", appsignin.isChecked()).apply();
        finish();
    }
}