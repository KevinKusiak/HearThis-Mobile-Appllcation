package mobileapps.kusiak.hearthisapplication.UserProfile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import mobileapps.kusiak.hearthisapplication.Feed.MainFeedPage;
import mobileapps.kusiak.hearthisapplication.Feed.ViewPost;
import mobileapps.kusiak.hearthisapplication.LiveAnalysis.MainLiveAnalysisPage;
import mobileapps.kusiak.hearthisapplication.R;
import mobileapps.kusiak.hearthisapplication.Trending.MainTrendingPage;
import mobileapps.kusiak.hearthisapplication.Videos.MainVideosPage;

public class UserQuestionsAskedPage extends AppCompatActivity {

    public int databaseChildSize;

    public String oneQuestion;
    public String oneTopic;

    private int countPages;

    private ArrayList<String> extraQuestions = new ArrayList<String>();
    private boolean page = false;

    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.askedquestions_user_profile);

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
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        Button upButton = (Button) findViewById(R.id.scrollUpQA);
        Button downButton = (Button) findViewById(R.id.scrollDownQA);
        upButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                managePagesUp();
            }
        });
        downButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                managePagesDown();
            }
        });

        Button feedButton = (Button) findViewById(R.id.feedButton);
        Button eventsButton = (Button) findViewById(R.id.eventsButton);
        Button liveButton = (Button) findViewById(R.id.liveButton);
        Button videosButton = (Button) findViewById(R.id.videosButton);
        Button userButton = (Button) findViewById(R.id.userButton);
        Button topicButton = (Button) findViewById(R.id.topicButton);

        Button settingsButton = (Button) findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(UserQuestionsAskedPage.this, Settings.class);
                i.putExtra("key", 2);
                startActivity(i);

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

        topicButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(UserQuestionsAskedPage.this, MainUserProfilePage.class));

            }
        });

        feedButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(UserQuestionsAskedPage.this, MainFeedPage.class));

            }
        });
        eventsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(UserQuestionsAskedPage.this, MainTrendingPage.class));

            }
        });
        liveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(UserQuestionsAskedPage.this, MainLiveAnalysisPage.class));

            }
        });
        videosButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(i);

            }
        });

        askedQuestions();
    }

    private void quick(String stringer){
        i = new Intent(UserQuestionsAskedPage.this, MainVideosPage.class);
        i.putExtra("key", stringer);
    }

    private void managePagesUp(){
        TextView textView1 = (TextView)findViewById(R.id.asked1);
        TextView textView2 = (TextView)findViewById(R.id.asked2);
        TextView textView3 = (TextView)findViewById(R.id.asked3);
        TextView textView4 = (TextView)findViewById(R.id.asked4);
        TextView textView5 = (TextView)findViewById(R.id.asked5);

        if (countPages == 1){
            Toast.makeText(UserQuestionsAskedPage.this,
                    "There are no earlier questions!", Toast.LENGTH_LONG).show();

        }
        else{
            countPages -= 1;
            textView1.setText(String.valueOf(extraQuestions.get((countPages - 1) * 5)));
            textView2.setText(String.valueOf(extraQuestions.get(((countPages - 1) * 5) + 1)));
            textView3.setText(String.valueOf(extraQuestions.get(((countPages - 1) * 5)+ 2)));
            textView4.setText(String.valueOf(extraQuestions.get(((countPages - 1) * 5) + 3)));
            textView5.setText(String.valueOf(extraQuestions.get(((countPages - 1) * 5) + 4)));
            page = false;
        }
    }
    private void managePagesDown(){
        TextView textView1 = (TextView)findViewById(R.id.asked1);
        TextView textView2 = (TextView)findViewById(R.id.asked2);
        TextView textView3 = (TextView)findViewById(R.id.asked3);
        TextView textView4 = (TextView)findViewById(R.id.asked4);
        TextView textView5 = (TextView)findViewById(R.id.asked5);

        if(extraQuestions.size() <=5){
            Toast.makeText(UserQuestionsAskedPage.this,
                    "You have reached the end of the questions!", Toast.LENGTH_LONG).show();
        }
        else if (page == true){
            Toast.makeText(UserQuestionsAskedPage.this,
                    "You have reached the end of the questions!", Toast.LENGTH_LONG).show();
        }
        else{
            if (extraQuestions.size() >= (countPages + 1) * 5){
                textView1.setText(String.valueOf(extraQuestions.get(countPages * 5)));
                textView2.setText(String.valueOf(extraQuestions.get((countPages * 5) + 1)));
                textView3.setText(String.valueOf(extraQuestions.get((countPages * 5)+ 2)));
                textView4.setText(String.valueOf(extraQuestions.get((countPages * 5) + 3)));
                textView5.setText(String.valueOf(extraQuestions.get((countPages * 5) + 4)));
            }
            else if (extraQuestions.size() < (countPages + 1) * 5){
                textView1.setText(".");
                textView2.setText(".");
                textView3.setText(".");
                textView4.setText(".");
                textView5.setText(".");

                for(int i = 0; i < (extraQuestions.size() - ((countPages) * 5)); i = i + 1){
                    if (String.valueOf(textView1.getText()).equals(".")){
                        textView1.setText(String.valueOf(extraQuestions.get(i + (countPages * 5))));
                    }
                    else if (String.valueOf(textView2.getText()).equals(".")){
                        textView2.setText(String.valueOf(extraQuestions.get(i + (countPages * 5))));
                    }
                    else if (String.valueOf(textView3.getText()).equals(".")){
                        textView3.setText(String.valueOf(extraQuestions.get(i + (countPages * 5))));
                    }
                    else if (String.valueOf(textView4.getText()).equals(".")){
                        textView4.setText(String.valueOf(extraQuestions.get(i + (countPages * 5))));
                    }
                    else if (String.valueOf(textView5.getText()).equals(".")){
                        textView5.setText(String.valueOf(extraQuestions.get(i + (countPages * 5))));
                    }
                }
                page = true;
            }
            countPages += 1;
        }
    }

    private void askedQuestions(){
        final ArrayList<String> oneQuestionArray = new ArrayList<String>();
        final ArrayList<String> oneTopicarray = new ArrayList<String>();


        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DatabaseReference ref = myRef.child("users").child(currentFirebaseUser.getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                databaseChildSize = (int) dataSnapshot.child("Questions").getChildrenCount();

                for (int i =1; i <= databaseChildSize / 2; i++){
                    oneQuestion = dataSnapshot.child("Questions").child(String.valueOf(i)).getValue(String.class);
                    oneTopic = dataSnapshot.child("Questions").child(String.valueOf(i) + " Topics").getValue(String.class);
                    oneQuestionArray.add(oneQuestion);
                    oneTopicarray.add(oneTopic);
                }
                putStuffOnThePage(oneQuestionArray, oneTopicarray);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    private void putStuffOnThePage(ArrayList oneQuestionArray, ArrayList oneTopicArray){
        final TextView textView1 = (TextView)findViewById(R.id.asked1);
        final TextView textView2 = (TextView)findViewById(R.id.asked2);
        final TextView textView3 = (TextView)findViewById(R.id.asked3);
        final TextView textView4 = (TextView)findViewById(R.id.asked4);
        final TextView textView5 = (TextView)findViewById(R.id.asked5);

        if (oneQuestionArray.size() <= 5){
            for(int i = 0; i < oneQuestionArray.size(); i = i + 1){
                if (String.valueOf(textView1.getText()).equals(".")){
                    textView1.setText(String.valueOf(oneQuestionArray.get(i)));
                }
                else if (String.valueOf(textView2.getText()).equals(".")){
                    textView2.setText(String.valueOf(oneQuestionArray.get(i)));
                }
                else if (String.valueOf(textView3.getText()).equals(".")){
                    textView3.setText(String.valueOf(oneQuestionArray.get(i)));
                }
                else if (String.valueOf(textView4.getText()).equals(".")){
                    textView4.setText(String.valueOf(oneQuestionArray.get(i)));
                }
                else if (String.valueOf(textView5.getText()).equals(".")){
                    textView5.setText(String.valueOf(oneQuestionArray.get(i)));
                }
            }
        }
        else{
            textView1.setText(String.valueOf(oneQuestionArray.get(0)));
            textView2.setText(String.valueOf(oneQuestionArray.get(1)));
            textView3.setText(String.valueOf(oneQuestionArray.get(2)));
            textView4.setText(String.valueOf(oneQuestionArray.get(3)));
            textView5.setText(String.valueOf(oneQuestionArray.get(4)));
        }
        countPages += 1;
        extraQuestions = oneQuestionArray;

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserQuestionsAskedPage.this, ViewPost.class);
                i.putExtra("key", textView1.getText().toString());
                i.putExtra("activity", 3);
                startActivity(i);
            }
        });

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserQuestionsAskedPage.this, ViewPost.class);
                i.putExtra("key", textView2.getText().toString());
                i.putExtra("activity", 3);
                startActivity(i);
            }
        });

        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserQuestionsAskedPage.this, ViewPost.class);
                i.putExtra("key", textView3.getText().toString());
                i.putExtra("activity", 3);
                startActivity(i);
            }
        });

        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserQuestionsAskedPage.this, ViewPost.class);
                i.putExtra("key", textView4.getText().toString());
                i.putExtra("activity", 3);
                startActivity(i);
            }
        });

        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserQuestionsAskedPage.this, ViewPost.class);
                i.putExtra("key", textView5.getText().toString());
                i.putExtra("activity", 3);
                startActivity(i);
            }
        });

    }
}