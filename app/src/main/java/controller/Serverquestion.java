package controller;

import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class Serverquestion extends Application {

    private RequestQueue requestQueue;
    private static Serverquestion instance;
    private Context ctx;


   public static synchronized Serverquestion getInstance()
   {
//       if(instance== null)
//       {
//          instance=new Serverquestion(con);
//       }
       final Serverquestion instance = Serverquestion.instance;
       return instance;
   }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }

    public RequestQueue getRequestQueue() {

        if(requestQueue== null)
        {
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addtoqueue(Request<T> rec)
    {
       getRequestQueue().add(rec);
    }
}
