package com.ismailmushraf.mydiary.ui.todo;

import static com.ismailmushraf.mydiary.utils.DateUtils.getTime;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ismailmushraf.mydiary.R;
import com.ismailmushraf.mydiary.model.Todo;
import com.ismailmushraf.mydiary.model.TodoCollection;
import com.ismailmushraf.mydiary.ui.add.AddTodoCollectionActivity;
import com.ismailmushraf.mydiary.viewmodel.TodoViewModel;

import java.util.ArrayList;
import java.util.List;

public class TodoCollectionFragment extends Fragment {

    private ActivityResultLauncher<Intent> addEntryLauncher;
    List<TodoCollection> collections = new ArrayList<>();
    private TodoViewModel viewModel;
    private TodoCollectionAdapter adapter;

    public static TodoCollectionFragment newInstance() {
        return new TodoCollectionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_todo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupActivityResultLauncher();

        seedData();

        RecyclerView todosView = view.findViewById(R.id.recycler_view_todo_collections);
        FloatingActionButton fab = view.findViewById(R.id.fab_add_todo_collection);

        todosView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new TodoCollectionAdapter(collections, (collection, position) -> {
            openEditScreen(collection);
        });
        todosView.setAdapter(adapter);
    }

    private void setupActivityResultLauncher() {
        addEntryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                }
        );
    }

    private void openEditScreen(TodoCollection collection) {
        Intent intent = new Intent(requireActivity(), AddTodoCollectionActivity.class);
        intent.putExtra("title", collection.getTitle());
        intent.putParcelableArrayListExtra(
                "todos",
                new ArrayList<>(collection.getTodos())
        );
        addEntryLauncher.launch(intent);
    }

    private void seedData() {
        // ===== Collection 1 =====
        TodoCollection androidRoadmap = new TodoCollection(
                "Become Strong Android Developer",
                getTime(2026, 2, 18)
        );

        androidRoadmap.addTodo(new Todo(
                "Finish Room Integration",
                "Understand DAO, Repository and ViewModel flow deeply"
        ));

        androidRoadmap.addTodo(new Todo(
                "Implement Bottom Navigation",
                "Move diary into fragment and add Todo tab"
        ));

        androidRoadmap.addTodo(new Todo(
                "Learn DiffUtil",
                "Replace notifyDataSetChanged with proper list diffing"
        ));

        collections.add(androidRoadmap);


        // ===== Collection 2 =====
        TodoCollection weeklyPlanning = new TodoCollection(
                "Weekly Execution Plan",
                getTime(2026, 2, 19)
        );

        weeklyPlanning.addTodo(new Todo(
                "Solve 5 LeetCode problems",
                "Focus on arrays and sliding window"
        ));

        weeklyPlanning.addTodo(new Todo(
                "Study Android Lifecycle",
                "Understand Fragment vs Activity lifecycles"
        ));

        weeklyPlanning.addTodo(new Todo(
                "Workout 1 hour",
                "Morning session"
        ));

        collections.add(weeklyPlanning);
        collections.get(1).getTodos().get(0).setCompleted(true);
        collections.get(1).getTodos().get(1).setCompleted(true);
        collections.get(1).getTodos().get(2).setCompleted(true);


        // ===== Collection 3 =====
        TodoCollection personalGrowth = new TodoCollection(
                "Personal Development",
                getTime(2026, 2, 20)
        );

        Todo reading = new Todo(
                "Read 20 pages",
                "Book: The Art of Impossible"
        );
        reading.setCompleted(true);

        personalGrowth.addTodo(reading);

        personalGrowth.addTodo(new Todo(
                "Write 1 diary entry",
                "Reflect on progress and mindset"
        ));

        collections.add(personalGrowth);
    }
}