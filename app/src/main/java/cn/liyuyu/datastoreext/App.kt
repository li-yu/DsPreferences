package cn.liyuyu.datastoreext

import android.app.Application
import cn.liyuyu.datastoreext.core.DsPreferences
import com.google.gson.Gson

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        DsPreferences.converter = GsonConverter(Gson())
    }
}