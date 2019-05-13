package masran.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import masran.myapplication.model.PlaceData;
import masran.myapplication.utils.JSONParserUtils;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener,
LocationListener, GoogleMap.InfoWindowAdapter {
    private static final String TAG = "MapsActivity";
    private static HashMap<String,PlaceData> places;
    private static HashMap<String,Bitmap> images;
    private static String currentPlaceType = "hospital";

    private boolean loadImageFlag = false;
    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastlocation;
    private Marker currentLocationmMarker;
    public static final int REQUEST_LOCATION_CODE = 99;
    int PROXIMITY_RADIUS = 10000;
    double latitude,longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        
        places = new HashMap<>();
        images = new HashMap<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkLocationPermission();

        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case REQUEST_LOCATION_CODE:
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) !=  PackageManager.PERMISSION_GRANTED)
                    {
                        if(client == null)
                        {
                            bulidGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else
                {
                    Toast.makeText(this,"Permission Denied" , Toast.LENGTH_LONG).show();
                }
        }
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

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            bulidGoogleApiClient();
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);
        }

        mMap.setInfoWindowAdapter(this);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                loadImageFlag = false;
                return false;
            }
        });
    }


    protected synchronized void bulidGoogleApiClient() {
        client = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        client.connect();

    }

    @Override
    public void onLocationChanged(Location location) {

        latitude = location.getLatitude();
        longitude = location.getLongitude();
        lastlocation = location;
        if(currentLocationmMarker != null)
        {
            currentLocationmMarker.remove();

        }
        Log.d("lat = ",""+latitude);
        LatLng latLng = new LatLng(location.getLatitude() , location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        currentLocationmMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));

        if(client != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(client,this);
        }
    }

    public void onClick(View v)
    {
        Object dataTransfer[] = new Object[3];
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();

       switch(v.getId())
        {

            case R.id.B_t:
                mMap.clear();
                currentPlaceType = "hospital";
                String url = getUrl(latitude, longitude, currentPlaceType);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;
                dataTransfer[2] = currentPlaceType;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(MapsActivity.this, "Showing Nearby Hospitals", Toast.LENGTH_SHORT).show();
                break;


            case R.id.B_p :
                mMap.clear();
                currentPlaceType = "pharmacy";
                url = getUrl(latitude, longitude, currentPlaceType);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;
                dataTransfer[2] = currentPlaceType;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(MapsActivity.this, "Showing Nearby Pharmacy", Toast.LENGTH_SHORT).show();
                break;

        }
    }


    private String getUrl(double latitude , double longitude , String nearbyPlace)
    {

        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+latitude+","+longitude);
        googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type="+nearbyPlace);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key="+"AIzaSyBLEPBRfw7sMb73Mr88L91Jqh3tuE4mKsE");

        Log.d("MapsActivity", "url = "+googlePlaceUrl.toString());

        return googlePlaceUrl.toString();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(100);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }
    }


    public boolean checkLocationPermission()
    {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)  != PackageManager.PERMISSION_GRANTED )
        {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            }
            return false;

        }
        else
            return true;
    }


    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(final Marker marker) {
        PlaceData place = places.get(marker.getTitle());

        View view = getLayoutInflater().inflate(R.layout.layout_infowindow,null);

        ImageView imageView = view.findViewById(R.id.info_image);
        TextView textName = view.findViewById(R.id.info_name);
        TextView textVincinity = view.findViewById(R.id.info_vincinity);
        TextView textOpenNow = view.findViewById(R.id.info_open_now);

        textName.setText(place.getName());
        textVincinity.setText(place.getVincinity());

        String openStr = place.isOpenNow() ? "open" : "close";
        textOpenNow.setText("Status : "+openStr);
        Picasso.get().load(place.getImageUrl()).into(imageView);

        return view;
    }

    private static class GetNearbyPlacesData extends AsyncTask<Object, String, HashMap<String,PlaceData>> {

        private String googlePlacesData;
        private GoogleMap mMap;
        String url;
        String place;

        @Override
        protected HashMap<String,PlaceData> doInBackground(Object... objects){
            mMap = (GoogleMap)objects[0];
            url = (String)objects[1];
            place = (String)objects[2];

            HashMap<String,PlaceData> placeList = null;

            DownloadURL downloadURL = new DownloadURL();
            try {
                googlePlacesData = downloadURL.readUrl(url);
                placeList = JSONParserUtils.parserGooglePlace(googlePlacesData);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return placeList;
        }

        @Override
        protected void onPostExecute(HashMap<String,PlaceData> placeList){
//            List<HashMap<String, String>> nearbyPlaceList;
//            DataParser parser = new DataParser();
//            nearbyPlaceList = parser.parse(s);

//            showNearbyPlaces(nearbyPlaceList);
            places = placeList;
            loadImages(placeList);
            showNearbyPlaces(placeList);
        }

        private void loadImages(HashMap<String,PlaceData> placeList) {
            for(Map.Entry<String, PlaceData> place : placeList.entrySet()) {
                final PlaceData placeData = place.getValue();

                Target target = new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Log.d(TAG, "onBitmapLoaded: " + bitmap.getByteCount());
                        images.put(placeData.getName(),bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        Log.e(TAG, "onBitmapFailed: ",e );
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                };

                Picasso.get().load(placeData.getImageUrl()).into(target);
            }
        }

        private void showNearbyPlaces(HashMap<String, PlaceData> nearbyPlaceList)
        {
            for(Map.Entry<String, PlaceData> place : nearbyPlaceList.entrySet()) {
                PlaceData placeData = place.getValue();
                MarkerOptions markerOptions = new MarkerOptions();

                markerOptions.position(placeData.getLatLng());
                markerOptions.title(placeData.getName());
                if (currentPlaceType.contentEquals("hospital")){
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_hs));
                }
                else
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_ph));

                mMap.addMarker(markerOptions);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(placeData.getLatLng()));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
            }
//            for(int i = 0; i < nearbyPlaceList.size(); i++)
//            {
//                MarkerOptions markerOptions = new MarkerOptions();
//                HashMap<String, String> googlePlace = nearbyPlaceList.get(i);
//
//                String placeName = googlePlace.get("place_name");
//                String vicinity = googlePlace.get("vicinity");
//
//                double lat = Double.parseDouble( googlePlace.get("lat"));
//                double lng = Double.parseDouble( googlePlace.get("lng"));
//
//                LatLng latLng = new LatLng( lat, lng);
//                markerOptions.position(latLng);
//                markerOptions.title(placeName+ " : "+ vicinity );
//                if (place == "hospital"){
//                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_hs));
//                }
//                else
//                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_ph));
//
//                mMap.addMarker(markerOptions);
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//                mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
//            }
        }
    }
}
