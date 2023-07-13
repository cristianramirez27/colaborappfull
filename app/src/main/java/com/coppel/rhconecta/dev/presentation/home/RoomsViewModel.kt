package com.coppel.rhconecta.dev.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coppel.rhconecta.dev.domain.rooms.ReservationsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class RoomsViewModel @Inject constructor(
    private val reservationsUseCase: ReservationsUseCase
) : ViewModel() {

    fun init (url: String, token : String,timeZone : String) {
        viewModelScope.launch {
            val result = reservationsUseCase(url, token, timeZone)
            result.fold({e ->
                Log.e("RoomsViewModel",e.message ?: "print nothing")
            },{ list ->
                if (list.isNotEmpty()){
                    Log.e("RoomsViewModel","show details")
                }
            })
        }
    }
}
