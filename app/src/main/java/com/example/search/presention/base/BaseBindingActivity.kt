package com.example.search.presention.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.search.presention.utils.BackPressUtil
import io.reactivex.disposables.CompositeDisposable

abstract class BaseBindingActivity<T : ViewDataBinding> : AppCompatActivity() {

    private var _binding: T? = null
    protected val binding get() = _binding!!

    private val compositeDisposable = CompositeDisposable()

    private var backPressHandler: BackPressUtil? = null

    abstract fun initData()

    abstract fun getLayoutRes(): Int

    abstract fun initView(viewDataBinding: T)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        _binding = DataBindingUtil.setContentView(this, getLayoutRes())
        binding.lifecycleOwner = this
        backPressHandler = BackPressUtil(this)
        initView(binding)
    }

    protected fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        backPressHandler?.onBackPressed()
    }
}