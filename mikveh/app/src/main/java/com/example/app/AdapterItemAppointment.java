package com.example.app;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterItemAppointment extends ArrayAdapter<dataUser> {

    FirebaseFirestore db;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userProfile;

    // constructor for our list view adapter.
    public AdapterItemAppointment(@NonNull Context context, ArrayList<dataUser> dataArrayList) {
        super(context, 0, dataArrayList);
        db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.item_layout, parent, false);
        }

        dataUser data_model = getItem(position);

        // initializing our UI components of list view item.
        TextView name = listitemView.findViewById(R.id.meetingUsername);
        TextView address = listitemView.findViewById(R.id.meetingAddress);
        TextView city = listitemView.findViewById(R.id.meetingCity);
        TextView date = listitemView.findViewById(R.id.txt_date_item);
        TextView time = listitemView.findViewById(R.id.txt_time_item);
        ImageButton delete = listitemView.findViewById(R.id.imageView);

        name.setText(data_model.getName());
        address.setText(data_model.getAddress());
        city.setText(data_model.getCity());
        date.setText(data_model.getDate());
        time.setText(data_model.getTime());

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userProfile = fAuth.getCurrentUser().getUid();

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .collection("Appointments").document(data_model.getID()).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("Meeting has been removed.", "Meeting has been removed.");
                                Toast.makeText(getContext(), "Appointments successfully deleted", Toast.LENGTH_SHORT).show();
                                remove(data_model);
                                notifyDataSetChanged();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("Error!", "");
                            }
                        });
                db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .collection("UpcomingMeetings").document(data_model.getID()).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("Meeting has been removed.", "Meeting has been removed.");
                                Toast.makeText(getContext(), "Appointments successfully deleted", Toast.LENGTH_SHORT).show();
                                remove(data_model);
                                notifyDataSetChanged();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("Error!", "");
                            }
                        });
            }

        });
        return listitemView;

    }
}
