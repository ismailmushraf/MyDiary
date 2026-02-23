package com.ismailmushraf.mydiary.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ismailmushraf.mydiary.model.DiaryEntry;

@Database(entities = {DiaryEntry.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;
    public abstract DiaryDao diaryDao();

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "diary_database"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}