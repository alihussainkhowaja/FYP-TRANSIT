package com.example.transit;

import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Constants {
    private static final  String PACKAGE_NAME ="com.example.transit";
    static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";
    static  final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
    static final String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA";
    static final int SUCCESS_RESULT = 1;
    static final int FAILURE_RESULT =0;
}
//    Spinner spinner = findViewById(R.id.select_stop);
//    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Select_Stop, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinner.setAdapter(adapter);
//                spinner.setOnItemSelectedListener(this);
//
//                }