package wilderpereira.com.brbox

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RatingBar
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
        ratingBar.setOnTouchListener { v, event ->
            val ratingView = ratingBar as RatingBar
            mDatabase.child("users")?.child("frentista123")?.child("rating")?.setValue(user.score + ratingView.rating)
            //parabens vc canhou pontos
            //dar pontos
            true
        }

    }

    private fun credit(value: Int) {
        user = PreferencesManager(this@ReviewActivity).user
        val mDatabase = FirebaseDatabase.getInstance().reference
        mDatabase.child("users")?.child("frentista123")?.child("score")?.setValue(user.score + value)
    }

}
