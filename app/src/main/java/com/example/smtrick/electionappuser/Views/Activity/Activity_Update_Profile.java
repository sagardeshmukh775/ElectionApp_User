package com.example.smtrick.electionappuser.Views.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.smtrick.electionappuser.AppSingleton.AppSingleton;
import com.example.smtrick.electionappuser.Callback.CallBack;
import com.example.smtrick.electionappuser.Constants.Constants;
import com.example.smtrick.electionappuser.Models.PostVO;
import com.example.smtrick.electionappuser.Models.Users;
import com.example.smtrick.electionappuser.R;
import com.example.smtrick.electionappuser.Repositories.Impl.LeedRepositoryImpl;
import com.example.smtrick.electionappuser.Repositories.Impl.UserRepositoryImpl;
import com.example.smtrick.electionappuser.Repositories.LeedRepository;
import com.example.smtrick.electionappuser.Repositories.UserRepository;
import com.example.smtrick.electionappuser.Services.ImageCompressionService;
import com.example.smtrick.electionappuser.Services.impl.ImageCompressionServiceImp;
import com.example.smtrick.electionappuser.Utils.ExceptionUtil;
import com.example.smtrick.electionappuser.Utils.FileUtils;
import com.example.smtrick.electionappuser.Utils.Utility;
import com.example.smtrick.electionappuser.firebasestorage.StorageService;
import com.example.smtrick.electionappuser.preferences.AppSharedPreference;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import static com.itextpdf.text.error_messages.MessageLocalization.getMessage;

public class Activity_Update_Profile extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgProfile;
    private Button btnUpdate;
    private EditText edtName, edtPassword, edtAddress, edtMobileNumber;

    UserRepository userRepository;
    AppSharedPreference appSharedPreference;
    private StorageReference storageReference;

    Users users;
    private static final int REQUEST_PICK_IMAGE = 1002;
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

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        }
        if (v == imgProfile) {
            startCropImageActivity();
        }
    }

    //Start crop image activity for the given image.
    private void startCropImageActivity() {
        try {
            CropImage.activity(null)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setMultiTouchEnabled(true)
                    .start(this);
        } catch (Exception e) {
            ExceptionUtil.logException(e);
        }
    }

    public void onActivityResult(int requestCode, int resultCode,
                                 Intent imageData) {
        super.onActivityResult(requestCode, resultCode, imageData);
        try {
            switch (requestCode) {
                case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                    CropImage.ActivityResult result = CropImage.getActivityResult(imageData);
                    if (resultCode == RESULT_OK) {
                        if (imageData != null) {
                            Bundle extras = imageData.getExtras();
                            if (extras != null) {
                                Bitmap bitmapImg = MediaStore.Images.Media.getBitmap(getContentResolver(), result.getUri());
                                profileUri = result.getUri();
//                                activityUpdateProfileBinding.ivCancelProfile.setVisibility(View.VISIBLE);
                                if (bitmapImg != null)
                                    imgProfile.setImageBitmap(bitmapImg);
                                compressBitmap(profileUri);
                            }
                        }
                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Utility.showMessage(this, "Cropping failed: " + result.getError());
                    }
                    break;
            }
        } catch (Exception e) {
            ExceptionUtil.logException(e);
        }
    }

    private void compressBitmap(Uri uri) {
        String path = FileUtils.getPath(this, uri);
        ImageCompressionService imageCompressionService = new ImageCompressionServiceImp();
        imageCompressionService.compressImage(path, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    bitmap = (Bitmap) object;
                }
            }

            @Override
            public void onError(Object object) {
            }
        });
    }

    void uploadImage(Bitmap bitmap, String storagePath) {
        try {
            AppSingleton.getInstance(this).setNotificationManager();
            InputStream imageInputStream = Utility.returnInputStreamFromBitmap(bitmap);
            StorageService.uploadImageStreamToFirebaseStorage(imageInputStream, storagePath, new CallBack() {
                public void onSuccess(Object object) {
                    if (object != null) {
                        String downloadUrlLarge = (String) object;
                        try {
                            appSharedPreference.setUserProfileImages(downloadUrlLarge);
                            Intent broadcastIntent = new Intent();
                            broadcastIntent.setAction(MainActivity.ImageUploadReceiver.PROCESS_RESPONSE);
                            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(broadcastIntent);
                        } catch (Exception e) {
                            ExceptionUtil.logException(e);
                        }
                        updateUserData(downloadUrlLarge);
                    }
                }

                public void onError(Object object) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateUserData(final String downloadUrlLarge) {
        HashMap<String, String> map = new HashMap<>();
        map.put("profileImage", downloadUrlLarge);
        map.put("profileImage", downloadUrlLarge);
        userRepository.updateUser(appSharedPreference.getUserId(), map, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                AppSingleton.getInstance(Activity_Update_Profile.this).updateProgress(1, 1, 100);
            }

            @Override
            public void onError(Object object) {
                Utility.showMessage(getApplicationContext(), getMessage(String.valueOf(R.string.data_updation_fails_message)));
            }
        });
    }

}