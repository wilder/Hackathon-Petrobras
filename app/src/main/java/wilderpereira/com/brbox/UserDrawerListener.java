package wilderpereira.com.brbox;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Wilder on 30/09/17.
 */

public class UserDrawerListener implements ValueEventListener{

    Context context;
    PreferencesManager preferencesManager;
    TextView username;
    TextView score;
    TextView credits;

    public UserDrawerListener(Context context, TextView username, TextView score, TextView credits) {
        this.context = context;
        preferencesManager = new PreferencesManager(context);
        this.username = username;
        this.score = score;
        this.credits = credits;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        User user = dataSnapshot.getValue(User.class);
        username.setText(user.getUsername());
        score.setText(user.getScore().toString());
        credits.setText(user.getCredits().toString());
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        // Getting Post failed, log a message
        Log.i("wilder changed", databaseError.toException().getMessage());
        // ...
    }
}
