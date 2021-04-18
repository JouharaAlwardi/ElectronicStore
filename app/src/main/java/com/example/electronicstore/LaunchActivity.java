package com.example.electronicstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LaunchActivity extends AppCompatActivity {

    private Button userTitle, adminstratorTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        userTitle = (Button) findViewById(R.id.userTitle);
        adminstratorTitle = (Button) findViewById(R.id.adminTitle);


       userTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(LaunchActivity.this, Login.class));
            }
        });

        adminstratorTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LaunchActivity.this, AdminstratorLogin.class));
            }
        });
    }
}