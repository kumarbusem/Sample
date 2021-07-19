package com.busem.sample.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.busem.sample.BR


abstract class BaseAbstractFragment<VT : BaseViewModel, BT : ViewDataBinding>(
    viewModelClass: Class<VT>,
    private val inflate: Inflate<BT>
) : BaseFragment() {

    protected lateinit var binding: BT
    protected val viewModel: VT by lazy { ViewModelProviders.of(this)[viewModelClass] }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflate.invoke(layoutInflater)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            setVariable(BR.mViewModel, viewModel)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply { setupViews().invoke(binding) }
        viewModel.apply { setupObservers().invoke(viewModel) }
    }

    abstract fun setupViews(): BT.() -> Unit
    abstract fun setupObservers(): VT.() -> Unit
}