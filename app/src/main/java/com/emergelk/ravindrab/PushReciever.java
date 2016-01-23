package com.emergelk.ravindrab;

import android.content.Context;
import android.content.Intent;

import com.parse.ParseAnalytics;
import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by prabod on 1/24/16.
 */
public class PushReciever extends ParsePushBroadcastReceiver {

    private static final String TAG = "PUSH";


    @Override
    public void onPushOpen(Context context, Intent intent) {
        ParseAnalytics.trackAppOpenedInBackground(intent);
        Intent i = new Intent(context, MainActivity.class);
        try {
            JSONObject extras = new JSONObject(intent.getExtras().getString("com.parse.Data"));
            Iterator<?> keys = extras.keys();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                //if (extras.get(key) instanceof JSONObject) {
                i.putExtra(key, extras.get(key).toString());
                //}
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

    }

    @Override
    protected void onPushReceive(Context context, Intent intent) {
        super.onPushReceive(context, intent);
    }

    @Override
    protected void onPushDismiss(Context context, Intent intent) {
        super.onPushDismiss(context, intent);
    }

}
