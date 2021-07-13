package com.busem.sample.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.busem.sample.BR

abstract class BaseAbstractFragment<VT : BaseViewModel, BT : ViewDataBinding>
    (@LayoutRes private val layoutId: Int) : BaseFragment() {

    protected lateinit var mBinding: BT
    protected val viewModel: VT by lazy { setViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        mBinding.apply {
            lifecycleOwner = viewLifecycleOwner
            setVariable(BR.mViewModel, viewModel)
        }
        viewModel.apply {
            setupObservers().invoke(viewModel)
            obsToastMessage.observe(viewLifecycleOwner) { showToast(it) }
        }
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.apply { setupViews().invoke(mBinding) }
    }

    abstract fun setViewModel(): VT
    abstract fun setupViews(): BT.() -> Unit
    abstract fun setupObservers(): VT.() -> Unit
}