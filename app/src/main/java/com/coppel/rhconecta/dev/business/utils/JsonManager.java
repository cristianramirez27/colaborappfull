package com.coppel.rhconecta.dev.business.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by flima on 15/05/17.
 */
public class JsonManager {

    public static JSONObject madeJson(String key, JSONObject json) {
        JSONObject deepJson = new JSONObject();
        try {
            deepJson.put(key, json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return deepJson;
    }


    public static JSONObject madeJsonArr(String key, JSONArray jsonArray) {
        JSONObject deepJson = new JSONObject();
        try {
            deepJson.put(key, jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return deepJson;
    }

    public static JSONObject madeJsonFromObject(Object oRequest) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        String tmp = gson.toJson(oRequest);

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject = new JSONObject(tmp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;

    }

    public static Object jsonToObject(String json, Type type) {
        Object o = null;
        try {
            o = new GsonBuilder()
                    .setDateFormat("dd-MM-yyyy HH:mm:ss")
                    .create().
                            fromJson(json, type);
        } catch (Exception e) {
            o = null;
        }
        return o;
    }

}
