package com.dongminpark.foodmarketandroid.Effects

import android.net.Uri
import java.io.File
import java.net.URLDecoder

fun convertUriToUrl(uriString: String): String? {
    try {
        val decodedUriString = URLDecoder.decode(uriString, "UTF-8")
        val uri = Uri.parse(decodedUriString)

        //val inputStream = contentResolver.openInputStream(uri)
        val path = File(uri.path).absolutePath
        val file = File(path)
        val fileUrl = file.toURI().toURL()
        return fileUrl.toString()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}