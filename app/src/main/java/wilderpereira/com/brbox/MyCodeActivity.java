package wilderpereira.com.brbox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Wilder on 01/10/17.
 */

public class MyCodeActivity extends Activity {

    DatabaseReference mDatabase;
    User user;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_code);

        context = this;
        user = new PreferencesManager(context).getUser();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(user.userId).child("message");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = (String) dataSnapshot.getValue();
                if (!user.getMessage().equals(s)) {
                    user.setMessage(s);
                    new PreferencesManager(context).setUser(user);
                    startActivity(new Intent(MyCodeActivity.this, ReviewActivity.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
