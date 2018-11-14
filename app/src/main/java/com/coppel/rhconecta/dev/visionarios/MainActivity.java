package com.coppel.rhconecta.dev.visionarios;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.visionarios.inicio.views.InicioActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Registrado";
                        if (!task.isSuccessful()) {
                            msg = "Error al registrar";
                        }
                        Log.d(TAG, msg);
                    }
                });

        Intent intent = new Intent(this, InicioActivity.class);
        startActivity(intent);

    }
}
