package com.reyhalconmotors;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ViewEmployeeActivity extends AppCompatActivity {
    int input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_employee);
        AlertDialog.Builder alb=new AlertDialog.Builder(this);
        alb.setTitle(R.string.view_employee_by);
        alb.setSingleChoiceItems(R.array.view_employee_way, 2, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case 0:
                        input=1;
                        Toast.makeText(ViewEmployeeActivity.this, "Id no", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        input=2;
                        Toast.makeText(ViewEmployeeActivity.this, "Name ", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(ViewEmployeeActivity.this, "All", Toast.LENGTH_SHORT).show();
                }
            }
        });
        alb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        alb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Start ViewEmployeeShow Activity to show Employee by either id or name or all
                Intent intent=new Intent(ViewEmployeeActivity.this,ViewEmployeeShowActivity.class);
                intent.putExtra("input",input);
                startActivity(intent);
                finish();
            }
        });
        alb.setCancelable(false);
        alb.create().show();
    }
}
