package masran.myapplication.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class ContactRepository {
    private ContactDao contactDao;
    private LiveData<List<Contact>> contactList;

    public ContactRepository(Application application) {
        MyDatabase db = MyDatabase.getDatabase(application);

        contactDao = db.contactDao();
        contactList = contactDao.getAllContact();
    }

    public LiveData<List<Contact>> getAllContacts() {
        return contactList;
    }

    public void insert(Contact contact) {
        new InsertTask(contactDao).execute(contact);
    }

    public void delete(String title) {
        new DeleteTask(contactDao).execute(title);
    }

    class InsertTask extends AsyncTask<Contact, Void, Void> {

        private ContactDao mDao;

        InsertTask(ContactDao dao) {
            mDao = dao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            mDao.insert(contacts[0]);
            return null;
        }
    }

    class DeleteTask extends AsyncTask<String, Void, Void> {

        private ContactDao mDao;

        DeleteTask(ContactDao dao) {
            mDao = dao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            mDao.delete(strings[0]);
            return null;
        }
    }

}
