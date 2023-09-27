package com.touhidapps.drivingtestquiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun addFragment(fragment: Fragment, activity: FragmentActivity?, bundle: Bundle? = null) {

    val fName = "${fragment::class.simpleName}"

    val f : Fragment? = activity?.supportFragmentManager?.findFragmentByTag(fName)

    if (f != null) {
        if (f.isAdded) {
            return
        }
    }

    bundle?.let {
        fragment.arguments = it
    }

    activity?.supportFragmentManager
        ?.beginTransaction()
        ?.replace(R.id.fragmentContainer, fragment, fName)
        ?.commit()

}