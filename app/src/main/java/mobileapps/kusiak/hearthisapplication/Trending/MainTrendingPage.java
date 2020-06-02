package mobileapps.kusiak.hearthisapplication.Trending;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import mobileapps.kusiak.hearthisapplication.Feed.MainFeedPage;
import mobileapps.kusiak.hearthisapplication.Feed.ViewPost;
import mobileapps.kusiak.hearthisapplication.LiveAnalysis.MainLiveAnalysisPage;
import mobileapps.kusiak.hearthisapplication.R;
import mobileapps.kusiak.hearthisapplication.UserProfile.MainUserProfilePage;
import mobileapps.kusiak.hearthisapplication.Videos.MainVideosPage;

public class MainTrendingPage extends AppCompatActivity {

    final public ArrayList<String> allQuestionsClassicJazz = new ArrayList<String>();
    final public ArrayList<Integer> allQuestionsCommentsClassicJazz = new ArrayList<Integer>();
    final public ArrayList<String> allQuestionsHowToJazz = new ArrayList<String>();
    final public ArrayList<Integer> allQuestionsCommentsHowToJazz = new ArrayList<Integer>();
    final public ArrayList<String> allQuestionsJazzCollege = new ArrayList<String>();
    final public ArrayList<Integer> allQuestionsCommentsJazzCollege = new ArrayList<Integer>();
    final public ArrayList<String> allQuestionsJazzFunk = new ArrayList<String>();
    final public ArrayList<Integer> allQuestionsCommentsJazzFunk = new ArrayList<Integer>();
    final public ArrayList<String> allQuestionsJazzMusicians = new ArrayList<String>();
    final public ArrayList<Integer> allQuestionsCommentsJazzMusicians = new ArrayList<Integer>();
    final public ArrayList<String> allQuestionsRhythmSection = new ArrayList<String>();
    final public ArrayList<Integer> allQuestionsCommentsRhythmSection = new ArrayList<Integer>();
    final public ArrayList<String> allQuestionsSaxophones = new ArrayList<String>();
    final public ArrayList<Integer> allQuestionsCommentsSaxophones = new ArrayList<Integer>();
    final public ArrayList<String> allQuestionsTrombones = new ArrayList<String>();
    final public ArrayList<Integer> allQuestionsCommentsTrombones = new ArrayList<Integer>();
    final public ArrayList<String> allQuestionsTrumpets = new ArrayList<String>();
    final public ArrayList<Integer> allQuestionsCommentsTrumpets = new ArrayList<Integer>();

    final public ArrayList<String> AllTrending = new ArrayList<String>();


    final public ArrayList<String> allQuestionsLegit = new ArrayList<String>();
    public int cstant;

    boolean found;

    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_trending);

        Button feedButton = (Button) findViewById(R.id.feedButton);
        Button liveButton = (Button) findViewById(R.id.liveButton);
        Button videosButton = (Button) findViewById(R.id.videosButton);
        Button userButton = (Button) findViewById(R.id.userButton);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DatabaseReference ref = myRef.child("users").child(currentFirebaseUser.getUid());

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                quick(dataSnapshot.child("Instrument").getValue(String.class));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        feedButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(MainTrendingPage.this, MainFeedPage.class));

            }
        });
        liveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(MainTrendingPage.this, MainLiveAnalysisPage.class));

            }
        });
        videosButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(i);

            }
        });
        userButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(MainTrendingPage.this, MainUserProfilePage.class));

            }
        });

        putTrending();

        Button searchButton = (Button) findViewById(R.id.searchButton);
        final EditText search = (EditText) findViewById(R.id.searchTrending);

        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                searching(search.getText().toString());

            }
        });

    }

    private void quick(String stringer){
        i = new Intent(MainTrendingPage.this, MainVideosPage.class);
        i.putExtra("key", stringer);
    }

    private void searching(final String name){
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DatabaseReference ref = myRef.child("users");
        Log.i("searched", name);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int sizeeeeeeeee = (int) dataSnapshot.getChildrenCount();

                for(int i = 1; i <= sizeeeeeeeee/2; i++){
                    if(name.equals(dataSnapshot.child(String.valueOf(i)).child("user").getValue(String.class))){
                        found = true;
                        Intent k = new Intent(MainTrendingPage.this, OtherUserProfilePage.class);
                        k.putExtra("key", dataSnapshot.child(String.valueOf(i)).child(name).getValue(String.class));
                        startActivity(k);
                    }
                }

                if (!found){
                    Toast.makeText(MainTrendingPage.this,
                            "There is no such user!", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    private void putTrending(){
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        DatabaseReference ref = myRef.child("JazzCollegeTopics");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cstant = (int) dataSnapshot.getChildrenCount();

                for (int i = 1; i <= cstant/2; i++){
                    if (i == cstant/2){
                        legitJazzCollege(dataSnapshot.child(String.valueOf(i)).getValue().toString(), (int) dataSnapshot.child(String.valueOf(i) + " Comments").getChildrenCount(), true);
                    }
                    else{
                        legitJazzCollege(dataSnapshot.child(String.valueOf(i)).getValue().toString(), (int) dataSnapshot.child(String.valueOf(i) + " Comments").getChildrenCount(), false);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        ref = myRef.child("SaxophonesTopics");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cstant = (int) dataSnapshot.getChildrenCount();

                for (int i = 1; i <= cstant/2; i++){
                    if (i == cstant/2){
                        legitSaxophones(dataSnapshot.child(String.valueOf(i)).getValue().toString(), (int) dataSnapshot.child(String.valueOf(i) + " Comments").getChildrenCount(), true);
                    }
                    else{
                        legitSaxophones(dataSnapshot.child(String.valueOf(i)).getValue().toString(), (int) dataSnapshot.child(String.valueOf(i) + " Comments").getChildrenCount(), false);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        ref = myRef.child("TrumpetsTopics");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cstant = (int) dataSnapshot.getChildrenCount();

                for (int i = 1; i <= cstant/2; i++){
                    if (i == cstant/2){
                        legitTrumpets(dataSnapshot.child(String.valueOf(i)).getValue().toString(), (int) dataSnapshot.child(String.valueOf(i) + " Comments").getChildrenCount(), true);
                    }
                    else{
                        legitTrumpets(dataSnapshot.child(String.valueOf(i)).getValue().toString(), (int) dataSnapshot.child(String.valueOf(i) + " Comments").getChildrenCount(), false);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        ref = myRef.child("TrombonesTopics");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cstant = (int) dataSnapshot.getChildrenCount();

                for (int i = 1; i <= cstant/2; i++){
                    if (i == cstant/2){
                        legitTrombones(dataSnapshot.child(String.valueOf(i)).getValue().toString(), (int) dataSnapshot.child(String.valueOf(i) + " Comments").getChildrenCount(), true);
                    }
                    else{
                        legitTrombones(dataSnapshot.child(String.valueOf(i)).getValue().toString(), (int) dataSnapshot.child(String.valueOf(i) + " Comments").getChildrenCount(), false);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        ref = myRef.child("RhythmSectionTopics");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cstant = (int) dataSnapshot.getChildrenCount();

                for (int i = 1; i <= cstant/2; i++){
                    if (i == cstant/2){
                        legitRhythmSection(dataSnapshot.child(String.valueOf(i)).getValue().toString(), (int) dataSnapshot.child(String.valueOf(i) + " Comments").getChildrenCount(), true);
                    }
                    else{
                        legitRhythmSection(dataSnapshot.child(String.valueOf(i)).getValue().toString(), (int) dataSnapshot.child(String.valueOf(i) + " Comments").getChildrenCount(), false);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        ref = myRef.child("ClassicJazzTopics");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cstant = (int) dataSnapshot.getChildrenCount();

                for (int i = 1; i <= cstant/2; i++){
                    if (i == cstant/2){
                        legitClassicJazz(dataSnapshot.child(String.valueOf(i)).getValue().toString(), (int) dataSnapshot.child(String.valueOf(i) + " Comments").getChildrenCount(), true);
                    }
                    else{
                        legitClassicJazz(dataSnapshot.child(String.valueOf(i)).getValue().toString(), (int) dataSnapshot.child(String.valueOf(i) + " Comments").getChildrenCount(), false);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        ref = myRef.child("JazzFunkTopics");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cstant = (int) dataSnapshot.getChildrenCount();

                for (int i = 1; i <= cstant/2; i++){
                    if (i == cstant/2){
                        legitJazzFunk(dataSnapshot.child(String.valueOf(i)).getValue().toString(), (int) dataSnapshot.child(String.valueOf(i) + " Comments").getChildrenCount(), true);
                    }
                    else{
                        legitJazzFunk(dataSnapshot.child(String.valueOf(i)).getValue().toString(), (int) dataSnapshot.child(String.valueOf(i) + " Comments").getChildrenCount(), false);
                    }
                }


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        ref = myRef.child("JazzMusiciansTopics");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cstant = (int) dataSnapshot.getChildrenCount();

                for (int i = 1; i <= cstant / 2; i++){
                    if (i == cstant/2){
                        legitJazzMusicians(dataSnapshot.child(String.valueOf(i)).getValue().toString(), (int) dataSnapshot.child(String.valueOf(i) + " Comments").getChildrenCount(), true);
                    }
                    else{
                        legitJazzMusicians(dataSnapshot.child(String.valueOf(i)).getValue().toString(), (int) dataSnapshot.child(String.valueOf(i) + " Comments").getChildrenCount(), false);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        ref = myRef.child("HowToJazzTopics");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cstant = (int) dataSnapshot.getChildrenCount();

                for (int i = 1; i <= cstant/2; i++){
                    if (i == cstant/2){
                        legitHowToJazz(dataSnapshot.child(String.valueOf(i)).getValue().toString(), (int) dataSnapshot.child(String.valueOf(i) + " Comments").getChildrenCount(), true);
                    }
                    else{
                        legitHowToJazz(dataSnapshot.child(String.valueOf(i)).getValue().toString(), (int) dataSnapshot.child(String.valueOf(i) + " Comments").getChildrenCount(), false);
                    }
                }


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    private void legitJazzCollege(String adder, int childrenCount, boolean boo){
        allQuestionsJazzCollege.add(adder);
        allQuestionsCommentsJazzCollege.add(childrenCount);
        if (boo){
            Collections.sort(allQuestionsCommentsJazzCollege);
            final int constant = allQuestionsCommentsJazzCollege.get(allQuestionsCommentsJazzCollege.size() - 1);

            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            DatabaseReference ref = myRef.child("JazzCollegeTopics");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    cstant = (int) dataSnapshot.getChildrenCount();
                    for (int i = 1; i <= cstant/2; i++){
                        if ((int) dataSnapshot.child(String.valueOf(i) + " Comments").getChildrenCount() == constant){
                            anyTime(dataSnapshot.child(String.valueOf(i)).getValue().toString());
                            break;
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }

    private void legitSaxophones(String adder, int childrenCount, boolean boo){
        allQuestionsSaxophones.add(adder);
        allQuestionsCommentsSaxophones.add(childrenCount);
        if (boo){
            Collections.sort(allQuestionsCommentsSaxophones);
            final int constant = allQuestionsCommentsSaxophones.get(allQuestionsCommentsSaxophones.size() - 1);

            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            DatabaseReference ref = myRef.child("SaxophonesTopics");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    cstant = (int) dataSnapshot.getChildrenCount();
                    for (int i = 1; i <= cstant/2; i++){
                        if ((int) dataSnapshot.child(String.valueOf(i) + " Comments").getChildrenCount() == constant){
                            anyTime(dataSnapshot.child(String.valueOf(i)).getValue().toString());
                            break;
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }

    private void legitTrumpets(String adder, int childrenCount, boolean boo){
        allQuestionsTrumpets.add(adder);
        allQuestionsCommentsTrumpets.add(childrenCount);
        if (boo){
            Collections.sort(allQuestionsCommentsTrumpets);
            final int constant = allQuestionsCommentsTrumpets.get(allQuestionsCommentsTrumpets.size() - 1);

            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            DatabaseReference ref = myRef.child("TrumpetsTopics");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    cstant = (int) dataSnapshot.getChildrenCount();
                    for (int i = 1; i <= cstant/2; i++){
                        if ((int) dataSnapshot.child(String.valueOf(i) + " Comments").getChildrenCount() == constant){
                            anyTime(dataSnapshot.child(String.valueOf(i)).getValue().toString());
                            break;
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }

    private void legitTrombones(String adder, int childrenCount, boolean boo){
        allQuestionsTrombones.add(adder);
        allQuestionsCommentsTrombones.add(childrenCount);
        if (boo){
            Collections.sort(allQuestionsCommentsTrombones);
            final int constant = allQuestionsCommentsTrombones.get(allQuestionsCommentsTrombones.size() - 1);

            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            DatabaseReference ref = myRef.child("TrombonesTopics");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    cstant = (int) dataSnapshot.getChildrenCount();
                    for (int i = 1; i <= cstant/2; i++){
                        if ((int) dataSnapshot.child(String.valueOf(i) + " Comments").getChildrenCount() == constant){
                            anyTime(dataSnapshot.child(String.valueOf(i)).getValue().toString());
                            break;
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }

    private void legitRhythmSection(String adder, int childrenCount, boolean boo){
        allQuestionsRhythmSection.add(adder);
        allQuestionsCommentsRhythmSection.add(childrenCount);
        if (boo){
            Collections.sort(allQuestionsCommentsRhythmSection);
            final int constant = allQuestionsCommentsRhythmSection.get(allQuestionsCommentsRhythmSection.size() - 1);

            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            DatabaseReference ref = myRef.child("RhythmSectionTopics");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    cstant = (int) dataSnapshot.getChildrenCount();
                    for (int i = 1; i <= cstant/2; i++){
                        if ((int) dataSnapshot.child(String.valueOf(i) + " Comments").getChildrenCount() == constant){
                            anyTime(dataSnapshot.child(String.valueOf(i)).getValue().toString());
                            break;
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }


    private void legitClassicJazz(String adder, int childrenCount, boolean boo){
        allQuestionsClassicJazz.add(adder);
        allQuestionsCommentsClassicJazz.add(childrenCount);
        if (boo){
            Collections.sort(allQuestionsCommentsClassicJazz);
            final int constant = allQuestionsCommentsClassicJazz.get(allQuestionsCommentsClassicJazz.size() - 1);

            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            DatabaseReference ref = myRef.child("ClassicJazzTopics");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    cstant = (int) dataSnapshot.getChildrenCount();
                    for (int i = 1; i <= cstant/2; i++){
                        if ((int) dataSnapshot.child(String.valueOf(i) + " Comments").getChildrenCount() == constant){
                            anyTime(dataSnapshot.child(String.valueOf(i)).getValue().toString());
                            break;
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }

    private void legitJazzFunk(String adder, int childrenCount, boolean boo){
        allQuestionsJazzFunk.add(adder);
        allQuestionsCommentsJazzFunk.add(childrenCount);
        if (boo){
            Collections.sort(allQuestionsCommentsJazzFunk);
            final int constant = allQuestionsCommentsJazzFunk.get(allQuestionsCommentsJazzFunk.size() - 1);

            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            DatabaseReference ref = myRef.child("JazzFunkTopics");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    cstant = (int) dataSnapshot.getChildrenCount();
                    for (int i = 1; i <= cstant/2; i++){
                        if ((int) dataSnapshot.child(String.valueOf(i) + " Comments").getChildrenCount() == constant){
                            anyTime(dataSnapshot.child(String.valueOf(i)).getValue().toString());
                            break;
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }

    private void legitJazzMusicians(String adder, int childrenCount, boolean boo){
        allQuestionsJazzMusicians.add(adder);
        allQuestionsCommentsJazzMusicians.add(childrenCount);
        if (boo){
            Collections.sort(allQuestionsCommentsJazzMusicians);
            final int constant = allQuestionsCommentsJazzMusicians.get(allQuestionsCommentsJazzMusicians.size() - 1);

            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            DatabaseReference ref = myRef.child("JazzMusiciansTopics");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    cstant = (int) dataSnapshot.getChildrenCount();
                    for (int i = 1; i <= cstant/2; i++){
                        if ((int) dataSnapshot.child(String.valueOf(i) + " Comments").getChildrenCount() == constant){
                            anyTime(dataSnapshot.child(String.valueOf(i)).getValue().toString());
                            break;
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }

    private void legitHowToJazz(String adder, int childrenCount, boolean boo){
        allQuestionsHowToJazz.add(adder);
        allQuestionsCommentsHowToJazz.add(childrenCount);
        if (boo){
            Collections.sort(allQuestionsCommentsHowToJazz);
            final int constant = allQuestionsCommentsHowToJazz.get(allQuestionsCommentsHowToJazz.size() - 1);


            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            DatabaseReference ref = myRef.child("HowToJazzTopics");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    cstant = (int) dataSnapshot.getChildrenCount();
                    for (int i = 1; i <= cstant/2; i++){
                        if ((int) dataSnapshot.child(String.valueOf(i) + " Comments").getChildrenCount() == constant){
                            anyTime(dataSnapshot.child(String.valueOf(i)).getValue().toString());
                            break;
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }


    private void anyTime(String thisThat){
        AllTrending.add(thisThat);

        if (AllTrending.size() == 9){
            TextView textView1 = (TextView)findViewById(R.id.trending1);
            TextView textView2 = (TextView)findViewById(R.id.trending2);
            TextView textView3 = (TextView)findViewById(R.id.trending3);
            TextView textView4 = (TextView)findViewById(R.id.trending4);
            TextView textView5 = (TextView)findViewById(R.id.trending5);
            TextView textView6 = (TextView)findViewById(R.id.trending6);
            TextView textView7 = (TextView)findViewById(R.id.trending7);
            TextView textView8 = (TextView)findViewById(R.id.trending8);
            TextView textView9 = (TextView)findViewById(R.id.trending9);


            textView1.setText(AllTrending.get(0));
            textView2.setText(AllTrending.get(1));
            textView3.setText(AllTrending.get(2));
            textView4.setText(AllTrending.get(3));
            textView5.setText(AllTrending.get(4));
            textView6.setText(AllTrending.get(5));
            textView7.setText(AllTrending.get(6));
            textView8.setText(AllTrending.get(7));
            textView9.setText(AllTrending.get(8));

            textView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainTrendingPage.this, ViewPost.class);
                    i.putExtra("key", AllTrending.get(0));
                    i.putExtra("activity", 2);
                    startActivity(i);
                }
            });

            textView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainTrendingPage.this, ViewPost.class);
                    i.putExtra("key", AllTrending.get(1));
                    i.putExtra("activity", 2);
                    startActivity(i);
                }
            });

            textView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainTrendingPage.this, ViewPost.class);
                    i.putExtra("key", AllTrending.get(2));
                    i.putExtra("activity", 2);
                    startActivity(i);
                }
            });

            textView4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainTrendingPage.this, ViewPost.class);
                    i.putExtra("key", AllTrending.get(3));
                    i.putExtra("activity", 2);
                    startActivity(i);
                }
            });

            textView5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainTrendingPage.this, ViewPost.class);
                    i.putExtra("key", AllTrending.get(4));
                    i.putExtra("activity", 2);
                    startActivity(i);
                }
            });

            textView6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainTrendingPage.this, ViewPost.class);
                    i.putExtra("key", AllTrending.get(5));
                    i.putExtra("activity", 2);
                    startActivity(i);
                }
            });

            textView7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainTrendingPage.this, ViewPost.class);
                    i.putExtra("key", AllTrending.get(6));
                    i.putExtra("activity", 2);
                    startActivity(i);
                }
            });

            textView8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainTrendingPage.this, ViewPost.class);
                    i.putExtra("key", AllTrending.get(7));
                    i.putExtra("activity", 2);
                    startActivity(i);
                }
            });

            textView9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainTrendingPage.this, ViewPost.class);
                    i.putExtra("key", AllTrending.get(8));
                    i.putExtra("activity", 2);
                    startActivity(i);
                }
            });
        }
    }


}
