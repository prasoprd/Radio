package cz.prasoprd.radio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        TextView textView = (TextView) findViewById(R.id.textView);
        TextView textView2 = (TextView) findViewById(R.id.textView2);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10);
                        textView.post(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText(DateTimeFormatter.ofPattern("h:mm:ss a - EEEE - d/M/yyyy").format(LocalDateTime.now()));
                            }
                        });
                        textView2.post(new Runnable() {
                            @Override
                            public void run() {
                                textView2.setText("Volume: " + seekBar.getProgress());
                            }
                        });
                        Radio.setVolume(seekBar.getProgress() / 100F);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        ((Button) findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Radio.play(v,"https://streams.ilovemusic.de/iloveradio1.mp3");
                createNotification("I ♥ Radio");
            }
        });

        ((Button) findViewById(R.id.button2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Radio.play(v,"https://streams.ilovemusic.de/iloveradio2.mp3");
                createNotification("I ♥ Dance");
            }
        });

        ((Button) findViewById(R.id.button3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Radio.play(v,"https://streams.ilovemusic.de/iloveradio3.mp3");
                createNotification("I ♥ HipHop");
            }
        });

        ((Button) findViewById(R.id.button4)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Radio.play(v,"https://streams.ilovemusic.de/iloveradio5.mp3");
                createNotification("I ♥ Mashup");
            }
        });

        ((Button) findViewById(R.id.button6)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Radio.play(v,"https://stream.laut.fm/cokeradio");
                createNotification("Coke radio");
            }
        });

        ((Button) findViewById(R.id.button7)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Radio.stop(v);
                removeNotification();
            }
        });
    }

    private void createNotification(String radio) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("prasoprd","prasoprd's apps", NotificationManager.IMPORTANCE_LOW);
            channel.setDescription("For applications created by prasoprd");
            getSystemService(NotificationManager.class).createNotificationChannel(channel);
        }
        NotificationManagerCompat.from(this).notify(1337, new NotificationCompat.Builder(this,"prasoprd").setOngoing(true).setSmallIcon(R.mipmap.ic_launcher_foreground).setContentTitle("Playing").setContentText(radio).setPriority(NotificationCompat.PRIORITY_LOW).build());
    }

    private void removeNotification() {
        NotificationManagerCompat.from(this).cancel(1337);
    }
}
