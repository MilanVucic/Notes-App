package com.vucic.notesapp.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity()
public class NoteColor {
    @Id(autoincrement = true)
    private Long id;

    private String colorCode; // #123ff2

    @Generated(hash = 1193980650)
    public NoteColor(Long id, String colorCode) {
        this.id = id;
        this.colorCode = colorCode;
    }

    @Generated(hash = 1913159339)
    public NoteColor() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColorCode() {
        return this.colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }
}
