package org.weibeld.example.tabs.ServicesOrParsers;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.weibeld.example.tabs.MainActivity;
import org.weibeld.example.tabs.MyCustomListener.Not;
import org.weibeld.example.tabs.MyCustonJsonRequestPareser.MyJsonObjectRequest;
import org.weibeld.example.tabs.entity.Answer;
import org.weibeld.example.tabs.entity.Question;

import java.util.ArrayList;
import java.util.List;

public class AnswerRestUtils {

    private final static String TAG = MainActivity.class.getName();
    private List<Answer> answers = new ArrayList<>();
    private Not not;
    private TextViewBuilder textViewBuilder;
    private AnswerResponseParser answerResponseParser;

    public AnswerRestUtils(Context context) {
        textViewBuilder = new TextViewBuilder(context);
        answerResponseParser = new AnswerResponseParser();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void send(final Context context, String url) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    Log.i(TAG, response);
                    try {
                        answers = answerResponseParser.getAnswerListFromJSON(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    answers.forEach(a -> {
                        not.setTextView(textViewBuilder.buildStandartTextView(a.getText()));
                        not.addData();
                    });
                }, error -> Log.i(TAG, error.getMessage()));
        queue.add(stringRequest);
    }


    public List<Answer> getAnswers() {
        return answers;
    }

    public Not getNot() {
        return not;
    }

    public void setNot(Not not) {
        this.not = not;
    }


    private boolean repeatSendingEnabledMode = false;

    public boolean isRepeatSendingEnabledMode() {
        return repeatSendingEnabledMode;
    }

    public void setRepeatSendingEnabledMode(boolean repeatSendingEnabledMode) {
        this.repeatSendingEnabledMode = repeatSendingEnabledMode;
    }



    public void repeatSending(Context context, String url, Question question, Integer time) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                try {
                    getAnswersByQuestion(context, url, question);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (repeatSendingEnabledMode)
                    handler.postDelayed(this, time);
            }
        }, 1000);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getAnswersByQuestion(final Context context, String url, Question question) throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", question.getId());
        jsonObject.put("text", question.getText());

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonobj = new JsonArrayRequest(
                Request.Method.POST, url, jsonArray, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.i(TAG, response.toString());
                try {
                    answers = answerResponseParser.getAnswerListFromJSON(response.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                answers.forEach(a -> {
                    not.setTextView(textViewBuilder.buildStandartTextView(a.getText()));
                    not.addData();
                });
            }
        }, null);

        queue.add(jsonobj);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void saveAnswerOnQuestion(final Context context, String url, Answer answer) throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("text", answer.getText());
        String jso ="[]";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            jso = objectMapper.writeValueAsString(answer);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        jsonObject = new JSONObject(jso);


        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonobj = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                response -> {
                      Answer responseAnswer = answerResponseParser.parseAnswer(response.toString());
                      answer.setText(responseAnswer.getText());
                      answer.setId(responseAnswer.getId());
                      answer.setQuestion(responseAnswer.getQuestion());
                      },
                error -> {
                    String a = error.getMessage();
                }
        );

        queue.add(jsonobj);
    }
}