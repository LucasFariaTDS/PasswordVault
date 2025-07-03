package com.lucas.passwordvault.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.lucas.passwordvault.R;

public class SecondActivity extends AppCompatActivity {
    private TextView tx_Password, tx_Username, tx_Email;
    private Button btn_generatePassword, btn_generateUsernames, btn_generateEmail, btn_savePassword, btn_saveUsernames, btn_saveEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.second_activity);

        tx_Password = findViewById(R.id.tx_password);
        tx_Username = findViewById(R.id.tx_password2);
        tx_Email = findViewById(R.id.tx_password3);

        btn_generatePassword = findViewById(R.id.btn_generatePW);
        btn_generateUsernames = findViewById(R.id.btn_generateUN);
        btn_generateEmail = findViewById(R.id.btn_generateEM);

        btn_savePassword = findViewById(R.id.btn_savePW);
        btn_saveUsernames = findViewById(R.id.btn_saveUN);
        btn_generateEmail = findViewById(R.id.btn_saveEM);



    }
}

