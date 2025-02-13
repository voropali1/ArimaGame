package com.example.arimaa2;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.scene.control.Label;

/**
 * Represents a timer for the game.
 */
public class GameTimer {
    private Timer timer;
    private int secondsElapsed = 0;

    /**
     * Starts the timer and updates the provided label with the elapsed time.
     *
     * @param timelabel the label to update with the elapsed time
     */
    public void timerstart(Label timelabel) {
        if (timer != null) {
            timer.cancel();
        }
        secondsElapsed = 0;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (timelabel != null) {
                    updateLabel(timelabel);
                    secondsElapsed++;
                }
            }
        }, 0, 1000);
    }

    /**
     * Stops the timer.
     */
    public void stop() {
        if (timer != null) {
            timer.cancel();
        }
    }

    /**
     * Updates the provided label with the formatted time based on the elapsed seconds.
     *
     * @param timelabel1 the label to update with the formatted time
     */
    private void updateLabel(Label timelabel1) {
        int hours = secondsElapsed / 3600;
        int minutes = (secondsElapsed % 3600) / 60;
        int seconds = secondsElapsed % 60;

        String formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        Platform.runLater(() -> timelabel1.setText(formattedTime));
    }
}