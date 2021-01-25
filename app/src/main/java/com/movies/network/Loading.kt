package com.movies.network

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.DisplayMetrics
import androidx.appcompat.app.AlertDialog
import com.movies.R
import org.jetbrains.anko.layoutInflater
import org.jetbrains.anko.windowManager

object Loading {
    private lateinit var builder: AlertDialog.Builder
    private lateinit var loading: AlertDialog

    @SuppressLint("InflateParams")
    fun showLoading(context: Context) {
        val view = context.layoutInflater.inflate(R.layout.custom_loading, null)
        builder = AlertDialog.Builder(context)
        builder.setView(view)
        builder.setCancelable(false)
        loading = builder.create()
        loading.show()

        val displayMetrics = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val displayWidth = displayMetrics.widthPixels
        val dialogWindowWidth = (displayWidth * 0.35f).toInt()
        loading.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        loading.window?.setLayout(dialogWindowWidth, dialogWindowWidth)
    }

    fun hideLoading() {
        loading.dismiss()
    }
}