package mobileapps.kusiak.hearthisapplication.UserProfile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import mobileapps.kusiak.hearthisapplication.Login.StartupLoginPage;
import mobileapps.kusiak.hearthisapplication.R;

public class Settings extends AppCompatActivity {

    private int passedValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            passedValue = extras.getInt("key");
        }

        Button goBackButton = (Button) findViewById(R.id.goBackButton);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (passedValue == 1){
                    startActivity(new Intent(Settings.this, MainUserProfilePage.class));
                }
                else{
                    startActivity(new Intent(Settings.this, UserQuestionsAskedPage.class));
                }

            }
        });

        Button signOutButton = (Button) findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                signOut();

            }
        });

        Button saveButton = (Button) findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                saveAndExit();

            }
        });


        final TextView labelUserName = (TextView) findViewById(R.id.usernameViewSettings);
        final TextView labelBio = (TextView) findViewById(R.id.bioViewSettings);
        String[] arraySpinner = new String[] {
                "Piano", "Alto Saxophone",  "Tenor Saxophone",  "Bari Saxophone", "Trombone", "Bass Trombone", "Trumpet", "Bass", "Guitar","Drums","Percussion"
        };
        final Spinner s = (Spinner) findViewById(R.id.instrumentViewSettings);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DatabaseReference ref = myRef.child("users").child(currentFirebaseUser.getUid());


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                labelUserName.setText(dataSnapshot.child("Username").getValue().toString());
                labelBio.setText(dataSnapshot.child("Bio").getValue().toString());
                int spinnerPosition = adapter.getPosition(dataSnapshot.child("Instrument").getValue().toString());
                s.setSelection(spinnerPosition);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    private void saveAndExit(){
        TextView labelUserName = (TextView) findViewById(R.id.usernameViewSettings);
        TextView labelBio = (TextView) findViewById(R.id.bioViewSettings);
        Spinner s = (Spinner) findViewById(R.id.instrumentViewSettings);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DatabaseReference ref = myRef.child("users").child(currentFirebaseUser.getUid());

        ref.child("Username").setValue(labelUserName.getText().toString());
        ref.child("Bio").setValue(labelBio.getText().toString());
        ref.child("Instrument").setValue(s.getSelectedItem().toString());

        if (passedValue == 1){
            startActivity(new Intent(Settings.this, MainUserProfilePage.class));
        }
        else{
            startActivity(new Intent(Settings.this, UserQuestionsAskedPage.class));
        }
    }

    private void signOut(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        startActivity(new Intent(Settings.this, StartupLoginPage.class));

    }
}
