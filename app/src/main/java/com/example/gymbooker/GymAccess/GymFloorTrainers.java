package com.example.gymbooker.GymAccess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.example.gymbooker.Adapter.AdapterGymFloorTrainers;
import com.example.gymbooker.Model.ModelGymTrainers;
import com.example.gymbooker.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

public class GymFloorTrainers extends AppCompatActivity {

    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();

    ArrayList<ModelGymTrainers> ModelGymTrainers;
    ArrayList<ModelGymTrainers> ModelSearchGymTrainers;

    AdapterGymFloorTrainers AdapterGymTrainers;

    int statistic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gym_floor_trainers);

        RecyclerView recGymFloorTrainers = findViewById(R.id.RV_GymFloorTrainer);
        recGymFloorTrainers.setHasFixedSize(true);
        recGymFloorTrainers.setLayoutManager(new LinearLayoutManager(this));

        ModelGymTrainers = new ArrayList<>();

        AdapterGymTrainers = new AdapterGymFloorTrainers(ModelGymTrainers, GymFloorTrainers.this);
        recGymFloorTrainers.setAdapter(AdapterGymTrainers);

        readGymFloorTrainersDB();
        statisticGymClasses();
    }

    // Search bar
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

    // Compare
    private void filter(String text) {
        ModelSearchGymTrainers = new ArrayList<ModelGymTrainers>();
        for (ModelGymTrainers item : ModelGymTrainers) {

            if (item.getInTrainerName().toLowerCase().contains(text.toLowerCase())) {
                ModelSearchGymTrainers.add(item);
            }

            AdapterGymTrainers.AdapterSearchList(ModelSearchGymTrainers);
        }
    }

    private void readGymFloorTrainersDB() {

        fStore.collection("Gym Trainers")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (int x=0; x<queryDocumentSnapshots.size(); x++){
                            DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(x);

                            String GymTrainer = documentSnapshot.getString("Gym Trainer");
                            String GymType = documentSnapshot.getString("Gym Type");
                            String Day = documentSnapshot.getString("Day");
                            String Duration = documentSnapshot.getString("Duration");
                            String Category = documentSnapshot.getString("Trainer Category"); // Only for document reference
                            String Limit = documentSnapshot.getString("Slot Limit");
                            String Details = documentSnapshot.getString("Details");
                            String Time = documentSnapshot.getString("Time");

                            ModelGymTrainers GymTrainers = new ModelGymTrainers(GymTrainer,GymType,Day,Duration,Category,Limit,Details,Time);
                            ModelGymTrainers.add(GymTrainers);
                        }

                        AdapterGymTrainers.notifyDataSetChanged();
                    }
                });
    }

    private void statisticGymClasses(){
        fStore.collection("Statistics")
                .document("Gym Floor Trainers")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String statistics = documentSnapshot.getString("Total Bookings");
                        statistic = Integer.parseInt(statistics);
                        statistic++;

                    }
                });
    }

}