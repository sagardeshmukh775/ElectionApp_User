package com.example.smtrick.electionappuser.Views.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smtrick.electionappuser.Callback.CallBack;
import com.example.smtrick.electionappuser.Constants.Constants;
import com.example.smtrick.electionappuser.Models.Users;
import com.example.smtrick.electionappuser.R;
import com.example.smtrick.electionappuser.Repositories.Impl.UserRepositoryImpl;
import com.example.smtrick.electionappuser.Repositories.UserRepository;
import com.example.smtrick.electionappuser.Utils.ExceptionUtil;
import com.example.smtrick.electionappuser.Utils.Utility;
import com.example.smtrick.electionappuser.Views.ProgressDialogClass;
import com.example.smtrick.electionappuser.preferences.AppSharedPreference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    TextView txtregister;
    Button login, Register,btnReset;
    EditText etMobileNumber, etpassword;
    private AppSharedPreference appSharedPreference;
    private UserRepository userRepository;
    private ProgressDialogClass progressDialog;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set the view now
        setContentView(R.layout.activity_login);

        userRepository = new UserRepositoryImpl(this);
        appSharedPreference = new AppSharedPreference(this);
        checkLoginState();
        Register = (Button) findViewById(R.id.btn_signup);
        login = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);
        etMobileNumber = (EditText) findViewById(R.id.mobilenumber);
        etpassword = (EditText) findViewById(R.id.password);

        Register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                login1();
            }
        });
        etMobileNumber.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                Animation zoomOutAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomin);
                etMobileNumber.startAnimation(zoomOutAnimation);
                return false;
            }
        });
        etpassword.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                Animation zoomOutAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomin);
                etpassword.startAnimation(zoomOutAnimation);
                return false;
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });
    }

    public void checkLoginState() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (appSharedPreference != null && appSharedPreference.getUserLoginStatus()) {
                        if (appSharedPreference.getRegId() != null && appSharedPreference.getUserId() != null) {
                            loginToApp();
                        }
                    }
                } catch (Exception e) {
                    ExceptionUtil.logException( e);
                }
            }
        });
    }

    private void loginToApp() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void login1() {
        progressDialog = new ProgressDialogClass(this);
        progressDialog.showDialog(this.getString(R.string.SIGNING_IN), this.getString(R.string.PLEASE_WAIT));
        String code = "91";
        String number = etMobileNumber.getText().toString().trim();
        final String username = etpassword.getText().toString().trim();

        if (number.isEmpty() || number.length() < 10) {
            etMobileNumber.setError("Valid number is required");
            etMobileNumber.requestFocus();
            return;
        }
        if (username.isEmpty()) {
            etpassword.setError("Password is required");
            etpassword.requestFocus();
            return;
        }

        final String phoneNumber = number;


        DatabaseReference Dref = FirebaseDatabase.getInstance().getReference("users");
        Dref.orderByChild("mobileNumber").equalTo(phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() != null) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                        Users user = postSnapshot.getValue(Users.class);

                        progressDialog.dismissDialog();
                        if (user.getPassword().equalsIgnoreCase(etpassword.getText().toString())) {
                            String userid = user.getUserId();
                            appSharedPreference.createUserLoginSession();
                            appSharedPreference.addUserDetails(user);
                            Toast.makeText(LoginActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                            loginToApp();
                        }else {
                            Toast.makeText(LoginActivity.this, "Sorry Wrong Password", Toast.LENGTH_SHORT).show();
                        }

                        //                        signInUserData(userid);

                    }


                } else {
                    progressDialog.dismissDialog();
                    Toast.makeText(LoginActivity.this, "Login failed Please Register", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void signInUserData(final String userId) {
        userRepository.readUserByUserId(userId, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    Users user = (Users) object;
                    appSharedPreference.createUserLoginSession();
                    appSharedPreference.addUserDetails(user);
                    loginToApp();
                } else {
                    Utility.showTimedSnackBar(activity,etpassword, getMessage(R.string.login_fail_try_again));
                }
                if (progressDialog != null)
                    progressDialog.dismissDialog();
            }

            @Override
            public void onError(Object object) {
                if (progressDialog != null)
                    progressDialog.dismissDialog();
                Utility.showTimedSnackBar(activity,etpassword, getMessage(R.string.login_fail_try_again));
            }
        });
    }

    private String getMessage(int id) {
        return getString(id);
    }

    private boolean validate(String mobileNumber, String password) {
        String validationMessage;
        boolean isValid = true;
        try {
            if (Utility.isEmptyOrNull(mobileNumber)) {
                validationMessage = getString(R.string.MOBILE_NUMBER_SHOULD_NOT_BE_EMPTY);
                etMobileNumber.setError(validationMessage);
                isValid = false;
            } else if (!Utility.isValidMobileNumber(mobileNumber)) {
                validationMessage = getMessage(R.string.INVALID_MOBILE_NUMBER);
                etMobileNumber.setError(validationMessage);
                isValid = false;
            }
            if (Utility.isEmptyOrNull(password)) {
                validationMessage = getString(R.string.PASSWORD_SHOULD_NOT_BE_EMPTY);
                etpassword.setError(validationMessage);
                isValid = false;
            }
        } catch (Exception e) {
            isValid = false;
            ExceptionUtil.logException( e);
        }
        return isValid;
    }
}

