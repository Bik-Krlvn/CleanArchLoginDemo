package com.cheise_proj.cleanarchlogin.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cheise_proj.cleanarchlogin.R
import com.cheise_proj.cleanarchlogin.ui.base.BaseActivity
import com.cheise_proj.cleanarchlogin.utils.LOGIN_USER_ID_EXTRA
import com.cheise_proj.presentation.model.Status
import com.cheise_proj.presentation.model.UserProfile
import com.cheise_proj.presentation.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_profile.*
import timber.log.Timber

class ProfileActivity : BaseActivity() {
    private lateinit var userViewModel: UserViewModel
    private var identifier: String? = null
    private lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        progress = progressBar
        showLoadingProgress(false)
        if (intent.hasExtra(LOGIN_USER_ID_EXTRA)) {
            identifier = intent.getStringExtra(LOGIN_USER_ID_EXTRA)
        }
        configViewModel()
    }

    private fun configViewModel() {
        userViewModel = ViewModelProvider(this, viewModelFactory)[UserViewModel::class.java]
        identifier?.let { id ->
            userViewModel.getUserProfile(id).observe(this, Observer {
                when (it.status) {
                    Status.LOADING -> {
                        showLoadingProgress()
                    }
                    Status.SUCCESS -> {
                        showLoadingProgress(false)
                        setProfileData(it.data)
                    }
                    Status.ERROR -> {
                        showLoadingProgress(false)
                        Timber.i("profileError: ${it.message}")
                    }
                }
            })
        }

    }

    private fun setProfileData(data: UserProfile?) {
        val userId = "identifier: ${data?.id}"
        val email = "email Address: ${data?.email}"
        val username = "username: ${data?.username}"
        val name = "full name: ${data?.name}"
        val dob = "Date of Birth: ${data?.dob}"

        tv_user_identifier.text = userId
        tv_username.text = username
        tv_email.text = email
        tv_name.text = name
        tv_dob.text = dob
    }

    private fun showLoadingProgress(show: Boolean = true) {
        if (show) {
            progress.visibility = View.VISIBLE
        } else {
            progress.visibility = View.GONE
        }
    }
}
