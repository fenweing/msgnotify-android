package com.tuanbaol.myapplication;

import android.app.Notification;
import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;

import com.tuanbaol.myapplication.bean.Message;
import com.tuanbaol.myapplication.constant.CommonConstant;
import com.tuanbaol.myapplication.constant.UrlEnum;
import com.tuanbaol.myapplication.util.AESUtils;
import com.tuanbaol.myapplication.util.Base64GarbleUtil;
import com.tuanbaol.myapplication.util.DateUtils;
import com.tuanbaol.myapplication.util.HttpUtil;
import com.tuanbaol.myapplication.util.JsonUtil;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

public class MyNotifiService extends NotificationListenerService {
    private List<Message> filterPattern = new ArrayList<>();
    private Map<String, BiFunction<MyNotifiService, Message, Message>> filterStrategyMap = new HashMap<>();

    public MyNotifiService() {
        filterStrategyMap.put("default", MyNotifiService::defaultFilter);

        addFilterPattern("选择输入法", "android");
        addFilterPattern("", "com.oneplus.dialer");
        addFilterPattern("", "com.oneplus.mms");
//        addFilterPattern("", "com.tencent.mm");
        addFilterPattern("", "com.tencent.qqmusic");
        addFilterPattern("Nextcloud", "com.nextcloud.client");
        addFilterPattern("正在上传…", "com.nextcloud.client");
    }

    private void addFilterPattern(String title, String srcPack) {
        filterPattern.add(new Message(title, srcPack));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("service", "Service is started" + "-----");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        try {
            Notification notification = sbn.getNotification();
            CharSequence tickerText = notification.tickerText;
            Object textObj = notification.extras.get("android.text");
            String text = textObj == null ? StringUtils.EMPTY : String.valueOf(textObj);
            Object titleObj = notification.extras.get("android.title");
            String title = titleObj == null ? StringUtils.EMPTY : String.valueOf(titleObj);
            Message message = new Message();
            message.setSrcPack(sbn.getPackageName());
            message.setTitle(title);
            message.setTicker(StringUtils.isBlank(tickerText) ? StringUtils.EMPTY : tickerText + StringUtils.EMPTY);
            message.setText(text);
            message.setTime(DateUtils.formatDate2ReadableStrV2(new Date()));
            if (filterMessage(message) == null) {
                return;
            }
            Log.i("encrypt message:", JsonUtil.toString(message));
            String encryptedMessage = encryptMessage(message);
            sendMessage(encryptedMessage);
        } catch (Exception e) {
            Toast.makeText(MyNotifiService.this, "不可解析的通知:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private String encryptMessage(Message msg) {
        String msgStr = JsonUtil.toString(msg);
        try {
            return Base64GarbleUtil.encode(msgStr);
        } catch (Exception e) {
            throw new RuntimeException("加密消息失败", e);
        }
    }
//    private String encryptMessage(Message msg) {
//        test();
//        String msgStr = JsonUtil.toString(msg);
//        try {
//            byte[] encrypt = AESUtils.encrypt(msgStr.getBytes(CommonConstant.CHARSET_UTF8), CommonConstant.cipher.getBytes(CommonConstant.CHARSET_UTF8));
//            return AESUtils.parseByte2HexStr(encrypt);
//        } catch (Exception e) {
//            throw new RuntimeException("加密消息失败", e);
//        }
//    }

//    private void test() {
//        byte[] encrypt = new byte[0];
//        try {
//            encrypt = AESUtils.encrypt("1234哈哈".getBytes(CommonConstant.CHARSET_UTF8), CommonConstant.cipher.getBytes(CommonConstant.CHARSET_UTF8));
//            System.out.println(AESUtils.parseByte2HexStr(encrypt));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private void sendMessage(String message) {
        new Thread(() -> {
            try {
                String url = UrlEnum.MESSAGE_SERVER.getUrl();
                String result;
                Log.i("send message", "begin,url:" + url);
                result = HttpUtil.doPost(url, message);
                Log.i("send message", "end,result:" + result);
            } catch (Throwable e) {
                Log.e("send message", "failed", e);
            }
        }).start();
    }

    private Message filterMessage(Message msg) {
        Set<Map.Entry<String, BiFunction<MyNotifiService, Message, Message>>> entries = filterStrategyMap.entrySet();
        for (Map.Entry<String, BiFunction<MyNotifiService, Message, Message>> entry : entries) {
            if (entry.getValue().apply(MyNotifiService.this, msg) == null) {
                return null;
            }
        }
        return msg;
    }

    //+++++++++++++++++++++message filter strategy start++++++++++++++++++++
    public Message defaultFilter(Message message) {
        List<Message> filterList = getFilterPattern();
        boolean shouldFilter = filterList.stream().anyMatch(f ->
                (StringUtils.isBlank(f.getTitle()) ||
                        StringUtils.equals(f.getTitle(), message.getTitle()))
                        && StringUtils.equals(f.getSrcPack(), message.getSrcPack()));
        return shouldFilter ? null : message;
    }

    //+++++++++++++++++++++message filter strategy end++++++++++++++++++++
    private List<Message> getFilterPattern() {
        return filterPattern;
    }

}

