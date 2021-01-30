package cz.prasoprd.radio;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class Radio {

    private static MediaPlayer player;

    public static void setVolume(float volume) {
        if (player != null) {
            player.setVolume(volume, volume);
        }
    }

    public static void stop(View view) {
        if (player != null && player.isPlaying()) {
            if (view != null) {
                Snackbar.make(view,"Stopping player", Snackbar.LENGTH_LONG).show();
            }
            player.stop();
        }
    }

    public static void play(View view, String link) {
        new Thread(() -> {
            try {
                Snackbar.make(view,"Preparing for playing", Snackbar.LENGTH_LONG).show();
                stop(null);
                player = new MediaPlayer();
                player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                player.setDataSource(link);
                player.prepare();
                player.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
