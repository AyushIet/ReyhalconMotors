package com.reyhalconmotors;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewEmployeeShowActivity extends AppCompatActivity {
    EditText editText;
    Button button, button1;
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    int input;
    private DatabaseReference mDatabase, rec_database;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    ArrayList arrayList, keys;
    boolean arrayListStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_employee_show);
        editText = findViewById(R.id.edit_text_taking_id_name);
        button = findViewById(R.id.button_view_employee);
        button1 = findViewById(R.id.button_view_employee_name);
        recyclerView = findViewById(R.id.recycler_view_show_all);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        rec_database = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        Intent intent = getIntent();
        savedInstanceState = intent.getExtras();
        input = savedInstanceState.getInt("input");
        if (input == 0) {
            showAll();

        }
        if (input == 1) {
            editText.setVisibility(View.VISIBLE);
            editText.setHint("Enter Id");
            button.setVisibility(View.VISIBLE);
            button.setText("Ok");
            showById();
        }
        if (input == 2) {
            editText.setVisibility(View.VISIBLE);
            button1.setVisibility(View.VISIBLE);
            button1.setText("Ok");
            showByName();
        }
    }

    public void showAll() {
        //here we have to access all the data
        mDatabase.child("Employee_Details").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long noOfEmployee = dataSnapshot.getChildrenCount();
                arrayList = new ArrayList((int) noOfEmployee);
                keys=new ArrayList((int) noOfEmployee);
                Toast.makeText(ViewEmployeeShowActivity.this, "no of child--" + noOfEmployee, Toast.LENGTH_SHORT).show();
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    keys.add(snapshot.getKey());
                    //VALUE EVENT LISTENER TO THE KEY FIND BY VALUE EVENT LISTENER
                }
                Toast.makeText(ViewEmployeeShowActivity.this, "size of keys--" + keys.size(), Toast.LENGTH_SHORT).show();
                confirm(keys);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void showById() {
        mDatabase.child("Employee_Details").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AddEmployeeDetailsHelper addEmployeeDetailsHelper = dataSnapshot.getValue(AddEmployeeDetailsHelper.class);
                //extract value from here
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void showByName() {
        //To be implemented Later
    }

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = getLayoutInflater().inflate(R.layout.view_all_employee, parent, false);
            CustomViewHolder customViewHolder = new CustomViewHolder(v);
            return customViewHolder;
        }

        @Override
        public void onBindViewHolder(CustomViewHolder holder, int position) {
            AddEmployeeDetailsHelper addEmployeeDetailsHelper = (AddEmployeeDetailsHelper) arrayList.get(position);
            String name = addEmployeeDetailsHelper.getNameOfEmployee();
            Toast.makeText(ViewEmployeeShowActivity.this, name, Toast.LENGTH_SHORT).show();
            String designation = addEmployeeDetailsHelper.getDesignationOfEmployee();
            String salary = addEmployeeDetailsHelper.getSalaryOfEmployee();
            holder.tv1.setText(name);
            holder.tv2.setText(designation);
            holder.tv3.setText(salary);

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        class CustomViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView tv1, tv2, tv3, tv4;

            CustomViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.recycler_view_image_view);
                tv1 = itemView.findViewById(R.id.recycler_view_name);
                tv2 = itemView.findViewById(R.id.recycler_view_designation);
                tv3 = itemView.findViewById(R.id.recycler_view_salary);
                tv4 = itemView.findViewById(R.id.recycler_view_today_attendance);
            }
        }
    }

    public void confirm(ArrayList keys) {
        for(int i=0;i<keys.size();i++){
            String iud=(String)keys.get(i);
            rec_database.child("Employee_Details").child(iud).child("Personal_Details").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    AddEmployeeDetailsHelper addEmployeeDetailsHelper=dataSnapshot.getValue(AddEmployeeDetailsHelper.class);
                    arrayList.add(addEmployeeDetailsHelper);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        Toast.makeText(this, "Size of ArrayList after--"+arrayList.size(), Toast.LENGTH_SHORT).show();
        //recyclerView.setVisibility(View.VISIBLE);
        //recyclerView.setAdapter(new CustomAdapter());
    }
}
