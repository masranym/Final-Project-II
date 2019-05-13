package masran.myapplication.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class ReminderRepository {
    private ReminderDao reminderDao;
    private LiveData<List<Reminder>> reminderList;

    public ReminderRepository(Application application) {
        MyDatabase db = MyDatabase.getDatabase(application);

        reminderDao = db.reminderDao();
        reminderList = reminderDao.getAllReminder();
    }

    public LiveData<List<Reminder>> getAllReminders() {
        return reminderList;
    }

    public void insert(Reminder reminder) {
        new InsertTask(reminderDao).execute(reminder);
    }

    public void delete(String title) {
        new DeleteTask(reminderDao).execute(title);
    }

    class InsertTask extends AsyncTask<Reminder, Void, Void> {

        private ReminderDao mDao;

        InsertTask(ReminderDao dao) {
            mDao = dao;
        }

        @Override
        protected Void doInBackground(Reminder... reminders) {
            mDao.insert(reminders[0]);
            return null;
        }
    }

    class DeleteTask extends AsyncTask<String, Void, Void> {

        private ReminderDao mDao;

        DeleteTask(ReminderDao dao) {
            mDao = dao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            mDao.delete(strings[0]);
            return null;
        }
    }

}
