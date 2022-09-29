package com.vucic.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.vucic.notesapp.callbacks.LoginCallback;
import com.vucic.notesapp.repository.NotesRepository;
import com.vucic.notesapp.repository.RepositoryProvider;

public class LoginActivity extends AppCompatActivity implements LoginCallback {

    private TextInputLayout usernameTextInputLayout, passwordTextInputLayout;
    private EditText usernameEditText, passwordEditText;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.signInButton).setOnClickListener(v ->
                trySignIn(usernameEditText.getText().toString(), passwordEditText.getText().toString()));
        findViewById(R.id.signUpButton).setOnClickListener(v ->
                        goToSignUp());
        setupUI();
    }

    private void goToSignUp() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    private void trySignIn(String username, String password) {
        if (TextUtils.isEmpty(username)) {
            usernameEditText.requestFocus();
            usernameTextInputLayout.setError("Username cannot be empty");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            passwordEditText.requestFocus();
            passwordTextInputLayout.setError("Password cannot be empty");
            return;
        }

        NotesRepository notesRepository = RepositoryProvider.getInstance(this);
        notesRepository.logIn(username, password, this);
    }

    private void setupUI() {
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        usernameTextInputLayout = findViewById(R.id.usernameTextInputLayout);
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout);

    }

    @Override
    public void onSuccess() {
        Toast.makeText(this, "Yay", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}