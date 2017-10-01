package wilderpereira.com.brbox

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    var mDatabase :DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = PreferencesManager(this).user

        if (user != null || !user.username.isNullOrBlank()) {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }

        setContentView(R.layout.activity_login)
    }

    fun goToMain(view: View) {
        mDatabase = FirebaseDatabase.getInstance().reference
        writeNewUser(et_email.text.toString(), et_email.text.toString(), et_pass.text.toString())
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
    }

    private fun writeNewUser(name: String, email: String, password: String) {
        val user = User(name.substring(0, name.indexOf("@")), email, password, 0, 1, "")
        mDatabase?.child("users")?.child(password)?.setValue(user)
        PreferencesManager(this@LoginActivity).user = user
    }
}
