package com.example.gdl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "SignInActivity";

    EditText mUsername;
    EditText mEmail;
    EditText mPassword;
    Button mSaveButton;
    Button mSetPic;
    ImageView mPicPreview;

    private Uri pic_uri;

    private final int REQUEST_IMAGE_GET = 123;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);

        mUsername = findViewById(R.id.sign_in_username);
        mEmail = findViewById(R.id.sign_in_email);
        mPassword = findViewById(R.id.sign_in_password);
        mSaveButton = findViewById(R.id.sign_in_save_button);
        mSetPic = findViewById(R.id.sign_in_set_profile_photo_button);
        mPicPreview = findViewById(R.id.sign_in_pic_preview);

        mAuth = FirebaseAuth.getInstance();

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignIn();
            }
        });

        mSetPic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_IMAGE_GET);
                }
            }
        });
    }

    private void startSignIn() {

        String username = mUsername.getText().toString();
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //TODO: give a loading toast and make thread sleep for a bit until name can update
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Log.d(TAG, "username: "+username);
                                user.updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(username).build());
                                if (pic_uri != null) {
                                    try {
                                        Log.d(TAG, "pic url: " + pic_uri);
                                        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(SignInActivity.this.getContentResolver(), pic_uri);
                                        FireBaseUtils.uploadImageToStorage(SignInActivity.this, storageRef, bitmap);
                                        user.updateProfile(new UserProfileChangeRequest.Builder().setPhotoUri(pic_uri).build());
                                    } catch (IOException e) {
                                        Toast.makeText(SignInActivity.this, "io error", Toast.LENGTH_LONG);
                                    }
                                }
                                Log.d(TAG, "User added: name = "+user.getDisplayName()+", email = "+user.getEmail()+
                                                ", photoUrl = "+user.getPhotoUrl()+", uid = "+user.getUid());
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignInActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK) {
            pic_uri = data.getData();
            Toast.makeText(this, "pic received", Toast.LENGTH_SHORT).show();
            mPicPreview.setImageURI(pic_uri);
        } else {
            Toast.makeText(SignInActivity.this, "file not found", Toast.LENGTH_LONG);
        }

    }

}