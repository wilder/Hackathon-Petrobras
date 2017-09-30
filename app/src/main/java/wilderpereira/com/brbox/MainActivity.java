package wilderpereira.com.brbox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, NavigationView.OnNavigationItemSelectedListener {

    MapView mMapView;
    Context context;
    Toolbar toolbar;
    private GoogleMap googleMap;
    GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    LatLng mLocation;
    private GoogleMap mMap;
    private double longitude;
    private double latitude;
    LocationManager lm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mMapView = (MapView) findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);

                }

                if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 0);

                }

                if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);

                }

                googleMap.setMyLocationEnabled(true);

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        if(lm==null)
            lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {

            // For dropping a marker at a point on the Map
            mLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            googleMap.addMarker(new MarkerOptions()
                    .position(mLocation)
                    .title("RecyKing")
                    .snippet("Daniel Oliveira"))
                    .setIcon(BitmapDescriptorFactory
                            .fromResource(R.drawable.ic_audiotrack_dark));

            // For zooming automatically to the location of the marker
            CameraPosition cameraPosition = new CameraPosition.Builder().target(mLocation).zoom(12).build();
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(mLocation));
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            LatLng mLocation2 = new LatLng(-23.524423,-46.670640700000035);
            googleMap.addMarker(new MarkerOptions()
                    .position(mLocation2)
                    .title("RecyKing")
                    .snippet("Wilder Pereira"))
                    .setIcon(BitmapDescriptorFactory
                            .fromResource(R.drawable.ic_audiotrack_dark));

            mLocation2 = new LatLng(-23.5996933,-46.62317529999996);
            googleMap.addMarker(new MarkerOptions()
                    .position(mLocation2)
                    .title("RecyQueen")
                    .snippet("Kesia Ventura"))
                    .setIcon(BitmapDescriptorFactory
                            .fromResource(R.drawable.ic_audiotrack_dark));


            mLocation2 = new LatLng(-23.4722745,-46.669822599999975);
            googleMap.addMarker(new MarkerOptions()
                    .position(mLocation2)
                    .title("RecyKing")
                    .snippet("Wilder Pereira"))
                    .setIcon(BitmapDescriptorFactory
                            .fromResource(R.drawable.ic_audiotrack_dark));

            mLocation2 = new LatLng(-23.5584042,-46.68748900000003);
            googleMap.addMarker(new MarkerOptions()
                    .position(mLocation2)
                    .title("RecyQueen")
                    .snippet("Julio Cesar"))
                    .setIcon(BitmapDescriptorFactory
                            .fromResource(R.drawable.ic_audiotrack_dark));

            mLocation2 = new LatLng(-23.5931297,-46.63511790000001);
            googleMap.addMarker(new MarkerOptions()
                    .position(mLocation2)
                    .title("RecyKing")
                    .snippet("Wilder Pereira"))
                    .setIcon(BitmapDescriptorFactory
                            .fromResource(R.drawable.ic_audiotrack_dark));
            mLocation2 = new LatLng(-23.5299194,-46.58302359999999);
            googleMap.addMarker(new MarkerOptions()
                    .position(mLocation2)
                    .title("RecyQueen")
                    .snippet("Kesia Ventura"))
                    .setIcon(BitmapDescriptorFactory
                            .fromResource(R.drawable.ic_audiotrack_dark));
            mLocation2 = new LatLng(-23.6532661,-46.64945510000001);
            googleMap.addMarker(new MarkerOptions()
                    .position(mLocation2)
                    .title("RecyKing")
                    .snippet("Wilder Pereira"))
                    .setIcon(BitmapDescriptorFactory
                            .fromResource(R.drawable.ic_audiotrack_dark));
            mLocation2 = new LatLng(-23.6117038,-46.6446474);
            googleMap.addMarker(new MarkerOptions()
                    .position(mLocation2)
                    .title("RecyKing")
                    .snippet("Diego Moreno"))
                    .setIcon(BitmapDescriptorFactory
                            .fromResource(R.drawable.ic_audiotrack_dark));
            mLocation2 = new LatLng(-23.5506966,-46.52565129999999);
            googleMap.addMarker(new MarkerOptions()
                    .position(mLocation2)
                    .title("RecyKing")
                    .snippet("John Doe"))
                    .setIcon(BitmapDescriptorFactory
                            .fromResource(R.drawable.ic_audiotrack_dark));
            mLocation2 = new LatLng(-23.664129,-46.77536629999997);
            googleMap.addMarker(new MarkerOptions()
                    .position(mLocation2)
                    .title("RecyKing")
                    .snippet("Diego Moreno"))
                    .setIcon(BitmapDescriptorFactory
                            .fromResource(R.drawable.ic_audiotrack_dark));
            mLocation2 = new LatLng(-23.6729898,-46.7910445);
            googleMap.addMarker(new MarkerOptions()
                    .position(mLocation2)
                    .title("RecyKing")
                    .snippet("John Doe"))
                    .setIcon(BitmapDescriptorFactory
                            .fromResource(R.drawable.ic_audiotrack_dark));
            mLocation2 = new LatLng(-23.6384647,-46.75496780000003);
            googleMap.addMarker(new MarkerOptions()
                    .position(mLocation2)
                    .title("RecyKing")
                    .snippet("John Doe"))
                    .setIcon(BitmapDescriptorFactory
                            .fromResource(R.drawable.ic_audiotrack_dark));
            mLocation2 = new LatLng(-23.638313,-46.74193630000002);
            googleMap.addMarker(new MarkerOptions()
                    .position(mLocation2)
                    .title("Recyking")
                    .snippet("John Doe"))
                    .setIcon(BitmapDescriptorFactory
                            .fromResource(R.drawable.ic_audiotrack_dark));

            mLocation2 = new LatLng(-23.622432,-46.72546990000001);
            googleMap.addMarker(new MarkerOptions()
                    .position(mLocation2)
                    .title("Recyking")
                    .snippet("John Doe"))
                    .setIcon(BitmapDescriptorFactory
                            .fromResource(R.drawable.ic_audiotrack_dark));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);

    }

    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
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
    };

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                Log.i("Barcode Result", contents);
                // Handle successful scan
            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
                Log.i("Barcode Result","Result canceled");
            }
        }
    }

    public void readCode(View view) {
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        startActivityForResult(intent, 0);
    }

}
