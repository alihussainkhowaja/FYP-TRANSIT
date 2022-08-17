package com.example.transit;

import android.widget.TextView;

public class ModelClass {
    private String  name;
    private String  stop;
    private String  price;
    private String  date;

    ModelClass(String name, String stop, String price, String date){
        this.name=name;
        this.stop=stop;
        this.price=price;
        this.date=date;
    }

    public String getName() {
        return name;
    }

    public String getStop() {
        return stop;
    }

    public String getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }
}
