package com.vucic.notesapp.models;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vucic.notesapp.AddNoteActivity;
import com.vucic.notesapp.R;
import com.vucic.notesapp.repository.Tags;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private Context context;
    private List<Note> notes;

    public NotesAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.note_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.titleTextView.setText(note.getTitle());
        holder.descriptionTextView.setText(note.getDescription());
        holder.containerLayout.setOnClickListener(view -> openNoteDetails(note.getId()));
        if (note.getArchived()) {
            holder.archivedImageView.setVisibility(View.VISIBLE);
        } else {
            holder.archivedImageView.setVisibility(View.GONE);
        }
    }

    private void openNoteDetails(Long id) {
        Intent intent = new Intent(context, AddNoteActivity.class);
        intent.putExtra(Tags.NOTE_ID, id);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, descriptionTextView;
        View containerLayout;
        View archivedImageView;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            containerLayout = itemView.findViewById(R.id.containerLayout);
            archivedImageView = itemView.findViewById(R.id.archivedImageView);
        }
    }
}
