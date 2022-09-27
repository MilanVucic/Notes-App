package com.vucic.notesapp.repository;

import com.vucic.notesapp.models.Note;

import java.util.List;

public interface NotesRepository {
    void add(Note note);
    void edit(Note note);
    Note getById(long id);
    List<Note> getAllNotes();
    void delete(long id);
}