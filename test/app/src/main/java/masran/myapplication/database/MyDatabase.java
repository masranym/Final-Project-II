package masran.myapplication.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

@Database(entities = {Contact.class, Reminder.class},version = 2)
public abstract class MyDatabase extends RoomDatabase{
    public abstract ContactDao contactDao();
    public abstract ReminderDao reminderDao();

    private static volatile MyDatabase INSTANCE;

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `Reminder` (`title` TEXT NOT NULL, `text` TEXT, `time` TEXT, PRIMARY KEY(`title`))");
        }
    };

    static MyDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (MyDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, MyDatabase.class, "contact_database").
                            addMigrations(MIGRATION_1_2).build();
                }
            }
        }
        return INSTANCE;
    }
}
