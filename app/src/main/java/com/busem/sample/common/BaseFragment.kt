package com.busem.sample.common

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.busem.sample.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

open class BaseFragment : Fragment() {

    private lateinit var mActivity: MainActivity

    private val mJob = Job()
    protected val ioScope: CoroutineScope by lazy { CoroutineScope(Dispatchers.IO + mJob) }
    protected val uiScope: CoroutineScope by lazy { CoroutineScope(Dispatchers.Main + mJob) }

    protected val TAG: String = javaClass.simpleName

    protected inline fun <T> LiveData<T>.startObserving(crossinline onChange: (T?) -> Unit) {
        this.observe(viewLifecycleOwner) { onChange(it) }
    }

    protected fun navigateById(navigationId: Int) {
        findNavController().navigate(navigationId)
    }

    protected fun navigateBack() {
        findNavController().popBackStack()
    }

    protected fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mJob.cancel()
    }
}