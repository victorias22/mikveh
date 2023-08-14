package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

public class AdminMenuAppActivity extends AppCompatActivity {

    TextView admin_menu_header, tollbarTitle;
    private Button mListMikveh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu_app);

        admin_menu_header = findViewById(R.id.admin_menu_header);
        tollbarTitle = findViewById(R.id.toolbar_title);
        mListMikveh = (Button) findViewById(R.id.adm_list_btn);

        //admin menu has been clicked
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tollbarTitle = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);

        mListMikveh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminMenuAppActivity.this, MikvehListActivity.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.item1:
                adminData();
                return true;

            case R.id.item2:
                logout();;
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void adminData() {
        startActivity(new Intent(AdminMenuAppActivity.this, AdminProfile.class));
    }

    public void admManage() {
        startActivity(new Intent(AdminMenuAppActivity.this, AdminManageAppUsers.class));

    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();//logout
        finish();
        startActivity(new Intent(getApplicationContext(),SignInActivity.class));
    }
}
