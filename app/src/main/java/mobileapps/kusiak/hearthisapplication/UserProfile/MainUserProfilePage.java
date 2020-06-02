package mobileapps.kusiak.hearthisapplication.UserProfile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import mobileapps.kusiak.hearthisapplication.LiveAnalysis.MainLiveAnalysisPage;
import mobileapps.kusiak.hearthisapplication.R;
import mobileapps.kusiak.hearthisapplication.Trending.MainTrendingPage;
import mobileapps.kusiak.hearthisapplication.Videos.MainVideosPage;

public class MainUserProfilePage extends AppCompatActivity {

    private String JazzCollege, Saxophones, Trumpets, Trombones, RhythmSection, ClassicJazz, JazzFunk, JazzMusicians, HowToJazz;

    private boolean updateJazzCollege;
    private boolean updateSaxophones;
    private boolean updateTrumpets;
    private boolean updateTrombones;
    private boolean updateRhythmSection;
    private boolean updateClassicJazz;
    private boolean updateJazzFunk;
    private boolean updateJazzMusicians;
    private boolean updateHowToJazz;

    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_user_profile);

        Button feedButton = (Button) findViewById(R.id.feedButton);
        Button eventsButton = (Button) findViewById(R.id.eventsButton);
        Button liveButton = (Button) findViewById(R.id.liveButton);
        Button videosButton = (Button) findViewById(R.id.videosButton);
        Button userButton = (Button) findViewById(R.id.userButton);
        Button questionsAsked = (Button) findViewById(R.id.savedQuestionButton);

        Button settingsButton = (Button) findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                updateTopics();
                Intent i = new Intent(MainUserProfilePage.this, Settings.class);
                i.putExtra("key", 1);
                startActivity(i);

            }
        });



        final TextView labelUserName = (TextView) findViewById(R.id.userNameUser);
        final TextView labelBio = (TextView) findViewById(R.id.bioViewUser);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DatabaseReference ref = myRef.child("users").child(currentFirebaseUser.getUid());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                labelUserName.setText(dataSnapshot.child("Username").getValue().toString());
                labelBio.setText(dataSnapshot.child("Bio").getValue().toString());
                JazzCollege = dataSnapshot.child("JazzCollege").getValue().toString();
                Saxophones = dataSnapshot.child("Saxophones").getValue().toString();
                Trumpets = dataSnapshot.child("Trumpets").getValue().toString();
                Trombones = dataSnapshot.child("Trombones").getValue().toString();
                RhythmSection = dataSnapshot.child("RhythmSection").getValue().toString();
                ClassicJazz = dataSnapshot.child("ClassicJazz").getValue().toString();
                JazzFunk = dataSnapshot.child("JazzFunk").getValue().toString();
                JazzMusicians = dataSnapshot.child("JazzMusicians").getValue().toString();
                HowToJazz = dataSnapshot.child("HowToJazz").getValue().toString();
                userTopics(JazzCollege, Saxophones, Trumpets, Trombones, RhythmSection, ClassicJazz, JazzFunk, JazzMusicians, HowToJazz);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        ref = myRef.child("users").child(currentFirebaseUser.getUid());

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                quick(dataSnapshot.child("Instrument").getValue(String.class));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        questionsAsked.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                updateTopics();
                startActivity(new Intent(MainUserProfilePage.this, UserQuestionsAskedPage.class));

            }
        });

        feedButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                updateTopics();
                startActivity(new Intent(MainUserProfilePage.this, MainFeedPage.class));

            }
        });
        eventsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                updateTopics();
                startActivity(new Intent(MainUserProfilePage.this, MainTrendingPage.class));

            }
        });
        liveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                updateTopics();
                startActivity(new Intent(MainUserProfilePage.this, MainLiveAnalysisPage.class));

            }
        });
        videosButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                updateTopics();
                startActivity(i);

            }
        });
        userButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                updateTopics();
            }
        });
    }

    private void quick(String stringer){
        i = new Intent(MainUserProfilePage.this, MainVideosPage.class);
        i.putExtra("key", stringer);
    }

    private void updateTopics(){
        updateJazzCollege = ((CheckBox) findViewById(R.id.checkJazzCollegesUser)).isChecked();
        updateSaxophones = ((CheckBox) findViewById(R.id.checkSaxophonesUser)).isChecked();
        updateTrumpets = ((CheckBox) findViewById(R.id.checkTrumpetsUser)).isChecked();
        updateTrombones = ((CheckBox) findViewById(R.id.checkTrombonesUser)).isChecked();
        updateRhythmSection = ((CheckBox) findViewById(R.id.checkRhythmSectionUser)).isChecked();
        updateClassicJazz = ((CheckBox) findViewById(R.id.checkClassicJazzUser)).isChecked();
        updateJazzFunk = ((CheckBox) findViewById(R.id.checkJazzFunkUser)).isChecked();
        updateJazzMusicians = ((CheckBox) findViewById(R.id.checkJazzMusiciansUser)).isChecked();
        updateHowToJazz = ((CheckBox) findViewById(R.id.checkHowToUser)).isChecked();


        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("users").child(currentFirebaseUser.getUid()).child("JazzCollege").setValue(updateJazzCollege);
        myRef.child("users").child(currentFirebaseUser.getUid()).child("Saxophones").setValue(updateSaxophones);
        myRef.child("users").child(currentFirebaseUser.getUid()).child("Trumpets").setValue(updateTrumpets);
        myRef.child("users").child(currentFirebaseUser.getUid()).child("Trombones").setValue(updateTrombones);
        myRef.child("users").child(currentFirebaseUser.getUid()).child("RhythmSection").setValue(updateRhythmSection);
        myRef.child("users").child(currentFirebaseUser.getUid()).child("ClassicJazz").setValue(updateClassicJazz);
        myRef.child("users").child(currentFirebaseUser.getUid()).child("JazzFunk").setValue(updateJazzFunk);
        myRef.child("users").child(currentFirebaseUser.getUid()).child("JazzMusicians").setValue(updateJazzMusicians);
        myRef.child("users").child(currentFirebaseUser.getUid()).child("HowToJazz").setValue(updateHowToJazz);
    }

    private void userTopics(String JazzCollege, String Saxophones, String Trumpets, String Trombones, String RhythmSection, String ClassicJazz, String JazzFunk, String JazzMusicians, String HowToJazz){
        if (JazzCollege.equals("true")){
            ((CheckBox) findViewById(R.id.checkJazzCollegesUser)).setChecked(true);
        }
        if (Saxophones.equals("true")){
            ((CheckBox) findViewById(R.id.checkSaxophonesUser)).setChecked(true);
        }
        if (Trumpets.equals("true")){
            ((CheckBox) findViewById(R.id.checkTrumpetsUser)).setChecked(true);
        }
        if (Trombones.equals("true")){
            ((CheckBox) findViewById(R.id.checkTrombonesUser)).setChecked(true);
        }
        if (RhythmSection.equals("true")){
            ((CheckBox) findViewById(R.id.checkRhythmSectionUser)).setChecked(true);
        }
        if (ClassicJazz.equals("true")){
            ((CheckBox) findViewById(R.id.checkClassicJazzUser)).setChecked(true);
        }
        if (JazzFunk.equals("true")){
            ((CheckBox) findViewById(R.id.checkJazzFunkUser)).setChecked(true);
        }
        if (JazzMusicians.equals("true")){
            ((CheckBox) findViewById(R.id.checkJazzMusiciansUser)).setChecked(true);
        }
        if (HowToJazz.equals("true")){
            ((CheckBox) findViewById(R.id.checkHowToUser)).setChecked(true);
        }
    }
}
