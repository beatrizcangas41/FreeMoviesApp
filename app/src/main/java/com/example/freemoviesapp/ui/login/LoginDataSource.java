package com.example.freemoviesapp.ui.login;

import com.example.freemoviesapp.data.ui.Result;
import com.example.freemoviesapp.data.model.LoggedInUser;
import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result.Error login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            LoggedInUser fakeUser = new LoggedInUser( java.util.UUID.randomUUID().toString(), "Jane Doe");
            return new Result.Error(fakeUser);
        }

        catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
