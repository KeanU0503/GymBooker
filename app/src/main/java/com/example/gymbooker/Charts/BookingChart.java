package com.example.gymbooker.Charts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.gymbooker.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class BookingChart extends AppCompatActivity {

    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String UserID = fAuth.getCurrentUser().getUid();
    String cardio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_booking);
        getSupportActionBar().hide();

        BarChart barchart = findViewById(R.id.barChart);

        Intent intent = getIntent();

        String classBookings = intent.getStringExtra("Class");
        String floorBookings = intent.getStringExtra("Floor");
        String trainerBookings = intent.getStringExtra("Trainer");

        ArrayList<BarEntry> bookings = new ArrayList<>();
        bookings.add(new BarEntry(1, Float.parseFloat(classBookings)));
        bookings.add(new BarEntry(2, Float.parseFloat(floorBookings)));
        bookings.add(new BarEntry(3, Float.parseFloat(trainerBookings)));

        ArrayList<Integer> colors = new ArrayList<>();
        for(int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        // get colors from a list of colors in the ColorTemplate

        BarDataSet barDataSet = new BarDataSet(bookings, "Gym Class  -  Floor  -  Trainer");  // creating a new bar data set.
        barDataSet.setColors(colors);                                                              // adding color to our bar data set.
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);                                                           // setting text size

        BarData barData = new BarData(barDataSet);                                                  // creating a new bar data and passing our bar data set.
        barData.setDrawValues(true);
        barchart.setFitBars(true);
        barchart.getXAxis().setDrawLabels(false);
        barchart.getDescription().setEnabled(false);
        barchart.animateY(2000);

        barchart.setData(barData);                                                                  // to set data to our bar chart.

    }

}