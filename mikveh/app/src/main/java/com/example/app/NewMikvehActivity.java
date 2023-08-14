package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class NewMikvehActivity extends AppCompatActivity {

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
    private Button mAdd_btn;
    private Button mBack_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_mikveh);

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
        mAdd_btn = (Button) findViewById(R.id.add_btn);
        mBack_btn = (Button) findViewById(R.id.back_btn);

        mAdd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mikveh mikveh = new Mikveh();
                mikveh.setOwner_ID(FirebaseAuth.getInstance().getCurrentUser().getUid());
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
                new FirebaseRealtimeDatabaseHelper().addMikveh(mikveh, new FirebaseRealtimeDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Mikveh> mikvot, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {
                        Toast.makeText(NewMikvehActivity.this, "The mikveh record has" +
                                "been inserted successfully", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }
        });
        mBack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();//?-to terminate this activity and get back to the precedent activity
                return;//?-to stop executing enething else
            }
        });


    }
}