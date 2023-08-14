package com.example.app;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UsersListAdapter extends ArrayAdapter<UsersDataModel> {
    FirebaseFirestore db;

    // constructor for our list view adapter.
    public UsersListAdapter(@NonNull Context context, ArrayList<UsersDataModel> dataModalArrayList) {
        super(context, 0, dataModalArrayList);
        db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.users_list_item, parent, false);
        }

        UsersDataModel dataModal = getItem(position);

        // initializing our UI components of list view item.
        TextView userName = listitemView.findViewById(R.id.username_txt);
        TextView profile = listitemView.findViewById(R.id.profile_txt);
        TextView email = listitemView.findViewById(R.id.email_txt);
        ImageButton delete = listitemView.findViewById(R.id.imageView);


        userName.setText(dataModal.getUsername());
        profile.setText(dataModal.getProfile());
        email.setText(dataModal.getEmail());

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<dataUser> meetings = new ArrayList<dataUser>();
                db.collection("Users").document(dataModal.getID()).collection("Appointments").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> subColl = queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot doc : subColl) {
                                dataUser meeting = doc.toObject(dataUser.class);
                                meeting.setID(doc.getId());
                                meetings.add(meeting);
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Database Error", "Error loading from Appointments db");
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(dataUser e: meetings) {
                            Log.d("Address", e.getAddress());
                            db.collection("Users").document(dataModal.getID())
                            .collection("Appointments").document(e.getID()).delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                        }
                        Toast.makeText(getContext(), "Appointments successfully deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                db.collection("Users").document(dataModal.getID()).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "User deleted successfully", Toast.LENGTH_SHORT).show();
                        remove(dataModal);
                        notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Error deleting user", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        return listitemView;
    }
}
