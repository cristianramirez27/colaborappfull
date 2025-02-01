package com.coppel.rhconecta.dev.views.fragments.fondoAhorro.movements

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.coppel.rhconecta.dev.R
import com.coppel.rhconecta.dev.data.analytics.AnalyticsRepositoryImpl
import com.coppel.rhconecta.dev.databinding.FragmentMovementsBinding
import com.coppel.rhconecta.dev.presentation.common.dialog.SingleActionDialog
import com.coppel.rhconecta.dev.presentation.fondoahorro.movements.MovementsViewModel
import com.coppel.rhconecta.dev.presentation.fondoahorro.movements.utils.Event
import com.coppel.rhconecta.dev.views.activities.FondoAhorroActivity
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader
import com.coppel.rhconecta.dev.views.fragments.fondoAhorro.movements.adapter.MovementsAdapter
import com.coppel.rhconecta.dev.views.fragments.fondoAhorro.movements.model.Movement
import org.koin.android.viewmodel.ext.android.viewModel

class MovementsFragment : Fragment(), DialogFragmentGetDocument.OnButtonClickListener {

    private var parent: FondoAhorroActivity? = null
    private var dialogFragmentLoader: DialogFragmentLoader? = null
    private var dialogFragmentGetDocument: DialogFragmentGetDocument? = null
    private val movementsViewModel: MovementsViewModel by viewModel()
    private lateinit var movementsAdapter: MovementsAdapter
    private lateinit var binding: FragmentMovementsBinding
    private lateinit var listener: OnMovementsFragmentListener
    private var analyticsRepositoryImpl: AnalyticsRepositoryImpl = AnalyticsRepositoryImpl()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentMovementsBinding>(
            inflater,
            R.layout.fragment_movements,
            container,
            false
        ).apply { lifecycleOwner = this@MovementsFragment }

        setHasOptionsMenu(true)
        parent = activity as FondoAhorroActivity?
        parent?.setToolbarTitle(getString(R.string.movements))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movementsAdapter = MovementsAdapter { movement ->
            listener.openMovementDetail(movement)
        }.also {
            setHasOptionsMenu(true)
        }

        binding.rvMovements.run {
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
            adapter = movementsAdapter
            isMotionEventSplittingEnabled = false

        }

        movementsViewModel.events.observe(viewLifecycleOwner, Observer(this::validateEvents))
        movementsViewModel.onGetAllMovements()
        analyticsRepositoryImpl.sendVisitFlow(9, 32)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as OnMovementsFragmentListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement OnMovementsFragmentListener")
        }
    }

    /**
     * Private methods
     */
    private fun validateEvents(event: Event<MovementsViewModel.MovementsListNavigation>?) {
        event?.getContentIfNotHandled()?.let { navigation ->
            when (navigation) {
                is MovementsViewModel.MovementsListNavigation.ShowMovementsError -> navigation.run {
                    showWarningDialog(message)
                }
                is MovementsViewModel.MovementsListNavigation.ShowMovementsList -> navigation.run {
                    showList(movementList)
                }
                MovementsViewModel.MovementsListNavigation.HideLoading -> {
                    hideLoaderDialog()
                }
                MovementsViewModel.MovementsListNavigation.ShowLoading -> {
                    showLoaderDialog()
                }
                is MovementsViewModel.MovementsListNavigation.ShowListWithAlertEndMonth -> navigation.run {
                    showSimpleDialog(message) { showList(list) }
                }
                is MovementsViewModel.MovementsListNavigation.ShowAlertNotContinue -> navigation.run {
                    showSimpleDialog(message) { activity?.finish() }
                }
            }
        }
    }

    private fun showSimpleDialog(message: String, function: () -> Unit) {
        SingleActionDialog(
            context,
            getString(R.string.alert),
            message,
            getString(R.string.accept)
        ) { function() }.apply {
            setCancelable(false)
            show()
        }
    }

    private fun showLoaderDialog() {
        dialogFragmentLoader = DialogFragmentLoader()
        parent?.supportFragmentManager?.let {
            dialogFragmentLoader?.show(
                it,
                DialogFragmentLoader.TAG
            )
        }
    }

    private fun hideLoaderDialog() {
        dialogFragmentLoader?.close()
    }

    private fun showList(movementList: List<Movement>) {
        binding.tvInformation.visibility = View.VISIBLE
        movementsAdapter.addData(movementList)
    }

    private fun showWarningDialog(message: String) {
        dialogFragmentGetDocument = DialogFragmentGetDocument()
        dialogFragmentGetDocument?.apply {
            setType(DialogFragmentGetDocument.NO_REFUSE_REMOVE, parent)
            setContentText(message)
            setOnButtonClickListener(this@MovementsFragment)
            parent?.supportFragmentManager?.let {
                show(
                    it,
                    DialogFragmentGetDocument.TAG
                )
            }
        }
    }

    /**
     * View callback
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                parent?.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * DialogFragmentGetDocument callback, used to showWarningDialog
     */
    override fun onSend(email: String?) {
    }

    override fun onAccept() {
        activity?.finish()
        dialogFragmentGetDocument?.close()
    }

    companion object {
        val TAG = MovementsFragment::class.java.simpleName
        fun newInstance(): MovementsFragment {
            return MovementsFragment()
        }
    }

    /**
     * Local Callback
     */
    interface OnMovementsFragmentListener {
        fun openMovementDetail(itemMovement: Movement)
    }
}
