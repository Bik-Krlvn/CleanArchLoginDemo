package com.cheise_proj.cleanarchlogin.di.module

import android.app.Application
import android.content.Context
import com.cheise_proj.cleanarchlogin.di.module.data.DataModule
import com.cheise_proj.cleanarchlogin.di.module.domain.DomainModule
import com.cheise_proj.cleanarchlogin.di.module.local.LocalModule
import com.cheise_proj.cleanarchlogin.di.module.presentation.PresentationModule
import com.cheise_proj.cleanarchlogin.di.module.remote.RemoteModule
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        DomainModule::class,
        DataModule::class,
        PresentationModule::class,
        RemoteModule::class,
        LocalModule::class
    ]
)
class AppModule {
    @Provides
    fun provideContext(application: Application): Context = application.baseContext
}