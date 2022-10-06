package com.vucic.notesapp.viewmodels;

import com.vucic.notesapp.models.NoteColor;

public class NoteColorViewModel {
    private NoteColor noteColor;
    private boolean selected;

    public NoteColorViewModel(NoteColor noteColor) {
        this.noteColor = noteColor;
    }

    public NoteColor getNoteColor() {
        return noteColor;
    }

    public void setNoteColor(NoteColor noteColor) {
        this.noteColor = noteColor;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
