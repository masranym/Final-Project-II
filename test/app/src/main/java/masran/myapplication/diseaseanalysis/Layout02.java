package masran.myapplication.diseaseanalysis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import masran.myapplication.R;

public class Layout02 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layouth02);
        ButterKnife.bind(this);


    }
}
