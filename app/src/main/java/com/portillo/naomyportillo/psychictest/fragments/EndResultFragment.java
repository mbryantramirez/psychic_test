package com.portillo.naomyportillo.psychictest.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.portillo.naomyportillo.psychictest.R;
import com.portillo.naomyportillo.psychictest.database.GuessDataBaseHelper;
import com.portillo.naomyportillo.psychictest.model.GuessModel;

import java.text.DecimalFormat;
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
    private Button endButton;
    private Button resetButton;
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

        guessDataBaseHelper = GuessDataBaseHelper.getInstance(getContext());

        getRate();

        if (getArguments() != null) {
            guessImage = Integer.parseInt(getArguments().getString(GUESS));
            userguessImage = Integer.parseInt(getArguments().getString(USERGUESS));
            userResult = getArguments().getString(RESULT);
        }
    }

    private void getRate() {

        double successfulAttempts = 0;
        double failedAttempts = 0;

        List<GuessModel> databaseGuesses = guessDataBaseHelper.getGuessList();

        Log.d(".EndResultFrag _ numm", "guessList: " + String.valueOf(databaseGuesses.size()));

        int guesses = databaseGuesses.size();

        for (int i = 0; i < databaseGuesses.size(); i++) {
            if (databaseGuesses.get(i).getSuccess() == 1) {
                successfulAttempts++;
            }
            if (databaseGuesses.get(i).getFails() == 1) {
                failedAttempts++;
            }
        }
        rate = (((successfulAttempts) / guesses) * 100);
        DecimalFormat df = new DecimalFormat("#.##");
        rate = Double.valueOf(df.format(rate));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_end_result, container, false);

        findViews();
        setViews();

        return rootView;
    }

    private void setViews() {
        androidguessView.setImageResource(guessImage);
        userGuessView.setImageResource(userguessImage);
        resultTextview.setText(userResult);

        String userRate = getString(R.string.your_successful_guess_rate_textview) + rate + "%";
        ratiotextView.setText(userRate);

        onClick();

    }

    private void onClick() {
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainFragment mainFragment = MainFragment.newInstance();
                FragmentManager fragManager = getActivity().getSupportFragmentManager();


                FragmentTransaction fragmentTransaction = fragManager.beginTransaction();

                fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);

                fragmentTransaction.replace(R.id.fragment_container, mainFragment);
                fragmentTransaction.addToBackStack("choices");
                fragmentTransaction.commit();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guessDataBaseHelper.clearGuessList();
                Log.d(".EndResultFrag _ numm", "guessList: " + String.valueOf( guessDataBaseHelper.getGuessList().size()));
                Toast.makeText(getContext(), "You've resetted the test", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void findViews() {
        androidguessView = rootView.findViewById(R.id.app_guess_imageview);
        userGuessView = rootView.findViewById(R.id.user_guess_imageview);
        resultTextview = rootView.findViewById(R.id.guess_result_textView);
        ratiotextView = rootView.findViewById(R.id.guess_rate_textview);

        endButton = rootView.findViewById(R.id.endfragment_play_againbutton);
        resetButton = rootView.findViewById(R.id.endfragment_resetbutton);

    }

}
