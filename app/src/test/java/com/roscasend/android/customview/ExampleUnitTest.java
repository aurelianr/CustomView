package com.roscasend.android.customview;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {

        final JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("componentId", "111");
        } catch (JSONException e) {
            fail(e.getMessage());
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("############ ");
        stringBuilder.append(jsonBody.toString());
        String out = "############ " + 111;

        assertEquals("############ " + 111, jsonBody.toString());
    }


}