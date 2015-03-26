package com.jonaxel.cameraintent;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;


public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    private Camera camera;
    private SurfaceHolder mHolder;
    private static int ROTATION_90 = 90;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        this.camera = camera;

        // Install a SurfaceHolder.Callback so we get notified when the
                // underlying surface is created and destroyed.
                mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.setDisplayOrientation(ROTATION_90);
            camera.startPreview();
        } catch (IOException e) {
            Log.d("CAMERA", "Error setting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        //If your preview can change or rotate, take care of those events here.
        //Make sure to stop the preview before resizing or reformatting it;

        if (mHolder.getSurface() == null) {
            //Preview surface does not exist
            return;
        }

        try {
            camera.stopPreview();
        } catch (Exception e) {
            Log.d("CAMERA", "Tried to stop a non-existent preview");
        }

        //set preview size and make any resize, rotate or
        //reformatting changes here

        //start preview with new settings

        try {
            camera.setPreviewDisplay(mHolder);
            camera.startPreview();
        } catch (Exception e) {
            Log.d("CAMERA", "Error starting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        //Take care of releasing the Camera preview in your activity
    }
}
