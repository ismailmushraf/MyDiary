package com.ismailmushraf.mydiary.ui.add;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ismailmushraf.mydiary.R;
import com.ismailmushraf.mydiary.model.Todo;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    private final List<Todo> todos;

    public TodoAdapter(List<Todo> todos) {
        this.todos = todos;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        holder.bind(todos.get(position));
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public interface OnItemActionsListener {
        public void onEditClick(Todo todo, int position);
        public void onDeleteClick(Todo todo, int position);
    }
    class TodoViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView description;
        private final CheckBox checkBox;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.textview_todo_title);
            this.description = itemView.findViewById(R.id.textview_todo_description);
            this.checkBox = itemView.findViewById(R.id.checkbox_todo);
        }

        public void bind(Todo todo) {
            title.setText(todo.getTitle());
            description.setText(todo.getDescription());
            checkBox.setChecked(todo.isCompleted());
        }
    }
}
