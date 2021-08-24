package com.coppel.rhconecta.dev.views.dialogs;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.animation.AccelerateDecelerateInterpolator;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.BenefitCodeRequest;
import com.coppel.rhconecta.dev.business.models.BenefitsAdvertisingResponse;
import com.coppel.rhconecta.dev.business.models.BenefitsCompaniesResponse;
import com.coppel.rhconecta.dev.business.models.InfoCompanyResponse;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.views.customviews.TicketView;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class DialogFragmentCompany extends DialogFragment implements View.OnClickListener {

    public static final String TAG = DialogFragmentCompany.class.getSimpleName();
    public static final String KEY_COMPANY = "KEY_COMPANY";

    @BindView(R.id.scrollview)
    ScrollView scrollview;

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.imageFull)
    PhotoView imageFull;

    @BindView(R.id.txtDiscountQuantity)
    TextView txtDiscountQuantity;
    @BindView(R.id.txtDiscount)
    TextView txtDiscount;
    @BindView(R.id.percent_benefit)
    TextView txtPercent;
    @BindView(R.id.text_label)
    TextView txtLabelPercent;
    @BindView(R.id.txtDescription)
    TextView txtDescription;
    @BindView(R.id.txtNote)
    TextView txtNote;
    @BindView(R.id.txtAddress)
    TextView txtAddress;
    @BindView(R.id.txtPhone)
    TextView txtPhone;
    @BindView(R.id.textView4)
    TextView moreInfo;
    @BindView(R.id.btnAdvertising)
    Button btnAdvertising;
    @BindView(R.id.btnCode)
    Button btnCode;
    @BindView(R.id.ticketView)
    TicketView ticketView;
    @BindView(R.id.textViewCode)
    TextView textViewCode;
    @BindView(R.id.textView5)
    TextView titleCode;

    private OnBenefitsAdvertisingClickListener onBenefitsAdvertisingClickListener;

    private BenefitsCompaniesResponse.Company company;
    private BenefitsAdvertisingResponse.Advertising advertising;

    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;
    private ObjectAnimator ticketAnimation = null;
    private boolean mIsBackVisible = false;
    private boolean isExpandable = false;
    @BindView(R.id.card_front)
    View mCardFrontLayout;
    @BindView(R.id.card_back)
    View mCardBackLayout;

    private long mLastClickTime = 0;
    private CoppelServicesPresenter coppelServicesPresenter;
    private InfoCompanyResponse infoCompany;
    private boolean isCodeView = false;

    public static DialogFragmentCompany getInstance(BenefitsCompaniesResponse.Company company){
        DialogFragmentCompany dialogFragmentCompany = new DialogFragmentCompany();
        Bundle params = new Bundle();
        params.putSerializable(KEY_COMPANY,company);
        dialogFragmentCompany.setArguments(params);
        return dialogFragmentCompany;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setStyle(DialogFragment.STYLE_NO_TITLE, R.style.FullScreen);
        company = (BenefitsCompaniesResponse.Company)getArguments().getSerializable(KEY_COMPANY);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_benefit_company, container, false);
        ButterKnife.bind(this, view);
        setCancelable(true);
        initView();
    /*    if (getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawableResource(R.color.colorBackgroundDialogs);
        }*/
        getDialog().setCancelable(true);
        getDialog().setCanceledOnTouchOutside(true);
        return view;
    }

    private void initView() {
        if(company.getRuta() != null && !company.getRuta().isEmpty())
            Picasso.with(getApplicationContext()).load(company.getRuta())
                    .placeholder(R.drawable.placeholder_discount)
                    .error(R.drawable.placeholder_discount).into(image);

        String discount = company.getDescuento();
        String discountQuantity = "";
        String requiriments = company.getRequisitos();
        String note = "<html><body><p align=\"justify\">" +String.format("%s\n%s",company.getNota(),requiriments) + "</p></body></html>";
        String address = "<html><body><p align=\"justify\">" +company.getDomicilio() + "</p></body></html>";
        String phone = "<html><body><p align=\"justify\">" +company.getTelefono() + "</p></body></html>";

        if(discount.contains("%")){
            discountQuantity = discount.substring(0,discount.indexOf("%")+1);
            discount = discount.substring(discount.indexOf("%")+1,discount.length());
            txtDiscountQuantity.setText(discountQuantity);
            txtLabelPercent.setText(discount);
            txtDiscount.setText(discountQuantity);
            txtPercent.setText(discountQuantity);
        }else {
            txtDiscountQuantity.setText(discount);
            txtDiscount.setVisibility(View.GONE);
        }

        txtDescription.setText(company.getDes_descripcionapp());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtNote.setText(Html.fromHtml(note, Html.FROM_HTML_MODE_COMPACT));
            txtAddress.setText(Html.fromHtml(address, Html.FROM_HTML_MODE_COMPACT));
            txtPhone.setText(Html.fromHtml(phone, Html.FROM_HTML_MODE_COMPACT));
        } else {
            txtNote.setText(Html.fromHtml(note));
            txtAddress.setText(Html.fromHtml(address));
            txtPhone.setText(Html.fromHtml(phone));
        }


        btnAdvertising.setOnClickListener(this);
        btnCode.setOnClickListener(this);
        txtAddress.setMovementMethod(new ScrollingMovementMethod());

        scrollview.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                txtAddress.getParent().requestDisallowInterceptTouchEvent(false);

                return false;
            }
        });

        txtAddress.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                txtAddress.getParent().requestDisallowInterceptTouchEvent(true);

                return false;
            }
        });
        moreInfo.setOnClickListener(this);
        getInfoCompany();
    }

    public void close() {
        if (getDialog() != null && isVisible()) {
            dismiss();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), R.style.AlertDialogCopper);
    }

    @Override
    public void onClick(View view) {


        if (SystemClock.elapsedRealtime() - mLastClickTime < 1500){
            return;
        }

        mLastClickTime = SystemClock.elapsedRealtime();

        switch (view.getId()) {
            case R.id.btnAdvertising:
                //Se muestra la publicidad de la empresa
                btnAdvertising.setEnabled(false);
                onBenefitsAdvertisingClickListener.onCategoryClick(company);
                break;
            case R.id.textView4:
                isExpandable = !isExpandable;
                int visibility = isExpandable ? View.VISIBLE : View.GONE;
                scrollview.setVisibility(visibility);
                btnAdvertising.setVisibility(visibility);
                if (infoCompany != null && infoCompany.getOpc_desc_app() == 1) {
                    btnCode.setVisibility(visibility);
                    if (!isExpandable && isCodeView) {
                        isCodeView = false;
                        showViewCode();
                        showViewTicket();
                    }
                }
                moreInfo.setText(isExpandable ? R.string.label_less_information : R.string.label_more_information);
                break;
            case R.id.btnCode:
                getCodeBenefit();
                break;
        }
    }

    public void setOnBenefitsAdvertisingClickListener(OnBenefitsAdvertisingClickListener onBenefitsAdvertisingClickListener) {
        this.onBenefitsAdvertisingClickListener = onBenefitsAdvertisingClickListener;
    }

    public void setAdvertising(BenefitsAdvertisingResponse.Advertising advertising) {
        this.advertising = advertising;

        if(advertising.getRuta() != null && !advertising.getRuta().isEmpty()){
            imageFull.setZoomable(true);
        }else{
            imageFull.setZoomable(false);

            imageFull.setImageResource(R.drawable.img_publicidad);
        }


        Glide.with(this).load(advertising.getRuta()).placeholder(R.drawable.img_publicidad).into(imageFull);


        loadAnimations();
        changeCameraDistance();
        flipCard();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mCardFrontLayout.setVisibility(View.GONE);

            }
        }, 3200);



      /*  new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                close();
                getActivity().finish();
            }
        }, 5000);*/

    }

    public interface OnBenefitsAdvertisingClickListener {
        void onCategoryClick(BenefitsCompaniesResponse.Company company);
        void closeCategoryDialog();
    }

    private void changeCameraDistance() {
        int distance = 8000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mCardFrontLayout.setCameraDistance(scale);
        mCardBackLayout.setCameraDistance(scale);
    }

    private void loadAnimations() {
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.out_animation);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.in_animation);
    }

    private void loadAnimationsTickets() {
        if (ticketAnimation == null) {
            ticketAnimation = ObjectAnimator.ofFloat(ticketView, "rotationX", 0.0f, 180f);
            ticketAnimation.setDuration(1000);
            ticketAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        }
    }


    public void flipCard() {
        mCardBackLayout.setVisibility(View.VISIBLE);
        if (!mIsBackVisible) {
            mSetRightOut.setTarget(mCardFrontLayout);
            mSetLeftIn.setTarget(mCardBackLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = true;
            mCardBackLayout.setVisibility(View.VISIBLE);
        } else {
            mSetRightOut.setTarget(mCardBackLayout);
            mSetLeftIn.setTarget(mCardFrontLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = false;
        }
    }

    public void setCoppelServicesPresenter(CoppelServicesPresenter coppelServicesPresenter) {
        this.coppelServicesPresenter = coppelServicesPresenter;
    }

    public void getCodeBenefit() {
        String numEmpleado = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR);
        String token = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_TOKEN);
        BenefitCodeRequest benefitCodeRequest = new BenefitCodeRequest();
        benefitCodeRequest.setOpc(1);
        benefitCodeRequest.setNumEmpleado(Integer.parseInt(numEmpleado));
        benefitCodeRequest.setNumEmpresa(company.getServicios());

        this.coppelServicesPresenter.getBenefitCode(benefitCodeRequest, token);
    }

    public void getInfoCompany() {
        String numEmpleado = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR);
        String token = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_TOKEN);
        BenefitCodeRequest infoCompanyRequest = new BenefitCodeRequest();
        infoCompanyRequest.setOpc(2);
        infoCompanyRequest.setNumEmpleado(Integer.parseInt(numEmpleado));
        infoCompanyRequest.setNumEmpresa(company.getServicios());

        this.coppelServicesPresenter.getInfoCompany(infoCompanyRequest, token);
    }

    public void setCodeView(String newCode) {
        textViewCode.setText(newCode);
        flipCodeCard();
    }

    public void setInfoCompany(InfoCompanyResponse infoCompanyResponse) {
        this.infoCompany = infoCompanyResponse;
    }

    public void flipCodeCard() {
        loadAnimationsTickets();
        isCodeView = !isCodeView;
        showViewTicket();
        ticketAnimation.start();
        ticketView.setBackgroundBeforeDivider(requireContext().getDrawable(android.R.color.white));
        btnCode.setVisibility(View.GONE);
        new Handler().postDelayed(this::showViewCode, 1300);
    }

    private void showViewCode() {
        int visibility = isCodeView ? View.VISIBLE : View.GONE;
        textViewCode.setVisibility(visibility);
        titleCode.setVisibility(visibility);
    }

    private void showViewTicket() {
        int visibility = isCodeView ? View.GONE : View.VISIBLE;
        image.setVisibility(visibility);
        txtLabelPercent.setVisibility(visibility);
        txtPercent.setVisibility(visibility);
        ticketView.setBackgroundBeforeDivider(requireContext().getDrawable(R.color.colorPrimaryCoppelAzul));
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        //your code hear
        onBenefitsAdvertisingClickListener.closeCategoryDialog();
        dialog.cancel();
    }
}