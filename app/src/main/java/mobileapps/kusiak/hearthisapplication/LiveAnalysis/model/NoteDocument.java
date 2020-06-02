package mobileapps.kusiak.hearthisapplication.LiveAnalysis.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Model for a sequence of notes. Also the key it is in, right now it only
// remembers if it needs flat.
public class NoteDocument implements Serializable {
    public NoteDocument() {
        notes = new ArrayList<DisplayNote>();
    }

    public boolean isEmpty() { return notes.isEmpty(); }
    public int size() { return notes.size(); }
    public void clear() { notes.clear(); }
    public void pop() { notes.remove(notes.size() - 1); }
    public DisplayNote get(int i) { return notes.get(i); }
    public void add(DisplayNote n) { notes.add(n); }

    // Access to list representation of notes.
    public List<DisplayNote> getNotes() { return notes; }

    // Set if semi-tones are shown as flat or sharp.
    public boolean isFlat() { return wantsFlat; }
    public void setFlat(boolean f) { wantsFlat = f; }

    private final List<DisplayNote> notes;  // TODO: separate in measures.
    private boolean wantsFlat;
}
