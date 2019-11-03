package com.cheise_proj.cleanarchlogin.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cheise_proj.cleanarchlogin.R
import com.cheise_proj.cleanarchlogin.ui.base.BaseActivity
import com.cheise_proj.cleanarchlogin.ui.main.MainActivity
import com.cheise_proj.cleanarchlogin.utils.LOGIN_USER_ID_EXTRA
import com.cheise_proj.presentation.model.Status
import com.cheise_proj.presentation.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_login.*
import timber.log.Timber

class LoginActivity : BaseActivity() {
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        configViewModel()
        btn_login.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        val username = et_username.text.toString()
        val password = et_password.text.toString()
        userViewModel.authenticateUser(username, password).observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {
                    Timber.i("loading...")
                }
                Status.SUCCESS -> {
                    val mainActivityIntent = Intent(this, MainActivity::class.java)
                    mainActivityIntent.putExtra(LOGIN_USER_ID_EXTRA, it.data?.id)
                    Toast.makeText(this, "welcome ${it.data?.username}", Toast.LENGTH_LONG).show()
                    Timber.i("data: ${it.data} ")
                    startActivity(mainActivityIntent)
                    finish()
                }
                Status.ERROR -> {
                    it.message?.let { err ->
                        setLoginErrorMessage(err)
                    }
                    Timber.i("error: ${it.message}")
                }
            }
        })
    }

    private fun setLoginErrorMessage(err: String) {
        if (err.contains("Failed to connect to")) {
            Toast.makeText(this, "no internet connection", Toast.LENGTH_LONG).show()
        }
        if (err.contains("HTTP 401 Unauthorized")) {
            Toast.makeText(this, "username or password invalid", Toast.LENGTH_LONG).show()
        }
    }

    private fun configViewModel() {
        userViewModel = ViewModelProvider(this, viewModelFactory)[UserViewModel::class.java]
    }
}
