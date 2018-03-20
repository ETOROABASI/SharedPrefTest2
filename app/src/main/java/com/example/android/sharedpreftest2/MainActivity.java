package com.example.android.sharedpreftest2;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    EditText message;
    SeekBar seekBar;
//    RadioButton rbBlue, rbGreen, rbRed, rbOrange;
    Button btn_save, btn_clear;

    int fontColor;
    float fontSize= 25;
    String messageString;

    private static final String PREF_FILE_NAME = "preferenceFileName";
    private static final String FONT_SIZE_KEY = "fontSizeKey";
    private static final String FONT_COLOR_KEY = "fontColorKey";
    private static final String MESSAGE_KEY = "messageKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        message = findViewById(R.id.edit_message);
        seekBar = findViewById(R.id.seekbar_font_size);
  //      rbBlue = findViewById(R.id.rb_blue);
//        rbGreen = findViewById(R.id.rb_green);
//        rbOrange = findViewById(R.id.rb_orange);
//        rbRed = findViewById(R.id.rb_red);
        btn_save = findViewById(R.id.btn_save_settings);
        btn_clear = findViewById(R.id.btn_clear_settings);


        getPreferences();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {  //this code runs when the user is dragging the
                                                        //seekbar...ie the process is ongoing

                message.setTextSize(TypedValue.COMPLEX_UNIT_PX, i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {     //this method runs when the user just starts the process of dragging

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {      //this runs when the user has finished dragging the seekbar

                fontSize = message.getTextSize();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeDataInPreference(message.getText().toString(), fontColor, fontSize);           }
        });


        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDataFromSharedPreference();
            }
        });


    }


    public void changeFontColor(View view){

        switch (view.getId()){
            case R.id.rb_red:
                message.setTextColor(Color.parseColor("#C62828"));
                message.setHintTextColor(Color.parseColor("#C62828"));
                fontColor = Color.parseColor("#C62828");
                break;
            case R.id.rb_blue:
                message.setTextColor(getResources().getColor(R.color.blue));
                message.setHintTextColor(getResources().getColor(R.color.blue));
                fontColor = getResources().getColor(R.color.blue);
                break;
            case R.id.rb_green:
                message.setTextColor(getResources().getColor(R.color.green));
                message.setHintTextColor(getResources().getColor(R.color.green));
                fontColor = getResources().getColor(R.color.green);
                break;
            case R.id.rb_orange:
                message.setTextColor(getResources().getColor(R.color.orange));
                message.setHintTextColor(getResources().getColor(R.color.orange));
                fontColor = getResources().getColor(R.color.orange);
                break;

        }

    }

    private void storeDataInPreference(String message, int fontColor, float fontSize){
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor mEditor = sharedPreferences.edit();
        mEditor.putInt(FONT_COLOR_KEY, fontColor);
        mEditor.putString(MESSAGE_KEY, message);
        mEditor.putFloat(FONT_SIZE_KEY, fontSize);
        mEditor.apply();

    }

    private void getPreferences(){
        SharedPreferences mSharedPreference = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);
        int fontColor = mSharedPreference.getInt(FONT_COLOR_KEY, getResources().getColor(R.color.default_color));
        float fontSize = mSharedPreference.getFloat(FONT_SIZE_KEY, 25);
        String messageString = mSharedPreference.getString(MESSAGE_KEY, "");

        message.setTextColor(fontColor);
        message.setHintTextColor(fontColor);
        message.setText(messageString);
        message.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
        if(fontSize == 25){     // that is the default,
            seekBar.setProgress(0);
        }
        else{
            seekBar.setProgress((int) fontSize);
        }
    }

    private void deleteDataFromSharedPreference(){
        SharedPreferences mSharedPreferences = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor mEditor  = mSharedPreferences.edit();
        mEditor.clear();
        mEditor.apply();
    }
}
