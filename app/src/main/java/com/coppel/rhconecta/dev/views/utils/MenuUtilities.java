package com.coppel.rhconecta.dev.views.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.appcompat.content.res.AppCompatResources;

import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.VoucherResponseV2;
import com.coppel.rhconecta.dev.resources.db.RealmHelper;
import com.coppel.rhconecta.dev.resources.db.models.HomeMenuItem;
import com.coppel.rhconecta.dev.resources.db.models.NotificationsUser;
import com.coppel.rhconecta.dev.resources.db.models.UserPreference;
import com.coppel.rhconecta.dev.resources.db.models.MainSection;
import com.coppel.rhconecta.dev.resources.db.models.SectionDb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmList;

import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_COVID_SURVEY;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_QR;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_STAYHOME;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_COCREA;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.TITLE_COCREA;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_WHEATHER;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.TITLE_WHEATHER;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.YES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.ICON_AGUINALDO;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.ICON_FONDOAHORRO;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.ICON_GASOLINA;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.ICON_NOMINA;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.ICON_PENSION;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.ICON_PTU;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.MY_MOVEMENTS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_COLLABORATOR_AT_HOME;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_COLLAGE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_COVID_SURVEY;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_EXPENSES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_HOLIDAYS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_HOLIDAYS_COL;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_HOLIDAYS_GTE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_LETTERS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_QR_CODE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_SAVING_FUND;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_COCREA;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_WHEATHER;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_INTERAL_VACANCY;

public class MenuUtilities {
    static Map<String, Integer> sectionsMap = null;
    static Map<String, Integer> subSectionsMap = null;

    public static List<MainSection> getMainSection() {
        return RealmHelper.getListSection();
    }

    public static List<SectionDb> getSubSection() {
        return RealmHelper.getListSubSection();
    }

    public static Map<String, Integer> getSectionsMap() {
        if (sectionsMap == null) {
            sectionsMap = new HashMap<>();
            sectionsMap.put(AppConstants.OPTION_VISIONARIES, 1);
            sectionsMap.put(AppConstants.OPTION_NOTICE, 2);
            sectionsMap.put(AppConstants.OPTION_BENEFITS, 3);
            sectionsMap.put(OPTION_COCREA, 4);
            sectionsMap.put(OPTION_COVID_SURVEY, 5);
            sectionsMap.put(OPTION_WHEATHER, 6);
            sectionsMap.put(OPTION_COLLAGE, 7);
            sectionsMap.put(AppConstants.OPTION_PROFILE, 8);
            sectionsMap.put(OPTION_SAVING_FUND, 9);
            sectionsMap.put(AppConstants.OPTION_LETTERS, 10);
            sectionsMap.put(AppConstants.OPTION_EXPENSES, 11);
            sectionsMap.put(AppConstants.OPTION_EXPENSES_COL, 12);
            sectionsMap.put(AppConstants.OPTION_EXPENSES_GTE, 13);
            sectionsMap.put(OPTION_HOLIDAYS, 14);
            sectionsMap.put(OPTION_HOLIDAYS_COL, 15);
            sectionsMap.put(OPTION_HOLIDAYS_GTE, 16);
            sectionsMap.put(AppConstants.OPTION_PAYROLL_VOUCHER, 17);
            sectionsMap.put(AppConstants.OPTION_COLLABORATOR_AT_HOME, 18);
            sectionsMap.put(OPTION_QR_CODE, 20);
            sectionsMap.put(OPTION_INTERAL_VACANCY, 21);
        }
        return sectionsMap;
    }

    public static Map<String, Integer> getSubSectionsMap() {
        if (subSectionsMap == null) {
            subSectionsMap = new HashMap<>();
            subSectionsMap.put(AppConstants.OPTION_REMOVE, 2);
            subSectionsMap.put(AppConstants.OPTION_PAY, 3);
            subSectionsMap.put(AppConstants.OPTION_ADITIONAL_SAVED, 4);
            subSectionsMap.put(AppConstants.OPTION_WORK_RECORD, 5);
            subSectionsMap.put(AppConstants.OPTION_VISA_PASSPORT, 6);
            subSectionsMap.put(AppConstants.OPTION_BANK_CREDIT, 7);
            subSectionsMap.put(AppConstants.OPTION_INFONAVIT, 8);
            subSectionsMap.put(AppConstants.OPTION_IMSS, 9);
            subSectionsMap.put(AppConstants.OPTION_KINDERGARTEN, 10);
            subSectionsMap.put(AppConstants.OPTION_AUTHORIZE_REQUESTS, 11);
            subSectionsMap.put(AppConstants.OPTION_CONTROLS_LIQ, 12);
            subSectionsMap.put(AppConstants.OPTION_HOLIDAY_REQUESTS, 13);
            subSectionsMap.put(AppConstants.OPTION_CALENDAR_GRAL, 14);
            subSectionsMap.put(AppConstants.OPTION_ADITIONAL_DAYS, 15);
            subSectionsMap.put(AppConstants.OPTION_PAYROLL_VOUCHER, 16);
            subSectionsMap.put(AppConstants.OPTION_BONUS, 17);
            subSectionsMap.put(AppConstants.OPTION_GAS, 18);
            subSectionsMap.put(AppConstants.OPTION_PTU, 19);
            subSectionsMap.put(AppConstants.OPTION_ALIMONY, 20);
            subSectionsMap.put(AppConstants.OPTION_SAVING_FUND, 21);
            subSectionsMap.put(AppConstants.OPTION_MYS_MOVEMENTS, 22);
        }
        return subSectionsMap;
    }

    /**
     *
     */
    public static List<HomeMenuItem> getHomeMenuItems(Context context, String email, boolean isSlide, int[] notifications, boolean enableCoCrea) {

        String title_cocrea = AppUtilities.getStringFromSharedPreferences(context, TITLE_COCREA);
        if (title_cocrea == null || title_cocrea.isEmpty())
            title_cocrea = context.getString(R.string.title_cocrea);

        String title_wheather = AppUtilities.getStringFromSharedPreferences(context, TITLE_WHEATHER);
        if (title_wheather == null || title_wheather.isEmpty())
            title_wheather = context.getString(R.string.title_wheather);

        Map<String, Integer> sections = getSectionsMap();
        /*Setamos el menu por default*/
        ArrayList<HomeMenuItem> listMenuDefault = new ArrayList<>(Arrays.asList(
                new HomeMenuItem(context.getString(R.string.notices), AppConstants.OPTION_NOTICE, notifications[0]), //2
                new HomeMenuItem(context.getString(R.string.payroll_voucher), AppConstants.OPTION_PAYROLL_VOUCHER),//17
                new HomeMenuItem(context.getString(R.string.benefits), AppConstants.OPTION_BENEFITS), //3
                new HomeMenuItem(context.getString(R.string.loan_saving_fund), OPTION_SAVING_FUND),//9
                new HomeMenuItem(context.getString(R.string.employment_letters), AppConstants.OPTION_LETTERS),//10
                new HomeMenuItem(context.getString(R.string.travel_expenses), AppConstants.OPTION_EXPENSES),//11
                new HomeMenuItem(context.getString(R.string.request_holidays), OPTION_HOLIDAYS),//14
                new HomeMenuItem(context.getString(R.string.title_collage), OPTION_COLLAGE),//7
                new HomeMenuItem(context.getString(R.string.visionaries), AppConstants.OPTION_VISIONARIES, notifications[1]), //1
                new HomeMenuItem(context.getString(R.string.collaborator_at_home), AppConstants.OPTION_COLLABORATOR_AT_HOME, notifications[2]),//18
                new HomeMenuItem(context.getString(R.string.qrCode), OPTION_QR_CODE),//20
                new HomeMenuItem(context.getString(R.string.covid_survey_title), OPTION_COVID_SURVEY),//5
                new HomeMenuItem(title_wheather, OPTION_WHEATHER)//6
        ));

        List<MainSection> mainSections = RealmHelper.getListSection();

        if (enableCoCrea) {
            listMenuDefault.add(new HomeMenuItem(title_cocrea, OPTION_COCREA)); //4
        }


        HashMap<String, HomeMenuItem> mapNames = new HashMap<>();

        String numgColaborator = AppUtilities.getStringFromSharedPreferences(
                CoppelApp.getContext(),
                SHARED_PREFERENCES_NUM_COLABORADOR
        );

        List<NotificationsUser> notificationSaved = RealmHelper.getNotifications(numgColaborator);

        Map<Integer, Integer> mapNotification = new HashMap<>();
        mapNotification.put(9, 0);//fondo
        mapNotification.put(10, 0);//Vacaciones
        mapNotification.put(11, 0);//Gastos

        if (notificationSaved != null) {
            for (NotificationsUser notification : notificationSaved) {
                int count = mapNotification.get(notification.getID_SISTEMA()) + 1;
                mapNotification.put(
                        notification.getID_SISTEMA(),
                        count
                );
            }
        }

        for (HomeMenuItem item : listMenuDefault)
            mapNames.put(item.getTAG(), item);

        UserPreference userPreferences = RealmHelper.getUserPreferences(email);
        ArrayList<HomeMenuItem> homeMenuItems = new ArrayList<>();

        if (userPreferences != null && userPreferences.getMenuItems() != null && userPreferences.getMenuItems().size() > 0 && !isSlide) {

            RealmList<HomeMenuItem> menus = userPreferences.getMenuItems();
            List<String> listMenuSavedtTAG = new ArrayList<>();

            for (HomeMenuItem item : menus)
                listMenuSavedtTAG.add(item.getTAG());


            /**Validamos si se agregaron todos los items del menu en las preferencias, en caso contrario, lo agregamos*/
            if (listMenuSavedtTAG.size() < listMenuDefault.size()) {
                for (HomeMenuItem homeMenuItem : listMenuDefault) {
                    if (!listMenuSavedtTAG.contains(homeMenuItem.getTAG())) {
                        menus.add(homeMenuItem);
                    }
                }
            }


            Realm realm = Realm.getDefaultInstance();
            if (!realm.isInTransaction()) realm.beginTransaction();
            for (int i = 0; i < menus.size(); i++) {
                try {


                    /*Actualizamos el nombre de las opciones del menu guardadas*/
                    if (menus.get(i).getName().compareToIgnoreCase(mapNames.get(menus.get(i).getTAG()).getName()) != 0) {
                        menus.get(i).setName(mapNames.get(menus.get(i).getTAG()).getName());
                    }


                    /***Notificaciones de Fondo de ahorro**/
                    if (menus.get(i).getTAG().equals(OPTION_SAVING_FUND)) { // agrega notificaciones a vacaciones
                        if (mapNotification.containsKey(9)) {
                            menus.get(i).setNotifications(mapNotification.get(9));
                        }
                    }


                    /***Notificaciones de Vacaciones**/
                    if (menus.get(i).getTAG().equals(OPTION_HOLIDAYS)) { // agrega notificaciones a vacaciones
                        if (mapNotification.containsKey(10)) {
                            menus.get(i).setNotifications(mapNotification.get(10));
                        }
                    }

                    /***Notificaciones de Gastos de viaje**/
                    if (menus.get(i).getTAG().equals(OPTION_EXPENSES)) { // agrega notificaciones a vacaciones
                        if (mapNotification.containsKey(11)) {
                            menus.get(i).setNotifications(mapNotification.get(11));
                        }
                    }

                    if (menus.get(i).getTAG().equals(AppConstants.OPTION_NOTICE)) { // agrega notificaciones a comunicados
                        if (notifications.length > 0) {
                            menus.get(i).setNotifications(notifications[0]);
                        }
                    }

                    if (menus.get(i).getTAG().equals(AppConstants.OPTION_VISIONARIES)) { // agrega notificaciones a videos visionarios
                        if (notifications.length > 1) {
                            menus.get(i).setNotifications(notifications[1]);
                        }
                    }

                    if (menus.get(i).getTAG().equals(OPTION_COLLABORATOR_AT_HOME)) { // agrega notificaciones a videos colaborador en casa
                        if (notifications.length > 1) {
                            menus.get(i).setNotifications(notifications[2]);
                        }
                    }
                } catch (Exception e) {
                    /* Error add notifications menu: e.getMessage() */
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

        // Se verifica la visibilidad de las opciones
        boolean blockStayHome = AppUtilities.getStringFromSharedPreferences(context, BLOCK_STAYHOME).equals(YES);
        boolean blockQr = AppUtilities.getStringFromSharedPreferences(context, BLOCK_QR).equals(YES);
        boolean blockCovidSurvey = AppUtilities.getStringFromSharedPreferences(context, BLOCK_COVID_SURVEY).equals(YES);
        boolean blockCoCrea = AppUtilities.getStringFromSharedPreferences(context, BLOCK_COCREA).equals(YES);
        boolean blockWheather = AppUtilities.getStringFromSharedPreferences(context, BLOCK_WHEATHER).equals(YES);
        ArrayList<HomeMenuItem> response = new ArrayList<>();
        if (isSlide) {
            response.add(new HomeMenuItem(context.getString(R.string.title_home), AppConstants.OPTION_HOME));
        }
        if (MenuUtilities.isFilial(context)) {
            Integer index = null;
            for (HomeMenuItem item : homeMenuItems) {
                index = sections.get(item.getTAG());
                if (index != null && findItem(mainSections, index))
                    response.add(item);
            }
        } else {
            for (HomeMenuItem item : homeMenuItems) {
                if (item.getTAG().equals(OPTION_COLLABORATOR_AT_HOME) && blockStayHome)
                    continue;
                if (item.getTAG().equals(OPTION_QR_CODE) && blockQr)
                    continue;
                if (item.getTAG().equals(OPTION_COVID_SURVEY) && blockCovidSurvey)
                    continue;
                if (item.getTAG().equals(OPTION_COCREA) && blockCoCrea || (item.getTAG().equals(OPTION_COCREA) && !enableCoCrea))
                    continue;
                if (item.getTAG().equals(OPTION_WHEATHER) && blockWheather)
                    continue;
                response.add(item);
            }
        }
        return response;
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
            case OPTION_SAVING_FUND:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_saving_fund);
                break;
            case AppConstants.OPTION_VISIONARIES:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_visionaries);
                break;
            case AppConstants.OPTION_COLLABORATOR_AT_HOME:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_collaborator_at_home);
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

            case OPTION_EXPENSES:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_gastos_viaje);
                break;

            case AppConstants.OPTION_HOLIDAYS:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_vacaciones);
                break;

            case AppConstants.OPTION_COLLAGE:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_collage);
                break;
            /**Iconos para menu con iconos*/
            case AppConstants.OPTION_MENU_COLABORATOR:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_icn_colaborator);
                break;
            case AppConstants.OPTION_MENU_GTE:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_icn_manager);
                break;
            case AppConstants.OPTION_HOLIDAY_REQUESTS:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_icn_vacations_request);
                break;
            case AppConstants.OPTION_CALENDAR_GRAL:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_calendar_gral);
                break;
            case AppConstants.OPTION_ADITIONAL_DAYS:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_icn_aditional_days);
                break;
            case AppConstants.OPTION_AUTHORIZE_REQUESTS:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_icn_authorize);
                break;
            case AppConstants.OPTION_CONTROLS_LIQ:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_icn_active_controls);
                break;
            case AppConstants.OPTION_REMOVE:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_icn_withdraw);
                break;
            case AppConstants.OPTION_ADITIONAL_SAVED:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_icn_aditional_savings);
                break;
            case AppConstants.OPTION_PAY:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_icn_pay);
                break;
            case AppConstants.OPTION_QR_CODE:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_scanqr_web);
                break;
            case AppConstants.OPTION_COVID_SURVEY:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_covid_survey);
                break;
            case AppConstants.OPTION_COCREA:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_cocrea);
                break;
            case AppConstants.OPTION_WHEATHER:
                icon = AppCompatResources.getDrawable(context, R.drawable.clima);
                break;
            case MY_MOVEMENTS:
                icon = AppCompatResources.getDrawable(context, R.drawable.ic_movements);
                break;
        }
        return icon;
    }

    public static List<HomeMenuItem> getPayrollMenu(Context context, VoucherResponseV2.Response voucherResponse) {
        List<HomeMenuItem> menuItems = new ArrayList<>();
        List<HomeMenuItem> resultMenuItems = new ArrayList<>();
        for (VoucherResponseV2.OpcionNomina opcionNomina : voucherResponse.getOpciones_nomina()) {
            if (!opcionNomina.getSfechanomina().equals("0")) {
                switch (opcionNomina.itipocomprobante) {

                    case ICON_NOMINA:
                        menuItems.add(new HomeMenuItem(context.getString(R.string.payroll), AppConstants.OPTION_PAYROLL_VOUCHER));
                        break;
                    case ICON_FONDOAHORRO:
                        menuItems.add(new HomeMenuItem(context.getString(R.string.saving_fund), OPTION_SAVING_FUND));
                        break;
                    case ICON_GASOLINA:
                        menuItems.add(new HomeMenuItem(context.getString(R.string.gas), AppConstants.OPTION_GAS));
                        break;

                    case ICON_PTU:
                        menuItems.add(new HomeMenuItem(context.getString(R.string.ptu), AppConstants.OPTION_PTU));
                        break;
                    case ICON_PENSION:
                        menuItems.add(new HomeMenuItem(context.getString(R.string.alimony), AppConstants.OPTION_ALIMONY));
                        break;

                    case ICON_AGUINALDO:
                        menuItems.add(new HomeMenuItem(context.getString(R.string.bonus), AppConstants.OPTION_BONUS));
                        break;
                }
            }
        }
        if (MenuUtilities.isFilial(context)) {
            List<SectionDb> subSections = getSubSection();
            for (HomeMenuItem item : menuItems) {
                if (findSubItem(subSections, getSectionsMap().get(AppConstants.OPTION_PAYROLL_VOUCHER), getSubSectionsMap().get(item.getTAG())))
                    resultMenuItems.add(item);
            }
            menuItems = resultMenuItems;
        }


        return menuItems;
    }

    public static List<HomeMenuItem> getEmploymentLettersMenu(Context context) {
        List<HomeMenuItem> menuItems;
        int[] idRes = {R.string.work_record, R.string.visa_passport, R.string.bank_credit, R.string.imss, R.string.infonavit, R.string.kindergarten};
        String[] options = {AppConstants.OPTION_WORK_RECORD, AppConstants.OPTION_VISA_PASSPORT, AppConstants.OPTION_BANK_CREDIT, AppConstants.OPTION_IMSS, AppConstants.OPTION_INFONAVIT, AppConstants.OPTION_KINDERGARTEN};
        menuItems = buildOptionSubSection(OPTION_LETTERS, options, idRes, context);

        return menuItems;
    }

    public static List<HomeMenuItem> getRolUserMenu(Context context) {
        List<HomeMenuItem> menuItems = new ArrayList<>();
        List<MainSection> sectionList = getMainSection();
        if (MenuUtilities.isFilial(context)) {
            if (findItem(sectionList, getSectionsMap().get(AppConstants.OPTION_EXPENSES_COL)))
                menuItems.add(new HomeMenuItem(context.getString(R.string.colaborator_vacation), AppConstants.OPTION_MENU_COLABORATOR));
            if (findItem(sectionList, getSectionsMap().get(AppConstants.OPTION_EXPENSES_GTE)))
                menuItems.add(new HomeMenuItem(context.getString(R.string.manager_vacation), AppConstants.OPTION_MENU_GTE));
        } else {
            menuItems.add(new HomeMenuItem(context.getString(R.string.manager_vacation), AppConstants.OPTION_MENU_GTE));
            menuItems.add(new HomeMenuItem(context.getString(R.string.colaborator_vacation), AppConstants.OPTION_MENU_COLABORATOR));
        }
        return menuItems;
    }

    public static List<HomeMenuItem> getRolUserMenuHoliday(Context context) {
        List<HomeMenuItem> menuItems = new ArrayList<>();
        List<MainSection> sectionList = getMainSection();
        if (MenuUtilities.isFilial(context)) {
            if (findItem(sectionList, getSectionsMap().get(AppConstants.OPTION_HOLIDAYS_COL)))
                menuItems.add(new HomeMenuItem(context.getString(R.string.colaborator_vacation), AppConstants.OPTION_MENU_COLABORATOR));
            if (findItem(sectionList, getSectionsMap().get(AppConstants.OPTION_HOLIDAYS_GTE)))
                menuItems.add(new HomeMenuItem(context.getString(R.string.manager_vacation), AppConstants.OPTION_MENU_GTE));
        } else {
            menuItems.add(new HomeMenuItem(context.getString(R.string.manager_vacation), AppConstants.OPTION_MENU_GTE));
            menuItems.add(new HomeMenuItem(context.getString(R.string.colaborator_vacation), AppConstants.OPTION_MENU_COLABORATOR));
        }
        return menuItems;
    }

    public static List<HomeMenuItem> getExpensesManagerMenu(Context context) {
        List<HomeMenuItem> menuItems = new ArrayList<>();
        int[] idRes = {R.string.authorize_request_icon, R.string.active_controls};
        String[] opt = {AppConstants.OPTION_AUTHORIZE_REQUESTS, AppConstants.OPTION_CONTROLS_LIQ};
        buildOptionSubSection(AppConstants.OPTION_EXPENSES_GTE, opt, idRes, context);

        return menuItems;
    }

    public static List<HomeMenuItem> getHolidaysManagerMenu(Context context) {
        List<HomeMenuItem> menuItems;
        int[] idRes = {R.string.btn_request_holidays, R.string.btn_calendar_holidays, R.string.btn_aditional_days};
        String[] options = {AppConstants.OPTION_HOLIDAY_REQUESTS, AppConstants.OPTION_CALENDAR_GRAL, AppConstants.OPTION_ADITIONAL_DAYS};
        menuItems = buildOptionSubSection(OPTION_HOLIDAYS_GTE, options, idRes, context);

        return menuItems;
    }

    public static List<HomeMenuItem> getSavingFoundMenu(Context context) {
        List<HomeMenuItem> menuItems;
        int[] idRes = {R.string.remove, R.string.add, R.string.additional_saving_double_line};//string para movimientos
        String[] options = {AppConstants.OPTION_REMOVE, AppConstants.OPTION_PAY, AppConstants.OPTION_ADITIONAL_SAVED};//AppConstants.OPTION_MYS_MOVEMENTS
        menuItems = buildOptionSubSection(OPTION_SAVING_FUND, options, idRes, context);
        return menuItems;
    }

    private static List<HomeMenuItem> buildOptionSubSection(String mainSection, String[] options, int[] idRes, Context context) {
        List<HomeMenuItem> menuItems = new ArrayList<>();
        List<SectionDb> subSections = getSubSection();

        if (MenuUtilities.isFilial(context)) {
            int idParent = getSectionsMap().get(mainSection);
            for (int i = 0; i < options.length; i++) {
                String opt = options[i];
                if (findSubItem(subSections, idParent, getSubSectionsMap().get(opt)))
                    menuItems.add(new HomeMenuItem(context.getString(idRes[i]), opt));
            }
        } else {
            for (int i = 0; i < options.length; i++) {
                menuItems.add(new HomeMenuItem(context.getString(idRes[i]), options[i]));
            }
        }
        return menuItems;
    }

    public static boolean findItem(List<MainSection> list, int id) {
        for (MainSection m : list) {
            if (m.getId() == id)
                return true;
        }
        return false;
    }

    public static boolean findSubItem(List<SectionDb> list, int idParent, int id) {
        for (SectionDb m : list) {
            if (m.getId() == id && m.getParentId() == idParent)
                return true;
        }
        return false;
    }

    public static boolean isFilial(Context context) {
        return AppUtilities.getBooleanFromSharedPreferences(context, AppConstants.SHARED_PREFERENCES_FILIAL);
    }
}