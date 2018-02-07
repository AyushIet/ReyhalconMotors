package com.reyhalconmotors;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class EmployeeLoginActivity extends AppCompatActivity {
    EditText log_email,log_password,log_name,reg_email,reg_password,re_reg_password;
    CardView log_card_view,reg_card_view;
    Button log_login,log_signup;
    private FirebaseAuth mAuth;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            //open activity to show used details
            Toast.makeText(this, "update UI", Toast.LENGTH_SHORT).show();
            String uId=currentUser.getUid();
            Intent intent=new Intent(EmployeeLoginActivity.this,EmployeeHomePage.class);
            intent.putExtra("user",uId);
            startActivity(intent);
            finish();
            Toast.makeText(this, ""+uId, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_login);
        mAuth = FirebaseAuth.getInstance();
        log_card_view=findViewById(R.id.employee_login_card_layout1);
        log_name=findViewById(R.id.employee_login_name);
        log_email=findViewById(R.id.employee_login_email);
        log_password=findViewById(R.id.employee_login_password);
        log_login=findViewById(R.id.employee_login_button_submit);
        reg_card_view=findViewById(R.id.employee_login_card_layout2);
        reg_email=findViewById(R.id.employee_login_email2);
        reg_password=findViewById(R.id.employee_login_password2);
        re_reg_password=findViewById(R.id.employee_login_password3);
        log_signup=findViewById(R.id.employee_login_button_login);
    }

    public void showSignUpPage(View view) {
        log_card_view.setVisibility(View.GONE);
        reg_card_view.setVisibility(View.VISIBLE);
    }

    public void signUp(View view) {
        final String name=log_name.getText().toString().trim();
        String email=reg_email.getText().toString().trim();
        String password=reg_password.getText().toString().trim();
        String rePassword=re_reg_password.getText().toString().trim();
        if(name.isEmpty()){
            log_name.setError("Empty");
            log_name.requestFocus();
        }else{
            if(email.isEmpty()){
                reg_email.setError("Empty");
                reg_email.requestFocus();
            }else{
                if(password.isEmpty()){
                    reg_password.setError("Empty");
                    reg_password.requestFocus();
                }else{
                    if(rePassword.isEmpty()){
                        re_reg_password.setError("Empty");
                        re_reg_password.requestFocus();
                    }else{
                        if(password.equals(rePassword)){
                            //save to firebase
                            mAuth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                // Sign in success, update UI with the signed-in user's information
                                                Log.d("aaaa--->", "createUserWithEmail:success");
                                                FirebaseUser user = mAuth.getCurrentUser();
                                                String uId=user.getUid();
                                                mAuth.signOut();
                                                //Empty the Field
                                                reg_email.setText("");
                                                reg_password.setText("");
                                                re_reg_password.setText("");
                                                //saving id to database
                                                DatabaseReference mDatabase;
                                                mDatabase = FirebaseDatabase.getInstance().getReference();
                                                mDatabase.child("Employee_Details").child(user.getUid()).setValue(name);
                                                //updateUI(user);
                                                Intent intent=new Intent(EmployeeLoginActivity.this,EmployeeWriteID.class);
                                                intent.putExtra("id",uId);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                // If sign in fails, display a message to the user.
                                                Log.d("bbbb--->", "createUserWithEmail:failure", task.getException());
                                                //updateUI(null);
                                            }

                                            // ...
                                        }
                                    });
                            Toast.makeText(this, "save to firebase", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(this, "Password Not Matched", Toast.LENGTH_SHORT).show();
                            reg_password.setText("");
                            re_reg_password.setText("");
                            reg_password.requestFocus();
                        }
                    }
                }
            }
        }
    }

    public void login(View view) {
        String email=log_email.getText().toString().trim();
        String password=log_password.getText().toString().trim();
        if(email.isEmpty()){
            log_email.setText("Empty");
            log_email.requestFocus();
        }else{
            if(password.isEmpty()){
                log_password.setError("");
                log_password.requestFocus();
            }else{
                //Validate by Firebase
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("aaa---->", "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(EmployeeLoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                    String userId=user.getUid();
                                    log_email.setText("");
                                    log_password.setText("");


                                    //updateUI(user);
                                    //open the Activity that show employee Details

                                    Intent intent=new Intent(EmployeeLoginActivity.this,EmployeeHomePage.class);
                                    intent.putExtra("user",userId);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("bbb---->", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(EmployeeLoginActivity.this, "Not Login", Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }
                            }
                        });
            }
        }
    }
}
