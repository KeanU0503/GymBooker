package com.example.gymbooker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.example.gymbooker.Adapter.AdapterChatList;
import com.example.gymbooker.Model.ModelChatList;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

public class ChatList extends AppCompatActivity {

    RecyclerView RecChatList;
    AdapterChatList adaptChatList;
    ArrayList<ModelChatList> arrayModelChatList;

    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        RecChatList = findViewById(R.id.RV_ChatList);
        RecChatList.setHasFixedSize(true);
        RecChatList.setLayoutManager(new LinearLayoutManager(this));

        arrayModelChatList = new ArrayList<>();

        adaptChatList = new AdapterChatList(arrayModelChatList, ChatList.this);

        RecChatList.setAdapter(adaptChatList);

        readChatListDB();

    }

    private void readChatListDB() {

        fStore.collection("Accounts")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (int x=0; x<queryDocumentSnapshots.size(); x++){
                            DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(x);

                            String Name = documentSnapshot.getString("FullName");
                            String Uid = documentSnapshot.getId();

                            ModelChatList fStoreModelChatList = new ModelChatList(Name, Uid);
                            arrayModelChatList.add(fStoreModelChatList);
                        }

                        adaptChatList.notifyDataSetChanged();
                    }
                });

    }
}