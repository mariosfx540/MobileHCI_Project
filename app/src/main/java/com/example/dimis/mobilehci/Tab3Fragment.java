package com.example.dimis.mobilehci;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by perakisMAC on 07/03/2018.
 */

public class Tab3Fragment extends Fragment {
    private static final String TAG = "Tab3Fragment";
    private TextView textView;

    private LinearLayout linearLayout;
    private Button btnAPI;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab3_fragment,container,false);


        /* LinearLayout clickable */
        linearLayout = (LinearLayout) view.findViewById(R.id.linear2);

        // Take me to translator
        btnAPI = (Button) view.findViewById(R.id.btnSpeak4);

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
