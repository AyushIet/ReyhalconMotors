package com.reyhalconmotors;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.Date;

public class EmployeeHomePage extends AppCompatActivity {
    TextView name, salary, contactNumber, designation, todayDate;
    String nameOfEmployee;
    ImageView imageView;
    String userId;
    DatabaseReference mDatabase;
    FirebaseStorage storage;

    @Override
    protected void onStart() {
        super.onStart();
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        int monthTemp = calendar.get(Calendar.MONTH);
        Date date = calendar.getTime();
        final int month = ++monthTemp;
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        try {
            mDatabase.child("Employee_Attendance").child(userId).child("" + year).child("" + month).child("" + day).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String status = (String) dataSnapshot.getValue();
                    if(status==null){

                    }else {
                        if (status.equals("a")) {
                            todayDate.setTextColor(Color.RED);
                        }
                        if (status.equals("p")) {
                            todayDate.setTextColor(Color.GREEN);
                        }
                        if (status.equals("h")) {
                            todayDate.setTextColor(Color.GREEN);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }catch(Exception e){
            Log.e("aa---->",""+e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_home_page);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        int monthTemp = calendar.get(Calendar.MONTH);
        final int month = ++monthTemp;
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        name = findViewById(R.id.employee_home_page_name);
        salary = findViewById(R.id.employee_home_page_salary);
        designation = findViewById(R.id.employee_home_page_designation);
        contactNumber = findViewById(R.id.employee_home_page_contact_no);
        todayDate = findViewById(R.id.employee_home_page_date);
        imageView = findViewById(R.id.employee_home_page_employee_pic);
        todayDate.setText(day + "-" + month + "-" + year);
        Intent intent = getIntent();
        savedInstanceState = intent.getExtras();
        userId = savedInstanceState.getString("user");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Employee_Details").child(userId).child("Personal_Details").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AddEmployeeDetailsHelper addEmployeeDetailsHelper = dataSnapshot.getValue(AddEmployeeDetailsHelper.class);
                Log.e("a---->", userId);
                String nameOfEmployee = addEmployeeDetailsHelper.getNameOfEmployee();
                String sal = addEmployeeDetailsHelper.getSalaryOfEmployee();
                String contact = addEmployeeDetailsHelper.getContactNumberOfEmployee();
                String desg = addEmployeeDetailsHelper.getDesignationOfEmployee();
                name.setText(nameOfEmployee);
                salary.setText(sal);
                contactNumber.setText(contact);
                designation.setText(desg);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        storage = FirebaseStorage.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final StorageReference storageRef = storage.getReference().child("" + userId + ".jpg");
                Glide.with(EmployeeHomePage.this /* context */)
                        .using(new FirebaseImageLoader())
                        .load(storageRef)
                        .into(imageView);
            }
        }, 5000);
        Toast.makeText(this, "" + userId, Toast.LENGTH_SHORT).show();
    }

    public void delete(View view) {

    }

    public void salary(View view) {
    }

    public void update(View view) {
        AlertDialog.Builder alb = new AlertDialog.Builder(this);
        alb.setTitle("Update Details");
        View v = getLayoutInflater().inflate(R.layout.employee_update_details, null, false);
        final Button b1 = v.findViewById(R.id.employee_update_name_button);
        final Button b2 = v.findViewById(R.id.employee_update_phone_button);
        final EditText name_editText = v.findViewById(R.id.employee_update_details_name);
        final EditText phone_editText = v.findViewById(R.id.employee_update_details_phone);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b1.setVisibility(View.GONE);
                name_editText.setVisibility(View.VISIBLE);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b2.setVisibility(View.GONE);
                phone_editText.setVisibility(View.VISIBLE);
            }
        });

        alb.setView(v);
        alb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //perform action on selected item
                String name = name_editText.getText().toString().trim();
                String phone = phone_editText.getText().toString().trim();
                if (!name.isEmpty()) {
                    mDatabase.child("Employee_Details").child(userId).child("Personal_Details").child("nameOfEmployee").setValue(name);
                }
                if (!phone.isEmpty()) {
                    mDatabase.child("Employee_Details").child(userId).child("Personal_Details").child("contactNumberOfEmployee").setValue(phone);
                }
            }
        });
        alb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alb.setCancelable(false);
        alb.create().show();
    }

    public void attendence(View view) {

        AlertDialog.Builder alb = new AlertDialog.Builder(this);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        int monthTemp = calendar.get(Calendar.MONTH);
        Date date = calendar.getTime();
        final int hour = date.getHours();
        final int month = ++monthTemp;
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        alb.setMessage("Give Attendence");
        alb.setNegativeButton("Absent", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDatabase.child("Employee_Attendance").child(userId).child("" + year).child("" + month).child("" + day).setValue("a");
                todayDate.setTextColor(Color.RED);
            }
        });
        alb.setPositiveButton("Present", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Taking System Time and Latitude,Longitude
                Toast.makeText(EmployeeHomePage.this, "" + hour, Toast.LENGTH_SHORT).show();
                if (hour < 10) {
                    mDatabase.child("Employee_Attendance").child(userId).child("" + year).child("" + month).child("" + day).setValue("f");
                    todayDate.setTextColor(Color.GREEN);
                }
                if (hour < 12) {
                    mDatabase.child("Employee_Attendance").child(userId).child("" + year).child("" + month).child("" + day).setValue("h");
                    todayDate.setTextColor(Color.GREEN);
                }
                if (hour > 12) {
                    mDatabase.child("Employee_Attendance").child(userId).child("" + year).child("" + month).child("" + day).setValue("a");
                    Toast.makeText(EmployeeHomePage.this, "Office Hours finish", Toast.LENGTH_SHORT).show();
                    todayDate.setTextColor(Color.RED);
                }
            }
        });
        alb.setCancelable(false);
        alb.create().show();
    }
}
