package com.kinire.proyectointegrador.android.ui.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.kinire.proyectointegrador.android.R;
import com.kinire.proyectointegrador.android.controllers.activities.UserActivityController;

public class UserActivity extends AppCompatActivity {

    private EditText userField;
    private Button button;

    private UserActivityController controller;

    private String invalidUserName;
    private String incorrectAdminPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        this.initializeElements();
        this.setListeners();
    }

    private void initializeElements() {
        this.userField = findViewById(R.id.user_write);
        this.button = findViewById(R.id.accept_username);
        this.incorrectAdminPassword = getString(R.string.incorrect_admin_password);
        this.invalidUserName = getString(R.string.invalid_user_name);
        this.controller = new UserActivityController(this);
    }

    public void setListeners() {
        this.button.setOnClickListener(controller);
    }

    public String getUserNameContent() {
        return userField.getText().toString();
    }

    public void invalidUserName() {
        Toast.makeText(this, invalidUserName, Toast.LENGTH_SHORT).show();
    }
    
    public void incorrectAdminPassword() {
        Toast.makeText(this, incorrectAdminPassword, Toast.LENGTH_SHORT).show();
    }

    public String askForPassword() {
        return "";
    }
}