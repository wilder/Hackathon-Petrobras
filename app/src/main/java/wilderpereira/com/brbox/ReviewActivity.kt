package wilderpereira.com.brbox

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.RatingBar
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_review.*

class ReviewActivity : AppCompatActivity() {

    lateinit var user: User
    lateinit var mDatabase: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        user = PreferencesManager(this@ReviewActivity).user

        mDatabase = FirebaseDatabase.getInstance().reference

        val simpleAlert = AlertDialog.Builder(this@ReviewActivity).create()
        simpleAlert.setTitle("Parabéns")
        simpleAlert.setMessage("Você ganhou 5 pontos")

        simpleAlert.setButton(AlertDialog.BUTTON_POSITIVE, "OK", {
            dialogInterface, i ->
            startActivity(Intent(this@ReviewActivity, MainActivity::class.java))
        })



        ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            val ratingView = ratingBar as RatingBar
            mDatabase.child("users")?.child("frentista"+user.userId)?.child("rating")?.setValue(user.score + ratingView.rating)
            credit(user.userId, 5)
            simpleAlert.show()
            true
        }

    }

    private fun credit(userid: String, value: Int) {
        user = PreferencesManager(this@ReviewActivity).user
        val mDatabase = FirebaseDatabase.getInstance().reference
        mDatabase.child("users")?.child(userid)?.child("score")?.setValue(user.score + value-3)
        user.setScore(user.score+value)
        PreferencesManager(this@ReviewActivity).user = user
    }

}
