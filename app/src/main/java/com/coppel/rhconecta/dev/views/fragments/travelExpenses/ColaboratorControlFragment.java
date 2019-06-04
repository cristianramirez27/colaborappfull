package com.coppel.rhconecta.dev.views.fragments.travelExpenses;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.views.activities.GastosViajeActivity;
import com.coppel.rhconecta.dev.views.customviews.CardColaboratorControl;
import com.coppel.rhconecta.dev.views.customviews.ExpandableSimpleTitle;
import com.coppel.rhconecta.dev.views.customviews.TextViewDetail;
import com.coppel.rhconecta.dev.views.customviews.TextViewExpandableHeader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColaboratorControlFragment extends Fragment implements  View.OnClickListener{

    public static final String TAG = ColaboratorControlFragment.class.getSimpleName();
    private GastosViajeActivity parent;
    @BindView(R.id.cardColaboratorControl)
    CardColaboratorControl cardColaboratorControl;
    @BindView(R.id.totalAutorizado)
    TextViewDetail totalAutorizado;
    @BindView(R.id.totalComprobado)
    TextViewDetail totalComprobado;
    @BindView(R.id.totalDevolucion)
    TextViewDetail totalDevolucion;

    @BindView(R.id.expMotivoViaje)
    ExpandableSimpleTitle expMotivoViaje;
    @BindView(R.id.expItinerario)
    ExpandableSimpleTitle expItinerario;
    @BindView(R.id.expViajerosAdicionales)
    ExpandableSimpleTitle expViajerosAdicionales;

    @BindView(R.id.layoutMotivoViaje)
    LinearLayout layoutMotivoViaje;


    private long mLastClickTime = 0;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_control_colaborador, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        parent = (GastosViajeActivity) getActivity();
        parent.setToolbarTitle(getString(R.string.title_colaborator_control));

        totalAutorizado.setTexts("Total autorizado", "$4,000.00");
        totalComprobado.setTexts("Total comprobado", "$0.00");
        totalDevolucion.setTexts("Total devolución", "$0.00");

        expMotivoViaje.setText("Motivo de viaje");
        expItinerario.setText("Itinerario");
        expViajerosAdicionales.setText("Viajeros adicionales");


        expMotivoViaje.setOnExpadableListener(new ExpandableSimpleTitle.OnExpadableListener() {
            @Override
            public void onClick() {
                travelReasonStateChange();
            }
        });

        return view;
    }

    private void travelReasonStateChange() {
        if (expMotivoViaje.isExpanded()) {
            layoutMotivoViaje.setVisibility(View.VISIBLE);
        } else {
            layoutMotivoViaje.setVisibility(View.GONE);
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

        if (SystemClock.elapsedRealtime() - mLastClickTime < 1200){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        switch (view.getId()) {
            case R.id.btnColaborator:

                break;

            case R.id.btnManager:

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

}
