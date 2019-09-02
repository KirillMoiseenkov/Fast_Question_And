package org.weibeld.example.tabs.pages;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.weibeld.example.R;
import org.weibeld.example.tabs.MyCustomListener.Not;
import org.weibeld.example.tabs.ServicesOrParsers.AnswerRestUtils;
import org.weibeld.example.tabs.ServicesOrParsers.QuestionRestUtils;
import org.weibeld.example.tabs.entity.Question;

public class AnswerQuestion extends UniversalPage{

    protected String saveQuestionURL = urlBegin + "/saveQuestion";

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page1, container, false);

        textView = (TextView) rootView.findViewById(R.id.question);
        questionRestUtils = new QuestionRestUtils();

        editText = (EditText) rootView.findViewById(R.id.enterInput);

        textView.setTextColor(editText.getTextColors());

        button = (Button) rootView.findViewById(R.id.send);
        linearLayout = (LinearLayout) rootView.findViewById(R.id.layout);

        textView.setText("Ask your own question");

        Question question = new Question();

        linearLayout = (LinearLayout) rootView.findViewById(R.id.layout);

        not = new Not();
        not.setLinearLayout(linearLayout);
        answerRestUtils = new AnswerRestUtils(getContext());
        answerRestUtils.setNot(not);


        button.setOnClickListener(view -> {
            customViewPager.setPagingEnabled(true);
            try {
                question.setText(String.valueOf(editText.getText()));
                questionRestUtils.saveQuestion(getContext(),saveQuestionURL,question,textView,not,getAnswerByQuestion);
                linearLayout.removeView(editText);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            linearLayout.removeView(button);
        });


        return rootView;
    }

    @Override
    public void setUpdateMode(boolean updateMode) {
        questionRestUtils.setUpdateMode(updateMode);
    }
}
