package com.touhidapps.fragment

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun addFragment(activity: FragmentActivity?, bundle: Bundle?, fragment: Fragment) {

    val className: String = "${fragment.javaClass.simpleName}"

    bundle?.let {
        fragment.arguments = bundle
    }
    activity?.supportFragmentManager
        ?.beginTransaction()
        ?.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
        ?.addToBackStack(className)
        ?.add(R.id.fragmentContainer, fragment, className)
        ?.commit()

}