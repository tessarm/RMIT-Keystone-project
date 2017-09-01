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
 * Created by A on 31/08/2017.
 */

public class ToiletAdapter extends RecyclerView.Adapter<ToiletAdapter.ToiletViewHolder>
{
    private List<String> KEYS = new ArrayList<>();
    private ArrayMap<String, Toilet> toiletData = new ArrayMap<>();

    //private List<Toilet> toiletList;
    private Location reference;

    public void add(DataSnapshot dataToilet){
        KEYS.add(dataToilet.getKey());
        toiletData.put(dataToilet.getKey(), dataToilet.getValue(Toilet.class));
        Log.i("toiletdata", "Adding " + dataToilet.getKey());
        notifyDataSetChanged();
    }
    public void remove(DataSnapshot dataToilet){
        KEYS.remove(dataToilet.getKey());
        toiletData.remove(dataToilet.getKey());

        Log.i("toiletdata", "Removing " + dataToilet.getKey());
        notifyDataSetChanged();
    }
    public void setLocation(Location location){

    }

    @Override
    public ToiletViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.toilet_view, parent, false);
        ToiletViewHolder holder = new ToiletViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ToiletViewHolder holder, int position) {
        Toilet item = toiletData.get(KEYS.get(position));

        holder.textTitle.setText(item.getName());
    }

    @Override
    public int getItemCount() {
       return KEYS.size();
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
