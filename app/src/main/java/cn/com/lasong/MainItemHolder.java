package cn.com.lasong;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author: zhusong
 * Email: song.zhu@lasong.com.cn
 * Date: 2020-03-04
 * Description:
 */
public class MainItemHolder extends RecyclerView.ViewHolder {
    protected ImageView mIvIcon;
    protected TextView mTvName;
    public MainItemHolder(@NonNull View itemView) {
        super(itemView);
        mIvIcon = itemView.findViewById(R.id.iv_icon);
        mTvName = itemView.findViewById(R.id.tv_name);
    }

}
