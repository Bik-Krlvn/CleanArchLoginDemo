package com.cheise_proj.cleanarchlogin.di

import android.app.Application
import com.cheise_proj.cleanarchlogin.App
import com.cheise_proj.cleanarchlogin.di.module.AppModule
import com.cheise_proj.cleanarchlogin.di.module.activity.ActivityBinds
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, ActivityBinds::class])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(instance: App?)
}