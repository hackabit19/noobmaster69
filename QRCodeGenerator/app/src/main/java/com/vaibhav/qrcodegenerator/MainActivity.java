package com.vaibhav.qrcodegenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.zxing.WriterException;

import java.util.Collection;

import javax.annotation.Nullable;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class MainActivity extends AppCompatActivity {

    private ImageView QRCode;
    private EditText Code;
    private Button Generate;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QRCode = findViewById(R.id.QRCODE);
        Code = findViewById(R.id.Code);
        Generate = findViewById(R.id.Generate);

        final DocumentReference doc_ref = db.collection("disp-counter").document("1");
        doc_ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(e!=null)
                    return;

                if(documentSnapshot!=null  &&  documentSnapshot.exists()){
                    QRGEncoder qrgEncoder = new QRGEncoder(documentSnapshot.get("id").toString(),null, QRGContents.Type.TEXT,128);
                    try {
                        Bitmap b = qrgEncoder.encodeAsBitmap();
                        QRCode.setImageBitmap(b);
                    } catch (WriterException er) {
                        er.printStackTrace();
                        Toast.makeText(MainActivity.this, "UNSUCCESS\n" + er.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        Generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Code.getText().toString().equals("")){
                    QRGEncoder qrgEncoder = new QRGEncoder(Code.getText().toString(),null, QRGContents.Type.TEXT,128);
                    try {
                        Bitmap b = qrgEncoder.encodeAsBitmap();
                        QRCode.setImageBitmap(b);
                    } catch (WriterException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "UNSUCCESS\n" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Add some text!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
