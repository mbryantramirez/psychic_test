package com.portillo.naomyportillo.psychictest.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;

import com.portillo.naomyportillo.psychictest.ImageArray;
import com.portillo.naomyportillo.psychictest.R;
import com.portillo.naomyportillo.psychictest.database.GuessDataBaseHelper;
import com.portillo.naomyportillo.psychictest.model.GuessModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class ChoicesFragment extends Fragment {

    private static final String THEME = "fragment_theme";
    private static String fragmentTheme;
    private View rootView;
    private List<Integer> images;
    private ImageView firstImg;
    private ImageView secondImg;
    private ImageView thirdImg;
    private ImageView fourthImg;
    private Random rand;
    private int androidGuess;
    private GuessDataBaseHelper guessDataBaseHelper;


    public static ChoicesFragment newInstance(String theme) {
        ChoicesFragment fragment = new ChoicesFragment();
        Bundle args = new Bundle();
        args.putString(THEME, theme);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         guessDataBaseHelper = GuessDataBaseHelper.getInstance(getContext());


        if (getArguments() != null) {
            fragmentTheme = getArguments().getString(THEME);
        }
        getTheme(fragmentTheme);
        androidGuesses();
    }

    public void androidGuesses() {
        rand = new Random();
        androidGuess = rand.nextInt(images.size());
    }

    public void getTheme(String theme) {
        if (theme.equals("sweets")) {
            images = new ImageArray().getSweets();
        }
        if (theme.equals("popcorn")) {
            images = new ImageArray().getPopcorn();
        }
        if (theme.equals("funhouse")) {
            images = new ImageArray().getFunhouse();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_choices, container, false);
        setViews();
        setImages();
        imgOnClick(firstImg);
        imgOnClick(secondImg);
        imgOnClick(thirdImg);
        imgOnClick(fourthImg);
        return rootView;
    }

    private void imgOnClick(final ImageView imgView) {
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int userGuess = (int) imgView.getTag();

                Log.d(".choicesfrag _ numm", "user: " + String.valueOf(userGuess));
                int android = images.get(androidGuess);
                Log.d(".choicesfrag _ numm", "android: " + String.valueOf(android));

                verifyChoice(userGuess, android);
            }
        });
    }

    private void verifyChoice(int userGuess, int android) {

        int success = 0;
        int fail = 0;

        Log.d(".choicesfrag - numm", "Check entry " + userGuess + " " + android);
        Log.d(".choicesfrag - numm", "Check attempts " + success + " " + fail);


        if (userGuess == android) {
            fail = 0;
            success = 1;

            Log.d(".choicesfrag - numm", "Check entry " + userGuess + " " + android);
            Log.d(".choicesfrag - numm", "Check attempts " + success + " " + fail);

            guessDataBaseHelper.addGuess(new GuessModel(success, fail));
            startEndResultFragment(userGuess, android, getString(R.string.guess_right));
        }

        if (userGuess != android) {
            success = 0;
            fail = 1;

            Log.d(".choicesfrag - numm", "Check entry " + userGuess + " " + android);

            guessDataBaseHelper.addGuess(new GuessModel(success, fail));
            startEndResultFragment(userGuess, android, getString(R.string.guess_wrong));
        }
    }

    private void startEndResultFragment(int userGuess, int android, String result) {
        EndResultFragment endResultFragment = EndResultFragment.newInstance(android, userGuess, result);
        FragmentManager fragManager = getActivity().getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, endResultFragment);
        fragmentTransaction.addToBackStack("result");
        fragmentTransaction.commit();
        androidGuess = 0;
    }

    private void setViews() {
        firstImg = rootView.findViewById(R.id.img_view1);
        secondImg = rootView.findViewById(R.id.img_view2);
        thirdImg = rootView.findViewById(R.id.img_view3);
        fourthImg = rootView.findViewById(R.id.img_view4);
    }

    @SuppressLint("ResourceType")
    public void setImages() {
        if (images != null) {
            firstImg.setTag(images.get(0));
            secondImg.setTag(images.get(1));
            thirdImg.setTag(images.get(2));
            fourthImg.setTag(images.get(3));

            firstImg.setImageResource(images.get(0));
            secondImg.setImageResource(images.get(1));
            thirdImg.setImageResource(images.get(2));
            fourthImg.setImageResource(images.get(3));
        }
    }


}
