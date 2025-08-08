package com.lucas.passwordvault.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.lucas.passwordvault.R;
import com.lucas.passwordvault.controller.DBController;
import com.lucas.passwordvault.controller.UserController;
import com.lucas.passwordvault.model.User;

public class ThirdActivity extends AppCompatActivity {
    private EditText et_Username, et_Email, et_Password;
    private Button btnRegister;
    private UserController usersController;
    private DBController dbController;
    private TextView tx_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_activity);

        usersController = new UserController(this);
        dbController = new DBController(this);
        et_Username = findViewById(R.id.et_Username);
        et_Email = findViewById(R.id.et_Email);
        et_Password = findViewById(R.id.et_Password);
        btnRegister = findViewById(R.id.btnRegister);
        tx_register = findViewById(R.id.textView2);

        btnRegister.setOnClickListener(v -> {
            if (et_Username.getText().toString().trim().isEmpty() || et_Password.getText().toString().trim().isEmpty() || et_Email.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, getString(R.string.msg_please_fill_in_all_fields), Toast.LENGTH_SHORT).show();
                return;
            } else if (et_Username.getText().toString().trim().length() < 3 && et_Username.getText().toString().trim().length() > 12) {
                Toast.makeText(this, getString(R.string.msg_error_User), Toast.LENGTH_SHORT).show();
                return;
            } else if (et_Password.getText().toString().trim().length() < 8) {
                Toast.makeText(this, getString(R.string.msg_error_Password), Toast.LENGTH_SHORT).show();
                return;
            }
            User user = new User(et_Username.getText().toString(), et_Password.getText().toString(), et_Email.getText().toString());
            usersController.saveUsers(user);
            dbController.insertData(user.getName(), user.getPassword(), user.getEmail());

            Toast.makeText(this, getString(R.string.msg_savedData), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(ThirdActivity.this, SecondActivity.class);
            startActivity(intent);
        });
        loadUser();
        tx_register.setOnClickListener(v -> {
            Intent intent = new Intent(ThirdActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    public void loadUser() {
        User user = usersController.loadUser();
        et_Username.setText(user.getName());
        et_Password.setText(user.getPassword());
        et_Email.setText(user.getEmail());
    }
}
