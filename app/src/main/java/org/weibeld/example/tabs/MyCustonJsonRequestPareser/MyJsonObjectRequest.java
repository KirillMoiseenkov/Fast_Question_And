package org.weibeld.example.tabs.MyCustonJsonRequestPareser;

import android.support.annotation.Nullable;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.weibeld.example.tabs.entity.Answer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyJsonObjectRequest extends JsonObjectRequest {
    public MyJsonObjectRequest(int method, String url, @Nullable JSONObject jsonRequest, Response.Listener<JSONObject> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    public MyJsonObjectRequest(String url, @Nullable JSONObject jsonRequest, Response.Listener<JSONObject> listener, @Nullable Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
    }

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {

            List<Answer> answers = new ArrayList<>();

            String jsonString =
                    new String(
                            response.data,
                            HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));

            try {

                answers = new ArrayList<>(Arrays.asList(objectMapper.readValue(jsonString, Answer[].class)));

                int i = 5;
            } catch (IOException e) {
                e.printStackTrace();
            }


            return Response.success(
                    new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));


        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

}
