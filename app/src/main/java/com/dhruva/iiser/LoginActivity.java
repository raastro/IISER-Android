package com.dhruva.iiser;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.an.biometric.BiometricCallback;
import com.an.biometric.BiometricManager;

//All fingerprint functionality has been copied from https://www.androidauthority.com/how-to-add-fingerprint-authentication-to-your-android-app-747304/


public class LoginActivity extends Activity {
    private EditText password;
    private Intent activityChanger = new Intent();
    private SharedPreferences shared;
    private Button signinButton;
    private ImageView fingerprint;
    private BiometricCallback biometricCallback;
    public LoginActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        biometricCallback = new BiometricCallback() {

            @Override
            public void onSdkVersionNotSupported() {
                // Will be called if the device sdk version does not support Biometric authentication
                Toast.makeText(getApplicationContext(), "You have an old device, or have not updated to latest version", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onBiometricAuthenticationNotSupported() {
                //Will be called if the device does not contain any fingerprint sensors
                Toast.makeText(getApplicationContext(), "You don't have supported hardware!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onBiometricAuthenticationNotAvailable() {
                //The device does not have any biometrics registered in the device.
                Toast.makeText(getApplicationContext(), "The device does not have any biometrics registered in the device.", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onBiometricAuthenticationPermissionNotGranted() {
                //android.permission.USE_BIOMETRIC permission is not granted to the app
                Toast.makeText(getApplicationContext(), "Permission is not granted to the app", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBiometricAuthenticationInternalError(String error) {
                //This method is called if one of the fields such as the title, subtitle, description or the negative button text is empty
            }

            @Override
            public void onAuthenticationFailed() {
                //When the fingerprint doesn't match with any of the fingerprints registered on the device, then this callback will be triggered
                Toast.makeText(getApplicationContext(), "Your phone says - 'I've never met this man before'", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationCancelled() {
                //The authentication is cancelled by the user.
                Toast.makeText(getApplicationContext(), "Sign-in using password", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAuthenticationSuccessful() {
                //When the fingerprint is has been successfully matched with one of the fingerprints registered on the device, then this callback will be triggered.
                signin();
            }

            @Override
            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                //This method is called when a non-fatal error has occurred during the authentication process. The callback will be provided with an help code to identify the cause of the error, along with a help message.
                Toast.makeText(getApplicationContext(), "Developer probably doesn't know what to do, and doesn't care, but report this - " + helpString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                //When an unrecoverable error has been encountered and the authentication process has completed without success, then this callback will be triggered. The callback is provided with an error code to identify the cause of the error, along with the error message.
            }
        };
        shared = getSharedPreferences("shared", Activity.MODE_PRIVATE);
        password = findViewById(R.id.pwentry);
        signinButton = findViewById(R.id.signin);
        fingerprint = findViewById(R.id.fingerprintImage);
        if (shared.getBoolean("appsignin",false)){
            initialize();
            if (Build.VERSION.SDK_INT >= 28) {
                new BiometricManager.BiometricBuilder(this)
                        .setTitle("Sign-in using Biometrics")
                        .setSubtitle("")
                        .setDescription("")
                        .setNegativeButtonText("Cancel")
                        .build()
                        .authenticate(biometricCallback);
            } else {
                Toast.makeText(getApplicationContext(),"You are not running Android Pie. Please wait until this feature comes to you.",Toast.LENGTH_SHORT).show();
            }
        } else {
            signin();
        }
    }
    private void initialize(){
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if(shared.getString("pswd", "").equals(password.getText().toString())){
                    signin();
                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect Password!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        fingerprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (Build.VERSION.SDK_INT >= 28) {
                    new BiometricManager.BiometricBuilder(getApplicationContext())
                            .setTitle("Sign-in using Biometrics")
                            .setSubtitle("")
                            .setDescription("")
                            .setNegativeButtonText("Cancel")
                            .build()
                            .authenticate(biometricCallback);
                } else {
                    Toast.makeText(getApplicationContext(),"You are not running Android Pie. Please wait until the fingerprint feature comes to you.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void signin(){
        activityChanger.setAction(Intent.ACTION_VIEW);
        activityChanger.setClass(getApplicationContext(), MainActivity.class);
        startActivity(activityChanger);
        finish();
    }
}
