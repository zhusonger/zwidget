# AndroidZ
AndroidZ开源项目

## Base

基础库

https://github.com/zhusonger/androidz_base

```
implementation 'cn.com.lasong:base:0.0.2'
```

## Widget

控件库

https://github.com/zhusonger/androidz_widget

```
implementation 'cn.com.lasong:widget:0.0.1'
```

## Media

媒体库

https://github.com/zhusonger/androidz_media

```
implementation 'cn.com.lasong:media:0.0.4'
```

## Plugin

插件库

https://github.com/zhusonger/androidz_plugin


```
// 根目录build.gradle
buildscript {
    dependencies {
        // 1.添加classpath
        classpath "cn.com.lasong:plugin:1.0.0"
    }
}
allprojects {
    // 2. 所有模块都添加插件/单个模块应用插件
    apply plugin: 'cn.com.lasong.inject'
    repositories {
        google()
        jcenter()
    }
}
```