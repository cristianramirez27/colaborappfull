package com.coppel.rhconecta.dev.views.fragments.employmentLetters;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.LetterGenerateResponse;
import com.coppel.rhconecta.dev.business.models.LetterPreviewResponse;
import com.coppel.rhconecta.dev.business.models.PreviewDataVO;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.ServicesConstants;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.ConfigLetterActivity;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_LETTER;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_EMAIL;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;

public class PreviewLetterFragment extends Fragment implements View.OnClickListener, IServicesContract.View,
        DialogFragmentWarning.OnOptionClick,DialogFragmentGetDocument.OnButtonClickListener{

    public static final String TAG = PreviewLetterFragment.class.getSimpleName();
    private ConfigLetterActivity parent;
   // private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;

    private DialogFragmentWarning dialogFragmentWarning;
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private final int PERMISSIONS_REQUEST_CODE = 10;

    private DialogFragmentGetDocument dialogFragmentGetDocument;
    /*Content Letter*/
    @BindView(R.id.header1)
    TextView header1;
    @BindView(R.id.header2)
    TextView header2;
    @BindView(R.id.body)
    TextView body;
    @BindView(R.id.footer1)
    TextView footer1;
    @BindView(R.id.footer2)
    TextView footer2;

    @BindView(R.id.editLetter)
    TextView editLetter;

    @BindView(R.id.btnSend)
    Button btnSend;

    @BindView(R.id.btnDownload)
    Button btnDownload;

    private DialogFragmentLoader dialogFragmentLoader;

    private File pdf;
    private boolean EXPIRED_SESSION;
    private boolean WARNING_PERMISSIONS;
    private boolean SHARE_PDF;

    private boolean successGenerate = false;
    private int typeLetter;
    private PreviewDataVO previewDataVO;
    private com.coppel.rhconecta.dev.business.interfaces.ILettersNavigation ILettersNavigation;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ILettersNavigation = (com.coppel.rhconecta.dev.business.interfaces.ILettersNavigation) context;
    }

    public static PreviewLetterFragment getInstance(int tipoCarta){
        PreviewLetterFragment fragment = new PreviewLetterFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_LETTER,tipoCarta);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        typeLetter = getArguments().getInt(BUNDLE_LETTER);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preview_letter, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        parent = (ConfigLetterActivity) getActivity();
        //parent.setToolbarTitle(getString(R.string.bonus));
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
       // btnNext.setOnClickListener(this);
        editLetter.setOnClickListener(this);
        btnSend.setOnClickListener(this);
        btnDownload.setOnClickListener(this);

        return view;
    }

    private void setContentLetter(){

    if(previewDataVO != null){
        LetterPreviewResponse.Data dataLetter = previewDataVO.getDataLetter();
        String txtHeader1 = dataLetter.getResponse().getEncabezado1().replace("\n\n", System.getProperty("line.separator"));
        String txtHeader2 = dataLetter.getResponse().getEncabezado2().replace("\n\n", System.getProperty("line.separator"));
        String bodyJusfify = "<html><body><p align=\"justify\">" + dataLetter.getResponse().getCuerpo() + "</p></body></html>";
        String txtFooter1 = dataLetter.getResponse().getPie1().replace("\n\n", System.getProperty("line.separator"));
        String txtFooter2 = dataLetter.getResponse().getPie2().replace("\n\n", System.getProperty("line.separator"));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            header1.setText(txtHeader1);
            header2.setText(txtHeader2);
            body.setText(Html.fromHtml(bodyJusfify, Html.FROM_HTML_MODE_COMPACT));
            footer1.setText(txtFooter1);
            footer2.setText(txtFooter2);

        } else {
            header1.setText(txtHeader1);
            header2.setText(txtHeader2);
            body.setText(Html.fromHtml(bodyJusfify));
            footer1.setText(txtFooter1);
            footer2.setText(txtFooter2);
        }
        }
    }

    public void setDataLetter(PreviewDataVO previewDataVO){
        this.previewDataVO = previewDataVO;
        setContentLetter();
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
            case R.id.editLetter:
                ILettersNavigation.showFragmentAtPosition(0,null);
                break;
            case R.id.btnSend:
                onMailClick();
                break;
            case R.id.btnDownload:
                onDownloadClick();
                break;
        }
    }

    @Override
    public void showResponse(ServicesResponse response) {
        switch (response.getType()) {
            case ServicesRequestType.LETTERGENERATE_DOWNLOADMAIL:

                successGenerate = true;
                LetterGenerateResponse letterGenerateResponse= (LetterGenerateResponse) response.getResponse();
                pdf = AppUtilities.savePDFFile(getString(R.string.bonus).replace(" ", "_"),
                        letterGenerateResponse.getData().getResponse().getPdf());
                if (pdf != null) {
                    SHARE_PDF = true;
                    showGetVoucherReadyDialog(DialogFragmentGetDocument.LETTER_DOWNLOADED,"");
                } else {
                    showWarningDialog(getString(R.string.error_save_file));
                }
                break;

            case ServicesRequestType.LETTERGENERATE_SENDMAIL:
                successGenerate = true;
                LetterGenerateResponse letterGenerateResponseSend= (LetterGenerateResponse) response.getResponse();
                showGetVoucherReadyDialog(DialogFragmentGetDocument.LETTER_SENT,letterGenerateResponseSend.getData().getResponse().getMensaje());
                break;
        }
    }

    @Override
    public void showError(ServicesError coppelServicesError) {
        switch (coppelServicesError.getType()) {
            case ServicesRequestType.LETTERPREVIEW:

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

            case ServicesRequestType.LETTERGENERATE_DOWNLOADMAIL:
                showGetVoucherReadyDialog(DialogFragmentGetDocument.LETTER_DOWNLOAD_FAIL,"");
                break;


            case ServicesRequestType.LETTERGENERATE_SENDMAIL:
                showGetVoucherReadyDialog(DialogFragmentGetDocument.LETTER_SEND_FAIL,"");
                break;


        }
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

    private void showPrintLetter(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialogFragmentWarning = new DialogFragmentWarning();
                dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), getString(R.string.remember_print), getString(R.string.accept));
                dialogFragmentWarning.setOnOptionClick(new DialogFragmentWarning.OnOptionClick() {
                    @Override
                    public void onLeftOptionClick() {
                        dialogFragmentWarning.close();

                        if (SHARE_PDF) {
                            AppUtilities.openPDF(parent, pdf);
                            //AppUtilities.sharePDF(parent, pdf);
                        }
                        SHARE_PDF = false;


                        getActivity().finish();
                    }
                    @Override
                    public void onRightOptionClick() {
                        dialogFragmentWarning.close();

                        if (SHARE_PDF) {
                            AppUtilities.openPDF(parent, pdf);
                            //AppUtilities.sharePDF(parent, pdf);
                        }
                        SHARE_PDF = false;

                        getActivity().finish();
                    }
                });
                dialogFragmentWarning.show(getActivity().getSupportFragmentManager(), DialogFragmentWarning.TAG);
               // dialogFragmentLoader.close();
            }
        }, 500);
    }

    @Override
    public void onSend(String email) {
        if (email != null && !email.isEmpty()) {
            generateLetter(ServicesConstants.SHIPPING_OPTION_SEND_EMAIL,email);
            dialogFragmentGetDocument.close();
        } else {
            dialogFragmentGetDocument.setWarningMessage(getString(R.string.error_email));
        }
    }

    @Override
    public void onAccept() {

        dialogFragmentGetDocument.close();
        if(successGenerate) {
            successGenerate = false;
            showPrintLetter();
        }
    }


    private  void onMailClick(){
        showGetVoucherReadyDialog(DialogFragmentGetDocument.SEND_TO,"");
    }

    private  void onDownloadClick(){
        requestPermissions();
    }


    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(parent, permissions[0]) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(parent, permissions[1]) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(permissions, PERMISSIONS_REQUEST_CODE);
        } else {
            String userEmail = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_EMAIL);
            generateLetter(ServicesConstants.SHIPPING_OPTION_DOWNLOAD_PDF,userEmail);
        }
    }

    private void showGetVoucherReadyDialog(int type,String content) {
        dialogFragmentGetDocument = new DialogFragmentGetDocument();
        dialogFragmentGetDocument.setContentText(content);
        dialogFragmentGetDocument.setType(type, parent);
        if (type == DialogFragmentGetDocument.SEND_TO) {
            String userEmail = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_EMAIL);
            dialogFragmentGetDocument.setPreloadedText(userEmail);
        }
        dialogFragmentGetDocument.setOnButtonClickListener(this);
        dialogFragmentGetDocument.show(parent.getSupportFragmentManager(), DialogFragmentGetDocument.TAG);
    }


    private void generateLetter(int actionLetter,String email){

        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        String numEmployer = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        coppelServicesPresenter.requestLetterGenerateView(numEmployer,typeLetter,actionLetter,
                email,previewDataVO.getFieldsLetter(),null,previewDataVO.isHasStamp(),token);

    }



}