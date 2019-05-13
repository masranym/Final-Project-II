package masran.myapplication.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Contact {

    @PrimaryKey
    @NonNull
    private String title;

    private String telNo;

    public Contact() {
    }

    @Ignore
    public Contact(String title, String telNo) {
        this.title = title;
        this.telNo = telNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }
}
