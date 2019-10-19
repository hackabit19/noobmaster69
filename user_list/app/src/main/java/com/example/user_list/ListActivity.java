package com.example.user_list;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ListActivity extends AppCompatActivity {
    SharedPreferences pref;
    String id,s;
    TextView text;
    Integer flag=0;
    ProgressDialog progressDialog;
    Map<String, Pair<String,String>> myMap = new HashMap<String, Pair<String, String>>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    Map<String,Object> l;
    List<List<String>> mp=new ArrayList<List<String>>() {
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
//        mAdapter = new MyAdapter(new Arr);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("downloading database");
        progressDialog.setCancelable(false);
        progressDialog.show();
        getlist();
    }

    public void getlist()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("prices")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                myMap.put(document.getId(),new Pair<String, String>(document.get("name").toString(),document.get("price").toString()));
                                progressDialog.hide();
                            }
                        } else {
                        }
                        disp();
                    }
                });
    }
    //
    public void disp()
    {
        s=" ";
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference docRef = db.collection("barcode").document(id);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if(snapshot.getData()==null && !snapshot.exists())
                {
                    Log.e("Logged out","logged out");
                    Toast.makeText(ListActivity.this,"Logged out",Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor = pref.edit();
                    editor.clear();
                    editor.apply();
                    finish();
                }
                if (e != null) {
                    Log.e("aaa", "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    l=snapshot.getData();//l stores data of a user, mymap stores price db map
                } else {
                    flag=1;
                    Log.e("aa", "Current data: null");
                }
                s="";
                if(flag==0) {
                    mp.clear();
                    Iterator it = l.entrySet().iterator();
                    while (it.hasNext()) {

                        Map.Entry pair = (Map.Entry) it.next();
                        if (myMap.containsKey(pair.getKey()))
                        {   List<String>mm = new  ArrayList<String>();;
                            mm.add(myMap.get(pair.getKey()).first.toString());
                            mm.add(pair.getValue().toString());
                            mm.add(myMap.get(pair.getKey()).second.toString());
                            mp.add(mm);
                        }
                        else
                            Log.e("no", "no");
                        Log.e("abcd", pair.getKey() + " = " + pair.getValue());
                        it.remove();
                    }
                }
                Log.e("ss",mp.size()+"");
                mAdapter = new MyAdapter(ListActivity.this,mp);
                recyclerView.setAdapter(mAdapter);
                recyclerView.setLayoutManager(layoutManager);
            }
        });

    }

}
