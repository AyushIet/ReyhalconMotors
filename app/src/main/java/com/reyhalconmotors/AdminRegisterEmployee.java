package com.reyhalconmotors;


import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

public class AdminRegisterEmployee extends AppCompatActivity {
    String nameOfEmployee, contactNumberOfEmployee, salaryOfEmployee, designationOfEmployee, inTimeOfEmployee;
    Bitmap picOfEmployee;
    boolean nameOfEmployee_status, contactNumberOfEmployee_status, salaryOfEmployee_status;
    FloatingActionButton floatingActionButton;
    TextInputLayout til1, til2, til3, til4;
    Spinner designation;
    CardView cv1, cv2;
    EditText name, contactNumber, salary;
    LinearLayout imageLinearLayout;
    ImageView employeePic, imageIcon;
    Button inTime;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register_employee);
        //finding reference of CardView
        cv1 = findViewById(R.id.admin_register_employee_card_view1);
        cv2 = findViewById(R.id.admin_register_employee_card_view2);
        //finding reference of TextInputLayout
        til1 = findViewById(R.id.admin_register_employee_text_input_layout1);
        til2 = findViewById(R.id.admin_register_employee_text_input_layout2);
        til3 = findViewById(R.id.admin_register_employee_text_input_layout3);
        til4 = findViewById(R.id.admin_register_employee_text_input_layout4);
        //finding reference of Spinner
        designation = findViewById(R.id.admin_register_employee_spinner);
        //finding reference of EditText
        name = findViewById(R.id.admin_register_employee_name_edit_text);
        contactNumber = findViewById(R.id.admin_register_employee_contact_edit_text);
        salary = findViewById(R.id.admin_register_employee_salary_edit_text);
        imageLinearLayout = findViewById(R.id.admin_register_employee_linear_layout3);
        employeePic = findViewById(R.id.admin_register_employee_image);
        imageIcon = findViewById(R.id.admin_register_employee_image_icon);
        floatingActionButton = findViewById(R.id.admin_register_employee_fab);
        //finding reference of in time
        inTime = findViewById(R.id.admin_register_employee_in_time);
        //Getting Time from system
        Calendar calendar = Calendar.getInstance();
        final int system_hour = calendar.get(Calendar.HOUR);
        final int system_minute = calendar.get(Calendar.MINUTE);
        int system_second = calendar.get(Calendar.SECOND);
        //Adding TextChangeListener to EditText Name
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                nameOfEmployee = s.toString().trim();
                til1.setErrorEnabled(true);
                if (nameOfEmployee.isEmpty()) {
                    til1.setError("Empty");
                    name.requestFocus();
                } else {
                    til1.setError("");
                    char array[] = nameOfEmployee.toCharArray();
                    for (char x : array) {
                        if ((x > 64 && x < 91) || (x > 96 && x < 123) || (x == 32)) {
                            nameOfEmployee_status = true;
                        } else {
                            til1.setError("only letters allowed");
                            name.requestFocus();
                        }
                    }
                }
            }
        });
        //Adding TextChangeListener to EditText Contact Number
        contactNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                contactNumberOfEmployee = s.toString().trim();
                til2.setErrorEnabled(true);
                if (contactNumberOfEmployee.isEmpty()) {
                    til2.setError("empty");
                } else {
                    til2.setError("");
                    if (contactNumberOfEmployee.length() != 10) {
                        til2.setError("must be 10 digit");
                    } else {
                        til2.setError("");
                        contactNumberOfEmployee_status = true;
                        if (nameOfEmployee_status == true) {
                            floatingActionButton.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });
        //Adding TextChangeListener to EditText Contact Number
        salary.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                salaryOfEmployee = s.toString().trim();
                til3.setErrorEnabled(true);
                if (salaryOfEmployee.isEmpty()) {
                    til3.setError("empty");
                } else {
                    til3.setError("");
                    salaryOfEmployee_status = true;
                }
            }
        });
        //Adding view to Spinner
        Resources resources = getResources();
        final String arr[] = resources.getStringArray(R.array.my_spinner_item);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, arr);
        designation.setAdapter(arrayAdapter);

        //Getting value from Spinner
        designation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("tag---------->", "" + position);
                designationOfEmployee = arr[position];
                if (salaryOfEmployee_status == true) {
                    floatingActionButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Listening Action on Fab
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = ++count;
                if (count == 1) {
                    cv1.setVisibility(View.GONE);
                    cv2.setVisibility(View.VISIBLE);
                    floatingActionButton.setVisibility(View.GONE);
                }
                if (count == 2) {
                    cv2.setVisibility(View.GONE);
                    imageLinearLayout.setVisibility(View.VISIBLE);
                    floatingActionButton.setVisibility(View.GONE);
                }
                if (count >2) {
                    //firebase connectivity logic here
                    //Today I am going to open new Activity for Saving data to firebase
                    final DatabaseReference mDatabase;
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    Log.e("tag-------->", "ha ha ha ha server");
                    Toast.makeText(AdminRegisterEmployee.this, "Successfully Saved", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder ab = new AlertDialog.Builder(AdminRegisterEmployee.this);
                    ab.setTitle("Enter Employee Id");
                    LayoutInflater layoutInflater=getLayoutInflater();
                    View view=layoutInflater.inflate(R.layout.unique_id_taking_alert_dialog,null,false);
                    final EditText editText=view.findViewById(R.id.edit_text_taking_id_as_input);
                    final EditText acc=view.findViewById(R.id.edit_text_account_id_as_input);
                    ab.setView(view);
                    ab.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    ab.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final String uniqueId=editText.getText().toString().trim();
                            final String accountNumberOfEmployee=acc.getText().toString().trim();
                            //asli logic to firebase write here
                            //Validating ID Verification
                            mDatabase.child("Employee_Details").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    long childCount=dataSnapshot.getChildrenCount();
                                    ArrayList<String> keyList=new ArrayList<>((int)childCount);
                                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                                        Log.e("!_@@_Key::>", child.getKey());
                                        keyList.add( child.getKey());
                                    }
                                    if(keyList.contains(uniqueId)){
                                        //here we have to save all details
                                        //Adding image of employee to storage
                                        FirebaseStorage storage = FirebaseStorage.getInstance();
                                        StorageReference storageRef = storage.getReference();
                                        StorageReference employeeImagesRef = storageRef.child(""+uniqueId+".jpg");

                                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                        picOfEmployee.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                        byte[] data = baos.toByteArray();
                                        UploadTask uploadTask = employeeImagesRef.putBytes(data);
                                        uploadTask.addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {
                                                // Handle unsuccessful uploads
                                            }
                                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                            }
                                        });

                                        String tokenOfEmployee="";
                                        Toast.makeText(AdminRegisterEmployee.this, "add details", Toast.LENGTH_SHORT).show();
                                        AddEmployeeDetailsHelper addEmployeeDetailsHelper= new AddEmployeeDetailsHelper(nameOfEmployee,contactNumberOfEmployee,salaryOfEmployee,designationOfEmployee,inTimeOfEmployee,accountNumberOfEmployee,tokenOfEmployee);
                                        mDatabase.child("Employee_Details").child(uniqueId).child("Personal_Details").setValue(addEmployeeDetailsHelper);
                                        finish();
                                    }else{
                                        Toast.makeText(AdminRegisterEmployee.this, "Id not Exist", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    });
                    ab.setCancelable(false);
                    ab.create().show();
                }
            }
        });
        imageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 101);

            }
        });

        inTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(AdminRegisterEmployee.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Toast.makeText(AdminRegisterEmployee.this, "" + hourOfDay + ":" + minute, Toast.LENGTH_SHORT).show();
                        inTimeOfEmployee = hourOfDay + ":" + minute;
                        inTime.setText(inTimeOfEmployee);
                        floatingActionButton.setVisibility(View.VISIBLE);
                    }
                }, system_hour, system_minute, false);
                timePickerDialog.show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "show image", Toast.LENGTH_SHORT).show();
                Bundle b = data.getExtras();
                picOfEmployee = (Bitmap) b.get("data");
                employeePic.setImageBitmap(picOfEmployee);
                imageIcon.setVisibility(View.GONE);
                inTime.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(this, "Failed to capture photo", Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        count = 0;
    }
}
