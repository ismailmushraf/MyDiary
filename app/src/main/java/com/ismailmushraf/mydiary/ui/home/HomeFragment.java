package com.ismailmushraf.mydiary.ui.home;

import static android.app.Activity.RESULT_OK;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ismailmushraf.mydiary.R;
import com.ismailmushraf.mydiary.model.DiaryEntry;
import com.ismailmushraf.mydiary.ui.add.AddLogActivity;
import com.ismailmushraf.mydiary.ui.main.MainActivity;
import com.ismailmushraf.mydiary.viewmodel.DiaryViewModel;

import java.util.ArrayList;
import java.util.Calendar;

public class HomeFragment extends Fragment {

    private ActivityResultLauncher<Intent> addEntryLauncher;

    private DiaryViewModel viewModel;

    private DiaryAdapter adapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupActivityResultLauncher();

        viewModel = new ViewModelProvider(requireActivity())
                .get(DiaryViewModel.class);

        RecyclerView logsView = view.findViewById(R.id.recycler_view_logs);
        FloatingActionButton fab = view.findViewById(R.id.fab_add);

        logsView.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new DiaryAdapter(new ArrayList<>(), getItemActionListener());
        logsView.setAdapter(adapter);

        viewModel.getAllEntries().observe(getViewLifecycleOwner(), entries -> {
            adapter.setDiaryEntries(entries);
        });

        fab.setOnClickListener(v -> {
            addEntryLauncher.launch(
                    new Intent(requireContext(), AddLogActivity.class)
            );
        });
    }

    private void setupActivityResultLauncher() {
        addEntryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String title = result.getData().getStringExtra("title");
                        String description = result.getData().getStringExtra("description");

                        boolean isEdit = result.getData().getBooleanExtra("isEdit", false);
                        int id = result.getData().getIntExtra("id", -1);

                        if (isEdit) {
                            long createdAt = result.getData().getLongExtra("createdAt", 0);
                            DiaryEntry entry = new DiaryEntry(title, description, createdAt);
                            long updateAt = Calendar.getInstance().getTimeInMillis();
                            entry.setId(id);
                            entry.setUpdatedAt(updateAt);
                            viewModel.update(entry);
                        } else {
                            long createdAt = Calendar.getInstance().getTimeInMillis();
                            DiaryEntry entry = new DiaryEntry(title, description, createdAt);
                            viewModel.insert(entry);
                        }
                    }
                }
        );
    }

    private DiaryAdapter.OnItemActionListener getItemActionListener() {
        return new DiaryAdapter.OnItemActionListener() {

            @Override
            public void onEditClick(DiaryEntry entry, int position) {
                openEditScreen(entry);
            }

            @Override
            public void onDeleteClick(DiaryEntry entry, int position) {
                showDeleteDialog(entry);
            }
        };
    }

    private void showDeleteDialog(DiaryEntry entry) {

        new AlertDialog.Builder(requireContext())
                .setTitle("Delete Entry")
                .setMessage(getString(R.string.delete_message, entry.getTitle()))
                .setPositiveButton("Delete", (dialog, which) -> {

                    viewModel.delete(entry);

                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void openEditScreen(DiaryEntry entry) {

        Intent intent = new Intent(requireActivity(), AddLogActivity.class);
        intent.putExtra("isEdit", true);
        intent.putExtra("id", entry.getId());
        intent.putExtra("title", entry.getTitle());
        intent.putExtra("description", entry.getDescription());
        intent.putExtra("createdAt", entry.getCreatedAt());

        addEntryLauncher.launch(intent);
    }
}