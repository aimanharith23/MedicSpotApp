package net.airith.medicspot3;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.airith.medicspot3.databinding.ActivityMapsBinding;


import java.util.Vector;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    MarkerOptions marker;
    LatLng centerlocation;
    Vector<MarkerOptions> markerOptions;

    private String URL ="http://192.168.0.106/medicspot/all.php";
    RequestQueue requestQueue;
    Gson gson;
    Hospital[] hospitals;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        gson = new GsonBuilder().create();


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        centerlocation = new LatLng(3.0,101);

        markerOptions = new Vector<>();

        markerOptions.add(new MarkerOptions().title("Alor Setar")
                .position(new LatLng(6.12,100.37))
                .snippet("Ibu negeri Kedah"));

        markerOptions.add(new MarkerOptions().title("George Town")
                .position(new LatLng(5.42,100.33))
                .snippet("Ibu negeri Pulau Pinang"));

        markerOptions.add(new MarkerOptions().title("Ipoh")
                .position(new LatLng(4.5975,101.0901))
                .snippet("Ibu negeri Perak"));

        markerOptions.add(new MarkerOptions().title("Kota Bharu")
                .position(new LatLng(6.14,102.24))
                .snippet("Ibu negeri Kelantan"));

        markerOptions.add(new MarkerOptions().title("Kangar")
                .position(new LatLng(6.44,100.22))
                .snippet("Ibu negeri Perlis"));

        markerOptions.add(new MarkerOptions().title("Johor Bahru")
                .position(new LatLng(1.53,103.75))
                .snippet("Ibu negeri Johor"));

        markerOptions.add(new MarkerOptions().title("Bandaraya Melaka")
                .position(new LatLng(2.20,102.25))
                .snippet("Ibu negeri Melaka"));

        markerOptions.add(new MarkerOptions().title("Seremban")
                .position(new LatLng(2.73,101.94))
                .snippet("Ibu negeri Negeri Sembilan"));

        markerOptions.add(new MarkerOptions().title("Kuantan")
                .position(new LatLng(3.76,103.22))
                .snippet("Ibu negeri Pahang"));

        markerOptions.add(new MarkerOptions().title("Kota Kinabalu")
                .position(new LatLng(5.98,116.07))
                .snippet("Ibu negeri Sabah"));

        markerOptions.add(new MarkerOptions().title("Kuching")
                .position(new LatLng(1.55,110.36))
                .snippet("Ibu negeri Sarawak"));

        markerOptions.add(new MarkerOptions().title("Shah Alam")
                .position(new LatLng(3.07,101.52))
                .snippet("Ibu negeri Selangor"));

        markerOptions.add(new MarkerOptions().title("Kuala Terengganu")
                .position(new LatLng(5.33,103.14))
                .snippet("Ibu negeri Terengganu"));

        markerOptions.add(new MarkerOptions().title("Kuala Lumpur")
                .position(new LatLng(3.14,101.69))
                .snippet("Ibu negara Malaysia"));




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
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

        for(MarkerOptions mark : markerOptions){
            mMap.addMarker(mark);
        }

        enableMyLocation();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerlocation,8));
        sendRequest();

    }
    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            String perms[] = {"android.permission.ACCESS_FINE_LOCATION"};
            // Permission to access the location is missing. Show rationale and request permission
            ActivityCompat.requestPermissions(this,perms,200);
        }
    }

    public void sendRequest(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,URL,onSuccess,onError);
        requestQueue.add(stringRequest);


    }

    public Response.Listener<String> onSuccess = new Response.Listener<String>(){

        @Override
        public void onResponse(String response) {

            hospitals = gson.fromJson(response, Hospital[].class);

            //this will be displayed on logcat as debug
            Log.d("Hospital","Number of Hospital Data Point:" + hospitals.length);

            if (hospitals.length < 1) {
                Toast.makeText(getApplicationContext(),"Problem retrieving JSON data",Toast.LENGTH_LONG).show();
                return;

            }

            for(Hospital info: hospitals){
                Double lat = Double.parseDouble(info.lat);
                Double log = Double.parseDouble(info.log);
                String title = info.hospName;
                String snippet = info.states;

                MarkerOptions marker = new MarkerOptions().position(new LatLng(lat,log))
                        .title(title)
                        .snippet(snippet)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));

                mMap.addMarker(marker);

            }
        }
    };

    public Response.ErrorListener onError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();

        }
    };

}