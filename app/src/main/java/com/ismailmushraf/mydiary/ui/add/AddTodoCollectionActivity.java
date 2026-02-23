package com.ismailmushraf.mydiary.ui.add;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ismailmushraf.mydiary.R;
import com.ismailmushraf.mydiary.model.Todo;

import java.util.ArrayList;

public class AddTodoCollectionActivity extends AppCompatActivity {

    private TodoAdapter adapter;

    private TextView titleTextView;
    private RecyclerView todosView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_todo_collection);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        titleTextView = findViewById(R.id.text_view_collection_title);
        todosView = findViewById(R.id.recycler_view_todos);
        todosView.setLayoutManager(new LinearLayoutManager(this));

        String title = getIntent().getStringExtra("title");
        ArrayList<Todo> todos =
                getIntent().getParcelableArrayListExtra("todos");

        titleTextView.setText(title);
        adapter = new TodoAdapter(todos);
        todosView.setAdapter(adapter);
    }
}