package com.coppel.rhconecta.dev.business.utils;

public class ServicesConstants {

    public static final String REGEX_EMAIL = "[a-zA-Z0-9._+-]+@[a-zA-Z]+\\.+[a-z]+";
    public static final String REGEX_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";

    /**TESTING ENVIROMENT*/
    public static final String URL_BASE = "http://qa-apipos.coppel.com:9000/";
    //public static final String GET_LOGIN = "appcoppel/api/v1/inicioSesion";
      public static final String GET_LOGIN = "rhconecta/api/v1/inicioSesion";
    public static final String GET_PROFILE = "appcoppel/api/v1/perfilColaboradorConsulta";
    public static final String GET_LETTERS_VALIDATE_SIGNATURE = "appcoppel/api/v1/cartasValidarFirma";
    public static final String GET_CONFIG = "appcoppel/api/v1/cartasConfig";
    public static final String GET_LETTER_PREVIEW= "appcoppel/api/v1/cartasVistaPrevia";
    public static final String GET_LETTER_GENERATE= "appcoppel/api/v1/cartasGenerar";
    public static final String GET_VOUCHER = "appcoppel/api/v1/comprobantesNomina";
    public static final String GET_LOANSAVINGFUND = "appcoppel/api/v1/fondoAhorroConsulta";
    public static final String GET_RECOVERY_PASSWORD = "rhconecta/api/v2/recuperacontra";

    /**PROD ENVIROMENT*/
    /*public static final String URL_BASE = "https://apipos.coppel.cloud/";
    public static final String GET_LOGIN = "appcoppel/api/v1/inicioSesion";
    public static final String GET_PROFILE = "rhconecta/api/v2/perfilColaboradorConsulta";
    public static final String GET_LETTERS_VALIDATE_SIGNATURE = "rhconecta/api/v2/cartasValidarFirma";
    public static final String GET_CONFIG = "rhconecta/api/v2/cartasConfig";
    public static final String GET_VOUCHER = "rhconecta/api/v2/comprobantesNomina";
    public static final String GET_LOANSAVINGFUND = "rhconecta/api/v2/fondoAhorroConsulta";
    public static final String GET_RECOVERY_PASSWORD = "rhconecta/api/v2/recuperacontra";*/

    public static final String SUCCESS = "SUCCESS";

    public static final int PETITION_PAYROLL_VOUCHER_LIST = 1;
    public static final int CONSTANCE_TYPE_PAYROLL = 1;
    public static final int CONSTANCE_TYPE_SAVING_FUND = 2;
    public static final int CONSTANCE_TYPE_GAS = 3;
    public static final int CONSTANCE_TYPE_PAYROLL_PTU = 4;
    public static final int CONSTANCE_TYPE_ALIMONY = 5;
    public static final int CONSTANCE_TYPE_BONUS = 6;

    public static final int SHIPPING_OPTION_GET = 0;
    public static final int SHIPPING_OPTION_DOWNLOAD_PDF = 1;
    public static final int SHIPPING_OPTION_SEND_EMAIL = 2;
}
