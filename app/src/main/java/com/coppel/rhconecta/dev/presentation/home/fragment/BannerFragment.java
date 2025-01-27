package com.coppel.rhconecta.dev.presentation.home.fragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.domain.home.entity.Banner;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 *
 */
public class BannerFragment extends Fragment {

    /*  */
    public Banner banner;
    private OnBannerClickListener onBannerClickListener;


    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_banner, container, false);
    }

    /**
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    /**
     *
     */
    private void initViews2() {
        // Init views
        assert getView() != null;
        View flBanner = getView().findViewById(R.id.flBanner);
        ImageView ivBannerPreview = getView().findViewById(R.id.ivBannerPreview);
        if (banner != null) {
            // Set image
            Glide.with(this)
                    .load(banner.getImage())
                    .error(R.drawable.ic_image_grey_300_48dp)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivBannerPreview);
            // Set on click listener
            flBanner.setOnClickListener(v -> onBannerClickListener.onClick(banner));
        }
    }

    private void initViews() {
        // Init views
        assert getView() != null;
        View flBanner = getView().findViewById(R.id.flBanner);
        ImageView ivBannerPreview = getView().findViewById(R.id.ivBannerPreview);
        if (banner != null) {
            if (banner.getImage().startsWith("https://")) {
                Log.i("prueba", "empieza con https");
                // Set image
                Glide.with(this)
                        .load(banner.getImage())
                        .error(R.drawable.ic_image_grey_300_48dp)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(ivBannerPreview);
                // Set on click listener
                flBanner.setOnClickListener(v -> onBannerClickListener.onClick(banner));
            } else {
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference imageRef = storage.getReference().child(banner.getImage());
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
                                            .into(ivBannerPreview);
                                    // Set on click listener
                                    flBanner.setOnClickListener(v -> onBannerClickListener.onClick(banner));
                                }).addOnFailureListener(exception -> {
                                    // Handle any errors
                                    Log.i("FirebaseStorage", "Error downloading image", exception);
                                });
                            } else {
                                // Manejar errores
                                Log.e("TAG", "Error al crear usuario anónimo", task.getException());
                            }
                        });

            }
        }
    }

    /**
     * @param banner
     * @return
     */
    public static BannerFragment createInstance(Banner banner, OnBannerClickListener onClickListener) {
        BannerFragment fragment = new BannerFragment();
        fragment.banner = banner;
        fragment.onBannerClickListener = onClickListener;
        return fragment;
    }

    /**
     *
     */
    public interface OnBannerClickListener {

        /**
         * @param banner
         */
        void onClick(Banner banner);

    }

}
