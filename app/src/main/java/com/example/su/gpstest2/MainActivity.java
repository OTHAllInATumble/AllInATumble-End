package com.example.su.gpstest2;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    TextView txt_time;
    TextView txt_lat;
    TextView txt_lng;
    LocationManager lom;
    Location loc;
    Double lat;
    Double lng;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date now;
    String str_date;
    Timer timer;
    MainActivity mainActivity;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mainActivity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            System.out.print("sucess");
        } else {
            Toast.makeText(this, "Error permission map", Toast.LENGTH_LONG).show();
        }

        lom = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        get_con();
        get_gps();

        timer = new Timer(true);
        timer.schedule(task, 0, 1000);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public void get_gps() {
        //return the location of user.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        loc = lom.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (loc != null) {
            lat = loc.getLatitude();
            lng = loc.getLongitude();
            Double distanceToCompany = this.getDistances();
            txt_lat.setText("Latitude:" + String.valueOf(lat));
            txt_lng.setText("Longitude:" + String.valueOf(lng));
        } else {
            txt_lat.setText("Latitude: Unknowen");
            txt_lng.setText("Longitude:Unknowen");
        }
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String provider = lom.getBestProvider(criteria, true);

        lom.requestLocationUpdates(provider, 1000, 10, los);
    }

    LocationListener los = new LocationListener() {
        public void onLocationChanged(Location location) {
            if (location != null) {
                lat = location.getLatitude();
                lng = location.getLongitude();
                Double distanceToCompany = mainActivity.getDistances();
                txt_lat.setText("Latitude:" + String.valueOf(lat));
                txt_lng.setText("Longitude:" + String.valueOf(lng));
            } else {
                txt_lat.setText("Latitude: Unknowen");
                txt_lng.setText("Longitude:Unknowen");
            }
        }

        ;

        public void onProviderDisabled(String provider) {

        }

        ;

        public void onProviderEnabled(String provider) {
        }

        ;

        public void onStatusChanged(String provider, int status,
                                    Bundle extras) {
        }

        ;
    };

    // get widget
    public void get_con() {
        txt_time = (TextView) findViewById(R.id.txt_time);
        txt_lat = (TextView) findViewById(R.id.txt_lat);
        txt_lng = (TextView) findViewById(R.id.txt_lng);
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    get_time();
                    break;
            }
        }
    };

    TimerTask task = new TimerTask() {
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };

    //get time
    public void get_time() {
        now = new Date(System.currentTimeMillis());
        str_date = formatter.format(now);
        txt_time.setText("Timenow:" + str_date);
    }

    public double getDistances(Location origins, Location destinations) {
        // For Funschelist function.
        // Use google map api get the json object, then process element "rows > elements > distance" to get the result and return the minimal distance.
        String request = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + origins.getLatitude() + "," + origins.getLongitude() + "&destinations=" + destinations.getLatitude() + "," + destinations.getLongitude() + "&mode=walking&key=AIzaSyCyozbEtlhLu9M4f78T4XvOuZlLEybLz30";
        //TODO : use json to get the request result from element "rows > elements > distance" and return the minimal distance.
        JsonParser parser = new JsonParser();
        JsonObject object = (JsonObject) parser.parse(new FileReader("test.json"));
        JsonArray rows = object.getAsJsonArray("rows").get("elements").get("distance");
        for (JsonElement jsonElement : rows) {
            //JsonObject language = jsonElement.getAsJsonObject();
            //System.out.println("id = " + language.get("id").getAsInt() + ",ide = " + language.get("ide").getAsString() + ",name = " + language.get("name").getAsString());
        }

        return 0.1;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.su.gpstest2/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.su.gpstest2/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}