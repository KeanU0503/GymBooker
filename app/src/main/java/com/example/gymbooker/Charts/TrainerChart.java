package com.example.gymbooker.Charts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.gymbooker.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class TrainerChart extends AppCompatActivity {

    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String UserID = fAuth.getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_trainer);
        getSupportActionBar().hide();

        PieChart piechart = findViewById(R.id.pieChartTrainer);

        Intent intent = getIntent();

        String cardio = intent.getStringExtra("Cardio");
        String cycling = intent.getStringExtra("Cycling");
        String hilt = intent.getStringExtra("Hilt");
        String mind = intent.getStringExtra("Mind");
        String strength = intent.getStringExtra("Strength");

        // insert data from received intent
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(Float.parseFloat(cardio), "Cardio"));
        entries.add(new PieEntry(Float.parseFloat(cycling), "Cycling"));
        entries.add(new PieEntry(Float.parseFloat(hilt), "Hilt"));
        entries.add(new PieEntry(Float.parseFloat(mind), "Mind"));
        entries.add(new PieEntry(Float.parseFloat(strength), "Strength"));

        ArrayList<Integer> colors = new ArrayList<>();
        for(int color: ColorTemplate.MATERIAL_COLORS) { // get colors from a list of colors in the ColorTemplate
            colors.add(color);
        }

        for(int color: ColorTemplate.VORDIPLOM_COLORS) { // get colors from a list of colors in the ColorTemplate
            colors.add(color);
        }

        // pie dataset object and include the entries
        PieDataSet pieDataSet = new PieDataSet(entries, " - Floor Trainer Category");
        pieDataSet.setColors(colors);

        // constructor ??
        PieData data = new PieData(pieDataSet);
        data.setDrawValues(true);
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        piechart.setData(data);
        piechart.invalidate(); // refresh
        piechart.getDescription().setEnabled(false);
        piechart.setDrawHoleEnabled(true); // show a hole in the middle/donut

        piechart.setCenterText("Total of Floor Trainer Bookings");

    }

}
