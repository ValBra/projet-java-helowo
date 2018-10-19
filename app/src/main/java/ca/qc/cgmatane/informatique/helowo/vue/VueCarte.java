package ca.qc.cgmatane.informatique.helowo.vue;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ca.qc.cgmatane.informatique.helowo.R;

public class VueCarte extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_carte);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("Helowo", "onMapReady");
        mMap = googleMap;

        //AF:19:C1:9E:59:02:AE:16:D3:4B:52:B5:C9:17:AD:CE:52:F8:B7:2D

        LatLng matane = new LatLng(48.840, -67.497);
        mMap.addMarker(new MarkerOptions().position(matane).title("Marker in Matane"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(matane));
        /*if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1000);
        }else {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            try{
                LatLng geoloc = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(geoloc).title("Lieu de la publication"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(geoloc));
            }catch (Exception e){
                e.printStackTrace();
                Log.d("Helowo", "dans le catch");
                LatLng matane = new LatLng(48.840, -67.497);
                mMap.addMarker(new MarkerOptions().position(matane).title("Marker in Matane"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(matane));
            }
        }*/
    }
}
