package com.myapplicationdev.android.l8pslocatingaplace;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = findViewById(R.id.spinner);

        /* TODO: You can use this adapter to provide views for an AdapterView.
            Returns a view for each object in a collection of data objects you provide,
            and can be used with list-based user interface widgets such as ListView or Spinner*/
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.branches, android.R.layout.simple_spinner_item);

        // Sets the layout resource to create the drop down views.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Sets the SpinnerAdapter used to provide the data which backs this Spinner.
        spinner.setAdapter(adapter);

        // Interface for interacting with Fragment objects inside of an Activity
        FragmentManager fm = getSupportFragmentManager();

        // An app's Map component. This is the simplest approach to include a map in an application.
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);

        // An assertion allows testing the correctness of any assumptions that have been made in the program.
        assert mapFragment != null;


       /* TODO: mapFragment.getMapAsync() returns a reference to a Google Map object.
            When the object is entirely loaded, it will be ready.
            Because the time required for this object is dependent on the Internet connection,
            it is tracked individually.*/
        mapFragment.getMapAsync(googleMap -> {
            map = googleMap;

            assert map != null;

            // Sets the map's type to Basic map.
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);


            /* TODO:Objects that are immutable and represent a pair of
                 latitude and longitude coordinates saved as degrees.*/
            LatLng downtownCore = new LatLng(1.295416599631411, 103.86085283827651);
            LatLng serangoon = new LatLng(1.3510765477411812, 103.87012575316815);
            LatLng eastCoast = new LatLng(1.3089197404107595, 103.905462077644);
            LatLng singapore = new LatLng(1.3521, 103.8198);


            //TODO: Objects provide methods for building CameraUpdate objects, which change the camera on a map.
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(singapore, 11));

            /* TODO: Icon objects that are placed at specific points on the map's surface. */
            Marker central = map.addMarker(new MarkerOptions()
                    .position(downtownCore)
                    .title("HQ-Central")
                    .snippet("Downtown Core")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            );
            Marker north = map.addMarker(new MarkerOptions()
                    .position(serangoon)
                    .title("North-HQ")
                    .snippet("332 Serangoon Ave 3, Block 332, Singapore 550332")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
            Marker east = map.addMarker(new MarkerOptions()
                    .position(eastCoast)
                    .title("East-HQ")
                    .snippet("Tembeling Rd")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));


            map.setOnMarkerClickListener(marker -> {

                // TODO: provide a view with a quick tiny message for the user to see information about a location
                Toast.makeText(MainActivity.this, marker.getTitle(), Toast.LENGTH_SHORT).show();

                return false;
            });


            // TODO: Set a callback that will be called when an item in this AdapterView (spinner) is selected.
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // your code here
                    if (position == 0) {
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(singapore, 11));
                    } else if (position == 1) {
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(serangoon, 15));
                    } else if (position == 2) {
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(downtownCore, 15));
                    } else if (position == 3) {
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(eastCoast, 15));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });

            /* TODO: Settings for the user interface of a GoogleMap.*/
            UiSettings ui = map.getUiSettings();

            // Configuring the Compass and ZoomControls
            ui.setCompassEnabled(true);
            ui.setZoomControlsEnabled(true);

            // Determine whether you have been granted a particular permission.
            int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);

            /* TODO: If permission is granted, the ability to set the map location will be enabled.*/
            if (permissionCheck == PermissionChecker.PERMISSION_GRANTED) {
                map.setMyLocationEnabled(true);

                //If this permission is not granted,
                // the class activity will request that permissions be granted to this application.
            } else {
                Log.e("Gmap-Permission", "Gps access has not been granted");
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            }


        });
    }
}