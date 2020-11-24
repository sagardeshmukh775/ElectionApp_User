package com.example.smtrick.electionappuser.Services.impl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.os.AsyncTask;

import com.example.smtrick.electionappuser.Callback.CallBack;
import com.example.smtrick.electionappuser.Services.ImageCompressionService;
import com.example.smtrick.electionappuser.Utils.ExceptionUtil;


public class ImageCompressionServiceImp implements ImageCompressionService {

    @Override
    public void compressImage(String ImagePath, final CallBack callBack) {
        try {
            Bitmap returned_bitma = new ImageCompression(ImagePath).execute().get();
            if (returned_bitma != null)
                callBack.onSuccess(returned_bitma);
            else
                callBack.onError(null);
        } catch (Exception exception) {
            callBack.onError(exception);
            ExceptionUtil.errorMessage("compressImage", "ImageCompressionServiceImp", exception);
        }
    }

    private class ImageCompression extends AsyncTask<String, Void, Bitmap> {
        private final float maxHeight = 876.0f;
        private final float maxWidth = 876.0f;
        private String bitmapPath;

        private ImageCompression(String bitmapPath) {
            this.bitmapPath = bitmapPath;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return compressImage(bitmapPath);
        }

        protected void onPostExecute(Bitmap bitmap) {

        }

        private Bitmap compressImage(String imagePath) {
            Bitmap scaledBitmap = null;
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                Bitmap bmp = BitmapFactory.decodeFile(imagePath, options);
                int actualHeight = options.outHeight;
                int actualWidth = options.outWidth;
                float imgRatio = (float) actualWidth / (float) actualHeight;
                float maxRatio = maxWidth / maxHeight;
                if (actualHeight > maxHeight || actualWidth > maxWidth) {
                    if (imgRatio < maxRatio) {
                        imgRatio = maxHeight / actualHeight;
                        actualWidth = (int) (imgRatio * actualWidth);
                        actualHeight = (int) maxHeight;
                    } else if (imgRatio > maxRatio) {
                        imgRatio = maxWidth / actualWidth;
                        actualHeight = (int) (imgRatio * actualHeight);
                        actualWidth = (int) maxWidth;
                    } else {
                        actualHeight = (int) maxHeight;
                        actualWidth = (int) maxWidth;
                    }
                }
                options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
                options.inJustDecodeBounds = false;
                options.inDither = false;
                options.inPurgeable = true;
                options.inInputShareable = true;
                options.inTempStorage = new byte[16 * 1024];
                try {
                    bmp = BitmapFactory.decodeFile(imagePath, options);
                } catch (Exception exception) {
                    ExceptionUtil.errorMessage("compressImage", "ImageCompressionServiceImp", exception);
                }
                try {
                    scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.RGB_565);
                } catch (Exception exception) {
                    ExceptionUtil.errorMessage("compressImage", "ImageCompressionServiceImp", exception);
                }
                float ratioX = actualWidth / (float) options.outWidth;
                float ratioY = actualHeight / (float) options.outHeight;
                float middleX = actualWidth / 2.0f;
                float middleY = actualHeight / 2.0f;
                Matrix scaleMatrix = new Matrix();
                scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);
                Canvas canvas = new Canvas(scaledBitmap);
                canvas.setMatrix(scaleMatrix);
                canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
                if (bmp != null) {
                    bmp.recycle();
                }
                ExifInterface exif;
                try {
                    exif = new ExifInterface(imagePath);
                    int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
                    Matrix matrix = new Matrix();
                    if (orientation == 6) {
                        matrix.postRotate(90);
                    } else if (orientation == 3) {
                        matrix.postRotate(180);
                    } else if (orientation == 8) {
                        matrix.postRotate(270);
                    }
                    scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
                } catch (Exception e) {
                    ExceptionUtil.errorMessage("compressImage", "ImageCompressionServiceImp", e);
                }
            } catch (Exception e) {
                ExceptionUtil.logException(e);
            }
            return scaledBitmap;
        }

        private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;
            if (height > reqHeight || width > reqWidth) {
                final int heightRatio = Math.round((float) height / (float) reqHeight);
                final int widthRatio = Math.round((float) width / (float) reqWidth);
                inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
            }
            final float totalPixels = width * height;
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;
            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
            return inSampleSize;
        }

    }
}
