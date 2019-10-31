package com.cheise_proj.domain.useCases.base

import io.reactivex.Observable
import io.reactivex.Scheduler

/**
 * Build use case user observable
 *
 * @param T
 * @param Input
 * @property backgroundScheduler rx background schedulers
 * @property foregroundScheduler rx foreground schedulers
 */
abstract class ObservableUseCase<T, in Input>(
    private val backgroundScheduler: Scheduler,
    private val foregroundScheduler: Scheduler
) {
    /**
     * return observable wrapper of type T
     *
     * @param input provide any type
     * @return observable wrapper of type T
     */
    protected abstract fun generateObservable(input: Input?): Observable<T>
    fun buildUseCase(input: Input? = null): Observable<T> = generateObservable(input)
        .subscribeOn(backgroundScheduler)
        .observeOn(foregroundScheduler)
}