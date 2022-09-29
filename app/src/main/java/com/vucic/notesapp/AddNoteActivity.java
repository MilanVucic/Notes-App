package com.vucic.notesapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vucic.notesapp.models.Note;
import com.vucic.notesapp.repository.NotesRepository;
import com.vucic.notesapp.repository.RepositoryProvider;
import com.vucic.notesapp.repository.Tags;

public class AddNoteActivity extends AppCompatActivity {

    public static final int NO_NOTE = -1;
    private EditText titleEditText;
    private EditText descriptionEditText;
    private CheckBox archiveCheckBox;

    private NotesRepository notesRepository;
    private long noteId;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        notesRepository = RepositoryProvider.getInstance(this);
        Button saveButton = findViewById(R.id.saveButton);
        Button deleteButton = findViewById(R.id.deleteButton);
        saveButton.setOnClickListener(v -> saveNote());
        noteId = getIntent().getLongExtra(Tags.NOTE_ID, NO_NOTE);

        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        archiveCheckBox = findViewById(R.id.archiveCheckBox);
        if (noteId != NO_NOTE) {
            deleteButton.setOnClickListener(v -> deleteNote(noteId));
            deleteButton.setVisibility(View.VISIBLE);
            note = notesRepository.getNoteById(noteId);
            updateUI(note);
        } else {
            deleteButton.setVisibility(View.INVISIBLE);
        }
    }

    private void deleteNote(long noteId) {
        notesRepository.deleteNote(noteId);
        Toast.makeText(this, "Deleted note", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void updateUI(Note note) {
        if (note != null) {
            titleEditText.setText(note.getTitle());
            descriptionEditText.setText(note.getDescription());
            archiveCheckBox.setChecked(note.getArchived());
        }
    }

    private void saveNote() {
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        if(note == null) {
            Note note = new Note();
            note.setTitle(title);
            note.setDescription(description);
            note.setArchived(archiveCheckBox.isChecked());
            notesRepository.addNote(note);
        } else {
            note.setTitle(title);
            note.setDescription(description);
            note.setArchived(archiveCheckBox.isChecked());
            notesRepository.editNote(note);
        }
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        finish();
    }
}