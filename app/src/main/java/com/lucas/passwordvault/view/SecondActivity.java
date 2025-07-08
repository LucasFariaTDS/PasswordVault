package com.lucas.passwordvault.view;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.lucas.passwordvault.R;
import com.lucas.passwordvault.controller.DBController_Email;
import com.lucas.passwordvault.controller.DBController_Password;
import com.lucas.passwordvault.controller.DBController_Username;
import com.lucas.passwordvault.model.Password;
import java.security.SecureRandom;
import java.util.Random;

public class SecondActivity extends AppCompatActivity {
    private TextView tx_Password, tx_Username, tx_Email;
    private Button btn_generatePassword, btn_generateUsernames, btn_generateEmail;
    private Button btn_savePassword, btn_saveUsername, btn_saveEmail;
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

        CreatNotification();

        dbControllerPassword = new DBController_Password(this);
        dbControllerEmail = new DBController_Email(this);
        dbControllerUsername = new DBController_Username(this);

        tx_Password = findViewById(R.id.tx_password);
        tx_Username = findViewById(R.id.tx_password2);
        tx_Email = findViewById(R.id.tx_password3);

        btn_generatePassword = findViewById(R.id.btn_generatePW);
        btn_generateUsernames = findViewById(R.id.btn_generateUN);
        btn_generateEmail = findViewById(R.id.btn_generateEM);

        btn_savePassword = findViewById(R.id.btn_savePW);
        btn_saveUsername = findViewById(R.id.btn_saveUN);
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
            SendNotification();
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
            email = nome.toLowerCase() + sufixo + "@gmail.com";
            tx_Email.setText(email);
        });

        btn_saveEmail.setOnClickListener(v -> {
            if (!email.isEmpty()) {
                dbControllerEmail.insertData(email);
                Toast.makeText(this, "Email saved successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Generate a email before saving.", Toast.LENGTH_SHORT).show();
                SendNotification();
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
            username = nome.toLowerCase() + "_" + sufixo;
            tx_Username.setText(username);
        });

        btn_saveUsername.setOnClickListener(v -> {
            if (!username.isEmpty()) {
                dbControllerUsername.insertData(username);
                Toast.makeText(this, "Username saved successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Generate a username before saving.", Toast.LENGTH_SHORT).show();
            }
            SendNotification();
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

    private void SendNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "canal_password")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Password Vault")
                .setContentText("Your generated data has been saved, thank you for your preference!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(1, builder.build());
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
        }
    }

    private void CreatNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel canal = new NotificationChannel("canal_password", "Notificações Password Vault", NotificationManager.IMPORTANCE_DEFAULT);
            canal.setDescription("Chanel for notification of order");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(canal);
        }
    }
}





