package com.ismailmushraf.mydiary.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "diary_entries")
public class DiaryEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String description;
    private long createdAt;
    private long updatedAt;

    public DiaryEntry(String title, String description, long createdAt) {
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
