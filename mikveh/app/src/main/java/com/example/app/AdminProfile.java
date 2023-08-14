package com.example.app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class AdminProfile extends AppCompatActivity {
    public static final String TAG = "TAG";

    ImageView adminImage, emailIcon;
    TextView admUsernameTXT, admUsernameVIEW, admEmailTXT, admEmailVIEW, admProfileTXT, admProfileVIEW;
    Button editProfile, resetPassword, goBack, logout;
    String adminID;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_profile);

        adminImage = findViewById(R.id.admin_image);
        emailIcon = findViewById(R.id.mail_image);
        admUsernameTXT = findViewById(R.id.admin_username_txt);
        admUsernameVIEW = findViewById(R.id.admin_username_view);
        admEmailTXT = findViewById(R.id.admin_email_txt);
        admEmailVIEW = findViewById(R.id.admin_email_view);
        admProfileTXT = findViewById(R.id.profile_txt);
        admProfileVIEW = findViewById(R.id.profile_view);
        editProfile = findViewById(R.id.adm_edit_button);
        resetPassword = findViewById(R.id.resetPassword_button);
        goBack = findViewById(R.id.goback_button);
        logout = findViewById(R.id.logout_button);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        adminID = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("Users").document(adminID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                admUsernameVIEW.setText(documentSnapshot.getString("userName"));
                admEmailVIEW.setText(documentSnapshot.getString("email"));
                admProfileVIEW.setText(documentSnapshot.getString("profile"));
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminProfile.this, AdminEditProfile.class));
                Intent x = new Intent(AdminProfile.this, AdminEditProfile.class);
                x.putExtra("userName", admUsernameVIEW.getText().toString());
                x.putExtra("email", admEmailVIEW.getText().toString());
                startActivity(x);
            }
        });

        //Reset password button
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText resetMail = new EditText(v.getContext());
                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter Your Email To Received Reset Link.");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // extract the email and send reset link
                        String mail = resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(AdminProfile.this, "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AdminProfile.this, "Error ! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // close the dialog
                    }
                });
                passwordResetDialog.create().show();
            }
        });

        //Go back button
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminProfile.this, AdminManageAppUsers.class));
            }
        });

        //Logout button
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();//logout
                startActivity(new Intent(getApplicationContext(),SignInActivity.class));
                finish();
            }
        });

    }


}
