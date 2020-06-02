package mobileapps.kusiak.hearthisapplication.Trending;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import mobileapps.kusiak.hearthisapplication.Trending.MainTrendingPage;
import mobileapps.kusiak.hearthisapplication.Feed.MainFeedPage;
import mobileapps.kusiak.hearthisapplication.LiveAnalysis.MainLiveAnalysisPage;
import mobileapps.kusiak.hearthisapplication.R;
import mobileapps.kusiak.hearthisapplication.Videos.MainVideosPage;

public class OtherUserProfilePage extends AppCompatActivity {

    private String JazzCollege, Saxophones, Trumpets, Trombones, RhythmSection, ClassicJazz, JazzFunk, JazzMusicians, HowToJazz;

    private String passedValue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.look_at_user_profile);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            passedValue = extras.getString("key");
        }

        Button questionsAsked = (Button) findViewById(R.id.savedQuestionButton);

        Button goBack = (Button) findViewById(R.id.goBackButtonTrending);


        final TextView labelUserName = (TextView) findViewById(R.id.userNameUser);
        final TextView labelBio = (TextView) findViewById(R.id.bioViewUser);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DatabaseReference ref = myRef.child("users").child(passedValue);

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

        questionsAsked.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent k = new Intent(OtherUserProfilePage.this, OtherUserQuestionsAskedPage.class);
                k.putExtra("key", passedValue);
                startActivity(k);

            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(OtherUserProfilePage.this, MainTrendingPage.class));

            }
        });

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
