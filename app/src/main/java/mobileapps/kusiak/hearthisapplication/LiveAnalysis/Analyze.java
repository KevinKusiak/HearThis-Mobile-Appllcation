//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import mobileapps.kusiak.hearthisapplication.R;
//
//public class Analyze extends AppCompatActivity {
//
//
//    private String[] Achord = new String[] {"Your recording is in the A chord", "b"};
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.analyze_page);
//
//        TextView analyzeTextView = (TextView) findViewById(R.id.textViewAnalyze);
//        Button goBack= (Button) findViewById(R.id.goBackButtonAnalyze);
//
//
//    }
//
//}
//
package mobileapps.kusiak.hearthisapplication.LiveAnalysis;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Random;

import mobileapps.kusiak.hearthisapplication.LiveAnalysis.model.MeasuredPitch;
import mobileapps.kusiak.hearthisapplication.LiveAnalysis.view.CenterOffsetView;
import mobileapps.kusiak.hearthisapplication.R;

public class Analyze extends Activity {
    private static final int kCentThreshold = 10;  // TODO: make configurable
    private static final boolean kShowTechInfo = false;

    private TextView frequencyDisplay;
    private TextView noteDisplay;
    private TextView flatDisplay;
    private TextView sharpDisplay;
    private TextView decibelView;
    private TextView prevNote;
    private TextView nextNote;
    private TextView instruction;
    private CenterOffsetView offsetCentView;

    private PitchSource pitchPoster;
    private ImageView earIcon;

    private enum KeyDisplay {
        DISPLAY_FLAT,
        DISPLAY_SHARP,
    }
    private KeyDisplay keyDisplay = KeyDisplay.DISPLAY_SHARP;
    private static final String noteNames[][] = {
            { "A", "Bb", "B", "C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab" },
            { "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#" },
    };

    private static final String noteNamesDisplay[][] = {
            { "A", "B", "C", "Db", "D", "E", "F", "Gb", "G"},
            { "A", "B", "C", "C#", "D", "E", "F", "F#", "G"},
    };

    private String passedValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            passedValue = extras.getString("key");
        }




        earIcon = (ImageView) findViewById(R.id.earIcon);
        flatDisplay = (TextView) findViewById(R.id.flatText);
        sharpDisplay = (TextView) findViewById(R.id.sharpText);
        prevNote = (TextView) findViewById(R.id.nextLower);
        nextNote = (TextView) findViewById(R.id.nextHigher);
        noteDisplay = (TextView) findViewById(R.id.noteDisplay);
        noteDisplay.setKeepScreenOn(true);
        noteDisplay.setText("");
        instruction = (TextView) findViewById(R.id.tunerInsruction);
        instruction.setText("");

        offsetCentView = (CenterOffsetView) findViewById(R.id.centView);
        offsetCentView.setRange(25);
        offsetCentView.setQuantization(2.5f);
        offsetCentView.setMarkAt(kCentThreshold);

        int techVisibility = kShowTechInfo ? View.VISIBLE : View.INVISIBLE;
        frequencyDisplay = (TextView) findViewById(R.id.frequencyDisplay);
        frequencyDisplay.setVisibility(techVisibility);
        decibelView = (TextView) findViewById(R.id.decibelView);
        decibelView.setVisibility(techVisibility);

        Random r = new Random();
        int i1 = r.nextInt(9 - 0);


        Intent i = new Intent(Analyze.this, PutAnalyzeOntoScreen.class);
        i.putExtra("key", noteNamesDisplay[0][i1]);
        startActivity(i);

        addAccidentalListener();
    }

    private void addAccidentalListener() {
        final RadioGroup accidentalGroup = (RadioGroup) findViewById(R.id.accidentalSelection);
        accidentalGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                flatDisplay.setVisibility(View.INVISIBLE);
                sharpDisplay.setVisibility(View.INVISIBLE);
                switch (checkedId) {
                    case R.id.flatRadio:
                        keyDisplay = KeyDisplay.DISPLAY_FLAT;
                        break;
                    case R.id.sharpRadio:
                        keyDisplay = KeyDisplay.DISPLAY_SHARP;
                        break;
                }
            }
        });
        ((RadioButton) findViewById(R.id.sharpRadio)).setChecked(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        pitchPoster.stopSampling();
        pitchPoster = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        pitchPoster = new MicrophonePitchSource();
        pitchPoster.setHandler(new UIUpdateHandler());
        pitchPoster.startSampling();
    }

    // Whenever MicrophonePitchSource has a new note value available, it will
    // post it to the message queue, received here.
    private final class UIUpdateHandler extends Handler {
        private final static int kMaxWait = 32;

        // Old Android versions don't seem to have the 'setAlpha()' method.
        private void setAlphaOnText(TextView v, float alpha) {
            int alpha_bits = ((int) (0xFF * alpha)) << 24;
            v.setTextColor(v.getCurrentTextColor() & 0xFFFFFF | alpha_bits);
        }

        private void setFadeableComponentAlpha(float alpha) {
            setAlphaOnText(noteDisplay, alpha);
            setAlphaOnText(flatDisplay, alpha);
            setAlphaOnText(sharpDisplay, alpha);
            setAlphaOnText(frequencyDisplay, alpha);
            setAlphaOnText(prevNote, alpha);
            setAlphaOnText(nextNote, alpha);
            setAlphaOnText(instruction, alpha);
            offsetCentView.setFadeAlpha(alpha);
        }

        public void handleMessage(Message msg) {
            final MeasuredPitch data
                    = (MeasuredPitch) msg.obj;

            if (data != null && data.decibel > -30) {
                frequencyDisplay.setText(String.format(data.frequency < 200 ? "%.1fHz" : "%.0fHz",
                        data.frequency));
                final String printNote = noteNames[keyDisplay.ordinal()][data.note % 12];
                noteDisplay.setText(printNote.substring(0, 1));
                final String accidental = printNote.length() > 1 ? printNote.substring(1) : "";
                flatDisplay.setVisibility("b".equals(accidental) ? View.VISIBLE : View.INVISIBLE);
                sharpDisplay.setVisibility("#".equals(accidental) ? View.VISIBLE : View.INVISIBLE);
                nextNote.setText(noteNames[keyDisplay.ordinal()][(data.note + 1) % 12]);
                prevNote.setText(noteNames[keyDisplay.ordinal()][(data.note + 11) % 12]);
                final boolean inTune = Math.abs(data.cent) <= kCentThreshold;
                final int c = inTune ? Color.rgb(50, 255, 50) : Color.rgb(255,50, 50);
                noteDisplay.setTextColor(c);
                flatDisplay.setTextColor(c);
                sharpDisplay.setTextColor(c);
                if (!inTune) {
                    instruction.setText(data.cent < 0 ? "Too low" : "Too high");
                } else {
                    instruction.setText("");
                }
                setFadeableComponentAlpha(1.0f);
                offsetCentView.setValue((int) data.cent);
                fadeCountdown = kMaxWait;
            } else {
                --fadeCountdown;
                if (fadeCountdown < 0) fadeCountdown = 0;
                setFadeableComponentAlpha(1.0f * fadeCountdown / kMaxWait);
            }
            earIcon.setVisibility(data != null && data.decibel > -30
                    ? View.VISIBLE : View.INVISIBLE);
            if (data != null && data.decibel > -60) {
                decibelView.setText(String.format("%.0fdB", data.decibel));
            } else {
                decibelView.setText("");
            }
            lastPitch = data;
        }

        private MeasuredPitch lastPitch;
        private int fadeCountdown;
    }
}
