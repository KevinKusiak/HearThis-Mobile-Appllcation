package mobileapps.kusiak.hearthisapplication.Feed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import mobileapps.kusiak.hearthisapplication.R;

public class MakePost extends AppCompatActivity {

    public String classTopic, classQuestion;

    public int sized;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_feed_post);

        String[] arraySpinner = new String[] {
                "ClassicJazz", "HowToJazz", "JazzCollege", "JazzFunk", "JazzMusicians", "RhythmSection", "Saxophones","Trombones","Trumpets"
        };
        Spinner s = (Spinner) findViewById(R.id.dropdownTopics);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        EditText e = (EditText) findViewById(R.id.askQuestion);
        classQuestion = e.getText().toString();

        Button submit = (Button) findViewById(R.id.submitPost);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                store();
//                if (classQuestion == null || classQuestion.equals("") || classQuestion.equals(" ")){
//                    Toast.makeText(MakePost.this,
//                            "Enter something valid!", Toast.LENGTH_LONG).show();
//                }
//                else{
//                    store();
//                }

            }
        });

        final Button goBack= (Button) findViewById(R.id.goBackButton);


        goBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(MakePost.this, MainFeedPage.class));

            }
        });



    }

    private void store(){

        Spinner s = (Spinner) findViewById(R.id.dropdownTopics);
        classTopic = s.getSelectedItem().toString();

        EditText e = (EditText) findViewById(R.id.askQuestion);
        classQuestion = e.getText().toString();


        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DatabaseReference ref = myRef.child(classTopic + "Topics");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sized = (int) dataSnapshot.getChildrenCount();
                cont(String.valueOf((sized / 2) + 1));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }

    private void cont(String z){
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child(classTopic + "Topics").child(z).setValue(classQuestion);
        myRef.child(classTopic + "Topics").child(z + " Comments").child("0").setValue("Init");


        DatabaseReference ref = myRef.child("users").child(currentFirebaseUser.getUid());

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String wow = (String) dataSnapshot.child("AskedQuestionConstant").getValue().toString();
                contFinal(Integer.parseInt(wow));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void contFinal(int number){
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        number += 1;
        myRef.child("users").child(currentFirebaseUser.getUid()).child("AskedQuestionConstant").setValue(String.valueOf(number));

        myRef.child("users").child(currentFirebaseUser.getUid()).child("Questions").child(String.valueOf(number)).setValue(classQuestion);
        myRef.child("users").child(currentFirebaseUser.getUid()).child("Questions").child(String.valueOf(number) + " Topics").setValue(classTopic + "Topics");

        startActivity(new Intent(MakePost.this, MainFeedPage.class));
    }


}
