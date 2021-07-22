package com.busem.sample.features.login

import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.busem.sample.R
import com.busem.sample.common.BaseAbstractFragment
import com.busem.sample.common.ViewModelFactory
import com.busem.sample.common.toast
import com.busem.sample.databinding.FragmentLoginBinding

class LoginFragment : BaseAbstractFragment<LoginViewModel, FragmentLoginBinding>(
    FragmentLoginBinding::inflate,
) {

    override fun setViewModel(): LoginViewModel =
        ViewModelProvider(this@LoginFragment, ViewModelFactory {
            LoginViewModel()
        }).get(LoginViewModel::class.java)

    override fun setupViews(): FragmentLoginBinding.() -> Unit = {
        fun setupFields() {
            etUsername.doAfterTextChanged { etUsername.error = null }
            etPassword.doAfterTextChanged { etPassword.error = null }
        }

        fun setupGitIn() {

            fun userNameError() {
                etUsername.error = getString(R.string.enter_proper_username)
            }

            fun passwordError() {
                etPassword.error = getString(R.string.enter_proper_password)
            }

            btnGitIn.setOnClickListener {
                val username = etUsername.text.toString().trim().takeIf { it.isNotBlank() } ?: run {
                    userNameError()
                    return@setOnClickListener
                }

                val password = etPassword.text.toString().trim().takeIf { it.isNotBlank() } ?: run {
                    passwordError()
                    return@setOnClickListener
                }

                viewModel.gitInUser(username, password)
            }
        }

        setupFields()
        setupGitIn()
    }

    override fun setupObservers(): LoginViewModel.() -> Unit = {

        userType.observe(viewLifecycleOwner) { userType ->
            when (userType) {
                UserType.NEW -> {
                    toast(getString(R.string.welcome))
                    navigateToHomeScreen()
                }
                UserType.EXISTING -> {
                    toast(getString(R.string.welcome_back))
                    navigateToHomeScreen()
                }
                else -> {
                    toast(getString(R.string.failed_to_auth))
                }
            }
        }

    }

    private fun navigateToHomeScreen() {
        navigateById(R.id.action_loginFragment_to_homeFragment)
    }

}
