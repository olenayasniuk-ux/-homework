package com.shpp.p2p.cs.oyasniuk.assignment3;

import com.shpp.cs.a.console.TextProgram;

/**
 * The program provides health-related recommendations to the user based on their activity.
 * It tracks physical activity for a week and evaluates whether the user met the goals
 * for cardiovascular health and low blood pressure.
 */
public class Assignment3Part1 extends TextProgram {
    /**
     * The total number of days in a calendar week.
     */
    public static final int DAYS_IN_WEEK = 7;
    /**
     * Format string used to prompt the user for daily training minutes.
     */
    public static final String ASK_USER = "How many minutes did you do on day %d ?";
    /**
     * Required number of active days per week to maintain good cardiovascular health.
     */
    public static final int NUMBER_OF_DAYS_FOR_CARDIOVASCULAR_HEALTH = 5;
    /**
     * Required number of active days per week to maintain low blood pressure.
     */
    public static final int NUMBER_OF_DAYS_FOR_LOW_BLOOD_PRESSURE = 3;
    /**
     * Minimum duration of a single workout in minutes to count towards cardiovascular health.
     */
    public static final int MIN_DAY_MINUTES_FOR_CARDIOVASCULAR_HEALTH = 30;
    /**
     * Minimum duration of a single workout in minutes to count towards low blood pressure.
     */
    public static final int MIN_DAY_MINUTES_FOR_LOW_BLOOD_PRESSURE = 40;
    /**
     * Message displayed when the cardiovascular health target is successfully met.
     */
    public static final String ANSWER_USER_CARDIOVASCULAR_HEALTH_GOOD_JOB = "Cardiovascular health:\n"+
            "  Great job! You've done enough exercise for cardiovascular health.";
    /**
     * Format string message displayed when the cardiovascular health target is not met.
     */
    public static final String ANSWER_USER_CARDIOVASCULAR_HEALTH_INSUFFICIENT_JOB = "Cardiovascular health:\n" +
            "  You needed to train hard for at least %d more day(s) a week!";
    /**
     * Message displayed when the low blood pressure goal is successfully met.
     */
    public static final String ANSWER_USER_LOW_BLOOD_PRESSURE_GOOD_JOB = "Blood pressure:\n" +
            "  Great job! You've done enough exercise to keep a low blood pressure.";
    /**
     * Format string message displayed when the low blood pressure goal is not met.
     */
    public static final String ANSWER_USER_LOW_BLOOD_PRESSURE_INSUFFICIENT_JOB = "Blood pressure:\n" +
            "  You needed to train hard for at least %d more day(s) a week!";

    /**
     * Execution entry point for the program. It coordinates user data input,
     * performs statistical analysis of health metrics, and displays the results.
     */
    public void run() {
        int[] trainingData = new int[DAYS_IN_WEEK];

        trainingData = inputData(trainingData);
        outputResultForCardiovascularHealth(
                analyzeDataForCardiovascularHealth(
                        trainingData));
        outputResultForLowBloodPressure(
                analyzeDataForLowBloodPressure(
                        trainingData));
    }

    /**
     * Prints the final feedback regarding low blood pressure requirements.
     * Evaluates if there are remaining days left to achieve the goal.
     *
     * @param remainingDaysForLowBloodPressure The number of days short of the target.
     */
    private void outputResultForLowBloodPressure(int remainingDaysForLowBloodPressure) {
        if (remainingDaysForLowBloodPressure <= 0) {
            println(ANSWER_USER_LOW_BLOOD_PRESSURE_GOOD_JOB);
        } else {
            System.out.printf(ANSWER_USER_LOW_BLOOD_PRESSURE_INSUFFICIENT_JOB + "\n",
                    remainingDaysForLowBloodPressure);
        }
    }

    /**
     * Prints the final feedback regarding cardiovascular health requirements.
     * Evaluates if there are remaining days left to achieve the goal.
     *
     * @param remainingDaysForCardiovascularHealth The number of days short of the target.
     */
    private void outputResultForCardiovascularHealth(int remainingDaysForCardiovascularHealth) {
        if (remainingDaysForCardiovascularHealth <= 0) {
            println(ANSWER_USER_CARDIOVASCULAR_HEALTH_GOOD_JOB);
        } else {
            System.out.printf(ANSWER_USER_CARDIOVASCULAR_HEALTH_INSUFFICIENT_JOB + "\n",
                    remainingDaysForCardiovascularHealth);
        }
    }

    /**
     * Analyzes the weekly training data against low blood pressure milestones.
     * Counts how many target days are still needed to meet the objective.
     *
     * @param trainingResults An array containing the duration of workouts for each day.
     * @return The number of missing days required to complete the health goal (0 or less means target met).
     */
    private int analyzeDataForLowBloodPressure(int[] trainingResults) {
        int remainingDaysForLowBloodPressure = NUMBER_OF_DAYS_FOR_LOW_BLOOD_PRESSURE;
        for (int i = 0; i < trainingResults.length; i++) {
            if (trainingResults[i] >= MIN_DAY_MINUTES_FOR_LOW_BLOOD_PRESSURE) {
                remainingDaysForLowBloodPressure--;
            }
        }
        return remainingDaysForLowBloodPressure;
    }

    /**
     * Analyzes the weekly training data against cardiovascular health milestones.
     * Counts how many target days are still needed to meet the objective.
     *
     * @param trainingResults An array containing the duration of workouts for each day.
     * @return The number of missing days required to complete the health goal (0 or less means target met).
     */
    private int analyzeDataForCardiovascularHealth(int[] trainingResults) {
        int remainingDaysForCardiovascularHealth = NUMBER_OF_DAYS_FOR_CARDIOVASCULAR_HEALTH;
        for (int i = 0; i < trainingResults.length; i++) {
            if (trainingResults[i] >= MIN_DAY_MINUTES_FOR_CARDIOVASCULAR_HEALTH) {
                remainingDaysForCardiovascularHealth--;
            }
        }
        return remainingDaysForCardiovascularHealth;
    }

    /**
     * Prompts the user sequentially to enter their exercise minutes for each day of the week
     * and saves the values into the provided array.
     *
     * @param trainingResults An empty or pre-allocated array to store daily workout minutes.
     * @return The populated array containing the user's weekly training logs.
     */
    private int[] inputData(int[] trainingResults) {
        for (int i = 0; i < trainingResults.length; i++) {
            trainingResults[i] = readInt(String.format(ASK_USER, i + 1));
        }
        return trainingResults;
    }
}
