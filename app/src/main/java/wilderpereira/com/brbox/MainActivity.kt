package wilderpereira.com.brbox

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView

import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, NavigationView.OnNavigationItemSelectedListener {

    lateinit var mDatabase: DatabaseReference
    lateinit var user: User
    lateinit var mMapView: MapView
    lateinit var context: Context
    lateinit var toolbar: Toolbar
    var googleMap: GoogleMap? = null
    lateinit var mGoogleApiClient: GoogleApiClient
    private var mLastLocation: Location? = null
    lateinit var mLocation: LatLng
    private val mMap: GoogleMap? = null
    private var longitude: Double = 0.toDouble()
    private var latitude: Double = 0.toDouble()
    private var lm: LocationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this

        user = PreferencesManager(this@MainActivity).user!!

        mDatabase = FirebaseDatabase.getInstance().reference.child("users")?.child(user.userId)!!
        mDatabase?.addValueEventListener(UserListener(this@MainActivity))

        toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        mGoogleApiClient = GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()

        mMapView = findViewById(R.id.mapView) as MapView
        mMapView.onCreate(savedInstanceState)
        mMapView.onResume()// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        mMapView.getMapAsync { mMap ->
            googleMap = mMap

            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(context as Activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 0)

            }

            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(context as Activity, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION), 0)

            }

            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(context as Activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 0)

            }

            googleMap!!.isMyLocationEnabled = true
        }

    }

    public override fun onResume() {
        super.onResume()
        mMapView.onResume()
    }

    public override fun onPause() {
        super.onPause()
        mMapView.onPause()
    }

    public override fun onDestroy() {
        super.onDestroy()
        mMapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView.onLowMemory()
    }

    public override fun onStart() {
        mGoogleApiClient.connect()
        super.onStart()
    }

    public override fun onStop() {
        mGoogleApiClient.disconnect()
        super.onStop()
    }

    override fun onConnected(bundle: Bundle?) {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        if (lm == null)
            lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient)
        if (mLastLocation != null) {

            // For dropping a marker at a point on the Map
            mLocation = LatLng(mLastLocation!!.latitude, mLastLocation!!.longitude)
            googleMap!!.addMarker(MarkerOptions()
                    .position(mLocation)
                    .title("RecyKing")
                    .snippet("Daniel Oliveira"))
                    .setIcon(BitmapDescriptorFactory
                            .fromResource(R.drawable.ic_audiotrack_dark))

            // For zooming automatically to the location of the marker
            val cameraPosition = CameraPosition.Builder().target(mLocation).zoom(12f).build()
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(mLocation));
            googleMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        val header = navigationView.findViewById(R.id.nav_view)

        val username = header.findViewById(R.id.usernamee) as TextView
        val score = header.findViewById(R.id.scoree) as TextView
        val credits = header.findViewById(R.id.credit) as TextView

        mDatabase?.addValueEventListener(UserDrawerListener(this@MainActivity, username, score, credits))


        lm!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10f, locationListener)

    }


    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            longitude = location.longitude
            latitude = location.latitude
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {

        }

        override fun onProviderEnabled(provider: String) {

        }

        override fun onProviderDisabled(provider: String) {

        }
    }

    override fun onConnectionSuspended(i: Int) {

    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.nav_transp -> {
                val gmmIntentUri = Uri.parse("geo:0,0?q=Cristo+Redentor,Rio+de+Janeiro&mode=d")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.`package` = "com.google.android.apps.maps"
                startActivity(mapIntent)
            }
        }
        return true
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.action_share) {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
            sendIntent.type = "text/plain"
            startActivity(Intent.createChooser(sendIntent, "venha conhecer o neobr!"))

        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent) {
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                val contents = intent.getStringExtra("SCAN_RESULT")
                val format = intent.getStringExtra("SCAN_RESULT_FORMAT")
                Log.i("Barcode Result", contents)
                // Handle successful scan
                credit(10)
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // Handle cancel
                Log.i("Barcode Result", "Result canceled")
            }
        }
    }

    fun readCode(view: View) {
        val intent = Intent("com.google.zxing.client.android.SCAN")
        intent.putExtra("SCAN_MODE", "QR_CODE_MODE")
        startActivityForResult(intent, 0)
    }

    private fun credit(value: Int) {
        user = PreferencesManager(this@MainActivity).user!!
        val mDatabase = FirebaseDatabase.getInstance().reference
        mDatabase.child("users")?.child(user.userId)?.child("score")?.setValue(user.score - value)
    }

    fun checkin(view: View) {
        startActivity(Intent(this@MainActivity, MyCodeActivity::class.java))
    }

}
