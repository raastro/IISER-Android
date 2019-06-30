package com.dhruva.iiser;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.List;

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

    SharedPreferences expenditure;
    PieChart pieChart;
    float messChange, eateryChange, canteenChange, storeChange;

    public UPIActivity() {
        messChange = 0f;
        eateryChange = 0f;
        canteenChange = 0f;
        storeChange = 0f;
    }

    //Activity Functions
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upi_payment);
        initializePie();
        initialize();
        initializeLogic();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d("Tag", "onStop Called");
        applyChanges();
    }

    //initializer Functions
    private void initialize(){
        spinner = findViewById(R.id.spinner);
        final Button pay = findViewById(R.id.pay);
        final EditText note = findViewById(R.id.note);
        final ImageView refresh = findViewById(R.id.refresh);
        final ImageView remove = findViewById(R.id.remove);
        final ImageView revert = findViewById(R.id.revert);
        final EditText amt = findViewById(R.id.amount);
        expenditure = getSharedPreferences("expenditure", Activity.MODE_PRIVATE);

        //Graph changes
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Apply any changes then refresh
                applyChanges();
                refreshGraph();
            }
        });
        revert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revertChanges();
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expenditure.edit().clear().apply();
            }
        });

        //Payment
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = spinner.getSelectedItemPosition();
                if (upiIds[i].equals("")){
                    // UPI id hasn't been added yet.
                    Toast.makeText(getApplicationContext(),"UPI ID not added yet",Toast.LENGTH_SHORT).show();
                }
                else {
                    //UPI Id exists, continue
                    String amtText = amt.getText().toString();
                    if (amtText.equals("")){
                        //amtText is empty
                        Toast.makeText(getApplicationContext(),"Amount is empty!",
                                Toast.LENGTH_SHORT).show();
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

                        //Change variables but do not apply changes
                        if (i < 4) {
                            //Mess
                            messChange += Float.valueOf(amt.getText().toString());
                        } else if (i < 6) {
                            //Canteen
                            canteenChange += Float.valueOf(amt.getText().toString());
                        } else if (i < 8) {
                            //Eateries
                            eateryChange += Float.valueOf(amt.getText().toString());
                        } else {
                            //Stores
                            storeChange += Float.valueOf(amt.getText().toString());
                        }

                        //Start the intent.
                        startActivityForResult(intent, 1);
                    }
                }
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

    //Graphing Functions
    private void initializePie(){
        pieChart = findViewById(R.id.pieChart);

        //Make it look "good"
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 5, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);

        //Hole Formation
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);
        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setDrawCenterText(true);
        pieChart.setRotationAngle(0);

        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        //Apparently Doesn't work?
        /*pieChart.setUnit(" €");
        pieChart.setDrawUnitsInChart(true);*/

        //Animate
        pieChart.animateY(1400, Easing.EaseInOutQuad);

        //Legend
        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
        l.setTextSize(15f);

        // entry label styling
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setEntryLabelTextSize(12f);
    }
    private void refreshGraph(){
        //get data to fill
        String[] labels = {"Messes","Canteens","Stores","Eateries"};
        List<Float> values = new ArrayList<>();
        for (String key : new String[] {"mess","canteen","store","eateries"}) {
            values.add(expenditure.getFloat(key, 0.0f));
        }
        //Fill and Refresh
        pieChart.setData(makeData(labels,values,"Expenditure Split"));
        pieChart.invalidate();
    }
    private PieData makeData(String[] labels, List<Float> values, String name) {

        // This holds entries for DataSet
        ArrayList<PieEntry> entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their
        // position around the center of the chart.

        for (int i=0; i<labels.length; i++) {
            if (values.get(i) > 0)
                entries.add(new PieEntry(values.get(i), labels[i]));
        }

        //DataSet for modifying output
        PieDataSet dataSet = new PieDataSet(entries, name);
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add colors for the pie chart
        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.MATERIAL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        //Final PieData build and return
        PieData data = new PieData(dataSet);
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        return data;
    }

    //Save data functions
    private void revertChanges(){
        Log.d("Tag", "revertChanges called");
        //reset variables to original values
        messChange = 0;
        eateryChange = 0;
        canteenChange = 0;
        storeChange = 0;
    }
    private void applyChanges(){
        Log.d("Tag", "applyChanges called");
        expenditure.edit().putFloat("mess",
                expenditure.getFloat("mess",0.0f)+messChange).apply();
        expenditure.edit().putFloat("canteen",
                expenditure.getFloat("canteen",0.0f)+canteenChange).apply();
        expenditure.edit().putFloat("store",
                expenditure.getFloat("store",0.0f)+storeChange).apply();
        expenditure.edit().putFloat("eatery",
                expenditure.getFloat("eatery",0.0f)+eateryChange).apply();
        messChange = 0;
        eateryChange = 0;
        canteenChange = 0;
        storeChange = 0;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data!=null) {
            //Got a good response
            if(data.getExtras().getString("Status", "").equals("FAILURE")){
                //Failed
                Toast.makeText(getApplicationContext(),"Transaction Unsuccessful",Toast.LENGTH_SHORT).show();
                revertChanges();
            }
            else {
                //Success
                Toast.makeText(getApplicationContext(),"Transaction Successful",Toast.LENGTH_SHORT).show();
                applyChanges();
                refreshGraph();
            }
        }
        else {
            //Got a bad response
            Toast.makeText(getApplicationContext(),"Transaction Result Unknown. " +
                    "If unsuccessful, touch the revert button. " +
                    "If successful, you MUST refresh the graph to save changes", Toast.LENGTH_LONG)
                    .show();
        }
    }
}
