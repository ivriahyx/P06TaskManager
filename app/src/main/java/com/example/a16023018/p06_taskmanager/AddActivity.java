package com.example.a16023018.p06_taskmanager;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    int reqCode = 12345;
    Button btnAdd , btnCancel;
    EditText etName, etDesc,etSeconds;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        etName = (EditText)findViewById(R.id.editTextName);
        etDesc = (EditText) findViewById(R.id.editTextDesc);
        etSeconds = (EditText)findViewById(R.id.etRemindSeconds);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = etName.getText().toString();
                String desc = etDesc.getText().toString();
                int seconds = Integer.parseInt(etSeconds.getText().toString());

                Log.d("Data",""+name+" "+desc+" "+seconds);
                DBHelper db = new DBHelper(AddActivity.this);
                db.insertNote(name,desc);
                db.close();
                Toast.makeText(AddActivity.this, "Inserted", Toast.LENGTH_SHORT).show();
                Log.d("insertData",""+name+" "+desc);
                //Notification
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, seconds);

                Intent intent = new Intent(AddActivity.this,
                        MyBroadcastReceiver.class);
                intent.putExtra("name",name);
                intent.putExtra("description",desc);
                intent.putExtra("seconds",seconds+"");

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        AddActivity.this, reqCode,
                        intent, PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager am = (AlarmManager)
                        getSystemService(Activity.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                        pendingIntent);


                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
