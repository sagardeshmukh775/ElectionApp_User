package com.example.smtrick.electionappuser.Views.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smtrick.electionappuser.Models.Users;
import com.example.smtrick.electionappuser.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText inputNumber;
    private Button btnReset, btnBack;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private DatabaseReference mDatabase;
    String key;
    LinearLayout layout_Update;
    EditText inputPassword;
    Button btnUpdatePAssword;
    Users user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        inputNumber = (EditText) findViewById(R.id.number);
//        inputName = (EditText) findViewById(R.id.name);
        btnReset = (Button) findViewById(R.id.btn_reset_password);
        btnBack = (Button) findViewById(R.id.btn_back);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        inputPassword = (EditText) findViewById(R.id.password);
        layout_Update = (LinearLayout) findViewById(R.id.layout_update_password);
        btnUpdatePAssword = (Button) findViewById(R.id.btn_update_password);

        layout_Update.setVisibility(View.GONE);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number = inputNumber.getText().toString().trim();
//                String name = inputNumber.getText().toString().trim();

                if (TextUtils.isEmpty(number)) {
                    Toast.makeText(getApplication(), "Enter your registered Mobile Number", Toast.LENGTH_SHORT).show();
                    return;
                }
//                if (TextUtils.isEmpty(name)) {
//                    Toast.makeText(getApplication(), "Enter your registered Name", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                progressBar.setVisibility(View.VISIBLE);

                Query query6 = FirebaseDatabase.getInstance().getReference("users")
                        .orderByChild("mobileNumber")
                        .equalTo(number);

                query6.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.hasChildren()) {
                            for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                                user = Snapshot.getValue(Users.class);
                                if (user != null) {
                                    layout_Update.setVisibility(View.VISIBLE);
                                    key = user.getUserId();
                                    progressBar.setVisibility(View.GONE);
                                } else {
                                    Toast.makeText(ResetPasswordActivity.this, "Sorry No User Found", Toast.LENGTH_SHORT).show();
                                    layout_Update.setVisibility(View.GONE);
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        }else {
                            Toast.makeText(ResetPasswordActivity.this, "Sorry No User Found", Toast.LENGTH_SHORT).show();
                            layout_Update.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        btnUpdatePAssword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwprd = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(passwprd)) {
                    Toast.makeText(getApplication(), "Enter your Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("users").child(key).child("password").setValue(passwprd);
                Toast.makeText(ResetPasswordActivity.this, "Password Updated", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(ResetPasswordActivity.this,LoginActivity.class);
                startActivity(intent);

            }
        });
    }

}
