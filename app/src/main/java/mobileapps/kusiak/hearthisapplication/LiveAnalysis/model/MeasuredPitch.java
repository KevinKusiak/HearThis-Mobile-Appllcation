package mobileapps.kusiak.hearthisapplication.LiveAnalysis.model;

// Representation of a pitch and quality signals, such as how far off and volume.
// This contains the raw frequency as well as the
// semi-tone it translates to (though we all map everything to one octave as our algorithm is
// not very robust in distinguishing octaves).
public final class MeasuredPitch {
    public MeasuredPitch(double f, int n, double c, double d) {
        frequency = f;
        note = n;
        cent = c;
        decibel = d;
    }

    // Convenience factory to create pitch data. linearVolume range is 0..1
    public static MeasuredPitch createPitchData(double frequency,
                                                double linearVolume) {
        final double kPitchA = 440.0; // Hz.
        final double base = kPitchA / 8; // The A just below our C string (55Hz)
        final double d = Math.exp(Math.log(2) / 1200);
        final double cent_above_base = Math.log(frequency / base) / Math.log(d);
        final int scale_above_C = (int)Math.round(cent_above_base / 100.0) - 3;
        if (scale_above_C < 0) {
            return null;
        }

        // Press into regular scale
        double scale = cent_above_base / 100.0;
        final int rounded = (int) Math.round(scale);
        final double cent = 100 * (scale - rounded);
        final double vu_db = 20 * (Math.log(linearVolume) / Math.log(10));
        return new MeasuredPitch(frequency, rounded, cent, vu_db);
    }

    // Raw frequency.
    public final double frequency;

    // (note % 12) returns a range from 0 (A) to 11 (Ab/G#).
    // The absolute range starts with 0 (low A, 55Hz), 12 = 110Hz ...
    public final int note;

    public final double cent;     // How far off we are in cent.
    public final double decibel;  // input level in decibel.
}
