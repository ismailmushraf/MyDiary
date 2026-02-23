package com.ismailmushraf.mydiary.ui.add;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.ismailmushraf.mydiary.R;

public class AddLogActivity extends AppCompatActivity {

    private EditText editTitle;
    private EditText editDescription;
    private boolean isEdit;
    private int id;
    private long createdAt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_log);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTitle = findViewById(R.id.edit_text_log_title);
        editDescription = findViewById(R.id.edit_text_log_description);

        editTitle.setText(getIntent().getStringExtra("title"));
        editDescription.setText(getIntent().getStringExtra("description"));

        isEdit = getIntent().getBooleanExtra("isEdit", false);
        if (isEdit) {
            id = getIntent().getIntExtra("id", -1);
            createdAt = getIntent().getLongExtra("createdAt", 0);
        }

        MaterialToolbar toolbar = findViewById(R.id.add_log_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editor_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        if (item.getItemId() == R.id.action_save) {
            saveEntry();
            return true;
        }

        if (item.getItemId() == R.id.action_share) {
            shareEntry();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveEntry() {
        String title = editTitle.getText().toString();
        String description = editDescription.getText().toString();

        if (title.trim().isEmpty()) {
            editTitle.setError("Title cannot be empty");
            return;
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("title", title);
        resultIntent.putExtra("description", description);
        resultIntent.putExtra("createdAt", createdAt);
        resultIntent.putExtra("isEdit", isEdit);
        resultIntent.putExtra("id", id);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void shareEntry() {
        String title = editTitle.getText().toString();
        String description = editDescription.getText().toString();

        if (title.trim().isEmpty()) {
            editTitle.setError("Title cannot be empty");
            return;
        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, description);
        startActivity(Intent.createChooser(intent, "Share via"));
    }
}