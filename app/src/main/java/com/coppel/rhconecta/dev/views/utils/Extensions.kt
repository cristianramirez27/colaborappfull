package com.coppel.rhconecta.dev.views.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

fun <T : ViewDataBinding> ViewGroup.bindingInflate(
    @LayoutRes layoutRes: Int,
    attachToRoot: Boolean = true,
): T = DataBindingUtil.inflate(LayoutInflater.from(context), layoutRes, this, attachToRoot)
