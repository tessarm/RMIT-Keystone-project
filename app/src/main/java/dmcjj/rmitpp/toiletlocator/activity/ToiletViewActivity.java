package dmcjj.rmitpp.toiletlocator.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dmcjj.rmitpp.toiletlocator.R;
import dmcjj.rmitpp.toiletlocator.model.Toilet;
import dmcjj.rmitpp.toiletlocator.view.SimpleDividerItemDecoration;
import dmcjj.rmitpp.toiletlocator.view.ToiletAdapter;
import dmcjj.rmitpp.toiletlocator.web.OnToiletListener;
import dmcjj.rmitpp.toiletlocator.web.ToiletApi;
import dmcjj.rmitpp.toiletlocator.web.ToiletResponse;

/**
 * Created by A on 31/08/2017.
 */

public class ToiletViewActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private ToiletAdapter adapter;



    private ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            adapter.add(dataSnapshot);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            adapter.remove(dataSnapshot);
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private OnToiletListener toiletListener = new OnToiletListener() {
        @Override
        public void onToiletResponse(int requestCode, ToiletResponse toiletResponse) {
            //adapter.setData(Arrays.asList(toiletResponse.getToiletData()));
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewtoilets);

        adapter = new ToiletAdapter();
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));




        FirebaseDatabase.getInstance().getReference().child("toilets").addChildEventListener(childEventListener);

        ToiletApi api = new ToiletApi(this);
        api.requestToiletData(0, toiletListener);


    }
}
