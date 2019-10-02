package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences;
    EditText editEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab3);

        editEmail = findViewById(R.id.userEmail);
        preferences = getSharedPreferences("prefsFileName", Context.MODE_PRIVATE);
        String saveEmail = preferences.getString("ReserveName", "");
        editEmail.setText(saveEmail);

        Button loginBtn = findViewById(R.id.loginButton);
        loginBtn.setOnClickListener(v -> {

            Intent profile = new Intent(MainActivity.this, ProfileActivity.class);

            profile.putExtra("EmailTyped", editEmail.getText().toString());
            startActivity(profile);


        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("ReserveName", editEmail.getText().toString());
        edit.apply();


    }
}