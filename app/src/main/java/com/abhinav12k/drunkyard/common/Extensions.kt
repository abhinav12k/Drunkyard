package com.abhinav12k.drunkyard.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.PixelCopy
import android.view.View
import androidx.annotation.RequiresApi
import java.io.File

fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
    outputStream().use { out ->
        bitmap.compress(format, quality, out)
        out.flush()
    }
}

fun Context.openIntentChooser(msg: String, pkg: String = "", contentUri: Uri?) {
    try {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        if (pkg.isNotEmpty()) {
            sendIntent.`package` = pkg
        }
        sendIntent.putExtra(Intent.EXTRA_TEXT, msg)
        if (contentUri != null) {
            sendIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
            sendIntent.type = "image/*"
        } else {
            sendIntent.type = "text/plain"
        }
        startActivity(Intent.createChooser(sendIntent, msg))
    } catch (ex: Exception) {
        //handle exception here
    }
}

fun getScreenShotDeprecated(view: View, callback: (Bitmap) -> Unit, defaultColor: Int) {
    val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    canvas.drawColor(defaultColor)
    view.draw(canvas)
    if (bitmap != null) {
        callback(bitmap)
    }
}

// for api level 28
@RequiresApi(Build.VERSION_CODES.O)
private fun getScreenShotFromView(
    view: View,
    context: Context,
    marginTopInDp: Int = 0,
    marginBottomInDp: Int = 0,
    callback: (Bitmap) -> Unit
) {
    val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val locationOfViewInWindow = IntArray(2)
    view.getLocationInWindow(locationOfViewInWindow)
    try {
        PixelCopy.request(
            (context as Activity).window,
            Rect(
                locationOfViewInWindow[0],
                locationOfViewInWindow[1],
                locationOfViewInWindow[0] + view.width,
                locationOfViewInWindow[1] + view.height
            ),
            bitmap, { copyResult ->
                if (copyResult == PixelCopy.SUCCESS) {
                    callback(bitmap)
                }
            }, Handler(Looper.getMainLooper())
        )
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
    }
}

fun View.takeScreenshot(
    context: Context,
    marginTop: Int = 0,
    marginBottom: Int = 0,
    defaultColor: Int = Color.WHITE,
    callback: (Bitmap) -> Unit
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        getScreenShotFromView(this, context, marginTop, marginBottom, callback)
    } else {
        getScreenShotDeprecated(this, callback, defaultColor)
    }
}