package com.vucic.notesapp.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;

@Entity()
public class Note {
    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String title;
    private String description;
    private boolean archived;
    private String photoPath;

    private long colorId;
    @ToOne(joinProperty = "colorId")
    private NoteColor noteColor;

    private long authorId;
    @ToOne(joinProperty = "authorId")
    private Author author;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 363862535)
    private transient NoteDao myDao;

    @Generated(hash = 1107320010)
    private transient Long author__resolvedKey;

    @Generated(hash = 2094086735)
    private transient Long noteColor__resolvedKey;

    @Generated(hash = 32310574)
    public Note(Long id, @NotNull String title, String description, boolean archived, String photoPath,
            long colorId, long authorId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.archived = archived;
        this.photoPath = photoPath;
        this.colorId = colorId;
        this.authorId = authorId;
    }
    @Generated(hash = 1272611929)
    public Note() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public boolean getArchived() {
        return this.archived;
    }
    public void setArchived(boolean archived) {
        this.archived = archived;
    }
    public long getAuthorId() {
        return this.authorId;
    }
    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 391889431)
    public Author getAuthor() {
        long __key = this.authorId;
        if (author__resolvedKey == null || !author__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            AuthorDao targetDao = daoSession.getAuthorDao();
            Author authorNew = targetDao.load(__key);
            synchronized (this) {
                author = authorNew;
                author__resolvedKey = __key;
            }
        }
        return author;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2013409422)
    public void setAuthor(@NotNull Author author) {
        if (author == null) {
            throw new DaoException(
                    "To-one property 'authorId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.author = author;
            authorId = author.getId();
            author__resolvedKey = authorId;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    public long getColorId() {
        return this.colorId;
    }
    public void setColorId(long colorId) {
        this.colorId = colorId;
    }
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 664563526)
    public NoteColor getNoteColor() {
        long __key = this.colorId;
        if (noteColor__resolvedKey == null || !noteColor__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            NoteColorDao targetDao = daoSession.getNoteColorDao();
            NoteColor noteColorNew = targetDao.load(__key);
            synchronized (this) {
                noteColor = noteColorNew;
                noteColor__resolvedKey = __key;
            }
        }
        return noteColor;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1149212713)
    public void setNoteColor(@NotNull NoteColor noteColor) {
        if (noteColor == null) {
            throw new DaoException(
                    "To-one property 'colorId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.noteColor = noteColor;
            colorId = noteColor.getId();
            noteColor__resolvedKey = colorId;
        }
    }
    public String getPhotoPath() {
        return this.photoPath;
    }
    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 799086675)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getNoteDao() : null;
    }
}
