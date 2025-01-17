package com.coppel.rhconecta.dev.views.fragments.travelExpenses;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.utils.OnEventListener;
import com.coppel.rhconecta.dev.resources.db.models.HomeMenuItem;
import com.coppel.rhconecta.dev.views.activities.GastosViajeActivity;
import com.coppel.rhconecta.dev.views.adapters.IconsMenuRecyclerAdapter;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.views.utils.MenuUtilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_TRAVEL_MANAGER_AUTHORIZE;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_TRAVEL_MANAGER_AUTHORIZE_MESSAGE;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_TRAVEL_MANAGER_CONTROLS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_TRAVEL_MANAGER_CONTROLS_MESSAGE;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.YES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_AUTHORIZE_REQUEST;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_AUTHORIZE_REQUESTS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_CONSULT_CONTROLS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_CONTROLS_LIQ;

/**
 * A simple {@link Fragment} subclass.
 */
public class TravelExpensesManagerFragment extends Fragment implements View.OnClickListener, IconsMenuRecyclerAdapter.OnItemClick {

    public static final String TAG = TravelExpensesManagerFragment.class.getSimpleName();
    private GastosViajeActivity parent;
    @BindView(R.id.btnAuthorize)
    Button btnAuthorize;
    @BindView(R.id.btnControls)
    Button btnControls;
    @BindView(R.id.rcvOptions)
    RecyclerView rcvOptions;
    private List<HomeMenuItem> menuItems;
    private IconsMenuRecyclerAdapter iconsMenuRecyclerAdapter;
    private OnEventListener OnEventListener;

    private long mLastClickTime = 0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        OnEventListener = (OnEventListener) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.select_manager_travel_expenses_fragment, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        parent = (GastosViajeActivity) getActivity();
        parent.setToolbarTitle(getString(R.string.travel_expenses_manager));
        btnAuthorize.setOnClickListener(this);
        btnControls.setOnClickListener(this);

        /**Se inicia menu con iconos**/
        rcvOptions.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rcvOptions.setLayoutManager(gridLayoutManager);
        menuItems = new ArrayList<>();
        menuItems.addAll(MenuUtilities.getExpensesManagerMenu(parent));
        iconsMenuRecyclerAdapter = new IconsMenuRecyclerAdapter(parent, menuItems, gridLayoutManager.getSpanCount());
        iconsMenuRecyclerAdapter.setOnItemClick(this);
        rcvOptions.setAdapter(iconsMenuRecyclerAdapter);

        /*requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                Log.i("BackStackCount", "Count: " + fragmentManager.getBackStackEntryCount());
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack(); // Vuelve al fragment anterior
                } else {
                    requireActivity().finish(); // Cierra la actividad si no hay más fragments en la pila
                }
            }
        });*/

        return view;
    }


    @Override
    public void onItemClick(String tag) {

        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        switch (tag) {

            case OPTION_AUTHORIZE_REQUESTS:
                if (AppUtilities.getStringFromSharedPreferences(requireContext(), BLOCK_TRAVEL_MANAGER_AUTHORIZE).equals(YES)) {
                    AppUtilities.showBlockDialog(AppUtilities.getStringFromSharedPreferences(requireContext(), BLOCK_TRAVEL_MANAGER_AUTHORIZE_MESSAGE), getString(R.string.attention), getString(R.string.accept), getChildFragmentManager());
                } else {
                    OnEventListener.onEvent(OPTION_AUTHORIZE_REQUEST, null);
                }
                break;
            case OPTION_CONTROLS_LIQ:
                if (AppUtilities.getStringFromSharedPreferences(requireContext(), BLOCK_TRAVEL_MANAGER_CONTROLS).equals(YES)) {
                    AppUtilities.showBlockDialog(AppUtilities.getStringFromSharedPreferences(requireContext(), BLOCK_TRAVEL_MANAGER_CONTROLS_MESSAGE), getString(R.string.attention), getString(R.string.accept), getChildFragmentManager());
                } else {
                    OnEventListener.onEvent(OPTION_CONSULT_CONTROLS, null);
                }
                break;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
    public void onClick(View view) {

        if (SystemClock.elapsedRealtime() - mLastClickTime < 1200) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        switch (view.getId()) {
            case R.id.btnAuthorize:
                OnEventListener.onEvent(OPTION_AUTHORIZE_REQUEST, null);
                break;

            case R.id.btnControls:
                OnEventListener.onEvent(OPTION_CONSULT_CONTROLS, null);
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

}
