package com.coppel.rhconecta.dev.views.dialogs;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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
import com.coppel.rhconecta.dev.business.models.BenefitsAdvertisingResponse;
import com.coppel.rhconecta.dev.business.models.BenefitsCompaniesResponse;
import com.coppel.rhconecta.dev.business.models.BenefitsDiscountsResponse;
import com.coppel.rhconecta.dev.views.activities.SplashScreenActivity;
import com.coppel.rhconecta.dev.views.customviews.ZoomableImageView;
import com.github.chrisbanes.photoview.PhotoView;
import com.ortiz.touchview.TouchImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class DialogFragmentCompany extends DialogFragment implements View.OnClickListener {

    public static final String TAG = DialogFragmentCompany.class.getSimpleName();
    public static final String KEY_COMPANY = "KEY_COMPANY";
    @BindView(R.id.icClose)
    ImageView icClose;
    @BindView(R.id.icClosePublicity)
    ImageView icClosePublicity;
    @BindView(R.id.icCloseCode)
    ImageView icCloseCode;

    @BindView(R.id.scrollview)
    ScrollView scrollview;

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.imageFull)
    PhotoView imageFull;
    @BindView(R.id.viewCover)
    View viewCover;

    @BindView(R.id.txtDiscountQuantity)
    TextView txtDiscountQuantity;
    @BindView(R.id.txtDiscount)
    TextView txtDiscount;
    @BindView(R.id.txtDescription)
    TextView txtDescription;
    @BindView(R.id.txtNote)
    TextView txtNote;
    @BindView(R.id.txtAddress)
    TextView txtAddress;
    @BindView(R.id.txtPhone)
    TextView txtPhone;
    @BindView(R.id.code)
    TextView code;

    @BindView(R.id.btnAdvertising)
    Button btnAdvertising;
    @BindView(R.id.btnCode)
    Button btnCode;
    @BindView(R.id.btnBackCompany)
    Button btnBackCompany;


    private OnBenefitsAdvertisingClickListener onBenefitsAdvertisingClickListener;

    private BenefitsCompaniesResponse.Company company;
    private BenefitsAdvertisingResponse.Advertising advertising;

    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;
    private boolean mIsBackVisible = false;
    @BindView(R.id.card_front)
    View mCardFrontLayout;
    @BindView(R.id.card_back)
    View mCardBackLayout;
    @BindView(R.id.card_code_benefit)
    View mCardCodeBenefit;

    private long mLastClickTime = 0;

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
            txtDiscount.setText(discount);
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
        icClosePublicity.setOnClickListener(this);
        btnCode.setOnClickListener(this);
        btnBackCompany.setOnClickListener(this);
        btnBackCompany.setClickable(false);
        icCloseCode.setOnClickListener(this);

        icClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
                onBenefitsAdvertisingClickListener.closeCategoryDialog();
            }
        });

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
    }

    public void close() {
        if (getDialog() != null && isVisible()) {
            dismiss();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_iset_alert);
        return dialog;
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
            case R.id.btnCode:
                getCodeBenefit();
                break;
            case R.id.btnBackCompany:
                flipCodeCard();
                break;
            case R.id.icClosePublicity:
            case R.id.icCloseCode:
                close();
                getActivity().finish();
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
        mCardCodeBenefit.setCameraDistance(scale);
    }

    private void loadAnimations() {
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.out_animation);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.in_animation);
    }


    public void flipCard() {
        mCardCodeBenefit.setVisibility(View.GONE);
        mCardBackLayout.setVisibility(View.VISIBLE);
        if (!mIsBackVisible) {
            mSetRightOut.setTarget(mCardFrontLayout);
            mSetLeftIn.setTarget(mCardBackLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = true;
        } else {
            mSetRightOut.setTarget(mCardBackLayout);
            mSetLeftIn.setTarget(mCardFrontLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = false;
        }
    }

    public void getCodeBenefit(){
        code.setText("GAVA940915");
        flipCodeCard();
    }

    public void flipCodeCard(){
        loadAnimations();
        mCardBackLayout.setVisibility(View.GONE);
        mCardCodeBenefit.setVisibility(View.VISIBLE);
        if (!mIsBackVisible) {
            mSetRightOut.setTarget(mCardFrontLayout);
            mSetLeftIn.setTarget(mCardCodeBenefit);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = true;
            btnAdvertising.setClickable(false);
            btnCode.setClickable(false);
            btnBackCompany.setClickable(true);
        } else {
            mSetRightOut.setTarget(mCardCodeBenefit);
            mSetLeftIn.setTarget(mCardFrontLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = false;
            btnBackCompany.setClickable(false);
            btnAdvertising.setClickable(true);
            btnCode.setClickable(true);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        //your code hear
        onBenefitsAdvertisingClickListener.closeCategoryDialog();
        dialog.cancel();
    }
}