package com.coppel.rhconecta.dev.presentation.release_detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.Enums.AccessOption;
import com.coppel.rhconecta.dev.di.release.DaggerReleaseComponent;
import com.coppel.rhconecta.dev.domain.release.entity.Release;
import com.coppel.rhconecta.dev.presentation.common.extension.IntentExtension;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

/**
 *
 */
public class ReleaseDetailActivity extends AppCompatActivity {

    /* */
    @Inject
    public ReleaseDetailViewModel releaseDetailViewModel;
    public static String RELEASE_ID = "RELEASE_ID";
    public static String ACCESS_OPTION = "ACCESS_OPTION";
    private int releaseId;
    private AccessOption accessOption;
    /* VIEWS */
    private ImageView ivHeader;
    private TextView tvHeader;
    private ImageView ivImage;
    private TextView tvTitle;
    private TextView tvContent;
    private ProgressBar loader;


    /**
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_detail);
        DaggerReleaseComponent.create().inject(this);
        initValues();
        initViews();
        observeViewModel();
        execute();
    }

    /**
     *
     */
    private void initValues() {
        releaseId = IntentExtension.getIntExtra(getIntent(), RELEASE_ID);
        accessOption = (AccessOption) IntentExtension.getSerializableExtra(getIntent(), ACCESS_OPTION);
        Log.i("prueba","accessOption: " + accessOption);
    }

    /**
     *
     */
    private void initViews() {
        initToolbar();
        loader = (ProgressBar) findViewById(R.id.pbLoader);
        ivHeader = (ImageView) findViewById(R.id.ivHeader);
        tvHeader = (TextView) findViewById(R.id.tvHeader);
        ivImage = (ImageView) findViewById(R.id.ivImage);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvContent = (TextView) findViewById(R.id.tvContent);

    }

    /**
     *
     */
    private void observeViewModel() {
        releaseDetailViewModel.getLoadReleaseStatus().observe(this, this::getLoadReleaseObserver);
    }


    /**
     *
     */
    private void getLoadReleaseObserver(ProcessStatus processStatus) {
        loader.setVisibility(View.GONE);
        switch (processStatus) {
            case LOADING:
                loader.setVisibility(View.VISIBLE);
                break;
            case FAILURE:
                Toast.makeText(this, R.string.default_server_error, Toast.LENGTH_SHORT).show();
                break;
            case COMPLETED:
                setReleaseInformation(releaseDetailViewModel.getRelease());
                break;
        }
    }

    /**
     *
     */
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.release_detail_activity_title);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    /**
     *
     */
    private void setReleaseInformation(Release release) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imageRef = storage.getReference().child(release.getImage());
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.signInAnonymously()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // El usuario se ha autenticado correctamente de forma anónima
                        FirebaseUser user = mAuth.getCurrentUser();
                        String uid = user.getUid();
                        Log.d("TAG", "Usuario anónimo creado con UID: " + uid);
                        // Set image
                        imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            Glide.with(this)
                                    .load(uri)
                                    .error(R.drawable.ic_image_grey_300_48dp)
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(ivImage);
                        }).addOnFailureListener(exception -> {
                            // Handle any errors
                            Log.i("FirebaseStorage", "Error downloading image", exception);
                        });
                    } else {
                        // Manejar errores
                        Log.e("TAG", "Error al crear usuario anónimo", task.getException());
                    }
                });


        /*Glide.with(this)
                .load(release.getImage())
                .error(R.drawable.ic_image_grey_300_48dp)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivImage);*/

        tvTitle.setText(release.getTitle());

        if (release.getUrl_pdf().isEmpty() || release.getUrl_pdf() == null || release.getUrl_pdf().equals("n/a")) {
            String body = release.getContent().replace("text-align: right", "text-align: end");
            Spanned spanned = Html.fromHtml(body);
            tvContent.setText(spanned);
        } else {
            StorageReference storageRef = storage.getReference().child(release.getUrl_pdf());
            // Obtén la URL de descarga
            storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                //atomicBodyReplace.set(atomicBodyReplace.get().replaceAll("#PDF", String.valueOf(uri)));
                String updatedBodyReplace = release.getContent().replace("#PDF", uri.toString());
                updatedBodyReplace = updatedBodyReplace.replace("text-align: right", "text-align: end");
                Spanned spanned = Html.fromHtml(updatedBodyReplace);
                tvContent.setText(spanned);
            }).addOnFailureListener(f -> {
                // Maneja el error
                Log.e("FirebaseStorage", "Error al obtener la URL: ", f);
            });
        }

        //tvContent.setText(spannableFromHtml(spanned));
        tvContent.setMovementMethod(LinkMovementMethod.getInstance());
        tvContent.setLinksClickable(true);
    }

    // Método para crear un Spannable con ClickableSpan en los enlaces
    private Spannable spannableFromHtml(Spanned spanned) {
        Spannable spannable = new SpannableString(spanned);
        URLSpan[] urls = spannable.getSpans(0, spanned.length(), URLSpan.class);

        for (URLSpan urlSpan : urls) {
            int start = spannable.getSpanStart(urlSpan);
            int end = spannable.getSpanEnd(urlSpan);
            spannable.removeSpan(urlSpan);

            // Añade un ClickableSpan personalizado
            spannable.setSpan(new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    String url = urlSpan.getURL();
                    //if(url.startsWith("http") {

                    //}

                    Toast.makeText(widget.getContext(), "Link clicked: " + url, Toast.LENGTH_SHORT).show();
                    // Aquí puedes agregar cualquier acción adicional que desees
                }

                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(Color.BLUE); // Color del enlace
                    ds.setUnderlineText(true); // Subrayado del enlace
                }
            }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return spannable;
    }


    /**
     *
     */
    private void execute() {
        releaseDetailViewModel.loadRelease(releaseId, accessOption);
    }

    /**
     *
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}

