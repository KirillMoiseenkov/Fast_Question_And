package org.weibeld.example.tabs.MyCustomListener;

import android.widget.LinearLayout;
import android.widget.TextView;

public class Not implements Notificater {

    TextView textView;
    LinearLayout linearLayout;

    @Override
    public void addData()
    {
        linearLayout.addView(textView);
    }

    public void setLinearLayout(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}
