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

## v0.0.3

添加阴影控件, 自定义背景色与阴影色, 阴影控件与实际展示内容大小一致, 不需要预留阴影的空间

```xml
<cn.com.lasong.widget.shadow.ShadowLayout
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginTop="20dp"
    app:bgColor="@android:color/white"
    app:bgRadius="10dp"
    app:shadowColor="#4D000000"
    app:shadowRadius="4dp"
    app:shadowDx="3dp"
    app:shadowDy="3dp"
    >

    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="10dp">


        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:minWidth="150dp"
            android:minHeight="40dp"
            android:text="阴影控件内容"
            android:textSize="20sp" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:minWidth="150dp"
            android:minHeight="40dp"
            android:text="阴影控件内容" />
    </LinearLayout>
</cn.com.lasong.widget.shadow.ShadowLayout>
```