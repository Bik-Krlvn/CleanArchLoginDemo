package com.cheise_proj.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

/**
 * A ViewModel Factory
 * @property ViewModelFactory
 * @author Kelvin Birikorang
 */
class ViewModelFactory @Inject constructor(
    private val creators:Map<Class<out ViewModel>,@JvmSuppressWildcards Provider<ViewModel>>
):ViewModelProvider.Factory {
    /**
     * @throws IllegalArgumentException
     */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val creator = creators[modelClass]?:creators.entries.firstOrNull{
            modelClass.isAssignableFrom(it.key)
        }?.value?:throw IllegalArgumentException("unknown model class $modelClass")

        @Suppress("UNCHECKED_CAST")
        return creator.get() as T
    }
}