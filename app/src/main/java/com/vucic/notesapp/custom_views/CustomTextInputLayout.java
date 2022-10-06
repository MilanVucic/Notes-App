package com.vucic.notesapp.custom_views;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputLayout;

public class CustomTextInputLayout extends TextInputLayout {
    public CustomTextInputLayout(@NonNull Context context) {
        super(context);
    }

    public CustomTextInputLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextInputLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initErrorRemoval();
    }

    private void initErrorRemoval() {
        if (getEditText() != null) {
            getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    setError(null);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }
}
