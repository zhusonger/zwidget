# Widget
常用控件集合

# 引入

```
implementation 'cn.com.lasong:widget:0.0.1'
```

## v0.0.1  

* 添加歌词控件LrcView  
使用TextureView实现歌词渲染, 提高渲染效率  
![](https://www.lasong.com.cn/assets/img/gif/lyric.gif)

* 添加拖动控件MoveView
 使用相对布局实现移动布局, 可以像普通RelativeLayout一样使用, 支持移动被MoveView包含的所有内容   
 ![](https://www.lasong.com.cn/assets/img/gif/move.gif)

## v0.0.2

添加可设置图标大小的CheckedTextView&EditText

```xml
<cn.com.lasong.widget.text.ResizeTextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:drawableLeft="@drawable/ic_widget"
    app:drawLeftWidth="32dp"
    app:drawLeftHeight="32dp"
    android:text="自定义TextView图标大小"
    android:gravity="center"
    android:drawablePadding="10dp"
    android:padding="5dp"
    android:textSize="16sp"
    />

<cn.com.lasong.widget.text.ResizeEditText
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:drawableLeft="@drawable/ic_widget"
    app:drawLeftWidth="32dp"
    app:drawLeftHeight="32dp"
    android:hint="自定义EditText图标大小"
    android:gravity="center"
    android:drawablePadding="10dp"
    android:background="@null"
    android:padding="5dp"
    android:textSize="16sp"
    />
```