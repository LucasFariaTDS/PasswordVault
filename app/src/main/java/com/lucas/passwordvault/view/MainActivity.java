package com.lucas.passwordvault.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.lucas.passwordvault.DB.DB;
import com.lucas.passwordvault.DB.KeyHelper;
import com.lucas.passwordvault.R;


public class MainActivity extends AppCompatActivity {
    private EditText et_Username, et_Email, et_Password;
    private Button btnRegister;
    private TextView tx_login;
    private DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        db = new DB(this);
        et_Username = findViewById(R.id.et_Username);
        et_Email = findViewById(R.id.et_Email);
        et_Password = findViewById(R.id.et_Password);
        btnRegister = findViewById(R.id.btnRegister);
        tx_login = findViewById(R.id.login);

        btnRegister.setOnClickListener(v -> {
            String password = et_Password.getText().toString().trim();
            String email = et_Email.getText().toString().trim();
            String username = et_Username.getText().toString().trim();
            if (!password.isEmpty() && !email.isEmpty() && !username.isEmpty()) {
                if (db.verifyUser(password, email, username)) {
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    startActivity(intent);
                    try {
                        KeyHelper.createKeyIfNotExists();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "Invalid data!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
            }

        });
        tx_login.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
            startActivity(intent);
        });
    }
}

