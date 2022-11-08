package com.example.gymbooker.GymAccess;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gymbooker.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Calendar;

public class GymFloor extends AppCompatActivity {

    // The type and the name of the variables
    ListView listview;
    TextView tvSelDate, tvSelTime, tvSelDuration, tvGymFloorTitle;
    Button btDate, btNext;
    RadioGroup rdGroup;
    RadioButton rdButton;

    Calendar calender = Calendar.getInstance();
    DatePickerDialog showDatePicker;
    ArrayList<String> StringModelTime; // Type of data it will hold (references to String)

    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) { // Method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gym_floor);
        getSupportActionBar().hide();

        Intent intent = getIntent();

        String userName = intent.getStringExtra("Client Name");

        listview = findViewById(R.id.listViewTime);
        tvSelDate = findViewById(R.id.gym_floor_selectedDate);
        tvSelTime = findViewById(R.id.gym_floor_selectedTime);
        tvSelDuration = findViewById(R.id.gym_floor_selectedDuration);
        tvGymFloorTitle = findViewById(R.id.gymFloor_title);
        btDate = findViewById(R.id.gym_floor_showDate);
        btNext = findViewById(R.id.gym_floor_next);
        rdGroup = findViewById(R.id.radioGroup);

        StringModelTime = new ArrayList<String>();

        int dayOfMonth = calender.get(Calendar.DAY_OF_MONTH);
        int month = calender.get(Calendar.MONTH);
        int year = calender.get(Calendar.YEAR);

        btDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDatePicker = new DatePickerDialog(GymFloor.this, new DatePickerDialog.OnDateSetListener() {
                    @Override

                    public void onDateSet(DatePicker datePicker, int year , int month, int day) {
                        String chosenDate = day + "/" + (month + 1) + "/" + year;
                        tvSelDate.setText(chosenDate);
                    }
                }, year, month , dayOfMonth);

                showDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                showDatePicker.show();
            }
        });

        btNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String selectedDuration = tvSelDuration.getText().toString();
                String selectedDate = tvSelDate.getText().toString();
                String selectedTime = tvSelTime.getText().toString();
                String passGymFloorTitle = tvGymFloorTitle.getText().toString();

                Intent toVerify = new Intent(view.getContext(), GymFloorBooking.class);
                toVerify.putExtra("Gym Floor", passGymFloorTitle);
                toVerify.putExtra("Duration", selectedDuration);
                toVerify.putExtra("Date", selectedDate);
                toVerify.putExtra("Time", selectedTime);
                toVerify.putExtra("Client Name", userName);

                view.getContext().startActivity(toVerify);
            }
        });

        readCheckoutInformationDB();
    }

    public void rbClick(View view) {

        int radioSelected = rdGroup.getCheckedRadioButtonId();
        rdButton = findViewById(radioSelected);
        tvSelDuration.setText(rdButton.getText());
    }


    private void readCheckoutInformationDB() {

        fStore.collection("Time Slots").orderBy("Time", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException error) {
                StringModelTime.clear();

                for (DocumentSnapshot snapshot : documentSnapshots){
                    StringModelTime.add(snapshot.getString("Time"));
                }


                ArrayAdapter<String>adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_single_choice, StringModelTime);
                listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                listview.setAdapter(adapter);
                adapter.notifyDataSetChanged();


                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override

                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        String time = (String) adapterView.getItemAtPosition(position);
                        Toast.makeText(GymFloor.this, "Selected: " + time, Toast.LENGTH_SHORT).show();
                        tvSelTime.setText(time);
                    }
                });

            }
        });
    }

}