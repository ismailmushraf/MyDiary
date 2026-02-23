package com.ismailmushraf.mydiary.ui.todo;

import static com.ismailmushraf.mydiary.utils.DateUtils.formatDate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ismailmushraf.mydiary.R;
import com.ismailmushraf.mydiary.model.Todo;
import com.ismailmushraf.mydiary.model.TodoCollection;

import java.util.List;

public class TodoCollectionAdapter extends RecyclerView.Adapter<TodoCollectionAdapter.TodoCollectionViewHolder> {

    private final List<TodoCollection> collections;
    private final OnItemActionListener itemActionListener;

    public TodoCollectionAdapter(List<TodoCollection> collections, OnItemActionListener itemActionListener) {
        this.collections = collections;
        this.itemActionListener = itemActionListener;
    }

    @Override
    public int getItemCount() {
        return collections.size();
    }

    @NonNull
    @Override
    public TodoCollectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_collection_item, parent, false);
        return new TodoCollectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoCollectionViewHolder holder, int position) {
        holder.bind(collections.get(position));
    }

    public interface OnItemActionListener {
        void onEditClick(TodoCollection collection, int position);
    }

    class TodoCollectionViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView progress;
        private final TextView createdDate;
        private final TextView updatedDate;

        public TodoCollectionViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_view_collection_title);
            progress = itemView.findViewById(R.id.text_view_progress);
            createdDate = itemView.findViewById(R.id.text_view_created_date);
            updatedDate = itemView.findViewById(R.id.text_view_updated_date);

            itemView.setOnClickListener(view -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    itemActionListener.onEditClick(collections.get(position), position);
                }
            });
        }

        public void bind(TodoCollection collection) {
            title.setText(collection.getTitle());
            int completed = 0;
            for (Todo todo: collection.getTodos()) {
                if (todo.isCompleted()) {
                    completed++;
                }
            }
            progress.setText(itemView.getContext().getString(
                    R.string.todo_collection_completion,
                    completed,
                    collection.getTodos().size()
            ));
            createdDate.setText(itemView.getContext().getString(
                    R.string.written_on,
                    formatDate(collection.getCreatedAt())
            ));
            if (collection.getUpdatedAt() != 0) {
                updatedDate.setText(itemView.getContext().getString(
                        R.string.updated_on,
                        formatDate(collection.getUpdatedAt())
                ));
            } else {
                updatedDate.setText("");
            }
        }
    }
}
