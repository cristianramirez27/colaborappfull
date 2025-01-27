package com.coppel.rhconecta.dev.presentation.profile_actions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.coppel.rhconecta.dev.domain.common.Either
import com.coppel.rhconecta.dev.domain.common.failure.Failure
import com.coppel.rhconecta.dev.domain.fingerprint.use_case.have_fingerprints.HaveFingerprintsParams
import com.coppel.rhconecta.dev.domain.fingerprint.use_case.have_fingerprints.HaveFingerprintsUseCase
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus

/** */
class ProfileActionsViewModel(
        private val haveFingerprintsUseCase: HaveFingerprintsUseCase
) : ViewModel() {

    /* */
    private val haveFingerprintsLiveData = MutableLiveData<ProcessStatus>()

    /* */
    var haveFingerprints: Boolean = false
        private set

    /* */
    var failure: Failure? = null


    /** */
    fun haveFingerprintsAsLiveData(): LiveData<ProcessStatus> {
        haveFingerprintsLiveData.postValue(ProcessStatus.LOADING)
        val params = HaveFingerprintsParams
        haveFingerprintsUseCase.run(params) {
            when (it) {
                is Either.Right -> {
                    haveFingerprints = it.right.haveFingerprints
                    haveFingerprintsLiveData.postValue(ProcessStatus.COMPLETED)
                }
                is Either.Left -> {
                    failure = it.left
                    haveFingerprintsLiveData.postValue(ProcessStatus.FAILURE)
                }
            }
        }
        return haveFingerprintsLiveData
    }

}