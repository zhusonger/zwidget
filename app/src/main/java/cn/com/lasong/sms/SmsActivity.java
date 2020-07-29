package cn.com.lasong.sms;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Locale;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import cn.com.lasong.R;
import cn.com.lasong.base.BaseActivity;
import cn.com.lasong.utils.FileUtils;
import cn.com.lasong.utils.ILog;
import cn.com.lasong.utils.TN;

/**
 * Author: zhusong
 * Email: song.zhu@lasong.com.cn
 * Date: 2020/7/28
 * Description: 短信群发
 */
public class SmsActivity extends BaseActivity implements View.OnClickListener, ISmsListener {

    private EditText mEdtContent;
    private Button mBtnSend;
    private NumberPicker mNpNum;

    private SmsSendReceiver mSendReceiver;
    private SmsDeliverReceiver mDeliverReceiver;
    private AppCompatCheckBox mCbLoad;

    private TextView mTvSend;
    private TextView mTvDeliver;
    private TextView mTvDeliverRatio;

    private int mAllPhoneNum;
    private int mOnceNum = 100;
    private int mAllSendNum = 0;
    private int mAllDeliverNum = 0;

    private Queue<String> mPhoneQueue = new LinkedBlockingQueue<>();

    public final int REQUEST_CODE_DOC = 111;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        mCbLoad = findViewById(R.id.cb_load);
        mEdtContent = findViewById(R.id.edt_content);
        mBtnSend = findViewById(R.id.btn_send);



        mTvSend = findViewById(R.id.tv_send);
        mTvDeliver = findViewById(R.id.tv_deliver);
        mTvDeliverRatio = findViewById(R.id.tv_deliver_ratio);

        mNpNum = findViewById(R.id.np_num);
        mNpNum.setMinValue(1);
        mNpNum.setMaxValue(200);
        mNpNum.setValue(mOnceNum);
        mNpNum.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int previous, int current) {
                mOnceNum = current;
            }
        });

        mCbLoad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mBtnSend.setText("加载");
                } else {
                    mBtnSend.setText("发送");
                }
            }
        });
        //注册广播
        mSendReceiver = new SmsSendReceiver();
        registerReceiver(mSendReceiver, SmsSendReceiver.createFilter());
        mDeliverReceiver = new SmsDeliverReceiver();
        registerReceiver(mDeliverReceiver, SmsDeliverReceiver.createFilter());

        mSendReceiver.setListener(this);
        mDeliverReceiver.setListener(this);
        mBtnSend.setOnClickListener(this);

        mBtnSend.setText("加载");
        ILog.setLogLevel(Log.DEBUG);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mSendReceiver);
        unregisterReceiver(mDeliverReceiver);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_DOC:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    Uri uri = data.getData();
                    try {
                        String path = FileUtils.getFilePath(this, uri);
                        BufferedReader reader = new BufferedReader(new FileReader(path));

                        boolean checked = false;
                        String line;
                        while ((line = reader.readLine()) != null) {
                            if (!checked) {
                                if(line.length() != 11) {
                                    break;
                                }
                                checked = true;
                            }
                            mPhoneQueue.offer(line);
                        }

                        if (!mPhoneQueue.isEmpty()) {
                            mAllPhoneNum = mPhoneQueue.size();
                            mTvSend.setText(String.format(Locale.CHINA, "发送成功(0/%d)", mAllPhoneNum));
                            mTvDeliver.setText(String.format(Locale.CHINA, "送达成功(0/%d)", mAllPhoneNum));
                            TN.show("加载成功");
                            mCbLoad.setChecked(false);
                        } else {
                            TN.show("不符合要求的文件");
                        }

                        reader.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                        TN.showError("文件读取失败");
                    }
                }

                mBtnSend.setEnabled(true);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (!requestAllPermissions(Manifest.permission.SEND_SMS, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            return;
        }
        if (v == mBtnSend) {
            if (mCbLoad.isChecked()) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*").addCategory(Intent.CATEGORY_OPENABLE);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                try {
                    startActivityForResult(Intent.createChooser(intent, "Choose File"), REQUEST_CODE_DOC);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, "亲，木有文件管理器啊-_-!!", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            String content = mEdtContent.getText().toString();
            if (TextUtils.isEmpty(content) || TextUtils.isEmpty(content.trim())) {
                TN.show("短信内容不能为空");
                return;
            }

            if (mPhoneQueue.isEmpty()) {
                TN.show("没有有效的电话号码");
                return;
            }

            mBtnSend.setEnabled(false);
            SmsManager manager = SmsManager.getDefault();
            int sendNum = 0;
            while (!mPhoneQueue.isEmpty() && sendNum < mOnceNum) {
                String phone = mPhoneQueue.poll();
                manager.sendTextMessage(phone, null, content,
                        SmsSendReceiver.createIntent(getApplicationContext()), SmsDeliverReceiver.createIntent(getApplicationContext()));
                sendNum++;
            }
            mBtnSend.setEnabled(true);
        }
    }

    @Override
    public void onSend(String message) {
        // 发送成功
        if (TextUtils.isEmpty(message)) {
            mAllSendNum++;
            mTvSend.setText(String.format(Locale.CHINA, "发送成功(%d/%d)", mAllSendNum, mAllPhoneNum));
        }
        // 发送失败
        else {
            TN.showError(message);
        }
    }

    @Override
    public void onDeliver() {
        // 发送到目标用户
        mAllDeliverNum++;
        mTvDeliver.setText(String.format(Locale.CHINA, "送达成功(%d/%d)", mAllDeliverNum, mAllPhoneNum));
        mTvDeliverRatio.setText(String.format(Locale.CHINA, "送达率(%.1f%%)", (mAllDeliverNum * 100.0f / mAllSendNum)));
    }
}