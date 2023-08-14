package com.example.app;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;



public class FirebaseRealtimeDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private List<Mikveh> mikvot = new ArrayList<Mikveh>();


    public interface DataStatus {
        void DataIsLoaded(List<Mikveh> mikvot, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseRealtimeDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference();
    }

    public void addMikveh(Mikveh mikveh, final DataStatus status){
        String key = mReference.push().getKey();
        mReference.child(key).setValue(mikveh).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                status.DataIsInserted();
            }
        });
    }

    public void editMikveh(String key, Mikveh mikveh, final DataStatus status) {
        mReference.child(key).setValue(mikveh).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                status.DataIsUpdated();
            }
        });
    }

    public void deleteMikveh(String key, final DataStatus status) {
        mReference.child(key).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                status.DataIsDeleted();
            }
        });
    }

    public void readMikvehDB(final DataStatus status) {
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mikvot.clear();
                List<String> keys = new ArrayList<String>();
                for(DataSnapshot keyNode: snapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Mikveh mikveh = keyNode.getValue(Mikveh.class);
                    mikvot.add(mikveh);
                }
                status.DataIsLoaded(mikvot, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void readMikvehByPosition(String city ,DataStatus status) {
            mikvot.clear();
            List<String> keys = new ArrayList<String>();
            mReference.orderByChild("city").equalTo(city).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Mikveh mikveh = snapshot.getValue(Mikveh.class);
                mikvot.add(mikveh);
                keys.add(snapshot.getKey());
                status.DataIsLoaded(mikvot, keys);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void readMikvehByOwner(final DataStatus status) {
        String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mikvot.clear();
                List<String> keys = new ArrayList<String>();
                for(DataSnapshot keyNode: snapshot.getChildren()) {
                    Mikveh mikveh = keyNode.getValue(Mikveh.class);
                    if(mikveh.getOwner_ID().equals(user_id)){
                        keys.add(keyNode.getKey());
                        mikvot.add(mikveh);
                    }
                }
                status.DataIsLoaded(mikvot, keys);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }




}
