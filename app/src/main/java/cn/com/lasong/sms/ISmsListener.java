package cn.com.lasong.sms;

/**
 * Author: zhusong
 * Email: song.zhu@lasong.com.cn
 * Date: 2020/7/28
 * Description:
 */
public interface ISmsListener {
    void onSend(String message);
    void onDeliver();
}
