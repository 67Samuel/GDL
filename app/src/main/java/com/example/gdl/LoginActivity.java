package com.example.gdl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    Button submit_button;
    EditText user_email;
    EditText user_pswd;
    TextView user_create_acc;
    SharedPreferences loginPreferences;

    public static final String sharedPrefFile = "com.example.gdl.LoginPageSP";
    public static final String loginStatus = "loginStatus";

    private static DatabaseReference mRootDatabaseRef;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        //database
        mRootDatabaseRef = FirebaseDatabase.getInstance().getReference(); //get reference to root node
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null) { //if logged in, go to home page
                    Toast.makeText(LoginActivity.this, "user has logged in", Toast.LENGTH_SHORT).show();
                    Intent hp = new Intent(LoginActivity.this, HomePage.class);
                    hp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(hp);

                }

            }
        };

        submit_button = findViewById(R.id.login_submit);
        user_email = findViewById(R.id.login_email);
        user_pswd = findViewById(R.id.login_pswd);
        user_create_acc = findViewById(R.id.create_account_hyperlink);

        user_create_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "going to sign-in page", Toast.LENGTH_SHORT).show();                startActivity(new Intent(LoginActivity.this, SignInActivity.class));
            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: send it to database and check. If authenticated, go to Home Page. If not, stay here and put a Toast saying that pswd/user is wrong
                startLogIn();
            }
        });
    }

    private void startLogIn() {

        String email = user_email.getText().toString();
        String password = user_pswd.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Email or password is empty", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Email or Password is wrong", Toast.LENGTH_LONG).show();
                    }

                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
}
