package com.example.roomdemo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")

public class Note {
    @PrimaryKey(autoGenerate = true)
    int id;

    String title;
    String content;

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
