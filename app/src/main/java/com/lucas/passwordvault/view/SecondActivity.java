package com.lucas.passwordvault.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.lucas.passwordvault.DB.DBPassword;
import com.lucas.passwordvault.R;
import com.lucas.passwordvault.controller.DBController_Password;
import com.lucas.passwordvault.model.Password;

import java.security.SecureRandom;

public class SecondActivity extends AppCompatActivity {
    private TextView tx_Password, tx_Username, tx_Email;
    private Button btn_generatePassword, btn_generateUsernames, btn_generateEmail, btn_savePassword, btn_saveUsernames, btn_saveEmail;
    private DBController_Password dbControllerPassword;
    private String password = "";

    private static final String all = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&{}/_?.,><()+=";
    private static final SecureRandom random = new SecureRandom();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.second_activity);

        dbControllerPassword = new DBController_Password(this);

        tx_Password = findViewById(R.id.tx_password);
        tx_Username = findViewById(R.id.tx_password2);
        tx_Email = findViewById(R.id.tx_password3);

        btn_generatePassword = findViewById(R.id.btn_generatePW);
        btn_generateUsernames = findViewById(R.id.btn_generateUN);
        btn_generateEmail = findViewById(R.id.btn_generateEM);

        btn_savePassword = findViewById(R.id.btn_savePW);
        btn_saveUsernames = findViewById(R.id.btn_saveUN);
        btn_generateEmail = findViewById(R.id.btn_saveEM);

        btn_generatePassword.setOnClickListener(v -> {
            int length = 12;

            do {
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < length; i++) {
                    int index = random.nextInt(all.length());
                    builder.append(all.charAt(index));
                }
                password = builder.toString();
            } while (!isStrongPassword(password));

            tx_Password.setText(password);
        });
        btn_savePassword.setOnClickListener(v->{
            if (!password.isEmpty()){
                Password password1 = new Password(password);
                dbControllerPassword.insertData(password1.getPassword());
                Toast.makeText(this, "Password saved successfully!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Generate a password before saving.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static boolean isStrongPassword(String password) {
        return password.length() >= 12 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*[0-9].*") &&
                password.matches(".*[!@#$%&{}/_?.,><()+=].*");
    }
}


