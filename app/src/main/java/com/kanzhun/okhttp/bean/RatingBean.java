package com.kanzhun.okhttp.bean;

/**
 * Author: ZhouYou
 * Date: 2017/6/6.
 */
public class RatingBean {

    /**
     * max : 10
     * average : 9.6
     * numRaters : 3280
     * min : 0
     */
    private int max;
    private String average;
    private int numRaters;
    private int min;

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public int getNumRaters() {
        return numRaters;
    }

    public void setNumRaters(int numRaters) {
        this.numRaters = numRaters;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    @Override
    public String toString() {
        return "RatingBean{" +
                "max=" + max +
                ", average='" + average + '\'' +
                ", numRaters=" + numRaters +
                ", min=" + min +
                '}';
    }
}
