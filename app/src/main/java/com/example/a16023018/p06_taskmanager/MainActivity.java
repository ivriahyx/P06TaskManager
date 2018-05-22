package com.example.a16023018.p06_taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAddNew;
    TextView tvDBContent;
    ListView lv;
    ArrayList<String> al;
    ArrayAdapter aa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) this.findViewById(R.id.lv);
        al = new ArrayList<String>();
        aa = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, al);

        btnAddNew = (Button)this.findViewById(R.id.btnAddNew);
        tvDBContent = (TextView)this.findViewById(R.id.tvDBContent);
        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                //startActivity(intent);
                startActivityForResult(intent,9);
            }
        });
        DBHelper db = new DBHelper(MainActivity.this);

        // Insert a task
        al.addAll(db.getAllContent());
        Log.d("db.getAllContent",""+db.getAllContent());
        db.close();

        String txt = "";
        for (int i = 0; i< al.size(); i++){
            String tmp = al.get(i);
            Log.d("tmp string txt",""+tmp);
            txt += tmp + "\n";
        }

        lv.setAdapter(aa);
        tvDBContent.setText(txt);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 9){
            DBHelper db = new DBHelper(MainActivity.this);

            // Insert a task
            al.clear();
            al.addAll(db.getAllContent());
            db.close();

            lv.setAdapter(aa);
            aa.notifyDataSetChanged();
        }
    }


}
