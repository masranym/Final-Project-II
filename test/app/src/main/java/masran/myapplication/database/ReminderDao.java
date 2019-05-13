package masran.myapplication.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ReminderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Reminder reminder);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertReminders(List<Reminder> reminderList);

    @Query("DELETE FROM Reminder WHERE :reminderTitle = title")
    void delete(String reminderTitle);

    @Query("DELETE FROM Reminder")
    void deleteAll();

    @Query("SELECT * from Reminder ORDER BY title ASC")
    LiveData<List<Reminder>> getAllReminder();
}
