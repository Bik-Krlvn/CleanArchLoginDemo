package com.cheise_proj.domain.useCases.base

import io.reactivex.Scheduler
import io.reactivex.Single

/**
 * Build Single UseCase
 *
 * @param T type any
 * @param Input type any
 * @property backgroundScheduler provide rx schedulers
 * @property foregroundScheduler provide rx schedulers
 */
abstract class SingleUserCase<T, in Input>(
    private val backgroundScheduler: Scheduler,
    private val foregroundScheduler: Scheduler
) {
    protected abstract fun generateSingle(input: Input?): Single<T>
    /**
     * Subscribe to single build case
     *
     * @param input type input
     * @return subscribe to generate single<T>
     */
    fun buildUseCase(input: Input? = null): Single<T> = generateSingle(input)
        .subscribeOn(backgroundScheduler)
        .observeOn(foregroundScheduler)
}