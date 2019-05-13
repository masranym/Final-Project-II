package masran.myapplication.alarmreminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import masran.myapplication.Alarm;
import masran.myapplication.R;
import masran.myapplication.database.Reminder;
import masran.myapplication.database.ReminderViewModel;

public class ReminderActivity extends AppCompatActivity implements OnItemLongClickListener {

    private static final String TAG = "ReminderActivity";

    private AlarmManager alarmManager;

    private RecyclerView recyclerView;
    private ReminderListAdapter adapter;
    private int i = 1;
    EditText chooseTime;
    TimePickerDialog timePickerDialog;
    String amPm;
    EditText etadd;
    EditText ettime;
    EditText ettext;

    int selectedHour = 0;
    int selectedMin = 0;

    private ReminderViewModel reminderViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_reminder);

        setupViewModel();

//        FloatingActionButton adremind = (FloatingActionButton)findViewById(R.id.addalarm01);
//        adremind.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Dialog dialog = new Dialog(ReminderActivity.this);
//                dialog.setContentView(R.layout.alarm_box);
//                dialog.show();
//            }
//        });

        chooseTime = findViewById(R.id.selectime);

        Calendar c = Calendar.getInstance();
        int curH = c.get(Calendar.HOUR_OF_DAY);
        int curM = c.get(Calendar.MINUTE);
        chooseTime.setText(String.format("%02d:%02d ", curH, curM));


        chooseTime.setOnClickListener(new View.OnClickListener() {
            Calendar calendar = Calendar.getInstance();
            int currenH = calendar.get(Calendar.HOUR_OF_DAY);
            int currenM = calendar.get(Calendar.MINUTE);

            @Override
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(ReminderActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        chooseTime.setText(String.format("%02d:%02d ", hourOfDay, minute) + amPm);

                        selectedHour = hourOfDay;
                        selectedMin = minute;
                    }
                }, currenH, currenM, false);
                timePickerDialog.show();
            }

        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview01);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ReminderListAdapter(new ArrayList<Reminder>(), this);
        adapter.setItemLongClickListener(this);
        recyclerView.setAdapter(adapter);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//
//      listItems = new ArrayList<>();
//        for (int i =1 ; i<4;i++) {
//            Listitem listItem = new Listitem("กินยาแอสไพริน", "18:"+i*10);
//            listItems.add(listItem);
//        }
//        Listitem listItem = new Listitem("กินยาแอสไพริน", "18:30");
//        listItems.add(listItem);
//        Listitem listItem = new Listitem("กินยาแก้ปวดลดไข้", "19:50");
//        listItems.add(listItem);
//        Listitem listItem = new Listitem("กินยาแก้แพ้", "21:30");
//        listItems.add(listItem);

//        adapter = new MyAdapter(listItems,this);
//        recyclerView.setAdapter(adapter);

    }

    private void setupViewModel() {
        reminderViewModel = ViewModelProviders.of(this).get(ReminderViewModel.class);
        reminderViewModel.getReminderList().observe(this, new Observer<List<Reminder>>() {
            @Override
            public void onChanged(@Nullable List<Reminder> reminders) {
                Log.d(TAG, "onChanged: ");
                adapter.setReminderList(reminders);
            }
        });

    }

    public void alarmAdd(View view) {
        etadd = (EditText) findViewById(R.id.etremind);
        ettime = (EditText) findViewById(R.id.selectime);
        ettext = (EditText) findViewById(R.id.sss);

        String name = etadd.getText().toString();
        String settime = ettime.getText().toString();
        String textremind = ettext.getText().toString();

        //TODO add text
        reminderViewModel.insert(new Reminder(name, textremind, settime));

//        Long alertTime = new GregorianCalendar().getTimeInMillis() + 5 * 1000;
//
//        Intent alertIntent = new Intent(this, Alarm.class);
//
//        //TODO add title name
//        alertIntent.putExtra("title", chooseTime.toString());
//        alertIntent.putExtra("name", name);
//
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        alarmManager.set(AlarmManager.RTC, alertTime, PendingIntent.getBroadcast(this, 1, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT));

        createAlarm(textremind, name);

        etadd.setText("");
        ettime.setText("");
        ettext.setText("");

    }

    private void createAlarm(String title, String text) {
        int id = adapter.getItemCount();
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
        calendar.set(Calendar.MINUTE, selectedMin);
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(getApplicationContext(), NotificationReciever.class);
        intent.putExtra("id", id);
        intent.putExtra("title", title);
        intent.putExtra("name", text);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void cancelAlarm(int index) {
        Intent intent = new Intent(getApplicationContext(), NotificationReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), index, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (pendingIntent != null)
            alarmManager.cancel(pendingIntent);
    }

    @Override
    public void onLItemLongClick(String title, int position) {
        reminderViewModel.delete(title);
        cancelAlarm(position);
    }
}
