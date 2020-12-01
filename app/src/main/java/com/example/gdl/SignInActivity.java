package com.example.gdl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "SignInActivity";

    EditText mUsername;
    EditText mEmail;
    EditText mPassword;
    Button mSaveButton;
    Button mSetPic;
    CropImageView mPicPreview;

    Map<String, Object> userInfo;

    private Uri pic_uri;
    private String downloadUrl;

    private FirebaseAuth mAuth;
    private StorageReference mUserProfileImageRef;
    public FirebaseFirestore db;

    private FirebaseUser user;
    final StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);

        mUsername = findViewById(R.id.sign_in_username);
        mEmail = findViewById(R.id.sign_in_email);
        mPassword = findViewById(R.id.sign_in_password);
        mSaveButton = findViewById(R.id.sign_in_save_button);
        mSetPic = findViewById(R.id.sign_in_set_profile_photo_button);
        mPicPreview = findViewById(R.id.cropImageView);

        //database stuff
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        //init map for user data
        userInfo = new HashMap<>();

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignIn();
            }
        });

        mSetPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onComplete: GETTING PROFILE PICTURE");
                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1,1).start(SignInActivity.this);
            }
        });
    }

    private void startSignIn() {

        String username = mUsername.getText().toString();
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        userInfo.put("name", username);
        userInfo.put("email", email);
        userInfo.put("debt", 0);
        userInfo.put("lent", 0);

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password) //once this is called, the code moves on sequentially while this runs in the background (its fkin weird)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "createUserWithEmail:success");

                                user = mAuth.getCurrentUser();

                                user.updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(username).build());
                                if (pic_uri != null) {
                                    try {
                                        Log.d(TAG, "onComplete: trying to store picture");
                                        mUserProfileImageRef = storageRef.child("Profile Images");
                                        StorageReference filePath = mUserProfileImageRef.child(user.getUid() + ".jpg");
                                        Log.d(TAG, "onComplete: filepath computed");
                                        filePath.putFile(pic_uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                                                                                if (task.isSuccessful()) {
                                                                                                    Log.d(TAG, "onComplete: profile pic is stored in storage");
                                                                                                    downloadUrl = task.getResult().getMetadata().getReference().getDownloadUrl().toString();
                                                                                                    userInfo.put("profile picture storage link", downloadUrl);
                                                                                                } else {
                                                                                                    Log.d(TAG, "onComplete: profile pic was not stored in storage");
                                                                                                }
                                                                                            }
                                                                                        });

                                        Log.d(TAG, "pic uri: trying to set photo in userprofile");
                                        user.updateProfile(new UserProfileChangeRequest.Builder().setPhotoUri(pic_uri).build());
                                    } catch (Exception e) {
                                        Log.d(TAG, "onComplete: "+e);
                                    }
                                } else {
                                    Log.d(TAG, "onComplete: pic_uri is null");
                                }
                                Log.d(TAG, "User added: name = "+user.getDisplayName()+", email = "+user.getEmail()+
                                                ", photoUrl = "+user.getPhotoUrl()+", uid = "+user.getUid());

                                //wait for user profile to update
                                Date date = new Date();
                                long startTime = date.getTime();
                                long endTime;
                                while (user.getDisplayName() == null || user.getEmail() == null) {
                                    endTime = date.getTime();
                                    if (endTime-startTime>10000) {
                                        Log.d(TAG, "error: updating profile took too long (>10s)");
                                        break;
                                    }
                                }

                                db.collection("Users").document(user.getUid()).set(userInfo);

                                Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
                                startActivity(intent);

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
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                pic_uri = result.getUri();
                userInfo.put("profile picture uri", pic_uri.toString());
                Toast.makeText(this, "photo uri obtained", Toast.LENGTH_SHORT).show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }

}