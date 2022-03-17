package cn.liyuyu.datastoreext

import android.R.attr.name
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cn.liyuyu.datastoreext.core.DsPreferences


/**
 * Created by frank on 2022/3/17.
 */
class ViewModelFactory(private val dsPreferences: DsPreferences) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dsPreferences) as T
        }
        throw RuntimeException("unknown class :" + modelClass.name)
    }
}