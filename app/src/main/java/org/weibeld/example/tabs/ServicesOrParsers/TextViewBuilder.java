package org.weibeld.example.tabs.ServicesOrParsers;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

public class TextViewBuilder {

    private Context context;

    public TextViewBuilder(Context context){
        this.context = context;
    }

    public TextView buildStandartTextView(String text){
        TextView textView = new TextView(context);
        textView.setTextSize(20);
        textView.setTextColor(Color.parseColor("#F0F6EE"));
        textView.setPadding(100, 50, 50, 10);
        textView.setGravity(8388659);
        textView.setText(text);
        return textView;
    }


}
