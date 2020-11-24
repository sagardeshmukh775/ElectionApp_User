package com.example.smtrick.electionappuser.Views.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.smtrick.electionappuser.AppSingleton.AppSingleton;
import com.example.smtrick.electionappuser.Callback.CallBack;
import com.example.smtrick.electionappuser.Models.Users;
import com.example.smtrick.electionappuser.R;
import com.example.smtrick.electionappuser.Repositories.Impl.UserRepositoryImpl;
import com.example.smtrick.electionappuser.Repositories.UserRepository;
import com.example.smtrick.electionappuser.Utils.ExceptionUtil;
import com.example.smtrick.electionappuser.Utils.Utility;
import com.example.smtrick.electionappuser.firebasestorage.StorageService;
import com.example.smtrick.electionappuser.preferences.AppSharedPreference;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class Activity_Update_Profile extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgProfile;
    private Button btnUpdate;
    private EditText edtName, edtPassword, edtAddress, edtMobileNumber;

    UserRepository userRepository;
    AppSharedPreference appSharedPreference;
    private StorageReference storageReference;

    Users users;
    private final int PICK_IMAGE_REQUEST = 22;
    String image, Sdownloadurl;
    private Uri filePath;
    private Uri profileUri;
    Bitmap bitmap;


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__update__profile);

//        assert getSupportActionBar() != null;   //null check
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userRepository = new UserRepositoryImpl();
        appSharedPreference = new AppSharedPreference(this);
        storageReference = FirebaseStorage.getInstance().getReference();

        edtName = findViewById(R.id.username);
        edtPassword = findViewById(R.id.password);
        edtAddress = findViewById(R.id.address);
        edtMobileNumber = findViewById(R.id.mobilenumber);
        btnUpdate = findViewById(R.id.update_button);
        imgProfile = findViewById(R.id.memberImage);

        redUser();

        btnUpdate.setOnClickListener(this);
        imgProfile.setOnClickListener(this);

    }

    private void redUser() {
        userRepository.readUserByUserId(appSharedPreference.getUserId(), new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    users = (Users) object;

                    edtName.setText(users.getName());
                    edtMobileNumber.setText(users.getMobileNumber());
                    edtPassword.setText(users.getPassword());
                    Glide.with(getApplicationContext()).load(users.getProfileImage()).placeholder(R.drawable.user1).into(imgProfile);
                }
            }

            @Override
            public void onError(Object object) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == btnUpdate) {

            uploadImage();
        }
        if (v == imgProfile) {
            SelectImage();
        }
    }

    private void SelectImage() {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE_REQUEST);
    }

    // Override onActivityResult method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the Uri of data
            filePath = data.getData();
            try {
                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgProfile.setImageBitmap(bitmap);
            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    // UploadImage method
    private void uploadImage() {
        if (filePath != null) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("profile/" + UUID.randomUUID().toString());

            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String downloadBarCodeurl = uri.toString();
                            updateProductDetails(users, downloadBarCodeurl);
                            progressDialog.dismiss();
                            Toast.makeText(Activity_Update_Profile.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                        }

                        private void updateProductDetails(Users user, String url) {
                            user.setName(edtName.getText().toString());
                            user.setMobileNumber(edtMobileNumber.getText().toString());
                            user.setProfileImage(url);
                            updateLeed(user.getUserId(), user.getLeedStatusMap());
                        }

                        private void updateLeed(String userId, Map<String, Object> toMap) {
                            userRepository.updateUser(userId, toMap, new CallBack() {
                                @Override
                                public void onSuccess(Object object) {
                                    Toast.makeText(Activity_Update_Profile.this, "User Details Updated", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Activity_Update_Profile.this, MainActivity.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void onError(Object object) {

                                }
                            });
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(Activity_Update_Profile.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                // Progress Listener for loading
                // percentage on the dialog box
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                }
            });
        }
    }

}