package com.example.user_list;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.TextView;

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
    String id,s;
    TextView text;
    Integer flag=0;
    ProgressDialog progressDialog;
    Map<String, Pair<String,String>> myMap = new HashMap<String, Pair<String, String>>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    Map<String,Object> l;
    //    Map<String,Pair<String,String> > mp;
    List<List<String>> mp=new ArrayList<List<String>>() {
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
//        mAdapter = new MyAdapter(new ArrayList<List<String> >() {{"aa","aa","aa"}});
//        recyclerView.setAdapter(mAdapter);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("downloading database");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        recycle=(RecyclerView)findViewById(R.id.recycle);
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
//                                disp();
//                                Log.e("ghsghsg",myMap.get("1").first);
//                                Log.e("ghsghsg",myMap.get("1").second);
                            }
                        } else {
//                            progressDialog.hide();
                            // Log.d(TAG, "Error getting documents: ", task.getException());
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
//        ListenerRegistration listenerRegistration = db.collection("barcode").document(id).addSnapshotListener(new EventListener<QueryDocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable QueryDocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//                for (QueryDocumentSnapshot doc : documentSnapshot) {
//                    Map<String,Object> a = doc.getData();
//                }
//            }
//        });

        final DocumentReference docRef = db.collection("barcode").document(id);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.e("aaa", "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
//                    l.add(snapshot.getData());
//                    snapshot.getData();
                    l=snapshot.getData();//l stores data of a user, mymap stores price db map
//                    Log.e("ab", "Current data: " + (snapshot.getData()));
                } else {
                    flag=1;
                    Log.e("aa", "Current data: null");
                }
//                Log.e("aa", l.size()+"");
                s="";
                if(flag==0) {
                    mp.clear();
                    Iterator it = l.entrySet().iterator();
                    while (it.hasNext()) {

                        Map.Entry pair = (Map.Entry) it.next();
//                    s=myMap.get(pair.getKey()).first+" " +pair.getValue()+" "+Integer.parseInt(myMap.get(pair.getKey()).second)*Integer.valueOf((Integer) pair.getValue())+"\n";
                        if (myMap.containsKey(pair.getKey()))
                        {   List<String>mm = new  ArrayList<String>();;
                            mm.add(myMap.get(pair.getKey()).first.toString());
                            mm.add(pair.getValue().toString());
                            mm.add(myMap.get(pair.getKey()).second.toString());
                            mp.add(mm);
                        }
//                            mp.put(myMap.get(pair.getKey()).first,new Pair<String, String>(pair.getValue().toString(),myMap.get(pair.getKey()).second));
//                            s = s + myMap.get(pair.getKey()).first + " " + pair.getValue() + " " + Integer.parseInt(myMap.get(pair.getKey()).second) + "\n";
                        else
                            Log.e("no", "no");
                        Log.e("abcd", pair.getKey() + " = " + pair.getValue());
                        it.remove();
                    }
                }
//                Log.e("yes",s);
//                text.setText(s);
                Log.e("ss",mp.size()+"");
//                MyAdapter my=new MyAdapter(fetch.this,mp);
//                RecyclerView.LayoutManager re=new GridLayoutManager(fetch.this,1);
//                recyclerView.setLayoutManager(re);
//                recyclerView.setItemAnimator( new DefaultItemAnimator());
//                recyclerView.setAdapter(my);
//                recyclerView.setAdapter
                mAdapter = new MyAdapter(ListActivity.this,mp);
                recyclerView.setAdapter(mAdapter);
                recyclerView.setLayoutManager(layoutManager);
            }
        });

    }

}
