package com.example.pasgenap10rpl121;

public class NotesModel {
    private String title;
    private String note;
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public NotesModel(String title, String note) {
        this.title = title;
        this.note = note;
    }

    public NotesModel(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


}
