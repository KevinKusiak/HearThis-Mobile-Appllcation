package mobileapps.kusiak.hearthisapplication.LiveAnalysis.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;

import java.io.Serializable;

// A DisplayNote represents a note to be displayed.
// It mixes abstract note model with display hints
//   - Note model is the pitch (integer semi-tone, starting with 0=low A at 55Hz) and duration
//   - Color is the color this note should be displayed in
//   - Optionally an Annotator is something that can add additional information to be displayed in
//     the staff.
public final class DisplayNote implements Serializable {
    // Optional annotator for users to implement to add arbitrary
    // annotations to the note.
    public interface Annotator extends Serializable {
        void draw(DisplayNote note, Canvas canvas,
                  RectF staffBoundingBox, RectF noteBoundingBox);
    }

    // Immutable struct to represent a note to display.
    public DisplayNote(int note, int duration) {
        this.note = note;
        this.duration = duration;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || !(other instanceof DisplayNote))
            return false;
        DisplayNote on = (DisplayNote) other;
        return on.note == note && on.duration == duration && on.color == color;
    }

    // Get the frequency equivalent of this note.
    public double getFrequency() {
        return 55.0 * Math.exp(note * Math.log(2) / 12);
    }

    // -- avoiding chatty setters and getters here, but should probably be
    // here for a self-respecting Java program :)

    // 0 for low A at 55Hz, 1 for A#.. 36 for A at 440Hz
    public final int note;

    // 1=full note 4=1/4 note ... Ignored for now, only 1/4 work.
    public final int duration;

    // Color to display. Standard Android color representation.
    // Can change over the lifetime, but starts with black.
    // (should have a setter)
    public int color = Color.BLACK;

    // User provided annotator. If not null, is called when the note is
    // drawn.
    public Annotator annotator;
}
