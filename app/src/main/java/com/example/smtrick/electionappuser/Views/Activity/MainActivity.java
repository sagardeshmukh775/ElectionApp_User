package com.example.smtrick.electionappuser.Views.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smtrick.electionappuser.Callback.CallBack;
import com.example.smtrick.electionappuser.Listeners.OnFragmentInteractionListener;
import com.example.smtrick.electionappuser.R;
import com.example.smtrick.electionappuser.Repositories.Impl.LeedRepositoryImpl;
import com.example.smtrick.electionappuser.Repositories.Impl.UserRepositoryImpl;
import com.example.smtrick.electionappuser.Repositories.LeedRepository;
import com.example.smtrick.electionappuser.Models.Users;
import com.example.smtrick.electionappuser.Repositories.UserRepository;
import com.example.smtrick.electionappuser.Views.Fragments.Tab_Fragment;
import com.example.smtrick.electionappuser.Views.ProgressDialogClass;
import com.example.smtrick.electionappuser.preferences.AppSharedPreference;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity
        implements OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener{

    private NavigationView navigationView;
    private Fragment selectedFragement;
    private FirebaseAuth firebaseAuth;
    private AppSharedPreference appSharedPreference;
    //user

    private String uid;
    private FirebaseUser Fuser;
    private DatabaseReference databaseReference;

    private TextView userEmail;
    private TextView username;
    String Language,Userid,acctemail,acctname;
    private Menu top_menu;
    Users user;
    LeedRepository leedRepository;
    UserRepository userRepository;
    ProgressDialogClass progressDialog;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (isNetworkAvailable()){
//            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        leedRepository = new LeedRepositoryImpl();
        user = new Users();
        appSharedPreference = new AppSharedPreference(this);
        userRepository = new UserRepositoryImpl();
        progressDialog = new ProgressDialogClass(this);
        // NOTE : Just remove the fab button
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        Boolean per = isStoragePermissionGranted();
        if (per){
            //   Toast.makeText(this, "Storage Premission Granted", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Storage Premission Required", Toast.LENGTH_SHORT).show();
        }

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //NOTE:  Checks first item in the navigation drawer initially
        navigationView.setCheckedItem(R.id.home);
        View headerview = navigationView.getHeaderView(0);
        username = (TextView) headerview.findViewById(R.id.username);
        userEmail = (TextView) headerview.findViewById(R.id.useremail);

        FirebaseMessaging.getInstance().subscribeToTopic("Products");

        getCurrentuserdetails();

        //setMenuTitles();

        //NOTE:  Open fragment1 initially.
        selectedFragement = new Tab_Fragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame, selectedFragement);
        ft.commit();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                //   Log.v(TAG,"Permission is granted");
                return true;
            } else {

                // Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    return true;
                }else {
                    return false;
                }
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            //  Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),
                            "Permission Granted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Permission Denied", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }

    private void getCurrentuserdetails() {

        progressDialog.showDialog(getString(R.string.loading), getString(R.string.PLEASE_WAIT));
        userRepository.readUserByUserId(appSharedPreference.getUserId(), new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    user = (Users) object;
                    acctname = user.getName();
                    acctemail = user.getEmail();
                    Language = user.getLanguage();
                    Userid = user.getUserId();
                    username.setText(acctname);
                    userEmail.setText(acctemail);
                    if (Language != null) {
                        if (Language.equalsIgnoreCase("Marathi")) {
                            setLanguage();
                        }
                    }
                    progressDialog.dismissDialog();
                }
            }

            @Override
            public void onError(Object object) {

            }
        });
    }

    private void setLanguage() {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        //NOTE: creating fragment object
        Fragment fragment = null;
        if (id == R.id.home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,
                    new Tab_Fragment()).commit();


        }
        else if (id == R.id.logout) {

            // clearDataWithSignOut();
            FirebaseAuth.getInstance().signOut();
            finish();
            appSharedPreference.clear();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));

        }

        //NOTE: Fragment changing code
        selectedFragement = fragment;
        if (fragment != null) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, fragment);
            ft.commit();

        }

        //NOTE:  Closing the drawer after selecting
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout); //Ya you can also globalize this variable :P
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onFragmentInteraction(String title) {

        // NOTE:  Code to replace the toolbar title based current visible fragment
        getSupportActionBar().setTitle(title);
    }


    @Override
    public void changeFragement(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, fragment);
            ft.commit();
        }
    }



    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}