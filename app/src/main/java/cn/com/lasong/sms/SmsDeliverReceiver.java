package cn.com.lasong.sms;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Author: zhusong
 * Email: song.zhu@lasong.com.cn
 * Date: 2020/7/28
 * Description: 短信回执广播
 */
public class SmsDeliverReceiver extends BroadcastReceiver {
    private ISmsListener listener;

    public void setListener(ISmsListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
//        ILog.e("SmsDeliverReceiver:" + intent.toString()+(null != intent.getExtras() ? ", "+intent.getExtras().toString():""));
//        Toast.makeText(context, "短信发送成功", Toast.LENGTH_SHORT).show();
        if (null != listener) {
            listener.onDeliver();
        }
    }

    public static IntentFilter createFilter() {
        return new IntentFilter("DELIVERED_SMS_ACTION");
    }

    public static PendingIntent createIntent(Context context) {
        Intent sendIntent = new Intent("DELIVERED_SMS_ACTION");
        return PendingIntent.getBroadcast(context, 0, sendIntent, 0);
    }
}
