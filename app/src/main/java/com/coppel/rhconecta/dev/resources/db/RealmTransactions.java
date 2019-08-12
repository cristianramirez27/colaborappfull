package com.prasyde.impulsorintelectual.Resources.Db.Configuration;

import com.prasyde.impulsorintelectual.ImpulsorIntelectualApp;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by Fausto on 25/08/2016.
 *
 * @author fausto@kokonutstudio
 * @version 1.0
 *
 * Clase para realizar las operaciones CRUD en Realm
 *
 */
public class RealmTransactions {


    /**
     * Metodo para realizar la inserción en la base de datos
     *
     * @param  passedObject objeto extendido de RealmObject
     */
    public static <T extends RealmObject> void insertInto(final T passedObject){
        Realm realm = RealmSingleton.getRealmIntance(ImpulsorIntelectualApp.getContext());
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                T itemToWrite = realm.copyToRealmOrUpdate(passedObject);
            }
        });

    }


    /**
     * Metodo para realizar la eliminaci+on en la base de datos
     *
     * @param  clazz clase extendida de RealmObject
     * @param  primarykeyName nombre del campo que es primary key del objeto
     * @param  primaryKeyValue Valor del campo primary key del objeto
     */
    public static <T extends RealmObject> void deleteObject(Class<T> clazz,String primarykeyName ,int primaryKeyValue){
        RealmObject resultsDb = searchByPrimaryKey(clazz,primarykeyName,primaryKeyValue);

        if(resultsDb!= null){
            Realm realm = RealmSingleton.getRealmIntance(ImpulsorIntelectualApp.getContext());
            realm.beginTransaction();
            resultsDb.deleteFromRealm();
            realm.commitTransaction();
        }
    }

    public static <T extends RealmObject> void deleteObject(Class<T> clazz,String key ,String param){


        RealmObject resultsDb = searchByParamKey(clazz,key,param);

        if(resultsDb!= null){
            Realm realm = RealmSingleton.getRealmIntance(ImpulsorIntelectualApp.getContext());
            realm.beginTransaction();
            resultsDb.deleteFromRealm();
            realm.commitTransaction();
        }


    }

    public static <T extends RealmObject>   void deleteAllObjectByClass(Class<T> clazz){
        Realm realm = RealmSingleton.getRealmIntance(ImpulsorIntelectualApp.getContext());
        realm.beginTransaction();
        realm.delete(clazz);
        realm.commitTransaction();
    }


    public static  void deleteAllObject(){
            Realm realm = RealmSingleton.getRealmIntance(ImpulsorIntelectualApp.getContext());
            realm.beginTransaction();
            realm.deleteAll();
            realm.commitTransaction();
    }

    /**
     * Metodo para realizar la busqueda en la base de datos a partir del id
     *
     * @param  clazz clase extendida de RealmObject
     * @param  primarykeyName nombre del campo que es primary key del objeto
     * @param  primaryKeyValue Valor del campo primary key del objeto
     */

    public static <T extends RealmObject>  RealmObject  searchByPrimaryKey(Class<T> clazz,String primarykeyName ,int primaryKeyValue){

        Realm realm = RealmSingleton.getRealmIntance(ImpulsorIntelectualApp.getContext());
        realm.beginTransaction();
        T resultsDb = realm.where(clazz).equalTo(primarykeyName, primaryKeyValue).findFirst();
        realm.commitTransaction();

        return resultsDb;
    }


    public static <T extends RealmObject>  RealmObject  searchByParamKey(Class<T> clazz,String paramName ,String value){

        Realm realm = RealmSingleton.getRealmIntance(ImpulsorIntelectualApp.getContext());
        realm.beginTransaction();
        T resultsDb = realm.where(clazz).equalTo(paramName, value).findFirst();
        realm.commitTransaction();

        return resultsDb;
    }


    public static <T extends RealmObject> List<T>  searchByParam(Class<T> clazz,String param ,String value){

        Realm realm = RealmSingleton.getRealmIntance(ImpulsorIntelectualApp.getContext());
        realm.beginTransaction();
        List<T>  resultsDb = null;
        if(!value.isEmpty())
            resultsDb = realm.where(clazz).equalTo(param, value).findAll();
        else
            resultsDb = realm.where(clazz).findAll();

        realm.commitTransaction();

        return resultsDb;

    }


    public static <T extends RealmObject> List<T>  searchByParam(Class<T> clazz,String param ,int value){

        Realm realm = RealmSingleton.getRealmIntance(ImpulsorIntelectualApp.getContext());
        realm.beginTransaction();
        List<T>  resultsDb = null;
        if(value != 0)
            resultsDb = realm.where(clazz).equalTo(param, value).findAll();
        else
            resultsDb = realm.where(clazz).findAll();

        realm.commitTransaction();

        return resultsDb;

    }

    public static <T extends RealmObject> List<T>  searchByClass(Class<T> clazz){

        Realm realm = RealmSingleton.getRealmIntance(ImpulsorIntelectualApp.getContext());
        realm.beginTransaction();
        List<T>  resultsDb  = realm.where(clazz).findAll();
        realm.commitTransaction();
        return resultsDb;

    }



    public static <T extends RealmObject>  RealmObject  searchByBooleanParam(Class<T> clazz,String column ,boolean param){

        Realm realm = RealmSingleton.getRealmIntance(ImpulsorIntelectualApp.getContext());
        realm.beginTransaction();
        T resultsDb = realm.where(clazz).equalTo(column, param).findFirst();
        realm.commitTransaction();

        return resultsDb;
    }


    public static <T extends RealmObject> List<T> searchAllByBooleanParam(Class<T> clazz, String column , boolean param){

        Realm realm = RealmSingleton.getRealmIntance(ImpulsorIntelectualApp.getContext());
        realm.beginTransaction();
        List<T> resultsDb = realm.where(clazz).equalTo(column, param).findAll();
        realm.commitTransaction();

        return resultsDb;
    }

}
