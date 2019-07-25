package com.developer.dejavu.service;

import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.app.Service;
import android.os.IBinder;
import androidx.annotation.Nullable;

import com.developer.dejavu.R;

/**
 * Sound background service to play music application wide.
 */
public class SoundService extends Service {

    private MediaPlayer mp;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mp = MediaPlayer.create(this, R.raw.gamemusic);
        mp.setLooping(true);
        mp.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.stop();
    }
}