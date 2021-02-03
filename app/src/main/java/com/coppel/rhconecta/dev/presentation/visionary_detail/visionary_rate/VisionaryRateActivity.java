package com.coppel.rhconecta.dev.presentation.visionary_detail.visionary_rate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.databinding.ActivityVisionaryRateBinding;
import com.coppel.rhconecta.dev.di.visionary.DaggerVisionaryComponent;
import com.coppel.rhconecta.dev.di.visionary.DaggerVisionaryRateComponent;
import com.coppel.rhconecta.dev.domain.visionary.entity.Visionary;
import com.coppel.rhconecta.dev.domain.visionary.entity.VisionaryRate;
import com.coppel.rhconecta.dev.presentation.common.extension.IntentExtension;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;
import com.coppel.rhconecta.dev.presentation.visionaries.VisionaryType;
import com.coppel.rhconecta.dev.presentation.visionary_detail.VisionaryDetailViewModel;
import com.coppel.rhconecta.dev.presentation.visionary_detail.visionary_rate.adapter.OnOptionClickListener;
import com.coppel.rhconecta.dev.presentation.visionary_detail.visionary_rate.adapter.RateOptionAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/* */
public class VisionaryRateActivity extends AppCompatActivity {

    /* */
    public static final String VISIONARY = "VISIONARY";
    public static final String VISIONARY_TYPE = "VISIONARY_TYPE";
    public static final String VISIONARY_RATE_STATUS = "VISIONARY_RATE_STATUS";

    /* */
    private ActivityVisionaryRateBinding binding;

    /* */
    @Inject
    public VisionaryRateViewModel viewModel;

    /* */
    private ArrayList<VisionaryRate.Option> options;

    /* */
    private VisionaryType visionaryType;
    private Visionary visionary;
    private Visionary.RateStatus requiredRateStatus;

    /* */
    private VisionaryRate.Option selectedOption;

    /**
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVisionaryRateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        DaggerVisionaryRateComponent.create().inject(this);
        initValues();
        setupViews();
        setupOptionsList();
        observeViewModel();
    }

    /**
     *
     */
    private void initValues() {
        options = new ArrayList<>();
        visionary = (Visionary) IntentExtension.getSerializableExtra(getIntent(), VISIONARY);
        visionaryType = (VisionaryType) IntentExtension.getSerializableExtra(getIntent(), VISIONARY_TYPE);
        requiredRateStatus = (Visionary.RateStatus) IntentExtension
                .getSerializableExtra(getIntent(), VISIONARY_RATE_STATUS);
    }

    /**
     *
     */
    private void setupViews() {
        setupToolbar();
        setupOptionsRecyclerView();
        setupActionButton();
    }

    /**
     *
     */
    private void setupToolbar() {
        binding.toolbar.setTitle("");
        setSupportActionBar(binding.toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    /**
     *
     */
    private void setupOptionsRecyclerView() {
        RateOptionAdapter adapter = new RateOptionAdapter(options, this::onOptionClickListener);
        binding.rvOptions.setAdapter(adapter);
        binding.rvOptions.setLayoutManager(new GridLayoutManager(this, 2));
    }

    /**
     *
     */
    private void onOptionClickListener(VisionaryRate.Option option) {
        selectedOption = option;
        for (VisionaryRate.Option o : options)
            o.isSelected = false;
        option.isSelected = true;
        notifyDataSetChanged();
    }

    /**
     *
     */
    private void setupActionButton() {
        if (selectedOption == null) {
            String message = getString(R.string.visionary_rate_action_option_not_selected);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            viewModel.updateVisionaryStatus(visionaryType, visionary, requiredRateStatus, selectedOption);
        }
    }

    /**
     *
     */
    private void setupOptionsList() {
        VisionaryRate visionaryRate = visionary.getVisionaryRate();
        switch (requiredRateStatus) {
            case LIKED:
                setTitle(visionaryRate.getTitleWhenLike());
                setOptions(visionaryRate.getOptionsWhenLike());
                break;
            case DISLIKED:
                setTitle(visionaryRate.getTitleWhenDislike());
                setOptions(visionaryRate.getOptionsWhenDislike());
                break;
            default:
                finish();
                break;
        }
    }

    /**
     *
     */
    private void observeViewModel() {
        viewModel.getUpdateVisionaryStatusProcessStatus()
                .observe(this, createUpdateVisionaryProcessStatusObserver());
    }

    /* */
    private Observer<ProcessStatus> createUpdateVisionaryProcessStatusObserver() {
        return processStatus -> {
            binding.pbLoader.setVisibility(View.GONE);
            switch (processStatus) {
                case LOADING:
                    binding.pbLoader.setVisibility(View.VISIBLE);
                    break;
                case FAILURE:
                    Toast.makeText(this, R.string.default_server_error, Toast.LENGTH_SHORT).show();
                    break;
                case COMPLETED:
                    finish();
                    break;
            }
        };
    }

    /**
     *
     */
    private void setTitle(String title) {
        binding.toolbar.setTitle(title);
    }

    /**
     *
     */
    private void setOptions(List<VisionaryRate.Option> options) {
        this.options.addAll(options);
        notifyDataSetChanged();
    }

    /**
     *
     */
    private void notifyDataSetChanged() {
        RateOptionAdapter rateOptionAdapter = (RateOptionAdapter) binding.rvOptions.getAdapter();
        if (rateOptionAdapter != null)
            rateOptionAdapter.notifyDataSetChanged();
    }

}