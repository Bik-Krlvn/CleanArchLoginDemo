package com.cheise_proj.presentation.viewmodel.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * Base View model class
 * @author Kelvin Birikorang
 * @property BaseViewModel
 */
abstract class BaseViewModel : ViewModel() {
    protected val disposable = CompositeDisposable()
    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

}