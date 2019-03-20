package com.coppel.rhconecta.dev.views.fragments.fondoAhorro;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.FondoAhorroActivity;
import com.coppel.rhconecta.dev.views.customviews.EditTextMoney;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AbonoTipoFragment extends Fragment implements View.OnClickListener, IServicesContract.View,
        DialogFragmentWarning.OnOptionClick {

    public static final String TAG = AbonoTipoFragment.class.getSimpleName();
    private FondoAhorroActivity parent;
    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;
    private long mLastClickTime = 0;

    private DialogFragmentWarning dialogFragmentWarning;
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private boolean WARNING_PERMISSIONS;

    private boolean EXPIRED_SESSION;
    private List<Fragment> fragmentList;

    @BindView(R.id.edtAbonoActual)
    EditTextMoney edtAbonoActual;
    @BindView(R.id.edtAbonoCambiar)
    EditTextMoney edtAbonoCambiar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public static AbonoTipoFragment getInstance(){
        AbonoTipoFragment fragment = new AbonoTipoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deposit_option, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);


        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        edtAbonoActual.setTitleGravity(Gravity.LEFT);
        edtAbonoCambiar.setTitleGravity(Gravity.LEFT);
        edtAbonoActual.setPaddinRigthTitle();
        edtAbonoCambiar.setPaddinRigthTitle();

        edtAbonoActual.setInformativeMode("Quiero abonar","");
        edtAbonoCambiar.setInformativeMode("Cambiar abono","");

        edtAbonoActual.setEnableQuantity(true);
        edtAbonoCambiar.setEnableQuantity(true);
        edtAbonoCambiar.setHint("Ingresa una cantidad");

        //ViewPagerData viewPagerData =  new ViewPagerData<>(fragmentList, ElementsMainTab.values());

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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAdd:

                break;
        }
    }

    @Override
    public void showResponse(ServicesResponse response) {
        switch (response.getType()) {

            case ServicesRequestType.LETTERPREVIEW:

                break;
        }
    }

    @Override
    public void showError(ServicesError coppelServicesError) {
        switch (coppelServicesError.getType()) {
            case ServicesRequestType.LETTERSCONFIG:

                break;
            case ServicesRequestType.INVALID_TOKEN:
                EXPIRED_SESSION = true;
                showWarningDialog(getString(R.string.expired_session));
                break;
        }


        hideProgress();
    }

    @Override
    public void showProgress() {
        dialogFragmentLoader = new DialogFragmentLoader();
        dialogFragmentLoader.show(parent.getSupportFragmentManager(), DialogFragmentLoader.TAG);
    }

    @Override
    public void hideProgress() {
       if(dialogFragmentLoader != null) dialogFragmentLoader.close();
    }


    @Override
    public void onLeftOptionClick() {
        dialogFragmentWarning.close();
    }

    @Override
    public void onRightOptionClick() {
        if (EXPIRED_SESSION) {
            AppUtilities.closeApp(parent);
        } else if (WARNING_PERMISSIONS) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(parent, permissions[0]) &&
                    !ActivityCompat.shouldShowRequestPermissionRationale(parent, permissions[1])) {
                AppUtilities.openAppSettings(parent);
            }
        }
        WARNING_PERMISSIONS = false;
        dialogFragmentWarning.close();

    }

    private void showWarningDialog(String message) {
        dialogFragmentWarning = new DialogFragmentWarning();
        dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), message, getString(R.string.accept));
        dialogFragmentWarning.setOnOptionClick(this);
        dialogFragmentWarning.show(parent.getSupportFragmentManager(), DialogFragmentWarning.TAG);
    }



}