package com.portillo.naomyportillo.psychictest.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.portillo.naomyportillo.psychictest.R;
import com.portillo.naomyportillo.psychictest.database.GuessDataBaseHelper;
import com.portillo.naomyportillo.psychictest.model.GuessModel;

import java.util.List;
import java.util.Objects;

public class EndResultFragment extends Fragment {


    private static final String GUESS = "AndroidGuess";
    private static final String USERGUESS = "UserGuess";
    private static final String RESULT = "result";

    private int guessImage;
    private int userguessImage;
    private String userResult;
    private View rootView;
    private ImageView androidguessView;
    private ImageView userGuessView;
    private TextView resultTextview;
    private TextView ratiotextView;
    private GuessDataBaseHelper guessDataBaseHelper;
    double rate;


    public static EndResultFragment newInstance(int guess, int userGuess, String result) {
        EndResultFragment fragment = new EndResultFragment();
        Bundle args = new Bundle();
        args.putString(GUESS, String.valueOf(guess));
        args.putString(USERGUESS, String.valueOf(userGuess));
        args.putString(RESULT, result);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        guessDataBaseHelper.getInstance(Objects
                .requireNonNull(getActivity()).getApplicationContext());

        getRatio();

        if (getArguments() != null) {
            guessImage = Integer.parseInt(getArguments().getString(GUESS));
            userguessImage = Integer.parseInt(getArguments().getString(USERGUESS));
            userResult = getArguments().getString(RESULT);

        }


    }

    private void getRatio() {

        int successfulAttempts = 0;
        int failedAttempts = 0;

        List<GuessModel> databaseGuesses = guessDataBaseHelper.getGuessList();
        int guesses = databaseGuesses.size();
        for (int i = 0; i < databaseGuesses.size(); i++) {
            if (databaseGuesses.get(i).getSuccess() == 0) {
                successfulAttempts++;
            } else {
                failedAttempts++;
            }
        }
        rate = (((successfulAttempts) / guesses) * 100);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_end_result, container, false);

        androidguessView = rootView.findViewById(R.id.app_guess_imageview);
        userGuessView = rootView.findViewById(R.id.user_guess_imageview);
        resultTextview = rootView.findViewById(R.id.guess_result_textView);
        ratiotextView = rootView.findViewById(R.id.guess_rate_textview);

        androidguessView.setImageResource(guessImage);
        userGuessView.setImageResource(userguessImage);
        resultTextview.setText(userResult);

        String userRate = getString(R.string.your_successful_guess_rate_textview) + rate;
        ratiotextView.setText(userRate);

        return rootView;
    }

}