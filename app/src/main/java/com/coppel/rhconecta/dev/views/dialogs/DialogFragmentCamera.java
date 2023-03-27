package com.coppel.rhconecta.dev.views.dialogs;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.views.utils.CameraUtilities;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraUtils;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.Facing;
import com.otaliastudios.cameraview.Flash;
import com.otaliastudios.cameraview.Gesture;
import com.otaliastudios.cameraview.GestureAction;
import com.otaliastudios.cameraview.SessionType;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogFragmentCamera extends DialogFragment implements View.OnClickListener, CameraUtils.BitmapCallback {

    public static final String TAG = DialogFragmentCamera.class.getSimpleName();
    private OnCaptureListener onCaptureListener;
    @BindView(R.id.cvCamera)
    CameraView cvCamera;
    @BindView(R.id.imgvFlash)
    ImageView imgvFlash;
    @BindView(R.id.imgvPicture)
    ImageView imgvPicture;
    @BindView(R.id.imgvSwitch)
    ImageView imgvSwitch;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.FullScreen);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_camera, container, false);
        ButterKnife.bind(this, view);
        imgvFlash.setOnClickListener(this);
        imgvPicture.setOnClickListener(this);
        imgvSwitch.setOnClickListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        cvCamera.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cvCamera.destroy();
    }

    private void initCamera() {
        if (getContext() != null) {
            if (!getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
                imgvSwitch.setVisibility(View.GONE);
            }
            if (!getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                imgvFlash.setVisibility(View.GONE);
            }
        }
        cvCamera.start();
        cvCamera.setSessionType(SessionType.PICTURE);
        cvCamera.setFacing(Facing.BACK);
        cvCamera.setPlaySounds(true);
        cvCamera.setFlash(Flash.OFF);
        cvCamera.mapGesture(Gesture.PINCH, GestureAction.ZOOM);
        cvCamera.mapGesture(Gesture.TAP, GestureAction.FOCUS_WITH_MARKER);
        cvCamera.addCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(byte[] jpeg) {
                super.onPictureTaken(jpeg);
                CameraUtils.decodeBitmap(jpeg, DialogFragmentCamera.this);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgvFlash:
                toggleFlash();
                break;
            case R.id.imgvPicture:
                cvCamera.capturePicture();
                break;
            case R.id.imgvSwitch:
                switchCamera();
                break;
        }
    }

    public void close() {
        if (getDialog() != null && isVisible()) {
            dismiss();
        }
    }

    private void toggleFlash() {
        if (cvCamera.getFlash() == Flash.OFF) {
            cvCamera.setFlash(Flash.ON);
            imgvFlash.setImageResource(R.drawable.ic_flash_on_white);
        } else if (cvCamera.getFlash() == Flash.ON) {
            cvCamera.setFlash(Flash.AUTO);
            imgvFlash.setImageResource(R.drawable.ic_flash_auto_white);
        } else {
            cvCamera.setFlash(Flash.OFF);
            imgvFlash.setImageResource(R.drawable.ic_flash_off_white);
        }
    }

    private void switchCamera() {
        if (cvCamera.getFacing() == Facing.FRONT) {
            cvCamera.setFacing(Facing.BACK);
        } else {
            cvCamera.setFacing(Facing.FRONT);
        }
    }

    @Override
    public void onBitmapReady(Bitmap bitmap) {
        if (onCaptureListener != null) {
            //bitmap = CameraUtilities.saveTemporalImage(bitmap); DEVUELVE EL BITMAP EN NULL
            onCaptureListener.onPictureTaken(bitmap);
        }
    }

    public interface OnCaptureListener {
        void onPictureTaken(Bitmap bitmap);
    }

    public void setOnCaptureListener(OnCaptureListener onCaptureListener) {
        this.onCaptureListener = onCaptureListener;
    }
}
