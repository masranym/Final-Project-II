package masran.myapplication.database;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class ReminderViewModel extends AndroidViewModel {
    private ReminderRepository repository;
    private LiveData<List<Reminder>> reminderList;

    public ReminderViewModel(Application application) {
        super(application);
        repository = new ReminderRepository(application);
        reminderList = repository.getAllReminders();
    }

    public LiveData<List<Reminder>> getReminderList() {
        return reminderList;
    }

    public void insert(Reminder contact) {
        repository.insert(contact);
    }

    public void delete(String title) {
        repository.delete(title);
    }
}
