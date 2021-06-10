package com.example.pasgenap10rpl121;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class NewActivity extends AppCompatActivity {

    EditText et_title, et_note;
    FloatingActionButton fab_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        getSupportActionBar().setTitle("Create Note");
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        et_title = findViewById(R.id.tf_title);
        et_note = findViewById(R.id.tf_note);
        fab_save = findViewById(R.id.fab_save);

        fab_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = et_title.getText().toString().trim();
                String note = et_note.getText().toString().trim();

                if(title.isEmpty() || note.isEmpty()){
                    Toast.makeText(getApplicationContext(), "All Fields arre required", Toast.LENGTH_SHORT).show();
                } else {
                    // Create a new user with a first and last name
                    Map<String, Object> notes = new HashMap<>();
                    notes.put("title", title);
                    notes.put("note", note);

                    // Add a new document with a generated ID
                    db.collection("notes")
                            .add(notes)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(getApplicationContext(), "Note created successfuly", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                    startActivity(new Intent(getApplicationContext(), NotesActivity.class));
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Create note failed", Toast.LENGTH_SHORT).show();
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });
                }
            }
        });
    }
}