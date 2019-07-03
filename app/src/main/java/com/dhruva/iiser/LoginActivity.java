package com.dhruva.iiser;

import android.content.Context;
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
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

//All fingerprint functionality has been copied from https://www.androidauthority.com/how-to-add-fingerprint-authentication-to-your-android-app-747304/


public class LoginActivity extends AppCompatActivity {
    private EditText password;
    @NonNull
    private Intent activityChanger = new Intent();
    private Button signinButton;
    private ImageView fingerprint;
    BiometricPrompt myBiometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;
    private Context context = this;
    SharedPreferences secret = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            secret = EncryptedSharedPreferences.create(
                    "secrets",
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

        if (secret.getBoolean("appsignin", false)) {
            initialize();
            myBiometricPrompt.authenticate(promptInfo);
        } else {
            signin();
        }
    }

    private void initialize() {
        Button emerno = findViewById(R.id.emerno);
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (Objects.equals(secret.getString("pswd", ""), password.getText().toString())) {
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
        emerno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                activityChanger.setAction(Intent.ACTION_VIEW);
                activityChanger.setClass(getApplicationContext(), EmergencyActivity.class);
                startActivity(activityChanger);
            }
        });
    }

    public void signin() {
        activityChanger.setAction(Intent.ACTION_VIEW);
        activityChanger.setClass(getApplicationContext(), MainActivity.class);
        startActivity(activityChanger);
        finish();
    }
}
