package cn.liyuyu.datastoreext

import android.app.Application
import cn.liyuyu.datastoreext.core.DsPreferences

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        DsPreferences.init(this)
    }
}