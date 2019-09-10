package com.naxions.www.wechathelper;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**循环播放一段无声音频，以提升进程优先级*/
public class PlayerMusicService extends Service {
    private final static String TAG = "PlayerMusicService";
    private MediaPlayer mMediaPlayer;
 
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
 
    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.raw);
        mMediaPlayer.setLooping(true);
    }
 
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                startPlayMusic();
            }
        }).start();
        return START_STICKY;
    }
 
    private void startPlayMusic(){
        if(mMediaPlayer != null){
            mMediaPlayer.start();
        }
    }
 
    private void stopPlayMusic(){
        if(mMediaPlayer != null){
            mMediaPlayer.stop();

        }
    }
 
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopPlayMusic();
             System.out.println(TAG+"---->onCreate,停止服务");
        // 重启
        Intent intent = new Intent(getApplicationContext(),PlayerMusicService.class);
        startService(intent);
    }
}


