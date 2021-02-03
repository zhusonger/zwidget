package cn.com.lasong.app;

import android.view.View;
import android.view.ViewGroup;

import cn.com.lasong.base.BaseActivity;
import cn.com.lasong.widget.utils.ViewHelper;

/**
 * Author: zhusong
 * Email: song.zhu@lasong.com.cn
 * Date: 2021/2/3
 * Description: 应用activity基类
 */
public class AppBaseActivity extends BaseActivity {

    /**
     * 适配状态栏类型枚举
     */
    protected enum FitStatusBarType {
        NONE, // 没有适配
        PADDING, // 以padding适配
        MARGIN  // 以margin适配
    }

    /**
     * 设置完布局后的回调
     */
    @Override
    public void onContentChanged() {
        super.onContentChanged();
        // 透明化状态栏
        if (transparentStatusBar()) {
            ViewHelper.transparentStatusBar(this);
        }
        // 适配状态栏
        if (transparentStatusBar() && fitStatusBarType() != FitStatusBarType.NONE) {
            View content = findViewById(android.R.id.content);
            if (content instanceof ViewGroup) {
                int resId = fitStatusBarResId();
                View appContent;
                if (resId == View.NO_ID) {
                    appContent = ((ViewGroup) content).getChildAt(0);
                } else {
                    appContent = ((ViewGroup) content).findViewById(resId);
                }
                ViewHelper.fitStatusBar(appContent, fitStatusBarType() == FitStatusBarType.PADDING);
            }
        }
    }

    /**
     * 适配透明化状态栏的方式
     * 默认使用PADDING方式
     * @return
     */
    protected FitStatusBarType fitStatusBarType() {
        return FitStatusBarType.PADDING;
    }

    /**
     * 返回 需要适配透明化状态栏的控件ID
     * NO_ID 代表当前activity布局的ROOT控件
     * @return
     */
    protected int fitStatusBarResId() {
        return View.NO_ID;
    }

    /**
     * 是否透明化状态栏
     * @return
     */
    protected boolean transparentStatusBar() {
        return true;
    }




}
