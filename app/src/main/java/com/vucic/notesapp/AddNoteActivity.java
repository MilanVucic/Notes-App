package com.vucic.notesapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.vucic.notesapp.callbacks.ColorSelectedCallback;
import com.vucic.notesapp.custom_views.ColorSelector;
import com.vucic.notesapp.models.Note;
import com.vucic.notesapp.models.NoteColor;
import com.vucic.notesapp.repository.NotesRepository;
import com.vucic.notesapp.repository.RepositoryProvider;
import com.vucic.notesapp.repository.Tags;
import com.vucic.notesapp.viewmodels.NoteColorViewModel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddNoteActivity extends AppCompatActivity implements ColorSelectedCallback {

    public static final int NO_NOTE = -1;
    private static final int REQUEST_IMAGE_CAPTURE = 99;
    private EditText titleEditText;
    private EditText descriptionEditText;
    private CheckBox archiveCheckBox;
    private ImageView previewImageView;
    private ImageView removeImageView;
    private Group photoGroup;

    private NotesRepository notesRepository;
    private long noteId;
    private Note note;
    private ColorSelector colorSelector;
    String currentPhotoPath;

    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    dispatchTakePictureIntent();
                } else {
                    Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
                }
            });
    private Uri photoURI;

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
        previewImageView = findViewById(R.id.previewImageView);
        removeImageView = findViewById(R.id.removeImageView);
        photoGroup = findViewById(R.id.photoGroup);

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
        findViewById(R.id.takePhotoButton).setOnClickListener(v -> checkProperPermissions());
        removeImageView.setOnClickListener(v -> removeImage());
    }

    private void removeImage() {
        currentPhotoPath = null;
        photoGroup.setVisibility(View.GONE);
    }

    private void checkProperPermissions() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            dispatchTakePictureIntent();
        } else {
            requestPermissionLauncher.launch(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap imageBitmap = getImageFromURI(photoURI);
            previewImageView.setImageBitmap(imageBitmap);
            photoGroup.setVisibility(View.VISIBLE);
        }
    }

    @Nullable
    private Bitmap getImageFromURI(Uri photoURI) {
        Bitmap imageBitmap = null;
        try {
            imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoURI);
        } catch (IOException e) {
            Toast.makeText(this, "Something went wrong. Try again later.", Toast.LENGTH_SHORT).show();
        }
        return imageBitmap;
    }

    private void galleryAddPic() {
        MediaScannerConnection mediaScannerConnection =
                new MediaScannerConnection(this, new MediaScannerConnection.MediaScannerConnectionClient() {
            @Override
            public void onMediaScannerConnected() {

            }

            @Override
            public void onScanCompleted(String path, Uri uri) {
                Toast.makeText(AddNoteActivity.this, "Scan completed.", Toast.LENGTH_SHORT).show();
            }
        });
        mediaScannerConnection.scanFile(currentPhotoPath, null);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.vucic.notesapp", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
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
            if (!TextUtils.isEmpty(note.getPhotoPath())) {
                currentPhotoPath = note.getPhotoPath();

                File file = new File(note.getPhotoPath());
                if (file.exists()) {
                    photoURI = FileProvider.getUriForFile(this,
                            "com.vucic.notesapp", file);
                    Bitmap imageBitmap = getImageFromURI(photoURI);
                    previewImageView.setImageBitmap(imageBitmap);
                }
                photoGroup.setVisibility(View.VISIBLE);
            } else {
                photoGroup.setVisibility(View.GONE);
            }
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
            note.setPhotoPath(currentPhotoPath);
            notesRepository.addNote(note);
        } else {
            note.setTitle(title);
            note.setDescription(description);
            note.setArchived(archiveCheckBox.isChecked());
            note.setColorId(selectedColor.getNoteColor().getId());
            note.setPhotoPath(currentPhotoPath);
            notesRepository.editNote(note);
        }
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onColorSelected(NoteColor noteColor) {

    }
}