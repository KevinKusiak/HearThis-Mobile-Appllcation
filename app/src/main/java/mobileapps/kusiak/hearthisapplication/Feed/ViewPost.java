package mobileapps.kusiak.hearthisapplication.Feed;
import android.content.Intent;
import android.os.Bundle;
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

import mobileapps.kusiak.hearthisapplication.R;
import mobileapps.kusiak.hearthisapplication.Trending.MainTrendingPage;
import mobileapps.kusiak.hearthisapplication.Trending.OtherUserQuestionsAskedPage;
import mobileapps.kusiak.hearthisapplication.UserProfile.UserQuestionsAskedPage;

public class ViewPost extends AppCompatActivity {
    private String passedValue;
    private String anotherPassedValue;

    private String addComment;
    public String firstChildTopics;
    public int sized;
    public int c;
    public int z;
    public boolean Kevin = false;

    private ArrayList<String> extraComments = new ArrayList<String>();

    private int countPages;

    private boolean page = false;

    private int ChangeActivites;

    private String oneValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_feed_post);

        startProcesses();
    }

    private void startProcesses(){
        TextView displayTextView = (TextView)findViewById(R.id.postTextview);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            passedValue = extras.getString("key");
            ChangeActivites = extras.getInt("activity");

            oneValue = extras.getString("cstant");
        }
        displayTextView.setText(passedValue);

        Button openComment = (Button) findViewById(R.id.createCommentButton);
        final EditText actuallyMakeComment = (EditText) findViewById(R.id.commentText);
        final Button submitComment = (Button) findViewById(R.id.createCommentText);
        final Button goBack= (Button) findViewById(R.id.goBackButton);

        Button upButton = (Button) findViewById(R.id.scrollUp);
        Button downButton = (Button) findViewById(R.id.scrollDown);

        addCommentsFirst();

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

        goBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (ChangeActivites == 1){
                    startActivity(new Intent(ViewPost.this, MainFeedPage.class));
                }
                else if(ChangeActivites == 2){
                    startActivity(new Intent(ViewPost.this, MainTrendingPage.class));
                }
                else if(ChangeActivites == 3){
                    startActivity(new Intent(ViewPost.this, UserQuestionsAskedPage.class));
                }
                else{
                    Intent k = new Intent(ViewPost.this, OtherUserQuestionsAskedPage.class);
                    k.putExtra("key", oneValue);
                    startActivity(k);
                }

            }
        });
        openComment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                actuallyMakeComment.setVisibility(View.VISIBLE);
                submitComment.setVisibility(View.VISIBLE);
            }
        });

        submitComment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (actuallyMakeComment.getText().toString() == null || actuallyMakeComment.getText().toString().equals("") || actuallyMakeComment.getText().toString().equals(" ")){
                    Toast.makeText(ViewPost.this,
                            "Enter something valid!", Toast.LENGTH_LONG).show();
                }
                else{
                    classify();
                    actuallyMakeComment.setVisibility(View.INVISIBLE);
                    submitComment.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void managePagesUp(){
        TextView textView1 = (TextView)findViewById(R.id.comment1);
        TextView textView2 = (TextView)findViewById(R.id.comment2);
        TextView textView3 = (TextView)findViewById(R.id.comment3);
        TextView textView4 = (TextView)findViewById(R.id.comment4);
        TextView textView5 = (TextView)findViewById(R.id.comment5);

        if (countPages == 1){
                Toast.makeText(ViewPost.this,
                        "There are no earlier comments!", Toast.LENGTH_LONG).show();

            }
        else{
            countPages -= 1;
            textView1.setText(String.valueOf(extraComments.get((countPages - 1) * 5)));
            textView2.setText(String.valueOf(extraComments.get(((countPages - 1) * 5) + 1)));
            textView3.setText(String.valueOf(extraComments.get(((countPages - 1) * 5)+ 2)));
            textView4.setText(String.valueOf(extraComments.get(((countPages - 1) * 5) + 3)));
            textView5.setText(String.valueOf(extraComments.get(((countPages - 1) * 5) + 4)));
            page = false;
        }
    }
    private void managePagesDown(){
        TextView textView1 = (TextView)findViewById(R.id.comment1);
        TextView textView2 = (TextView)findViewById(R.id.comment2);
        TextView textView3 = (TextView)findViewById(R.id.comment3);
        TextView textView4 = (TextView)findViewById(R.id.comment4);
        TextView textView5 = (TextView)findViewById(R.id.comment5);

        if(extraComments.size() <=5){
            Toast.makeText(ViewPost.this,
                    "You have reached the end of the comments!", Toast.LENGTH_LONG).show();
        }
        else if (page == true){
            Toast.makeText(ViewPost.this,
                    "You have reached the end of the comments!", Toast.LENGTH_LONG).show();
        }
        else{
            if (extraComments.size() >= (countPages + 1) * 5){
                textView1.setText(String.valueOf(extraComments.get(countPages * 5)));
                textView2.setText(String.valueOf(extraComments.get((countPages * 5) + 1)));
                textView3.setText(String.valueOf(extraComments.get((countPages * 5)+ 2)));
                textView4.setText(String.valueOf(extraComments.get((countPages * 5) + 3)));
                textView5.setText(String.valueOf(extraComments.get((countPages * 5) + 4)));
            }
            else if (extraComments.size() < (countPages + 1) * 5){
                textView1.setText(".");
                textView2.setText(".");
                textView3.setText(".");
                textView4.setText(".");
                textView5.setText(".");

                for(int i = 0; i < (extraComments.size() - ((countPages) * 5)); i = i + 1){
                    if (String.valueOf(textView1.getText()).equals(".")){
                        textView1.setText(String.valueOf(extraComments.get(i + (countPages * 5))));
                    }
                    else if (String.valueOf(textView2.getText()).equals(".")){
                        textView2.setText(String.valueOf(extraComments.get(i + (countPages * 5))));
                    }
                    else if (String.valueOf(textView3.getText()).equals(".")){
                        textView3.setText(String.valueOf(extraComments.get(i + (countPages * 5))));
                    }
                    else if (String.valueOf(textView4.getText()).equals(".")){
                        textView4.setText(String.valueOf(extraComments.get(i + (countPages * 5))));
                    }
                    else if (String.valueOf(textView5.getText()).equals(".")){
                        textView5.setText(String.valueOf(extraComments.get(i + (countPages * 5))));
                    }
                }
                page = true;
            }
            countPages += 1;
        }
    }














    private void addCommentsFirst(){
//        countPages = 0;

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DatabaseReference ref = myRef.child("ClassicJazzTopics");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sized = (int) dataSnapshot.getChildrenCount() / 2;
                for(int i = 1; i <= sized; i = i + 1) {
                    if (passedValue.equals(dataSnapshot.child(String.valueOf(i)).getValue(String.class))){
                        addCommentSecond("ClassicJazzTopics");
                        break;
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
                sized = (int) dataSnapshot.getChildrenCount();
                for(int i = 1; i <= sized; i = i + 1) {
                    if (passedValue.equals(dataSnapshot.child(String.valueOf(i)).getValue(String.class))){
                        addCommentSecond("HowToJazzTopics");
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        ref = myRef.child("JazzCollegeTopics");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sized = (int) dataSnapshot.getChildrenCount();
                for(int i = 1; i <= sized; i = i + 1) {
                    if (passedValue.equals(dataSnapshot.child(String.valueOf(i)).getValue(String.class))){
                        addCommentSecond("JazzCollegeTopics");
                        break;
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
                sized = (int) dataSnapshot.getChildrenCount();
                for(int i = 1; i <= sized; i = i + 1) {
                    if (passedValue.equals(dataSnapshot.child(String.valueOf(i)).getValue(String.class))){
                        addCommentSecond("JazzFunkTopics");
                        break;
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
                sized = (int) dataSnapshot.getChildrenCount();
                for(int i = 1; i <= sized; i = i + 1) {
                    if (passedValue.equals(dataSnapshot.child(String.valueOf(i)).getValue(String.class))){
                        addCommentSecond("JazzMusiciansTopics");
                        break;
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
                sized = (int) dataSnapshot.getChildrenCount();
                for(int i = 1; i <= sized; i = i + 1) {
                    if (passedValue.equals(dataSnapshot.child(String.valueOf(i)).getValue(String.class))){
                        addCommentSecond("RhythmSectionTopics");
                        break;
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
                sized = (int) dataSnapshot.getChildrenCount();
                for(int i = 1; i <= sized; i = i + 1) {
                    if (passedValue.equals(dataSnapshot.child(String.valueOf(i)).getValue(String.class))){
                        addCommentSecond("SaxophonesTopics");
                        break;
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
                sized = (int) dataSnapshot.getChildrenCount();
                for(int i = 1; i <= sized; i = i + 1) {
                    if (passedValue.equals(dataSnapshot.child(String.valueOf(i)).getValue(String.class))){
                        addCommentSecond("TrombonesTopics");
                        break;
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
                sized = (int) dataSnapshot.getChildrenCount();
                for(int i = 1; i <= sized; i = i + 1) {
                    if (passedValue.equals(dataSnapshot.child(String.valueOf(i)).getValue(String.class))){
                        addCommentSecond("TrumpetsTopics");
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void addCommentSecond(final String boi){
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();
        DatabaseReference ref = myRef.child(boi);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sized = (int) dataSnapshot.getChildrenCount();
                for(int i = 1; i <= sized; i = i + 1) {
                    if (passedValue.equals(dataSnapshot.child(String.valueOf(i)).getValue(String.class))){
                        c = (int) dataSnapshot.child(String.valueOf(i) + " Comments").getChildrenCount();
                        z = i;
                        Kevin = true;
                        addCommentsFinalCheck(boi, c, z);
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void addCommentsFinalCheck(final String otherBoi, final int c, final int z){
        // C - 1 is the amount of comments already,
        // Z is what number the question corresponds to
        final ArrayList<String> postComments = new ArrayList<String>();

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DatabaseReference ref = myRef.child(otherBoi);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(int i = 1; i <= (c - 1); i = i + 1){
                    postComments.add(dataSnapshot.child(String.valueOf(z) + " Comments").child(String.valueOf(i)).getValue(String.class));
                }
                if (postComments.size() == (c-1)){
                    addCommentsFinal(postComments);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void addCommentsFinal(ArrayList postComments){
        TextView textView1 = (TextView)findViewById(R.id.comment1);
        TextView textView2 = (TextView)findViewById(R.id.comment2);
        TextView textView3 = (TextView)findViewById(R.id.comment3);
        TextView textView4 = (TextView)findViewById(R.id.comment4);
        TextView textView5 = (TextView)findViewById(R.id.comment5);

        if (postComments.size() <= 5){
            for(int i = 0; i < postComments.size(); i = i + 1){
                if (String.valueOf(textView1.getText()).equals(".")){
                    textView1.setText(String.valueOf(postComments.get(i)));
                }
                else if (String.valueOf(textView2.getText()).equals(".")){
                    textView2.setText(String.valueOf(postComments.get(i)));
                }
                else if (String.valueOf(textView3.getText()).equals(".")){
                    textView3.setText(String.valueOf(postComments.get(i)));
                }
                else if (String.valueOf(textView4.getText()).equals(".")){
                    textView4.setText(String.valueOf(postComments.get(i)));
                }
                else if (String.valueOf(textView5.getText()).equals(".")){
                    textView5.setText(String.valueOf(postComments.get(i)));
                }
            }
        }
        else{
            textView1.setText(String.valueOf(postComments.get(0)));
            textView2.setText(String.valueOf(postComments.get(1)));
            textView3.setText(String.valueOf(postComments.get(2)));
            textView4.setText(String.valueOf(postComments.get(3)));
            textView5.setText(String.valueOf(postComments.get(4)));

        }

        countPages += 1;
        extraComments = postComments;
    }

















    private void classify(){
        final EditText actuallyMakeComment = (EditText) findViewById(R.id.commentText);
        addComment = actuallyMakeComment.getText().toString();

        actuallyMakeComment.setText(null);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DatabaseReference ref = myRef.child("ClassicJazzTopics");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sized = (int) dataSnapshot.getChildrenCount() / 2;
                for(int i = 1; i <= sized; i = i + 1) {
                    if (passedValue.equals(dataSnapshot.child(String.valueOf(i)).getValue(String.class))){
                        makeComment("ClassicJazzTopics");
                        break;
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
                sized = (int) dataSnapshot.getChildrenCount();
                for(int i = 1; i <= sized; i = i + 1) {
                    if (passedValue.equals(dataSnapshot.child(String.valueOf(i)).getValue(String.class))){
                        makeComment("HowToJazzTopics");
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        ref = myRef.child("JazzCollegeTopics");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sized = (int) dataSnapshot.getChildrenCount();
                for(int i = 1; i <= sized; i = i + 1) {
                    if (passedValue.equals(dataSnapshot.child(String.valueOf(i)).getValue(String.class))){
                        makeComment("JazzCollegeTopics");
                        break;
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
                sized = (int) dataSnapshot.getChildrenCount();
                for(int i = 1; i <= sized; i = i + 1) {
                    if (passedValue.equals(dataSnapshot.child(String.valueOf(i)).getValue(String.class))){
                        makeComment("JazzFunkTopics");
                        break;
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
                sized = (int) dataSnapshot.getChildrenCount();
                for(int i = 1; i <= sized; i = i + 1) {
                    if (passedValue.equals(dataSnapshot.child(String.valueOf(i)).getValue(String.class))){
                        makeComment("JazzMusiciansTopics");
                        break;
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
                sized = (int) dataSnapshot.getChildrenCount();
                for(int i = 1; i <= sized; i = i + 1) {
                    if (passedValue.equals(dataSnapshot.child(String.valueOf(i)).getValue(String.class))){
                        makeComment("RhythmSectionTopics");
                        break;
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
                sized = (int) dataSnapshot.getChildrenCount();
                for(int i = 1; i <= sized; i = i + 1) {
                    if (passedValue.equals(dataSnapshot.child(String.valueOf(i)).getValue(String.class))){
                        makeComment("SaxophonesTopics");
                        break;
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
                sized = (int) dataSnapshot.getChildrenCount();
                for(int i = 1; i <= sized; i = i + 1) {
                    if (passedValue.equals(dataSnapshot.child(String.valueOf(i)).getValue(String.class))){
                        makeComment("TrombonesTopics");
                        break;
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
                sized = (int) dataSnapshot.getChildrenCount();
                for(int i = 1; i <= sized; i = i + 1) {
                    if (passedValue.equals(dataSnapshot.child(String.valueOf(i)).getValue(String.class))){
                        makeComment("TrumpetsTopics");
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    private void makeComment(final String dunno){

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();
        DatabaseReference ref = myRef.child(dunno);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sized = (int) dataSnapshot.getChildrenCount();
                for(int i = 1; i <= sized; i = i + 1) {
                    if (passedValue.equals(dataSnapshot.child(String.valueOf(i)).getValue(String.class))){
                        c = (int) dataSnapshot.child(String.valueOf(i) + " Comments").getChildrenCount();
                        z = i;
                        Kevin = true;
                        checkity(dunno, z, addComment, c);
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    private void checkity(String dunno, int z, String addComment, int c){
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();
        DatabaseReference ref = myRef.child(dunno);
        myRef.child(dunno).child(String.valueOf(z) + " Comments").child(String.valueOf(c)).setValue(addComment);

        TextView textView1 = (TextView)findViewById(R.id.comment1);
        TextView textView2 = (TextView)findViewById(R.id.comment2);
        TextView textView3 = (TextView)findViewById(R.id.comment3);
        TextView textView4 = (TextView)findViewById(R.id.comment4);
        TextView textView5 = (TextView)findViewById(R.id.comment5);
        countPages = 1;
        extraComments.add(addComment);
        page = false;
        if (extraComments.size() <= 5){
            if(extraComments.size() == 5){
                textView1.setText(String.valueOf(extraComments.get(0)));
                textView2.setText(String.valueOf(extraComments.get(1)));
                textView3.setText(String.valueOf(extraComments.get(2)));
                textView4.setText(String.valueOf(extraComments.get(3)));
                textView5.setText(String.valueOf(extraComments.get(4)));
            }
            if(extraComments.size() == 4){
                textView1.setText(String.valueOf(extraComments.get(0)));
                textView2.setText(String.valueOf(extraComments.get(1)));
                textView3.setText(String.valueOf(extraComments.get(2)));
                textView4.setText(String.valueOf(extraComments.get(3)));
            }
            if(extraComments.size() == 3){
                textView1.setText(String.valueOf(extraComments.get(0)));
                textView2.setText(String.valueOf(extraComments.get(1)));
                textView3.setText(String.valueOf(extraComments.get(2)));
            }
            if(extraComments.size() == 2){
                textView1.setText(String.valueOf(extraComments.get(0)));
                textView2.setText(String.valueOf(extraComments.get(1)));
            }
            if(extraComments.size() == 1){
                textView1.setText(String.valueOf(extraComments.get(0)));
            }
        }
        else{
            textView1.setText(String.valueOf(extraComments.get(0)));
            textView2.setText(String.valueOf(extraComments.get(1)));
            textView3.setText(String.valueOf(extraComments.get(2)));
            textView4.setText(String.valueOf(extraComments.get(3)));
            textView5.setText(String.valueOf(extraComments.get(4)));

        }
    }
}
