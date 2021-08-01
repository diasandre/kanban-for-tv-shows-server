package extensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private val gson = Gson()

fun <T> T?.toJson(): String = gson.toJson(this)

fun emptyGson(): String = Gson().toJson(null)

fun <T> T.toMap(): Map<String, Any> = gson.fromJson(this.toJson(), object : TypeToken<Map<String, Any>>() {}.type)
