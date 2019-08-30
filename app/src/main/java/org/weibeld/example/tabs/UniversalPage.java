package org.weibeld.example.tabs;

import android.annotation.TargetApi;
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

import org.json.JSONException;
import org.weibeld.example.R;
import org.weibeld.example.tabs.MyCustomListener.Not;
import org.weibeld.example.tabs.ServicesOrParsers.AnswerRestUtils;
import org.weibeld.example.tabs.ServicesOrParsers.QuestionRestUtils;
import org.weibeld.example.tabs.entity.Answer;
import org.weibeld.example.tabs.entity.Question;

import java.util.ArrayList;

public class UniversalPage extends Fragment {


    private AnswerRestUtils answerRestUtils;
    public static Integer count = 1;
    private CustomViewPager customViewPager;
    private String url = "http://192.168.0.192:8080/getAnswers";
    private String questionURL = "http://192.168.0.192:8080/getAllQuestion";
    private String getRandomAnswerURL = "http://192.168.0.192:8080/getRandomQuestion";
    private Not not;
    private QuestionRestUtils questionRestUtils;
    private TextView textView;
    private Button button;
    private LinearLayout linearLayout;
    private EditText editText;

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page1, container, false);

        textView = (TextView) rootView.findViewById(R.id.question);

        questionRestUtils = new QuestionRestUtils();

        questionRestUtils.GET(getContext(), questionURL, textView, null);

        editText = (EditText) rootView.findViewById(R.id.enterInput);

        textView.setTextColor(editText.getTextColors());

        button = (Button) rootView.findViewById(R.id.send);
        linearLayout = (LinearLayout) rootView.findViewById(R.id.layout);

        not = new Not();
        not.setLinearLayout(linearLayout);
        answerRestUtils = new AnswerRestUtils(getContext());
        answerRestUtils.setNot(not);



        Question question = new Question();
        getQuestion(question);

        button.setOnClickListener(view -> {
            customViewPager.setPagingEnabled(true);
            saveAnswer(question);
            getAllAnswers(question);
            linearLayout.removeView(button);
        });


        return rootView;
    }


    public CustomViewPager getCustomViewPager() {
        return customViewPager;
    }

    public void setCustomViewPager(CustomViewPager customViewPager) {
        this.customViewPager = customViewPager;
    }

    ArrayList<Answer> answers = new ArrayList<>();

    boolean updateMode = true;

    public boolean isUpdateMode() {
        return updateMode;
    }

    public void setUpdateMode(boolean updateMode) {
        this.updateMode = updateMode;
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.M)
    public ArrayList<Answer> getAllAnswers(Question question) {
            url = "http://192.168.0.192:8080/getAnswerByQuestion";
            answerRestUtils.setRepeatSendingEnabledMode(updateMode);
            answerRestUtils.repeatSending(getContext(), url, question, 1000);
        return answers;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getQuestion(Question question){
        questionRestUtils.GET(getContext(), getRandomAnswerURL, textView, question);
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void saveAnswer(Question question){
        url = "http://192.168.0.192:8080/saveAnswer";
        Answer answer = new Answer();
        answer.setText(editText.getText().toString());
        answer.setQuestion(question);

        try {
            answerRestUtils.saveAnswerOnQuestion(getContext(),url,answer);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
