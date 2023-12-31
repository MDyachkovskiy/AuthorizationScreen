package com.test.application.core.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.test.application.core.utils.AppState
import com.test.application.core.utils.ServerError
import com.test.application.core.utils.ServerException

typealias Inflate<F> = (LayoutInflater, ViewGroup?, Boolean) -> F
@Suppress("UNCHECKED_CAST")
abstract class BaseFragment <I, VB : ViewBinding>(
    private val inflate: Inflate<VB>
) : Fragment() {
    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    protected lateinit var progressBar: FrameLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflate.invoke(inflater, container, false)
        findProgressBar()
        return binding.root
    }

    abstract fun findProgressBar(): FrameLayout

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    protected fun renderData(appState: AppState<I>) {
        when (appState) {
            is AppState.Success<*> -> {
                hideLoading()
                val data = appState.data as I
                setupData(data)
            }

            is AppState.Loading -> {
                showViewLoading()
            }

            is AppState.Error -> {
                hideLoading()
                showErrorDialog(appState.message)
            }
        }
    }

    abstract fun showViewLoading()

    abstract fun setupData(data: I)

    open protected fun showErrorDialog(error: Throwable) {
        hideLoading()
        val message = when(error) {
            is ServerException -> {
                when(error.error) {
                    ServerError.BodyNull -> getString(com.test.application.core.R.string.error_body_null)
                    ServerError.LoginFailed -> { getString(com.test.application.core.R.string.error_login_failed) }
                    ServerError.TokenMissing -> {getString(com.test.application.core.R.string.token_is_required)}
                    else -> { getString(com.test.application.core.R.string.error_unknown) }
                }
            }
            else -> {
                getString(com.test.application.core.R.string.error_generic)
            }
        }
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    abstract fun hideLoading()

}