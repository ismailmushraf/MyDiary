package com.ismailmushraf.mydiary.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Todo implements Parcelable {
    private String title;
    private String description;
    private boolean isCompleted;

    public Todo(String title, String description) {
        this.title = title;
        this.description = description;
        this.isCompleted = false;
    }

    public Todo(Parcel in) {
        title = in.readString();
        description = in.readString();
        isCompleted = in.readByte() != 0;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public void toggleCompletion() {
        isCompleted = !isCompleted;
    }

    // 🔹 Required CREATOR
    public static final Creator<Todo> CREATOR = new Creator<Todo>() {
        @Override
        public Todo createFromParcel(Parcel in) {
            return new Todo(in);
        }

        @Override
        public Todo[] newArray(int size) {
            return new Todo[size];
        }
    };

    // 🔹 Write data into Parcel
    @Override
    public void writeToParcel(@NonNull Parcel parcel, int flags) {
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeByte((byte) (isCompleted ? 1 : 0));
    }

    // 🔹 Usually return 0
    @Override
    public int describeContents() {
        return 0;
    }
}
