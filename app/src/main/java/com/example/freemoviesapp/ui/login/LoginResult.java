package com.example.freemoviesapp.ui.login;

import androidx.annotation.Nullable;

import com.example.freemoviesapp.data.ui.MainActivity;

/**
 * Authentication result : success (user details) or error message.
 */
class LoginResult {
    @Nullable private MainActivity success;
    @Nullable private Integer error;

    LoginResult(@Nullable Integer error) {
        this.error = error;
    }

    LoginResult(@Nullable MainActivity success) {
        this.success = success;
    }

    @Nullable MainActivity getSuccess() {
        return success;
    }

    @Nullable Integer getError() {
        return error;
    }
}
