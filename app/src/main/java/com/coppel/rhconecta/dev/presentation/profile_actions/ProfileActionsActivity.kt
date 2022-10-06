package com.coppel.rhconecta.dev.presentation.profile_actions

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.coppel.rhconecta.dev.R
import com.coppel.rhconecta.dev.analytics.time.AnalyticsTimeAppCompatActivity
import com.coppel.rhconecta.dev.business.models.ProfileResponse
import com.coppel.rhconecta.dev.databinding.ActivityProfileActionsBinding
import com.coppel.rhconecta.dev.domain.common.failure.Failure
import com.coppel.rhconecta.dev.presentation.common.builder.IntentBuilder
import com.coppel.rhconecta.dev.presentation.common.dialog.SingleActionDialog
import com.coppel.rhconecta.dev.presentation.common.extension.IntentExtension
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus
import com.coppel.rhconecta.dev.presentation.poll_toolbar.PollToolbarFragment
import com.coppel.rhconecta.dev.presentation.poll_toolbar.PollToolbarFragment.ToolbarFragmentCommunication
import com.coppel.rhconecta.dev.presentation.profile_actions.fingerprint.FingerprintActivity
import com.coppel.rhconecta.dev.presentation.profile_actions.profile_details.ProfileDetailsActivity
import com.coppel.rhconecta.dev.views.utils.AppConstants
import com.coppel.rhconecta.dev.views.utils.AppUtilities
import com.coppel.rhconecta.dev.views.utils.MenuUtilities
import org.koin.android.viewmodel.ext.android.viewModel

/** */
class ProfileActionsActivity :  AnalyticsTimeAppCompatActivity(),
    ToolbarFragmentCommunication {

    /* */
    private val binding: ActivityProfileActionsBinding
            by lazy { ActivityProfileActionsBinding.inflate(layoutInflater) }

    /* */
    private val profileActionsViewModel: ProfileActionsViewModel by viewModel()

    /* */
    private lateinit var profileResponse: ProfileResponse.Response

    lateinit var pollToolbarFragment: PollToolbarFragment


    /** */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initValues()
        setupViews()
        execute()
    }

    /** */
    private fun initValues() {
        profileResponse = IntentExtension.getSerializableExtra(
                intent,
                AppConstants.BUNLDE_PROFILE_RESPONSE
        ) as ProfileResponse.Response
    }

    /** */
    private fun setupViews() {
        setupToolbar()
        setupUserInformation()
        setupActions()
    }

    /** */
    private fun execute() {
        profileActionsViewModel.haveFingerprintsAsLiveData().observe(this) {
            when (it) {
                ProcessStatus.LOADING, null ->
                    showProgressBar()
                ProcessStatus.FAILURE ->
                    manageHaveFingerprintFailure(profileActionsViewModel.failure!!)
                ProcessStatus.COMPLETED -> {
                    val sections = MenuUtilities.getSubSection()
                    if (AppUtilities.getBooleanFromSharedPreferences(this, AppConstants.SHARED_PREFERENCES_FILIAL))
                        manageHaveFingerprintDone(MenuUtilities.findSubItem(sections, MenuUtilities.getSectionsMap()[AppConstants.OPTION_PROFILE]
                                ?: -1, 1) && profileActionsViewModel.haveFingerprints)
                    else
                        manageHaveFingerprintDone(profileActionsViewModel.haveFingerprints)
                }
            }
        }
    }

    /** */
    private fun showProgressBar() {
        binding.pbLoader.visibility = View.VISIBLE
    }

    /** */
    private fun hideProgressBar() {
        binding.pbLoader.visibility = View.GONE
    }

    /** */
    private fun manageHaveFingerprintFailure(failure: Failure) {
        hideProgressBar()
        SingleActionDialog(
                this,
                getString(R.string.profile_actions_failure_default_title),
                getString(R.string.profile_actions_failure_default_content),
                getString(R.string.profile_actions_failure_default_action),
                null
        ).show()
    }

    /** */
    private fun manageHaveFingerprintDone(haveFingerprints: Boolean) {
        hideProgressBar()
        binding.flFingerprints.visibility = if (haveFingerprints)
            View.VISIBLE
        else View.GONE
    }

    /** */
    private fun setupToolbar() {
        pollToolbarFragment =
            supportFragmentManager.findFragmentById(R.id.toolbar_fragment) as PollToolbarFragment
        setSupportActionBar(pollToolbarFragment.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        pollToolbarFragment.tvTitleToolbar.setText(R.string.profile_actions_activity_title)
    }

    /** */
    private fun setupUserInformation() {
        binding.apply {
            Glide.with(root)
                    .load(profileResponse.fotoperfil)
                    .circleCrop()
                    .into(ivUserImage)
            tvUserName.text = profileResponse.nombre
            tvUserNumber.text = profileResponse.colaborador
        }
    }

    /** */
    private fun setupActions() {
        binding.apply {
            flUserProfile.setOnClickListener(::onUserProfileClickListener)
            flFingerprints.setOnClickListener(::onFingerprintsClickListener)
        }
    }

    /** */
    private fun onUserProfileClickListener(view: View) {
        val intent = IntentBuilder(Intent(this, ProfileDetailsActivity::class.java))
                .putSerializableExtra(AppConstants.BUNLDE_PROFILE_RESPONSE, profileResponse)
                .build()
        startActivity(intent)
    }

    /** */
    private fun onFingerprintsClickListener(view: View) {
        val intent = Intent(this, FingerprintActivity::class.java)
        startActivity(intent)
    }

    /** */
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}