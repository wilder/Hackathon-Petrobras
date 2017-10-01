package wilderpereira.com.brbox;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Wilder on 30/09/17.
 */

public class UserListener implements ValueEventListener{

    Context context;
    PreferencesManager preferencesManager;

    public UserListener(Context context) {
        this.context = context;
        preferencesManager = new PreferencesManager(context);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        User user = dataSnapshot.getValue(User.class);

        User oldUser = preferencesManager.getUser();
        oldUser.setScore(user.getScore());

        preferencesManager.setUser(oldUser);

        Log.i("wilder changed", oldUser.toString());
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        // Getting Post failed, log a message
        Log.i("wilder changed", databaseError.toException().getMessage());
        // ...
    }
}
