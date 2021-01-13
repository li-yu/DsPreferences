[![](https://jitpack.io/v/li-yu/Datastoreext.svg)](https://jitpack.io/#li-yu/Datastoreext)

# DsPreferences

> [Jetpack DataStore](https://mp.weixin.qq.com/s/26Uxotf3-oceKUbrujqX3w)，这是一个经过改进的全新数据存储解决方案，旨在替代原有的 SharedPreferences。Jetpack DataStore 基于 Kotlin 协程和 Flow 开发，并提供两种不同的实现: Proto DataStore 和 Preferences DataStore。

这几天抽空学习了下基本用法，基于 Preferences DataStore 实现了一个简单的数据存储扩展，还是熟悉的样子，还是熟悉的配方，这就是全新 **DsPreferences**。

## 添加依赖
1. 添加 jitpack 源到工程 build.gradle ：
```
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

2. 添加 DsPreferences 依赖：
```
implementation 'com.github.li-yu:DataStoreExt:1.0.0'
```

## 使用
目前支持基本数据类型：Int, Long, Boolean, Float, Double, String 以及自定义 Converter 实现复杂实体类型的存取。

#### 初始化！！！
务必一定初始化！！！如果只是基本数据类型的存取，用 `DsPreferences.init(this)` 初始化即可，当然也可选支持自定义文件名和 Converter。
``` kotlin
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        DsPreferences.init(this)
    }
}
```

#### 1. 传统用法
与原先的 SharedPreferences 主流包装用法没有差异，只是要注意所有读写操作要放在协程里。
``` kotlin
// 写
DsPreferences.set("name", "frank")  // 字符串类型
DsPreferences.set("age", 18)        // 数字类型     
DsPreferences.set("foo", foo)       // 自定义实体类型

// 读
val foo = DsPreferences.get("name", "default")
val age = DsPreferences.get("age", -1) 
val foo = DsPreferences.get<Foo>("foo") // 如果读取失败则返回 null
```

#### 2. ViewModel + LiveData 用法
官方推荐的用法，不过实际使用场景可能不一样，下面是一个简单的示例：
``` kotlin
class MainViewModel : ViewModel() {

    // Flow 通过扩展函数转为 LiveData
    var appSettings = DsPreferences.flow("settings","DefaultValue").asLiveData()
}

...
// 观察 LiveData 
viewModel.appSettings.observe(this, { value ->
    showValue(value)
})
```

#### 3. 自定义 Converter 实现复杂类型用法
和 SharedPreferences 一样，DataStore 同样不推荐存储复杂的大数据，但是聊胜于无。
``` kotlin
interface Converter {

    // 系列化为字符串
    fun serialize(value: Any): String

    // 反序列化为对象
    fun <T> deserialize(serialized: String, clazz: Class<T>): T?
}
```
下面是一个简单的 `GsonConverter` 实现供参考：
``` kotlin
class GsonConverter(val gson: Gson) : Converter {
    override fun serialize(value: Any): String {
        return gson.toJson(value)
    }

    override fun <T> deserialize(serialized: String, clazz: Class<T>): T? {
        return gson.fromJson(serialized, clazz)
    }
}
```
最后记得在 DsPreferences 初始化时传入自定义的 Converter：
```
DsPreferences.init(this, converter = GsonConverter(Gson()))
```

然后就可以使用了：

``` kotlin
DsPreferences.set("foo", foo) 
...
val foo = DsPreferences.get<Foo>("foo")
```

## 未完成的功能

- [ ] Proto DataStore

## License

[Apache License Version 2.0](https://github.com/li-yu/DataStoreExt/blob/master/LICENSE)