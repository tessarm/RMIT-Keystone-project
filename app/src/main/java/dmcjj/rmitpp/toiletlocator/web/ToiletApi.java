package dmcjj.rmitpp.toiletlocator.web;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dmcjj.rmitpp.toiletlocator.model.Toilet;
import dmcjj.rmitpp.toiletlocator.model.ToiletFactory;

/**
 * Created by A on 18/08/2017.
 */

public class ToiletApi
{
    private Context context;
    private RequestQueue requestQueue;
    private static String TOILET_URL = "https://api.myjson.com/bins/vixlx";


    public ToiletApi(Context context){
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);


    }

    public void requestToiletData(final int requestCode, final OnToiletListener onToiletListener){

        //JSON Listener
        Response.Listener<JSONObject> jsonListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("toilets");
                    Toilet[] toilets = new Toilet[array.length()];
                    for(int i=0; i < array.length(); i++){
                        Toilet toilet = ToiletFactory.createToilet(array.getJSONObject(i));
                        toilets[i] = toilet;

                        Log.i("toiletapi", toilet.getName());

                    }
                    ToiletResponse toiletResponse = new ToiletResponse(toilets);
                    onToiletListener.onToiletResponse(requestCode, toiletResponse);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        //Request object
        JsonObjectRequest toiletRequest = new JsonObjectRequest(
                TOILET_URL, null, jsonListener, null
        );

        requestQueue.add(toiletRequest);

    }
}
