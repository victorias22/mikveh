package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OwnerMeetings extends AppCompatActivity {

    public static final String TAG = "TAG";

    TextView TVheader, TVaddress, TVcity, TVdate, TVtime, TVname;
    EditText chooseDate;
    Button dateButton, select;
    ImageView calendarImage;
    ListView OwnerMeetingsList;
    ArrayList<dataUser> dataArrayList;
    String ownerID, meetingID;
    FirebaseAuth fAuth;
    FirebaseFirestore db;

    ImageButton delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_meetings);

        calendarImage = findViewById(R.id.imageView3);
        select = findViewById(R.id.select_button);
        dateButton = findViewById(R.id.dateButton);
        OwnerMeetingsList = findViewById(R.id.ownerMikvehList);
        chooseDate = findViewById(R.id.chooseDate);
        TVheader = findViewById(R.id.txt_myapp);
        TVaddress = findViewById(R.id.meetingAddress);
        TVcity = findViewById(R.id.meetingCity);
        TVdate = findViewById(R.id.txt_date_item);
        TVtime = findViewById(R.id.txt_time_item);
        TVname = findViewById(R.id.meetingUsername);
        delete = findViewById(R.id.imageView);

        dataArrayList = new ArrayList<>();

        fAuth = FirebaseAuth.getInstance();
        ownerID = fAuth.getCurrentUser().getUid();
        meetingID = fAuth.getUid();

        db = FirebaseFirestore.getInstance();
        CollectionReference collRef = db.collection("Users").document(ownerID).collection("UpcomingMeetings");
        collRef.document(meetingID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        TVname.setText(doc.getString("name"));
                        TVaddress.setText(doc.getString("address"));
                        TVcity.setText(doc.getString("city"));
                        TVdate.setText(doc.getString("date"));
                        TVtime.setText(doc.getString("time"));
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDateButton();
            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();
            }
        });
    }

        private void loadData() {
        //Log.d("Date: ", date.toString());
        select.setVisibility(View.GONE);
        OwnerMeetingsList.setVisibility(View.VISIBLE);
            db.collection("Users").document(ownerID).collection("UpcomingMeetings")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d : list) {
                                    String key = d.getId();
                                    dataUser data_user = d.toObject(dataUser.class);
                                    data_user.setID(key);
                                    String str = data_user.getDate();
                                    final String date = chooseDate.getText().toString();

                                    if(date.equals(str)) {
                                        dataArrayList.add(data_user);
                                    }
                                }
                                // after that we are passing our array list to our adapter class.
                                AdapterItemAppointment adapter = new AdapterItemAppointment(OwnerMeetings.this, dataArrayList);

                                // after passing this array list to our adapter
                                // class we are setting our adapter to our list view.
                                OwnerMeetingsList.setAdapter(adapter);
                                //adapter.notifyDataSetChanged();

                            } else {
                                // if the snapshot is empty we are displaying a toast message.
                                Toast.makeText(OwnerMeetings.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // we are displaying a toast message
                    // when we get any error from Firebase.
                    Toast.makeText(OwnerMeetings.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
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
                chooseDate.setText(dateText);
            }
        }, YEAR, MONTH, DATE);

        datePickerDialog.show();
    }

}
