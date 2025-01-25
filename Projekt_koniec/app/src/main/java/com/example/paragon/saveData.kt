package com.example.paragon

import android.content.Context
import androidx.preference.PreferenceManager

fun saveData(context: Context, name: String, category: String) {
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    val editor = sharedPreferences.edit()
    editor.putString(name, category)
    editor.apply()
}

fun readData(context: Context, productName: String): String? {
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    return sharedPreferences.getString(productName, null)
}