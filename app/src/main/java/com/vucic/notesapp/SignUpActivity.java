package com.vucic.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.vucic.notesapp.models.Author;
import com.vucic.notesapp.repository.NotesRepository;
import com.vucic.notesapp.repository.RepositoryProvider;

public class SignUpActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        passwordEditText = findViewById(R.id.passwordEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        findViewById(R.id.signUpButton).setOnClickListener(v -> trySignUp());
    }

    private void trySignUp() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        Author author = new Author();
        author.setName(username);
        author.setPassword(password);
        NotesRepository notesRepository = RepositoryProvider.getInstance(this);
        notesRepository.signUp(author);
        finish();
    }
}