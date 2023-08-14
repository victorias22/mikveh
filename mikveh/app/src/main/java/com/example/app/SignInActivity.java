package com.example.app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignInActivity extends AppCompatActivity {
    Button LoginButn, creatButn;
    EditText eMail, Password;
    TextView forgotPassword;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
//    String userID, profile;


    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        FirebaseApp.initializeApp(SignInActivity.this);

        setContentView(R.layout.activity_sign_in);

        LoginButn = findViewById(R.id.email_sign_in_button);
        creatButn = findViewById(R.id.create_acct_button_login);
        eMail = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        forgotPassword = findViewById(R.id.forgot_password_link);
        progressBar = findViewById(R.id.login_progress);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();

        LoginButn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = eMail.getText().toString().trim();
                String password = Password.getText().toString().trim();

//                String email = "adam370@hotmail.fr";
//                String password = "secret";

                if (TextUtils.isEmpty(email)) {
                    eMail.setError("Email is Required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Password.setError("Password is Required.");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // authenticate the user
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            DocumentReference docRef = fStore.collection("Users").document(user.getUid());
                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot doc = task.getResult();
                                        if (doc.exists()) {
                                            String mail = (String) doc.getString("email");
                                            String profile = (String) doc.getString("profile");
                                            if (mail.equals(email)) {
                                                if (profile.equals("User")) {
                                                    Toast.makeText(SignInActivity.this, "User Logged in Successfully", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(getApplicationContext(), UserMenuAppActivity.class));
                                                }
                                                else if (profile.equals("Admin")) {
                                                    Toast.makeText(SignInActivity.this, "Admin Logged in Successfully", Toast.LENGTH_SHORT).show();
//                                                    startActivity(new Intent(getApplicationContext(), AdminMenuAppActivity.class));
                                                    startActivity(new Intent(getApplicationContext(), AdminManageAppUsers.class));
                                                }
                                                else {
                                                    Toast.makeText(SignInActivity.this, "Mikveh Owner Logged in Successfully", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(getApplicationContext(), OwnerMenuAppActivity.class));
                                                }
                                            }
                                            else {
                                                Toast.makeText(SignInActivity.this, "Email not found in the System. Please sign up :)", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
                                            }
                                        }
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(SignInActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });

            }
        });

        creatButn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
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
                                Toast.makeText(SignInActivity.this, "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignInActivity.this, "Error ! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
    }
}


