package cn.com.lasong.sms;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.widget.Toast;

import cn.com.lasong.utils.ILog;

/**
 * Author: zhusong
 * Email: song.zhu@lasong.com.cn
 * Date: 2020/7/28
 * Description: 短信发送回馈广播
 */
public class SmsSendReceiver extends BroadcastReceiver {

    private ISmsListener listener;

    public void setListener(ISmsListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
//        ILog.e("SmsSendReceiver:" + intent.toString()+(null != intent.getExtras() ? ", "+intent.getExtras().toString():""));
        String message = null;
        switch (getResultCode()) {
            case Activity.RESULT_OK:
                Toast.makeText(context, "短信发送成功", Toast.LENGTH_SHORT).show();
                break;
            case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                //常见故障(没插卡)
                ILog.d(intent.getStringExtra("destinationAddress"));
                ILog.e("SmsManager.RESULT_ERROR_GENERIC_FAILURE" + "-------" + "常见故障");
                message = "常见故障";
                break;
            case SmsManager.RESULT_ERROR_RADIO_OFF:
                //飞行模式
                ILog.e("SmsManager.RESULT_ERROR_RADIO_OFF" + "-------" + "飞行模式");
                message = "飞行模式";
                break;
            case SmsManager.RESULT_ERROR_NULL_PDU:
                //空号
                ILog.e("SmsManager.RESULT_ERROR_NULL_PDU" + "-------" + "空号");
                message = "空号";
                break;
            case SmsManager.RESULT_ERROR_NO_SERVICE:
                //无服务（没信号）
                ILog.e("SmsManager.RESULT_ERROR_NO_SERVICE" + "-------" + "无服务（没信号）");
                message = "无服务（没信号）";
                break;
            case SmsManager.RESULT_ERROR_LIMIT_EXCEEDED:
                //超过服务商规定的短信发送条数
                ILog.e("SmsManager.RESULT_ERROR_LIMIT_EXCEEDED" + "-------" + "超过服务商规定的短信发送条数");
                message = "超过服务商规定的短信发送条数";
                break;
            case 6:
                //开启FDN,即手机设定指定拨号后，只能与设置的几个有限号码通信
                ILog.e("SmsManager.RESULT_ERROR_FDN_CHECK_FAILURE" + "-------" + "开启FDN,即手机设定指定拨号后，只能与设置的几个有限号码通信");
                message = "开启FDN,即手机设定指定拨号后，只能与设置的几个有限号码通信";
                break;
            case SmsManager.RESULT_ERROR_SHORT_CODE_NOT_ALLOWED:
                //用户禁止发送短信
                ILog.e("SmsManager.RESULT_ERROR_SHORT_CODE_NOT_ALLOWED" + "-------" + "用户禁止发送短信");
                message = "用户禁止发送短信";
                break;
            case SmsManager.RESULT_ERROR_SHORT_CODE_NEVER_ALLOWED:
                //用户禁止了应用的发送短信权限
                ILog.e("SmsManager.RESULT_ERROR_SHORT_CODE_NEVER_ALLOWED" + "-------" + "用户禁止了应用的发送短信权限");
                message = "用户禁止了应用的发送短信权限";
                break;
            default:
                break;
        }

        if (null != listener) {
            listener.onSend(message);
        }
    }

    public static IntentFilter createFilter() {
        return new IntentFilter("SENT_SMS_ACTION");
    }

    public static PendingIntent createIntent(Context context) {
        Intent sendIntent = new Intent("SENT_SMS_ACTION");
        return PendingIntent.getBroadcast(context, 0, sendIntent, 0);
    }
}
