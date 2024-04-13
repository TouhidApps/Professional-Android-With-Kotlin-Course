package com.touhidapps.firebasetutorial

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

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

fun getFirebaseToken(context: Context, success: (String) -> Unit) {
    val TAG = "FirebaseMessaging"
    FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
        if (!task.isSuccessful) {
            Log.w(TAG, "Fetching FCM registration token failed", task.exception)
            return@OnCompleteListener
        }

        // Get new FCM registration token
        val token: String = task.result

        success(token)

        // Log and toast
      //  val msg = getString(R.string.msg_token_fmt, token)
        Log.d(TAG, token)
        Toast.makeText(context, token, Toast.LENGTH_SHORT).show()
    })
}