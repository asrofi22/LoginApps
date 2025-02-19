package com.nonadev.loginapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {
    EditText edtUserName, edtEmail, edtPassword;
    TextInputLayout txtInputLayoutUserName, txtInputLayoutEmail, txtInputPassword;
    CircularProgressButton btnRegister;
    SqliteHelper sqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sqliteHelper = new SqliteHelper(this);
        edtUserName = findViewById(R.id.editTextNameReg);
        edtEmail = findViewById(R.id.editTextEmailReg);
        edtPassword = findViewById(R.id.editTextPasswordReg);
        btnRegister = findViewById(R.id.cirRegisterButton);
        txtInputLayoutUserName = findViewById(R.id.textInputName);
        txtInputLayoutEmail = findViewById(R.id.textInputEmail);
        txtInputPassword = findViewById(R.id.textInputPassword);

        changeStatusBarColor();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if  (validate()){
                    String username = edtUserName.getText().toString();
                    String Email = edtEmail.getText().toString();
                    String Password = edtPassword.getText().toString();

                    if (!sqliteHelper.isEmailExists(Email)){
                        sqliteHelper.addUser(new User(null, username, Email, Password, null));
                        Snackbar.make(btnRegister, "Berhasil membuat Akun, Silahkan Login", Snackbar.LENGTH_SHORT).show();
                        Toast.makeText(RegisterActivity.this, "Test", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, Snackbar.LENGTH_SHORT);
                    } else {
                        Snackbar.make(btnRegister, "Email Sudah digunakan,silahkan login", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    public boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String UserName = edtUserName.getText().toString();
        String Email = edtEmail.getText().toString();
        String Password = edtPassword.getText().toString();

        //Handling validation for UserName field
        if (UserName.isEmpty()) {
            valid = false;
            txtInputLayoutUserName.setError("Please enter valid username!");
        } else {
            if (UserName.length() > 5) {
                valid = true;
                txtInputLayoutUserName.setError(null);
            } else {
                valid = false;
                txtInputLayoutUserName.setError("Username is to short!");
            }
        }

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            txtInputLayoutEmail.setError("Please enter valid email!");
        } else {
            valid = true;
            txtInputLayoutEmail.setError(null);
        }

        //Handling validation for Password field
        if (Password.isEmpty()) {
            valid = false;
            txtInputPassword.setError("Please enter valid password!");
        } else {
            if (Password.length() > 5) {
                valid = true;
                txtInputPassword.setError(null);
            } else {
                valid = false;
                txtInputPassword.setError("Password is to short!");
            }
        }

        return valid;
    }

    private void changeStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}