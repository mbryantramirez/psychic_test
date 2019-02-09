package com.portillo.naomyportillo.psychictest;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.portillo.naomyportillo.psychictest.database.GuessDataBaseHelper;
import com.portillo.naomyportillo.psychictest.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GuessDataBaseHelper guessDataBaseHelper = GuessDataBaseHelper.getInstance(this);

        MainFragment mainFragment = MainFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, mainFragment).addToBackStack("main");
        fragmentTransaction.commit();
    }
}
