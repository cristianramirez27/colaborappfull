package com.coppel.rhconecta.dev.presentation.profile_actions.profile_details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.coppel.rhconecta.dev.business.models.ProfileResponse
import com.coppel.rhconecta.dev.databinding.ActivityProfileDetailsBinding
import com.coppel.rhconecta.dev.presentation.common.extension.IntentExtension
import com.coppel.rhconecta.dev.views.utils.AppConstants

/** */
class ProfileDetailsActivity : AppCompatActivity() {

    /* */
    private val binding: ActivityProfileDetailsBinding
            by lazy { ActivityProfileDetailsBinding.inflate(layoutInflater) }

    /* */
    val profileResponse: ProfileResponse.Response by lazy {
        IntentExtension.getSerializableExtra(
                intent,
                AppConstants.BUNLDE_PROFILE_RESPONSE
        ) as ProfileResponse.Response
    }


    /** */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViews()
    }

    /** */
    private fun setupViews() {
        setupToolbar()
    }

    /** */
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    /** */
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}