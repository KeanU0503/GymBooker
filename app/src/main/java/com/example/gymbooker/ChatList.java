package com.example.gymbooker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import com.example.gymbooker.Adapter.AdapterChatList;
import com.example.gymbooker.Model.ModelChatList;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

public class ChatList extends AppCompatActivity {

    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();

    RecyclerView RecChatList;
    AdapterChatList adaptChatList;
    ArrayList<ModelChatList> arrayModelChatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_list);
        getSupportActionBar().hide();

        RecChatList = findViewById(R.id.RV_ChatList);
        RecChatList.setHasFixedSize(true);
        RecChatList.setLayoutManager(new LinearLayoutManager(this));

        arrayModelChatList = new ArrayList<>();

        adaptChatList = new AdapterChatList(arrayModelChatList, ChatList.this);

        RecChatList.setAdapter(adaptChatList);

        readChatListDB();

    }

    private void readChatListDB() {

        fStore.collection("Accounts").orderBy("Chat", Query.Direction.DESCENDING)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (int x=0; x<queryDocumentSnapshots.size(); x++){
                            DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(x);

                            String Name = documentSnapshot.getString("Full Name");
                            String UserID = documentSnapshot.getId();

                            ModelChatList fStoreModelChatList = new ModelChatList(Name, UserID);
                            arrayModelChatList.add(fStoreModelChatList);
                        }

                        adaptChatList.notifyDataSetChanged();
                    }
                });

    }
}