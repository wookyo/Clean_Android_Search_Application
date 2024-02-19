package com.example.search.presention.utils

import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

object ActivityUtils {

    @JvmStatic
    fun addFragment(fragmentManager: FragmentManager,
                    fragment: Fragment, frameId: Int, tag: String,
                    @AnimatorRes @AnimRes enter: Int, @AnimatorRes @AnimRes exit: Int,
                    @AnimatorRes @AnimRes popEnter: Int, @AnimatorRes @AnimRes popExit: Int) {
        val transaction = fragmentManager.beginTransaction()
        removeExistFragment(fragmentManager, transaction, tag)
        transaction.setCustomAnimations(enter, exit, popEnter, popExit)
        transaction.add(frameId, fragment, tag)
        transaction.commit()
    }

    @JvmStatic
    fun replaceFragment(fragmentManager: FragmentManager,
                        fragment: Fragment, frameId: Int, tag: String?) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(frameId, fragment, tag)
        transaction.commitAllowingStateLoss()
    }

    @JvmStatic
    private fun removeExistFragment(fragmentManager: FragmentManager,
                                    transaction: FragmentTransaction, tag: String) {
        val existFragment = fragmentManager.findFragmentByTag(tag)
        if (existFragment != null) {
            transaction.remove(existFragment)
        }
    }
}