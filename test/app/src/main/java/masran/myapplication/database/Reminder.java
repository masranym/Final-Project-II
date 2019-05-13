package masran.myapplication.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Reminder {

    @PrimaryKey
    @NonNull
    private String title;
    private String text;
    private String time;

    public Reminder() {
    }

    @Ignore
    public Reminder(String title, String text,String time) {
        this.title = title;
        this.time = time;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText(){return text;}

    public void setText(String text){this.text = text;}


}
