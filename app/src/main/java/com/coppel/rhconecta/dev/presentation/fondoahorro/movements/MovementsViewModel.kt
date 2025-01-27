package com.coppel.rhconecta.dev.presentation.fondoahorro.movements


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.coppel.rhconecta.dev.domain.common.Either
import com.coppel.rhconecta.dev.domain.movements.GetMovementsFailure
import com.coppel.rhconecta.dev.domain.movements.GetMovementsParams
import com.coppel.rhconecta.dev.domain.movements.GetMovementsResponse
import com.coppel.rhconecta.dev.presentation.fondoahorro.movements.utils.Event
import com.coppel.rhconecta.dev.usecases.movements.GetMovementsUseCase
import com.coppel.rhconecta.dev.views.fragments.fondoAhorro.movements.model.Movement

class MovementsViewModel(
    private val getMovementsUseCase: GetMovementsUseCase,
) : ViewModel() {

    /**
     * Fields
     */
    private lateinit var dataList: List<Movement>
    private val _events = MutableLiveData<Event<MovementsListNavigation>>()
    val events: LiveData<Event<MovementsListNavigation>> get() = _events

    /**
     * public methods
     */
    fun onGetAllMovements() {
        if (!this::dataList.isInitialized) {
            val params = GetMovementsParams
            getMovementsUseCase.run(params) {
                when (it) {
                    is Either.Right -> {
                        handleResponse(it.right)
                    }
                    is Either.Left -> {
                        _events.postValue(Event(MovementsListNavigation.ShowMovementsError((it.left as GetMovementsFailure).message)))
                    }
                }
            }
        } else {
            _events.value = Event(MovementsListNavigation.ShowMovementsList(dataList))
        }
    }

    /**
     * private methods
     */
    private fun handleResponse(response: GetMovementsResponse) {

        response.let {

            dataList = it.dataList
            when {
                it.code == NO_MOVEMENTS_GENERATED -> {
                    _events.postValue(Event(MovementsListNavigation.ShowAlertNotContinue(it.message)))
                }
                it.message.isNullOrEmpty() -> {
                    _events.postValue(Event(MovementsListNavigation.ShowMovementsList(dataList)))
                }
                else -> {
                    _events.postValue(
                        Event(
                            MovementsListNavigation.ShowListWithAlertEndMonth(
                                it.message,
                                dataList
                            )
                        )
                    )
                }
            }
        }
    }

    //Inner Classes & Interfaces
    sealed class MovementsListNavigation {
        data class ShowMovementsError(val message: String) : MovementsListNavigation()
        data class ShowMovementsList(val movementList: List<Movement>) : MovementsListNavigation()
        data class ShowListWithAlertEndMonth(val message: String, val list: List<Movement>) :
            MovementsListNavigation()

        data class ShowAlertNotContinue(val message: String) : MovementsListNavigation()

        object HideLoading : MovementsListNavigation()
        object ShowLoading : MovementsListNavigation()
    }

    companion object {
        const val MOVEMENTS_END_MONT = "01"
        const val NO_MOVEMENTS_GENERATED = "00"
    }
}
