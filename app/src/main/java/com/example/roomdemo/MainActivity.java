package com.example.roomdemo;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvNotes;
    FloatingActionButton fabAddNote;
    List<Note> notes;
    NoteDao noteDao;
    NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvNotes = findViewById(R.id.rvNotes);
        fabAddNote = findViewById(R.id.fabAddNote);
        noteDao = AppDatabase.getInstance(this).noteDao();
        notes = noteDao.getAllNotes();

        fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NoteEditorActivity.class);
                startActivity(intent);
            }
        });

        adapter = new NoteAdapter(this, notes, new OnNoteClickListener() {
            @Override
            public void onNoteClick(Note note) {
                Intent intent = new Intent(MainActivity.this, NoteEditorActivity.class);
                intent.putExtra("NOTE_ID", note.getId());
                startActivity(intent);
            }
        }, new OnNoteLongClickListener() {
            @Override
            public void onNoteLongClick(Note note) {
                showDeleteDialog(note);
            }
        });

        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        rvNotes.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        notes = noteDao.getAllNotes();
        adapter.notes = notes;
        adapter.notifyDataSetChanged();

    }

    private void showDeleteDialog(Note note) {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Delete Note")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    noteDao.delete(note);
                    notes = noteDao.getAllNotes();
                    adapter.notes = notes;
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}