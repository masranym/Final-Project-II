package masran.myapplication;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.EditText;

import java.util.Calendar;

public class Alarm extends BroadcastReceiver {
    private String text;

    @Override
    public void onReceive(Context context, Intent intent) {
        //TODO add title
        String name = intent.getStringExtra("name");
        String title = intent.getStringExtra("title");

                crateNotificiation(context, "อย่าลืมทานยา"+name, "ถึงเวลาทานยาแล้ว", "Alert");


    }

    public void crateNotificiation(Context context, String msg, String msfText, String msgAlert) {

        PendingIntent notificIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, Remind.class), 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.b)
                .setContentTitle(msg)
                .setTicker(msgAlert)
                .setContentText(msfText);
        mBuilder.setContentIntent(notificIntent);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationmanager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationmanager.notify(1, mBuilder.build());


    }

    public void setText(String text) {
        this.text = text;

    }
}
