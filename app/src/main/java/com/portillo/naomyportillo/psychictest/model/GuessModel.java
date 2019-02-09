package com.portillo.naomyportillo.psychictest.model;

public class GuessModel {

   private int success;
   private int fails;


    public GuessModel(int success, int fails) {
        this.success = success;
        this.fails = fails;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFails() {
        return fails;
    }

    public void setFails(int fails) {
        this.fails = fails;
    }
}
