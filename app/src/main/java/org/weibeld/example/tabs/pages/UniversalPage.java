package org.weibeld.example.tabs.pages;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.util.Log;
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
import org.weibeld.example.tabs.CustomViewPager;
import org.weibeld.example.tabs.MyCustomListener.Not;
import org.weibeld.example.tabs.ServicesOrParsers.AnswerRestUtils;
import org.weibeld.example.tabs.ServicesOrParsers.QuestionRestUtils;
import org.weibeld.example.tabs.entity.Answer;
import org.weibeld.example.tabs.entity.Question;

import java.util.ArrayList;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class UniversalPage extends Fragment {
    protected String urlBegin = "http://192.168.0.192:8080";
   // protected String urlBegin = "http://192.168.1.77:8080";

    protected AnswerRestUtils answerRestUtils;
    public static Integer count = 1;
    protected CustomViewPager customViewPager;
    protected String url = urlBegin + "/getAnswers";
    protected String questionURL = urlBegin + "/getAllQuestion";
    protected String getRandomAnswerURL = urlBegin + "/getRandomQuestion";
    protected String getAnswerByQuestion = urlBegin + "/getAnswerByQuestion";
    protected Not not;
    protected QuestionRestUtils questionRestUtils;
    protected TextView textView;
    protected Button button;
    protected LinearLayout linearLayout;
    protected EditText editText;
    //private ImageView imageView;
    private LinearLayout forPick;
    private Context context;

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

        //imageView = new ImageView(getContext());
        //imageView.setImageResource(R.drawable.anasty);

        Question question = new Question();
        getQuestion(question);

        if(Objects.nonNull(question) && question.getText()!=null && !question.getText().isEmpty()){
            textView.setText(question.getText());
        }

        button.setOnClickListener(view -> {
            customViewPager.setPagingEnabled(true);
            if(Objects.nonNull(question) && question.getText()!=null && !editText.getText().toString().isEmpty()
            && editText.getText().toString().length() > 5){
                textView.setText(question.getText());
                saveAnswer(question);
                getAllAnswers(question);
                linearLayout.removeView(button);
            }else
            galleryAc();
        });



        forPick = (LinearLayout) rootView.findViewById(R.id.for_picture);

        context = getContext();

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
            url = urlBegin + "/getAnswerByQuestion";
            answerRestUtils.setRepeatSendingEnabledMode(updateMode);
            answerRestUtils.repeatSending(getContext(), url, question, 1000);
        return answers;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getQuestion(Question question){
        questionRestUtils.GET(getContext(), getRandomAnswerURL, textView, question);
        if(Objects.nonNull(question) && question.getText()!=null && !question.getText().isEmpty()){
            textView.setText(question.getText());
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void saveAnswer(Question question){
        url = urlBegin + "/saveAnswer";
        Answer answer = new Answer();
        answer.setText(editText.getText().toString());
        answer.setQuestion(question);

        try {
            answerRestUtils.saveAnswerOnQuestion(getContext(),url,answer);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void galleryAc(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent,100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 100)
        {

            ImageView imageView = new ImageView(context);

            Uri uri = data.getData();
            imageView.setImageURI(uri);

            imageView.setScaleType(ImageView.ScaleType.CENTER);

            forPick.addView(imageView,550,550);
        }
    }
}
