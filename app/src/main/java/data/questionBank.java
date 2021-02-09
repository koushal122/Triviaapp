package data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import controller.Serverquestion;

public class questionBank {

    String url="https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";

    ArrayList<question> que = new ArrayList<>();

    public List<question> getQue(ProcessCompleted callback) {

        JsonArrayRequest jar= new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0;i<response.length();i++)
                {

                    try {

                        question Question=new question();
                        Question.setAnswer((Boolean) response.getJSONArray(i).getBoolean(1));
                        Question.setQuestion(response.getJSONArray(i).get(0).toString());
                        que.add(Question);

                       // Log.d("koushal", "onResponse: "+Question);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                callback.Processfinish(que);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("koushae", "onErrorResponse: somethingwenr ");
            }
        });

        Serverquestion.getInstance().addtoqueue(jar);
        return que;

    }
}
