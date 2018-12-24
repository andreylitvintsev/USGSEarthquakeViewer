package com.github.andreylitvintsev.usgsearthquakeviewer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.github.andreylitvintsev.usgsearthquakeviewer.ui.MapFragment


// TODO: добавить информацию как добавлять карту
// TODO: попробовать вынести правки в .md (https://www.jetbrains.com/help/idea/markdown.html)
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (supportFragmentManager.findFragmentById(android.R.id.content) == null) {
            supportFragmentManager.beginTransaction().add(android.R.id.content,
                MapFragment()
            ).commit()
        }
    }

}
