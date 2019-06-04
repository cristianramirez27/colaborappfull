package com.coppel.rhconecta.dev.views.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.content.res.AppCompatResources;
import android.util.Log;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.VoucherResponse;
import com.coppel.rhconecta.dev.resources.db.RealmHelper;
import com.coppel.rhconecta.dev.resources.db.models.HomeMenuItem;
import com.coppel.rhconecta.dev.resources.db.models.UserPreference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

public class MenuUtilities {

    public static List<HomeMenuItem> getHomeMenuItems(Context context, String email, boolean isSlide,int[] notifications) {

        /*Setamos el menu por default*/
        List<HomeMenuItem> listMenuDefault = Arrays.asList(
                new HomeMenuItem(context.getString(R.string.notices), AppConstants.OPTION_NOTICE,notifications[0]),
                new HomeMenuItem(context.getString(R.string.payroll_voucher), AppConstants.OPTION_PAYROLL_VOUCHER),
                new HomeMenuItem(context.getString(R.string.benefits), AppConstants.OPTION_BENEFITS),
                new HomeMenuItem(context.getString(R.string.loan_saving_fund), AppConstants.OPTION_SAVING_FUND),
                new HomeMenuItem(context.getString(R.string.employment_letters), AppConstants.OPTION_LETTERS),
                new HomeMenuItem(context.getString(R.string.travel_expenses), AppConstants.OPTION_EXPENSES),
                new HomeMenuItem(context.getString(R.string.visionaries), AppConstants.OPTION_VISIONARIES,notifications[1]));

        HashMap<String,HomeMenuItem> mapNames = new HashMap<>();

        for(HomeMenuItem item : listMenuDefault){
            mapNames.put(item.getTAG(),item);
        }


        UserPreference userPreferences = RealmHelper.getUserPreferences(email);
        List<HomeMenuItem> homeMenuItems = new ArrayList<>();
        if (userPreferences != null && userPreferences.getMenuItems() != null && userPreferences.getMenuItems().size() > 0 && !isSlide) {

            RealmList<HomeMenuItem> menus =  userPreferences.getMenuItems();

            List<String> listMenuSavedtTAG = new ArrayList<>();
            for(HomeMenuItem item : menus){
                listMenuSavedtTAG.add(item.getTAG());
            }



            /**Validamos si se agregaron todos los items del menu en las preferencias, en caso contrario, lo agregamos*/
            if(listMenuSavedtTAG.size() < listMenuDefault.size()){
                for (HomeMenuItem homeMenuItem : listMenuDefault){
                    if(!listMenuSavedtTAG.contains(homeMenuItem.getTAG())){
                        menus.add(homeMenuItem);
                    }
                }
            }


            Realm realm = Realm.getDefaultInstance();
            if (!realm.isInTransaction()) realm.beginTransaction();
            for (int i =0;i<menus.size();i++){
                try{


                    /*Actualizamos el nombre de las opciones del menu guardadas*/
                    if(menus.get(i).getName().compareToIgnoreCase(mapNames.get(menus.get(i).getTAG()).getName()) != 0){
                        menus.get(i).setName(mapNames.get(menus.get(i).getTAG()).getName());
                    }


                    if(menus.get(i).getTAG().equals(AppConstants.OPTION_NOTICE)){ // agrega notificaciones a comunicados
                        if(notifications.length>0){
                            menus.get(i).setNotifications(notifications[0]);
                        }
                    }

                    if(menus.get(i).getTAG().equals(AppConstants.OPTION_VISIONARIES)){ // agrega notificaciones a videos visionarios
                        if(notifications.length>1){
                            menus.get(i).setNotifications(notifications[1]);
                        }
                    }
                }catch (Exception e){
                    Log.d("MenuUtilities","Error add notifications menu: "+e.getMessage());
                }

            }
            realm.commitTransaction();

            homeMenuItems.addAll(menus);
        } else {
            if (isSlide) {
                homeMenuItems.add(new HomeMenuItem(context.getString(R.string.title_home), AppConstants.OPTION_HOME));
            }
            homeMenuItems.addAll(listMenuDefault);
        }
        return homeMenuItems;
    }

    public static Drawable getIconByTag(String tag, Context context) {
        Drawable icon = null;
        switch (tag) {
            case AppConstants.OPTION_HOME:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_home);
                break;
            case AppConstants.OPTION_NOTICE:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_notices);
                break;
            case AppConstants.OPTION_PAYROLL_VOUCHER:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_payroll);
                break;
            case AppConstants.OPTION_BENEFITS:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_benefits);
                break;
            case AppConstants.OPTION_SAVING_FUND:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_saving_fund);
                break;
            case AppConstants.OPTION_VISIONARIES:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_visionaries);
                break;
            case AppConstants.OPTION_POLL:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_poll);
                break;
            case AppConstants.OPTION_BONUS:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_bonus);
                break;
            case AppConstants.OPTION_GAS:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_gas);
                break;
            case AppConstants.OPTION_PTU:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_ptu);
                break;
            case AppConstants.OPTION_ALIMONY:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_alimony);
                break;
            case AppConstants.OPTION_LETTERS:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_cartaslaborales);
                break;
            case AppConstants.OPTION_WORK_RECORD:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_constancia_laboral);
                break;
            case AppConstants.OPTION_VISA_PASSPORT:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_visa_pasaporte);
                break;
            case AppConstants.OPTION_BANK_CREDIT:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_credito_bancario);
                break;
            case AppConstants.OPTION_IMSS:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_imss);
                break;
            case AppConstants.OPTION_INFONAVIT:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_infonavit);
                break;
            case AppConstants.OPTION_KINDERGARTEN:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_guarderia);
                break;

            case AppConstants.OPTION_EXPENSES:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_gastos_viaje);
                break;
        }
        return icon;
    }

    public static List<HomeMenuItem> getPayrollMenu(Context context, VoucherResponse.Response voucherResponse) {
        List<HomeMenuItem> menuItems = new ArrayList<>();
        if (voucherResponse.getFechas_nominas() != null && voucherResponse.getFechas_nominas().size() > 0 && !voucherResponse.getFechas_nominas().get(0).getSfechanomina().equals("0")) {
            menuItems.add(new HomeMenuItem(context.getString(R.string.payroll), AppConstants.OPTION_PAYROLL_VOUCHER));
        }
        if (voucherResponse.getFechas_Aguinaldo() != null && voucherResponse.getFechas_Aguinaldo().size() > 0 && !voucherResponse.getFechas_Aguinaldo().get(0).getSfechanomina().equals("0")) {
            menuItems.add(new HomeMenuItem(context.getString(R.string.bonus), AppConstants.OPTION_BONUS));
        }
        if (voucherResponse.getFecha_Corte_Cuenta() != null && voucherResponse.getFecha_Corte_Cuenta().size() > 0 && !voucherResponse.getFecha_Corte_Cuenta().get(0).getSfechanomina().equals("0")) {
            menuItems.add(new HomeMenuItem(context.getString(R.string.saving_fund), AppConstants.OPTION_SAVING_FUND));
        }
        if (voucherResponse.getFecha_Gasolina() != null && voucherResponse.getFecha_Gasolina().size() > 0 && !voucherResponse.getFecha_Gasolina().get(0).getSfechanomina().equals("0")) {
            menuItems.add(new HomeMenuItem(context.getString(R.string.gas), AppConstants.OPTION_GAS));
        }
        if (voucherResponse.getFechas_Utilidades() != null && voucherResponse.getFechas_Utilidades().size() > 0 && !voucherResponse.getFechas_Utilidades().get(0).getSfechanomina().equals("0")) {
            menuItems.add(new HomeMenuItem(context.getString(R.string.ptu), AppConstants.OPTION_PTU));
        }
        if (voucherResponse.getFechasPensiones() != null && voucherResponse.getFechasPensiones().size() > 0 && !voucherResponse.getFechasPensiones().get(0).getSfechanomina().equals("0")) {
            menuItems.add(new HomeMenuItem(context.getString(R.string.alimony), AppConstants.OPTION_ALIMONY));
        }
        return menuItems;
    }

    public static List<HomeMenuItem> getEmploymentLettersMenu(Context context) {
        List<HomeMenuItem> menuItems = new ArrayList<>();
        menuItems.add(new HomeMenuItem(context.getString(R.string.work_record), AppConstants.OPTION_WORK_RECORD));
        menuItems.add(new HomeMenuItem(context.getString(R.string.visa_passport), AppConstants.OPTION_VISA_PASSPORT));
        menuItems.add(new HomeMenuItem(context.getString(R.string.bank_credit), AppConstants.OPTION_BANK_CREDIT));
        menuItems.add(new HomeMenuItem(context.getString(R.string.imss), AppConstants.OPTION_IMSS));
        menuItems.add(new HomeMenuItem(context.getString(R.string.infonavit), AppConstants.OPTION_INFONAVIT));
        menuItems.add(new HomeMenuItem(context.getString(R.string.kindergarten), AppConstants.OPTION_KINDERGARTEN));

        return menuItems;
    }
}