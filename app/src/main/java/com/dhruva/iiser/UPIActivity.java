package com.dhruva.iiser;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Arrays;

public class UPIActivity extends Activity {
    String[] locations = {
            "H5 Mess", "H6 Mess", "H7 Mess", "H8 Mess",
            "H5 Canteen", "H7 Canteen",
            "LHC Eatery", "SC Eatery",
            "Shiva Stores", "Stationary"};

    String[] upiIds = {
            "rameshchand2107-1@okaxis", "BHARATPE90700002296@yesbankltd", "", "",
            "", "BHARATPE90700002310@yesbankltd",
            "BHARATPE90100181446@yesbankltd", "BHARATPE90100181438@yesbankltd",
            "BHARATPE90700002236@yesbankltd", "BHARATPE90700002290@yesbankltd"};

    Spinner spinner;
    ProgressBar messProg;
    ProgressBar eateryProg;
    ProgressBar storeProg;
    ProgressBar canteenProg;

    TextView messLabel;
    TextView eateryLabel;
    TextView storeLabel;
    TextView canteenLabel;

    ImageView reset;

    SharedPreferences expenditure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upi_payment);

        initialize();
        initializeLogic();
    }
    private void initialize(){
        spinner = findViewById(R.id.spinner);
        final Button pay = findViewById(R.id.pay);
        final EditText amt = findViewById(R.id.amount);
        final EditText note = findViewById(R.id.note);

        messProg = findViewById(R.id.messProg);
        eateryProg = findViewById(R.id.eateryProg);
        storeProg = findViewById(R.id.storeProg);
        canteenProg = findViewById(R.id.canteenProg);

        messLabel = findViewById(R.id.messLabel);
        eateryLabel = findViewById(R.id.eateryLabel);
        storeLabel = findViewById(R.id.storeLabel);
        canteenLabel = findViewById(R.id.canteenLabel);

        reset = findViewById(R.id.reset);

        expenditure = getSharedPreferences("expenditure", Activity.MODE_PRIVATE);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                int i = spinner.getSelectedItemPosition();
                if (upiIds[i].equals("")){
                    Toast.makeText(getApplicationContext(),"UPI ID not added yet",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(
                            Uri.parse("upi://pay?" +
                                    "pa=" + upiIds[i] +
                                    "&pn=" + locations[i].replace(" ", "%20") +
                                    "&am=" + amt.getText() +
                                    "&cu=INR" +
                                    "&tn=" + note.getText().toString().replace(" ", "%20"))
                    );
                    startActivity(intent);
                    //Mess
                    if (i<4) {
                        expenditure.edit().putFloat("mess",
                                expenditure.getFloat("mess",0.0f) +
                                        Float.valueOf(
                                                amt.getText().toString()
                                        )
                        ).apply();
                    }
                    //Canteen
                    else if (i<6) {
                        expenditure.edit().putFloat("canteen",
                                expenditure.getFloat("canteen",0.0f) +
                                        Float.valueOf(
                                                amt.getText().toString()
                                        )
                        ).apply();
                    }
                    //Eateries
                    else if (i<8) {
                        expenditure.edit().putFloat("eatery",
                                expenditure.getFloat("eatery",0.0f) +
                                        Float.valueOf(
                                                amt.getText().toString()
                                        )
                        ).apply();
                    }
                    //Stores
                    else {

                        expenditure.edit().putFloat("store",
                                expenditure.getFloat("store",0.0f) +
                                        Float.valueOf(
                                                amt.getText().toString()
                                        )
                        ).apply();
                    }
                    refreshGraph();
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expenditure.edit().putFloat("mess",0.0f).apply();
                expenditure.edit().putFloat("canteen",0.0f).apply();
                expenditure.edit().putFloat("store",0.0f).apply();
                expenditure.edit().putFloat("eatery",0.0f).apply();
                refreshGraph();
            }
        });

    }
    private void initializeLogic(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, R.layout.spinner_item, locations
        );
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_item);
        // Apply the adapter to the spinner_item
        spinner.setAdapter(adapter);
        Toast.makeText(getApplicationContext(),"PLEASE MAKE SURE THE UPI ID IS CORRECT BEFORE PAYING. DEVs ARE NOT RESPONSIBLE FOR INCORRECT PAYMENTs!",Toast.LENGTH_LONG).show();
        refreshGraph();
    }
    @SuppressLint("SetTextI18n")
    private void refreshGraph(){
        float messVal = expenditure.getFloat("mess", 0.0f);
        float canteenVal = expenditure.getFloat("canteen", 0.0f);
        float eateryVal = expenditure.getFloat("eatery", 0.0f);
        float storeVal =  expenditure.getFloat("store", 0.0f);
        float[] array = {messVal, canteenVal, eateryVal, storeVal};
        Arrays.sort(array);
        float max = array[array.length-1] * 10.0f / 9.0f;
        if (max<500){
            max = 500;
        }

        messProg.setProgress((int)(messVal*100/max));
        canteenProg.setProgress((int)(canteenVal*100/max));
        eateryProg.setProgress((int)(eateryVal*100/max));
        storeProg.setProgress((int)(storeVal*100/max));

        messLabel.setText(Float.toString(messVal));
        canteenLabel.setText(Float.toString(canteenVal));
        eateryLabel.setText(Float.toString(eateryVal));
        storeLabel.setText(Float.toString(storeVal));
    }
}
