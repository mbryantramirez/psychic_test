package com.portillo.naomyportillo.psychictest;

import java.util.ArrayList;
import java.util.Arrays;

public class ImageArray {

    private ArrayList<Integer> sweets = new ArrayList<>(Arrays.asList(R.drawable.sweets01, R.drawable.sweets02,R.drawable.sweets03, R.drawable.sweets04));

    private ArrayList<Integer> popcorn = new ArrayList<>(Arrays.asList(R.drawable.popcorn01, R.drawable.popcorn02,R.drawable.popcorn03, R.drawable.popcorn04));

    private ArrayList<Integer> funhouse = new ArrayList<>(Arrays.asList(R.drawable.funhouse01, R.drawable.funhouse02,R.drawable.funhouse03, R.drawable.funhouse04));

    public void setSweets(ArrayList<Integer> sweets) {
        this.sweets = sweets;
    }

    public void setPopcorn(ArrayList<Integer> popcorn) {
        this.popcorn = popcorn;
    }

    public void setFunhouse(ArrayList<Integer> funhouse) {
        this.funhouse = funhouse;
    }

    public ArrayList<Integer> getSweets() {
        return sweets;
    }

    public ArrayList<Integer> getPopcorn() {
        return popcorn;
    }

    public ArrayList<Integer> getFunhouse() {
        return funhouse;
    }
}
