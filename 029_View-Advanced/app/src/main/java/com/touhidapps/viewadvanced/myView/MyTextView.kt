package com.touhidapps.viewadvanced.myView

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.textview.MaterialTextView
import com.touhidapps.viewadvanced.R

class MyTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
    MaterialTextView(context, attrs, defStyle) {

    init {
        // Your code
        setMyFont()
        setBackgroundColor(Color.RED)
    }

    private fun setMyFont() {
        val tf = ResourcesCompat.getFont(context, R.font.caveat_bold)
        typeface = tf
    }


}