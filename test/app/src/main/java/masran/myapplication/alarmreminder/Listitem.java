package masran.myapplication.alarmreminder;

import android.app.LauncherActivity;

public class Listitem {

    private String head;
    private String Number;

    public Listitem(String head,String Number){
        this.head = head;
        this.Number = Number;
    }

    public String getHead(){
        return head;
    }
    public String getNumber(){
        return Number;
    }


}
