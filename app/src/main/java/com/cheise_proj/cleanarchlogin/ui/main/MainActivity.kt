package com.cheise_proj.cleanarchlogin.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cheise_proj.cleanarchlogin.R
import com.cheise_proj.cleanarchlogin.ui.login.ProfileActivity
import com.cheise_proj.cleanarchlogin.ui.password.ChangePasswordActivity
import com.cheise_proj.cleanarchlogin.utils.LOGIN_USER_ID_EXTRA
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var identifier: String? = null
        if (intent.hasExtra(LOGIN_USER_ID_EXTRA)) {
            identifier = intent.getStringExtra(LOGIN_USER_ID_EXTRA)
        }
        btn_profile.setOnClickListener {
            navigateToProfile(identifier)
        }

        btn_change_password.setOnClickListener { navigateToChangePassword(identifier) }
    }

    private fun navigateToChangePassword(identifier: String?) {
        val changePassIntent = Intent(this, ChangePasswordActivity::class.java)
        changePassIntent.putExtra(LOGIN_USER_ID_EXTRA, identifier)
        startActivity(changePassIntent)
    }

    private fun navigateToProfile(identifier: String?) {
        val profileIntent = Intent(this, ProfileActivity::class.java)
        profileIntent.putExtra(LOGIN_USER_ID_EXTRA, identifier)
        startActivity(profileIntent)
    }
}
