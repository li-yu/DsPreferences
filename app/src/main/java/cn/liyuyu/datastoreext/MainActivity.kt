package cn.liyuyu.datastoreext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import cn.liyuyu.datastoreext.core.DsPreferences
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        launch(Dispatchers.IO) {
            val value = DsPreferences.get("settings", "DefaultValue")
            findViewById<TextView>(R.id.tvValue).text = value
        }
    }

    fun onSaveData(view: View) {
        val text = findViewById<EditText>(R.id.editText).text.toString()
        if (text.isNotEmpty()) {
            launch(Dispatchers.IO) {
                DsPreferences.set("settings", text)
            }
        }
    }
}