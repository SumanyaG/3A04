package com.example.securechat.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.securechat.Main;
import com.example.securechat.R;

public class SignupActivity extends AppCompatActivity {
    Button signupButton;

    private void signupSuccessful() {
        Toast.makeText(SignupActivity.this, "Sign up successful!", Toast.LENGTH_SHORT).show();

        Intent mainIntent = new Intent(SignupActivity.this, Main.class);
        startActivity(mainIntent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupButton = findViewById(R.id.signupButton);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignupActivity.this, "Sign up successful!", Toast.LENGTH_SHORT).show();
                signupSuccessful();
            }
        });
    }
}