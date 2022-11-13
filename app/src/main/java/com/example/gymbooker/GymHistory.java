package com.example.gymbooker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.gymbooker.Adapter.AdapterGymHistory;
import com.example.gymbooker.Model.ModelGymHistory;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
        setContentView(R.layout.history_gym);

        RecGymHistory = findViewById(R.id.RV_GymHistory);
        RecGymHistory.setHasFixedSize(true);
        RecGymHistory.setLayoutManager(new LinearLayoutManager(this));

        ModelGymHistory = new ArrayList<>();

        AdapterGymHistory = new AdapterGymHistory(ModelGymHistory, GymHistory.this);
        RecGymHistory.setAdapter(AdapterGymHistory);

        readGymHistoryDB();
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

        ModelSearchList = new ArrayList<ModelGymHistory>();
        for (ModelGymHistory item : ModelGymHistory) {

            if (item.getInGymType().toLowerCase().contains(text.toLowerCase()) || item.getInBookTime().toLowerCase().contains(text.toLowerCase()) ) { // Checking if the entered string matched with any item of our recycler view.
                ModelSearchList.add(item);
            }

            AdapterGymHistory.AdapterSearchList(ModelSearchList);
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

                        Collections.sort(ModelGymHistory, new Comparator<ModelGymHistory>() {
                            @Override
                            public int compare(ModelGymHistory t1, ModelGymHistory t2) {
                                return t1.getInBookTime().compareToIgnoreCase(t2.getInBookTime());
                            } // compare all the elements and arrange in ascending order
                        });

                        Collections.reverse(ModelGymHistory);

                        AdapterGymHistory.notifyDataSetChanged();
                    }
                });
    }

}