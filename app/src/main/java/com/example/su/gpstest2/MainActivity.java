package com.example.su.gpstest2;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mainActivity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            System.out.print("sucess");
        } else {
            Toast.makeText(this, "error_permission_map", Toast.LENGTH_LONG).show();
        }

        lom = (LocationManager) this.getSystemService(LOCATION_SERVICE);
       /* if (lom.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            System.out.print("Enable");
        }else{
            System.out.print("Disable");
        }*/
        get_con();
        get_gps();

        timer = new Timer(true);
        timer.schedule(task, 0, 1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void get_gps() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        loc = lom.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (loc != null) {
            lat = loc.getLatitude();
            lng = loc.getLongitude();
            Double distanceToCompany = this.getDistances();
            txt_lat.setText("Latitude:" + String.valueOf(lat));
            txt_lng.setText("Longitude:" + String.valueOf(lng));
        }
        else{
            txt_lat.setText("Latitude: Unknowen" );
            txt_lng.setText("Longitude:Unknowen" );
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

    LocationListener los = new LocationListener(){
        public void onLocationChanged(Location location){
            if (location != null) {
                lat = location.getLatitude();
                lng = location.getLongitude();
                Double distanceToCompany = mainActivity.getDistances();
                txt_lat.setText("Latitude:" + String.valueOf(lat));
                txt_lng.setText("Longitude:" + String.valueOf(lng));
            }
            else{
                txt_lat.setText("Latitude: Unknowen" );
                txt_lng.setText("Longitude:Unknowen" );
            }
        };

        public void onProviderDisabled(String provider){

        };

        public void onProviderEnabled(String provider){ };

        public void onStatusChanged(String provider, int status,
                                    Bundle extras){ };
    };

    // 获取控件
    public void get_con(){
        txt_time = (TextView) findViewById(R.id.txt_time);
        txt_lat = (TextView) findViewById(R.id.txt_lat);
        txt_lng = (TextView) findViewById(R.id.txt_lng);
    }

    Handler handler = new Handler(){

        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    get_time();
                    break; }
        }
    };

    TimerTask task = new TimerTask(){
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };

    // 获取时间
    public void get_time(){
        now = new Date(System.currentTimeMillis());
        str_date = formatter.format(now);
        txt_time.setText("Timenow:" + str_date);
    }

    public double getDistances(Location from, Location to){
        String request = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + from.getLatitude() + "," + from.getLongitude() + "&destinations=" + to.getLatitude() + "," + to.getLongitude() + "&mode=walking&key=AIzaSyCyozbEtlhLu9M4f78T4XvOuZlLEybLz30";

        return 0.1;
    }
}