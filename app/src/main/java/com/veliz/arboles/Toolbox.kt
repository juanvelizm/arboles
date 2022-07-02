package com.veliz.arboles

import android.app.Activity
import java.io.IOException
import java.nio.charset.StandardCharsets


object Toolbox {
    fun loadJsonFromAssets(activity: Activity, filename: String): String? {
        var json: String? = ""
        try {
            val `is` = activity.assets.open("$filename")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer, StandardCharsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }

        return json
    }
}