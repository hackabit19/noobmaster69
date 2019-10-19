package com.example.user_list;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SharedPreferences pref;
    String session=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
//        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putString("key_name1", "abhi");
//        editor.apply();
//        SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
//        String ses = sharedPref.getString(getString(R.string.app_name), "-1");
//        session=Integer.parseInt(ses);
//        if(session==-1)
//        signin();
//        else
//        {
//            Intent intent=new Intent(this,ListActivity.class);
//            intent.putExtra("id",session.toString());
//            startActivity(intent);
//        }
        //--------
        if(pref.contains("id"))
        session=pref.getString("id", null);
        if(session!=null)
        {
            Intent intent=new Intent(this,ListActivity.class);
            intent.putExtra("id",session);
            startActivity(intent);
        }
        else
        {
            signin();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {

            } else {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("id", result.getContents());
                editor.apply();
//                SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPref.edit();
//                editor.putString(getString(R.string.app_name), result.getContents());
//                editor.commit();
                Intent intent=new Intent(this,ListActivity.class);
                intent.putExtra("id",result.getContents());
                startActivity(intent);
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void signin()
    {
            IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
            integrator.setOrientationLocked(true);
            integrator.setPrompt("Scan");
            integrator.setCameraId(0);
            integrator.setBeepEnabled(true);
            integrator.setBarcodeImageEnabled(false);
            integrator.initiateScan();
    }

    public void login(View view)
    {
        if(pref.contains("id"))
            session=pref.getString("id", null);
        else
            session=null;
        if(session!=null)
        {
            Intent intent=new Intent(this,ListActivity.class);
            intent.putExtra("id",session);
            startActivity(intent);
        }
        else
        {
            signin();
        }
//        Log.e("a",Integer.parseInt("null")+" ");
    }

    public void fx(View view)
    {

        SharedPreferences.Editor editor = pref.edit();
        editor.putString("session", "abhi");
        editor.apply();
    }

    public void fx1(View view)
    {
        String email;
        if(pref.contains("session"))
        {email=pref.getString("session", null);
        Log.e("aaa",email.toString());}
    }

    public void fx2(View view)
    {
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();
    }
}
