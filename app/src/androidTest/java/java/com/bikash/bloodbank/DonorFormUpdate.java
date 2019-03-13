package java.com.bikash.bloodbank;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.lazy_programmer.tourmate.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static java.com.bikash.bloodbank.MainActivity.database;

public class DonorFormUpdate extends AppCompatActivity {
    Spinner cityChoice;
    Spinner groupChoice, genderChoice;

    TextView Name;
    EditText lastDonate;

    Button Save;

    ProgressBar progressBar;

   MapsActivity mm = new MapsActivity();

    private FusedLocationProviderClient client;
      FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_form_update);
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

        Name = (TextView) findViewById(R.id.edt_name);
        lastDonate = (EditText) findViewById(R.id.lastDonation);
        Save = (Button) findViewById(R.id.btn_saveDonor);

        DatabaseReference myRef = database.getReference("donors");
        myRef.child("city").child("group").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Donor donor = dataSnapshot.getValue(Donor.class);
                String donorInfo = donor.name;
                Name.setText(donorInfo);

            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (ActivityCompat.checkSelfPermission(DonorFormUpdate.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

                    return;
                }
                client.getLastLocation().addOnSuccessListener(DonorFormUpdate.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if(location!= null){
                            String name = Name.getText().toString();
                            String city = cityChoice.getSelectedItem().toString();
                            String group = groupChoice.getSelectedItem().toString();
                            String genderString = genderChoice.getSelectedItem().toString();
                            String last = lastDonate.getText().toString();
                            Double lat = location.getLatitude();
                            Double lng = location.getLongitude();
                            String mobile = mAuth.getCurrentUser().getPhoneNumber().toString();

                          //  Donor donor = new Donor(name,mobile,group,city,lat.toString(), lng.toString(),genderString, last);
                           // DatabaseReference myRef = database.getReference("donors");
                          //  myRef.child(city).child(group).push().setValue(donor);

                            finish();
                        }

                    }
                });





            }
        });

    }
}
