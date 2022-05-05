package com.fifed.githubrepositories.ui.resource

import androidx.annotation.ColorRes
import androidx.annotation.StringRes

interface ResourceManager {
    fun getString(@StringRes id: Int, vararg formatArgs: Any): String
    fun getColor(@ColorRes resId: Int): Int
}