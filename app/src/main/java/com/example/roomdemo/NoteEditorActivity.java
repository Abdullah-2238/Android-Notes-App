package com.example.roomdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NoteEditorActivity extends AppCompatActivity {

    ImageButton btnBack;
    ImageButton btnSave;
    TextView tvScreenTitle;
    EditText etTitle;
    EditText etContent;
    NoteDao noteDao;
    String title;
    String content;
    Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);
        tvScreenTitle = findViewById(R.id.tvScreenTitle);
        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);
        noteDao = AppDatabase.getInstance(this).noteDao();

        int noteId = getIntent().getIntExtra("NOTE_ID", -1);
        if (noteId == -1) {
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    title = etTitle.getText().toString().trim();
                    content = etContent.getText().toString().trim();

                    note = new Note(title,content);

                    noteDao.insert(note);

                    finish();
                }
            });
        } else {
            note = noteDao.getNoteById(noteId);
            title = note.getTitle();
            content = note.getContent();

            tvScreenTitle.setText("Edit Note");
            etTitle.setText(title);
            etContent.setText(content);

            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    title = etTitle.getText().toString().trim();
                    content = etContent.getText().toString().trim();

                    note.setTitle(title);
                    note.setContent(content);
                    noteDao.update(note);

                    finish();
                }
            });
        }

    }
}