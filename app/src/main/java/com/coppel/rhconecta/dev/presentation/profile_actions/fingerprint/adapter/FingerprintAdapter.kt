package com.coppel.rhconecta.dev.presentation.profile_actions.fingerprint.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coppel.rhconecta.dev.domain.fingerprint.entity.Fingerprint

/** */
class FingerprintAdapter(
        private val fingerprints: List<Fingerprint>
) : RecyclerView.Adapter<FingerprintViewHolder>() {

    /** */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FingerprintViewHolder =
            FingerprintViewHolder.createInstance(parent)

    /** */
    override fun onBindViewHolder(holder: FingerprintViewHolder, position: Int) {
        val fingerprint = fingerprints[position]
        holder.bind(fingerprint)
    }

    /** */
    override fun getItemCount(): Int = fingerprints.size

}