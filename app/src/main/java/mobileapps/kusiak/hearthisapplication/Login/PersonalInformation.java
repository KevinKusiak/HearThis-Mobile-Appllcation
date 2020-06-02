package mobileapps.kusiak.hearthisapplication.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import mobileapps.kusiak.hearthisapplication.Feed.MainFeedPage;
import mobileapps.kusiak.hearthisapplication.R;

public class PersonalInformation extends AppCompatActivity {

    private EditText userName, instrument, bio;
    private Button finalizeButton;

    private TextView a;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalinformation_activity);

        String[] arraySpinner = new String[] {
                "Piano", "Alto Saxophone",  "Tenor Saxophone",  "Bari Saxophone", "Trombone", "Bass Trombone", "Trumpet", "Bass", "Guitar","Drums","Percussion"
        };
        Spinner s = (Spinner) findViewById(R.id.instrumentView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();


        userName = findViewById(R.id.usernameView);
        bio = findViewById(R.id.bioView);

        finalizeButton = (Button) findViewById(R.id.finishButton);

        a = (TextView)findViewById(R.id.personalInfoText);



        finalizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishUserAccount();
            }
        });

    }

    private void finishUserAccount(){
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        final String un, i , b;
        un = userName.getText().toString();
        b = bio.getText().toString();

        Spinner s = (Spinner) findViewById(R.id.instrumentView);
        i = s.getSelectedItem().toString();


        myRef.child("users").child(currentFirebaseUser.getUid()).child("Username").setValue(un);
        myRef.child("users").child(currentFirebaseUser.getUid()).child("Instrument").setValue(i);
        myRef.child("users").child(currentFirebaseUser.getUid()).child("Bio").setValue(b);

        firstMan(un);

        startActivity(new Intent(PersonalInformation.this, MainFeedPage.class));


    }

    private void firstMan(final String un){
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DatabaseReference ref = myRef.child("users");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int sizeee = (int) dataSnapshot.getChildrenCount();
                man(sizeee, un);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void man(int sizeee, String un){
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("users").child(String.valueOf((sizeee / 2) + 1)).child("user").setValue(un);
        myRef.child("users").child(String.valueOf((sizeee / 2) + 1)).child(un).setValue(currentFirebaseUser.getUid());

    }
}
