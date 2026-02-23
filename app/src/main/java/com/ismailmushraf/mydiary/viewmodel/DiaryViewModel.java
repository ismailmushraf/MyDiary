package com.ismailmushraf.mydiary.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ismailmushraf.mydiary.data.repository.DiaryRepository;
import com.ismailmushraf.mydiary.model.DiaryEntry;

import java.util.List;

public class DiaryViewModel extends AndroidViewModel {

    private final DiaryRepository repository;
    private final LiveData<List<DiaryEntry>> allEntries;

    public DiaryViewModel(@NonNull Application application) {
        super(application);
        repository = new DiaryRepository(application);
        allEntries = repository.getAllEntries();
    }

    public LiveData<List<DiaryEntry>> getAllEntries() {
        return allEntries;
    }

    public void insert(DiaryEntry entry) {
        repository.insert(entry);
    }

    public void update(DiaryEntry entry) {
        repository.update(entry);
    }

    public void delete(DiaryEntry entry) {
        repository.delete(entry);
    }
}
