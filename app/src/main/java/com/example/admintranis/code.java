package com.example.admintranis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.pdf417.encoder.BarcodeMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class code extends AppCompatActivity {

    EditText etInput;
    Button btGenerate;
    ImageView ivOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        etInput = findViewById(R.id.et_input);
        btGenerate = findViewById(R.id.bt_generate);
        ivOutput = findViewById(R.id.iv_output);

        btGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sText = etInput.getText().toString().trim();
                MultiFormatWriter writer = new MultiFormatWriter();
                try {
                    BitMatrix matrix = writer.encode(sText, BarcodeFormat.QR_CODE,500,500);
                    BarcodeEncoder encoder = new BarcodeEncoder();
                    Bitmap bitmap = encoder.createBitmap(matrix);
                    ivOutput.setImageBitmap(bitmap);
                    InputMethodManager manager = (InputMethodManager) getSystemService(
                            Context.INPUT_METHOD_SERVICE
                    );
                    manager.hideSoftInputFromWindow(etInput.getApplicationWindowToken(),0);

                } catch (WriterException e) {
                    e.printStackTrace();
                }

            }
        });

        ImageView imageView = findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(code.this, Login.class);
                startActivity(intent);
            }
        });
    }
}