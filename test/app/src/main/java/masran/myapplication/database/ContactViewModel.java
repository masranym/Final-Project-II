package masran.myapplication.database;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    private ContactRepository repository;
    private LiveData<List<Contact>> contactList;

    public ContactViewModel(Application application) {
        super(application);
        repository = new ContactRepository(application);
        contactList = repository.getAllContacts();
    }

    public LiveData<List<Contact>> getContactList() {
        return contactList;
    }

    public void insert(Contact contact) {
        repository.insert(contact);
    }

    public void delete(String title) {
        repository.delete(title);
    }
}
