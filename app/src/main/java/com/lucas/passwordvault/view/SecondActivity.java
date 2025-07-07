package com.lucas.passwordvault.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.lucas.passwordvault.R;
import com.lucas.passwordvault.controller.DBController_Email;
import com.lucas.passwordvault.controller.DBController_Password;
import com.lucas.passwordvault.controller.DBController_Username;
import com.lucas.passwordvault.model.Email;
import com.lucas.passwordvault.model.Password;
import com.lucas.passwordvault.model.Username;

import java.security.SecureRandom;
import java.util.Random;

public class SecondActivity extends AppCompatActivity {
    private TextView tx_Password, tx_Username, tx_Email;
    private Button btn_generatePassword, btn_generateUsernames, btn_generateEmail;
    private Button btn_savePassword, btn_saveUsernames, btn_saveEmail;
    private DBController_Password dbControllerPassword;
    private DBController_Email dbControllerEmail;
    private DBController_Username dbControllerUsername;
    private EditText et_name;
    private String password = "";
    private String email = "";
    private String username = "";

    private static final String all = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&{}/_?.,><()+=";
    private final SecureRandom random = new SecureRandom();

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
        btn_saveEmail = findViewById(R.id.btn_saveEM);

        et_name = findViewById(R.id.et_name);


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

        btn_savePassword.setOnClickListener(v -> {
            if (!password.isEmpty()) {
                Password password1 = new Password(password);
                dbControllerPassword.insertData(password1.getPassword());
                Toast.makeText(this, "Password saved successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Generate a password before saving.", Toast.LENGTH_SHORT).show();
            }
        });


        btn_generateEmail.setOnClickListener(v -> {
            String nome = et_name.getText().toString().trim();

            if (nome.isEmpty()) {
                Toast.makeText(this, "Enter a valid name", Toast.LENGTH_SHORT).show();
                return;
            }

            if (nome.length() > 10) {
                Toast.makeText(this, "Name must be up to 9 characters long", Toast.LENGTH_SHORT).show();
                return;
            }
            String sufixo = gerarSufixoAleatorio();
            email = nome.toLowerCase() + sufixo + "@email.com";
            tx_Email.setText(email);
        });

        btn_saveEmail.setOnClickListener(v -> {
            if (!email.isEmpty()) {
                Email email1 = new Email(email);
                dbControllerEmail.insertData(email1.getEmail());
                Toast.makeText(this, "Email saved successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Generate a email before saving.", Toast.LENGTH_SHORT).show();
            }
        });


        btn_generateUsernames.setOnClickListener(v -> {
            String nome = et_name.getText().toString().trim();

            if (nome.isEmpty()) {
                Toast.makeText(this, "Enter a valid name", Toast.LENGTH_SHORT).show();
                return;
            }

            if (nome.length() > 10) {
                Toast.makeText(this, "Name must be up to 9 characters long", Toast.LENGTH_SHORT).show();
                return;
            }
            String sufixo = gerarSufixoAleatorio();
            String usuario = nome.toLowerCase() + "_" + sufixo;
            tx_Username.setText(usuario);
        });

        btn_saveUsernames.setOnClickListener(v -> {
            if (!username.isEmpty()) {
                Username username1 = new Username(username);
                dbControllerUsername.insertData(username1.getUsername());
                Toast.makeText(this, "Username saved successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Generate a username before saving.", Toast.LENGTH_SHORT).show();
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

    private String gerarSufixoAleatorio() {
        String letras = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder sufixo = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            sufixo.append(letras.charAt(random.nextInt(letras.length())));
        }
        for (int i = 0; i < 2; i++) {
            sufixo.append(random.nextInt(10));
        }
        return sufixo.toString();
    }
}





