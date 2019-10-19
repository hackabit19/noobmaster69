package com.example.user_list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putString(getString(R.string.app_name), "aaaa");
//        editor.commit();
    }

    public void fx(View view)
    {
        SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
//        int defaultValue = getResources().getInteger(R.integ);
        String highScore = sharedPref.getString(getString(R.string.app_name), "a");
        Log.e("aa",highScore);
//        Toast.makeText(this,pref.getString("key_name", null),Toast.LENGTH_LONG).show();
    }
}
