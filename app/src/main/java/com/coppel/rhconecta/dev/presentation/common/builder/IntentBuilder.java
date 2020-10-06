package com.coppel.rhconecta.dev.presentation.common.builder;

import android.content.Intent;

import com.coppel.rhconecta.dev.presentation.common.extension.IntentExtension;

import java.io.Serializable;

/**
 *
 */
public class IntentBuilder {

    /* */
   private Intent intent;

    /**
     *
     */
    public IntentBuilder(Intent intent) {
        this.intent = intent;
    }

    /**
     *
     */
    public IntentBuilder putStringExtra(String key, String value){
        IntentExtension.putStringExtra(intent, key, value);
        return this;
    }

    /**
     *
     */
    public IntentBuilder putIntExtra(String key, int value){
        IntentExtension.putIntExtra(intent, key, value);
        return this;
    }

    /**
     *
     */
    public IntentBuilder putBooleanExtra(String key, boolean value){
        IntentExtension.putBooleanExtra(intent, key, value);
        return this;
    }

    /**
     *
     */
    public IntentBuilder putSerializableExtra(String key, Serializable value){
        IntentExtension.putSerializableExtra(intent, key, value);
        return this;
    }

    /**
     *
     */
    public Intent build(){
        return intent;
    }

}
