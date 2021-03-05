package com.coppel.rhconecta.dev.presentation.profile_actions.fingerprint

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.coppel.rhconecta.dev.domain.common.Either
import com.coppel.rhconecta.dev.domain.common.failure.Failure
import com.coppel.rhconecta.dev.domain.fingerprint.entity.Fingerprint
import com.coppel.rhconecta.dev.domain.fingerprint.use_case.get_fingerprints.GetFingerprintsParams
import com.coppel.rhconecta.dev.domain.fingerprint.use_case.get_fingerprints.GetFingerprintsUseCase
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus

/** */
class FingerprintViewModel(
        private val getFingerprintsUseCase: GetFingerprintsUseCase
) : ViewModel() {

    /* */
    private val getFingerprintsLiveData = MutableLiveData<ProcessStatus>()

    /* */
    var fingerprints: List<Fingerprint> = listOf()
        private set

    /* */
    var failure: Failure? = null


    /** */
    fun getFingerprintsAsLiveData(): LiveData<ProcessStatus> {
        getFingerprintsLiveData.postValue(ProcessStatus.LOADING)
        val params = GetFingerprintsParams
        getFingerprintsUseCase.run(params) {
            when (it) {
                is Either.Right -> {
                    fingerprints = it.right.fingerprints
                    getFingerprintsLiveData.postValue(ProcessStatus.COMPLETED)
                }
                is Either.Left -> {
                    failure = it.left
                    getFingerprintsLiveData.postValue(ProcessStatus.FAILURE)
                }
            }
        }
        return getFingerprintsLiveData
    }

}