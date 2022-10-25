package com.example.gymbooker;

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
    TextView tvDate, tvSelDate, tvSelTime, tvSelDuration, tvGymFloorTitle;
    Button btDate, btNext;
    RadioGroup rdGroup;
    RadioButton rdButton;

    Calendar theCalender = Calendar.getInstance();
    DatePickerDialog showDatePicker;
    ArrayList<String> LocalModelTime; // Type of data it will hold (references to String)

    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) { // Method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_floor);

        listview = findViewById(R.id.listViewTime);
        tvDate = findViewById(R.id.gym_floor_desDate);
        tvSelDate = findViewById(R.id.gym_floor_selectedDate);
        tvSelTime = findViewById(R.id.gym_floor_selectedTime);
        tvSelDuration = findViewById(R.id.gym_floor_selectedDuration);
        tvGymFloorTitle = findViewById(R.id.gymFloor_title);
        btDate = findViewById(R.id.gym_floor_showDate);
        btNext = findViewById(R.id.gym_floor_next);
        rdGroup = findViewById(R.id.radioGroup);

        LocalModelTime = new ArrayList<String>();

        //  Calendar settings
        int dayOfMonth = theCalender.get(Calendar.DAY_OF_MONTH);
        int month = theCalender.get(Calendar.MONTH);
        int year = theCalender.get(Calendar.YEAR);

        btDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDatePicker = new DatePickerDialog(GymFloor.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    // User selects the date
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
                toVerify.putExtra("GymFloor", passGymFloorTitle);
                toVerify.putExtra("Duration", selectedDuration);
                toVerify.putExtra("Date", selectedDate);
                toVerify.putExtra("Time", selectedTime);

                view.getContext().startActivity(toVerify);
            }
        });

        readCheckoutInformationDB();
    }

    public void rbClick(View view) { // Void does not return anything

        int radioSelected = rdGroup.getCheckedRadioButtonId();
        rdButton = findViewById(radioSelected);
        tvSelDuration.setText(rdButton.getText());

    }

    private void readCheckoutInformationDB() { // Method

        fStore.collection("Time Slots").orderBy("Time", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException error) {
                LocalModelTime.clear();

                for (DocumentSnapshot snapshot : documentSnapshots){
                    LocalModelTime.add(snapshot.getString("Time"));
                }

                // Converts data from the data sources into view items that can be displayed into the UI Component.
                ArrayAdapter<String>adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_single_choice,LocalModelTime);
                listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                listview.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                // Display the list
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    // AdapterView = the click response of the adapterView
                    // View = clicked
                    // Long = row id of the item that was clicked
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