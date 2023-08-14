package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OwnerProfile extends AppCompatActivity {
    private Button mAddMikveh;
    private Button mListMikveh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_profile);

        mAddMikveh = (Button) findViewById(R.id.own_addmikveh_btn);
        mListMikveh = (Button) findViewById(R.id.own_listmikveh_btn);

        mAddMikveh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OwnerProfile.this, NewMikvehActivity.class));
            }
        });
        mListMikveh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OwnerProfile.this, MikvehListActivity.class));
            }
        });
    }
}