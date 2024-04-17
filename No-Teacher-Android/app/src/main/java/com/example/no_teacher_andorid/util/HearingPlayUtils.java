package com.example.no_teacher_andorid.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.TextUtils;

import com.zhilehuo.advenglish.base.BaseApplication;

import java.io.File;

public class HearingPlayUtils {
    private static HearingPlayUtils instance = new HearingPlayUtils();
    private static MediaPlayer player;

    public static HearingPlayUtils getInstance() {
        return instance;
    }


    public void playMp3(int raw) {
        if (player != null) {
            if (player.isPlaying())
                player.stop();
            player.release();
            player = null;

        }
        player = MediaPlayer.create(BaseApplication.mInstance.getApplicationContext(), raw);
        if (player != null){
            player.start();
        }
    }

    public void playOnline(String fileUrl) {
        playOnline(fileUrl, null);
    }

    public void playOnline(String fileUrl, MediaPlayer.OnCompletionListener onCompletionListener) {
        if (TextUtils.isEmpty(fileUrl)){
            return;
        }
//        fileUrl = checkCache(fileUrl);
        try {
            if (player == null) {
                player = new MediaPlayer();
            } else {
                if (player.isPlaying()) {
                    player.stop();
                }
            }
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.reset();
            player.setDataSource(fileUrl);
            player.prepareAsync();
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    player.start();
                }
            });
            player.setOnCompletionListener(onCompletionListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playOnline(Context context, int wav, MediaPlayer.OnCompletionListener onCompletionListener) {
        try {
            if (player == null) {
                player = new MediaPlayer();
            } else {
                if (player.isPlaying()) {
                    player.stop();
                }
            }
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.reset();
            player.setDataSource(BaseApplication.mInstance.getApplicationContext()
                    , Uri.parse("android.resource://"+ context.getPackageName()+"/"+wav));
            player.prepareAsync();
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    player.start();
                }
            });
            player.setOnCompletionListener(onCompletionListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 是否已缓存在data/data/packaname/cache目录下
     * @param fileName
     * @return
     */
    private String checkCache(String fileName){
        if (fileName.startsWith("http")){
            int index = fileName.lastIndexOf("/");
            String name = fileName.substring(index + 1);
            if (!TextUtils.isEmpty(name)){
                String cacheName = BaseApplication.mInstance.getCacheDir().getPath() + File.separator + name;
                if (isExistsInCache(cacheName)){
                    return cacheName;
                }
            }
        }
        return fileName;
    }

    public void stop() {
        if (player != null) {
            try {
                if (player.isPlaying()) {
                    player.stop();
                    player.release();
                    player = null;
                }
            } catch (Exception e){}

        }

    }

    public MediaPlayer getPlayer() {
        return player;
    }

    //fileName 为文件名称 返回true为存在
    public boolean isExistsInCache(String fileName) {
        try {
            File f=new File(fileName);
            if(f.exists()) {
                android.util.Log.d("===wpt===", "有这个文件");
                return true;
            }else{
                android.util.Log.d("===wpt===", "没有这个文件");
                return false;
            }
        } catch (Exception e) {
            android.util.Log.d("===wpt===", "有异常");
            return false;
        }
    }
}
