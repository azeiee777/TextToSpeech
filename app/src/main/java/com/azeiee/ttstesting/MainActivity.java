package com.azeiee.ttstesting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private Button btnSpeak;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        Date currentTime = Calendar.getInstance().getTime();
        Log.d("timetesting", "onCreate: " + currentTime);
        initialiseTTS();
        Date currentTimee = Calendar.getInstance().getTime();
        Log.d("timetesting", "onCreate: " + currentTimee);
    }

    private void getDataForTTs() {
        // missing data, install it
        Intent installIntent = new Intent();
        installIntent.setAction(
                TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
        startActivity(installIntent);
    }

    private void initViews() {
        tv = findViewById(R.id.textView);
        btnSpeak = findViewById(R.id.button);
        tv.setText(R.string.testing_text);
        btnSpeak.setText(R.string.button_text);

    }
    public void speek(View v) {
        Date currentTimeee = Calendar.getInstance().getTime();
        Log.d("timetesting", "onCreate: " + currentTimeee);
        speak();
    }
    
    //UtteranceProgressListener is used as a Listner for TTS, when TTS start or ends we can get notify by it.
    UtteranceProgressListener utteranceProgressListener = new UtteranceProgressListener() {
        @Override
        public void onStart(String utteranceId) {
            Log.i("TextToSpeech", "On Start");
        }

        @Override
        public void onDone(String utteranceId) {
            Log.i("TextToSpeech", "On Done");
        }

        @Override
        public void onError(String utteranceId) {
            Log.i("TextToSpeech", "On Error");
        }
    };

    private void initialiseTTS() {
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.getDefault());
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.i("TextToSpeech", "Language Not Supported");
                        /**
                        *we use this method to get missing data, suppose a language data is not available in device
                        *but it is supported by TTS then this method will install the data for that language
                        **/
                        getDataForTTs();
                    }

                    textToSpeech.setOnUtteranceProgressListener(utteranceProgressListener);

                } else {
                    Log.i("TextToSpeech", "Initialization Failed");
                }
            }
        });
    }

    private void speak() {
        String text = tv.getText().toString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, TextToSpeech.ACTION_TTS_QUEUE_PROCESSING_COMPLETED);
        }
    }
}
