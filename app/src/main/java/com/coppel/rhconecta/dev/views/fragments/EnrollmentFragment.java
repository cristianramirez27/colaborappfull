package com.coppel.rhconecta.dev.views.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.views.activities.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnrollmentFragment extends Fragment implements View.OnClickListener{

    public static final String TAG = EnrollmentFragment.class.getSimpleName();
    private LoginActivity parent;
    @BindView(R.id.imgvBack)
    ImageView imgvBack;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_enrollment, container, false);
        ButterKnife.bind(this, view);
        parent = ((LoginActivity) getActivity());
        imgvBack.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgvBack:
                parent.onBackPressed();
                break;
        }
    }
}
