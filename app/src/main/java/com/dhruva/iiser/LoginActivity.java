package com.dhruva.iiser;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.fragment.app.FragmentActivity;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

//All fingerprint functionality has been copied from https://www.androidauthority.com/how-to-add-fingerprint-authentication-to-your-android-app-747304/


public class LoginActivity extends AppCompatActivity {
    private EditText password;
    private Intent activityChanger = new Intent();
    private SharedPreferences shared;
    private Button signinButton;
    private ImageView fingerprint;
    BiometricPrompt myBiometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        shared = getSharedPreferences("shared", Activity.MODE_PRIVATE);
        password = findViewById(R.id.pwentry);
        signinButton = findViewById(R.id.signin);
        fingerprint = findViewById(R.id.fingerprintImage);
        final FragmentActivity activity = this;

        //Create a thread pool with a single thread//
        Executor newExecutor = Executors.newSingleThreadExecutor();

        //Start listening for authentication events//
        myBiometricPrompt = new BiometricPrompt(activity, newExecutor, new BiometricPrompt.AuthenticationCallback() {

            @Override
            //onAuthenticationError is called when a fatal error occurs//
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            //onAuthenticationSucceeded is called when a fingerprint is matched successfully//
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                signin();
            }

            //onAuthenticationFailed is called when the fingerprint does not match//
            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Fingerprint Authentication")
                .setSubtitle("Sign-in by touching your sensor")
                .setDescription("")
                .setNegativeButtonText("Cancel")
                .build();

        if (shared.getBoolean("appsignin",false)){
            initialize();
            myBiometricPrompt.authenticate(promptInfo);
        } else {
            signin();
        }
    }
    private void initialize(){
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if(Objects.equals(shared.getString("pswd", ""), password.getText().toString())){
                    signin();
                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect Password!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        fingerprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                myBiometricPrompt.authenticate(promptInfo);
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
