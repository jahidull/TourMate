package java.com.bikash.bloodbank;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lazy_programmer.tourmate.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static java.com.bikash.bloodbank.MainActivity.database;

public class DonorForm extends AppCompatActivity {
    Spinner cityChoice;
    Spinner groupChoice, genderChoice;

    EditText Name;
    EditText lastDonate, age, weight;

    Button Save;

    ProgressBar progressBar;

   MapsActivity mm = new MapsActivity();

    private FusedLocationProviderClient client;
      FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_form);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        disableNavigationViewScrollbars(navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_profile) {
                    Intent i = new Intent(DonorForm.this, MainActivity.class);
                    startActivity(i);
                } else if (id == R.id.nav_chat) {
                    Intent i = new Intent(DonorForm.this, MainActivity.class);
                    startActivity(i);
                } else if (id == R.id.nav_contact) {

                    Intent i = new Intent(DonorForm.this, MainActivity.class);
                    startActivity(i);

                } else if (id == R.id.nav_online) {

                    Intent i = new Intent(DonorForm.this, MainActivity.class);
                    startActivity(i);

                } else if (id == R.id.nav_logout) {
                    FirebaseAuth.getInstance().signOut();
                    Intent i = new Intent(DonorForm.this, MainActivity.class);
                    startActivity(i);
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                assert drawer != null;
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });




        progressBar = (ProgressBar)findViewById(R.id.pbar);
        progressBar.setVisibility(View.INVISIBLE);

        client = LocationServices.getFusedLocationProviderClient(this);
         mAuth = FirebaseAuth.getInstance();
        cityChoice = (Spinner) findViewById(R.id.dropdownCity);

        String[] citis = new String[]{"Barisal","Chittagong", "Dhaka", "Mymensingh","Khulna", "Rajshahi", "Rangpur", "Sylhet"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, citis);
        cityChoice.setAdapter(adapter);


        groupChoice = (Spinner) findViewById(R.id.dropdownGroup);
        String[] group = new String[]{"O+","O-", "A+", "B+","A-", "B-", "AB+", "AB-"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, group);
        groupChoice.setAdapter(adapter1);

        genderChoice = (Spinner) findViewById(R.id.gender);
        final String[] genderGroup = new String[]{"Male","Female","Other"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, genderGroup);
        genderChoice.setAdapter(adapter2);

        Name = (EditText) findViewById(R.id.edt_name);
        lastDonate = (EditText) findViewById(R.id.lastDonation);
        age = (EditText) findViewById(R.id.edit_age);
        weight = (EditText) findViewById(R.id.edit_weight);
        Save = (Button) findViewById(R.id.btn_saveDonor);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager cmv = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo info = cmv.getActiveNetworkInfo();
                if(info !=null && info.isConnected())
                {
                    // Toast.makeText(LoginActivity.this, "Internet Connection Field", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(DonorForm.this, "Please Turn On Your Internet Connection", Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.VISIBLE);

                if (ActivityCompat.checkSelfPermission(DonorForm.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

                    return;
                }
                client.getLastLocation().addOnSuccessListener(DonorForm.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if(location!= null){
                            String name = Name.getText().toString();
                            String city = cityChoice.getSelectedItem().toString();
                            String group = groupChoice.getSelectedItem().toString();
                            String genderString = genderChoice.getSelectedItem().toString();
                            String ageString = age.getText().toString();
                            String weightString = weight.getText().toString();
                            String last = lastDonate.getText().toString();
                            Double lat = location.getLatitude();
                            Double lng = location.getLongitude();
                            String mobile = mAuth.getCurrentUser().getPhoneNumber().toString();

                            if(name.isEmpty())
                            {
                                Name.setError("Must Need Name");
                                Name.requestFocus();
                                return;
                            }
                            if(ageString.isEmpty())
                            {
                                age.setError("Must Need Age");
                                age.requestFocus();
                                return;
                            }
                            if(weightString.isEmpty())
                            {
                                weight.setError("Must Need Weight");
                                weight.requestFocus();
                                return;
                            }
                            if(last.isEmpty())
                            {
                                last = "Null";

                            }

                            String id = mAuth.getInstance().getCurrentUser().getUid();
                            Donor donor = new Donor(name,mobile,group,city,lat.toString(), lng.toString(),genderString, last,ageString,weightString);
                            DatabaseReference myRef = database.getReference("donors");
                            myRef.child(city).child(group).push().setValue(donor);

                            DatabaseReference myRefAll = database.getReference("all donors");
                            myRefAll.push().setValue(donor);

                            DonorAll donorAll = new DonorAll(name,mobile,group,city,lat.toString(), lng.toString(),genderString, last,ageString,weightString);
                            DatabaseReference myRefProfile = database.getReference("Donar Profile");
                            myRefProfile.child(id).push().setValue(donorAll);
                            progressBar.setVisibility(View.GONE);

                            finish();
                        }

                    }
                });





            }
        });

    }

    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }
}
