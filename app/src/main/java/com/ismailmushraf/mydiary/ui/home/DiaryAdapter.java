package com.ismailmushraf.mydiary.ui.home;

import static com.ismailmushraf.mydiary.utils.DateUtils.formatDate;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ismailmushraf.mydiary.R;
import com.ismailmushraf.mydiary.model.DiaryEntry;

import java.util.List;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder> {

    private final List<DiaryEntry> diaryEntries;
    private final OnItemActionListener actionListener;


    public DiaryAdapter(List<DiaryEntry> diaryEntries, OnItemActionListener listener) {
        this.diaryEntries = diaryEntries;
        this.actionListener = listener;
    }

    @Override
    public int getItemCount() {
        return diaryEntries.size();
    }

    @NonNull
    @Override
    public DiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diary_item, parent, false);
        return new DiaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryViewHolder holder, int position) {
        holder.bind(diaryEntries.get(position));
    }

    public interface OnItemActionListener {
        void onEditClick(DiaryEntry entry, int position);
        void onDeleteClick(DiaryEntry entry, int position);
    }

    public void setDiaryEntries(List<DiaryEntry> entries) {
        diaryEntries.clear();
        diaryEntries.addAll(entries);
        notifyDataSetChanged();
    }

    class DiaryViewHolder extends RecyclerView.ViewHolder {

        private final TextView logTitle;
        private final TextView logDescription;
        private final TextView logCreatedDate;
        private final TextView logUpdatedDate;

        public DiaryViewHolder(@NonNull View itemView) {
            super(itemView);
            logTitle = itemView.findViewById(R.id.text_view_log_title);
            logDescription = itemView.findViewById(R.id.text_view_log_description);
            logCreatedDate = itemView.findViewById(R.id.text_view_created_date);
            logUpdatedDate = itemView.findViewById(R.id.text_view_updated_date);

            itemView.setOnClickListener(view -> {
                if (actionListener != null) {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        actionListener.onEditClick(diaryEntries.get(position), position);
                    }
                }
            });

            itemView.setOnLongClickListener(view -> {
                PopupMenu popupMenu = new PopupMenu(itemView.getContext(), itemView);
                popupMenu.getMenuInflater().inflate(R.menu.entry_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    int position = getBindingAdapterPosition();
                    if (position == RecyclerView.NO_POSITION) return false;

                    return showPopMenu(menuItem, position);
                });

                popupMenu.show();
                return true;
            });
        }

        public void bind(DiaryEntry diaryEntry) {
            logTitle.setText(diaryEntry.getTitle());
            logDescription.setText(diaryEntry.getDescription());
            // Retrieve string template from resources and inject formatted date.
            // (R.string.updated_on is an int ID, so we must use getString() to resolve it)
            logCreatedDate.setText(itemView.getContext().getString(
                    R.string.written_on,
                    formatDate(diaryEntry.getCreatedAt())
            ));
            if (diaryEntry.getUpdatedAt() != 0) {
                logUpdatedDate.setText(itemView.getContext().getString(
                        R.string.updated_on,
                        formatDate(diaryEntry.getUpdatedAt())
                ));
            } else {
                logUpdatedDate.setText("");
            }
        }

        public boolean showPopMenu(MenuItem menuItem, int position) {
            if (menuItem.getItemId() == R.id.menu_edit) {
                actionListener.onEditClick(diaryEntries.get(position), position);
                return true;
            } else if (menuItem.getItemId() == R.id.menu_delete) {
                actionListener.onDeleteClick(diaryEntries.get(position), position);
                return true;
            }
            return false;
        }
    }

}
