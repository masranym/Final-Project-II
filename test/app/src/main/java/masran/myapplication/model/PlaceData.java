package masran.myapplication.model;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class PlaceData {
    private static final String TAG = "PlaceData";
    
    private String name;
    private String vincinity;
    private boolean openNow;
    private LatLng latLng;
    private String imageUrl;

    public PlaceData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVincinity() {
        return vincinity;
    }

    public void setVincinity(String vincinity) {
        this.vincinity = vincinity;
    }

    public boolean isOpenNow() {
        return openNow;
    }

    public void setOpenNow(boolean openNow) {
        this.openNow = openNow;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String photoReference) {
        String key = "AIzaSyBLEPBRfw7sMb73Mr88L91Jqh3tuE4mKsE";
        String apiUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+photoReference+"&key=" + key;
        this.imageUrl = apiUrl;

        Log.d(TAG, "setImageUrl: " + apiUrl);
    }
}
