package com.fifed.githubrepositories.ui.resource.impl

import android.content.res.Resources
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import com.fifed.githubrepositories.ui.resource.ResourceManager

class ResourceManagerImpl(private val resources: Resources) : ResourceManager {
    override fun getString(@StringRes id: Int, vararg formatArgs: Any): String {
        return if (formatArgs.isEmpty()) {
            resources.getString(id)
        } else {
            resources.getString(id, *formatArgs)
        }
    }

    override fun getColor(resId: Int): Int {
        return ResourcesCompat.getColor(resources, resId, null)
    }
}