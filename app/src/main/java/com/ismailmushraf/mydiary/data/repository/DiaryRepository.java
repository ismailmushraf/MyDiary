package com.ismailmushraf.mydiary.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.ismailmushraf.mydiary.data.local.AppDatabase;
import com.ismailmushraf.mydiary.data.local.DiaryDao;
import com.ismailmushraf.mydiary.model.DiaryEntry;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DiaryRepository {
    private DiaryDao diaryDao;
    private LiveData<List<DiaryEntry>> allEntries;
    private Executor executor = Executors.newSingleThreadExecutor();

    public DiaryRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        diaryDao = db.diaryDao();
        allEntries = diaryDao.getAllEntries();
    }

    public LiveData<List<DiaryEntry>> getAllEntries() {
        return allEntries;
    }

    public void insert(DiaryEntry entry) {
        executor.execute(() -> diaryDao.insert(entry));
    }

    public void update(DiaryEntry entry) {
        executor.execute(() -> diaryDao.update(entry));
    }

    public void delete(DiaryEntry entry) {
        executor.execute(() -> diaryDao.delete(entry));
    }
}
