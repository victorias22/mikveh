package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

public class UserEditProfile extends AppCompatActivity {
    public static final String TAG = "TAG";

    ImageView user_image;
    EditText editUsername, editEmail;
    Button save;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_edit_profile);

        //for moving current user data to -> edit profile fields
        Intent data = getIntent();
        String userName = data.getStringExtra("userName");
        String email = data.getStringExtra("email");

        user_image = findViewById(R.id.user_image);
        editUsername = findViewById(R.id.editTextUserName);
        editEmail = findViewById(R.id.editTextUserEmail);
        save = findViewById(R.id.save_changes_button);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();

        editUsername.setText(userName);
        editEmail.setText(email);

        //save changes button
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editUsername.getText().toString().isEmpty() || editEmail.getText().toString().isEmpty()) {
                    Toast.makeText(UserEditProfile.this, "One or Many fields are empty.", Toast.LENGTH_SHORT).show();
                }

                String email = editEmail.getText().toString();
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference docRef = fstore.collection("Users").document(user.getUid());
                        Map<String, Object> edited = new HashMap<>();
                        edited.put("userName", editUsername.getText().toString());
                        edited.put("email", email);
                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(UserEditProfile.this, "Profile has been changed successfully.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(UserEditProfile.this, UserProfile.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UserEditProfile.this, "Ops! Something went wrong. please Try again.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        //Toast.makeText(UserEditProfile.this, "Email is changed.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UserEditProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

    }


}
