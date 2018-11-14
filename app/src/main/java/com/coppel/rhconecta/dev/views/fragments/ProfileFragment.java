package com.coppel.rhconecta.dev.views.fragments;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.ProfileResponse;
import com.coppel.rhconecta.dev.resources.db.RealmHelper;
import com.coppel.rhconecta.dev.resources.db.models.UserPreference;
import com.coppel.rhconecta.dev.views.activities.HomeActivity;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentCamera;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.views.utils.CameraUtilities;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener, DialogFragmentWarning.OnOptionClick,
        DialogFragmentCamera.OnCaptureListener {

    public static final String TAG = ProfileFragment.class.getSimpleName();
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private final int CAMERA_PERMISSIONS_REQUEST_CODE = 10;
    private final int STORAGE_PERMISSIONS_REQUEST_CODE = 15;
    private final int PICK_IMAGE = 20;
    private HomeActivity parent;
    private DialogFragmentCamera dialogFragmentCamera;
    private DialogFragmentWarning dialogFragmentWarning;
    private boolean WARNING_PERMISSIONS;
    private ProfileResponse.Response profileResponse;
    private UserPreference userPreferences;
    private OnPictureChangedListener onPictureChanged;
    @BindView(R.id.imgvProfile)
    ImageView imgvProfile;
    @BindView(R.id.imgvEdit)
    ImageView imgvEdit;
    @BindView(R.id.txvName)
    TextView txvName;
    @BindView(R.id.txvCollaboratorNumberValue)
    TextView txvCollaboratorNumberValue;
    @BindView(R.id.txvCenterValue)
    TextView txvCenterValue;
    @BindView(R.id.txvPositionValue)
    TextView txvPositionValue;
    @BindView(R.id.txvEnterpriseValue)
    TextView txvEnterpriseValue;
    @BindView(R.id.txvErh)
    TextView txvErh;
    @BindView(R.id.txvErhValue)
    TextView txvErhValue;
    @BindView(R.id.txvManagerValue)
    TextView txvManagerValue;
    @BindView(R.id.txvAdmissionDateValue)
    TextView txvAdmissionDateValue;
    @BindView(R.id.txvSeniorityDateValue)
    TextView txvSeniorityDateValue;
    @BindView(R.id.txvRfcValue)
    TextView txvRfcValue;
    @BindView(R.id.txvCurpValue)
    TextView txvCurpValue;
    @BindView(R.id.txvNssValue)
    TextView txvNssValue;
    @BindView(R.id.txvEmailValue)
    TextView txvEmailValue;
    @BindView(R.id.linPicker)
    LinearLayout linPicker;
    @BindView(R.id.imgvCamera)
    ImageView imgvCamera;
    @BindView(R.id.imgvGallery)
    ImageView imgvGallery;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        parent = (HomeActivity) getActivity();
        parent.setToolbarTitle(getString(R.string.profile));
        profileResponse = parent.getProfileResponse();
        userPreferences = RealmHelper.getUserPreferences(profileResponse.getCorreo());
        imgvEdit.setOnClickListener(this);
        imgvCamera.setOnClickListener(this);
        imgvGallery.setOnClickListener(this);
        initProfile();
        return view;
    }

    private void initProfile() {
        if (userPreferences != null && userPreferences.getImage() != null && userPreferences.getImage().length > 0) {
            AppUtilities.loadLocalProfileImage(parent, BitmapFactory.decodeByteArray(userPreferences.getImage(), 0, userPreferences.getImage().length), imgvProfile);
        } else {
            AppUtilities.loadServiceProfileImage(parent, profileResponse.getFotoperfil(), imgvProfile);
        }
        txvName.setText(profileResponse.getNombreColaborador());
        txvCollaboratorNumberValue.setText(profileResponse.getColaborador());
        txvCenterValue.setText(profileResponse.getCentro() + " - " + profileResponse.getNombreCentro());
        txvPositionValue.setText(profileResponse.getPuesto() + " - " + profileResponse.getNombrePuesto());
        txvEnterpriseValue.setText(profileResponse.getNombreEmpresa());

        /**Se oculta la etiqueta de ERH en caso de que no se obtenga valor en dicho atributo*/
        if(profileResponse.getNombreErh() != null && !profileResponse.getNombreErh().isEmpty()){
            txvErh.setVisibility(View.VISIBLE);
            txvErhValue.setText(profileResponse.getNombreErh());
            txvErhValue.setVisibility(View.VISIBLE);
        }else {
            txvErh.setVisibility(View.GONE);
            txvErhValue.setText("");
            txvErhValue.setVisibility(View.GONE);
        }

        txvManagerValue.setText(profileResponse.getGte() + " - " + profileResponse.getNombreGte());
        txvAdmissionDateValue.setText(profileResponse.getFechaIngreso());
        txvSeniorityDateValue.setText(profileResponse.getAntiguedad());
        txvRfcValue.setText(profileResponse.getRfc());
        txvCurpValue.setText(profileResponse.getCurp());
        txvNssValue.setText(profileResponse.getNss());
        txvEmailValue.setText(profileResponse.getCorreo());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgvEdit:
                if (linPicker.getVisibility() == View.VISIBLE) {
                    linPicker.setVisibility(View.GONE);
                } else {
                    linPicker.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.imgvCamera:
                requestCameraPermissions();
                linPicker.setVisibility(View.GONE);
                break;
            case R.id.imgvGallery:
                requestStoragePermissions();
                linPicker.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                parent.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openGallery() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        Intent chooserIntent = Intent.createChooser(getIntent, getString(R.string.select_image));
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
        startActivityForResult(chooserIntent, PICK_IMAGE);
    }

    private void requestStoragePermissions() {
        if (ContextCompat.checkSelfPermission(parent, permissions[1]) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(parent, permissions[2]) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(Arrays.copyOfRange(permissions, 1, permissions.length), STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            openGallery();
        }
    }

    private void openCamera() {
        dialogFragmentCamera = new DialogFragmentCamera();
        dialogFragmentCamera.setOnCaptureListener(this);
        dialogFragmentCamera.show(parent.getSupportFragmentManager(), DialogFragmentCamera.TAG);
    }

    private void requestCameraPermissions() {
        if (ContextCompat.checkSelfPermission(parent, permissions[0]) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(parent, permissions[1]) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(parent, permissions[2]) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(permissions, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {
            openCamera();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_IMAGE:
                if (resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
                    try {
                        Bitmap selectedImage = CameraUtilities.saveTemporalImage(MediaStore.Images.Media.getBitmap(parent.getContentResolver(), data.getData()));
                        if (RealmHelper.updateProfileImage(profileResponse.getCorreo(), selectedImage)) {
                            AppUtilities.setProfileImage(parent, profileResponse.getCorreo(), profileResponse.getFotoperfil(), imgvProfile);
                        } else {
                            showErrorSavingImageDialog();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        showErrorSavingImageDialog();
                    }
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSIONS_REQUEST_CODE:
                if (AppUtilities.validatePermissions(permissions.length, grantResults)) {
                    openCamera();
                } else {
                    showErrorPermissionDialog();
                }
                break;
            case STORAGE_PERMISSIONS_REQUEST_CODE:
                if (AppUtilities.validatePermissions(permissions.length, grantResults)) {
                    openGallery();
                } else {
                    showErrorPermissionDialog();
                }
                break;
        }
    }

    @Override
    public void onLeftOptionClick() {
    }

    @Override
    public void onRightOptionClick() {
        if (WARNING_PERMISSIONS) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(parent, permissions[0]) &&
                    !ActivityCompat.shouldShowRequestPermissionRationale(parent, permissions[1]) &&
                    !ActivityCompat.shouldShowRequestPermissionRationale(parent, permissions[2])) {
                WARNING_PERMISSIONS = false;
                AppUtilities.openAppSettings(parent);
            }
        }
        dialogFragmentWarning.close();
    }

    @Override
    public void onPictureTaken(Bitmap bitmap) {
        if (bitmap != null) {
            if (RealmHelper.updateProfileImage(profileResponse.getCorreo(), bitmap)) {
                AppUtilities.setProfileImage(parent, profileResponse.getCorreo(), profileResponse.getFotoperfil(), imgvProfile);
                if (onPictureChanged != null) {
                    onPictureChanged.pictureChanged();
                }
            } else {
                showErrorSavingImageDialog();
            }
        } else {
            showErrorSavingImageDialog();
        }
        dialogFragmentCamera.close();
    }

    private void showErrorSavingImageDialog() {
        dialogFragmentWarning = new DialogFragmentWarning();
        dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), getString(R.string.error_save_image), getString(R.string.accept));
        dialogFragmentWarning.setOnOptionClick(this);
        dialogFragmentWarning.show(parent.getSupportFragmentManager(), DialogFragmentWarning.TAG);
    }

    private void showErrorPermissionDialog() {
        dialogFragmentWarning = new DialogFragmentWarning();
        dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), getString(R.string.permissions_needed), getString(R.string.accept));
        dialogFragmentWarning.setOnOptionClick(this);
        dialogFragmentWarning.show(parent.getSupportFragmentManager(), DialogFragmentWarning.TAG);
        WARNING_PERMISSIONS = true;
    }

    public interface OnPictureChangedListener {
        void pictureChanged();
    }

    public void setOnPictureChanged(OnPictureChangedListener onPictureChanged) {
        this.onPictureChanged = onPictureChanged;
    }
}
