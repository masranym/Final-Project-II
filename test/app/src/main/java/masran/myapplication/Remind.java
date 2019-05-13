package masran.myapplication;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

import butterknife.OnClick;
import masran.myapplication.database.Contact;

/**
 * Created by Mas'Z on 3/21/2018.
 */

public class Remind extends AppCompatActivity {
    private static final String TAG = "Remind";
    Notification.Builder notification;
    final int id=11;
    EditText edit;
    EditText tm;
    Spinner spinner;
    private String text;
    String free=" ";
    private AlarmManager alarm;
    int timea=1000;
    int timeb=5;

    EditText chooseTime;
    TimePickerDialog timePickerDialog;
    Calendar calendar;
    int currenH;
    int currenM;
    String amPm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remind);
        edit = (EditText) findViewById(R.id.edittext);
        spinner =(Spinner)findViewById(R.id.sptime);

        FloatingActionButton adremind = (FloatingActionButton)findViewById(R.id.addalarm);
        adremind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(Remind.this);
                dialog.setContentView(R.layout.alarm_box);
                dialog.show();


                Log.d(TAG, "onLongClick: 5555555555555555555555555555555555555555555555555555" );
            }
        });

 //------------------------------------------------------------
        chooseTime = findViewById(R.id.etChooseTime);
        chooseTime.setOnClickListener(new View.OnClickListener() {
            Calendar calendar = Calendar.getInstance();
            int currenH = calendar.get(Calendar.HOUR_OF_DAY);
            int currenM = calendar.get(Calendar.MINUTE);
            @Override
            public void onClick(View v) {
                timePickerDialog= new TimePickerDialog(Remind.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        chooseTime.setText(String.format("%02d:%02d ", hourOfDay,minute) + amPm);}
                    },currenH,currenM,false);timePickerDialog.show();
                }

            });

//----------------------------------------------------------------------
        chooseTime = findViewById(R.id.Time);
        chooseTime.setOnClickListener(new View.OnClickListener() {
            Calendar calendar = Calendar.getInstance();
            int currenH = calendar.get(Calendar.HOUR_OF_DAY);
            int currenM = calendar.get(Calendar.MINUTE);
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener time_listener = new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        // store the data in one string and set it to text
                        String time1 = String.valueOf(hour) + ":" + String.valueOf(minute);

                    }
                };

            }

        });

//----------------------------------------------------------------------
        chooseTime = findViewById(R.id.etTime);
        chooseTime.setOnClickListener(new View.OnClickListener() {
            Calendar calendar = Calendar.getInstance();
            int currenH = calendar.get(Calendar.HOUR_OF_DAY);
            int currenM = calendar.get(Calendar.MINUTE);
            @Override
            public void onClick(View v) {
                timePickerDialog= new TimePickerDialog(Remind.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        chooseTime.setText(String.format("%02d:%02d ", hourOfDay,minute) + amPm);}
                },currenH,currenM,false);timePickerDialog.show();
            }

        });

//----------------------------------------------------------------------

    }
    public void showNotification(View view) {
        Calendar calendar = Calendar.getInstance();
        int currenH = calendar.get(Calendar.HOUR_OF_DAY);
        int currenM = calendar.get(Calendar.MINUTE);

                    String text = spinner.getSelectedItem().toString();
                    String name = edit.getText().toString();
                    notification = new Notification.Builder(Remind.this);
                    notification.setSmallIcon(R.drawable.b);
                    notification.setWhen(System.currentTimeMillis());
                    notification.setContentTitle("ได้เวลาทานยาแล้ว");
                    notification.setTicker("มีข้อความใหม่");
                    notification.setContentText("รับประทานยา" + name + free + text);
                    Intent i = new Intent(this, Remind.class);
                    PendingIntent panding = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
                    notification.setContentIntent(panding);
                    NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    nm.notify(id, notification.build());

                    edit.setText("");
        Log.d(TAG, "onLongClick: 5555555555555" );

    }

    public void setText(String text) {
        this.text = text;

    }
   public void alarmSetmanager (View view){
        Long alertTime = new GregorianCalendar().getTimeInMillis()+5*1000;
        Intent alertIntent = new Intent(this,Alarm.class);
        AlarmManager alarmManager = (AlarmManager)
                getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC,alertTime,
                PendingIntent.getBroadcast(this,1,alertIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT));
   }
  // -------------------------------------------------
   // -----------------------------------------------

}
