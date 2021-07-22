package com.busem.sample

import androidx.lifecycle.ViewModelProvider
import com.busem.sample.common.BaseActivity
import com.busem.sample.common.ViewModelFactory
import com.busem.sample.databinding.ActivityMainBinding

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(
    ActivityMainBinding::inflate
) {
    override fun setViewModel(): MainViewModel =
        ViewModelProvider(this@MainActivity, ViewModelFactory {
            MainViewModel()
        }).get(MainViewModel::class.java)

    override fun setupViews(): ActivityMainBinding.() -> Unit = {

    }

    override fun setupObservers(): MainViewModel.() -> Unit = {

    }
}