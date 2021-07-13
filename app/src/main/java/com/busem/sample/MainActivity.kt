package com.busem.sample

import com.busem.sample.common.BaseActivity
import com.busem.sample.databinding.ActivityMainBinding

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(
    MainViewModel::class.java,
    ActivityMainBinding::inflate
) {

    override fun ActivityMainBinding.setupViews() {

    }

    override fun ActivityMainBinding.observeViewModel() {
        viewModel.apply {

        }
    }

}