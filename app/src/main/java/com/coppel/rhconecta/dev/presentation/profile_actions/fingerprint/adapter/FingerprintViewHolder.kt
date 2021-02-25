package com.coppel.rhconecta.dev.presentation.profile_actions.fingerprint.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coppel.rhconecta.dev.databinding.ViewHolderFingerprintBinding
import com.coppel.rhconecta.dev.domain.fingerprint.entity.Fingerprint

/** */
class FingerprintViewHolder(
        private val binding: ViewHolderFingerprintBinding
) : RecyclerView.ViewHolder(binding.root) {

    /** */
    fun bind(fingerprint: Fingerprint) {
        binding.fingerprint = fingerprint
    }

    /** */
    companion object {

        /** */
        fun createInstance(parent: ViewGroup): FingerprintViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ViewHolderFingerprintBinding.inflate(layoutInflater, parent, false)
            return FingerprintViewHolder(binding)
        }

    }

}