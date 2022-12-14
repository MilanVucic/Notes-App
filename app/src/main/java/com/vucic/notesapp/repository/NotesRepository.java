package com.vucic.notesapp.repository;

import com.vucic.notesapp.callbacks.LoginCallback;
import com.vucic.notesapp.models.Author;
import com.vucic.notesapp.models.Note;
import com.vucic.notesapp.models.NoteColor;

import java.util.List;

public interface NotesRepository {
    void addNote(Note note);
    void editNote(Note note);
    Note getNoteById(long id);
    List<Note> getAllNotes();
    void deleteNote(long id);
    long getLoggedInUserId();
    List<NoteColor> getNoteColors();

    void signUp(Author author);
    void logIn(String name, String password, LoginCallback loginCallback);
}