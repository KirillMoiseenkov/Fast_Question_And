package org.weibeld.example.tabs;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.weibeld.example.R;
import org.weibeld.example.tabs.entity.Answer;

import java.util.ArrayList;

public class UniversalPage extends Fragment {

    public static Integer count = 1;
    private CustomViewPager customViewPager;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page1, container, false);

        final TextView textView = (TextView) rootView.findViewById(R.id.question);


        final EditText editText = (EditText) rootView.findViewById(R.id.enterInput);

        textView.setTextColor(editText.getTextColors());

        getNewQuestion(textView);

        Button button = (Button) rootView.findViewById(R.id.send);

        final LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.layout);



        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                customViewPager.setPagingEnabled(true);
                textView.setText(editText.getText());

                final TextView newAnswer = new TextView(getContext());

                newAnswer.setText(editText.getText());
                newAnswer.setTextSize(20);
                newAnswer.setTextColor(85);
                newAnswer.setGravity(textView.getGravity());
                newAnswer.setPadding(100,50,50,10);
                newAnswer.setTextColor(editText.getTextColors());

                linearLayout.addView(newAnswer);
            }
        });



        return rootView;


    }


    private void getNewQuestion(final TextView textView){
        textView.setText("WOW" + count++);
    }


    public CustomViewPager getCustomViewPager() {
        return customViewPager;
    }

    public void setCustomViewPager(CustomViewPager customViewPager) {
        this.customViewPager = customViewPager;
    }

    ArrayList<Answer> answers = new ArrayList<>();

    public ArrayList<Answer> getAllAnswers(){
        Answer answer = new Answer();
        answer.setText(count.toString());
        answers.add(answer);

        return answers;
    }

}