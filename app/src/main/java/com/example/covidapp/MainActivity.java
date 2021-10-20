package com.example.covidapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covidapp.HealthAdmin.AvailableTimesListUserClass;
import com.example.covidapp.LogIn.LoginFragment;
import com.example.covidapp.UserReg.RegClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.covidapp.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Boolean isLoggedIn = false;
    Button buttonDashboard,buttonfaq,buttonUserLogin,buttonMainscreen2,buttonMyPage,buttonMyAppointments,buttonpassport,buttonuserreg,buttonquestresp, logout, buttonadminaddtimes;
    private FirebaseAuth firebaseAuth;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        binding.appBarMain.fab.hide();
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_my_page, R.id.nav_dashboard, R.id.nav_faq, R.id.nav_login,R.id.nav_admin_menu)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        onLogout();
//        loggedOut();
//        binding.navView.getMenu().findItem(R.id.nav_login).setTitle("Logga in");
//        binding.navView.getMenu().findItem(R.id.nav_my_page).setVisible(false);
//        binding.navView.getMenu().findItem(R.id.nav_dashboard).setVisible(false);
//        binding.navView.getMenu().findItem(R.id.nav_faq).setVisible(false);

        binding.navView.getMenu().findItem(R.id.nav_login).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(isLoggedIn) {
                    loggedOut();
                }
            return false;
            }
        });

    }

    //Changes the drawer menu and sets isLoggedIn to false
    public void onUserLogin() {
        user_loggedin();
        binding.navView.getMenu().findItem(R.id.nav_login).setTitle("Logga ut");
        binding.navView.getMenu().findItem(R.id.nav_my_page).setVisible(true);
        binding.navView.getMenu().findItem(R.id.nav_dashboard).setVisible(true);
        binding.navView.getMenu().findItem(R.id.nav_faq).setVisible(true);
        isLoggedIn = true;
    }

    //Changes drawer menu items shown for admin login
    public void onAdminLogin() {
        user_loggedin();
        binding.navView.getMenu().findItem(R.id.nav_login).setTitle("Logga ut");
        binding.navView.getMenu().findItem(R.id.nav_admin_menu).setVisible(true);
        //temporary
        binding.navView.getMenu().findItem(R.id.nav_my_page).setVisible(true);
        binding.navView.getMenu().findItem(R.id.nav_dashboard).setVisible(true);
        binding.navView.getMenu().findItem(R.id.nav_faq).setVisible(true);
        isLoggedIn = true;
    }

    //Logs out the user, changes the drawer menu items and sets isLoggedIn to false
    private void loggedOut() {
        FirebaseAuth.getInstance().signOut();
        Log.i("Error", "User successfully logged out!"); //logging
        Toast.makeText(getBaseContext(), "Du är nu utloggad!", Toast.LENGTH_SHORT).show(); // print that the user logged out.
        onLogout();
//        binding.navView.getMenu().findItem(R.id.nav_login).setTitle("Logga in");
//        binding.navView.getMenu().findItem(R.id.nav_my_page).setVisible(false);
//        binding.navView.getMenu().findItem(R.id.nav_dashboard).setVisible(false);
//        binding.navView.getMenu().findItem(R.id.nav_faq).setVisible(false);
        isLoggedIn = false;
    }

    //Changes the drawer menu items
    public void onLogout() {
        user_loggedin();
        binding.navView.getMenu().findItem(R.id.nav_login).setTitle("Logga in");
        binding.navView.getMenu().findItem(R.id.nav_my_page).setVisible(false);
        binding.navView.getMenu().findItem(R.id.nav_admin_menu).setVisible(false);
        binding.navView.getMenu().findItem(R.id.nav_dashboard).setVisible(false);
        binding.navView.getMenu().findItem(R.id.nav_faq).setVisible(false);
    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main_activity3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:

                return true;
            case R.id.OtherItem:
                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.nav_admin_add_available_times);
                return true;
            case R.id.TestDriveAdmin:
                NavController navController2 = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
                navController2.navigate(R.id.ConnectedTimesFragment);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onStop() {
        File file1 = new File(getExternalFilesDir("Download"), "Folkhalsomyndigheten_Covid19_Vaccine.xlsx");
        File file2 = new File(getExternalFilesDir("Download"), "v37-leveranser-av-covid-vaccin-till-och-med-vecka-39.xlsx");
        if(file1.exists()){
            Log.i("info", "Deleting file " + file1);
            file1.delete();
        }if(file2.exists()){
            Log.i("info", "Deleting file " + file2);
            file2.delete();
        }
        super.onStop();
    }
    protected void user_loggedin()
    {
        //set username
        NavigationView navigationView1 = (NavigationView)findViewById(R.id.nav_view);
        View headerView = navigationView1.getHeaderView(0);
        TextView navUsername = (TextView)headerView.findViewById(R.id.user_id);
        navUsername.setText("");
        //set email
        TextView navEmail = (TextView) headerView.findViewById(R.id.user_Email);
        navEmail.setText("");
        //getting user id/email from firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://covidid-14222-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference ref = database.getReference("User");
        ArrayList<RegClass> regclass = new ArrayList<>();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            FirebaseAuth firebaseAuth1 = FirebaseAuth.getInstance();
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (firebaseAuth1.getCurrentUser() == null) {
                    return;
                }
                else{
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        RegClass regclasses = dataSnapshot1.getValue(RegClass.class);
                        if (regclasses.getUserID().equals(firebaseAuth1.getUid())) {
                            String UID = regclasses.getFirstname() + " " + regclasses.getLastname();

                            navUsername.setText(UID);
                        }
                    }
                    String EMAIL = firebaseAuth1.getCurrentUser().getEmail();

                    navEmail.setText(EMAIL);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}