package pk.codebase.notificationsexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static pk.codebase.notificationsexample.App.CHANNEL_1_ID;
import static pk.codebase.notificationsexample.App.CHANNEL_2_ID;

public class MainActivity extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;
    private EditText editTextTitle;
    private EditText editTextMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = NotificationManagerCompat.from(this);
        editTextTitle = findViewById(R.id.edit_text_title);
        editTextMessage = findViewById(R.id.edit_text_message);
        Button btnChannel1 = findViewById(R.id.btn_channel1);
        Button btnChannel2 = findViewById(R.id.btn_channel2);
        btnChannel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTextTitle.getText().toString();
                String message = editTextMessage.getText().toString();
                Intent activityIntent = new Intent(MainActivity.this,MainActivity.class);
                PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this,
                        0,activityIntent,0);

                Intent broadcastIntent = new Intent(MainActivity.this,NotificationReceiver.class);
                broadcastIntent.putExtra("toastMessage",message);
                PendingIntent actionIntent = PendingIntent.getBroadcast(MainActivity.this,0,broadcastIntent,PendingIntent.FLAG_UPDATE_CURRENT);

                Notification notification = new NotificationCompat.Builder(MainActivity.this,CHANNEL_1_ID)
                        .setSmallIcon(R.drawable.ic_one)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .setColor(Color.BLUE)
                        .setContentIntent(contentIntent)
                        .setAutoCancel(true)
                        .setOnlyAlertOnce(true)
                        .addAction(R.mipmap.ic_launcher,"Toast",actionIntent)
                        .build();
                notificationManager.notify(1,notification);
            }
        });
        btnChannel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTextTitle.getText().toString();
                String message = editTextMessage.getText().toString();
                Notification notification = new NotificationCompat.Builder(MainActivity.this,CHANNEL_2_ID)
                        .setSmallIcon(R.drawable.ic_two)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setPriority(NotificationCompat.PRIORITY_LOW)
                        .build();
                notificationManager.notify(2,notification);
            }
        });
    }
}