package com.touhidapps.ndk_tutorial

import android.app.Application
import android.content.Context
import com.getkeepsafe.relinker.ReLinker

class App: Application() {

    companion object {
        var myContext: Context? = null
    }

    override fun onCreate() {
        super.onCreate()

        myContext = this

        ReLinker.loadLibrary(myContext, "ndk_tutorial")

    }

}