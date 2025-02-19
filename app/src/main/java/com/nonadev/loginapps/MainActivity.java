package com.nonadev.loginapps;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    EditText edtUserName, edtUserPassword;
    String name, password;
    CircularProgressButton btnLogin;
    TextInputLayout textInputLayoutPassword;
    User currentUser;
    SharedPreferences sharedPreferences;
    private static final String SHARE_PREF_NAME = "share_pref";
    private static final String KEY_USERNAME = "user_name";

    SqliteHelper sqliteHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        sqliteHelper = new SqliteHelper(this);

        textInputLayoutPassword = findViewById(R.id.textInputPassword);
        btnLogin = findViewById(R.id.cirLoginButton);

        sharedPreferences = getSharedPreferences(SHARE_PREF_NAME, MODE_PRIVATE);
        String names = sharedPreferences.getString(KEY_USERNAME, null);
        if (names != null){
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        }
                btnLogin.setOnClickListener(view -> {
                    btnLogin.startAnimation();
                    edtUserName = findViewById(R.id.editTextUserName);
                    edtUserPassword = findViewById(R.id.editTextPassword);
                    if (validate()) {
                        name = edtUserName.getText().toString();
                        password = edtUserPassword.getText().toString();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(KEY_USERNAME,name);
                        editor.apply();

                        currentUser = sqliteHelper.Authenticate(new User(null, name, null, password, null));

                    }
                    AsynctaskButton();
                });
            }

            private void AsynctaskButton () {
                @SuppressLint("StaticFieldLeak")
                AsyncTask<String, Integer, String> task = new AsyncTask<String, Integer, String>() {
                    @Override
                    protected String doInBackground(String... strings) {
                        for (int i = 0; i <= 100; i++) {
                            publishProgress(i);
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        return "yes";
                    }

                    @Override
                    protected void onProgressUpdate(Integer... values) {
                        btnLogin.setProgress(values[0]);
                        super.onProgressUpdate(values);
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        if (s.equals("yes")) {
                            btnLogin.doneLoadingAnimation(Color.parseColor("#11CFC5"),
                                    BitmapFactory.decodeResource(getResources(),
                                            com.github.leandroborgesferreira.loadingbutton.R.drawable.ic_done_white_48dp));
                            if (currentUser != null) {
                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                startActivity(intent);

                            } else {
                                Snackbar.make(btnLogin, "Username atau Password Salah, ", Snackbar.LENGTH_LONG).show();
                            }
                        }
                        super.onPostExecute(s);
                        btnLogin.revertAnimation();
                    }
                };
                task.execute();
            }
    public boolean validate() {
        boolean valid = false;
        String Password = edtUserPassword.getText().toString();

        if (Password.isEmpty()) {
            textInputLayoutPassword.setError("Please enter valid password");
        } else {
            if (Password.length() > 5) {
                valid = true;
                textInputLayoutPassword.setError(null);
            } else {
                textInputLayoutPassword.setError("Password is to Short!");
            }
        }
        return valid;
    }

    public void onLoginClick(View View){
        startActivity(new Intent(this,RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }

}






