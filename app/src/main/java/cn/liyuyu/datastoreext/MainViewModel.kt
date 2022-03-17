package cn.liyuyu.datastoreext

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import cn.liyuyu.datastoreext.core.DsPreferences

class MainViewModel(dsPreferences: DsPreferences) : ViewModel() {

    var appSettings = dsPreferences.flow("settings", "DefaultValue").asLiveData()
}