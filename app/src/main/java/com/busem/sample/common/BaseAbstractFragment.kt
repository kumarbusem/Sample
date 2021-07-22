package com.busem.sample.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.busem.sample.BR


abstract class BaseAbstractFragment<VM : BaseViewModel, VB : ViewDataBinding>(
    private val inflate: Inflate<VB>
) : BaseFragment() {

    protected lateinit var binding: VB
    protected val viewModel: VM by lazy { setViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflate.invoke(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            setVariable(BR.mViewModel, viewModel)
            setupViews().invoke(binding)
        }
        viewModel.apply { setupObservers().invoke(viewModel) }
    }

    abstract fun setViewModel(): VM
    abstract fun setupViews(): VB.() -> Unit
    abstract fun setupObservers(): VM.() -> Unit


}