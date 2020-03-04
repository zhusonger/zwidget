package cn.com.lasong;

import android.app.Activity;
import android.content.Intent;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedHashMap;
import java.util.Map;

import cn.com.lasong.base.AppManager;
import cn.com.lasong.lyric.LyricActivity;

/**
 * Author: zhusong
 * Email: song.zhu@lasong.com.cn
 * Date: 2020-03-04
 * Description:
 */
public class MainAdapter extends RecyclerView.Adapter<MainItemHolder> implements View.OnClickListener {

    private SparseArray<String> mKeys = new SparseArray<>();
    private SparseArray<Class> mValues = new SparseArray<>();

    public MainAdapter() {
        mKeys.append(0, "歌词控件");
        mValues.append(0, LyricActivity.class);
    }

    @NonNull
    @Override
    public MainItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new MainItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainItemHolder holder, int position) {
        TextView textView = (TextView) holder.itemView;
        String text = mKeys.get(position);
        textView.setText(text);
        textView.setTag(position);
        textView.setOnClickListener(this);

    }

    @Override
    public int getItemCount() {
        return mKeys.size();
    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        if (null == tag) {
            return;
        }
        int position = (int) tag;
        Class cls = mValues.get(position);
        Activity activity = AppManager.getInstance().current();
        if (null != activity) {
            Intent intent = new Intent(activity, cls);
            activity.startActivity(intent);
        }
    }
}
