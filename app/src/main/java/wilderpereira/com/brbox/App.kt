package wilderpereira.com.brbox

import android.app.Application

import com.google.firebase.database.FirebaseDatabase

/**
 * Created by Wilder on 30/09/17.
 */

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}
