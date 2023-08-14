package com.example.app;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AdminEditProfile extends AppCompatActivity {
    public static final String TAG = "TAG";

    ImageView img;
    EditText EditAdminUserName, EditAdminEmail;
    Button save;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    FirebaseUser adm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_edit_profile);

        //for moving current admin data to -> edit profile fields
        Intent data = getIntent();
        String userName = data.getStringExtra("userName");
        String email = data.getStringExtra("email");

        img = findViewById(R.id.admin_icon);
        EditAdminUserName = findViewById(R.id.edit_adm_username);
        EditAdminEmail = findViewById(R.id.edit_adm_email);
        save = findViewById(R.id.save_changes);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        adm = fAuth.getCurrentUser();

        EditAdminUserName.setText(userName);
        EditAdminEmail.setText(email);

        //save changes button
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (EditAdminUserName.getText().toString().isEmpty() || EditAdminEmail.getText().toString().isEmpty()) {
                    Toast.makeText(AdminEditProfile.this, "One or Many fields are empty.", Toast.LENGTH_SHORT).show();
                }

                String email = EditAdminEmail.getText().toString();
                adm.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference docRef = fstore.collection("Users").document(adm.getUid());
                        Map<String, Object> edited = new HashMap<>();
                        edited.put("userName", EditAdminUserName.getText().toString());
                        edited.put("email", email);
                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(AdminEditProfile.this, "Profile has been changed successfully.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AdminEditProfile.this, AdminProfile.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AdminEditProfile.this, "Ops! Something went wrong. please Try again.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        //Toast.makeText(UserEditProfile.this, "Email is changed.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminEditProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }


}
