package com.example.knowledgetree

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle


class MyActivity : Activity() {
    var prefs: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Perhaps set content view here
        prefs = getSharedPreferences("com.mycompany.myAppName", MODE_PRIVATE)
    }

    override fun onResume() {
        super.onResume()
        if (prefs!!.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
            prefs!!.edit().putBoolean("firstrun", false).apply()
        }
    }
}