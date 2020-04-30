package com.example.freemoviesapp.ui.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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
import com.example.freemoviesapp.R;

public class LoginActivity extends AppCompatActivity {

    int counter = 3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final LoginViewModel loginViewModel = ViewModelProviders.of(
                this, new LoginViewModelFactory()).get(LoginViewModel.class);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        final Button loginButton = findViewById(R.id.login);
        final Button registerButton = findViewById(R.id.register);
        final Button forgotPasswordButton = findViewById(R.id.forgot_password);

        final EditText usernameText = findViewById(R.id.username);
        final EditText passwordText = findViewById(R.id.password);

        final TextView displayMessage = findViewById(R.id.messege);
        displayMessage.setVisibility(View.GONE);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) return;

                loginButton.setEnabled(loginFormState.isDataValid());

                loginButton.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View v) {
                        if (usernameText.getText().toString().equals("admin") &&
                                passwordText.getText().toString().equals("admin")) {
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
                    }
                });

                if (loginFormState.getUsernameError() != null) {
                    usernameText.setError(getString(loginFormState.getUsernameError()));
                }

                if (loginFormState.getPasswordError() != null) {
                    passwordText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                loginButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadingProgressBar.setVisibility(View.VISIBLE);
                        loginViewModel.login(usernameText.getText().toString(),
                                passwordText.getText().toString());
                        final Intent mainIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                        startActivity(mainIntent);
                    }
                });
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameText.getText().toString(),
                        passwordText.getText().toString());
            }
        };

        usernameText.addTextChangedListener(afterTextChangedListener);
        passwordText.addTextChangedListener(afterTextChangedListener);

        passwordText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameText.getText().toString(),
                            passwordText.getText().toString());
                }
                return false;
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent mainIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(mainIntent);
                finish();
            }
        });


        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent mainIntent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(mainIntent);
                finish();
            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}