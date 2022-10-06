package com.coppel.rhconecta.dev.presentation.profile_actions.profile_details

import android.os.Bundle
import android.view.View
import com.coppel.rhconecta.dev.R
import com.coppel.rhconecta.dev.analytics.time.AnalyticsTimeAppCompatActivity
import com.coppel.rhconecta.dev.business.models.ProfileResponse
import com.coppel.rhconecta.dev.databinding.ActivityProfileDetailsBinding
import com.coppel.rhconecta.dev.presentation.common.extension.IntentExtension
import com.coppel.rhconecta.dev.views.utils.AppConstants
import com.coppel.rhconecta.dev.views.utils.ZendeskStatusCallBack
import com.coppel.rhconecta.dev.views.utils.ZendeskUtil

/** */
class ProfileDetailsActivity : AnalyticsTimeAppCompatActivity(), ZendeskStatusCallBack {

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

    override fun onResume() {
        super.onResume()
        setCallBackAndRefreshStatus(this)
    }

    /** */
    private fun setupViews() {
        setupToolbar()
        binding.appBarMain.zendeskInbox.setOnClickListener {
            clickZendesk()
        }
    }

    /** */
    private fun setupToolbar() {
        setSupportActionBar(binding.appBarMain.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        binding.appBarMain.titleToolbar.setText(R.string.profile_details_activity_title)
    }

    /** */
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    /**
     * Zendesk methods
     */
    override fun enableZendeskFeature() {
        binding.appBarMain.zendeskInbox.visibility = View.VISIBLE
    }

    override fun disableZendeskFeature() {
        binding.appBarMain.zendeskInbox.visibility = View.GONE
    }

    override fun zendeskChatOnLine() {
        binding.appBarMain.zendeskInbox.setActive()
    }

    override fun zendeskChatOutLine() {
        binding.appBarMain.zendeskInbox.setDisable()
    }

    override fun zendeskSetNotification() {
        binding.appBarMain.zendeskInbox.setNotification()
    }

    override fun zendeskRemoveNotification() {
        binding.appBarMain.zendeskInbox.removeNotification()
    }

    override fun zendeskErrorMessage(message: String) {
        showWarningDialog(message)
    }
}