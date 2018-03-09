package com.example.dimis.mobilehci;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import android.util.Log;
import android.widget.EditText;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Dimis on 07/03/2018.
 */

public class TranslationAPI extends AppCompatActivity implements TextToSpeech.OnInitListener{

    private TextToSpeech textToSpeech;
    private Button btnSpeak;
    private EditText txtText;
    private LinearLayout linearLayout;

    private ImageView imageView;

    private static final String API_KEY = "AIzaSyDW3LMH2K_d_6YP0jghp72042rgj-XMxPs";
    private static final String TAG = "TranslationAPI";
    private Button buttonToTranslate;
    private static EditText InputText;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView textView;
    private static TextView textView1;
    private static Handler textViewHandler= new Handler();


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.translate_screen);
        textView1 = (TextView)findViewById(R.id.textView1);
        //textView = (TextView)findViewById(R.id.btnOpen);
        InputText = (EditText)findViewById(R.id.editText);
        buttonToTranslate = (Button)findViewById(R.id.button11);


        // LinearLayout clickable
        linearLayout = (LinearLayout) findViewById(R.id.mic);

        // setting the text-to-speech functionality
        textToSpeech = new TextToSpeech(this, this);
        btnSpeak = (Button) findViewById(R.id.btnSpeak);

        // Setting the Image View
//        imageView = (ImageView) findViewById(R.id.micro);
//        imageView.setImageResource(R.drawable.small_mic);


        buttonToTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Translate(InputText.getText().toString());
            }
        });

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askSpeechInput();
            }
        });

        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                speakOut();
            }
        });
    }


    // Shut down textToSpeech
    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }


    private void askSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Hi speak something");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void speakOut(){
        String text = textView1.getText().toString();
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    Log.d("Results",result.get(0));
                    Translate(result.get(0));
                }
                break;
            }

        }
    }
    public static void Translate(final String text){
        new  AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                TranslateOptions options = TranslateOptions.newBuilder().setApiKey(API_KEY).build();
                Translate translate = options.getService();


                final Translation translation = translate.translate(text,Translate.TranslateOption.targetLanguage("ru"));
                textViewHandler.post(new Runnable(){
                    @Override
                    public void run() {

                        Log.d("Result","Text: %s%n"+ translation.getTranslatedText());
                        Log.d("Translation: %s%n", translation.getTranslatedText());
                        textView1.setText(translation.getTranslatedText());
                        InputText.setText(text);

                    }
                });
                return null;
            }

        }.execute();




    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = textToSpeech.setLanguage(new Locale("ru"));

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                btnSpeak.setEnabled(true);
                speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }
}


