package com.vucic.notesapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vucic.notesapp.adapters.NoteColorAdapter;
import com.vucic.notesapp.callbacks.ColorSelectedCallback;
import com.vucic.notesapp.custom_views.ColorSelector;
import com.vucic.notesapp.models.Note;
import com.vucic.notesapp.models.NoteColor;
import com.vucic.notesapp.repository.NotesRepository;
import com.vucic.notesapp.repository.RepositoryProvider;
import com.vucic.notesapp.repository.Tags;
import com.vucic.notesapp.viewmodels.NoteColorViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddNoteActivity extends AppCompatActivity implements ColorSelectedCallback {

    public static final int NO_NOTE = -1;
    private EditText titleEditText;
    private EditText descriptionEditText;
    private CheckBox archiveCheckBox;

    private NotesRepository notesRepository;
    private long noteId;
    private Note note;
    private ColorSelector colorSelector;

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

        List<NoteColor> noteColors = notesRepository.getNoteColors();
        List<NoteColorViewModel> viewModels = createViewModels(noteColors);

        if (noteId != NO_NOTE) {
            deleteButton.setOnClickListener(v -> deleteNote(noteId));
            deleteButton.setVisibility(View.VISIBLE);
            note = notesRepository.getNoteById(noteId);
            selectNoteColor(note, viewModels);
            updateUI(note);
        } else {
            deleteButton.setVisibility(View.INVISIBLE);
        }
        colorSelector = findViewById(R.id.noteColorSelector);
        colorSelector.setCallback(this);
        colorSelector.initializeColors(viewModels);
    }

    private void selectNoteColor(Note note, List<NoteColorViewModel> viewModels) {
        for (NoteColorViewModel noteColorVM : viewModels) {
            if (note.getColorId() == noteColorVM.getNoteColor().getId()) {
                noteColorVM.setSelected(true);
                break;
            }
        }
    }

    private List<NoteColorViewModel> createViewModels(List<NoteColor> noteColors) {
        List<NoteColorViewModel> list = new ArrayList<>();
        for (NoteColor noteColor : noteColors) {
            list.add(new NoteColorViewModel(noteColor));
        }
        return list;
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
        NoteColorViewModel selectedColor = colorSelector.getSelectedColor();
        if (selectedColor == null) {
            Toast.makeText(this, "Please pick a color.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (note == null) {
            Note note = new Note();
            note.setTitle(title);
            note.setDescription(description);
            note.setArchived(archiveCheckBox.isChecked());
            note.setAuthorId(notesRepository.getLoggedInUserId());
            note.setColorId(selectedColor.getNoteColor().getId());
            notesRepository.addNote(note);
        } else {
            note.setTitle(title);
            note.setDescription(description);
            note.setArchived(archiveCheckBox.isChecked());
            note.setColorId(selectedColor.getNoteColor().getId());
            notesRepository.editNote(note);
        }
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onColorSelected(NoteColor noteColor) {
        Toast.makeText(this, "You selected:" + noteColor.getColorCode(), Toast.LENGTH_SHORT).show();
    }
}