package com.busem.sample.features.splash

import androidx.lifecycle.ViewModelProvider
import com.busem.sample.R
import com.busem.sample.common.BaseAbstractFragment
import com.busem.sample.common.ViewModelFactory
import com.busem.sample.common.toast
import com.busem.sample.databinding.FragmentSplashBinding

class SplashFragment : BaseAbstractFragment<SplashViewModel, FragmentSplashBinding>(R.layout.fragment_splash) {

    override fun setViewModel(): SplashViewModel =
            ViewModelProvider(this@SplashFragment, ViewModelFactory {
                SplashViewModel()
            }).get(SplashViewModel::class.java)

    override fun setupViews(): FragmentSplashBinding.() -> Unit = {
        viewModel.checkAndNavigate()
    }

    override fun setupObservers(): SplashViewModel.() -> Unit = {
        navigateTo.observe(viewLifecycleOwner) { destination ->
            when (destination) {
                NavDestination.LOGIN -> navigateToAccessScreen()
                NavDestination.HOME -> navigateToHomeScreen()
                else -> toast(getString(R.string.failed_to_navigate))
            }
        }
    }

    private fun navigateToAccessScreen() {
        navigateById(R.id.action_splashFragment_to_loginFragment)
    }

    private fun navigateToHomeScreen() {
        navigateById(R.id.action_splashFragment_to_homeFragment)
    }
}
