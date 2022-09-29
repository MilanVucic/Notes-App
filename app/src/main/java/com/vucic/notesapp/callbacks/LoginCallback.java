package com.vucic.notesapp.callbacks;

public interface LoginCallback {
    void onSuccess();
    void onFailure(String message);
}
