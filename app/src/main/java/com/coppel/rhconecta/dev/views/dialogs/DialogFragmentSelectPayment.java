package com.coppel.rhconecta.dev.views.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.CatalogueData;
import com.coppel.rhconecta.dev.business.models.ConsultaMetodosPagoResponse;
import com.coppel.rhconecta.dev.views.adapters.PaymentsRecyclerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogFragmentSelectPayment extends DialogFragment implements View.OnClickListener {

    public static final String TAG = DialogFragmentSelectPayment.class.getSimpleName();
    public static final String KEY_DATA = "KEY_DATA";
    public static final String KEY_LAYOUT = "KEY_LAYOUT";
    private OnButonOptionClick onButtonClickListener;
    private Context context;
    private String email;
    @BindView(R.id.rcvFields)
    RecyclerView rcvFields;
    @BindView(R.id.btnActionLeft)
    Button btnActionLeft;
    @BindView(R.id.btnActionRight)
    Button btnActionRight;
    private   CatalogueData catalogueData;
    private List<ConsultaMetodosPagoResponse.PaymentWay> paymentWays;
    private PaymentsRecyclerAdapter paymentsRecyclerAdapter;
    private String contentText;
    private int iResLayout;



    public static DialogFragmentSelectPayment newInstance(CatalogueData statesData, int iResLayout){
        DialogFragmentSelectPayment fragmentScheduleData = new DialogFragmentSelectPayment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_DATA,statesData);
        args.putInt(KEY_LAYOUT,iResLayout);
        fragmentScheduleData.setArguments(args);
        return fragmentScheduleData;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.FullScreen);
        iResLayout = getArguments().getInt(KEY_LAYOUT);
        catalogueData = (CatalogueData) getArguments().getSerializable(KEY_DATA);
        paymentWays =  (List<ConsultaMetodosPagoResponse.PaymentWay>)catalogueData.getData();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(iResLayout, container, false);
        ButterKnife.bind(this, view);
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawableResource(R.color.colorBackgroundDialogs);
        }

        initViews();
        return view;
    }

    private void initViews() {
        rcvFields.setHasFixedSize(true);
        rcvFields.setLayoutManager(new LinearLayoutManager(getContext()));
        paymentsRecyclerAdapter = new PaymentsRecyclerAdapter(getContext(), paymentWays);
        rcvFields.setAdapter(paymentsRecyclerAdapter);
        btnActionLeft.setOnClickListener(this);
        btnActionRight.setOnClickListener(this);
    }

    public void close() {
        if (getDialog() != null && isVisible()) {
            dismiss();
        }
    }

    @Override
    public void onClick(View view) {
        if (onButtonClickListener != null) {
            switch (view.getId()) {
                case R.id.btnActionLeft:
                    onButtonClickListener.onLeftOptionPaymentClick();
                    break;
                case R.id.btnActionRight:
                    onButtonClickListener.onRightOptionPaymentClick(paymentsRecyclerAdapter.getSelectedPayment(),paymentsRecyclerAdapter.getSelectedPositionPayment());
                    break;
                case R.id.imgvClose:
                    close();
                    break;
            }
        }
    }

    public interface OnButonOptionClick {

        void onLeftOptionPaymentClick();

        void onRightOptionPaymentClick(ConsultaMetodosPagoResponse.PaymentWay data, int position);
    }

    public void setOnButtonClickListener(OnButonOptionClick onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

}
