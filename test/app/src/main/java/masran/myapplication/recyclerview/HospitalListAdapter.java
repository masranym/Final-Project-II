package masran.myapplication.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import masran.myapplication.database.Contact;
import masran.myapplication.R;

public class HospitalListAdapter extends RecyclerView.Adapter<HospitalListAdapter.Holder>{

    private static final String TAG = "HospitalListAdapter";
    private List<Contact> hospitalList;
    private ItemLongClickListener mListener;

    public HospitalListAdapter(List<Contact> hospitalList){
       this.hospitalList = hospitalList;
    }

    class Holder extends RecyclerView.ViewHolder{

        TextView textHospitalName;
        public View viewBackground;
        public View viewForeground;

        public Holder(View itemView) {
            super(itemView);
            textHospitalName = itemView.findViewById(R.id.text_hospital);
            viewBackground = itemView.findViewById(R.id.list_background);
            viewForeground = itemView.findViewById(R.id.list_foreground);

            viewForeground.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(mListener != null)
                        mListener.onLongClick(hospitalList.get(getAdapterPosition()));
                    return false;
                }
            });
        }

        public void bindItem(int position){
            textHospitalName.setText(hospitalList.get(position).getTitle());
        }

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_contact_item,parent,false);
        return new Holder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bindItem(position);
    }

    @Override
    public int getItemCount() {
        return hospitalList.size();
    }

    public void setHospitalList(List<Contact> contactList){
        hospitalList = contactList;
        notifyDataSetChanged();
    }
    public Contact getContact(int position) {
        return hospitalList.get(position);
    }

    public void setListener(ItemLongClickListener listener) {
        mListener = listener;
    }

}
