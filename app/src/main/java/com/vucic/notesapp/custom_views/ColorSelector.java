package com.vucic.notesapp.custom_views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.vucic.notesapp.R;
import com.vucic.notesapp.adapters.NoteColorAdapter;
import com.vucic.notesapp.callbacks.ColorSelectedCallback;
import com.vucic.notesapp.viewmodels.NoteColorViewModel;

import java.util.List;

public class ColorSelector extends FrameLayout {
    private RecyclerView recyclerView;
    private TextView labelTextView;

    private NoteColorAdapter noteColorAdapter;
    private ColorSelectedCallback callback;

    public ColorSelector(@NonNull Context context) {
        super(context);
        initViews(context);
    }

    public ColorSelector(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public ColorSelector(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    public ColorSelector(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews(context);
    }

    private void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.color_selector_layout, this, true);
    }

    public void setCallback(ColorSelectedCallback callback) {
        this.callback = callback;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        recyclerView = findViewById(R.id.colorsRecyclerView);
        labelTextView = findViewById(R.id.labelTextView);
    }

    public void initializeColors(List<NoteColorViewModel> viewModels) {
        noteColorAdapter = new NoteColorAdapter(getContext(), viewModels, callback);
        recyclerView.setAdapter(noteColorAdapter);
    }

    public NoteColorViewModel getSelectedColor() {
        return noteColorAdapter.getSelectedColor();
    }
}
