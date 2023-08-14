package com.example.app;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MikvehProfileActivity extends AppCompatActivity {
    public static final String TAG = "TAG";

    String userID, meetingID;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    Button dateButton,saveData;
    EditText dateTextET, timeText;
    TextView nameText;
    Spinner spinner;
    Context context;
    AlertDialog builderAlert;
    LayoutInflater layoutInflater;
    View showInput;


    private TextView mCouncil_txtView;
    private TextView mCity_txtView;
    private TextView mNeighborhood_txtView;
    private TextView mAddress_txtView;
    private TextView mPhone_txtView;
    private TextView mOpeningSummer_txtView;
    private TextView mOpeningWinter_txtView;
    private TextView mOpeningFriday_txtView;
    private TextView mOpeningSaturday_txtView;
    private TextView mAccess_txtView;
    private TextView mNotes_txtView;
    private Button mCreate;
    private Button mBack_btn;

    private String mAddress;
    private String mCity;
    private String mNeighbor;
    private String mReligious_Council;
    private String mOpening_Hours_Summer;
    private String mPhone;
    private String mOwner_ID;
    private String mOpening_Hours_Winter;
    private String mOpening_Hours_Holiday_Eve_Shabat_Eve;
    private String mOpening_Hours_Saturday_Night_Good_Day;
    private String mAccessibility;
    private String mNotes;

    private String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mikveh_profile);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        meetingID = fAuth.getUid();


        mCreate = findViewById(R.id.create);
        context = this;


        key = getIntent().getStringExtra("key");
        mAddress = getIntent().getStringExtra("address");
        mCity = getIntent().getStringExtra("city");
        mNeighbor = getIntent().getStringExtra("neighbor");
        mReligious_Council = getIntent().getStringExtra("religious_Council");
        mOpening_Hours_Summer = getIntent().getStringExtra("opening_Hours_Summer");
        mPhone = getIntent().getStringExtra("phone");
        mOwner_ID = getIntent().getStringExtra("owner_ID");
        mOpening_Hours_Winter = getIntent().getStringExtra("opening_Hours_Winter");
        mOpening_Hours_Holiday_Eve_Shabat_Eve = getIntent().getStringExtra("opening_Hours_Holiday_Eve_Shabat_Eve");
        mOpening_Hours_Saturday_Night_Good_Day = getIntent().getStringExtra("opening_Hours_Saturday_Night_Good_Day");
        mAccessibility = getIntent().getStringExtra("accessibility");
        //mSchedule_Appointment = getIntent().getStringExtra("schedule_Appointment");
        mNotes = getIntent().getStringExtra("notes");

        mCouncil_txtView = (TextView) findViewById(R.id.council_txtView);
        mCity_txtView = (TextView) findViewById(R.id.city_txtView);
        mNeighborhood_txtView = (TextView) findViewById(R.id.neighborhood_txtView);
        mAddress_txtView = (TextView) findViewById(R.id.address_txtView);
        mPhone_txtView = (TextView) findViewById(R.id.phone_txtView);
        mOpeningSummer_txtView = (TextView) findViewById(R.id.openingSummer_txtView);
        mOpeningWinter_txtView = (TextView) findViewById(R.id.openingWinter_txtView);
        mOpeningFriday_txtView = (TextView) findViewById(R.id.openingFriday_txtView);
        mOpeningSaturday_txtView = (TextView) findViewById(R.id.openingSaturday_txtView);
        mAccess_txtView = (TextView) findViewById(R.id.access_txtView);
        mNotes_txtView = (TextView) findViewById(R.id.notes_txtView);
        mBack_btn = (Button) findViewById(R.id.back_btn);

        mCouncil_txtView.setText("Religious council: " + mReligious_Council);
        mCity_txtView.setText("City: " + mCity);
        mNeighborhood_txtView.setText("Neighborhood: " + mNeighbor);
        mAddress_txtView.setText("Address: " + mAddress);
        mPhone_txtView.setText("Phone: " + mPhone);
        mOpeningSummer_txtView.setText("Opening Hours Summer: " + mOpening_Hours_Summer);
        mOpeningWinter_txtView.setText("Opening Hours Winter: " + mOpening_Hours_Winter);
        mOpeningFriday_txtView.setText("Opening Hours Friday: " + mOpening_Hours_Holiday_Eve_Shabat_Eve);
        mOpeningSaturday_txtView.setText("Opening Hours Saturday: " + mOpening_Hours_Saturday_Night_Good_Day);
        mAccess_txtView.setText("Accessibility: " + mAccessibility);
        mNotes_txtView.setText("Notes: " + mNotes);

        mCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputData();
            }
        });

        //showData();

        mBack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                return;
            }
        });

    }


    private void inputData() {

        builderAlert = new AlertDialog.Builder(context).create();
        layoutInflater = getLayoutInflater();
        showInput = layoutInflater.inflate(R.layout.input_layout, null);
        builderAlert.setView(showInput);

        nameText = showInput.findViewById(R.id.et_name);
        spinner = showInput.findViewById(R.id.time_spinner);
        dateButton = showInput.findViewById(R.id.btnDate);
        dateTextET = showInput.findViewById(R.id.et_date);
        timeText = showInput.findViewById(R.id.txt_time);
        saveData = showInput.findViewById(R.id.saveData);
        builderAlert.show();

        DocumentReference documentReference = fStore.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                nameText.setText(documentSnapshot.getString("userName"));
            }
        });

        //Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MikvehProfileActivity.this, R.array.times_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        timeText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View arg0, boolean hasFocus) {
                if(hasFocus){
                    spinner.setVisibility(View.VISIBLE);
                }
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
                timeText.setText(spinner.getSelectedItem().toString()); //this is taking the first value of the spinner by default.
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //spinner.setOnItemSelectedListener(this);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDateButton();
            }
        });

        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String time = timeText.getText().toString();
                final String date = dateTextET.getText().toString();
                final String name = nameText.getText().toString();

                userID = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fStore.collection("Users").document(userID).collection("Appointments").document();
                DocumentReference ownerRef = fStore.collection("Users").document(mOwner_ID).collection("UpcomingMeetings").document();
                Map<String, Object> meeting = new HashMap<>();
                meeting.put("date", date);
                meeting.put("time", time);
                meeting.put("address", mAddress);
                meeting.put("city", mCity);
                meeting.put("name", name);

                ownerRef.set(meeting).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("abc", "Success!");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("abc", "Error!");
                    }
                });

                documentReference.set(meeting).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Meeting Created! ", Toast.LENGTH_SHORT).show();
                        builderAlert.dismiss();
                        startActivity(new Intent(MikvehProfileActivity.this, MyAppointment.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Error! ", Toast.LENGTH_SHORT).show();
                        builderAlert.dismiss();
                        //startActivity(new Intent(MikvehProfileActivity.this, MyAppointment.class));
                    }
                });
            }
        });
    }

    private void handleDateButton() {
        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DATE, date);
                String dateText = DateFormat.format("EEEE, MMM d, yyyy", calendar1).toString();

                dateTextET.setText(dateText);
            }
        }, YEAR, MONTH, DATE);

        datePickerDialog.show();
    }



}


