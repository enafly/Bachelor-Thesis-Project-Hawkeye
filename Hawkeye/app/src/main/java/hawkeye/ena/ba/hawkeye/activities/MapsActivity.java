package hawkeye.ena.ba.hawkeye.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import hawkeye.ena.ba.hawkeye.R;
import hawkeye.ena.ba.hawkeye.model.Station;
import hawkeye.ena.ba.hawkeye.model.TransportVehicle;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, LocationListener, GoogleMap.OnMyLocationButtonClickListener {

    private GoogleMap mMap;
    private LatLng Sarajevo = new LatLng(43.8563, 18.4131);
    public static final int REQUEST_ID_ACCESS_COURSE_FINE_LOCATION = 100;
    private ProgressDialog myProgress;
    private static final String MYTAG = "MYTAG";
    Location myLocation;
    String option = ListActivity.option;
    LocationManager locationManager;
    String locationProvider;
    MarkerOptions markerPosition = new MarkerOptions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Create Progress Bar.
        myProgress = new ProgressDialog(this);
        myProgress.setTitle("Map Loading ...");
        myProgress.setMessage("Please wait...");
        myProgress.setCancelable(true);
        // Display Progress Bar.
        myProgress.show();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show());
//
//
//        <android.support.design.widget.FloatingActionButton
//        android:id="@+id/fab"
//        android:layout_width="wrap_content"
//        android:layout_height="wrap_content"
//        android:layout_gravity="end|bottom"
//        android:layout_margin="@dimen/fab_margin"
//        app:srcCompat="@android:drawable/ic_dialog_email" />
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        onMyMapReady();
        addMarkers(mMap);

//        mMap.addMarker(new MarkerOptions().position(Sarajevo).title("Sarajevo"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(Sarajevo));
//        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15005);
//        mMap.animateCamera(zoom);

        mMap.setOnMarkerClickListener(this);
    }

    private void addMarkers(GoogleMap mMap) {
        ArrayList<Station> stations = MainActivity.stations;
        for (Station station : stations) {
            Log.i("OnMapReady", "Stanica " + station.getName());
            //Odvoji stanice na kojima ima jedan ili vise tip option prevoza

            if (addOptionalMarkers(station)) {
                mMap.addMarker(new MarkerOptions()
                        .position(station.getCoordinates())
                        .title(station.getName())
                        .icon(setIconByOption())
                ).setTag(station.getId());
            }
        }
    }

    private BitmapDescriptor setIconByOption() {
        switch (option) {
            case "ALL":
                return BitmapDescriptorFactory.fromResource(R.mipmap.stanica);
            case "A":
                return BitmapDescriptorFactory.fromResource(R.mipmap.stanica_bus);
            case "M":
                return BitmapDescriptorFactory.fromResource(R.mipmap.stanica_minibus);
            case "TR":
                return BitmapDescriptorFactory.fromResource(R.mipmap.stanica_trolejbus);
            case "T":
                return BitmapDescriptorFactory.fromResource(R.mipmap.stanica_tramvaj);
            default:
                return BitmapDescriptorFactory.fromResource(R.mipmap.ic_ciko);
        }
    }

    private boolean addOptionalMarkers(Station station) {
        if ("ALL".equals(option))
            return true;
        else {
            //Ako ima u transport vehicles stanice bar jedan sa option, dodaj je na mapu
            for (TransportVehicle vehicle : station.getTransportVehicles()) {
                if (option.equals(vehicle.getType())) {
                    Log.i("AAAA", option + " ssss " + vehicle.getType());
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Integer clickCount = 0;
        clickCount++;

        if (clickCount != null) {
            Intent intent = new Intent(this, StationActivity.class);
            intent.putExtra("StationId", (String) marker.getTag());
            startActivity(intent);
            return true;
        }
        return false;
    }

    private void onMyMapReady() {
        mMap.setOnMapLoadedCallback(() -> {
            // Map loaded. Dismiss this dialog, removing it from the screen.
            myProgress.dismiss();
            askPermissionsAndShowMyLocation();

        });
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

    }


    private void askPermissionsAndShowMyLocation() {
        // With API> = 23, you have to ask the user for permission to view their location.
        if (Build.VERSION.SDK_INT >= 23) {
            int accessCoarsePermission
                    = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            int accessFinePermission
                    = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

            if (accessCoarsePermission != PackageManager.PERMISSION_GRANTED
                    || accessFinePermission != PackageManager.PERMISSION_GRANTED) {
                // The Permissions to ask user.
                String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION};
                // Show a dialog asking the user to allow the above permissions.
                ActivityCompat.requestPermissions(this, permissions,
                        REQUEST_ID_ACCESS_COURSE_FINE_LOCATION);
                return;
            }
        }
        // Show current location on Map.
        showMyLocation();
    }

    private void showMyLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationProvider = getEnabledLocationProvider();
        if (locationProvider == null) {
            return;
        }
        try {
            // This code need permissions (Asked above ***)
            locationManager.requestLocationUpdates(
                    locationProvider,
                    1000,
                    1, this);
            // Getting Location.
            myLocation = locationManager.getLastKnownLocation(locationProvider);
        }
        // With Android API >= 23, need to catch SecurityException.
        catch (SecurityException e) {
            Toast.makeText(this, "Show My Location Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e(MYTAG, "Show My Location Error:" + e.getMessage());
            e.printStackTrace();
            return;
        }
        showMyLocationOnMap();
    }

    private void showMyLocationOnMap() {
        if (myLocation != null) {
            LatLng latLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng)             // Sets the center of the map to location user
                    .zoom(15)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            // Add Marker to Map

            markerPosition.title(getResources().getString(R.string.mojaLokacija));
            markerPosition.position(latLng);
            Marker currentMarker = mMap.addMarker(markerPosition);
            currentMarker.showInfoWindow();
        } else {
            Toast.makeText(this, "Location not found!", Toast.LENGTH_SHORT).show();
            Log.i(MYTAG, "Location not found");
        }
    }

    private String getEnabledLocationProvider() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        boolean enabled = locationManager.isProviderEnabled(bestProvider);

        if (!enabled) {
            Toast.makeText(this, "No location provider enabled!", Toast.LENGTH_SHORT).show();
            Log.i(MYTAG, "No location provider enabled!");
            return null;
        }
        return bestProvider;
    }

    // When you have the request results.
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_ID_ACCESS_COURSE_FINE_LOCATION: {
                // Note: If request is cancelled, the result arrays are empty.
                // Permissions granted (read/write).
                if (grantResults.length > 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
                    // Show current location on Map.
                    showMyLocation();
                }
                // Cancelled or denied.
                else {
                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        locationManager.requestLocationUpdates(locationProvider, 1000, 10, this);
//        myLocation.setLatitude(location.getLatitude());
//        myLocation.setLongitude(location.getLongitude());
        showMyLocation();

//        try {
//            if (location != null) {
//                double lat = location.getLatitude();
//                double log = location.getLongitude();
//                if(lat !=0 && log!= 0){
//                    myLocation.setLongitude(location.getLongitude());
//                    myLocation.setLatitude(location.getLatitude());
//                    showMyLocation();
//                }
//            }
//        }
//        catch (Exception e){
////            finish();
////            startActivity(getIntent());
//        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public boolean onMyLocationButtonClick() {
        showMyLocation();
//        onMyMapReady();
        return true;
    }
}