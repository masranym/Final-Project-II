package masran.myapplication.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Contact contact);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertContacts(List<Contact> contactList);

    @Query("DELETE FROM Contact WHERE :contactTitle = title")
    void delete(String contactTitle);

    @Query("DELETE FROM Contact")
    void deleteAll();

    @Query("SELECT * from Contact ORDER BY title ASC")
    LiveData<List<Contact>> getAllContact();
}
