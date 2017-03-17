package co.edu.udea.compumovil.gr01_20171.maps;

import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private TextView longitud;
    private TextView latitud;
    private ToggleButton toggleButton;
    private GoogleApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        longitud = (TextView) findViewById(R.id.longitud);
        latitud = (TextView) findViewById(R.id.latitud);
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);

        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("location", "error al conectar");

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        } else {
            Location location = LocationServices.FusedLocationApi.getLastLocation(apiClient);
            updateUI(location);
        }
    }

    public void updateUI(Location location){
        if(location!=null){
            latitud.setText("Latitud: " + String.valueOf(location.getLatitude()));
            longitud.setText("Longitud: " + String.valueOf(location.getLongitude()));
        }else{
            latitud.setText("Latitud: Desconocido");
            longitud.setText("Longitud: Desconocido");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

        Log.d("location", "servicio suspendido");

    }
}
