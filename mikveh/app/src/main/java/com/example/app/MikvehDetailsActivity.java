package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

public class MikvehDetailsActivity extends AppCompatActivity {
    private ImageButton imageCalendar;
    private EditText mCouncil_editTxt;
    private EditText mCity_editTxt;
    private EditText mNeighborhood_editTxt;
    private EditText mAddress_editTxt;
    private EditText mPhone_editTxt;
    private EditText mOpeningSummer_editTxt;
    private EditText mOpeningWinter_editTxt;
    private EditText mOpeningFriday_editTxt;
    private EditText mOpeningSaturday_editTxt;
    private EditText mAccess_editTxt;
    private EditText mAppoint_editTxt;
    private EditText mNotes_editTxt;
    private Button mUpdate_btn;
    private Button mDelete_btn;
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
    private String mSchedule_Appointment;
    private String mNotes;

    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mikveh_details);

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
        mSchedule_Appointment = getIntent().getStringExtra("schedule_Appointment");
        mNotes = getIntent().getStringExtra("notes");

        mCouncil_editTxt = (EditText) findViewById(R.id.council_editTxt);
        mCity_editTxt = (EditText) findViewById(R.id.city_editTxt);
        mNeighborhood_editTxt = (EditText) findViewById(R.id.neighborhood_editTxt);
        mAddress_editTxt = (EditText) findViewById(R.id.address_editTxt);
        mPhone_editTxt = (EditText) findViewById(R.id.phone_editTxt);
        mOpeningSummer_editTxt = (EditText) findViewById(R.id.openingSummer_editTxt);
        mOpeningWinter_editTxt = (EditText) findViewById(R.id.openingWinter_editTxt);
        mOpeningFriday_editTxt = (EditText) findViewById(R.id.openingFriday_editTxt);
        mOpeningSaturday_editTxt = (EditText) findViewById(R.id.openingSaturday_editTxt);
        mAccess_editTxt = (EditText) findViewById(R.id.access_editTxt);
        mAppoint_editTxt = (EditText) findViewById(R.id.appoint_editTxt);
        mNotes_editTxt = (EditText) findViewById(R.id.notes_editTxt);
        mUpdate_btn = (Button) findViewById(R.id.update_btn);
        mDelete_btn = (Button) findViewById(R.id.delete_btn);
        mBack_btn = (Button) findViewById(R.id.back_btn);
        imageCalendar = findViewById(R.id.calendarImage);
        mCouncil_editTxt.setText(mReligious_Council);
        mCity_editTxt.setText(mCity);
        mNeighborhood_editTxt.setText(mNeighbor);
        mAddress_editTxt.setText(mAddress);
        mPhone_editTxt.setText(mPhone);
        mOpeningSummer_editTxt.setText(mOpening_Hours_Summer);
        mOpeningWinter_editTxt.setText(mOpening_Hours_Winter);
        mOpeningFriday_editTxt.setText(mOpening_Hours_Holiday_Eve_Shabat_Eve);
        mOpeningSaturday_editTxt.setText(mOpening_Hours_Saturday_Night_Good_Day);
        mAccess_editTxt.setText(mAccessibility);
        mAppoint_editTxt.setText(mSchedule_Appointment);
        mNotes_editTxt.setText(mNotes);

        imageCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MikvehDetailsActivity.this, OwnerMeetings.class));

            }
        });

        mUpdate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mikveh mikveh = new Mikveh();
                mikveh.setOwner_ID(mOwner_ID);//!-Le mettre!!
                mikveh.setReligious_Council(mCouncil_editTxt.getText().toString());
                mikveh.setCity(mCity_editTxt.getText().toString());
                mikveh.setNeighborhood(mNeighborhood_editTxt.getText().toString());
                mikveh.setMikve_Address(mAddress_editTxt.getText().toString());
                mikveh.setPhone(mPhone_editTxt.getText().toString());
                mikveh.setOpening_Hours_Summer(mOpeningSummer_editTxt.getText().toString());
                mikveh.setOpening_Hours_Winter(mOpeningWinter_editTxt.getText().toString());
                mikveh.setOpening_Hours_Holiday_Eve_Shabat_Eve(mOpeningFriday_editTxt.getText().toString());
                mikveh.setOpening_Hours_Saturday_Night_Good_Day(mOpeningSaturday_editTxt.getText().toString());
                mikveh.setAccessibility(mAccess_editTxt.getText().toString());
                mikveh.setSchedule_Appointment(mAppoint_editTxt.getText().toString());
                mikveh.setNotes(mNotes_editTxt.getText().toString());

                new FirebaseRealtimeDatabaseHelper().editMikveh(key, mikveh, new FirebaseRealtimeDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Mikveh> mikvot, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {
                        Toast.makeText(MikvehDetailsActivity.this, "This record has been" +
                                "updated successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }
        });
        mDelete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FirebaseRealtimeDatabaseHelper().deleteMikveh(key, new FirebaseRealtimeDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Mikveh> mikvot, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {
                        Toast.makeText(MikvehDetailsActivity.this, "This record has been" +
                                "deleted successfully", Toast.LENGTH_LONG).show();
                        finish();return;
                    }
                });
            }
        });
        mBack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();return;
            }
        });

    }
}