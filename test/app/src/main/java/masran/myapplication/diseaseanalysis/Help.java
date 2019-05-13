package masran.myapplication.diseaseanalysis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import masran.myapplication.R;

/**
 * Created by Mas'Z on 3/21/2018.
 */

public class Help extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    @BindView(R.id.txtInput)
    AutoCompleteTextView editText;
    @BindView(R.id.btAdd)
    Button btAdd;
    @BindView(R.id.vtlist)
    Button ct;
    @BindView(R.id.list)
    ListView listV;
    ArrayList<String> itemList;

    @BindView(R.id.txtset)
    TextView txtview;

    private static final String[] itemsdm = new String[]{"คัน","ปวดแสบปวดร้อน","ตุ่มน้ำใสๆ","มีหนอง","ตุ่มบวม","แสบร้อน","ผื่นแดง","ระคายเคือง","ตาเหลือง","ผิวเหลือง","ไข้ต่ำ","ผื่นเป็นจุดแดง","ปัสวะบ่อย",
            "หิวน้ำมากขึ้น","ผื่นรูปวงกลม","งูสวัด","ฝี","ลมพิษ","กลาก","ดีซาน","อีสุอีใส","เบาหวาน","เกลื้อน"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        ButterKnife.bind(this);

//        ArrayAdapter<String> = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,itemsdm);
//        editText.setAdapter(adapter);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,itemsdm);
        editText.setAdapter(adapter);

        itemList=new ArrayList<String>();
        adapter=new ArrayAdapter<String>(this,R.layout.list_item,R.id.txtview,itemList);
        listV.setAdapter(adapter);



    }

    @OnClick(R.id.btAdd)
    void addItem(){
        String newItem=editText.getText().toString();
        // add new item to arraylist
        if (!newItem.contentEquals("")){
            itemList.add(newItem);
            // notify listview of data changed
            adapter.notifyDataSetChanged();
            editText.setText("");
        }

    }

    @OnClick(R.id.vtlist)
    void analyse(){


            if (itemList.size() == 3){
                if (itemList.contains("ปวดแสบปวดร้อน")&& itemList.contains("ผื่นแดง") && itemList.contains("ตุ่มน้ำใสๆ")){
                    Intent intent = new Intent(Help.this, Layout02.class);
                    startActivity(intent);
                   // txtview.setText("งูสวัด");
                }

                else if (itemList.contains("มีหนอง")&& itemList.contains("ตุ่มบวม") && itemList.contains("แสบร้อน")){
                    Intent intent = new Intent(Help.this, Layout03.class);
                    startActivity(intent);
                    // txtview.setText("ฝี");
                }
                else if (itemList.contains("คัน")&& itemList.contains("แสบร้อน") && itemList.contains("ผื่นแดง")){
                    Intent intent = new Intent(Help.this, Layout04lompis.class);
                    startActivity(intent);}
                   // txtview.setText("ลมพิษ");
                else
                    txtview.setText("ไม่พบข้อมูล ควรไปพบปรึษาแพทย์");
            }
            else if (itemList.size() == 2){
                 if (itemList.contains("คัน")&&itemList.contains("ระคายเคือง")){ //กลาก
                     Intent intent = new Intent(Help.this, Layout01.class);
                     startActivity(intent);}
                else if (itemList.contains("ตาเหลือง")&&itemList.contains("ผิวเหลือง")){
                     Intent intent = new Intent(Help.this, Layout06desan.class);
                     startActivity(intent);}
                   // txtview.setText("ดีซ่าน ตาเหลือง ตัวเหลือง");
                 else if (itemList.contains("ไข้ต่ำ")&&itemList.contains("ผื่นเป็นจุดแดง")){
                     Intent intent = new Intent(Help.this, Layout07esuesai.class);
                     startActivity(intent);}
                     //txtview.setText("อีสุอีใส");
                 else if (itemList.contains("ปัสวะบ่อย")&&itemList.contains("หิวน้ำมากขึ้น")){
                     Intent intent = new Intent(Help.this, Layout05bouwan.class);
                     startActivity(intent);}
                     //txtview.setText("เบาหวาน");
                else
                     txtview.setText("ไม่พบข้อมูล ควรไปพบปรึษาแพทย์");

            }
            else if (itemList.size() == 1){
                 if (itemList.contains("ผื่นรูปวงกลม")){
                     Intent intent = new Intent(Help.this, Layout08glean.class);
                     startActivity(intent);
                     // txtview.setText("เกลื้อน");
                 }
                   // txtview.setText("เกลื้อน");
                 else if (itemList.contains("กลาก")){ //กลาก
                     Intent intent = new Intent(Help.this, Layout01.class);
                     startActivity(intent);}
                 else if (itemList.contains("ฝี")){
                     Intent intent = new Intent(Help.this, Layout03.class);
                     startActivity(intent);
                     // txtview.setText("ฝี");
                 }
                 else if (itemList.contains("งูสวัด")){
                     Intent intent = new Intent(Help.this, Layout02.class);
                     startActivity(intent);
                     // txtview.setText("งูสวัด");
                 }
                 else if (itemList.contains("เกลื้อน")){
                     Intent intent = new Intent(Help.this, Layout08glean.class);
                     startActivity(intent);
                     // txtview.setText("เกลื้อน");
                 }
                 else if (itemList.contains("ลมพิษ")){
                     Intent intent = new Intent(Help.this, Layout04lompis.class);
                     startActivity(intent);
                     // txtview.setText("ลมพิษ");
                 }
                 else if (itemList.contains("เบาหวาน")){
                     Intent intent = new Intent(Help.this, Layout05bouwan.class);
                     startActivity(intent);
                     // txtview.setText("เบาหวาน");
                 }
                 else if (itemList.contains("ดีซาน")){
                     Intent intent = new Intent(Help.this, Layout06desan.class);
                     startActivity(intent);
                     // txtview.setText("ดีซาน");
                 }
                 else if (itemList.contains("อีสุอีใส")){
                     Intent intent = new Intent(Help.this, Layout07esuesai.class);
                     startActivity(intent);
                     // txtview.setText("ดีซาน");
                 }
                 else if (itemList.contains("เครียด")){
                     Intent intent = new Intent(Help.this, Layout09.class);
                     startActivity(intent);
                     // txtview.setText("ดีซาน");
                 }
                 else if (itemList.contains("กุ้งยิง")){
                     Intent intent = new Intent(Help.this, Layout10.class);
                     startActivity(intent);
                     // txtview.setText("ดีซาน");
                 }
                 else if (itemList.contains("คลื่นไส้")){
                     Intent intent = new Intent(Help.this, Layout11.class);
                     startActivity(intent);
                     // txtview.setText("ดีซาน");
                 }
                 else if (itemList.contains("อาเจียน")){
                     Intent intent = new Intent(Help.this, Layout11.class);
                     startActivity(intent);
                     // txtview.setText("ดีซาน");
                 }
                 else if (itemList.contains("หน้ามืด")){
                     Intent intent = new Intent(Help.this, Layout12.class);
                     startActivity(intent);
                     // txtview.setText("ดีซาน");
                 }
                 else if (itemList.contains("คอพอก")){
                     Intent intent = new Intent(Help.this, Layout13.class);
                     startActivity(intent);
                     // txtview.setText("ดีซาน");
                 }
                 else if (itemList.contains("คัดจมูก")){
                     Intent intent = new Intent(Help.this, Layout14.class);
                     startActivity(intent);
                     // txtview.setText("ดีซาน");
                 }
                 else if (itemList.contains("น้ำมูกไหล")){
                     Intent intent = new Intent(Help.this, Layout14.class);
                     startActivity(intent);
                     // txtview.setText("ดีซาน");
                 }
                 else if (itemList.contains("คันตา")){
                     Intent intent = new Intent(Help.this, Layout15.class);
                     startActivity(intent);
                     // txtview.setText("ดีซาน");
                 }
                 else if (itemList.contains("คันหู")){
                     Intent intent = new Intent(Help.this, Layout16.class);
                     startActivity(intent);
                     // txtview.setText("ดีซาน");
                 }
                 else if (itemList.contains("คางทูบ")){
                     Intent intent = new Intent(Help.this, Layout17.class);
                     startActivity(intent);
                     // txtview.setText("ดีซาน");
                 }
                 else if (itemList.contains("ตาพร่า")){
                     Intent intent = new Intent(Help.this, Layout18.class);
                     startActivity(intent);
                     // txtview.setText("ดีซาน");
                 }
                 else if (itemList.contains("ตามัว")){
                     Intent intent = new Intent(Help.this, Layout18.class);
                     startActivity(intent);
                     // txtview.setText("ดีซาน");
                 }
                 else if (itemList.contains("ต่อมทอนซินอักเสบ")){
                     Intent intent = new Intent(Help.this, Layout19.class);
                     startActivity(intent);
                     // txtview.setText("ดีซาน");
                 }
                 else if (itemList.contains("ปวดฟัน")){
                     Intent intent = new Intent(Help.this, Layout20.class);
                     startActivity(intent);
                     // txtview.setText("ดีซาน");
                 }
                 else if (itemList.contains("ปวดหัว")){
                     Intent intent = new Intent(Help.this, Layout21.class);
                     startActivity(intent);
                     // txtview.setText("ดีซาน");
                 }
                 else if (itemList.contains("ปากนกกระจอก")){
                     Intent intent = new Intent(Help.this, Layout21.class);
                     startActivity(intent);
                     // txtview.setText("ดีซาน");
                 }
                 else if (itemList.contains("ปากเปื่อย")){
                     Intent intent = new Intent(Help.this, Layout22.class);
                     startActivity(intent);
                     // txtview.setText("ดีซาน");
                 }
                 else if (itemList.contains("ริดสีดวงตา")){
                     Intent intent = new Intent(Help.this, Layout22.class);
                     startActivity(intent);
                     // txtview.setText("ดีซาน");
                 }
                 else if (itemList.contains("ริดสีดวงตา")){
                     Intent intent = new Intent(Help.this, Layout23.class);
                     startActivity(intent);
                     // txtview.setText("ดีซาน");
                 }
                 else if (itemList.contains("ลิ้นฝ้าขาว")){
                     Intent intent = new Intent(Help.this, Layout24.class);
                     startActivity(intent);
                     // txtview.setText("ดีซาน");
                 }
                 else if (itemList.contains("หูอักเสบ")){
                     Intent intent = new Intent(Help.this, Layout25.class);
                     startActivity(intent);
                     // txtview.setText("ดีซาน");
                 }
                 else if (itemList.contains("หูน้ำหนวก")){
                     Intent intent = new Intent(Help.this, Layout25.class);
                     startActivity(intent);
                     // txtview.setText("ดีซาน");
                 }
                 else if (itemList.contains("เจ็บคอ")){
                     Intent intent = new Intent(Help.this, Layout26.class);
                     startActivity(intent);
                     // txtview.setText("ดีซาน");
                 }
                 else if (itemList.contains("เจ็บตา")){
                     Intent intent = new Intent(Help.this, Layout27.class);
                     startActivity(intent);
                     // txtview.setText("ดีซาน");
                 }
                 else if (itemList.contains("ตาแดง")){
                     Intent intent = new Intent(Help.this, Layout27.class);
                     startActivity(intent);
                     // txtview.setText("ดีซาน");
                 }
                 else if (itemList.contains("ตาแฉะ")){
                     Intent intent = new Intent(Help.this, Layout27.class);
                     startActivity(intent);
                     // txtview.setText("ดีซาน");
                 }
                 else if (itemList.contains("เสียงแหบ")){
                     Intent intent = new Intent(Help.this, Layout28.class);
                     startActivity(intent);
                     // txtview.setText("ดีซาน");
                 }
                 else if (itemList.contains("เหงือกอักเสบ")){
                     Intent intent = new Intent(Help.this, Layout29.class);
                     startActivity(intent);
                     // txtview.setText("ดีซาน");
                 }
                 else if (itemList.contains("ไซนัสอักเสบ")){
                     Intent intent = new Intent(Help.this, Layout29.class);
                     startActivity(intent);
                     // txtview.setText("ดีซาน");
                 }
                 else if (itemList.contains("ไอ")){
                     Intent intent = new Intent(Help.this, Layout29.class);
                     startActivity(intent);
                     // txtview.setText("ดีซาน");
                 }
                 else
                    txtview.setText("ไม่พบข้อมูล ควรไปปรึษาแพทย์");
            }
            else {
                Intent intent = new Intent(Help.this, Layout03.class);
                startActivity(intent);
            }

    }

}


