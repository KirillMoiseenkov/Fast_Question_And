package org.weibeld.example.tabs.ServicesOrParsers;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.weibeld.example.tabs.MainActivity;
import org.weibeld.example.tabs.MyCustomListener.Not;
import org.weibeld.example.tabs.entity.Answer;

import java.util.ArrayList;
import java.util.List;

public class RestUtils {

    private final static String TAG = MainActivity.class.getName();
    private List<Answer> answers = new ArrayList<>();
    private Not not;
    private TextViewBuilder textViewBuilder;
    private JSONObject jsonObject;
    private AnswerResponseParser answerResponseParser;

    public RestUtils(Context context) {
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
}