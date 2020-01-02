package com.example.ezequiel.camera2.others;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.util.SparseArray;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;

import java.io.ByteArrayOutputStream;

public class MyFaceDetector extends Detector<Face> {
    private Detector<Face> mDelegate;

    private Frame mFrame;

    public MyFaceDetector(Detector<Face> detector) {
        this.mDelegate = detector;
    }

    @Override
    public SparseArray<Face> detect(Frame frame) {
        mFrame = frame;
        return mDelegate.detect(frame);
    }

    public Bitmap getFrameBitmap() {
        if (mFrame != null) {
            YuvImage yuvImage = new YuvImage(mFrame.getGrayscaleImageData().array(),
                    ImageFormat.NV21,
                    mFrame.getMetadata().getWidth(),
                    mFrame.getMetadata().getHeight(),
                    null);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            yuvImage.compressToJpeg(new Rect(0,
                            0,
                            mFrame.getMetadata().getWidth(),
                            mFrame.getMetadata().getHeight()),
                    100, byteArrayOutputStream);
            byte[] jpegArray = byteArrayOutputStream.toByteArray();
            return BitmapFactory.decodeByteArray(jpegArray, 0, jpegArray.length);
        }
        return null;
    }

}