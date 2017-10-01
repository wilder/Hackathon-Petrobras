package wilderpereira.com.brbox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Wilder on 01/10/17.
 */

public class MyCodeActivity extends AppCompatActivity {

    DatabaseReference mDatabase;
    User user;
    Context context;
    TextView scoreAmount;
    TextView credits;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_code);

        context = this;
        user = new PreferencesManager(context).getUser();

        scoreAmount = (TextView) findViewById(R.id.scorePay);
        credits = (TextView) findViewById(R.id.credit);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(user.userId);

        scoreAmount.setText(user.getScore().toString());
        credits.setText(user.getCredits().toString());

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User retrievedUser = (User) dataSnapshot.getValue(User.class);
                if (!user.getMessage().equals(retrievedUser.getMessage())) {
                    user.setMessage(retrievedUser.getMessage());
                    mDatabase.child("credits").setValue(retrievedUser.getCredits()-10d);
                    mDatabase.child("score").setValue(retrievedUser.getScore()+10);
                    user.setCredits(retrievedUser.getCredits()-10d);
                    user.setScore(retrievedUser.getScore()+10);
                    new PreferencesManager(context).setUser(user);
                    startActivity(new Intent(MyCodeActivity.this, ReviewActivity.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
