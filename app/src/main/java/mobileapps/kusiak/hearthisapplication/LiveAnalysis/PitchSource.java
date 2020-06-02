package mobileapps.kusiak.hearthisapplication.LiveAnalysis;

import android.os.Handler;

// A PitchSource runs a thread to asynchronously gather pitch information and
// sends it to the message handler queue to process.
public interface PitchSource {
    // Set handler for messages generated from this PitchSource.
    // This sends 'MeasuredPitch' messages.
    void setHandler(Handler handler);

    // Start the thread doing the sampling.
    void startSampling();

    // Stop the thread. This is important when activity goes out of focus,
    // otherwise it might burn resources.
    void stopSampling();
}
