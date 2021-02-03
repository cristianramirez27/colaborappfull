package com.coppel.rhconecta.dev.presentation.common.extension;

import android.content.Intent;

import com.coppel.rhconecta.dev.system.encryption.EncryptionAES;

import java.io.Serializable;

/**
 *
 */
public class IntentExtension {

    /**
     *
     */
    public static void putStringExtra(Intent intent, String key, String value){
        String stringValue = EncryptionAES.encryptString(value);
        intent.putExtra(key, stringValue);
    }

    /**
     *
     */
    public static String getStringExtra(Intent intent, String key){
        String encryptedValue = intent.getStringExtra(key);
        return EncryptionAES.decryptString(encryptedValue);
    }

    /**
     *
     */
    public static void putIntExtra(Intent intent, String key, int value){
        String encryptedValue = EncryptionAES.encryptInteger(value);
        intent.putExtra(key, encryptedValue);
    }

    /**
     *
     */
    public static int getIntExtra(Intent intent, String key){
        String encryptedValue = intent.getStringExtra(key);
        return EncryptionAES.decryptInteger(encryptedValue);
    }

    /**
     *
     */
    public static void putBooleanExtra(Intent intent, String key, boolean value){
        String encryptedValue = EncryptionAES.encryptBoolean(value);
        intent.putExtra(key, encryptedValue);
    }

    /**
     *
     */
    public static boolean getBooleanExtra(Intent intent, String key){
        String encryptedValue = intent.getStringExtra(key);
        return EncryptionAES.decryptBoolean(encryptedValue);
    }

    /**
     *
     */
    public static void putSerializableExtra(Intent intent, String key, Serializable value){
        String stringValue = EncryptionAES.encryptSerializable(value);
        intent.putExtra(key, stringValue);
    }

    /**
     *
     */
    public static Serializable getSerializableExtra(Intent intent, String key){
        String encryptedValue = intent.getStringExtra(key);
        return EncryptionAES.decryptSerializable(encryptedValue);
    }

}
