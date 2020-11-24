package com.example.smtrick.electionappuser.Services;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.FileUtils;

import com.example.smtrick.electionappuser.AppSingleton.AppSingleton;
import com.example.smtrick.electionappuser.Callback.CallBack;
import com.example.smtrick.electionappuser.Constants.Constants;
import com.example.smtrick.electionappuser.Models.ImagesModel;
import com.example.smtrick.electionappuser.Models.ServiceRequestModel;
import com.example.smtrick.electionappuser.Repositories.Impl.LeedRepositoryImpl;
import com.example.smtrick.electionappuser.Repositories.LeedRepository;
import com.example.smtrick.electionappuser.Services.impl.ImageCompressionServiceImp;
import com.example.smtrick.electionappuser.Utils.ExceptionUtil;
import com.example.smtrick.electionappuser.Utils.Utility;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ImageUploadIntentService extends IntentService {

    public ImageUploadIntentService() {
        super("ImageUploadIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            ServiceRequestModel serviceRequestModel = new ServiceRequestModel();
            serviceRequestModel.initReuestModel(intent);
            if (serviceRequestModel.getUri() != null) {
                if (serviceRequestModel.getImageCount() == 1) {
                    AppSingleton.getInstance(this).setNotificationManager();
                }
                String path = FileUtils.getPath(this, serviceRequestModel.getUri());
                serviceRequestModel.setBitmapPath(path);
                compressBitmap(serviceRequestModel);
            }
        } catch (Exception e) {
            ExceptionUtil.logException(e);
        }
    }

    private void compressBitmap(final ServiceRequestModel serviceRequestModel) {
        ImageCompressionService imageCompressionService = new ImageCompressionServiceImp();
        imageCompressionService.compressImage(serviceRequestModel.getBitmapPath(), new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    Bitmap bitmap = (Bitmap) object;
                    serviceRequestModel.setBitmap(bitmap);
                    uploadImage(serviceRequestModel);
                }
            }

            @Override
            public void onError(Object object) {
            }
        });
    }

    void uploadImage(final ServiceRequestModel serviceRequestModel) {
        try {
            InputStream imageInputStream = Utility.returnInputStreamFromBitmap(serviceRequestModel.getBitmap());
            StorageService.uploadImageStreamToFirebaseStorage(imageInputStream, serviceRequestModel.getStoragePath(), new CallBack() {
                public void onSuccess(Object object) {
                    //  final Uri downloadUrlLarge= (Uri) object;
                    String downloadUrlLarge = (String) object;
                    serviceRequestModel.setImagePath(downloadUrlLarge);
                    if (serviceRequestModel.getStoragePath().equalsIgnoreCase(Constants.DOCUMENTS_PATH))
                        updateLeedDocuments(serviceRequestModel);
                    else if (serviceRequestModel.getStoragePath().equalsIgnoreCase(Constants.CUSROMER_PROFILE_PATH))
                        updateLeed(serviceRequestModel);
                }

                public void onError(Object object) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateLeedDocuments(final ServiceRequestModel serviceRequestModel) {
        LeedRepository leedRepository = new LeedRepositoryImpl();
        Map<String, Object> map = new HashMap<>();
        String key = Constants.LEEDS_TABLE_REF.push().getKey();
        ImagesModel imagesModel = new ImagesModel();
        imagesModel.setLargImage(serviceRequestModel.getImagePath());
        map.put(key, imagesModel);
        leedRepository.updateLeedDocuments(serviceRequestModel.getLeedId(), map, new CallBack() {
            @Override
            public void onSuccess(Object object) {
               /* Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(GenerateLeedFragment.ImageUploadReceiver.PROCESS_RESPONSE);
                broadcastIntent.putExtra(IMAGE_COUNT, imageCount);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(broadcastIntent);
                */
                int progress = 0;
                if (serviceRequestModel.getTotalCount() > 0)
                    progress = (100 / serviceRequestModel.getTotalCount()) * serviceRequestModel.getImageCount();
                AppSingleton.getInstance(ImageUploadIntentService.this).updateProgress(serviceRequestModel.getImageCount(), serviceRequestModel.getTotalCount(), progress);
            }

            @Override
            public void onError(Object object) {
            }
        });
    }

    private void updateLeed(final ServiceRequestModel serviceRequestModel) {
        LeedRepository leedRepository = new LeedRepositoryImpl();
        Map<String, Object> map = new HashMap<>();
        map.put("customerImagelarge", serviceRequestModel.getImagePath());
        map.put("customerImageSmall", serviceRequestModel.getImagePath());
        leedRepository.updateLeed(serviceRequestModel.getLeedId(), map, new CallBack() {
            @Override
            public void onSuccess(Object object) {
               /* Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(GenerateLeedFragment.ImageUploadReceiver.PROCESS_RESPONSE);
                broadcastIntent.putExtra(IMAGE_COUNT, imageCount);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(broadcastIntent);
                */
                int progress = 0;
                if (serviceRequestModel.getTotalCount() > 0)
                    progress = (100 / serviceRequestModel.getTotalCount()) * serviceRequestModel.getImageCount();
                AppSingleton.getInstance(com.smartloan.smtrick.smart_loan.service.ImageUploadIntentService.this).updateProgress(serviceRequestModel.getImageCount(), serviceRequestModel.getTotalCount(), progress);
            }

            @Override
            public void onError(Object object) {
            }
        });
    }
}//end of service