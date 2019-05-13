package masran.myapplication.alarmreminder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import masran.myapplication.R;
import masran.myapplication.database.Reminder;

public class ReminderListAdapter extends RecyclerView.Adapter<ReminderListAdapter.ViewHolder> {

    private List<Reminder> reminderList;
    private Context context;
    private OnItemLongClickListener listener;

    public ReminderListAdapter(List<Reminder> reminderList, Context context) {
        this.reminderList = reminderList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reminderlist,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reminder reminder = reminderList.get(position);
               holder.textViewHead.setText(reminder.getTitle());
               holder.textViewNumber.setText(reminder.getTime()+"");
    }

    @Override
    public int getItemCount() {
        return reminderList.size();
    }

    public List<Reminder> getReminderList() {
        return reminderList;
    }

    public void setReminderList(List<Reminder> reminderList) {
        this.reminderList = reminderList;
        notifyDataSetChanged();
    }

    public void setItemLongClickListener(OnItemLongClickListener l) {
        this.listener = l;
    }

    public class ViewHolder extends  RecyclerView.ViewHolder implements View.OnLongClickListener {

        public TextView textViewHead;
        public TextView textViewNumber;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewHead = (TextView) itemView.findViewById(R.id.text_reminder);
            textViewNumber = (TextView) itemView.findViewById(R.id.text_remindnumber);

            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            int pos = getAdapterPosition();
            if (listener != null) {
                listener.onLItemLongClick(reminderList.get(pos).getTitle(), pos);
            }
            return false;
        }
    }
}
