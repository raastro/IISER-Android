package com.dhruva.iiser;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class upiPayment extends Activity {

    String[] locations = {
            "H5 Mess", "H6 Mess", "H7 Mess", "H8 Mess",
            "H5 Canteen", "H7 Canteen",
            "LHCEatery", "SC Eatery",
            "Shiva Stores"};

    String[] upiIds = {
            "", "", "", "",
            "", "",
            "BHARATPE90100181446@yesbankltd", "BHARATPE90100181438@yesbankltd",
            ""};

    Spinner spinner;

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
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                int i = spinner.getSelectedItemPosition();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(
                        Uri.parse("upi://pay?" +
                                "pa="+ upiIds[i] +
                                "&pn=" + locations[i].replace(" ","%20") +
                                "&am=" + amt.getText()+
                                "&cu=INR" +
                                "&tn=" + note.getText().toString().replace(" ","%20"))

                );
                startActivity(intent);
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
    }
    @Override
    protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
        super.onActivityResult(_requestCode, _resultCode, _data);

        switch (_requestCode) {

            default:
                break;
        }
    }
}
