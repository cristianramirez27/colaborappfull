package com.coppel.rhconecta.dev.presentation.profile_actions.fingerprint

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.coppel.rhconecta.dev.R
import com.coppel.rhconecta.dev.databinding.ActivityFingerprintBinding
import com.coppel.rhconecta.dev.domain.common.failure.Failure
import com.coppel.rhconecta.dev.domain.fingerprint.entity.Fingerprint
import com.coppel.rhconecta.dev.presentation.common.dialog.SingleActionDialog
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus
import com.coppel.rhconecta.dev.presentation.profile_actions.fingerprint.adapter.FingerprintAdapter
import org.koin.android.viewmodel.ext.android.viewModel

/** */
class FingerprintActivity : AppCompatActivity() {

    /* */
    private val binding: ActivityFingerprintBinding
            by lazy { ActivityFingerprintBinding.inflate(layoutInflater) }

    /* */
    private val fingerprintViewModel: FingerprintViewModel by viewModel()

    /* */
    private lateinit var fingerprintAdapter: FingerprintAdapter
    private val fingerprints: ArrayList<Fingerprint> = arrayListOf()

    /** */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViews()
        execute()
    }

    /** */
    private fun setupViews() {
        setupToolbar()
        setupFingerprintsRecyclerView()
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
    private fun setupFingerprintsRecyclerView() {
        fingerprintAdapter = FingerprintAdapter(fingerprints)
        binding.rvFingerprints.apply {
            adapter = fingerprintAdapter
            layoutManager = LinearLayoutManager(this@FingerprintActivity)
        }
    }

    /** */
    private fun execute() {
        fingerprintViewModel.getFingerprintsAsLiveData().observe(this) {
            when (it) {
                ProcessStatus.LOADING, null -> showProgressBar()
                ProcessStatus.FAILURE ->
                    manageGetFingerprintsFailure(fingerprintViewModel.failure!!)
                ProcessStatus.COMPLETED ->
                    manageGetFingerprintsDone(fingerprintViewModel.fingerprints)
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
    private fun manageGetFingerprintsFailure(failure: Failure) {
        hideProgressBar()
        val dialog = SingleActionDialog(
                this,
                getString(R.string.fingerprint_failure_default_title),
                getString(R.string.fingerprint_failure_default_content),
                getString(R.string.fingerprint_failure_default_action)
        ) { finish() }
        dialog.setCancelable(false)
        dialog.show()
    }

    /** */
    private fun manageGetFingerprintsDone(fingerprints: List<Fingerprint>) {
        hideProgressBar()
        this.fingerprints.apply {
            clear()
            addAll(fingerprints)
        }
        fingerprintAdapter.notifyDataSetChanged()
    }

    /** */
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}