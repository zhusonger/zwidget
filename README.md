# AndroidZ
AndroidZ开源项目

## Base

基础库

https://github.com/zhusonger/androidz_base

```
implementation 'cn.com.lasong:base:latest.release'
```

## Widget

控件库

https://github.com/zhusonger/androidz_widget

```
implementation 'cn.com.lasong:widget:latest.release'
```

## Media

媒体库

https://github.com/zhusonger/androidz_media

```
implementation 'cn.com.lasong:media:latest.release'
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

// module的build.gradle
apply plugin: 'cn.com.lasong.inject'
```