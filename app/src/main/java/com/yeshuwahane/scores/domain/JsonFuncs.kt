package com.yeshuwahane.scores.domain

import android.content.Context
import org.json.JSONArray
import java.io.InputStream


fun loadJSONFromAsset(context: Context, fileName: String): String? {
    val inputStream: InputStream = context.resources.openRawResource(
        context.resources.getIdentifier(fileName, "raw", context.packageName)
    )
    return inputStream.bufferedReader().use { it.readText() }
}



