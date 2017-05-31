package com.aands.wefamily.Chat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.aands.wefamily.Family.Family;
import com.aands.wefamily.Family.Messages;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by LemonNoel on 2017/5/31.
 */

public class ReceiverBC extends BroadcastReceiver {
    List<Family> familyList = new ArrayList<>();

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage msg = null;
        familyList = DataSupport.findAll(Family.class);
        if (null != bundle) {
            Object[] smsObj = (Object[]) bundle.get("pdus");
            for (Object object : smsObj) {
                msg = SmsMessage.createFromPdu((byte[]) object);
                Date date = new Date(msg.getTimestampMillis());//时间
                SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss");
                String receiveTime = format.format(date);
                System.out.println("number:" + msg.getOriginatingAddress()
                        + "   body:" + msg.getDisplayMessageBody() + "  time:"
                        + msg.getTimestampMillis());

                for (Family person: familyList) {
                    if (msg.getOriginatingAddress().contains(person.getNumber())){
                        person.addMessage(new Messages(receiveTime, msg.getDisplayMessageBody(), Messages.TYPE_RECEIVED, true));
                        person.save();
                        break;
                    }
                }
            }
        }
    }
}
