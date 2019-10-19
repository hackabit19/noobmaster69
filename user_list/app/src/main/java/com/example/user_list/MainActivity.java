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
    Integer session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        String ses = sharedPref.getString(getString(R.string.app_name), "-1");
        session=Integer.parseInt(ses);
//        session=0;
//        session=0;
        if(session==-1)
        signin();
        else
        {
            Intent intent=new Intent(this,ListActivity.class);
            intent.putExtra("id",session.toString());
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {

            } else {
                SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.app_name), result.getContents());
                editor.commit();
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
        SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        String ses = sharedPref.getString(getString(R.string.app_name), "-1");
//        Log.e("aa",ses);
        session=Integer.parseInt(ses);
            IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
            integrator.setOrientationLocked(true);
            integrator.setPrompt("Scan");
            integrator.setCameraId(0);
            integrator.setBeepEnabled(true);
            integrator.setBarcodeImageEnabled(false);
            integrator.initiateScan();
    }

//    public void fx()
//    {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        // Listen for metadata changes to the document.
//        DocumentReference docRef = db.collection("aa").document("bb");
//        docRef.addSnapshotListener(MetadataChanges.INCLUDE, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot snapshot,
//                                @Nullable FirebaseFirestoreException e) {
//                if(snapshot.getData()==null)
//                Log.e("aa","ee1");
//                // ...
//            }
//        });
//    }
//
    public void login(View view)
    {
        SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        String ses = sharedPref.getString(getString(R.string.app_name), "-1");
        session=Integer.parseInt(ses);
        if(session==-1)
            signin();
        else
        {
            Intent intent=new Intent(this,ListActivity.class);
            intent.putExtra("id",session.toString());
            startActivity(intent);
        }
    }
}
