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

import java.util.Locale;

public class MainActivity extends AppCompatActivity{

  //  private Spinner spinner;
    private TextView tv;
    private Button btnSpeak;
    private TextToSpeech textToSpeech;
  //  private static final String[] paths = {"english", "हिंदी", "বাংলা"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.getDefault());
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.i("TextToSpeech", "Language Not Supported");
                        getDataForTTs();
                    }

                    textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
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
                    });

                } else {
                    Log.i("TextToSpeech", "Initialization Failed");
                }
            }
        });

    }

    private void getDataForTTs() {
        // missing data, install it
        Intent installIntent = new Intent();
        installIntent.setAction(
                TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
        startActivity(installIntent);
    }

    private void initViews() {

       // spinner = (Spinner)findViewById(R.id.spinner);
        tv = findViewById(R.id.textView);
        btnSpeak = findViewById(R.id.button);
/*
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);*/
        tv.setText(R.string.testing_text);
        btnSpeak.setText(R.string.button_text);

    }

  /*  @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                setLocale(getApplicationContext(),"en-rIN");
                Toast.makeText(this, "english selected", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                setLocale(getApplicationContext(),"hi-rIN");
                Toast.makeText(this, "hindi selected", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                setLocale(getApplicationContext(),"bn-rIN");
                Toast.makeText(this, "bangla selected", Toast.LENGTH_SHORT).show();
                break;

            default:
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void setLocale(Context context, String localeKey){
        *//*Locale locale = new Locale(key);
        Configuration config = getBaseContext().getResources().getConfiguration();
        config.locale = locale;
        getBaseContext().getResources()
        .updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());*//*

     *//*   String languageToLoad  = key; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());*//*

        Resources resources = context.getResources();
        Resources resourcesApp = context.getApplicationContext().getResources();
        String localLanguage = resources.getConfiguration().locale.getLanguage();
        Locale locale = new Locale(localeKey);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        resourcesApp.updateConfiguration(config, resources.getDisplayMetrics());
        //for API 25
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        context.getApplicationContext().createConfigurationContext(configuration);
        context.createConfigurationContext(configuration);
    }

   *//* public static void setLocale(Activity activity, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }*//*


*/


    public void speek(View v){
        String text = tv.getText().toString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, TextToSpeech.ACTION_TTS_QUEUE_PROCESSING_COMPLETED);
        }
    }
}