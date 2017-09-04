package dmcjj.rmitpp.toiletlocator.view;

import android.location.Location;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

import dmcjj.rmitpp.toiletlocator.R;
import dmcjj.rmitpp.toiletlocator.model.Toilet;

/**
 * Created by A on 2/09/2017.
 */

public class RealtimeToiletAdapter extends RecyclerView.Adapter<RealtimeToiletAdapter.ToiletViewHolder>
{
    private FirebaseRealtimeList<Toilet> firebaseRealtimeList;
    private static String firebaseEndpoint = "toilets/data";

    private FirebaseRealtimeList.EventListener<Toilet> toiletEventListener = new FirebaseRealtimeList.EventListener<Toilet>() {
        @Override
        public void onAdd(Toilet data) {
            notifyDataSetChanged();
        }

        @Override
        public void onModified(Toilet data) {
            notifyDataSetChanged();
        }

        @Override
        public void onRemoved(Toilet data) {
            notifyDataSetChanged();
        }
    };

    //private List<String> KEYS = new ArrayList<>();
    //private ArrayMap<String, Toilet> toiletData = new ArrayMap<>();

    //private List<Toilet> toiletList;
    private Location reference;

    private RealtimeToiletAdapter(){
        firebaseRealtimeList = FirebaseRealtimeList.newList(firebaseEndpoint, Toilet.class);
    }
    public static RealtimeToiletAdapter newConnection(){
        return new RealtimeToiletAdapter();
    }

    @Override
    public ToiletViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.toilet_view, parent, false);
        ToiletViewHolder holder = new ToiletViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ToiletViewHolder holder, int position) {
        Toilet item = firebaseRealtimeList.get(position);

        holder.textTitle.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return firebaseRealtimeList.size();
    }

    public class ToiletViewHolder extends RecyclerView.ViewHolder
    {
        private TextView textTitle;

        public ToiletViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
        }
    }
}