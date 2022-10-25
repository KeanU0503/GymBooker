package com.example.gymbooker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.example.gymbooker.Adapter.AdapterGymHistory;
import com.example.gymbooker.Model.ModelGymHistory;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

public class GymHistory extends AppCompatActivity {

    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String UserID = fAuth.getCurrentUser().getUid();

    ArrayList<ModelGymHistory> ModelGymHistory;

    RecyclerView RecGymHistory;
    AdapterGymHistory AdapterGymHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_history);

        RecGymHistory = findViewById(R.id.RV_GymHistory);
        RecGymHistory.setHasFixedSize(true);
        RecGymHistory.setLayoutManager(new LinearLayoutManager(this));

        ModelGymHistory = new ArrayList<>();

        AdapterGymHistory = new AdapterGymHistory(ModelGymHistory, GymHistory.this);
        RecGymHistory.setAdapter(AdapterGymHistory);

        readGymHistoryDB();
    }

    private void readGymHistoryDB() {

        fStore.collection("Accounts").document(UserID).collection("Bookings").orderBy("Date", Query.Direction.DESCENDING)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (int x=0; x<queryDocumentSnapshots.size(); x++){
                            DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(x);

                            String Date = documentSnapshot.getString("Date");
                            String Duration = documentSnapshot.getString("Duration");
                            String Time = documentSnapshot.getString("Time");
                            String GymType = documentSnapshot.getString("GymType");

                            ModelGymHistory GymHistory = new ModelGymHistory(Date,Duration,Time,GymType);
                            ModelGymHistory.add(GymHistory);
                        }

                        AdapterGymHistory.notifyDataSetChanged();
                    }
                });


    }
}