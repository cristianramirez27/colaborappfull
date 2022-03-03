package com.coppel.rhconecta.dev.views.fragments.fondoAhorro.movements.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coppel.rhconecta.dev.views.fragments.fondoAhorro.movements.MovementsHolderView
import com.coppel.rhconecta.dev.views.fragments.fondoAhorro.movements.model.Movement

class MovementsAdapter(private val listener: (Movement) -> Unit) :
    RecyclerView.Adapter<MovementsHolderView>() {

    private val movementsList: MutableList<Movement> = mutableListOf()
    private var lastPosition: Int = -1

    fun addData(newData: List<Movement>) {
        movementsList.addAll(newData)
        notifyDataSetChanged()
    }

    // Callbacks
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovementsHolderView {
        return MovementsHolderView.createInstance(parent, listener)
    }

    override fun onBindViewHolder(holder: MovementsHolderView, position: Int) {
        setAnimation(holder, position)
        holder.bind(movementsList[position])
    }

    override fun getItemCount(): Int {
        return movementsList.size
    }

    override fun onViewDetachedFromWindow(holder: MovementsHolderView) {
        holder.clearAnimation()
    }

    //Private methods
    private fun setAnimation(holder: MovementsHolderView, position: Int) {
        if (position > lastPosition) {
            holder.setAnimation()
            lastPosition = position
        }
    }
}
