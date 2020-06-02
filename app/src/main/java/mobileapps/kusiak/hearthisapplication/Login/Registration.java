package mobileapps.kusiak.hearthisapplication.Login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import mobileapps.kusiak.hearthisapplication.R;

public class Registration extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailTV, passwordTV;
    private Button lognBtn;
    private ProgressBar progressBar;
    private boolean JazzCollege;
    private boolean Saxophones;
    private boolean Trumpets;
    private boolean Trombones;
    private boolean RhythmSection;
    private boolean ClassicJazz;
    private boolean JazzFunk;
    private boolean JazzMusicians;
    private boolean HowToJazz;
    ArrayList<String> topics = new ArrayList<String>();



    private Boolean jc;
//    FirebaseFirestore db = FirebaseFirestore.getInstance();




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        mAuth = FirebaseAuth.getInstance();

        emailTV = findViewById(R.id.uName);
        passwordTV = findViewById(R.id.pWord);
        lognBtn = (Button) findViewById(R.id.registerButton);


        lognBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUserAccount();
            }
        });

    }

    private void registerUserAccount() {
       JazzCollege = ((CheckBox) findViewById(R.id.checkJazzColleges)).isChecked();
       Saxophones = ((CheckBox) findViewById(R.id.checkSaxophones)).isChecked();
       Trumpets = ((CheckBox) findViewById(R.id.checkTrumpets)).isChecked();
       Trombones = ((CheckBox) findViewById(R.id.checkTrombones)).isChecked();
       RhythmSection = ((CheckBox) findViewById(R.id.checkRhythmSection)).isChecked();
       ClassicJazz = ((CheckBox) findViewById(R.id.checkClassicJazz)).isChecked();
       JazzFunk = ((CheckBox) findViewById(R.id.checkJazzFunk)).isChecked();
       JazzMusicians = ((CheckBox) findViewById(R.id.checkJazzMusicians)).isChecked();
       HowToJazz = ((CheckBox) findViewById(R.id.checkHowTo)).isChecked();



        final String email, password;
        email = emailTV.getText().toString();
        password = passwordTV.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("lol", "createUserWithEmail:success");
                            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference();

                            myRef.child("users").child(currentFirebaseUser.getUid()).setValue(currentFirebaseUser);
                            myRef.child("users").child(currentFirebaseUser.getUid()).child("JazzCollege").setValue(JazzCollege);
                            myRef.child("users").child(currentFirebaseUser.getUid()).child("Saxophones").setValue(Saxophones);
                            myRef.child("users").child(currentFirebaseUser.getUid()).child("Trumpets").setValue(Trumpets);
                            myRef.child("users").child(currentFirebaseUser.getUid()).child("Trombones").setValue(Trombones);
                            myRef.child("users").child(currentFirebaseUser.getUid()).child("RhythmSection").setValue(RhythmSection);
                            myRef.child("users").child(currentFirebaseUser.getUid()).child("ClassicJazz").setValue(ClassicJazz);
                            myRef.child("users").child(currentFirebaseUser.getUid()).child("JazzFunk").setValue(JazzFunk);
                            myRef.child("users").child(currentFirebaseUser.getUid()).child("JazzMusicians").setValue(JazzMusicians);
                            myRef.child("users").child(currentFirebaseUser.getUid()).child("HowToJazz").setValue(HowToJazz);

                            myRef.child("users").child(currentFirebaseUser.getUid()).child("AskedQuestionConstant").setValue("0");


                            startActivity(new Intent(Registration.this, PersonalInformation.class));

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("lol", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Registration.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



}
