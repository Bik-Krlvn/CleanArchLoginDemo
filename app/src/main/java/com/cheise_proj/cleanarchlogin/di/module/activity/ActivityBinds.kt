package com.cheise_proj.cleanarchlogin.di.module.activity

import com.cheise_proj.cleanarchlogin.ui.login.LoginActivity
import com.cheise_proj.cleanarchlogin.ui.login.ProfileActivity
import com.cheise_proj.cleanarchlogin.ui.password.ChangePasswordActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBinds {
    @ContributesAndroidInjector
    abstract fun contributeLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    abstract fun contributeProfileActivity(): ProfileActivity

    @ContributesAndroidInjector
    abstract fun contributeChangePasswordActivity(): ChangePasswordActivity
}