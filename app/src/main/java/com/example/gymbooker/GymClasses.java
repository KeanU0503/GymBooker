package com.example.gymbooker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.example.gymbooker.Adapter.AdapterGymClasses;
import com.example.gymbooker.Model.ModelGymClasses;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

public class GymClasses extends AppCompatActivity {

    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();

    ArrayList<ModelGymClasses> ModelGymClasses;
    ArrayList<ModelGymClasses> ModelSearchList;

    AdapterGymClasses AdapterGymClasses;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_classes);

        // Set all the recycler view settings
        RecyclerView recGymClasses = findViewById(R.id.RV_GymClasses);
        recGymClasses.setHasFixedSize(true);
        recGymClasses.setLayoutManager(new LinearLayoutManager(this));

        // Set to the value or
        // put in the condition appropriate to the start of an operation
        ModelGymClasses = new ArrayList<>(); // Initialize

        // Create instance for adapter
        AdapterGymClasses = new AdapterGymClasses(ModelGymClasses, GymClasses.this);

        // Set the recyclerview with the adapter
        recGymClasses.setAdapter(AdapterGymClasses);

        readGymClassesDB();

    }

    // Search bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_gym_classes, menu); // To get our inflater inside inflater we are inflating our menu file.
        MenuItem item = menu.findItem(R.id.action_search); //  To get our menu item.

        SearchView searchView = (SearchView) item.getActionView(); // Getting search view of our item.
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

        ModelSearchList = new ArrayList<ModelGymClasses>(); // Creating a new array list to filter our data. // Must be here
        for (ModelGymClasses item : ModelGymClasses) { // Running a for loop to compare elements.

            if (item.getInClassName().toLowerCase().contains(text.toLowerCase()) || item.getInClassDate().toLowerCase().contains(text.toLowerCase()) ) { // Checking if the entered string matched with any item of our recycler view.
                ModelSearchList.add(item); // If the item is matched, add it to our filtered list.
            }

            AdapterGymClasses.AdapterSearchList(ModelSearchList); // Passing the filtered list to our adapter class.
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
                            String Date = documentSnapshot.getString("Class Date");
                            String Time = documentSnapshot.getString("Class Time");

                            // Constructor from model and use it to get Firestore data
                            ModelGymClasses GymClasses = new ModelGymClasses(Name,Details,Trainer,Duration,Date,Time);
                            ModelGymClasses.add(GymClasses);
                        }

                        AdapterGymClasses.notifyDataSetChanged();
                    }
                });

    }
}