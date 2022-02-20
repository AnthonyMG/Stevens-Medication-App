package com.example.stevens_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    boolean btn1Pressed, btn2Pressed = false;
    String btn1Time, btn2Time = "";

    // Declare elements
    Button btn1, btn2, btn_reset;
    TextView txt_time1, txt_time2;

    DateFormat df = new SimpleDateFormat("h:mm a\nMMM dd");

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        txt_time1 = findViewById(R.id.txt_time1);
        txt_time2 = findViewById(R.id.txt_time2);
        btn_reset = findViewById(R.id.btn_reset);

        restoreSharedPreferences();

        // Listeners
        // button one (left)
        btn1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (view.isSelected()) { return false; }
                view.setSelected(true);
                btn1Pressed = true;
                btn1Time = df.format(Calendar.getInstance().getTime());
                saveSharedPreferences();
                printTimes();
                return true;
            }
        });

        // button two (right)
        btn2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (view.isSelected()) { return false; }
                view.setSelected(true);
                btn2Pressed = true;
                btn2Time = df.format(Calendar.getInstance().getTime());
                saveSharedPreferences();
                printTimes();
                return true;
            }
        });

        // reset button
        btn_reset.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                reset();
                return true;
            }
        });
    }

    private void saveSharedPreferences() {
        SharedPreferences sharedPref = this.getPreferences(this.MODE_PRIVATE);
        SharedPreferences.Editor editor =  sharedPref.edit();
        editor.putBoolean("btn1Pressed", btn1Pressed);
        editor.putBoolean("btn2Pressed", btn2Pressed);
        editor.putString("btn1Time", btn1Time);
        editor.putString("btn2Time", btn2Time);
        editor.commit();
    }

    private void restoreSharedPreferences(){
        SharedPreferences sharedPref = this.getPreferences(this.MODE_PRIVATE);
        btn1Pressed = sharedPref.getBoolean("btn1Pressed", false);
        btn2Pressed = sharedPref.getBoolean("btn2Pressed", false);
        btn1Time = sharedPref.getString("btn1Time", "Error :(");
        btn2Time =  sharedPref.getString("btn2Time", "Error :(");
        printTimes();
    }

    private void printTimes() {
        if (btn1Pressed) {
            txt_time1.setText(btn1Time);
        }
        if (btn2Pressed) {
            txt_time2.setText(btn2Time);
        }
        btn1.setSelected(btn1Pressed);
        btn2.setSelected(btn2Pressed);
    }

    private void reset() {
        btn1Pressed = false;
        btn2Pressed = false;
        btn1Time = "";
        btn2Time = "";
        saveSharedPreferences();

        btn1.setSelected(false);
        btn2.setSelected(false);
        txt_time1.setText("");
        txt_time2.setText("");
    }
}
