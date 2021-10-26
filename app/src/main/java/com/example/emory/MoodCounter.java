package com.example.emory;

/*the class is used in Mood Analytics Activity
to count each mood that users input in every month
 */
public class MoodCounter {
    private int count;

    public MoodCounter() {
        this.count = 0;
    }

    public void addValue() {
        this.count += 1;
    }

    public int getCount() {
        return this.count;
    }
}
