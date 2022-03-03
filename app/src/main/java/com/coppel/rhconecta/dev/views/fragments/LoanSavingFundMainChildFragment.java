package com.coppel.rhconecta.dev.views.fragments;

import android.os.Bundle;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.ISelectedOption;
import com.coppel.rhconecta.dev.business.models.LoanSavingFundResponse;
import com.coppel.rhconecta.dev.resources.db.models.HomeMenuItem;
import com.coppel.rhconecta.dev.views.adapters.IconsMenuRecyclerAdapter;
import com.coppel.rhconecta.dev.views.utils.MenuUtilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_SAVINFOUND;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_MY_MOVEMENTS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_ADITIONAL_SAVED;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_PAY;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_REMOVE;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoanSavingFundMainChildFragment extends Fragment implements View.OnClickListener, IconsMenuRecyclerAdapter.OnItemClick {
    public static final int REQUEST_SAVING = 258;
    public static final String TAG = LoanSavingFundMainChildFragment.class.getSimpleName();
    @BindView(R.id.btnRemove)
    Button btnRemove;
    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.btnAdditionalSaving)
    Button btnAdditionalSaving;
    @BindView(R.id.rcvOptions)
    RecyclerView rcvOptions;
    private List<HomeMenuItem> menuItems;
    private IconsMenuRecyclerAdapter iconsMenuRecyclerAdapter;

    private long mLastClickTime = 0;
    private ISelectedOption ISelectedOption;

    private LoanSavingFundResponse loanSavingFundResponse;


    public static LoanSavingFundMainChildFragment getInstance(LoanSavingFundResponse loanSavingFundResponse){
        LoanSavingFundMainChildFragment fragment = new LoanSavingFundMainChildFragment();
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_SAVINFOUND,loanSavingFundResponse);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loanSavingFundResponse = (LoanSavingFundResponse) getArguments().getSerializable(BUNDLE_SAVINFOUND);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.loan_fragment_saving_fund_main_child, container, false);
        ButterKnife.bind(this, view);
        btnRemove.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnAdditionalSaving.setOnClickListener(this);

        /**Se inicia menu con iconos**/
        rcvOptions.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rcvOptions.setLayoutManager(gridLayoutManager);
        menuItems = new ArrayList<>();
        menuItems.addAll(MenuUtilities.getSavingFoundMenu(getActivity()));
        iconsMenuRecyclerAdapter = new IconsMenuRecyclerAdapter(getActivity(), menuItems, gridLayoutManager.getSpanCount());
        iconsMenuRecyclerAdapter.setOnItemClick(this);
        rcvOptions.setAdapter(iconsMenuRecyclerAdapter);


        return view;
    }


    @Override
    public void onItemClick(String tag) {

        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        int optionSelected = 0;
        switch (tag) {

            case OPTION_REMOVE:
                optionSelected = 1;
                break;
            case OPTION_PAY:
                optionSelected = 2;
                break;

            case OPTION_ADITIONAL_SAVED:
                optionSelected = 3;
                break;

            case OPTION_MY_MOVEMENTS:
                optionSelected = 4;
                break;
        }

        ISelectedOption.openOption(optionSelected);
    }

    @Override
    public void onClick(View view) {

        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }

        mLastClickTime = SystemClock.elapsedRealtime();

        int optionSelected = 0;
        switch (view.getId()) {
            case R.id.btnRemove:
                optionSelected = 1;
                break;
            case R.id.btnAdd:
                optionSelected = 2;
                break;
            case R.id.btnAdditionalSaving:
                optionSelected = 3;
                break;
        }

        ISelectedOption.openOption(optionSelected);
    }

    public void setISelectedOption(ISelectedOption ISelectedOption) {
        this.ISelectedOption = ISelectedOption;
    }
}
