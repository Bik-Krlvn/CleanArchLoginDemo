package com.cheise_proj.cleanarchlogin

import android.app.Application
import com.cheise_proj.cleanarchlogin.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

class App : DaggerApplication() {
    private val appComponent = DaggerAppComponent
        .builder().application(this).build()

    override fun onCreate() {
        super.onCreate()
        configTimber()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = appComponent

    private fun configTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}