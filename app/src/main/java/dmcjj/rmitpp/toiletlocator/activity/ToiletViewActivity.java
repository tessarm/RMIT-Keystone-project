package dmcjj.rmitpp.toiletlocator.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Arrays;

import dmcjj.rmitpp.toiletlocator.R;
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

    private OnToiletListener toiletListener = new OnToiletListener() {
        @Override
        public void onToiletResponse(int requestCode, ToiletResponse toiletResponse) {
            adapter.setData(Arrays.asList(toiletResponse.getToiletData()));
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


        ToiletApi api = new ToiletApi(this);
        api.requestToiletData(0, toiletListener);




    }
}
