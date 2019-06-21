package com.coppel.rhconecta.dev.views.fragments.travelExpenses;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.Enums.DetailExpenseTravelType;
import com.coppel.rhconecta.dev.business.Enums.ExpensesTravelType;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.AuthorizedRequestColaboratorSingleton;
import com.coppel.rhconecta.dev.business.models.AuthorizedResponse;
import com.coppel.rhconecta.dev.business.models.ColaboratorControlsMonthResponse;
import com.coppel.rhconecta.dev.business.models.ColaboratorRequestsListExpensesResponse;
import com.coppel.rhconecta.dev.business.models.DetailControlColaboratorResponse;
import com.coppel.rhconecta.dev.business.models.DetailExpenseTravelData;
import com.coppel.rhconecta.dev.business.models.DetailRequest;
import com.coppel.rhconecta.dev.business.models.DetailRequestColaboratorResponse;
import com.coppel.rhconecta.dev.business.models.Devolution;
import com.coppel.rhconecta.dev.business.models.ExpensesTravelRequestData;
import com.coppel.rhconecta.dev.business.models.ImportsList;
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
import com.coppel.rhconecta.dev.views.customviews.ViajerosAdicionales;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;
import static android.view.View.VISIBLE;
import static com.coppel.rhconecta.dev.business.Enums.DetailExpenseTravelType.COMPLEMENTO;
import static com.coppel.rhconecta.dev.business.Enums.DetailExpenseTravelType.CONTROL;
import static com.coppel.rhconecta.dev.business.Enums.DetailExpenseTravelType.CONTROLES_GTE;
import static com.coppel.rhconecta.dev.business.Enums.DetailExpenseTravelType.CONTROL_LIQUIDO_NOLIQUIDO;
import static com.coppel.rhconecta.dev.business.Enums.DetailExpenseTravelType.SOLICITUD;
import static com.coppel.rhconecta.dev.business.Enums.DetailExpenseTravelType.SOLICITUD_A_AUTORIZAR;
import static com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument.MSG_EXPENSES_TRAVEL;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_DATA_DETAIL_EXPENSE_TRAVEL;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_DATA_TRAVEL_EXPENSES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_TRAVEL_EXPENSES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_MORE_DETAIL_COMPLEMENT;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_MORE_DETAIL_CONTROL;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_MORE_DETAIL_REQUEST;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_REFUSE_REQUEST;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_GTE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColaboratorControlFragment extends Fragment implements  View.OnClickListener, IServicesContract.View,
        DialogFragmentWarning.OnOptionClick,DialogFragmentGetDocument.OnButtonClickListener{

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

    @BindView(R.id.layoutHospedaje)
    LinearLayout layoutHospedaje;
    @BindView(R.id.hospedaje)
    TextView hospedaje;

    @BindView(R.id.layoutObservaciones)
    LinearLayout layoutObservaciones;
    @BindView(R.id.txtObservacionesDescripcion)
    TextView txtObservacionesDescripcion;

    @BindView(R.id.titleObservationGte)
    TextView titleObservationGte;
    @BindView(R.id.txtObservacionesGteDescripcion)
    TextView txtObservacionesGteDescripcion;

    @BindView(R.id.layoutObservacionesGte)
    LinearLayout layoutObservacionesGte;
    @BindView(R.id.observationGte)
    EditText observationGte;
    @BindView(R.id.btnActionLeft)
    Button btnActionLeft;
    @BindView(R.id.btnActionRight)
    Button btnActionRight;

    private DialogFragmentWarning dialogFragmentWarning;


    @BindView(R.id.totalComplemento)
    TextViewDetail totalComplemento;
    @BindView(R.id.verDetallesComplemento)
    TextView verDetallesComplemento;
    Object dataItem;

    private boolean EXPIRED_SESSION;

    private DialogFragmentGetDocument dialogFragmentGetDocument;
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
        AuthorizedRequestColaboratorSingleton.getInstance().resetValues();

    }

    @Override
    public void onResume() {
        super.onResume();
        reCalculateTotal();

    }

    private void reCalculateTotal(){
        boolean isEdit = false;

        if(this.detailExpenseTravelData.getDetailExpenseTravelType() == SOLICITUD_A_AUTORIZAR){
            if( AuthorizedRequestColaboratorSingleton.getInstance().getCoppelServicesAuthorizedRequest().getCapturaGerente() != null){
                for(DetailRequest detailRequest :  AuthorizedRequestColaboratorSingleton.getInstance().getCoppelServicesAuthorizedRequest().getCapturaGerente()){
                    if(detailRequest.getIdu_tipoGasto() == -1){

                        if(AuthorizedRequestColaboratorSingleton.getInstance().getCoppelServicesAuthorizedRequest().getCapturaGerente() != null
                                && !AuthorizedRequestColaboratorSingleton.getInstance().getCoppelServicesAuthorizedRequest().getCapturaGerente().isEmpty()) {
                            isEdit = true;
                        }

                        String amount = detailRequest.getImp_total().replace(",","");
                        totalAutorizado.setTexts("Monto solicitado",
                                !isEdit  ?String.format("$%s",detailRequest.getImp_total()):
                                        String.format("%s", TextUtilities.getNumberInCurrencyFormat(Double.parseDouble( amount))));
                    }
                }
            }
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_control_colaborador, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        parent = (GastosViajeActivity) getActivity();

        verDetalles.setOnClickListener(this);
        verDetallesComplemento.setOnClickListener(this);
        btnActionLeft.setOnClickListener(this);
        btnActionRight.setOnClickListener(this);
        observationGte.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

        setTitle();

        cardColaboratorControl.setVisibleColaboratorInfo(
                detailExpenseTravelData.getDetailExpenseTravelType() == SOLICITUD_A_AUTORIZAR ||
                detailExpenseTravelData.getDetailExpenseTravelType() == CONTROLES_GTE ? VISIBLE : View.GONE  );


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
            layoutToExpand.setVisibility(VISIBLE);
        } else {
            layoutToExpand.setVisibility(View.GONE);
        }
    }

    private void setTitle(){

        String title = "";

        switch (this.detailExpenseTravelData.getDetailExpenseTravelType()){

            case SOLICITUD:
                title = getString(R.string.title_request_expenses);
                break;
            case COMPLEMENTO:
                title = getString(R.string.title_complemento_expenses);
                break;

            case SOLICITUD_A_AUTORIZAR:
                title = getString(R.string.title_request_expenses);
                break;

            case CONTROL:
                title = getString(R.string.title_my_control);
                break;

            case CONTROL_LIQUIDO_NOLIQUIDO:
                title = getString(R.string.title_my_control);
                break;

            case CONTROLES_GTE:
                title = getString(R.string.title_colaborator_control);
                break;

        }

        parent.setToolbarTitle(title);
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

            case R.id.btnActionLeft:
                openRefuseRequest();
                break;

            case R.id.btnActionRight:
                authorizeRequest();
                break;
        }

    }

    private void openDetail(boolean isComplement){

        if(dataItem instanceof DetailRequestColaboratorResponse){
            String detail = !isComplement ? OPTION_MORE_DETAIL_REQUEST: OPTION_MORE_DETAIL_COMPLEMENT ;

            ImportsList importsListRequest = new ImportsList();
            importsListRequest.setImportes( !isComplement  ?
                    ((DetailRequestColaboratorResponse)dataItem).getData().getResponse().getVerDetallesSolicitud() :
                    ((DetailRequestColaboratorResponse)dataItem).getData().getResponse().getVerDetallesComplemento());

            if (detailExpenseTravelData.getDetailExpenseTravelType() == SOLICITUD_A_AUTORIZAR) {
                //Tipo = 1 para complemento
                int tipo =  ((ColaboratorRequestsListExpensesResponse.RequestComplementsColaborator)this.detailExpenseTravelData.getData()).getTipo();
                importsListRequest.setType(isComplement ? 1 : 0);
                if(isComplement && tipo == 1){//Es solicitud
                    importsListRequest.setShowEdit(true);
                }else if(!isComplement && tipo == 0){
                    importsListRequest.setShowEdit(true);
                }else if(!isComplement && tipo == 1) {
                    importsListRequest.setShowEdit(false);
                }
            }

                    NavigationUtil.openActivityParamsSerializable(getActivity(), GastosViajeDetalleActivity.class,
                            BUNDLE_OPTION_DATA_TRAVEL_EXPENSES, importsListRequest,
                            BUNDLE_OPTION_TRAVEL_EXPENSES,detail);
        } else if(dataItem instanceof DetailControlColaboratorResponse){
            NavigationUtil.openActivityParamsSerializable(getActivity(), GastosViajeDetalleActivity.class,
                    BUNDLE_OPTION_DATA_TRAVEL_EXPENSES, (DetailControlColaboratorResponse)dataItem,
                    BUNDLE_OPTION_TRAVEL_EXPENSES,OPTION_MORE_DETAIL_CONTROL);
        }
    }

    private void openRefuseRequest(){

        this.detailExpenseTravelData.setObservations(getObservations());
        NavigationUtil.openActivityParamsSerializableRequestCode(getActivity(), GastosViajeDetalleActivity.class,
                BUNDLE_OPTION_DATA_TRAVEL_EXPENSES, this.detailExpenseTravelData,
                BUNDLE_OPTION_TRAVEL_EXPENSES,OPTION_REFUSE_REQUEST,888);
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

            case SOLICITUD_A_AUTORIZAR:

                ColaboratorRequestsListExpensesResponse.RequestComplementsColaborator requestData = (ColaboratorRequestsListExpensesResponse.RequestComplementsColaborator) this.detailExpenseTravelData.getData();

                expensesTravelRequestData = new ExpensesTravelRequestData(ExpensesTravelType.CONSULTA_DETALLE_SOLICITUD,
                        4,String.valueOf(requestData.getNumeviajero()));
                expensesTravelRequestData.setClv_solicitud(detailExpenseTravelData.getClave());
                coppelServicesPresenter.getExpensesTravel(expensesTravelRequestData,token);

                break;



            case CONTROL:
                expensesTravelRequestData = new ExpensesTravelRequestData(ExpensesTravelType.CONSULTA_DETALLE_CONTROL, 5,numEmployer);
                expensesTravelRequestData.setClv_control(detailExpenseTravelData.getClave());
                coppelServicesPresenter.getExpensesTravel(expensesTravelRequestData,token);
                break;

            case CONTROL_LIQUIDO_NOLIQUIDO:
                ColaboratorControlsMonthResponse.ControlMonth requestDataControlsLiqGte = (ColaboratorControlsMonthResponse.ControlMonth) this.detailExpenseTravelData.getData();

                numEmployer = String.valueOf(requestDataControlsLiqGte.getNumviajero());
                expensesTravelRequestData = new ExpensesTravelRequestData(ExpensesTravelType.CONSULTA_DETALLE_CONTROL, 5,numEmployer);
                expensesTravelRequestData.setClv_control(detailExpenseTravelData.getClave());
                coppelServicesPresenter.getExpensesTravel(expensesTravelRequestData,token);
                break;


            case CONTROLES_GTE:

                ColaboratorRequestsListExpensesResponse.ControlColaborator requestDataControlsGte = (ColaboratorRequestsListExpensesResponse.ControlColaborator) this.detailExpenseTravelData.getData();

                expensesTravelRequestData = new ExpensesTravelRequestData(ExpensesTravelType.CONSULTA_DETALLE_CONTROL,
                        5,String.valueOf(requestDataControlsGte.getNum_viajero()));
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
                }else if(response.getResponse() instanceof AuthorizedResponse){

                    showAlertDialog(((AuthorizedResponse)response.getResponse()).getData().getResponse().getDes_mensaje());

                }

                break;
        }

    }

    private void setDataRequest(DetailRequestColaboratorResponse detail){

        expMotivoViaje.setText("Motivo de viaje");

        expMotivoViaje.setVisibility(detail.getData().getResponse().getMotivoViaje() != null &&
                detail.getData().getResponse().getMotivoViaje().size() > 0 &&
                detail.getData().getResponse().getMotivoViaje().get(0).getDes_motivo() != null &&
                !detail.getData().getResponse().getMotivoViaje().get(0).getDes_motivo().isEmpty() ? VISIBLE : View.GONE);

        expItinerario.setText("Itinerario");
        expViajerosAdicionales.setText("Viajeros adicionales");

        ColaboratorRequestsListExpensesResponse.RequestComplementsColaborator requestComplementsColaborator = (ColaboratorRequestsListExpensesResponse.RequestComplementsColaborator) detailExpenseTravelData.getData();
        cardColaboratorControl.setItinerario(requestComplementsColaborator.getItinerario());
        cardColaboratorControl.setFechas(String.format("%s\n%s",requestComplementsColaborator.getFechasalida(),requestComplementsColaborator.getFecharegreso()));
       if(detailExpenseTravelData.getDetailExpenseTravelType() == SOLICITUD_A_AUTORIZAR){
           cardColaboratorControl.setStatus(
                   1,
                   ((ColaboratorRequestsListExpensesResponse.RequestComplementsColaborator)detailExpenseTravelData.getData()).getClv_estatus(),
                   ((ColaboratorRequestsListExpensesResponse.RequestComplementsColaborator)detailExpenseTravelData.getData()).getDes_colorletra(),
                   ((ColaboratorRequestsListExpensesResponse.RequestComplementsColaborator)detailExpenseTravelData.getData()).getDes_color(),
                   ((ColaboratorRequestsListExpensesResponse.RequestComplementsColaborator)detailExpenseTravelData.getData()).getNom_estatus());
       }else {
           cardColaboratorControl.setStatus(
                   1,
                   ((ColaboratorRequestsListExpensesResponse.RequestComplementsColaborator)detailExpenseTravelData.getData()).getClv_estatus(),
                   ((ColaboratorRequestsListExpensesResponse.RequestComplementsColaborator)detailExpenseTravelData.getData()).getDes_colorletra(),
                   ((ColaboratorRequestsListExpensesResponse.RequestComplementsColaborator)detailExpenseTravelData.getData()).getDes_color(),
                   ((ColaboratorRequestsListExpensesResponse.RequestComplementsColaborator)detailExpenseTravelData.getData()).getEstatus());
       }





        totalAutorizado.setTextsSize(15,18);
        totalAutorizado.hideDivider();

        if(detailExpenseTravelData.getDetailExpenseTravelType() == DetailExpenseTravelType.COMPLEMENTO){

            AuthorizedRequestColaboratorSingleton.getInstance().getCoppelServicesAuthorizedRequest()
                    .setCapturaGerente(detail.getData().getResponse().getVerDetallesComplemento());


            cardColaboratorControl.setBackgroundCard(R.color.colorBackgroundVerdeClaro);
            cardColaboratorControl.setTituloControl("Complemento");
            cardColaboratorControl.setNumeroControl(String.valueOf(this.detailExpenseTravelData.getClave()));
            if(detail.getData().getResponse().getVerDetallesComplemento() != null && !detail.getData().getResponse().getVerDetallesComplemento().isEmpty()){
                for(DetailRequest gastoComplemento : detail.getData().getResponse().getVerDetallesComplemento()){
                    if(gastoComplemento.getIdu_tipoGasto() == -1){
                        totalComplemento.setTexts("Total complemento\nsolicitado",String.format("$%s",gastoComplemento.getImp_total()));
                        totalComplemento.setVisibility(VISIBLE);
                        verDetallesComplemento.setVisibility(VISIBLE);
                        break;
                    }
                }
            }
        }

        else {

            AuthorizedRequestColaboratorSingleton.getInstance().getCoppelServicesAuthorizedRequest()
                    .setCapturaGerente(detail.getData().getResponse().getVerDetallesSolicitud());


            cardColaboratorControl.setBackgroundCard(R.color.colorBackgroundRosaClaro);
            cardColaboratorControl.setTituloControl("Solicitud de viaje");
            if(detail.getData().getResponse().getVerDetallesSolicitud() != null && !detail.getData().getResponse().getVerDetallesSolicitud().isEmpty()){
                for(DetailRequest detallesSolicitud : detail.getData().getResponse().getVerDetallesSolicitud()){
                    if(detallesSolicitud.getIdu_tipoGasto() == -1){
                        totalAutorizado.setTexts("Monto solicitado",String.format("$%s",detallesSolicitud.getImp_total()));
                        totalAutorizado.setVisibility(VISIBLE);
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
                itinerario.setVisibility(VISIBLE);
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

        //Mostramos las observaciones del Gte
        if( detailExpenseTravelData.getDetailExpenseTravelType() == SOLICITUD_A_AUTORIZAR ){

            if(detail.getData().getResponse().getObservaciones().get(0).getDes_observacionesGerente() != null &&
                    !detail.getData().getResponse().getObservaciones().get(0).getDes_observacionesGerente().isEmpty()){
                txtObservacionesGteDescripcion.setVisibility(VISIBLE);
                txtObservacionesGteDescripcion.setText(detail.getData().getResponse().getObservaciones().get(0).getDes_observacionesGerente());
                titleObservationGte.setVisibility(VISIBLE);
            }



            cardColaboratorControl.setNombreColaborador(requestComplementsColaborator.getNomviajero());
            cardColaboratorControl.setNumColaborador(String.valueOf(requestComplementsColaborator.getNumeviajero()));

            if(detail.getData().getResponse().getFotoPerfil() != null && !detail.getData().getResponse().getFotoPerfil().isEmpty() &&
                    detail.getData().getResponse().getFotoPerfil().get(0) != null &&  detail.getData().getResponse().getFotoPerfil().get(0).getFotoperfil() != null){
                String base64Picture = detail.getData().getResponse().getFotoPerfil().get(0).getFotoperfil();
                base64Picture = base64Picture.substring(base64Picture.indexOf(",")  + 1);
                 byte[] decodedString = Base64.decode(base64Picture, Base64.DEFAULT);
                 Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                 cardColaboratorControl.setImgColaborador(decodedByte);
            }

            //Validamos el estatus Pendiente
            layoutObservacionesGte.setVisibility(((ColaboratorRequestsListExpensesResponse.RequestComplementsColaborator)detailExpenseTravelData.getData()).getClv_estatus() == 1 ? VISIBLE : View.GONE);

            //TODO REMOVE Test
            layoutObservacionesGte.setVisibility(VISIBLE);


            observationGte.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if(s.toString().length() > 4){
                        btnActionRight.setEnabled(true);
                        btnActionRight.setBackgroundResource(R.drawable.background_blue_rounded);
                    }else {
                        btnActionRight.setEnabled(false);
                        btnActionRight.setBackgroundResource(R.drawable.background_darkgray_rounder);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });






        }


    }

    private void setDataControl(DetailControlColaboratorResponse detail){

        cardColaboratorControl.setBackgroundCard(R.color.colorBackgroundVerdeClaro);
        String nomViajero = "";
        String numViajero = "";
        expMotivoViaje.setText("Motivo de viaje");
        expMotivoViaje.setVisibility(detail.getData().getResponse().getMotivoViaje() != null &&
                detail.getData().getResponse().getMotivoViaje().size() > 0 &&
                detail.getData().getResponse().getMotivoViaje().get(0).getDes_motivo() != null &&
                !detail.getData().getResponse().getMotivoViaje().get(0).getDes_motivo().isEmpty() ? VISIBLE : View.GONE);

        expItinerario.setText("Itinerario");
        expViajerosAdicionales.setText("Viajeros adicionales");

        cardColaboratorControl.setTituloControl("Control");
        if(detailExpenseTravelData.getData() instanceof ColaboratorRequestsListExpensesResponse.ControlColaborator){
            ColaboratorRequestsListExpensesResponse.ControlColaborator control = (ColaboratorRequestsListExpensesResponse.ControlColaborator) detailExpenseTravelData.getData();
            cardColaboratorControl.setItinerario(String.valueOf(control.getItinerario()));
            cardColaboratorControl.setFechas(String.format("%s\n%s",control.getFec_salida(),control.getFec_regreso()));
            cardColaboratorControl.setStatus(
                    2,
                    ((ColaboratorRequestsListExpensesResponse.ControlColaborator)detailExpenseTravelData.getData()).getClv_estatus(),
                    ((ColaboratorRequestsListExpensesResponse.ControlColaborator)detailExpenseTravelData.getData()).getDes_colorletra(),
                    ((ColaboratorRequestsListExpensesResponse.ControlColaborator)detailExpenseTravelData.getData()).getDes_color(),
                    ((ColaboratorRequestsListExpensesResponse.ControlColaborator)detailExpenseTravelData.getData()).getEstatus());

            nomViajero = control.getNomviajero();
            numViajero = String.valueOf(control.getNum_viajero());
        }else if(detailExpenseTravelData.getData() instanceof ColaboratorControlsMonthResponse.ControlMonth){
            ColaboratorControlsMonthResponse.ControlMonth controlMonth = (ColaboratorControlsMonthResponse.ControlMonth) detailExpenseTravelData.getData();
            cardColaboratorControl.setItinerario(String.valueOf(controlMonth.getItinerario()));
            cardColaboratorControl.setFechas(String.format("%s\n%s",controlMonth.getFec_salida(),controlMonth.getFec_regreso()));
            cardColaboratorControl.setStatus(
                    3,
                    ((ColaboratorControlsMonthResponse.ControlMonth)detailExpenseTravelData.getData()).getClv_estatus(),
                    ((ColaboratorControlsMonthResponse.ControlMonth)detailExpenseTravelData.getData()).getDes_colorletra(),
                    ((ColaboratorControlsMonthResponse.ControlMonth)detailExpenseTravelData.getData()).getDes_color(),
                    ((ColaboratorControlsMonthResponse.ControlMonth)detailExpenseTravelData.getData()).getEstatus());

            nomViajero = controlMonth.getNomviajero();
            numViajero = String.valueOf(controlMonth.getNumviajero());

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
                    totalAutorizado.setTexts("Total autorizado",String.format("$%s",gastoAutorizado.getImp_total()));

                 /*   totalAutorizado.setTexts("Total autorizado",
                            TextUtilities.getNumberInCurrencyFormat(
                                    Double.parseDouble(TextUtilities.insertDecimalPoint(String.valueOf(gastoAutorizado.getImp_total())))));*/

                    totalAutorizado.setVisibility(VISIBLE);
                    break;
                }
            }
        }

        if(detail.getData().getResponse().getGastoComprobado() != null && !detail.getData().getResponse().getGastoComprobado().isEmpty()) {
            for (DetailRequest gastoComprobado : detail.getData().getResponse().getGastoComprobado()) {
                if (gastoComprobado.getIdu_tipoGasto() == -1) {
                    totalComprobado.setTexts("Total comprobado",String.format("$%s",gastoComprobado.getImp_total()));
                    totalComprobado.setVisibility(VISIBLE);
                    break;
                }
            }
        }

        if(detail.getData().getResponse().getDevoluciones() != null && !detail.getData().getResponse().getDevoluciones().isEmpty()) {
            for (Devolution devolution : detail.getData().getResponse().getDevoluciones()) {
                if (devolution.getDu_tipoGasto() == -1) {
                    totalDevolucion.setTexts("Total devolución", String.format("$%s",devolution.getTotal()));
                    totalDevolucion.setVisibility(VISIBLE);
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
                itinerario.setVisibility(VISIBLE);
            }
        }

        if(detail.getData().getResponse().getViajerosAdicionales() != null &&
                !detail.getData().getResponse().getViajerosAdicionales().isEmpty()){
            viajeroAdicionales.setDataRecyclerView(detail.getData().getResponse().getViajerosAdicionales());
        }else {
            expViajerosAdicionales.setVisibility(View.GONE);
        }

        layoutHospedaje.setVisibility(View.GONE);
        layoutObservaciones.setVisibility(View.GONE);

        /* No semuestrael tipo de hospedaje ni lasobservaciones
        hospedaje.setText(detail.getData().getResponse().getTipoHospedaje().get(0).getDes_tipoHospedaje());
        txtObservacionesDescripcion.setText(detail.getData().getResponse().getObservaciones().get(0).getDes_observacionesColaborador());

*/
        //Mostramos las observaciones del Gte
        if( detailExpenseTravelData.getDetailExpenseTravelType() == CONTROLES_GTE ){
           /* if(detail.getData().getResponse().getObservaciones().get(0).getDes_observacionesGerente() != null &&
                    !detail.getData().getResponse().getObservaciones().get(0).getDes_observacionesGerente().isEmpty()){
                txtObservacionesGteDescripcion.setVisibility(VISIBLE);
                txtObservacionesGteDescripcion.setText(detail.getData().getResponse().getObservaciones().get(0).getDes_observacionesGerente());
                titleObservationGte.setVisibility(VISIBLE);
            }*/

            cardColaboratorControl.setNombreColaborador(nomViajero);
            cardColaboratorControl.setNumColaborador(numViajero);

            if(detail.getData().getResponse().getFotoPerfil() != null && !detail.getData().getResponse().getFotoPerfil().isEmpty() &&
                    detail.getData().getResponse().getFotoPerfil().get(0) != null &&  detail.getData().getResponse().getFotoPerfil().get(0).getFotoperfil() != null){
                String base64Picture = detail.getData().getResponse().getFotoPerfil().get(0).getFotoperfil();
                base64Picture = base64Picture.substring(base64Picture.indexOf(",")  + 1);
                byte[] decodedString = Base64.decode(base64Picture, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                cardColaboratorControl.setImgColaborador(decodedByte);
            }
        }
    }

    @Override
    public void showError(ServicesError coppelServicesError) {
        if(coppelServicesError.getMessage() != null ){
            switch (coppelServicesError.getType()) {
                case ServicesRequestType.EXPENSESTRAVEL:
                    showWarningDialog(coppelServicesError.getMessage());
                    break;
                case ServicesRequestType.INVALID_TOKEN:
                    EXPIRED_SESSION = true;
                    showWarningDialog(getString(R.string.expired_session));
                    break;
            }

        }
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
        if (EXPIRED_SESSION) {
            AppUtilities.closeApp(parent);
        }else {
            dialogFragmentWarning.close();
            getActivity().finish();
        }

    }

    private void authorizeRequest(){
        if(!hasObservationGte()){
            Toast.makeText(getActivity(),"Favor de ingresar sus observaciones.",Toast.LENGTH_SHORT).show();
            return;
        }
        String numEmployer = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        String numGte = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_GTE);

        ExpensesTravelRequestData expensesTravelRequestData = new ExpensesTravelRequestData(ExpensesTravelType.AUTORIZAR_SOLICITUD, 8,numEmployer);
        expensesTravelRequestData.setClv_solicitud(detailExpenseTravelData.getClave());
        expensesTravelRequestData.setNum_gerente(Integer.parseInt(numGte));
        ColaboratorRequestsListExpensesResponse.RequestComplementsColaborator requestComplementsColaborator  = (ColaboratorRequestsListExpensesResponse.RequestComplementsColaborator) this.detailExpenseTravelData.getData();
        expensesTravelRequestData.setNum_control(Integer.parseInt(requestComplementsColaborator.getCLV_CONTROL()));
        expensesTravelRequestData.setClv_estatus(2);
        expensesTravelRequestData.setDes_observaciones(getObservations());/***----------**/
        expensesTravelRequestData.setDes_motivoRechazo("");
        expensesTravelRequestData.setClv_tipo(detailExpenseTravelData.getDetailExpenseTravelType() == COMPLEMENTO ? 1 : 0);
        AuthorizedRequestColaboratorSingleton requestColaboratorSingleton = AuthorizedRequestColaboratorSingleton.getInstance();
        expensesTravelRequestData.setCapturaGerente(requestColaboratorSingleton.getCoppelServicesAuthorizedRequest().getCapturaGerente());
        expensesTravelRequestData.setCapturaGerenteFormat(requestColaboratorSingleton.getCoppelServicesAuthorizedRequest().getCapturaGerente());



        coppelServicesPresenter.getExpensesTravel(expensesTravelRequestData,token);
    }

    private boolean hasObservationGte(){
        return observationGte.getText()!=null && !observationGte.getText().toString().isEmpty() ? true : false;
    }

    private String getObservations(){
        String colaboratorObsv = txtObservacionesDescripcion.getText() != null ? txtObservacionesDescripcion.getText().toString() : "";
        return String.format("COLABORADOR: %s GERENTE: %s",colaboratorObsv,  observationGte.getText().toString());
    }


    private void showAlertDialog(String msg) {
        dialogFragmentGetDocument = new DialogFragmentGetDocument();
        dialogFragmentGetDocument.setType(MSG_EXPENSES_TRAVEL, parent);
        dialogFragmentGetDocument.setContentText(msg);
        dialogFragmentGetDocument.setOnButtonClickListener(this);
        dialogFragmentGetDocument.show(parent.getSupportFragmentManager(), DialogFragmentGetDocument.TAG);
    }

    @Override
    public void onSend(String email) {

    }

    @Override
    public void onAccept() {
        dialogFragmentGetDocument.close();
        getActivity().finish();
    }
}
