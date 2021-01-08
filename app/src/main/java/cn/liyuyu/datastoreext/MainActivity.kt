package cn.liyuyu.datastoreext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    private val MY_SETTING = preferencesKey<String>("Name")
    private val dataStore by lazy {
        createDataStore(name = "settings")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mySettingFlow = dataStore.data.map {
            it[MY_SETTING] ?: "DefaultValue"
        }

        launch(Dispatchers.IO){
            mySettingFlow.collect {
                runOnUiThread {
                    findViewById<TextView>(R.id.tvValue).text = it
                }
            }
        }

    }

    fun onSaveData(view: View) {
        val text = findViewById<EditText>(R.id.editText).text.toString()
        if (text.isNotEmpty()) {
            launch(Dispatchers.IO) {
                dataStore.edit {
                    it[MY_SETTING] = text
                }
            }
        }
    }
}