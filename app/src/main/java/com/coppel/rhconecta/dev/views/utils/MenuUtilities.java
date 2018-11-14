package com.coppel.rhconecta.dev.views.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.content.res.AppCompatResources;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.VoucherResponse;
import com.coppel.rhconecta.dev.resources.db.RealmHelper;
import com.coppel.rhconecta.dev.resources.db.models.HomeMenuItem;
import com.coppel.rhconecta.dev.resources.db.models.UserPreference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuUtilities {

    public static List<HomeMenuItem> getHomeMenuItems(Context context, String email, boolean isSlide) {
        UserPreference userPreferences = RealmHelper.getUserPreferences(email);
        List<HomeMenuItem> homeMenuItems = new ArrayList<>();
        if (userPreferences != null && userPreferences.getMenuItems() != null && userPreferences.getMenuItems().size() > 0 && !isSlide) {
            homeMenuItems.addAll(userPreferences.getMenuItems());
        } else {
            if (isSlide) {
                homeMenuItems.add(new HomeMenuItem(context.getString(R.string.title_home), AppConstants.OPTION_HOME));
            }
            homeMenuItems.addAll(Arrays.asList(
                    new HomeMenuItem(context.getString(R.string.notices), AppConstants.OPTION_NOTICE),
                    new HomeMenuItem(context.getString(R.string.payroll_voucher), AppConstants.OPTION_PAYROLL_VOUCHER),
                    //new HomeMenuItem(context.getString(R.string.benefits), AppConstants.OPTION_BENEFITS),
                    new HomeMenuItem(context.getString(R.string.loan_saving_fund), AppConstants.OPTION_SAVING_FUND),
                    new HomeMenuItem(context.getString(R.string.visionaries), AppConstants.OPTION_VISIONARIES)));
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

}
