package com.coppel.rhconecta.dev.views.adapters;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IScheduleOptions;
import com.coppel.rhconecta.dev.business.models.HolidayPeriod;
import com.coppel.rhconecta.dev.views.fragments.holidays.gte.holidaysrequest.ColaboratorRequestHolidaysFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.coppel.rhconecta.dev.CoppelApp.getContext;

public class HolidayRequestRecyclerAdapter extends RecyclerView.Adapter<HolidayRequestRecyclerAdapter.ViewHolder> {

    private List<HolidayPeriod> dataItems;
    private IScheduleOptions IScheduleOptions;
    private boolean isSchedule;
    private boolean showIcon;
    private boolean hideCheckBox;
    private boolean fullHorizontalView;
    private boolean showAuthorize;
    private boolean changeStyleCheckbox;

    private int layoutItem = R.layout.item_solicitud_vacaciones;
    private OnRequestSelectedClickListener OnRequestSelectedClickListener;

    private ColaboratorRequestHolidaysFragment.ICalendarView ICalendarView;


    private boolean isGte;

    public HolidayRequestRecyclerAdapter(List<HolidayPeriod> holidays,IScheduleOptions IScheduleOptions,boolean isSchedule,boolean showIcon) {
       // this.isColaborator = isColaborator;
        this.dataItems = holidays;
        this.isSchedule = isSchedule;
        this.showIcon = showIcon;
        this.IScheduleOptions = IScheduleOptions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(layoutItem, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.itemView.setHasTransientState(true);
        viewHolder.fechaInicio.setText(dataItems.get(i).getFec_ini());
        viewHolder.fechaFin.setText(dataItems.get(i).getFec_fin());
        String daysNumber = "";
        try {
            daysNumber = String.valueOf(dataItems.get(i).getNum_dias()).split(" ")[0].trim();
            if (Double.parseDouble(daysNumber) % 1 == 0) {
                daysNumber = daysNumber.substring(0, daysNumber.indexOf("."));
                daysNumber = String.valueOf(Integer.parseInt(daysNumber));
            }

        }catch (Exception e){
            daysNumber = String.valueOf(dataItems.get(i).getNum_dias());
        }


        viewHolder.diasVacaciones.setText(String.format("%s %s",daysNumber, daysNumber.equals("1") ? "día" : "días"));
        viewHolder.layoutEstatusContainer.setVisibility(hasEstatus(dataItems.get(i).getNom_estatus(),dataItems.get(i).getNom_estaus()) ?  VISIBLE : GONE);

        int visibilityCheckElement = !hideCheckBox && showCheckOption(dataItems.get(i)) ? VISIBLE :
                fullHorizontalView ? GONE : INVISIBLE;
        viewHolder.checkboxElement.setVisibility(visibilityCheckElement);
        viewHolder.cbxLayout.setVisibility(visibilityCheckElement);

        viewHolder.checkboxElement.setChecked(dataItems.get(i).isSelected());

        viewHolder.ic_solicitud.setVisibility(showIcon ? VISIBLE : INVISIBLE);
        if(showIcon){
            viewHolder.ic_solicitud.setImageResource(getIcon(dataItems.get(i).getIdu_estatus()));
        }

        viewHolder.markerSplice.setVisibility(dataItems.get(i).getIdu_marca() == 1 ? VISIBLE : GONE);

        viewHolder.checkboxElement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    dataItems.get(i).setSelected(true);
                    if(isGte){
                        IScheduleOptions.showAuthorizeOption(true);
                        IScheduleOptions.showEliminatedOption(true,"Rechazar");
                    }else {
                        IScheduleOptions.showEliminatedOption(true,isSchedule ? "Eliminar" :"Cancelar vacaciones");
                    }

                    IScheduleOptions.showTitle(false);
                }else {
                    dataItems.get(i).setSelected(false);
                    hideEliminateOptionIfIsNecessary();
                }
                if(ICalendarView != null){
                    ICalendarView.enableCalendarOption(isSingleSelection());
                }
            }
        });

        viewHolder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(visibilityCheckElement == VISIBLE)
                    viewHolder.checkboxElement.setChecked(!viewHolder.checkboxElement.isChecked());
            }
        });


        if(!isSchedule){

            if(dataItems.get(i).getColorletra() != null && !dataItems.get(i).getColorletra().isEmpty())
                viewHolder.estatus.setTextColor( Color.parseColor(dataItems.get(i).getColorletra()));

            viewHolder.estatus.setText(dataItems.get(i).getNom_estatus() != null ? dataItems.get(i).getNom_estatus() :
                    (dataItems.get(i).getNom_estaus() != null ? dataItems.get(i).getNom_estaus() : ""));
            //viewHolder.estatus.setTextColor(Color.parseColor(dataItems.get(i).getDes_colorletra()));
            GradientDrawable gd = new GradientDrawable();
            gd.setColor(Color.parseColor(dataItems.get(i).getColor()));
            gd.setCornerRadius(80);
            viewHolder.estatus.setBackgroundDrawable(gd);
        }

        viewHolder.ic_solicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnRequestSelectedClickListener.onRequestSelectedClick(dataItems.get(i));
                unCheckedAll();
            }
        });

    }

    public void setShowAuthorize(boolean showAuthorize) {
        this.showAuthorize = showAuthorize;
    }

    public void setHideCheckBox(boolean hideCheckBox) {
        this.hideCheckBox = hideCheckBox;
    }

    private boolean hasEstatus(String status,String staus){

        if(status != null || staus != null)
            return true;

        return false;
    }

    private boolean isSingleSelection(){
        int selectedCount = 0 ;
        for(HolidayPeriod period : dataItems){
            if(period.isSelected()){
                selectedCount++;
            }
        }

        return selectedCount == 1 ? true : false;
    }


    public void unCheckedAll(){
        for(HolidayPeriod period : dataItems){
            period.setSelected(false);
        }

        IScheduleOptions.showTitle(true);
        notifyDataSetChanged();
    }

    private int getIcon(int status){

        switch (status){

            case 1:
                return R.drawable.ic_icn_calendar;

            case 4:
            case 3:
            case 2:
                return R.drawable.ic_icn_masinfo;
                default:
                    return R.drawable.ic_icn_calendar;

        }

    }

    public void setGte(boolean gte) {
        isGte = gte;
    }

    public void setICalendarView(ColaboratorRequestHolidaysFragment.ICalendarView ICalendarView) {
        this.ICalendarView = ICalendarView;
    }

    private boolean showCheckOption(HolidayPeriod period){

        if(isSchedule)
            return true;

        if(period.getIdu_estatus()>= 2 && period.getIdu_estatus() <=4) {
            return false;
        }

        /**Validar que la fecha de inicio sea mayor a la fecha actual*/
        String[] dateFormat =  period.getFec_ini().split(",")[1].split("-");
        Calendar datePeriod = Calendar.getInstance();
        datePeriod.set(Calendar.YEAR,Integer.parseInt(dateFormat[2].trim()));
        datePeriod.set(Calendar.MONTH,Integer.parseInt(dateFormat[1].trim())-1);
        datePeriod.set(Calendar.DAY_OF_MONTH,Integer.parseInt(dateFormat[0].trim()));
        Calendar today = Calendar.getInstance();
        if(!datePeriod.after(today)){
            return false;
        }


        return true;
    }


    private void hideEliminateOptionIfIsNecessary(){
        for( HolidayPeriod period : dataItems){
            if(period.isSelected()){
                if(isGte){
                    IScheduleOptions.showEliminatedOption(true ,"Rechazar");
                    IScheduleOptions.showAuthorizeOption(true);
                }else {
                    IScheduleOptions.showEliminatedOption(true ,isSchedule ? "Eliminar" :"Cancelar vacaciones");
                }
                return;
            }
        }

        IScheduleOptions.showAuthorizeOption(false);
        IScheduleOptions.showEliminatedOption(false,"");
        IScheduleOptions.showTitle(true);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public List<HolidayPeriod> getDataItemsSelected(){
        List<HolidayPeriod> periodsSelected = new ArrayList<>();
        for (HolidayPeriod period : dataItems){
            if(period.isSelected()){
                periodsSelected.add(period);
            }
        }

        return periodsSelected;

    }

    public List<HolidayPeriod> getAllItems(){
        return dataItems;

    }


    public void setLayoutItem(int layoutItem) {
        this.layoutItem = layoutItem;
    }

    public void setChangeStyleCheckbox(boolean changeStyleCheckbox) {
        this.changeStyleCheckbox = changeStyleCheckbox;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cardview)
        CardView cardview;
        @BindView(R.id.checkboxElement)
        CheckBox checkboxElement;
        @BindView(R.id.layoutEstatus)
        LinearLayout layoutEstatus;
        @BindView(R.id.layoutEstatusContainer)
        RelativeLayout layoutEstatusContainer;
        @BindView(R.id.estatus)
        TextView estatus;
        @BindView(R.id.ic_solicitud)
        ImageView ic_solicitud;
        @BindView(R.id.fechaInicio)
        TextView fechaInicio;
        @BindView(R.id.fechaFin)
        TextView fechaFin;
        @BindView(R.id.diasVacaciones)
        TextView diasVacaciones;
        @BindView(R.id.markerSplice)
        View markerSplice;

        @BindView(R.id.cbxLayout)
        RelativeLayout cbxLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnRequestSelectedClickListener {
        void onRequestSelectedClick(HolidayPeriod holidayPeriod);
    }

    public void setOnRequestSelectedClickListener(HolidayRequestRecyclerAdapter.OnRequestSelectedClickListener onRequestSelectedClickListener) {
        OnRequestSelectedClickListener = onRequestSelectedClickListener;
    }
}
