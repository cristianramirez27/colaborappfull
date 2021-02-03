package com.coppel.rhconecta.dev.system.encryption;

import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * The Advanced Encryption Standard (also known as Rijndael) is one of the most popular global
 * encryption standards, that is why its acronym AES keeps coming up in almost every discussion
 * related to cyber security.
 */
public class EncryptionAES {

    /* Algorithm type */
    private static final String algorithm = "AES";
    /* Secure Key to encryption */
    private static final String salt = "C6C64799C264DDF7";
    private static final String key = "AC2FDACBF8F0960DE5B6B62F52D4CF0C";
    private static final String initVector = "990C888FED6BB960";
    /* Cipher transformation */
    private static final String cipherTransformation = "AES/CBC/PKCS5PADDING";

    /**
     * Encrypt the String value received as param.
     * @param value to encrypt.
     * @return encrypted String generated by AES algorithm.
     */
    public static String encryptString(String value) {
        if(value == null) return null;
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algorithm);
            Cipher cipher = Cipher.getInstance(cipherTransformation);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Decrypt the String value received as param.
     * @param encrypted String value to decrypt.
     * @return decrypted String generated by AES algorithm.
     */
    public static String decryptString(String encrypted) {
        if(encrypted == null) return null;
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algorithm);
            Cipher cipher = Cipher.getInstance(cipherTransformation);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(Base64.decode(encrypted, Base64.DEFAULT));
            return new String(original);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Encrypt the int value received as param.
     * @param value to encrypt.
     * @return encrypted String generated by AES algorithm.
     */
    public static String encryptInteger(int value) {
        try {
            String valueAsString = String.valueOf(value);
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algorithm);
            Cipher cipher = Cipher.getInstance(cipherTransformation);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(valueAsString.getBytes());
            return Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Decrypt the String value received as param.
     * @param encrypted String value to decrypt.
     * @return decrypted int generated by AES algorithm.
     */
    public static int decryptInteger(String encrypted){
        String decryptedValue = decryptString(encrypted);
        assert decryptedValue != null;
        return Integer.parseInt(decryptedValue);
    }

    /**
     * Encrypt the long value received as param.
     * @param value to encrypt.
     * @return encrypted String generated by AES algorithm.
     */
    public static String encryptLong(long value) {
        try {
            String valueAsString = String.valueOf(value);
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algorithm);
            Cipher cipher = Cipher.getInstance(cipherTransformation);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(valueAsString.getBytes());
            return Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Decrypt the String value received as param.
     * @param encrypted String value to decrypt.
     * @return decrypted long generated by AES algorithm.
     */
    public static long decryptLong(String encrypted){
        String decryptedValue = decryptString(encrypted);
        assert decryptedValue != null;
        return Long.parseLong(decryptedValue);
    }

    /**
     * Encrypt the boolean value received as param.
     * @param value to encrypt.
     * @return encrypted String generated by AES algorithm.
     */
    public static String encryptBoolean(boolean value) {
        try {
            String valueAsString = String.valueOf(value);
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algorithm);
            Cipher cipher = Cipher.getInstance(cipherTransformation);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(valueAsString.getBytes());
            return Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Decrypt the String value received as param.
     * @param encrypted String value to decrypt.
     * @return decrypted boolean generated by AES algorithm.
     */
    public static boolean decryptBoolean(String encrypted){
        String decryptedValue = decryptString(encrypted);
        assert decryptedValue != null;
        return Boolean.parseBoolean(decryptedValue);
    }

    /**
     * Encrypt the Serializable value received as param.
     * @param value to encrypt.
     * @return encrypted String generated by AES algorithm.
     */
    public static String encryptSerializable(Serializable value){
        if(value == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(value);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String serializableAsString = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        return EncryptionAES.encryptString(serializableAsString);
    }
    /**
     * Decrypt the String value received as param.
     * @param encrypted String value to decrypt.
     * @return decrypted Object generated by AES algorithm.
     */
    public static Serializable decryptSerializable(String encrypted){
        if(encrypted == null) return null;
        Object o = null;
        String decryptedObject = decryptString(encrypted);
        try {
            byte[] data = Base64.decode(decryptedObject, Base64.DEFAULT);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            o = ois.readObject();
            ois.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return (Serializable) o;
    }

}
