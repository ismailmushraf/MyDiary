package com.ismailmushraf.mydiary.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ismailmushraf.mydiary.model.DiaryEntry;

import java.util.List;

@Dao
public interface DiaryDao {
    @Insert
    void insert(DiaryEntry entry);

    @Update
    void update(DiaryEntry entry);

    @Delete
    void delete(DiaryEntry entry);

    @Query("SELECT * FROM diary_entries ORDER BY createdAt DESC")
    LiveData<List<DiaryEntry>> getAllEntries();
}
