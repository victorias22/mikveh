package com.example.app;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MyAppointment extends AppCompatActivity {

    public static final String TAG = "TAG";

    String userID, meetingID;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    ListView meetingsList;
    ArrayList<dataUser> dataArrayList;

    TextView TVheader, TVaddress, TVcity, TVdate, TVtime, TVname;
    Button buttn, buttn2;
    ImageButton delete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointment);

        TVheader = findViewById(R.id.txt_myapp);
        TVaddress = findViewById(R.id.meetingAddress);
        TVcity = findViewById(R.id.meetingCity);
        TVdate = findViewById(R.id.txt_date_item);
        TVtime = findViewById(R.id.txt_time_item);
        TVname = findViewById(R.id.meetingUsername);
        delete = findViewById(R.id.imageView);

        buttn = findViewById(R.id.button);
        buttn2 = findViewById(R.id.button2);

        meetingsList = findViewById(R.id.meetingsList);
        dataArrayList = new ArrayList<>();

        fAuth = FirebaseAuth.getInstance();
        meetingID = fAuth.getUid();
        userID = fAuth.getCurrentUser().getUid();

        fStore = FirebaseFirestore.getInstance();
        CollectionReference collRef = fStore.collection("Users").document(userID).collection("Appointments");
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


        buttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();
            }
        });

        buttn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyAppointment.this, UserMenuAppActivity.class));
            }
        });

    }


    private void loadData() {
        meetingsList.setVisibility(View.VISIBLE);
        fStore.collection("Users").document(userID).collection("Appointments").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                String key = d.getId();
                                dataUser data_user = d.toObject(dataUser.class);
                                data_user.setID(key);
                                dataArrayList.add(data_user);
                            }
                            // after that we are passing our array list to our adapter class.
                            AdapterItemAppointment adapter = new AdapterItemAppointment(MyAppointment.this, dataArrayList);

                            // after passing this array list to our adapter
                            // class we are setting our adapter to our list view.
                            meetingsList.setAdapter(adapter);
                            //adapter.notifyDataSetChanged();

                        } else {
                            // if the snapshot is empty we are displaying a toast message.
                            Toast.makeText(MyAppointment.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    // we are displaying a toast message
                    // when we get any error from Firebase.
                        Toast.makeText(MyAppointment.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
                    }
                });
        }
}