package org.weibeld.example.tabs.ServicesOrParsers;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.weibeld.example.tabs.entity.Answer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class AnswerResponseParser {

    private List<Answer> answerList;

    public AnswerResponseParser() {
        answerList = new ArrayList<>();
    }

    private List<Answer> parseAnswerResponseToJSONArray(String response) throws JSONException {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(response, new TypeReference<List<Answer>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<Answer> parseJSONArrayToListAnswer(String response) throws JSONException {
        return parseAnswerResponseToJSONArray(response);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Answer> getAnswerListFromJSON(String response) throws JSONException {
        return checkOnDuplicate(parseJSONArrayToListAnswer(response));
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<Answer> checkOnDuplicate(List<Answer> answersResponse) {
        if (Objects.nonNull(answersResponse) && Objects.nonNull(answerList) && !answerList.isEmpty()) {
            Set<Answer> localAnswers = new HashSet<>(answerList);
            Set<Answer> answerSetResponse = new HashSet<>(answersResponse);

            answerList = new ArrayList<>(answerSetResponse);
            answerSetResponse.removeAll(localAnswers);
            return new ArrayList<>(answerSetResponse);
        }
        answerList = answersResponse;
        return answersResponse;
    }
}
