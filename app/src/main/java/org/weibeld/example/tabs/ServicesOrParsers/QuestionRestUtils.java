package org.weibeld.example.tabs.ServicesOrParsers;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.weibeld.example.tabs.MainActivity;
import org.weibeld.example.tabs.MyCustonJsonRequestPareser.MyJsonObjectRequest;
import org.weibeld.example.tabs.entity.Question;

import java.util.Objects;

public class QuestionRestUtils {

    private final static String TAG = MainActivity.class.getName();
    private Question questionResponse = new Question();
    private QuestionResponsePareser questionResponsePareser = new QuestionResponsePareser();


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void GET(final Context context, String url, TextView textView, Question question) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    Log.i(TAG, response);
                    try {
                        questionResponse = questionResponsePareser.parseQuestionResponseToQuestion(response);
                        if (Objects.nonNull(question)) {
                            question.setText(questionResponse.getText());
                            question.setId(questionResponse.getId());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (Objects.nonNull(textView))
                        textView.setText(questionResponse.getText());
                }, error -> Log.i(TAG, error.getMessage()));
        queue.add(stringRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sendCheck(final Context context, String url, TextView textView) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonobj = new MyJsonObjectRequest(Request.Method.POST, url, new JSONObject("{id:1,text:190}"),
                response -> {
                    String a = response.toString();
                },
                error -> {
                    String a = error.getMessage();
                }
        );

        queue.add(jsonobj);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sendCheckArray(final Context context, String url, TextView textView) throws JSONException {

        JSONArray jsonArray = new JSONArray("[{id:1,text:190}]");

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonobj = new JsonArrayRequest(
                Request.Method.POST, url, jsonArray, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                response.toString();
                int i = 4;

            }
        }, null);

        queue.add(jsonobj);
    }


}
