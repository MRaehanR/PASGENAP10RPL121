package com.example.pasgenap10rpl121;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    public ArrayList<NotesModel> notesList;
    public String docUid;
    Context context;

    public NotesAdapter(ArrayList<NotesModel> notesList, Context context) {
        this.notesList = notesList;
        this.context = context;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notes_list, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.tv_title.setText(notesList.get(position).getTitle());
        holder.tv_desc.setText(notesList.get(position).getNote());
        holder.cv_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("title", notesList.get(position).getTitle());
                intent.putExtra("note", notesList.get(position).getNote());
                context.startActivity(intent);
            }
        });
        holder.posisi = position;
    }

    @Override
    public int getItemCount() {
        return (notesList != null) ? notesList.size() : 0;
    }


    public class NotesViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        TextView tv_title, tv_desc;
        CardView cv_note;
        int posisi;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_title);
            tv_desc = itemView.findViewById(R.id.tv_desc);
            cv_note = itemView.findViewById(R.id.cv_note);

            cv_note.setOnCreateContextMenuListener(this);


        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(R.menu.menu1, R.id.option1, 1, "Edit").setOnMenuItemClickListener(this);
            menu.add(R.menu.menu1, R.id.option2, 2, "Delete").setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();


            switch (item.getItemId()){
                case R.id.option1:
                    Intent intent = new Intent(context, EditActivity.class);
                    intent.putExtra("title", notesList.get(posisi).getTitle());
                    intent.putExtra("note", notesList.get(posisi).getNote());
                    intent.putExtra("uid", notesList.get(posisi).getUid());
                    context.startActivity(intent);
                    break;
                case R.id.option2:
                    // kurang delete, harus bisa dapetin id setiap document
                    db.collection("notes").document(notesList.get(posisi).getUid())
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    notesList.remove(posisi);
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Note deleted successfuly", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error deleting document", e);
                                }
                            });
            }


            return false;
        }
    }
}
