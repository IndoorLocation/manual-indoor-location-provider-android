package io.indoorlocation.manual.demoapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Locale;

import io.indoorlocation.core.IndoorLocation;
import io.indoorlocation.manual.ManualIndoorLocationProvider;
import io.mapwize.mapwizesdk.api.LatLngFloor;
import io.mapwize.mapwizesdk.api.MapwizeObject;
import io.mapwize.mapwizesdk.map.MapOptions;
import io.mapwize.mapwizesdk.map.MapwizeMap;
import io.mapwize.mapwizeui.MapwizeFragment;

public class MapActivity extends AppCompatActivity implements MapwizeFragment.OnFragmentInteractionListener {

    private MapwizeFragment mapwizeFragment;
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 0;

    MapwizeMap mapwizeMap;
    private ManualIndoorLocationProvider manualIndoorLocationProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        FrameLayout container = findViewById(R.id.container);

        MapOptions opts = new MapOptions.Builder()
                .language(Locale.getDefault().getLanguage()).build();
        mapwizeFragment = MapwizeFragment.newInstance(opts);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(container.getId(), mapwizeFragment);
        ft.commit();

    }

    @Override
    public void onStart() {
        super.onStart();
        mapwizeFragment.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapwizeFragment.onResume();
    }

    @Override
    public void onPause() {
        mapwizeFragment.onPause();
        super.onPause();
    }

    @Override
    public void onStop() {
        mapwizeFragment.onStop();
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(@androidx.annotation.NonNull Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        mapwizeFragment.onSaveInstanceState(saveInstanceState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapwizeFragment.onLowMemory();
    }

    @Override
    public void onDestroy() {
        mapwizeFragment.onDestroy();
        super.onDestroy();
    }

    private void startLocationService() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_ACCESS_FINE_LOCATION);
        }
        else {
            setupLocationProvider();
        }
    }

    private void setupLocationProvider() {
        manualIndoorLocationProvider = new ManualIndoorLocationProvider();
        manualIndoorLocationProvider.start();
        mapwizeMap.setIndoorLocationProvider(manualIndoorLocationProvider);
        mapwizeMap.addOnClickListener(clickEvent -> {
            LatLngFloor llf = clickEvent.getLatLngFloor();
            IndoorLocation il = new IndoorLocation("manual", llf.getLatitude(), llf.getLongitude(), llf.getFloor(), System.currentTimeMillis());
            manualIndoorLocationProvider.setIndoorLocation(il);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @androidx.annotation.NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSION_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupLocationProvider();
            }
        }
    }

    @Override
    public void onMenuButtonClick() {
        Log.i("MapActivity", "onMenuButtonClick");
    }

    @Override
    public void onInformationButtonClick(MapwizeObject mapwizeObject) {
        Log.i("MapActivity", "onInformationButtonClick");
    }

    @Override
    public void onFragmentReady(MapwizeMap mapwizeMap) {
        this.mapwizeMap = mapwizeMap;
        startLocationService();
    }

    @Override
    public void onFollowUserButtonClickWithoutLocation() {
        Log.i("MapActivity", "onFollowUserButtonClickWithoutLocation");
    }

}

/*public class MapActivity extends AppCompatActivity {

    private MapView mapView;
    private MapwizePlugin mapwizePlugin;
    private ManualIndoorLocationProvider manualIndoorLocationProvider;
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.mapwize");
        setContentView(R.layout.activity_map);

        mapView = findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        mapView.setStyleUrl("http://outdoor.mapwize.io/styles/mapwize/style.json?key=" + AccountManager.getInstance().getApiKey());

        MapOptions opts = new MapOptions.Builder().build();
        mapwizePlugin = new MapwizePlugin(mapView, opts);
        mapwizePlugin.setOnDidLoadListener(new MapwizePlugin.OnDidLoadListener() {
            @Override
            public void didLoad(MapwizePlugin plugin) {

                setupLocationProvider();

                mapwizePlugin.setOnMapClickListener(new MapwizePlugin.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLngFloor latLngFloor) {
                        Location location = new Location(manualIndoorLocationProvider.getName());
                        location.setLatitude(latLngFloor.getLatitude());
                        location.setLongitude(latLngFloor.getLongitude());
                        IndoorLocation indoorLocation = new IndoorLocation(manualIndoorLocationProvider.getName(), latLngFloor.getLatitude(), latLngFloor.getLongitude(), latLngFloor.getFloor(), System.currentTimeMillis());
                        manualIndoorLocationProvider.setIndoorLocation(indoorLocation);
                    }
                });
            }
        });
    }

    private void setupLocationProvider() {
        manualIndoorLocationProvider = new ManualIndoorLocationProvider();
        mapwizePlugin.setLocationProvider(manualIndoorLocationProvider);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    setupLocationProvider();

                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
*/