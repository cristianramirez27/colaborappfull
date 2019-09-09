package com.coppel.rhconecta.dev.business.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Zita on 5/16/17.
 */
public class NavigationUtil {


    public static<T> void openActivity(Context context, Class<T> classTarget){

        Intent intent = new Intent(context, classTarget);
        context.startActivity(intent);
    }

    public static<T> void openActivityTransition(Activity context, Class<T> classTarget){

        Intent intent = new Intent(context, classTarget);
        context.startActivity(intent);
    }

    public static<T> void openActivityWithIntent(Context context, Intent intent){
        context.startActivity(intent);
    }

    public static<T> void openActivityClearTask(Context context, Class<T> classTarget){

        Intent intent = new Intent(context, classTarget);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public static<T> void openActivityClearTask(Context context, Class<T> classTarget, String key, String param){

        Intent intent = new Intent(context, classTarget);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(key,param);
        context.startActivity(intent);
    }



    public static<T> void openActivityWithStringParameter(Context context, Class<T> classTarget, HashMap<String,String> parameter){

        Intent intent = new Intent(context, classTarget);
        for(String key : parameter.keySet())
            intent.putExtra(key,parameter.get(key));

        context.startActivity(intent);

    }



    public static<T> void openActivityWithStringParameterForResult(Activity context, Class<T> classTarget, HashMap<String,String> parameter, int request){

        Intent intent = new Intent(context, classTarget);
        for(String key : parameter.keySet())
            intent.putExtra(key,parameter.get(key));

        context.startActivityForResult(intent,request);

    }

    public static<T> void openActivityWithStringParam(Activity context, Class<T> classTarget, String key, String param){

        Intent intent = new Intent(context, classTarget);
        intent.putExtra(key,param);
        context.startActivity(intent);

    }

    public static<T> void openActivityToAuthorize(Activity context, Class<T> classTarget, String key, String param){

        Intent intent = new Intent(context, classTarget);
        intent.putExtra(key,param);
        intent.putExtra("AUTHORIZE",true);
        context.startActivity(intent);

    }

    public static<T> void openActivityWithStringParamTransition(Activity context, Class<T> classTarget, String key, String param){

        Intent intent = new Intent(context, classTarget);
        intent.putExtra(key,param);
        context.startActivity(intent);
    }


    public static<T> void openActivityWithStringParam(Context context, Class<T> classTarget, String key, String param){

        Intent intent = new Intent(context, classTarget);
        intent.putExtra(key,param);
        context.startActivity(intent);

    }

    public static<T> void openActivityWithStringParamForResult(Activity context, Class<T> classTarget, String key, String param, int request){

        Intent intent = new Intent(context, classTarget);
        intent.putExtra(key,param);
        context.startActivityForResult(intent,request);

    }

    public static<T> void openActivityWithSerializableParamForResult(Activity context, Class<T> classTarget, String keyParameter, Serializable objSerializable, HashMap<String,String> parameter, int request){
        Intent intent = new Intent(context, classTarget);
        intent.putExtra(keyParameter,objSerializable);
        if(parameter!=null){
            for(String key : parameter.keySet())
                intent.putExtra(key,parameter.get(key));
        }

        context.startActivityForResult(intent,request);
    }

    public static<T> void openActivityWithSerializableParameter(Activity activity, Class<T> classTarget, String keyParameter, Serializable objSerializable, int requestCode){
        Intent intent = new Intent(activity, classTarget);
        intent.putExtra(keyParameter,objSerializable);
        activity.startActivityForResult(intent, requestCode);
    }

    public static<T> void openActivityWithIdParameter(Context context, Class<T> classTarget, String keyIdParameter , int idParameter){

        Intent intent = new Intent(context, classTarget);
        intent.putExtra(keyIdParameter,idParameter);
        context.startActivity(intent);
    }


    public static<T> void openActivityWithIdParameterForResult(Activity activity, Class<T> classTarget, String keyIdParameter , int idParameter, int requestCode){

        Intent intent = new Intent(activity, classTarget);
        intent.putExtra(keyIdParameter,idParameter);
        activity.startActivityForResult(intent, requestCode);
    }

    public static<T> void openActivityWithStringParameterNewTask(Context context, Class<T> classTarget, HashMap<String,String> parameter){


        Intent intent = new Intent(context, classTarget);
        for(String key : parameter.keySet())
            intent.putExtra(key,parameter.get(key));

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }


    public static<T> void openActivityForResult(Activity activity, Class<T> classTarget, int requestCode){


        Intent intent = new Intent(activity, classTarget);
        activity.startActivityForResult(intent, requestCode);
    }


    public static<T> void openActivityForResult(Activity activity, Intent intent, int requestCode){
        activity.startActivityForResult(intent, requestCode);
    }

    public static<T> void openActivityForResult(android.support.v4.app.Fragment f, Activity activity, Class<T> classTarget, int requestCode){


        Intent intent = new Intent(activity, classTarget);
        f.startActivityForResult(intent, requestCode);
    }

    public static<T> void openActivityWithSerializable(Activity activity, Class<T> classTarget, String key, Serializable objectSerializable){

        Intent intent = new Intent(activity, classTarget);
        intent.putExtra(key,objectSerializable);
        activity.startActivity(intent);
    }

    public static<T> void openActivityWithSerializable(Context context, Class<T> classTarget, String key, Serializable objectSerializable){

        Intent intent = new Intent(context, classTarget);
        intent.putExtra(key,objectSerializable);
        context.startActivity(intent);
    }

    public static<T> void openActivityParamsSerializable(Context context, Class<T> classTarget,
                                                         String keySerializable, Serializable objectSerializable,
                                                         String key,String section){

        Intent intent = new Intent(context, classTarget);
        intent.putExtra(keySerializable,objectSerializable);
        intent.putExtra(key,section);
        context.startActivity(intent);
    }

    public static<T> void openActivityParamsSerializableRequestCode(Activity context, Class<T> classTarget,
                                                         String keySerializable, Serializable objectSerializable,
                                                         String key,String section,int requestCode){

        Intent intent = new Intent(context, classTarget);
        intent.putExtra(keySerializable,objectSerializable);
        intent.putExtra(key,section);
        context.startActivityForResult(intent,requestCode);
    }

    public static<T> Intent getIntent(Context context, Class<T> classTarget, int... flags){

        Intent intent = new Intent(context, classTarget);
        if(flags != null && flags.length > 0){
            for(int flag : flags){
                intent.addFlags(flag);
            }
        }

        return intent;

    }


    public static<T> Intent getIntent(Context context, Class<T> classTarget, String key, String param, int... flags){

        Intent intent = new Intent(context, classTarget);
        if(flags != null && flags.length > 0){
            for(int flag : flags){
                intent.addFlags(flag);
            }
        }

        intent.putExtra(key,param);

        return intent;

    }


    public static<T> Intent getIntent(Context context, Class<T> classTarget, String key, Serializable objectSerializable, int... flags){

        Intent intent = new Intent(context, classTarget);
        if(key != null && objectSerializable != null)
            intent.putExtra(key,objectSerializable);
        if(flags != null && flags.length > 0){
            for(int flag : flags){
                intent.addFlags(flag);
            }
        }

        return intent;

    }

}