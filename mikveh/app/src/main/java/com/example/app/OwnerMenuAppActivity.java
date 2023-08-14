package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class OwnerMenuAppActivity extends AppCompatActivity {

    TextView owner_menu_header, currentOwnerUsername, tollbarTitle;
    private Button mAddMikveh;
    private Button mListMikveh;

    FirebaseAuth fAuth;
    String userID;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_menu_app);

        owner_menu_header = findViewById(R.id.admin_menu_header);
        currentOwnerUsername = findViewById(R.id.myOwnerUsername);
        tollbarTitle = findViewById(R.id.toolbar_title);
        mAddMikveh = (Button) findViewById(R.id.own_add_btn);
        mListMikveh = (Button) findViewById(R.id.own_list_btn);

        fAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        //Get the current username login for header title
        DocumentReference documentReference = db.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                currentOwnerUsername.setText(documentSnapshot.getString("userName"));
            }
        });

        //owner menu has been clicked
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tollbarTitle = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);

        mAddMikveh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OwnerMenuAppActivity.this, NewMikvehActivity.class));
            }
        });
        mListMikveh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OwnerMenuAppActivity.this, MikvehListActivity.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.owner_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.item1:
                //owner profile
                return true;

            case R.id.item2:
                upcomingMeetings();
                return true;

            case R.id.item3:
                contactUs();
                return true;

            case R.id.item4:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    public void userData() {
//        startActivity(new Intent(UserMenuAppActivity.this, UserProfile.class));
////    }

    public void upcomingMeetings() {
        startActivity(new Intent(OwnerMenuAppActivity.this, MikvehListActivity.class));
    }

    public void contactUs() {
        startActivity(new Intent(OwnerMenuAppActivity.this, ContactWithUs.class));
    }

    public void logout() {
        finish();
        FirebaseAuth.getInstance().signOut();//logout
        startActivity(new Intent(getApplicationContext(),SignInActivity.class));
    }
}