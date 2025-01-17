package com.coppel.rhconecta.dev.visionarios;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

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
        FirebaseMessaging.getInstance().subscribeToTopic("android")
                .addOnCompleteListener(task -> {
                    String msg = "Registrado";
                    if (!task.isSuccessful()) {
                        msg = "Error al registrar";
                    }
                });
    }

}
