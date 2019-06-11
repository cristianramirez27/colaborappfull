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
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.Enums.DetailExpenseTravelType;
import com.coppel.rhconecta.dev.business.Enums.ExpensesTravelType;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.ColaboratorControlsMonthResponse;
import com.coppel.rhconecta.dev.business.models.ColaboratorRequestsListExpensesResponse;
import com.coppel.rhconecta.dev.business.models.CoppelServicesDetailControlExpensesRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesDetailRequestExpensesRequest;
import com.coppel.rhconecta.dev.business.models.DetailControlColaboratorResponse;
import com.coppel.rhconecta.dev.business.models.DetailExpenseTravelData;
import com.coppel.rhconecta.dev.business.models.DetailRequest;
import com.coppel.rhconecta.dev.business.models.DetailRequestColaboratorResponse;
import com.coppel.rhconecta.dev.business.models.Devolution;
import com.coppel.rhconecta.dev.business.models.ExpensesTravelRequestData;
import com.coppel.rhconecta.dev.business.models.Itinerary;
import com.coppel.rhconecta.dev.business.models.ReasonTravel;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.NavigationUtil;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.GastosViajeActivity;
import com.coppel.rhconecta.dev.views.activities.GastosViajeDetalleActivity;
import com.coppel.rhconecta.dev.views.customviews.CardColaboratorControl;
import com.coppel.rhconecta.dev.views.customviews.ExpandableSimpleTitle;
import com.coppel.rhconecta.dev.views.customviews.Itinerario;
import com.coppel.rhconecta.dev.views.customviews.TextViewDetail;
import com.coppel.rhconecta.dev.views.customviews.TextViewExpandableHeader;
import com.coppel.rhconecta.dev.views.customviews.ViajerosAdicionales;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.fragments.benefits.DiscountsFragment;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_DATA_DETAIL_EXPENSE_TRAVEL;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_LETTER;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_DATA_TRAVEL_EXPENSES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_TRAVEL_EXPENSES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_MANAGER;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_MORE_DETAIL_COMPLEMENT;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_MORE_DETAIL_CONTROL;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_MORE_DETAIL_REQUEST;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColaboratorControlFragment extends Fragment implements  View.OnClickListener, IServicesContract.View,
        DialogFragmentWarning.OnOptionClick{

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
    @BindView(R.id.layoutItinerario)
    LinearLayout layoutItinerario;

    @BindView(R.id.layoutViajerosExtras)
    LinearLayout layoutViajerosExtras;

    //Motivo
    @BindView(R.id.txtMotivo)
    TextView txtMotivo;
    @BindView(R.id.txtMotivoDescripcion)
    TextView txtMotivoDescripcion;

    @BindView(R.id.itinerarioView)
    Itinerario itinerario;
    @BindView(R.id.viajeroAdicionales)
    ViajerosAdicionales viajeroAdicionales;

    @BindView(R.id.verDetalles)
    TextView verDetalles;

    @BindView(R.id.hospedaje)
    TextView hospedaje;
    @BindView(R.id.txtObservacionesDescripcion)
    TextView txtObservacionesDescripcion;

    private DialogFragmentWarning dialogFragmentWarning;


    @BindView(R.id.totalComplemento)
    TextViewDetail totalComplemento;
    @BindView(R.id.verDetallesComplemento)
    TextView verDetallesComplemento;
    Object dataItem;

    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;

    private DetailExpenseTravelData detailExpenseTravelData;

    private long mLastClickTime = 0;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    public static ColaboratorControlFragment getInstance(DetailExpenseTravelData detailExpenseTravelData){
        ColaboratorControlFragment fragment = new ColaboratorControlFragment();
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_DATA_DETAIL_EXPENSE_TRAVEL,detailExpenseTravelData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.detailExpenseTravelData = (DetailExpenseTravelData)getArguments().getSerializable(BUNDLE_DATA_DETAIL_EXPENSE_TRAVEL);
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_control_colaborador, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        parent = (GastosViajeActivity) getActivity();
        parent.setToolbarTitle(getString(R.string.title_colaborator_control));

        verDetalles.setOnClickListener(this);
        verDetallesComplemento.setOnClickListener(this);
        cardColaboratorControl.setVisibleColaboratorInfo(View.GONE);


        expMotivoViaje.setOnExpadableListener(new ExpandableSimpleTitle.OnExpadableListener() {
            @Override
            public void onClick() {
                travelReasonStateChange(expMotivoViaje,layoutMotivoViaje);
            }
        });

        expItinerario.setOnExpadableListener(new ExpandableSimpleTitle.OnExpadableListener() {
            @Override
            public void onClick() {
                travelReasonStateChange(expItinerario,layoutItinerario);
            }
        });

        expViajerosAdicionales.setOnExpadableListener(new ExpandableSimpleTitle.OnExpadableListener() {
            @Override
            public void onClick() {
                travelReasonStateChange(expViajerosAdicionales,layoutViajerosExtras);
            }
        });

        return view;
    }

    private void travelReasonStateChange(ExpandableSimpleTitle expandable,LinearLayout layoutToExpand) {
        if (expandable.isExpanded()) {
            layoutToExpand.setVisibility(View.VISIBLE);
        } else {
            layoutToExpand.setVisibility(View.GONE);
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getData(this.detailExpenseTravelData.getDetailExpenseTravelType());
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
            case R.id.verDetallesComplemento:
                openDetail(true);
                break;
            case R.id.verDetalles:
                openDetail(false);
                break;

        }

    }

    private void openDetail(boolean isComplement){

        if(dataItem instanceof DetailRequestColaboratorResponse){
            String detail = !isComplement ? OPTION_MORE_DETAIL_REQUEST: OPTION_MORE_DETAIL_COMPLEMENT ;
                    NavigationUtil.openActivityParamsSerializable(getActivity(), GastosViajeDetalleActivity.class,
                            BUNDLE_OPTION_DATA_TRAVEL_EXPENSES, (DetailRequestColaboratorResponse)dataItem,
                            BUNDLE_OPTION_TRAVEL_EXPENSES,detail);
        } else if(dataItem instanceof DetailControlColaboratorResponse){
            NavigationUtil.openActivityParamsSerializable(getActivity(), GastosViajeDetalleActivity.class,
                    BUNDLE_OPTION_DATA_TRAVEL_EXPENSES, (DetailControlColaboratorResponse)dataItem,
                    BUNDLE_OPTION_TRAVEL_EXPENSES,OPTION_MORE_DETAIL_CONTROL);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }


    private void getData(DetailExpenseTravelType detailExpenseTravelType){


        String numEmployer = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        ExpensesTravelRequestData expensesTravelRequestData;

        switch (detailExpenseTravelType){

            case SOLICITUD:
            case COMPLEMENTO:
                expensesTravelRequestData = new ExpensesTravelRequestData(ExpensesTravelType.CONSULTA_DETALLE_SOLICITUD, 4,numEmployer);
                expensesTravelRequestData.setClv_solicitud(detailExpenseTravelData.getClave());
                coppelServicesPresenter.getExpensesTravel(expensesTravelRequestData,token);

                break;

            case CONTROL:
            case CONTROL_LIQUIDO_NOLIQUIDO:
                expensesTravelRequestData = new ExpensesTravelRequestData(ExpensesTravelType.CONSULTA_DETALLE_CONTROL, 5,numEmployer);
                expensesTravelRequestData.setClv_control(detailExpenseTravelData.getClave());
                coppelServicesPresenter.getExpensesTravel(expensesTravelRequestData,token);
                break;

        }
    }


    @Override
    public void showResponse(ServicesResponse response) {

        switch (response.getType()) {

            case ServicesRequestType.EXPENSESTRAVEL:
                if(response.getResponse() instanceof DetailRequestColaboratorResponse){

                    DetailRequestColaboratorResponse detailRequestColaboratorResponse = (DetailRequestColaboratorResponse)response.getResponse();

                    if(detailRequestColaboratorResponse.getData().getResponse().getMensaje() != null &&
                            !detailRequestColaboratorResponse.getData().getResponse().getMensaje().isEmpty()){
                        showWarningDialog(detailRequestColaboratorResponse.getData().getResponse().getMensaje());
                    }else {
                        dataItem = response.getResponse();
                        setDataRequest(detailRequestColaboratorResponse);
                    }

                }else if(response.getResponse() instanceof DetailControlColaboratorResponse){
                    DetailControlColaboratorResponse detailControlColaboratorResponse = (DetailControlColaboratorResponse)response.getResponse();

                    if(detailControlColaboratorResponse.getData().getResponse().getMensaje() != null &&
                            !detailControlColaboratorResponse.getData().getResponse().getMensaje().isEmpty()){
                        showWarningDialog(detailControlColaboratorResponse.getData().getResponse().getMensaje());
                    }else {
                        dataItem = response.getResponse();
                        setDataControl(detailControlColaboratorResponse);
                    }
                }
                break;
        }

    }

    private void setDataRequest(DetailRequestColaboratorResponse detail){

        expMotivoViaje.setText("Motivo de viaje");
        expItinerario.setText("Itinerario");
        expViajerosAdicionales.setText("Viajeros adicionales");

        ColaboratorRequestsListExpensesResponse.RequestComplementsColaborator requestComplementsColaborator = (ColaboratorRequestsListExpensesResponse.RequestComplementsColaborator) detailExpenseTravelData.getData();
        cardColaboratorControl.setItinerario(requestComplementsColaborator.getItinerario());
        cardColaboratorControl.setFechas(String.format("%s\n%s",requestComplementsColaborator.getFechasalida(),requestComplementsColaborator.getFecharegreso()));
        cardColaboratorControl.setStatus(
                1,
                ((ColaboratorRequestsListExpensesResponse.RequestComplementsColaborator)detailExpenseTravelData.getData()).getClv_estatus(),
                ((ColaboratorRequestsListExpensesResponse.RequestComplementsColaborator)detailExpenseTravelData.getData()).getDes_color(),
                ((ColaboratorRequestsListExpensesResponse.RequestComplementsColaborator)detailExpenseTravelData.getData()).getEstatus());


        totalAutorizado.setTextsSize(15,18);
        totalAutorizado.hideDivider();

        if(detailExpenseTravelData.getDetailExpenseTravelType() == DetailExpenseTravelType.COMPLEMENTO){
            cardColaboratorControl.setTituloControl("Complemento");
            cardColaboratorControl.setNumeroControl(String.valueOf(this.detailExpenseTravelData.getClave()));
            if(detail.getData().getResponse().getVerDetallesComplemento() != null && !detail.getData().getResponse().getVerDetallesComplemento().isEmpty()){
                for(DetailRequest gastoComplemento : detail.getData().getResponse().getVerDetallesComplemento()){
                    if(gastoComplemento.getIdu_tipoGasto() == -1){
                        totalComplemento.setTexts("Total complemento\nsolicitado",
                                TextUtilities.getNumberInCurrencyFormat(
                                        Double.parseDouble(String.valueOf(gastoComplemento.getImp_total()))) );
                        totalComplemento.setVisibility(View.VISIBLE);
                        verDetallesComplemento.setVisibility(View.VISIBLE);
                        break;
                    }
                }
            }
        }else {
            cardColaboratorControl.setTituloControl("Solicitud de viaje");
            if(detail.getData().getResponse().getVerDetallesSolicitud() != null && !detail.getData().getResponse().getVerDetallesSolicitud().isEmpty()){
                for(DetailRequest detallesSolicitud : detail.getData().getResponse().getVerDetallesSolicitud()){
                    if(detallesSolicitud.getIdu_tipoGasto() == -1){
                        totalAutorizado.setTexts("Monto solicitado",
                                TextUtilities.getNumberInCurrencyFormat(
                                        Double.parseDouble(String.valueOf(detallesSolicitud.getImp_total()))));
                        totalAutorizado.setVisibility(View.VISIBLE);
                        break;
                    }
                }
            }
        }

        ReasonTravel reasonTravel =  detail.getData().getResponse().getMotivoViaje().get(0);
        txtMotivo.setText(reasonTravel.getDes_motivo());
        txtMotivoDescripcion.setText(reasonTravel.getDes_especificacion());
        int numNochestotal = 0;
        for(Itinerary itinerary: detail.getData().getResponse().getItinerario()){
            numNochestotal+= itinerary.getNum_noches();
        }
        itinerario.setDataRecyclerView(detail.getData().getResponse().getItinerario());
        itinerario.setTotalNoches(String.valueOf(numNochestotal));
       if(detail.getData().getResponse().getItinerarioMulticiudad() != null &&
                !detail.getData().getResponse().getItinerarioMulticiudad().isEmpty()){

            if(detail.getData().getResponse().getItinerarioMulticiudad().get(0).getClv_multiciudad() == 1){
                itinerario.setTipoItinerario(detail.getData().getResponse().getItinerarioMulticiudad().get(0).getDes_multiciudad());
                itinerario.setVisibility(View.VISIBLE);
            }
        }

        if(detail.getData().getResponse().getViajerosAdicionales() != null &&
                !detail.getData().getResponse().getViajerosAdicionales().isEmpty()){
            viajeroAdicionales.setDataRecyclerView(detail.getData().getResponse().getViajerosAdicionales());
        }else {
            expViajerosAdicionales.setVisibility(View.GONE);
        }

        hospedaje.setText(detail.getData().getResponse().getTipoHospedaje().get(0).getDes_tipoHospedaje());
        txtObservacionesDescripcion.setText(detail.getData().getResponse().getObservaciones().get(0).getDes_observacionesColaborador());
    }

    private void setDataControl(DetailControlColaboratorResponse detail){
        expMotivoViaje.setText("Motivo de viaje");
        expItinerario.setText("Itinerario");
        expViajerosAdicionales.setText("Viajeros adicionales");

        if(detailExpenseTravelData.getData() instanceof ColaboratorRequestsListExpensesResponse.ControlColaborator){
            ColaboratorRequestsListExpensesResponse.ControlColaborator control = (ColaboratorRequestsListExpensesResponse.ControlColaborator) detailExpenseTravelData.getData();
            cardColaboratorControl.setItinerario(String.valueOf(control.getItinerario()));
            cardColaboratorControl.setFechas(String.format("%s\n%s",control.getFec_salida(),control.getFec_regreso()));
            cardColaboratorControl.setStatus(
                    2,
                    ((ColaboratorRequestsListExpensesResponse.ControlColaborator)detailExpenseTravelData.getData()).getClv_estatus(),
                    ((ColaboratorRequestsListExpensesResponse.ControlColaborator)detailExpenseTravelData.getData()).getDes_color(),
                    ((ColaboratorRequestsListExpensesResponse.ControlColaborator)detailExpenseTravelData.getData()).getEstatus());

        }else if(detailExpenseTravelData.getData() instanceof ColaboratorControlsMonthResponse.ControlMonth){
            ColaboratorControlsMonthResponse.ControlMonth controlMonth = (ColaboratorControlsMonthResponse.ControlMonth) detailExpenseTravelData.getData();
            cardColaboratorControl.setItinerario(String.valueOf(controlMonth.getItinerario()));
            cardColaboratorControl.setFechas(String.format("%s\n%s",controlMonth.getFec_salida(),controlMonth.getFec_regreso()));
            cardColaboratorControl.setStatus(
                    3,
                    ((ColaboratorControlsMonthResponse.ControlMonth)detailExpenseTravelData.getData()).getClv_estatus(),
                    ((ColaboratorControlsMonthResponse.ControlMonth)detailExpenseTravelData.getData()).getDes_color(),
                    ((ColaboratorControlsMonthResponse.ControlMonth)detailExpenseTravelData.getData()).getEstatus());

        }

        cardColaboratorControl.setNumeroControl(String.valueOf(detailExpenseTravelData.getClave()));

        totalAutorizado.setTextsSize(15,18);
        totalAutorizado.hideDivider();
        totalComprobado.setTextsSize(15,18);
        totalDevolucion.setTextsSize(15,18);
        totalDevolucion.hideDivider();
        if(detail.getData().getResponse().getGastoAutorizado() != null && !detail.getData().getResponse().getGastoAutorizado().isEmpty()){
            for(DetailRequest gastoAutorizado : detail.getData().getResponse().getGastoAutorizado()){
                if(gastoAutorizado.getIdu_tipoGasto() == -1){
                    totalAutorizado.setTexts("Total autorizado",
                            TextUtilities.getNumberInCurrencyFormat(
                                    Double.parseDouble(String.valueOf(gastoAutorizado.getImp_total()))));


                    totalAutorizado.setVisibility(View.VISIBLE);
                    break;
                }
            }
        }

        if(detail.getData().getResponse().getGastoComprobado() != null && !detail.getData().getResponse().getGastoComprobado().isEmpty()) {
            for (DetailRequest gastoComprobado : detail.getData().getResponse().getGastoComprobado()) {
                if (gastoComprobado.getIdu_tipoGasto() == -1) {
                    totalComprobado.setTexts("Total comprobado",
                            TextUtilities.getNumberInCurrencyFormat(
                                    Double.parseDouble(String.valueOf(gastoComprobado.getImp_total()))));
                    totalComprobado.setVisibility(View.VISIBLE);
                    break;
                }
            }
        }

        if(detail.getData().getResponse().getDevoluciones() != null && !detail.getData().getResponse().getDevoluciones().isEmpty()) {
            for (Devolution devolution : detail.getData().getResponse().getDevoluciones()) {
                if (devolution.getDu_tipoGasto() == -1) {
                    totalDevolucion.setTexts("Total devolución",  TextUtilities.getNumberInCurrencyFormat(
                            Double.parseDouble( String.valueOf(devolution.getTotal()))));
                    totalDevolucion.setVisibility(View.VISIBLE);
                    break;
                }
            }
        }

       ReasonTravel reasonTravel =  detail.getData().getResponse().getMotivoViaje().get(0);
        txtMotivo.setText(reasonTravel.getDes_motivo());
        txtMotivoDescripcion.setText(reasonTravel.getDes_especificacion());
        int numNochestotal = 0;
        for(Itinerary itinerary: detail.getData().getResponse().getItinerario()){
            numNochestotal+= itinerary.getNum_noches();
        }

        itinerario.setDataRecyclerView(detail.getData().getResponse().getItinerario());
        itinerario.setTotalNoches(String.valueOf(numNochestotal));

        if(detail.getData().getResponse().getItinerarioMulticiudad() != null &&
                !detail.getData().getResponse().getItinerarioMulticiudad().isEmpty()){
            if(detail.getData().getResponse().getItinerarioMulticiudad().get(0).getClv_multiciudad() == 1){
                itinerario.setTipoItinerario(detail.getData().getResponse().getItinerarioMulticiudad().get(0).getDes_multiciudad());
                itinerario.setVisibility(View.VISIBLE);
            }
        }

        if(detail.getData().getResponse().getViajerosAdicionales() != null &&
                !detail.getData().getResponse().getViajerosAdicionales().isEmpty()){
            viajeroAdicionales.setDataRecyclerView(detail.getData().getResponse().getViajerosAdicionales());
        }else {
            expViajerosAdicionales.setVisibility(View.GONE);
        }


        hospedaje.setText(detail.getData().getResponse().getTipoHospedaje().get(0).getDes_tipoHospedaje());
        txtObservacionesDescripcion.setText(detail.getData().getResponse().getObservaciones().get(0).getDes_observacionesColaborador());

    }

    @Override
    public void showError(ServicesError coppelServicesError) {

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

    private void showWarningDialog(String message) {
        dialogFragmentWarning = new DialogFragmentWarning();
        dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), message, getString(R.string.accept));
        dialogFragmentWarning.setOnOptionClick(this);
        dialogFragmentWarning.show(parent.getSupportFragmentManager(), DialogFragmentWarning.TAG);
    }

    @Override
    public void onLeftOptionClick() {
        dialogFragmentWarning.close();
    }

    @Override
    public void onRightOptionClick() {

        dialogFragmentWarning.close();
        getActivity().finish();
    }
}
