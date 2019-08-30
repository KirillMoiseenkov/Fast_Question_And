package org.weibeld.example.tabs.ServicesOrParsers;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.weibeld.example.tabs.entity.Question;

import java.io.IOException;

public class QuestionResponsePareser {
    public Question parseQuestionResponseToQuestion(String response) throws JSONException {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(response, Question.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
