package mobileapps.kusiak.hearthisapplication.LiveAnalysis;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import mobileapps.kusiak.hearthisapplication.R;

public class PutAnalyzeOntoScreen extends AppCompatActivity {


    private String[] chords = new String[] {"Your recording is in the A chord, meaning you played the first note as a C, E, or A. For this I would recommend looking into 2-5-1s for an A chord, which would be, in progression, " +
            "a B minor scale, E major scale, and then of course, the A dominant scale. Remember, a 2-5-1 is 3 measures played for each of the 3 scale progressions.", "Your recording is in the D chord, meaning you played the first not as a F, or a D. For this, I would look into 2-5-1s for an D chord, which would be, in progression, " +
            "a E minor scale, A major scale, and then of course, the D dominant scale. Remember, a 2-5-1 is 3 measures played for each of the 3 scale progressions. However, since you are dealing with a dominant D chord, you may also use a D or F mixolydian scale, which of course is based off of the minor chord for either F" +
            " or D. Remember, the mixolydian scale is a step down from each chord's minor scale." , "Your recording is in the G chord, meaning you played the first as a B or a G. Because of this, I would not recommend playing a 2-5-1, but rather playing a dorian scale for either B or G. Remember, the dorian scale is a step" +
            " up from each chord's major scale.", "Sorry, your playing was not found in the systems as belonging to any major triad chord. Please record again!"};

    private String passedValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analyze_page);

        TextView analyzeTextView = (TextView) findViewById(R.id.textViewAnalyze);
        Button goBack= (Button) findViewById(R.id.goBackButtonAnalyze);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            passedValue = extras.getString("key");
        }

        if (passedValue.equals("A") || passedValue.equals("E") || passedValue.equals("C")){
            analyzeTextView.setText(chords[0]);
        }
        else if (passedValue.equals("F") || passedValue.equals("D")){
            analyzeTextView.setText(chords[1]);
        }
        else if (passedValue.equals("B") || passedValue.equals("G")){
            analyzeTextView.setText(chords[2]);
        }
        else{
            analyzeTextView.setText(chords[3]);
        }

        goBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(PutAnalyzeOntoScreen.this, MainLiveAnalysisPage.class));
            }
        });



    }

}

