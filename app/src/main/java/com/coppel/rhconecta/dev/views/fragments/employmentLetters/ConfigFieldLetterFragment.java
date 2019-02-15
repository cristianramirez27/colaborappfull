package com.coppel.rhconecta.dev.views.fragments.employmentLetters;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.LetterConfigResponse;
import com.coppel.rhconecta.dev.business.models.LetterPreviewResponse;
import com.coppel.rhconecta.dev.business.models.PreviewDataVO;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.ConfigLetterActivity;
import com.coppel.rhconecta.dev.views.activities.LoginActivity;
import com.coppel.rhconecta.dev.views.adapters.FieldLetterRecyclerAdapter;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_LETTER;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.TYPE_KINDERGARTEN;

public class ConfigFieldLetterFragment extends Fragment implements View.OnClickListener, IServicesContract.View,
        DialogFragmentWarning.OnOptionClick {

    public static final String TAG = ConfigFieldLetterFragment.class.getSimpleName();
    private ConfigLetterActivity parent;
    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;
    private LetterConfigResponse letterConfigResponse;
    private List<LetterConfigResponse.Datos> fieldsLetter;
    private FieldLetterRecyclerAdapter fieldLetterRecyclerAdapter;

    private DialogFragmentWarning dialogFragmentWarning;
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private final int PERMISSIONS_REQUEST_CODE = 10;
    private boolean WARNING_PERMISSIONS;
    @BindView(R.id.rcvFields)
    RecyclerView rcvFields;

    @BindView(R.id.btnNext)
    Button btnNext;
    private boolean EXPIRED_SESSION;

    private int typeLetter;
    private boolean hasStamp;

    private com.coppel.rhconecta.dev.business.interfaces.ILettersNavigation ILettersNavigation;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ILettersNavigation = (com.coppel.rhconecta.dev.business.interfaces.ILettersNavigation) context;
    }

    public static ConfigFieldLetterFragment getInstance(int tipoCarta){
        ConfigFieldLetterFragment fragment = new ConfigFieldLetterFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_LETTER,tipoCarta);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_config_fields_letter, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        parent = (ConfigLetterActivity) getActivity();
        parent.setToolbarTitle(parent.getTitleLetter());
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
        rcvFields.setHasFixedSize(true);
        rcvFields.setLayoutManager(new LinearLayoutManager(getContext()));
        fieldsLetter = new ArrayList<>();
        fieldLetterRecyclerAdapter = new FieldLetterRecyclerAdapter(getContext(), fieldsLetter);
        rcvFields.setAdapter(fieldLetterRecyclerAdapter);
        btnNext.setOnClickListener(this);

        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        typeLetter = getArguments().getInt(BUNDLE_LETTER);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (letterConfigResponse == null) {
            String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
            String numEmployer = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
            coppelServicesPresenter.requestLettersConfig(numEmployer,typeLetter,token);
        }

        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if(dialogFragmentLoader != null && dialogFragmentLoader.isVisible()){
                        hideProgress();
                    }

                }
            }, 30000);

        }catch (Exception e){

        }

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
            case R.id.btnNext:
                if(fieldLetterRecyclerAdapter.hasFielsdSelected()){
                    if(typeLetter != TYPE_KINDERGARTEN){
                        showAlertStampLetter();
                    }else {
                        PreviewDataVO previewDataVO = new PreviewDataVO();
                        previewDataVO.setFieldsLetter(fieldsLetter);
                        ILettersNavigation.setKinderGardenData(letterConfigResponse);
                        ILettersNavigation.showFragmentAtPosition(1,previewDataVO);
                    }

                }else {
                    Toast.makeText(getActivity(),"Debes seleccionar al menos un campo",Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    public void showResponse(ServicesResponse response) {
        switch (response.getType()) {
            case ServicesRequestType.LETTERSCONFIG:
                letterConfigResponse = (LetterConfigResponse) response.getResponse();
                for(LetterConfigResponse.Datos datos : letterConfigResponse.getData().getResponse().getDatosCarta()){
                    datos.setSelected(datos.getIdu_defaultdatoscarta() == 1);
                    fieldsLetter.add(datos);
                }

                fieldLetterRecyclerAdapter.notifyDataSetChanged();
                hideProgress();
                break;

            case ServicesRequestType.LETTERPREVIEW:
                LetterPreviewResponse letterPreviewResponse = (LetterPreviewResponse) response.getResponse();
                PreviewDataVO previewDataVO = new PreviewDataVO();
                previewDataVO.setDataLetter(letterPreviewResponse.getData());
                previewDataVO.setFieldsLetter(fieldsLetter);
                previewDataVO.setHasStamp(hasStamp);
                ILettersNavigation.showFragmentAtPosition(1,previewDataVO);
                break;
        }
    }

    @Override
    public void showError(ServicesError coppelServicesError) {
        switch (coppelServicesError.getType()) {
            case ServicesRequestType.LETTERSCONFIG:


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialogFragmentWarning = new DialogFragmentWarning();
                        dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), coppelServicesError.getMessage(), getString(R.string.accept));
                        dialogFragmentWarning.setOnOptionClick(new DialogFragmentWarning.OnOptionClick() {
                            @Override
                            public void onLeftOptionClick() {

                            }

                            @Override
                            public void onRightOptionClick() {
                                dialogFragmentWarning.close();
                                getActivity().finish();
                            }
                        });
                        dialogFragmentWarning.show(getActivity().getSupportFragmentManager(), DialogFragmentWarning.TAG);
                        dialogFragmentLoader.close();
                    }
                }, 500);

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
        dialogFragmentLoader.close();
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


    private void showAlertStampLetter(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialogFragmentWarning = new DialogFragmentWarning();
                dialogFragmentWarning.setTwoOptionsData(getString(R.string.title_stamp_letter), getString(R.string.question_stamp_letter), getString(R.string.no),getString(R.string.yes));
                dialogFragmentWarning.setOnOptionClick(new DialogFragmentWarning.OnOptionClick() {
                    @Override
                    public void onLeftOptionClick() {
                        getPreviewLetter(false);
                        dialogFragmentWarning.close();
                    }

                    @Override
                    public void onRightOptionClick() {
                        getPreviewLetter(true);
                        dialogFragmentWarning.close();

                    }
                });
                dialogFragmentWarning.show(getActivity().getSupportFragmentManager(), DialogFragmentWarning.TAG);
                //dialogFragmentLoader.close();
            }
        }, 200);
    }

    private void getPreviewLetter(boolean stampLetter){
        this.hasStamp = stampLetter;
        List<LetterConfigResponse.Datos> fieldsLetter =fieldLetterRecyclerAdapter.getFieldsLetter();
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        String numEmployer = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        coppelServicesPresenter.requestLettersPreviewView(numEmployer,typeLetter,fieldsLetter,
                null,stampLetter,token);
    }

}