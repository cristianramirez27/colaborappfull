package com.coppel.rhconecta.dev.presentation.common.extension;

import android.content.SharedPreferences;
import android.util.Base64;

import com.coppel.rhconecta.dev.system.encryption.EncryptionAES;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 *
 */
public class SharedPreferencesExtension {

    /**
     *
     */
    public static void putString(
            SharedPreferences sharedPreferences,
            String key,
            String value
    ){
        String encryptedValue = EncryptionAES.encryptString(value);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, encryptedValue);
        editor.apply();
    }

    /**
     *
     */
    public static String getString(
            SharedPreferences sharedPreferences,
            String key,
            String defaultValue
    ){
        String savedValue = sharedPreferences.getString(key, defaultValue);
        // Se debe agregar una validacion doble ya que puede haber dispositivos que actualmente
        // tengan informacion sin cifrar
        try {
            return EncryptionAES.decryptString(savedValue);
        } catch (Exception exception) {
            // Para que no se pierda dicha informacion se intentara consumir la informacion y
            // transformar de tal modo que se obtenga y cifre en una accion.
            putString(sharedPreferences, key, savedValue);
            return savedValue;
        }
    }

    /**
     *
     */
    public static void putInteger(
            SharedPreferences sharedPreferences,
            String key,
            int value
    ){
        String encryptedSerializable = EncryptionAES.encryptInteger(value);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, encryptedSerializable);
        editor.apply();
    }

    /**
     *
     */
    public static int getInteger(
            SharedPreferences sharedPreferences,
            String key,
            int defaultValue
    ){
        // Se debe agregar una validacion doble ya que puede haber dispositivos que actualmente
        // tengan informacion sin cifrar
        try {
            String encryptedValue = sharedPreferences.getString(key, null);
            if(encryptedValue == null)
                return defaultValue;
            return EncryptionAES.decryptInteger(encryptedValue);
        } catch (Exception exception) {
            // Para que no se pierda dicha informacion se intentara consumir la informacion y
            // transformar de tal modo que se obtenga y cifre en una accion.
            try {
                int notEncryptedValue = sharedPreferences.getInt(key, defaultValue);
                putInteger(sharedPreferences, key, notEncryptedValue);
                return notEncryptedValue;
            } catch (Exception ignore) { /* PASS */ }
            // Si no es posible obtener el valor de forma String o Integer, se retorna el valor
            // por omision.
            return defaultValue;
        }
    }

    /**
     *
     */
    public static void putBoolean(
            SharedPreferences sharedPreferences,
            String key,
            boolean value
    ){
        String encryptedSerializable = EncryptionAES.encryptBoolean(value);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, encryptedSerializable);
        editor.apply();
    }

    /**
     *
     */
    public static boolean getBoolean(
            SharedPreferences sharedPreferences,
            String key,
            boolean defaultValue
    ){
        // Se debe agregar una validacion doble ya que puede haber dispositivos que actualmente
        // tengan informacion sin cifrar
        try {
            String encryptedValue = sharedPreferences.getString(key, null);
            if(encryptedValue == null)
                return defaultValue;
            return EncryptionAES.decryptBoolean(encryptedValue);
        } catch (Exception exception) {
            // Para que no se pierda dicha informacion se intentara consumir la informacion y
            // transformar de tal modo que se obtenga y cifre en una accion.
            try {
                boolean notEncryptedValue = sharedPreferences.getBoolean(key, defaultValue);
                putBoolean(sharedPreferences, key, notEncryptedValue);
                return notEncryptedValue;
            } catch (Exception ignore) { /* PASS */ }
            // Si no es posible obtener el valor de forma String o Boolean, se retorna el valor
            // por omision.
            return defaultValue;
        }
    }

    /**
     *
     */
    public static void putSerializable(
            SharedPreferences sharedPreferences,
            String key,
            Serializable value
    ){
        String encryptedSerializable = EncryptionAES.encryptSerializable(value);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, encryptedSerializable);
        editor.apply();
    }

    /**
     *
     */
    public static Serializable getSerializable(
            SharedPreferences sharedPreferences,
            String key
    ){
        // Se debe agregar una validacion doble ya que puede haber dispositivos que actualmente
        // tengan informacion sin cifrar
        try {
            String encryptedValue = sharedPreferences.getString(key, null);
            if(encryptedValue == null)
                return null;
            return EncryptionAES.decryptSerializable(encryptedValue);
        } catch (Exception exception) {
            // Para que no se pierda dicha informacion se intentara consumir la informacion y
            // transformar de tal modo que se obtenga y cifre en una accion.
            try {
                String notEncryptedValue = sharedPreferences.getString(key, null);
                try {
                    byte[] data = Base64.decode(notEncryptedValue, Base64.DEFAULT);
                    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
                    Object o = ois.readObject();
                    ois.close();
                    Serializable s = (Serializable) o;
                    putSerializable(sharedPreferences, key, s);
                    return s;
                } catch (Exception ignore) { /* PASS */ }
                // Si el valor recuperado no es Serializable retorna null
                return null;
            } catch (Exception ignore) { /* PASS */ }
            // Si no es posible obtener el valor de forma String o Boolean, se retorna el valor
            // por omision.
            return null;
        }
    }

}
