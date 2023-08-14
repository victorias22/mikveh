package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class UserMenuAppActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView user_menu_header, currentUsername, textView_user_menu, tollbarTitle;
    Spinner spinner;
    Button select_city_button;
    FirebaseAuth fAuth;
    String userID;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FirebaseApp.initializeApp(UserMenuAppActivity.this);
        setContentView(R.layout.activity_user_menu_app);

        user_menu_header = findViewById(R.id.admin_menu_header);
        currentUsername = findViewById(R.id.myUsername);
        textView_user_menu = findViewById(R.id.text_user_menu);
        spinner = findViewById(R.id.city_spinner);
        select_city_button = findViewById(R.id.select_button);

        fAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        //Get the current username login for header title
        DocumentReference documentReference = db.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//                currentUsername.setText(documentSnapshot.getString("userName"));
                currentUsername.setText(documentSnapshot.getString("userName"));
            }
        });

        //user menu has been clicked
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tollbarTitle = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);

        //Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cities_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //spinner.setOnItemSelectedListener(this);


        select_city_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //after user select some city for mikveh location

                String userCityChoice = spinner.getSelectedItem().toString();//saving user choice
                Intent intent = new Intent(UserMenuAppActivity.this, MikvehListActivity.class);
                intent.putExtra("cityChoosed", userCityChoice);
                startActivity(intent);

                //Toast.makeText(UserMenuAppActivity.this, "Chosen city: " + userCityChoice , Toast.LENGTH_SHORT).show();//printing user choice

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.item1:
                userData();
                return true;
            case R.id.item2:
                userMeetings();
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

    public void userData() {
        startActivity(new Intent(UserMenuAppActivity.this, UserProfile.class));
    }

    public void userMeetings() {
        startActivity(new Intent(UserMenuAppActivity.this, MyAppointment.class));
    }

    public void contactUs() {
        startActivity(new Intent(UserMenuAppActivity.this, ContactWithUs.class));
    }

    public void logout() {
        finish();
        FirebaseAuth.getInstance().signOut();//logout
        startActivity(new Intent(getApplicationContext(),SignInActivity.class));
    }

    @Override
    public void onItemSelected (AdapterView < ? > parent, View view,int position, long id) {
        String sSelected = parent.getItemAtPosition(position).toString();
        Toast.makeText(this, sSelected, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected (AdapterView < ? > parent) {}

}