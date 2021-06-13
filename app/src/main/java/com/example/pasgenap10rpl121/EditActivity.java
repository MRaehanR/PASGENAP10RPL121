package com.example.pasgenap10rpl121;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditActivity extends AppCompatActivity {

    EditText et_title, et_note;
    String title, note, uid;
    FloatingActionButton fab_save;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        getSupportActionBar().setTitle("Edit Note");

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        et_title = findViewById(R.id.tf_title);
        et_note = findViewById(R.id.tf_note);
        fab_save = findViewById(R.id.fab_save);

        bundle = getIntent().getExtras();
        if(bundle != null){
            title = bundle.getString("title");
            note = bundle.getString("note");
            uid = bundle.getString("uid");
        }

        et_title.setText(title);
        et_note.setText(note);

        fab_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = et_title.getText().toString().trim();
                note = et_note.getText().toString().trim();

                if (title.isEmpty() || note.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "All Fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    db.collection("notes")
                            .document(uid)
                            .update("title", title);
                    db.collection("notes")
                            .document(uid)
                            .update("note", note);
                    finish();
                    startActivity(new Intent(getApplicationContext(), NotesActivity.class));
                }
            }
        });
    }
}