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
import android.widget.Spinner;

import com.portillo.naomyportillo.psychictest.R;
import com.portillo.naomyportillo.psychictest.database.GuessDataBaseHelper;

public class MainFragment extends Fragment {

    private View rootView;
    private Spinner mainSpinner;
    private Button mainButton;
    private String themeSelection;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mainSpinner = rootView.findViewById(R.id.spinner_main);
        mainButton = rootView.findViewById(R.id.main_choice_submitbutton);

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themeSelection = mainSpinner.getSelectedItem().toString();

                ChoicesFragment choicesFragment = ChoicesFragment.newInstance(themeSelection);
                FragmentManager fragManager = getActivity().getSupportFragmentManager();

                FragmentTransaction fragmentTransaction = fragManager.beginTransaction();

                fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);

                fragmentTransaction.replace(R.id.fragment_container, choicesFragment);
                fragmentTransaction.addToBackStack("choices");
                fragmentTransaction.commit();
            }
        });
        return rootView;
    }

}
