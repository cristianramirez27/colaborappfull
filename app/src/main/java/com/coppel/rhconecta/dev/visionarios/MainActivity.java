package com.coppel.rhconecta.dev.visionarios;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.coppel.rhconecta.dev.R;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(task -> {
                    String msg = "Registrado";
                    if (!task.isSuccessful()) {
                        msg = "Error al registrar";
                    }
                });
    }

}
