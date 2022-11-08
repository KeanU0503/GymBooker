package com.example.gymbooker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.gymbooker.Adapter.AdapterGymHistory;
import com.example.gymbooker.Model.ModelGymClasses;
import com.example.gymbooker.Model.ModelGymHistory;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

public class GymHistory extends AppCompatActivity {

    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private String UserID = fAuth.getCurrentUser().getUid();

    ArrayList<ModelGymHistory> ModelGymHistory;
    ArrayList<ModelGymHistory> ModelSearchList;

    RecyclerView RecGymHistory;
    AdapterGymHistory AdapterGymHistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gym_history);

        RecGymHistory = findViewById(R.id.RV_GymHistory);
        RecGymHistory.setHasFixedSize(true);
        RecGymHistory.setLayoutManager(new LinearLayoutManager(this));

        ModelGymHistory = new ArrayList<>();

        AdapterGymHistory = new AdapterGymHistory(ModelGymHistory, GymHistory.this);
        RecGymHistory.setAdapter(AdapterGymHistory);

        readGymHistoryDB();
    }

    // Search bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search_bar, menu); // To get our inflater inside inflater we are inflating our menu file.
        MenuItem iconSearch = menu.findItem(R.id.action_search); //  To get our menu item.

        SearchView searchView = (SearchView) iconSearch.getActionView(); // Getting search view of our item.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() { // To call set on query text listener method.
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText); // Inside onQueryTextChange method we are calling a method to filter our recycler view.
                return false;
            }
        });

        return true;
    }

    private void filter(String text) {

        ModelSearchList = new ArrayList<ModelGymHistory>(); // Creating a new array list to filter our data. // Must be here
        for (ModelGymHistory item : ModelGymHistory) { // Running a for loop to compare elements.

            if (item.getInGymType().toLowerCase().contains(text.toLowerCase()) || item.getInBookTime().toLowerCase().contains(text.toLowerCase()) ) { // Checking if the entered string matched with any item of our recycler view.
                ModelSearchList.add(item); // If the item is matched, add it to our filtered list.
            }

            AdapterGymHistory.AdapterSearchList(ModelSearchList); // Pass the filtered list to our adapter class.
        }
    }


    private void readGymHistoryDB() {

        fStore.collection("Gym Bookings").whereEqualTo("User ID", UserID)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (int x=0; x<queryDocumentSnapshots.size(); x++){
                            DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(x);

                            String Duration = documentSnapshot.getString("Duration");
                            String Date = documentSnapshot.getString("Date");
                            String Time = documentSnapshot.getString("Time");
                            String GymType = documentSnapshot.getString("Gym Type");
                            String GymTrainer = documentSnapshot.getString("Gym Trainer");
                            String Day = documentSnapshot.getString("Day");
                            String BookTime = documentSnapshot.getString("Booked Time");

                            ModelGymHistory GymHistory = new ModelGymHistory(Duration,Date,Time,GymType,GymTrainer,null,Day,BookTime,null);
                            ModelGymHistory.add(GymHistory);
                        }

                        AdapterGymHistory.notifyDataSetChanged();
                    }
                });


    }

}