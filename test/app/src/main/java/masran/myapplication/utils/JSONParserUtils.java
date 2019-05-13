package masran.myapplication.utils;

import android.util.Log;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import masran.myapplication.model.PlaceData;

public class JSONParserUtils {

    private static final String TAG = "JSONParserUtils";

    public static HashMap<String,PlaceData> parserGooglePlace(String s) {
        HashMap<String, PlaceData> placeList = new HashMap<>();
        JSONArray results;

        try {
            JSONObject jsonObject = new JSONObject(s);
            results = jsonObject.getJSONArray("results");

            for(int i = 0; i < results.length() ; i++) {
                JSONObject result = (JSONObject) results.get(i);

                JSONObject location = result.getJSONObject("geometry").getJSONObject("location");
                double lat = location.getDouble("lat");
                double lng = location.getDouble("lng");

                LatLng latLng = new LatLng(lat,lng);
                String name = result.getString("name");
                String vincinity = result.getString("vicinity");
                boolean openNow = result.getJSONObject("opening_hours").getBoolean("open_now");
                String photoReference = ((JSONObject)result.getJSONArray("photos").get(0)).getString("photo_reference");

                PlaceData placeData = new PlaceData();
                placeData.setName(name);
                placeData.setVincinity(vincinity);
                placeData.setOpenNow(openNow);
                placeData.setLatLng(latLng);
                placeData.setImageUrl(photoReference);

                placeList.put(placeData.getName(),placeData);
            }
        } catch (JSONException e) {
            Log.e(TAG, "parserGooglePlace: ",e );
        }
        return placeList;
    }
}
