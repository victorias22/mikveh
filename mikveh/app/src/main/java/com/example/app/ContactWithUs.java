package com.example.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class ContactWithUs extends AppCompatActivity {
    public static final String TAG = "TAG";
    TextView usernameTXT, emailTXT, usernameVIEW, emailVIEW;
    Button send;
    String userID;
    EditText contctus;
    FirebaseAuth fAuth;
    Context context;
    AlertDialog builderAlert;
    FirebaseFirestore fstore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_with_us);

        usernameTXT = findViewById(R.id.username_txt);
        emailTXT = findViewById(R.id.useremail_txt);
        usernameVIEW = findViewById(R.id.username_view);
        emailVIEW = findViewById(R.id.useremail_view);
        send = findViewById(R.id.send_button);
        contctus = findViewById(R.id.content_et);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fstore.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                usernameVIEW.setText(documentSnapshot.getString("userName"));
                emailVIEW.setText(documentSnapshot.getString("email"));
            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String contact = contctus.getText().toString().trim();
                final String name = usernameVIEW.getText().toString();
                final String email = emailVIEW.getText().toString();

                userID = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fstore.collection("Contactus").document(userID);
                Map<String, Object> con = new HashMap<>();
                con.put("name", name);
                con.put("email", email);
                con.put("contact", contact);
                documentReference.set(con).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ContactWithUs.this, "the message has been sent", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(ContactWithUs.this, UserMenuAppActivity.class));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ContactWithUs.this, "Error! ", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(ContactWithUs.this, UserMenuAppActivity.class));
                    }
                });

            }
        });
    }
}





