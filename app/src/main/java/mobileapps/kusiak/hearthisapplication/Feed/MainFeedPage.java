package mobileapps.kusiak.hearthisapplication.Feed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.Collections;

import mobileapps.kusiak.hearthisapplication.LiveAnalysis.MainLiveAnalysisPage;
import mobileapps.kusiak.hearthisapplication.R;
import mobileapps.kusiak.hearthisapplication.Trending.MainTrendingPage;
import mobileapps.kusiak.hearthisapplication.UserProfile.MainUserProfilePage;
import mobileapps.kusiak.hearthisapplication.Videos.MainVideosPage;

//import mobileapps.kusiak.hearthisapplication.LiveAnalysis.MainLiveAnalysisPage;

public class MainFeedPage extends AppCompatActivity {
    private String firstChildTopics = "";


    private String someVariable;

    public String getSomeVariable() {
        return someVariable;
    }

    public void setSomeVariable(String someVariable) {
        this.someVariable = someVariable;
    }

    private String JazzCollege, Saxophones, Trumpets, Trombones, RhythmSection, ClassicJazz, JazzFunk, JazzMusicians, HowToJazz;

    private String firstTopic, secondTopic, thirdTopic, fourthTopic;


    public int JazzCollegeA, SaxophonesA, TrumpetsA, TrombonesA, RhythmSectionA, ClassicJazzA, JazzFunkA, JazzMusiciansA, HowToJazzA;

    public int sized;


    public String constant;

    final public ArrayList<String> list = new ArrayList<String>();

    public int AllThings = 0;

    private Intent i;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_feed);




        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DatabaseReference ref = myRef.child("users").child(currentFirebaseUser.getUid());



            Button refresh = (Button) findViewById(R.id.refreshButton);
            refresh.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    startActivity(new Intent(MainFeedPage.this, MainFeedPage.class));

                }
            });


            Button createPost = (Button) findViewById(R.id.createButton);
            createPost.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    startActivity(new Intent(MainFeedPage.this, MakePost.class));

                }
            });

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                JazzCollege = dataSnapshot.child("JazzCollege").getValue().toString();
                Saxophones = dataSnapshot.child("Saxophones").getValue().toString();
                Trumpets = dataSnapshot.child("Trumpets").getValue().toString();
                Trombones = dataSnapshot.child("Trombones").getValue().toString();
                RhythmSection = dataSnapshot.child("RhythmSection").getValue().toString();
                ClassicJazz = dataSnapshot.child("ClassicJazz").getValue().toString();
                JazzFunk = dataSnapshot.child("JazzFunk").getValue().toString();
                JazzMusicians = dataSnapshot.child("JazzMusicians").getValue().toString();
                HowToJazz = dataSnapshot.child("HowToJazz").getValue().toString();
                createTopics(JazzCollege, Saxophones, Trumpets, Trombones, RhythmSection, ClassicJazz, JazzFunk, JazzMusicians, HowToJazz);
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





            Button eventsButton = (Button) findViewById(R.id.eventsButton);
        Button liveButton = (Button) findViewById(R.id.liveButton);
        Button videosButton = (Button) findViewById(R.id.videosButton);
        Button userButton = (Button) findViewById(R.id.userButton);


        eventsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(MainFeedPage.this, MainTrendingPage.class));

            }
        });
        liveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(MainFeedPage.this, MainLiveAnalysisPage.class));

            }
        });

        videosButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(i);
            }
        });
        userButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(MainFeedPage.this, MainUserProfilePage.class));

            }
        });

    }

    private void quick(String stringer){
        i = new Intent(MainFeedPage.this, MainVideosPage.class);
        i.putExtra("key", stringer);
    }

    private void createTopics(String JazzCollege, String Saxophones, String Trumpets, String Trombones, String RhythmSection, String ClassicJazz, String JazzFunk, String JazzMusicians, String HowToJazz){
        if (JazzCollege == "true"){
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            DatabaseReference ref = myRef.child("JazzCollegeTopics");

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    JazzCollegeA = (int) dataSnapshot.getChildrenCount();

                    AllThings = AllThings + (JazzCollegeA / 2);

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        if (Saxophones == "true"){
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            DatabaseReference ref = myRef.child("SaxophonesTopics");

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    SaxophonesA = (int) dataSnapshot.getChildrenCount();

                    AllThings = AllThings + (SaxophonesA / 2);

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        if (Trumpets == "true"){
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            DatabaseReference ref = myRef.child("TrumpetsTopics");

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    TrumpetsA = (int) dataSnapshot.getChildrenCount();

                    AllThings = AllThings + (TrumpetsA / 2);

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        if (Trombones == "true"){
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            DatabaseReference ref = myRef.child("TrombonesTopics");

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    TrombonesA = (int) dataSnapshot.getChildrenCount();

                    AllThings = AllThings + (TrombonesA / 2);

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        if (RhythmSection == "true"){
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            DatabaseReference ref = myRef.child("RhythmSectionTopics");

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    RhythmSectionA = (int) dataSnapshot.getChildrenCount();

                    AllThings = AllThings + (RhythmSectionA / 2);

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        if (ClassicJazz == "true"){
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            DatabaseReference ref = myRef.child("ClassicJazzTopics");

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ClassicJazzA = (int) dataSnapshot.getChildrenCount();

                    AllThings = AllThings + (ClassicJazzA / 2);

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        if (JazzFunk == "true"){
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            DatabaseReference ref = myRef.child("JazzFunkTopics");

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    JazzFunkA = (int) dataSnapshot.getChildrenCount();

                    AllThings = AllThings + (JazzFunkA / 2);

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        if (JazzMusicians == "true"){
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            DatabaseReference ref = myRef.child("JazzMusiciansTopics");

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    JazzMusiciansA = (int) dataSnapshot.getChildrenCount();

                    AllThings = AllThings + (JazzMusiciansA / 2);

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        if (HowToJazz == "true"){
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            DatabaseReference ref = myRef.child("HowToJazzTopics");

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    HowToJazzA = (int) dataSnapshot.getChildrenCount();

                    AllThings = AllThings + (HowToJazzA / 2);

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }








            if (JazzCollege == "true"){
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            DatabaseReference ref = myRef.child("JazzCollegeTopics");

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    JazzCollegeA = (int) dataSnapshot.getChildrenCount();

                    textViewSort("JazzCollegeTopics",JazzCollegeA);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        if (Saxophones == "true"){
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            DatabaseReference ref = myRef.child("SaxophonesTopics");

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    SaxophonesA = (int) dataSnapshot.getChildrenCount();

                    textViewSort("SaxophonesTopics",SaxophonesA);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        if (Trumpets == "true"){
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            DatabaseReference ref = myRef.child("TrumpetsTopics");

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    TrumpetsA = (int) dataSnapshot.getChildrenCount();

                    textViewSort("TrumpetsTopics",TrumpetsA);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        if (Trombones == "true"){
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            DatabaseReference ref = myRef.child("TrombonesTopics");

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    TrombonesA = (int) dataSnapshot.getChildrenCount();

                    textViewSort("TrombonesTopics",TrombonesA);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        if (RhythmSection == "true"){
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            DatabaseReference ref = myRef.child("RhythmSectionTopics");

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    RhythmSectionA = (int) dataSnapshot.getChildrenCount();

                    textViewSort("RhythmSectionTopics",RhythmSectionA);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        if (ClassicJazz == "true"){
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            DatabaseReference ref = myRef.child("ClassicJazzTopics");

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ClassicJazzA = (int) dataSnapshot.getChildrenCount();


                    textViewSort("ClassicJazzTopics",ClassicJazzA);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        if (JazzFunk == "true"){
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            DatabaseReference ref = myRef.child("JazzFunkTopics");

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    JazzFunkA = (int) dataSnapshot.getChildrenCount();


                    textViewSort("JazzFunkTopics",JazzFunkA);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        if (JazzMusicians == "true"){
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            DatabaseReference ref = myRef.child("JazzMusiciansTopics");

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    JazzMusiciansA = (int) dataSnapshot.getChildrenCount();


                    textViewSort("JazzMusiciansTopics",JazzMusiciansA);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        if (HowToJazz == "true"){
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            DatabaseReference ref = myRef.child("HowToJazzTopics");

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    HowToJazzA = (int) dataSnapshot.getChildrenCount();


                    textViewSort("HowToJazzTopics",HowToJazzA);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }


        //-----------------------------------------------------------------------------------------------------
    }



    private void textViewSort(final String childTopics, final int size){

        final TextView textView = new TextView(this);

        for(int i = 1; i <= size; i = i + 1){
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            DatabaseReference ref = myRef.child(childTopics);

            final String constant = String.valueOf(i);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    Log.i("given value", dataSnapshot.child(constant).getValue(String.class));
                    if (dataSnapshot.child(constant).getValue(String.class) != null){
                        list.add(dataSnapshot.child(constant).getValue(String.class));
                    }

                    if (list.size() == AllThings){
                        post(list, size);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }

    private void post(ArrayList list, int size){
        TextView textView1 = (TextView)findViewById(R.id.textView1);
        TextView textView2 = (TextView)findViewById(R.id.textView2);
        TextView textView3 = (TextView)findViewById(R.id.textView3);
        TextView textView4 = (TextView)findViewById(R.id.textView4);
        TextView textView5 = (TextView)findViewById(R.id.textView5);

        Collections.shuffle(list);
        final String one = String.valueOf(list.get(0));
        final String two = String.valueOf(list.get(1));
        final String three = String.valueOf(list.get(2));
        final String four = String.valueOf(list.get(3));
        final String five = String.valueOf(list.get(4));

        textView1.setText(String.valueOf(list.get(0)));
        textView2.setText(String.valueOf(list.get(1)));
        textView3.setText(String.valueOf(list.get(2)));
        textView4.setText(String.valueOf(list.get(3)));
        textView5.setText(String.valueOf(list.get(4)));

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainFeedPage.this, ViewPost.class);
                i.putExtra("key", one);
                i.putExtra("activity", 1);
                startActivity(i);
            }
        });

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainFeedPage.this, ViewPost.class);
                i.putExtra("key", two);
                i.putExtra("activity", 1);
                startActivity(i);
            }
        });

        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainFeedPage.this, ViewPost.class);
                i.putExtra("key", three);
                i.putExtra("activity", 1);
                startActivity(i);
            }
        });

        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainFeedPage.this, ViewPost.class);
                i.putExtra("key", four);
                i.putExtra("activity", 1);
                startActivity(i);
            }
        });

        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainFeedPage.this, ViewPost.class);
                i.putExtra("key", five);
                i.putExtra("activity", 1);
                startActivity(i);
            }
        });
    }
}


