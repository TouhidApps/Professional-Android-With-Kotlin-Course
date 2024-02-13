package com.touhidapps.firebasetutorial

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun addFragment(

    activity: FragmentActivity?,
    fragment: Fragment,
    bundle: Bundle? = null,
    isBackStack: Boolean = false,
    isReplace: Boolean = false

) {

    fragment.arguments = bundle

    val sn = "${fragment.javaClass.simpleName}"

    activity?.supportFragmentManager?.beginTransaction()?.apply {

        if (isBackStack) {
            addToBackStack(sn)
        }
        if (isReplace) {
            replace(R.id.containerView, fragment, sn)
        } else {
            add(R.id.containerView, fragment, sn)
        }

    }?.commit()

}