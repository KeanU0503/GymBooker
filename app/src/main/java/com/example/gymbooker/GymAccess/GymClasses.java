package com.example.gymbooker.GymAccess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.gymbooker.Adapter.AdapterGymClasses;
import com.example.gymbooker.Model.ModelGymClasses;
import com.example.gymbooker.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

public class GymClasses extends AppCompatActivity {

    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();

    ArrayList<ModelGymClasses> ModelGymClasses;
    ArrayList<ModelGymClasses> ModelSearchList;

    AdapterGymClasses AdapterGymClasses;

    int statistic;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gym_classes);

        RecyclerView recGymClasses = findViewById(R.id.RV_GymClasses);
        recGymClasses.setHasFixedSize(true);
        recGymClasses.setLayoutManager(new LinearLayoutManager(this));

        ModelGymClasses = new ArrayList<>();
        AdapterGymClasses = new AdapterGymClasses(ModelGymClasses, GymClasses.this);

        recGymClasses.setAdapter(AdapterGymClasses);


        readGymClassesDB();
        statisticGymClasses();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search_bar, menu);
        MenuItem iconSearch = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) iconSearch.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

        return true;
    }

    private void filter(String text) {

        ModelSearchList = new ArrayList<ModelGymClasses>();
        for (ModelGymClasses item : ModelGymClasses) {

            if (item.getInClassName().toLowerCase().contains(text.toLowerCase()) || item.getInClassDay().toLowerCase().contains(text.toLowerCase())
                    || item.getInClassTrainer().toLowerCase().contains(text.toLowerCase()) || item.getInClassDuration().toLowerCase().contains(text.toLowerCase())
                    || item.getInClassTime().toLowerCase().contains(text.toLowerCase()) ){

                ModelSearchList.add(item);
            }

            AdapterGymClasses.AdapterSearchList(ModelSearchList);

        }

    }


    private void readGymClassesDB() {

        fStore.collection("Gym Classes")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (int x=0; x<queryDocumentSnapshots.size(); x++){
                            DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(x);

                            String Name = documentSnapshot.getString("Class Name");
                            String Details = documentSnapshot.getString("Class Details");
                            String Trainer = documentSnapshot.getString("Class Trainer");
                            String Duration = documentSnapshot.getString("Class Duration");
                            String Day = documentSnapshot.getString("Class Day");
                            String Time = documentSnapshot.getString("Class Time");
                            String Limit = documentSnapshot.getString("Class Limit");
                            String Category = documentSnapshot.getString("Class Category");
                            String MaxLimit = documentSnapshot.getString("Class Max Limit");


                            ModelGymClasses GymClasses = new ModelGymClasses(Name,Details,Trainer,Duration,Day,Time,Limit,Category,MaxLimit);
                            ModelGymClasses.add(GymClasses);
                        }

                        AdapterGymClasses.notifyDataSetChanged();
                    }
                });
    }

    private void statisticGymClasses(){
        fStore.collection("Statistics")
                .document("Gym Classes")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String statistics = documentSnapshot.getString("Total Bookings");
                        statistic = Integer.parseInt(statistics);
                    }
                });
    }



    public void CardioClick(View view) { filter("Cardio"); }

    public void StrengthClick(View view) { filter("Strength"); }

    public void MindClick(View view) {
        filter("Mind");
    }

    public void HiltClick(View view) {
        filter("Hilt");
    }

    public void CyclingClick(View view) {
        filter("Cycling");
    }
}