package com.reyhalconmotors;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EmployeeWriteID extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_write_id);
        TextView textView=findViewById(R.id.employee_write_id);
        Intent intent=getIntent();
        savedInstanceState=intent.getExtras();
        String uId=savedInstanceState.getString("id");
        textView.setText(uId);
    }
}
