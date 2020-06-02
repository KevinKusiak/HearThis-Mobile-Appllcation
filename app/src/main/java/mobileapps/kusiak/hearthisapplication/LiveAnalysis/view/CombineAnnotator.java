package mobileapps.kusiak.hearthisapplication.LiveAnalysis.view;

import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;

import mobileapps.kusiak.hearthisapplication.LiveAnalysis.model.DisplayNote;

public class CombineAnnotator implements DisplayNote.Annotator {
    private List<DisplayNote.Annotator> annotators;
    public CombineAnnotator() {
        annotators = new ArrayList<DisplayNote.Annotator>();
    }

    public void addAnnotator(DisplayNote.Annotator a) {
        annotators.add(a);
    }

    @Override
    public void draw(DisplayNote note, Canvas canvas, RectF staffBoundingBox, RectF noteBoundingBox) {
        for (DisplayNote.Annotator a : annotators) {
            if (a == null) continue;
            a.draw(note, canvas, staffBoundingBox, noteBoundingBox);
        }
    }
}
