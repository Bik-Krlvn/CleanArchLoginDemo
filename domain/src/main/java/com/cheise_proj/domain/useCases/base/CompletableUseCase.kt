package com.cheise_proj.domain.useCases.base

import io.reactivex.Completable
import io.reactivex.Scheduler

/**
 * Build use case Completable
 *
 * @param Input type any
 * @property backgroundScheduler rx background schedulers
 * @property foregroundScheduler rx foreground schedulers
 */
abstract class CompletableUseCase<in Input>(
    private val backgroundScheduler: Scheduler,
    private val foregroundScheduler: Scheduler
) {
    protected abstract fun generateCompletable(input: Input?): Completable
    /**
     * return completable
     *
     * @param input type any
     * @return subscribe to stream
     */
    fun buildUseCase(input: Input? = null):Completable = generateCompletable(input)
        .subscribeOn(backgroundScheduler)
        .observeOn(foregroundScheduler)
}