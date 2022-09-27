package com.vucic.notesapp.repository;

import com.vucic.notesapp.models.Note;

import java.util.List;

public class RemoteRepository implements NotesRepository{
    @Override
    public void add(Note note) {

    }

    @Override
    public void edit(Note note) {

    }

    @Override
    public Note getById(long id) {
        return null;
    }

    @Override
    public List<Note> getAllNotes() {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
