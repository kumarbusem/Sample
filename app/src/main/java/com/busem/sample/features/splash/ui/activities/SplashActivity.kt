package com.busem.sample.features.splash.ui.activities

import com.busem.sample.R
import com.busem.sample.common.BaseActivity
import com.busem.sample.common.toast
import com.busem.sample.databinding.ActivitySplashBinding
import com.busem.sample.features.access.ui.AccessActivity
import com.busem.sample.features.home.ui.HomeActivity
import com.busem.sample.features.splash.viewModels.NavDestination
import com.busem.sample.features.splash.viewModels.SplashViewModel
import java.lang.ref.WeakReference

class SplashActivity : BaseActivity<SplashViewModel, ActivitySplashBinding>(
    SplashViewModel::class.java,
    ActivitySplashBinding::inflate
) {

    override fun ActivitySplashBinding.setupViews() {
        viewModel.checkAndNavigate()
    }

    override fun ActivitySplashBinding.observeViewModel() {
        viewModel.apply {

            navigateTo.observe(this@SplashActivity) { destination ->
                when (destination) {
                    NavDestination.LOGIN -> navigateToAccessScreen()
                    NavDestination.HOME -> navigateToHomeScreen()
                    else -> toast(getString(R.string.failed_to_navigate))
                }
            }
        }
    }

    private fun navigateToAccessScreen() {
        startActivity(AccessActivity.getIntent(WeakReference(this)))
        finish()
    }

    private fun navigateToHomeScreen() {
        startActivity(HomeActivity.getIntent(WeakReference(this)))
        finish()
    }

}