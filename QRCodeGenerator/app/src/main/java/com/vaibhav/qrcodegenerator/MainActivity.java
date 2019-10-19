package com.vaibhav.qrcodegenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import java.util.Collection;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class MainActivity extends AppCompatActivity {

    private ImageView QRCode;
    private EditText Code;
    private Button Generate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QRCode = findViewById(R.id.QRCODE);
        Code = findViewById(R.id.Code);
        Generate = findViewById(R.id.Generate);

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
