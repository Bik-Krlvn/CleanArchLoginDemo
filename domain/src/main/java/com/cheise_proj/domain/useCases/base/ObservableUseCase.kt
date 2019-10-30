package com.cheise_proj.domain.useCases.base

import io.reactivex.Observable
import io.reactivex.Scheduler

abstract class ObservableUseCase<T, in Input>(
    private val backgroundScheduler: Scheduler,
    private val foregroundScheduler: Scheduler
) {
    protected abstract fun generateObservable(input: Input?): Observable<T>
    fun buildUseCase(input: Input? = null) = generateObservable(input)
        .subscribeOn(backgroundScheduler)
        .observeOn(foregroundScheduler)
}