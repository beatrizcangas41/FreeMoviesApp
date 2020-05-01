package com.example.freemoviesapp.ui.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.freemoviesapp.R;
import com.example.freemoviesapp.data.ui.ForgotPasswordActivity;
import com.example.freemoviesapp.data.ui.MainActivity;
import com.example.freemoviesapp.data.ui.RegisterActivity;

import java.sql.SQLException;
import static com.example.freemoviesapp.data.util.DialogCreator.*;
import static com.example.freemoviesapp.data.db.UserDBHandler.userExists;
import static com.example.freemoviesapp.data.db.UserDBHandler.verifyPassword;
import static com.example.freemoviesapp.data.db.UserDBHandler.verifyUsername;

public class LoginActivity extends AppCompatActivity {

    int counter = 3;
    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();

    String usernameString, passwordString;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usernameText = findViewById(R.id.username);
        final EditText passwordText = findViewById(R.id.password);

        final TextView displayMessage = findViewById(R.id.messege);

        final Button loginButton = findViewById(R.id.login);
        final Button registerButton = findViewById(R.id.register);
        final Button forgotPasswordButton = findViewById(R.id.forgot_password);

        final LoginViewModel loginViewModel = ViewModelProviders.of(
                this, new LoginViewModelFactory()).get(LoginViewModel.class);

        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        displayMessage.setVisibility(View.GONE);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) return;
                loginButton.setEnabled(loginFormState.isDataValid());

                if (loginFormState.getUsernameError() != null) usernameText.setError(getString(loginFormState.getUsernameError()));
                if (loginFormState.getPasswordError() != null) passwordText.setError(getString(loginFormState.getPasswordError()));
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) return;
                loadingProgressBar.setVisibility(View.GONE);

                if (loginResult.getError() != null) showLoginFailed(loginResult.getError());
                if (loginResult.getSuccess() != null) updateUIwithUser(loginResult.getSuccess());
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                loginButton.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View v) {

                        loadingProgressBar.setVisibility(View.VISIBLE);


                        try {
                            if (!hasErrors()) {
                                //                        if (usernameText.getText().toString().equals("admin1") &&
                                //                                passwordText.getText().toString().equals("admin1")) {

                                loginFormState.setValue(new LoginFormState(true));
                                Toast.makeText(getApplicationContext(),
                                        "Redirecting...", Toast.LENGTH_SHORT).show();

                                final Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(mainIntent);
                                finish();
                            }

                            else {
                                Toast.makeText(getApplicationContext(),
                                        "Wrong Credentials", Toast.LENGTH_SHORT).show();

                                displayMessage.setVisibility(View.VISIBLE);
                                // displayMessage.setBackgroundColor(Color.RED);
                                counter--;
                                displayMessage.setText("Intents Left: " + counter);

                                if (counter == 0) {
                                    loginButton.setEnabled(false);
                                }
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override public void afterTextChanged(Editable s) {
                try {
                    if (!verifyUsername(getUsername())) loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
                    else if (!verifyPassword(getUsername(), getPassword())) loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
                    else loginFormState.setValue(new LoginFormState(true));
                }

                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        };

        usernameText.addTextChangedListener(afterTextChangedListener);
        passwordText.addTextChangedListener(afterTextChangedListener);

        passwordText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    try {
                        if (!verifyUsername(getUsername()))
                            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
                        else if (!verifyPassword(getUsername(), getPassword()))
                            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
                        else loginFormState.setValue(new LoginFormState(true));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                final Intent mainIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(mainIntent);
                finish();
            }
        });


        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                final Intent mainIntent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(mainIntent);
                finish();
            }
        });
    }

    private void updateUIwithUser(MainActivity model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    public final String getUsername() {
        EditText text = findViewById(R.id.username);
        usernameString = text.getText().toString();
        System.out.println("(get) username: " + usernameString);
        return usernameString;
    }

    public final String getPassword() {
        EditText text = findViewById(R.id.username);
        passwordString = text.getText().toString();
        System.out.println("(get) password: " + passwordString);
        return passwordString;
    }

    private boolean hasErrors() throws SQLException {

        boolean hasErrors = false;
        String errorMessage = "Please address the following error(s) before test can be run: \n";

        if (!userExists(getUsername())) {
            loginResult.setValue(new LoginResult(R.string.login_failed));
            errorMessage += "\n    - Please verify your input(s), user does not exist. ";
            hasErrors = true;
        }

        else {
            if (!verifyUsername(getUsername())) {
                loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
                errorMessage += "\n    - Wrong Username ";
                hasErrors = true;
            }
            else if (!verifyPassword(getUsername(), getPassword())) {
                loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
                errorMessage += "\n    - Wrong Password ";
                hasErrors = true;
            }
        }

        if (hasErrors) ErrorDialog("Unable to Login.", errorMessage);
        return hasErrors;
    }


}