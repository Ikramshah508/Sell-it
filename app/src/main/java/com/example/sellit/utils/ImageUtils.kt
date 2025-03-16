package com.example.sellit.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream

// First, create or update your ImageUtils class
object ImageUtils {
    fun compressAndGetBase64(context: Context, uri: Uri): String {
        val inputStream = context.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)

        // Calculate scale factor to reduce image size while maintaining aspect ratio
        val maxSize = 800 // max width or height
        val scale = Math.min(
            maxSize.toFloat() / bitmap.width,
            maxSize.toFloat() / bitmap.height
        )

        val scaledBitmap = Bitmap.createScaledBitmap(
            bitmap,
            (bitmap.width * scale).toInt(),
            (bitmap.height * scale).toInt(),
            true
        )

        // Compress to JPEG with quality that ensures size < 100KB
        var quality = 80
        val baos = ByteArrayOutputStream()
        do {
            baos.reset()
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos)
            quality -= 10
        } while (baos.toByteArray().size > 100 * 1024 && quality > 10) // 100KB limit

        return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)
    }

    fun base64ToBitmap(base64String: String): Bitmap? {
        return try {
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (e: Exception) {
            null
        }
    }
}