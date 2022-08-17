package com.example.transit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TopupScreen extends AppCompatActivity {

    private Button submit;
    private EditText credittxt;
    String creditvalue;
    int finalValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup_screen);

        submit=(Button)findViewById(R.id.button3);

        // finalValue=Integer.parseInt(credittxt.getText().toString());

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO

                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                credittxt=(EditText)findViewById(R.id.editTextPhonee);

                creditvalue= credittxt.getText().toString();
                finalValue=Integer.parseInt(creditvalue);
                myEdit.putInt("Credit",finalValue);
                myEdit.commit();

                Toast.makeText(TopupScreen.this, "You TopUp In Amount:"+finalValue, Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(getApplicationContext(), Wallet.class);
                startActivity(intent);


            }});




    }
}