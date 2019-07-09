package com.dhruva.iiser;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.core.content.ContextCompat;

public class Activity_Emergency extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
    }


    public void callBrijesh(View v) {
        Uri number = Uri.parse("tel:9779876456");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            callIntent = new Intent(Intent.ACTION_CALL, number);
        }
        startActivity(callIntent);
    }

    public void callSantosh(View v) {
        Uri number = Uri.parse("tel:9888152230");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            callIntent = new Intent(Intent.ACTION_CALL, number);
        }
        startActivity(callIntent);
    }

    public void callSatinder(View v) {
        Uri number = Uri.parse("tel:9417237476");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            callIntent = new Intent(Intent.ACTION_CALL, number);
        }
        startActivity(callIntent);
    }

    public void callManoj(View v) {
        Uri number = Uri.parse("tel:9872582757");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            callIntent = new Intent(Intent.ACTION_CALL, number);
        }
        startActivity(callIntent);
    }

    public void callKalpana(View v) {
        Uri number = Uri.parse("tel:9814408329");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            callIntent = new Intent(Intent.ACTION_CALL, number);
        }
        startActivity(callIntent);
    }

    public void emailBrijesh(View v) {
        Uri email = Uri.parse("mailto: brjeshbajpai@iisermohali.ac.in");
        Intent emailIntent = new Intent(Intent.ACTION_VIEW, email);
        startActivity(emailIntent);
    }

    public void emailSantosh(View v) {
        Uri email = Uri.parse("mailto:Santosh@iisermohali.ac.in");
        Intent emailIntent = new Intent(Intent.ACTION_VIEW, email);
        startActivity(emailIntent);
    }

    public void emailManoj(View v) {
        Uri email = Uri.parse("mailto:manojmaurya@iisermohali.ac.in");
        Intent emailIntent = new Intent(Intent.ACTION_VIEW, email);
        startActivity(emailIntent);
    }

    public void emailSatinder(View v) {
        Uri email = Uri.parse("mailto:satinder@iisermohali.ac.in");
        Intent emailIntent = new Intent(Intent.ACTION_VIEW, email);
        startActivity(emailIntent);
    }

    public void callGurpreet(View v) {
        Uri number = Uri.parse("tel:9417360233");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            callIntent = new Intent(Intent.ACTION_CALL, number);
        }
        startActivity(callIntent);
    }

    public void emailGurpreet(View v) {
        Uri email = Uri.parse("mailto:gurpreet@iisermohali.ac.in");
        Intent emailIntent = new Intent(Intent.ACTION_VIEW, email);
        startActivity(emailIntent);
    }

    public void callMobileSKAgarwal(View v) {
        Uri number = Uri.parse("tel:9815691982");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            callIntent = new Intent(Intent.ACTION_CALL, number);
        }
        startActivity(callIntent);
    }

    public void callClinicSKAgarwal(View v) {
        Uri number = Uri.parse("tel:01722656079");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            callIntent = new Intent(Intent.ACTION_CALL, number);
        }
        startActivity(callIntent);
    }

    public void webHealthCentre(View v) {
        Uri link = Uri.parse("http://www.iisermohali.ac.in/health-centre/facilities/health-centre");
        Intent webIntent = new Intent(Intent.ACTION_VIEW, link);
        startActivity(webIntent);
    }

    public void webExecutives(View v) {
        Uri link = Uri.parse("http://www.iisermohali.ac.in/executive-positions/people/executive-positions");
        Intent webIntent = new Intent(Intent.ACTION_VIEW, link);
        startActivity(webIntent);
    }
}