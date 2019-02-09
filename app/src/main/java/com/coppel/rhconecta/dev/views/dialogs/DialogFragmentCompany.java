package com.coppel.rhconecta.dev.views.dialogs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.BenefitsCompaniesResponse;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class DialogFragmentCompany extends DialogFragment implements View.OnClickListener {

    public static final String TAG = DialogFragmentCompany.class.getSimpleName();
    public static final String KEY_COMPANY = "KEY_COMPANY";
    @BindView(R.id.icClose)
    ImageView icClose;
    @BindView(R.id.image)
    ImageView image;
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
    @BindView(R.id.btnAdvertising)
    Button btnAdvertising;

    private BenefitsCompaniesResponse.Company company;

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
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.FullScreen);
        company = (BenefitsCompaniesResponse.Company)getArguments().getSerializable(KEY_COMPANY);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_benefit_company, container, false);
        ButterKnife.bind(this, view);
        setCancelable(false);
        initView();
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawableResource(R.color.colorBackgroundDialogs);
        }
        return view;
    }

    private void initView() {
        if(company.getRuta() != null && !company.getRuta().isEmpty())
            Picasso.with(getApplicationContext()).load(company.getRuta())
                    .placeholder(R.drawable.placeholder_discount)
                    .error(R.drawable.placeholder_discount).fit().centerCrop().into(image);

        txtDiscount.setText(company.getDescuento());
        txtDescription.setText(company.getDes_descripcionapp());
        txtNote.setText(company.getNota());
        txtAddress.setText(company.getDomicilio());
        txtPhone.setText(company.getTelefono());
    }

    public void close() {
        if (getDialog() != null && isVisible()) {
            dismiss();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAdvertising:
                break;
        }
    }
}