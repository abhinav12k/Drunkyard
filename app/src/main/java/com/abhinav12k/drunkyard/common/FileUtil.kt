package com.abhinav12k.drunkyard.common

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import com.abhinav12k.drunkyard.BuildConfig
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

object FileUtil {

    fun storeScreenShot(image: Bitmap, context: Context, fileName: String = Calendar.getInstance().timeInMillis.toString()): Uri? {
        val imagesFolder = File(context.cacheDir, "images")
        var uri: Uri? = null
        try {
            imagesFolder.mkdirs()
            val file =
                File(imagesFolder, "${fileName}.png")

            val stream = FileOutputStream(file)
            image.compress(Bitmap.CompressFormat.PNG, 90, stream)
            stream.flush()
            stream.close()
            uri = FileProvider.getUriForFile(context, BuildConfig.FILES_AUTHORITY, file)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return uri
    }
}