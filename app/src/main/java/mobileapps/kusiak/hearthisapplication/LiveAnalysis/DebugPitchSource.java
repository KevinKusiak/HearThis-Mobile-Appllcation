package mobileapps.kusiak.hearthisapplication.LiveAnalysis;

import android.os.Handler;

import java.util.Random;

import mobileapps.kusiak.hearthisapplication.LiveAnalysis.model.MeasuredPitch;

// A Debug pitch source is a source of frequencies for debugging purposes.
public class DebugPitchSource implements PitchSource {
    public DebugPitchSource() {
        expectedPitch = 220.0;  // some sensible default.
    }

    // Set the frequency this source should generate from now on until
    // changed.
    // This could do some jitter up and down to simulate non-precise pitches,
    public synchronized void setExpectedPitch(double frequency) {
        expectedPitch = frequency;
    }
    private synchronized double getExpectedPitch() { return expectedPitch; }

    @Override
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void startSampling() {
        if (runner != null) return; // already running.
        runner = new PitchGenerator();
        runner.start();
    }

    @Override
    public void stopSampling() {
        if (runner != null) {
            runner.windDown();
            runner = null;
        }
    }

    private class PitchGenerator extends Thread {
        public PitchGenerator() {
            random = new Random();
            running = true;
        }
        public void run() {
            while (isRunning()) {
                try {
                    Thread.sleep(25);  // a bit faster than usual.
                }
                catch (InterruptedException e) {
                    continue;
                }
                final double nextToneDiff = 0.059 * getExpectedPitch();
                final double jitter = (random.nextDouble() - 0.5)/3 * nextToneDiff;
                final MeasuredPitch nc = MeasuredPitch.createPitchData(getExpectedPitch() + jitter, 1);
                if (handler != null) {
                    handler.sendMessage(handler.obtainMessage(0, nc));
                }
            }
        }

        private synchronized boolean isRunning() { return running; }
        public synchronized void windDown() {
            running = false;
            interrupt();
        }

        private boolean running;
        private Random random;
    }

    private double expectedPitch;
    private Handler handler;
    private PitchGenerator runner;
}
