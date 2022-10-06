package com.vucic.notesapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vucic.notesapp.R;
import com.vucic.notesapp.callbacks.ColorSelectedCallback;
import com.vucic.notesapp.viewmodels.NoteColorViewModel;

import java.util.List;

public class NoteColorAdapter extends RecyclerView.Adapter<NoteColorAdapter.NoteColorViewHolder> {
    private Context context;
    private List<NoteColorViewModel> noteColors;
    private NoteColorViewModel selectedColor;
    private ColorSelectedCallback callback;

    public NoteColorAdapter(Context context, List<NoteColorViewModel> noteColors, ColorSelectedCallback callback) {
        this.context = context;
        this.noteColors = noteColors;
        for (NoteColorViewModel noteColorVM : noteColors) {
            if (noteColorVM.isSelected()) {
                selectedColor = noteColorVM;
                break;
            }
        }
        this.callback = callback;
    }

    public NoteColorViewModel getSelectedColor() {
        return selectedColor;
    }

    @NonNull
    @Override
    public NoteColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteColorViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.note_color_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteColorViewHolder holder, int position) {
        bindNoteColor(holder, noteColors.get(position));
    }

    private void bindNoteColor(NoteColorViewHolder holder, NoteColorViewModel noteColorViewModel) {
        String colorCodeString = noteColorViewModel.getNoteColor().getColorCode();
        holder.colorView.setBackgroundColor(Color.parseColor(colorCodeString));
        if (noteColorViewModel.isSelected()) {
            holder.containerLayout.setBackground(context.getResources().getDrawable(R.drawable.selected_item_background));
        } else {
            holder.containerLayout.setBackground(null);
        }
        holder.containerLayout.setOnClickListener(v -> selectNoteColor(noteColorViewModel));
    }

    private void selectNoteColor(NoteColorViewModel noteColorViewModel) {
        if (callback != null) {
            callback.onColorSelected(noteColorViewModel.getNoteColor());
        }
        if (selectedColor != null) {
            selectedColor.setSelected(false);
        }
        noteColorViewModel.setSelected(!noteColorViewModel.isSelected());
        selectedColor = noteColorViewModel;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return noteColors.size();
    }

    static class NoteColorViewHolder extends RecyclerView.ViewHolder {
        View colorView;
        FrameLayout containerLayout;

        public NoteColorViewHolder(@NonNull View itemView) {
            super(itemView);
            colorView = itemView.findViewById(R.id.colorView);
            containerLayout = itemView.findViewById(R.id.containerLayout);
        }
    }
}
