package com.vucic.notesapp.repository;

import android.app.Activity;

import com.vucic.notesapp.MyApp;
import com.vucic.notesapp.callbacks.LoginCallback;
import com.vucic.notesapp.models.Author;
import com.vucic.notesapp.models.AuthorDao;
import com.vucic.notesapp.models.DaoSession;
import com.vucic.notesapp.models.Note;
import com.vucic.notesapp.models.NoteColor;
import com.vucic.notesapp.models.NoteColorDao;
import com.vucic.notesapp.models.NoteDao;

import java.util.List;

public class LocalRepository implements NotesRepository {
    private DaoSession daoSession;
    private NoteDao noteDao;
    private AuthorDao authorDao;
    private NoteColorDao noteColorDao;
    private Author loggedInUser;
    private static LocalRepository instance;

    private LocalRepository(Activity activity) {
        daoSession = ((MyApp) activity.getApplication()).getDaoSession();
        noteDao = daoSession.getNoteDao();
        authorDao = daoSession.getAuthorDao();
        noteColorDao = daoSession.getNoteColorDao();
        seedColorsIfNeeded();
    }

    public static LocalRepository getInstance(Activity activity) {
        if (instance == null) {
            instance = new LocalRepository(activity);
        }
        return instance;
    }

    private void seedColorsIfNeeded() {
        List<NoteColor> noteColors = getNoteColors();
        if (noteColors == null || noteColors.isEmpty()) {
            NoteColor noteColor1 = new NoteColor();
            noteColor1.setColorCode("#FFFFEB3B");
            NoteColor noteColor2 = new NoteColor();
            noteColor2.setColorCode("#FFE91E63");
            NoteColor noteColor3 = new NoteColor();
            noteColor3.setColorCode("#FFFF9800");
            noteColorDao.insert(noteColor1);
            noteColorDao.insert(noteColor2);
            noteColorDao.insert(noteColor3);
        }
    }

    @Override
    public List<NoteColor> getNoteColors() {
        return noteColorDao.getSession().loadAll(NoteColor.class);
    }

    public Author getLoggedInUser() {
        return loggedInUser;
    }

    @Override
    public void addNote(Note note) {
        noteDao.insert(note);
        loggedInUser.getNotes().add(note);
    }

    @Override
    public void editNote(Note note) {
        noteDao.update(note);
    }

    @Override
    public Note getNoteById(long id) {
        return noteDao.getSession().load(Note.class, id);
    }

    @Override
    public List<Note> getAllNotes() {
        return loggedInUser.getNotes();
    }

    @Override
    public long getLoggedInUserId() {
        return loggedInUser.getId();
    }

    @Override
    public void deleteNote(long id) {
        Note note = getNoteById(id);
        noteDao.delete(note);
    }

    @Override
    public void signUp(Author author) {
        authorDao.insert(author);
    }

    @Override
    public void logIn(String name, String password, LoginCallback loginCallback) {
        List<Author> authors = authorDao.getSession().queryBuilder(Author.class)
                .where(AuthorDao.Properties.Name.eq(name)).build().list();
        if (authors.size() == 0) {
            loginCallback.onFailure("Username not found.");
        } else {
            Author author = authors.get(0);
            loggedInUser = author;
            if (author.getPassword().equals(password)) {
                loginCallback.onSuccess();
            } else {
                loginCallback.onFailure("Wrong password.");
            }
        }
    }
}
