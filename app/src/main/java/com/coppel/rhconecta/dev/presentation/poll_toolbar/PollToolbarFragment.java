package com.coppel.rhconecta.dev.presentation.poll_toolbar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.coppel.rhconecta.dev.R;

/**
 *
 *
 */
public class PollToolbarFragment extends Fragment {

    /* */
    private Toolbar toolbar;
    private ImageView ivBack;

    /**
     *
     *
     */
    public PollToolbarFragment() {
        // Required empty public constructor
    }

    /**
     *
     *
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_poll_toolbar, container, false);
    }

    /**
     *
     *
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ivBack = (ImageView) view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this::onBackPressed);

    }

    /**
     *
     *
     */
    public void setTitle(int title){
        toolbar.setTitle(title);
    }

    /**
     *
     *
     */
    public void setTitle(String title){
        toolbar.setTitle(title);
    }

    /**
     *
     *
     */
    private void onBackPressed(View v){
        if(getActivity() != null)
            getActivity().onBackPressed();
    }

}
