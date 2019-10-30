package com.cheise_proj.domain.useCases.base

import io.reactivex.Completable
import io.reactivex.Scheduler

abstract class CompletableUseCase<in Input>(
    private val backgroundScheduler: Scheduler,
    private val foregroundScheduler: Scheduler
) {
    protected abstract fun generateCompletable(input: Input?): Completable
    fun buildUseCase(input: Input? = null) = generateCompletable(input)
        .subscribeOn(backgroundScheduler)
        .observeOn(foregroundScheduler)
}