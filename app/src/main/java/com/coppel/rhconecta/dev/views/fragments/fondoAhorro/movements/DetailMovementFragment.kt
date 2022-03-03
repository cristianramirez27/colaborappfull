package com.coppel.rhconecta.dev.views.fragments.fondoAhorro.movements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.coppel.rhconecta.dev.R
import com.coppel.rhconecta.dev.databinding.FragmentDetailMovementBinding
import com.coppel.rhconecta.dev.views.activities.FondoAhorroActivity
import com.coppel.rhconecta.dev.views.fragments.fondoAhorro.movements.model.Movement

class DetailMovementFragment : Fragment() {

    private lateinit var binding: FragmentDetailMovementBinding
    private var parent: FondoAhorroActivity? = null
    private var movement: Movement? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.arguments?.apply {
            movement = getParcelable(MI_MOVEMENT_ITEM_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentDetailMovementBinding>(
            inflater,
            R.layout.fragment_detail_movement,
            container,
            false
        ).apply { lifecycleOwner = this@DetailMovementFragment }

        setHasOptionsMenu(true)
        parent = (activity as? FondoAhorroActivity)
        parent?.setToolbarTitle(getString(R.string.detail_movement_title))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.movement = movement
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                parent?.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {
        val TAG = DetailMovementFragment::class.java.simpleName
        private const val MI_MOVEMENT_ITEM_KEY = "MI_MOVEMENT_ITEM_KEY"

        fun newInstance(miMovementItem: Movement): DetailMovementFragment {
            return DetailMovementFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(MI_MOVEMENT_ITEM_KEY, miMovementItem)
                }
            }
        }
    }
}
