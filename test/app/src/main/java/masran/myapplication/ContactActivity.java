package masran.myapplication;

import android.Manifest;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import masran.myapplication.database.Contact;
import masran.myapplication.database.ContactViewModel;
import masran.myapplication.recyclerview.HospitalListAdapter;
import masran.myapplication.recyclerview.ItemLongClickListener;
import masran.myapplication.recyclerview.RecyclerItemTouchHelper;

/**
 * Created by Mas'Z on 3/21/2018.
 */

public class ContactActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener, ItemLongClickListener {
    private static final String TAG = "ContactActivity";

    private HospitalListAdapter adapter;

    private ContactViewModel contactViewModel;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_map);

        ButterKnife.bind(this);
//        SwipeMenuListView listView = (SwipeMenuListView) findViewById(R.id.lisview);

        setupViewModel();


        contactViewModel.insert(new Contact("โรงพยาบาลสิริโรจน์","tel:0973530088"));
        contactViewModel.insert(new Contact("โรงพยาบาลวชิระภูเก็ต","tel:0973530088"));


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        ItemTouchHelper.SimpleCallback itemTouchHelper = new RecyclerItemTouchHelper(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerView);

        adapter = new HospitalListAdapter(new ArrayList<Contact>());
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);

//        ArrayAdapter adapter = new ArrayAdapter(ContactActivity.this,android.R.layout.simple_list_item_1,list);
//        listView.setAdapter(adapter);
//
//        SwipeMenuCreator creator = new SwipeMenuCreator() {
//            @Override
//            public void create(SwipeMenu menu) {
//                SwipeMenuItem deleteItem = new SwipeMenuItem(
//                        getApplicationContext());
//                // set item background
//                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
//                        0x3F, 0x25)));
//                // set item width
//                deleteItem.setWidth(170);
//                // set a icon
//                deleteItem.setIcon(R.drawable.ic_call);
//                // add to menu
//                menu.addMenuItem(deleteItem);
//
//            }
//        };
//        listView.setMenuCreator(creator);
//        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
//                switch (index) {
//                    case 0:
//                        Log.d(TAG,"onMenuItemClick cliked itemp one "+ index);
//                        break;
//                    case 1:
//                        Log.d(TAG,"onMenuItemClick cliked itemp two"+ index);
//
//                        final int REQUEST_PHONE_CALL = 1;
//                        Intent callIntent = new Intent(Intent.ACTION_CALL);
//                        callIntent.setData(Uri.parse("tel:0973530088"));//076361888
//                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//                            if(ContextCompat.checkSelfPermission(ContactActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
//                                ActivityCompat.requestPermissions(ContactActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
//                            }else{
//                                //-------------------------------places the call-----------------------------------
//                                startActivity(callIntent);
//                            }
//                        }
//
//                        break;
//
//
//                }
//                // false : close the menu; true : not close the menu
//                return false;
//            }
//        });

    }

    private void setupViewModel() {
        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
        contactViewModel.getContactList().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(@Nullable List<Contact> contacts) {
                adapter.setHospitalList(contacts);
            }
        });
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        Contact contact = adapter.getContact(position);

        final int REQUEST_PHONE_CALL = 1;
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(contact.getTelNo()));//076342633
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(ContactActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(ContactActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
            }else{
                //-------------------------------places the call-----------------------------------
                startActivity(callIntent);
                this.finish();
            }
        }

//        if(position == 0){
//            final int REQUEST_PHONE_CALL = 1;
//            Intent callIntent = new Intent(Intent.ACTION_CALL);
//            callIntent.setData(Uri.parse("tel:0973530088"));//076342633
//            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//                if(ContextCompat.checkSelfPermission(ContactActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
//                    ActivityCompat.requestPermissions(ContactActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
//                }else{
//                    //-------------------------------places the call-----------------------------------
//                    startActivity(callIntent);
//                    this.finish();
//                }
//            }
//        }
//        if(position == 1){
//            final int REQUEST_PHONE_CALL = 1;
//            Intent callIntent = new Intent(Intent.ACTION_CALL);
//            callIntent.setData(Uri.parse("tel:0973530088"));//076342633
//            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//                if(ContextCompat.checkSelfPermission(ContactActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
//                    ActivityCompat.requestPermissions(ContactActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
//                }else{
//                    //-------------------------------places the call-----------------------------------
//                    startActivity(callIntent);
//                    this.finish();
//                }
//            }
//        }
//        if(position == 2){
//            final int REQUEST_PHONE_CALL = 1;
//            Intent callIntent = new Intent(Intent.ACTION_CALL);
//            callIntent.setData(Uri.parse("tel:0973530088"));//076342633
//            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//                if(ContextCompat.checkSelfPermission(ContactActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
//                    ActivityCompat.requestPermissions(ContactActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
//                }else{
//                    //-------------------------------places the call-----------------------------------
//                    startActivity(callIntent);
//                    this.finish();
//                }
//            }
//        }
//        if(position == 3){
//            final int REQUEST_PHONE_CALL = 1;
//            Intent callIntent = new Intent(Intent.ACTION_CALL);
//            callIntent.setData(Uri.parse("tel:0973530088"));//076342633
//            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//                if(ContextCompat.checkSelfPermission(ContactActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
//                    ActivityCompat.requestPermissions(ContactActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
//                }else{
//                    //-------------------------------places the call-----------------------------------
//                    startActivity(callIntent);
//                    this.finish();
//                }
//            }
//        }
//        if(position == 4){
//            final int REQUEST_PHONE_CALL = 1;
//            Intent callIntent = new Intent(Intent.ACTION_CALL);
//            callIntent.setData(Uri.parse("tel:0973530088"));//076342633
//            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//                if(ContextCompat.checkSelfPermission(ContactActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
//                    ActivityCompat.requestPermissions(ContactActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
//                }else{
//                    //-------------------------------places the call-----------------------------------
//                    startActivity(callIntent);
//                    this.finish();
//                }
//            }
//        }
//        if(position == 5){
//            final int REQUEST_PHONE_CALL = 1;
//            Intent callIntent = new Intent(Intent.ACTION_CALL);
//            callIntent.setData(Uri.parse("tel:0973530088"));//076342633
//            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//                if(ContextCompat.checkSelfPermission(ContactActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
//                    ActivityCompat.requestPermissions(ContactActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
//                }else{
//                    //-------------------------------places the call-----------------------------------
//                    startActivity(callIntent);
//                    this.finish();
//                }
//            }
//        }
    }

    private int count = 0;

    @OnClick(R.id.addContactBtn)
    void winbox(){
        final Dialog dialog = new Dialog(ContactActivity.this);
        dialog.setContentView(R.layout.windows_box);
        final EditText hosname = (EditText) dialog.findViewById(R.id.hosname);
        final EditText hosnumber = (EditText) dialog.findViewById(R.id.hosnumber);
        Button buttonCancel = (Button) dialog.findViewById(R.id.button_cancel);
        Button buttonLogin = (Button) dialog.findViewById(R.id.button_ad);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!"".equals(hosname.getText().toString()) && !"".equals(hosnumber.getText().toString())){
                    contactViewModel.insert(new Contact(hosname.getText().toString(),"tel:"+hosnumber.getText().toString()));
                    dialog.dismiss();
                }
                 if ("".equals(hosname.getText().toString()))
                                hosname.setHint("กรุณากรอกชื่อโรงพยาบาล");

                 if ("".equals(hosnumber.getText().toString()))
                    hosnumber.setHint("กรุณากรอกเบอร์ติดต่อ                                                     ");
            }});
        dialog.show();
    }

    @Override
    public void onLongClick(final Contact contact) {
        Log.d(TAG, "onLongClick: " + contact.getTitle());
        final Dialog dialog = new Dialog(ContactActivity.this);
        dialog.setContentView(R.layout.window_worning);
        Button buttonyes = (Button) dialog.findViewById(R.id.button_yes);
        Button buttonno = (Button) dialog.findViewById(R.id.button_no);
        buttonno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        buttonyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactViewModel.delete(contact.getTitle());
                dialog.dismiss();
            }
        });

        dialog.show();
    }


//    @OnLongClick(R.id.list_foreground)
//    boolean deletecontact(){
//        final Dialog dialog = new Dialog(ContactActivity.this);
//        dialog.setContentView(R.layout.windows_box);
//        dialog.show();
//        return false;
//    }

//    void insertPlace() {
//        Log.d(TAG, "insertPlace: ");
//        contactViewModel.insert(new Contact("tk" + count,"tel:0864573098"));
//        count++;
//    }

//    @OnLongClick(R.id.addContactBtn)
//    boolean deletePlace() {
//        Log.d(TAG, "deletePlace: ");
//        contactViewModel.delete("tk");
//        return false;
//    }
}
