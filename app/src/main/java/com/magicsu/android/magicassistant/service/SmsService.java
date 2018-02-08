package com.magicsu.android.magicassistant.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.telephony.SmsMessage;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.magicsu.android.magicassistant.R;
import com.magicsu.android.magicassistant.util.Constant;
import com.magicsu.android.magicassistant.util.L;
import com.magicsu.android.magicassistant.util.SP;
import com.magicsu.android.magicassistant.view.DispatchLinearLayout;

public class SmsService extends Service {
    private SmsReceiver mSmsReceiver;
    private String mSmsPhone;
    private String mSmsContent;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        L.i("service start");
        SP.putBoolean(this, Constant.SETTING_SMS, true);
        return super.onStartCommand(intent, flags, startId);
    }

    private void init() {
        L.i("service create");
        mSmsReceiver = new SmsReceiver();
        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        intentFilter.setPriority(Integer.MAX_VALUE);
        registerReceiver(mSmsReceiver, intentFilter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.i("service stop");
        SP.putBoolean(this, Constant.SETTING_SMS, false);
        unregisterReceiver(mSmsReceiver);
    }

    private void showWindow() {
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        layoutParams.format = PixelFormat.TRANSLUCENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }

        DispatchLinearLayout view = (DispatchLinearLayout) LayoutInflater.from(this).inflate(R.layout.item_sms, null, false);
        TextView smsFrom = view.findViewById(R.id.text_view_sms_from);
        TextView smsContent = view.findViewById(R.id.text_view_sms_content);
        Button smsReplyButton = view.findViewById(R.id.button_sms_reply);
        smsFrom.setText("发件人:" + mSmsPhone);
        smsContent.setText("内容：" + mSmsContent);
        smsReplyButton.setOnClickListener(v -> {
            sendSms();
            if (windowManager != null) {
                windowManager.removeView(view);
            }
        });

        if (windowManager != null) {
            windowManager.addView(view, layoutParams);
        }
        view.setDispatchKeyEventListener(event -> {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                if (windowManager != null) windowManager.removeView(view);
                return true;
            }
            return false;
        });
    }

    private void sendSms() {
        Uri uri = Uri.parse("smsto:" + mSmsPhone);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("sms_body", "");
        startActivity(intent);
    }

    public class SmsReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.provider.Telephony.SMS_RECEIVED".equals(action)) {
                Object[] objects = (Object[]) intent.getExtras().get("pdus");
                for (Object obj : objects) {
                    SmsMessage sms = SmsMessage.createFromPdu((byte[]) obj);
                    mSmsPhone = sms.getOriginatingAddress();
                    mSmsContent = sms.getMessageBody();
                    // 这里要做权限确认，否则会crush
                    showWindow();
                }
            }
        }
    }
}
