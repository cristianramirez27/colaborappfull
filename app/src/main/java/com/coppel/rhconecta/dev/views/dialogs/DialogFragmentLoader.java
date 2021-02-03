package com.coppel.rhconecta.dev.views.dialogs;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.coppel.rhconecta.dev.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogFragmentLoader extends DialogFragment {

    public static final String TAG = DialogFragmentLoader.class.getSimpleName();
    @BindView(R.id.lavLoader)
    LottieAnimationView lavLoader;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.FullScreen);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_loader, container, false);
        ButterKnife.bind(this, view);
        setCancelable(false);
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawableResource(R.color.colorBackgroundDialogs);
        }
        lavLoader.setAnimation(R.raw.loader);
        lavLoader.setRepeatCount(LottieDrawable.INFINITE);
        lavLoader.setRepeatMode(LottieDrawable.INFINITE);
        lavLoader.playAnimation();
        return view;
    }

    public void close() {
       // if (getDialog() != null && isVisible()) {
            dismiss();
       // }
    }
}
