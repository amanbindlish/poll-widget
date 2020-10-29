package com.bindlish.pollwidget.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bindlish.pollwidget.R
import com.bindlish.pollwidget.loadJSONFromAssets
import com.bindlish.pollwidget.saveJsonToPreference

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    MainFragment.getInstance(),
                    MainFragment.TAG
                )
                .commitNow()
            if(!getSharedPreferences("poll_widget", Context.MODE_PRIVATE).contains("key_poll_data")){
                saveJsonToPreference(
                    loadJSONFromAssets(
                        this
                    ), this
                )
            }
        }
    }
}
