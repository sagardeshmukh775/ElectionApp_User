package com.example.smtrick.electionappuser.firebasestorage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;


import androidx.annotation.NonNull;

import com.example.smtrick.electionappuser.Callback.CallBack;
import com.example.smtrick.electionappuser.Constants.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.File;
import java.io.InputStream;
import java.util.UUID;

public class StorageService {

    public static void uploadImageToFirebaseStorage(byte[] imageData, String storgaePath, final CallBack callback) {
        final String imageName = UUID.randomUUID().toString().replaceAll("-", "");
        UploadTask uploadTask = Constants.STORAGE_REFERENCE.child(storgaePath + "/" + imageName).putBytes(imageData);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                callback.onError(exception);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //Task<Uri> u = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                callback.onSuccess(imageName);
            }
        });
    }

    public static void uploadImageStreamToFirebaseStorage(InputStream imageData, String storgaePath, final CallBack callback) {
        final String imageName = UUID.randomUUID().toString().replaceAll("-", "");
        StorageReference mountainsRef = Constants.STORAGE_REFERENCE.child(storgaePath + "/" + imageName);
        UploadTask uploadTask = mountainsRef.putStream(imageData);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                callback.onError(exception);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //Task<Uri> u = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!urlTask.isSuccessful());
                Uri downloadUrl = urlTask.getResult();
                callback.onSuccess(downloadUrl.toString());
            }
        });
    }

    public static void downloadImageFromFirebaseStorage(String imageURL, final CallBack callback) {
        StorageReference storageRef = Constants.STORAGE.getReferenceFromUrl(imageURL);
        final long ONE_MEGABYTE = 1024 * 1024;
        storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmapImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                callback.onSuccess(bitmapImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    public static void downloadFileFromFirebaseStorage(StorageReference storageRef, File localFile, final CallBack callback) {
        storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                callback.onSuccess(taskSnapshot);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                callback.onError(exception);
            }
        });
    }

    public static String deleteImageFromFirebaseStorage(String imagePath) {
        StorageReference storageRef = Constants.STORAGE_REFERENCE.child(imagePath);
        storageRef.delete().addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                String deleteResponse = (String) o;
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Uh-oh, an error occurred!
                //ExceptionUtil.logException("Method: deleteImageFromFirebaseStorage", "Class : StorageService", exception);
            }
        });
        return "";
    }
}





