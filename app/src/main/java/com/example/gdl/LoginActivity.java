package com.example.gdl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    Button submit_button;
    EditText user_email;
    EditText user_pswd;
    TextView user_create_acc;
    SharedPreferences loginPreferences;

    public static final String sharedPrefFile = "com.example.gdl.LoginPageSP";
    public static final String loginStatus = "loginStatus";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        submit_button = findViewById(R.id.login_submit);
        user_email = findViewById(R.id.login_email);
        user_pswd = findViewById(R.id.login_pswd);
        user_create_acc = findViewById(R.id.create_account_hyperlink);

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: send it to database and check. If authenticated, go to Home Page. If not, stay here and put a Toast saying that pswd/user is wrong
                String userEmail = LoginActivity.this.user_email.getText().toString();
                String userPswd = LoginActivity.this.user_pswd.getText().toString();


                // Dummy login
                if (userEmail.equals("lzx") && userPswd.equals("lzx")){
                    Toast authenticated = Toast.makeText(LoginActivity.this, "User Authenticated. Logging in...", Toast.LENGTH_SHORT);

                    loginPreferences = getSharedPreferences(LoginActivity.sharedPrefFile, MODE_PRIVATE);
                    SharedPreferences.Editor editor = loginPreferences.edit();
                    editor.putBoolean(loginStatus, true);
                    editor.apply();
                    Intent hp = new Intent(LoginActivity.this, HomePage.class);
                    hp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(hp);
                }
            }
        });

//        user_create_acc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }
}
