package com.example.dimis.mobilehci;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

import java.util.Locale;


/**
 * Created by perakisMAC on 07/03/2018.
 */

public class Tab1Fragment extends Fragment{

    private LinearLayout linearLayout;
    private Button btnAPI;

    private static final String TAG = "Tab1Fragment";
    private TextView textView;

    public Tab1Fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_fragment,container,false);

        /* LinearLayout clickable */
        linearLayout = (LinearLayout) view.findViewById(R.id.linear2);

        // Take me to translator
        btnAPI = (Button) view.findViewById(R.id.btnSpeak2);


        btnAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),TranslationAPI.class);
                startActivity(intent);
            }
        });
        return view;


    }




}
