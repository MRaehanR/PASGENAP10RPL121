package com.example.pasgenap10rpl121;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class NotesActivity extends AppCompatActivity {

    FloatingActionButton fab_add;
    private FirebaseAuth mAuth;

    RecyclerView recyclerView;
    ArrayList<NotesModel> notesList;
    NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        getSupportActionBar().setTitle("My Notes");

        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        fab_add = findViewById(R.id.fab_add);

        recyclerView = findViewById(R.id.rv_notes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        notesList = new ArrayList<>();
        notesAdapter = new NotesAdapter(notesList, NotesActivity.this);
        recyclerView.setAdapter(notesAdapter);
        registerForContextMenu(recyclerView);

        db.collection("notes")
                .orderBy("title", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("Error adding document", error.getMessage());
                        }

                        for(DocumentChange dc : value.getDocumentChanges()){
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                NotesModel notesModel = dc.getDocument().toObject(NotesModel.class);
                                notesModel.setUid(dc.getDocument().getId());
                                notesList.add(notesModel);
//                                System.out.println(dc.getDocument().getId());
                                System.out.println(notesModel.getUid());
                            }
                            notesAdapter.notifyDataSetChanged();
                        }
                    }
                });

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewActivity.class);
                startActivity(intent);
            }
        });
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.mn_logout:

                mAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}