package com.example.app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MikvehListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    String userID;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userProfile;

    String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mikveh_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.mikveh_recyclerView);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("Users").document(userID);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()) {
                        Log.d("Youhou",document.getData().toString());
                        userProfile = document.get("profile").toString();

                        if(userProfile.equals("Owner")) {
                            new FirebaseRealtimeDatabaseHelper().readMikvehByOwner(new FirebaseRealtimeDatabaseHelper.DataStatus() {
                                @Override
                                public void DataIsLoaded(List<Mikveh> mikvot, List<String> keys) {
                                    findViewById(R.id.loading_mikvot).setVisibility(View.GONE);
                                    new RecyclerView_Config().setConfig(mRecyclerView,
                                            MikvehListActivity.this, mikvot, keys);
                                }

                                @Override
                                public void DataIsInserted() {

                                }

                                @Override
                                public void DataIsUpdated() {

                                }

                                @Override
                                public void DataIsDeleted() {

                                }
                            });
                        }
                        else if(userProfile.equals("Admin")) {
                            new FirebaseRealtimeDatabaseHelper().readMikvehDB(new FirebaseRealtimeDatabaseHelper.DataStatus() {
                                @Override
                                public void DataIsLoaded(List<Mikveh> mikvot, List<String> keys) {
                                    findViewById(R.id.loading_mikvot).setVisibility(View.GONE);
                                    new RecyclerView_Config().setConfig(mRecyclerView,
                                            MikvehListActivity.this,
                                            mikvot, keys);
                                }

                                @Override
                                public void DataIsInserted() {

                                }

                                @Override
                                public void DataIsUpdated() {

                                }

                                @Override
                                public void DataIsDeleted() {

                                }
                            });
                        }
                        else {
                            //String city = "אופקים";
                            city = getIntent().getStringExtra("cityChoosed");
                            new FirebaseRealtimeDatabaseHelper().readMikvehByPosition(city, new FirebaseRealtimeDatabaseHelper.DataStatus() {
                                @Override
                                public void DataIsLoaded(List<Mikveh> mikvot, List<String> keys) {
                                    findViewById(R.id.loading_mikvot).setVisibility(View.GONE);
                                    new RecyclerView_Config().setConfig(mRecyclerView,
                                            MikvehListActivity.this, mikvot, keys);
                                }

                                @Override
                                public void DataIsInserted() {

                                }

                                @Override
                                public void DataIsUpdated() {

                                }

                                @Override
                                public void DataIsDeleted() {

                                }
                            });

                        }

                    }
                    else {
                        Log.d("Error","No such document");
                    }
                }
                else {
                    Log.d("Error","get failed with", task.getException());

                }
            }
        });
    }
}