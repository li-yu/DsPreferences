package cn.liyuyu.datastoreext

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import cn.liyuyu.datastoreext.core.DsPreferences

class MainViewModel : ViewModel() {

    var appSettings = DsPreferences.flow("settings","DefaultValue").asLiveData()
}