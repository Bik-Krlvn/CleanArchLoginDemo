package com.cheise_proj.cleanarchlogin.ui.password

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cheise_proj.cleanarchlogin.R
import com.cheise_proj.cleanarchlogin.ui.base.BaseActivity
import com.cheise_proj.cleanarchlogin.utils.LOGIN_USER_ID_EXTRA
import com.cheise_proj.presentation.model.Status
import com.cheise_proj.presentation.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_change_password.*
import timber.log.Timber

class ChangePasswordActivity : BaseActivity() {
    private lateinit var currentPass: EditText
    private lateinit var newPass: EditText
    private lateinit var confirmPass: EditText
    private var identifier: String? = null
    private lateinit var userViewModel: UserViewModel
    private lateinit var progress:ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        progress = progressBar
        showProgress(false)
        initEditText()
        if (intent.hasExtra(LOGIN_USER_ID_EXTRA)) {
            identifier = intent.getStringExtra(LOGIN_USER_ID_EXTRA)
        }
        btn_change_pass.setOnClickListener {
            requestPasswordChange(identifier)
        }
        configViewModel()
    }

    private fun showProgress(show: Boolean = true) {
        if (show){
            progress.visibility = View.VISIBLE
        }else{
            progress.visibility = View.GONE
        }
    }

    private fun configViewModel() {
        userViewModel = ViewModelProvider(this, viewModelFactory)[UserViewModel::class.java]
    }

    private fun requestPasswordChange(identifier: String?) {
        val oldPass = currentPass.text.toString().trim()
        val updatePass = newPass.text.toString().trim()
        val confirm = confirmPass.text.toString().trim()
        if (TextUtils.isEmpty(oldPass)) {
            currentPass.error = "invalid password"
        }
        if (TextUtils.isEmpty(updatePass)) {
            newPass.error = "invalid password"
        }
        if (confirm != updatePass) {
            confirmPass.error = "password mismatch"
        } else {
            identifier?.let { id ->
                userViewModel.changeUserPassword(id, oldPass, confirm).observe(this, Observer {
                    when (it.status) {
                        Status.LOADING -> {
                            showProgress()
                            Timber.i("loading...")
                        }
                        Status.SUCCESS -> {
                            showProgress(false)
                            Timber.i("success response: ${it.data}")
                        }
                        Status.ERROR -> {
                            showProgress(false)
                            Timber.i("error password: ${it.message}")
                        }
                    }
                })
            }
        }

    }

    private fun initEditText() {
        currentPass = et_old_pass
        newPass = et_new_pass
        confirmPass = et_confirm_pass
    }
}
