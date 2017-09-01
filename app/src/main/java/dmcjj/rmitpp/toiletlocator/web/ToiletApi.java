package dmcjj.rmitpp.toiletlocator.web;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dmcjj.rmitpp.toiletlocator.model.Toilet;
import dmcjj.rmitpp.toiletlocator.model.ToiletFactory;

/**
 * Created by A on 18/08/2017.
 */

public class ToiletApi
{
    private Context context;
    private RequestQueue requestQueue;
    private static String TOILET_URL = "https://api.myjson.com/bins/ajkph";


    public ToiletApi(Context context){
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);


    }
    public void requestToiletData(final int requestCode, final OnToiletListener onToiletListener)
    {
        FirebaseDatabase db = FirebaseDatabase.getInstance();

        DatabaseReference tRef = db.getReference().child("toilets");

        tRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {


                Iterable<DataSnapshot> array = data.getChildren();


                List<Toilet> toilets = new ArrayList<>();

                for(DataSnapshot a : array){

                    Object o = a.getValue();
                    Toilet t = a.getValue(Toilet.class);

                    toilets.add(t);
                }

                ToiletResponse res = new ToiletResponse(toilets.toArray(new Toilet[toilets.size()]));
                onToiletListener.onToiletResponse(requestCode, res);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


    public void initToiletData(final int requestCode, final OnToiletListener onToiletListener){

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
