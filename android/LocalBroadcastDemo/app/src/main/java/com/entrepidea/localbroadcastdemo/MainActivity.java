package com.entrepidea.localbroadcastdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;


/*
* this is the demo to show how to use LocalBroadcast in android, the original code is from
* https://github.com/IanDarwin/Android-Cookbook-Examples/tree/master/LocalBroadcastDemo
* it is the sample code from this site: https://androidcookbook.com/home.seam
 *LocalBroadcastManager 's document is here:
  * https://developer.android.com/reference/android/support/v4/content/LocalBroadcastManager.html
* */
public class MainActivity extends AppCompatActivity {

    private IntentFilter receiveFilter;
    private static String TAG = "localbroadcastdemo";
    private static String BTN_CLICK_EVENT = "btn_clicked";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        receiveFilter = new IntentFilter(BTN_CLICK_EVENT);
        LocalBroadcastManager.getInstance(this).registerReceiver(handler, receiveFilter);
        LocalBroadcastManager.getInstance(this).registerReceiver(handler2, receiveFilter);
    }

    private BroadcastReceiver handler = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG,"MainActivity.handler.new BroadcastReceiver() {...}.onReceive(): " + Thread.currentThread());
            Toast.makeText(MainActivity.this, "Message received", Toast.LENGTH_LONG).show();
        }
    };

    private BroadcastReceiver handler2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG,"MainActivity.handler.new BroadcastReceiver() {...}.onReceive(): " + Thread.currentThread());
            Toast.makeText(MainActivity.this, "Message received here too", Toast.LENGTH_LONG).show();
        }
    };

    /**
     * Create and dispatch an Intent via the LocalBroadcastManager.
     * Called from a Button with android:onClick="send"
     */
    public void send(View v) {
        Log.d(TAG, "MainActivity.send(): " + Thread.currentThread());
        Intent sendableIntent = new Intent(BTN_CLICK_EVENT);
        LocalBroadcastManager.getInstance(this).sendBroadcast(sendableIntent);
    }
}
