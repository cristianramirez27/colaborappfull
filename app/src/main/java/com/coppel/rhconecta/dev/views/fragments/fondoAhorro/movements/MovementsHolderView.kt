package com.coppel.rhconecta.dev.views.fragments.fondoAhorro.movements

import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.coppel.rhconecta.dev.R
import com.coppel.rhconecta.dev.databinding.ItemMovementsBinding
import com.coppel.rhconecta.dev.views.fragments.fondoAhorro.movements.model.Movement
import com.coppel.rhconecta.dev.views.utils.bindingInflate

class MovementsHolderView(
    private val dataBinding: ItemMovementsBinding,
    val listener: (Movement) -> Unit
) : RecyclerView.ViewHolder(dataBinding.root) {

    /**
     * Init
     */
    fun bind(item: Movement) {
        dataBinding.movement = item
        itemView.setOnClickListener {
            listener(item)
        }
    }

    /**
     * Public methods for animation
     */
    fun setAnimation() {
        val animation =
            AnimationUtils.loadAnimation(itemView.context, R.anim.fade_transition)
        itemView.startAnimation(animation)
    }

    fun clearAnimation() {
        itemView.clearAnimation()
    }

    companion object {
        fun createInstance(
            parent: ViewGroup,
            listener: (Movement) -> Unit
        ): MovementsHolderView {
            return MovementsHolderView(
                parent.bindingInflate(R.layout.item_movements, false),
                listener
            )
        }
    }
}
