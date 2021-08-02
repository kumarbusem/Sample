package com.busem.sample.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import androidx.viewbinding.ViewBinding
import com.busem.sample.BR


abstract class BaseAbstractFragment<V : BaseViewModel, B : ViewBinding>(
    private val inflate: Inflate<B>
) : BaseFragment() {

    protected val viewModel: V by lazy { setViewModel() }

    @Suppress("MemberVisibilityCanBePrivate")
    protected var nullableBinding: B? = null

    @Suppress("MemberVisibilityCanBePrivate")
    protected val binding: B
        get() = nullableBinding
            ?: throw IllegalStateException("View either has not been initialized or has been destroyed")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        nullableBinding = inflate.invoke(layoutInflater)
        setupViews().invoke(binding)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers().invoke(viewModel)
    }

    abstract fun setViewModel(): V
    open fun setupViews(): B.() -> Unit = {}
    open fun setupObservers(): V.() -> Unit = {}

    override fun onDestroyView() {
        super.onDestroyView()
        nullableBinding = null
    }
}
