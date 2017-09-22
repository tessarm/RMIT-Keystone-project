package dmcjj.rmitpp.toiletlocator.view;

import android.app.Activity;
import android.content.Intent;
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
import dmcjj.rmitpp.toiletlocator.activity.MapsActivity;
import dmcjj.rmitpp.toiletlocator.model.Toilet;
import dmcjj.rmitpp.toiletlocator.model.ToiletValues;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by A on 31/08/2017.
 */

public class ToiletAdapter extends RecyclerView.Adapter<ToiletAdapter.ToiletViewHolder>
{
    private List<String> KEYS = new ArrayList<>();
    private ArrayMap<String, Toilet> toiletData = new ArrayMap<>();
    private Activity activityRef;

    //private List<ToiletValues> toiletList;
    private Location reference;
    public ToiletAdapter(Activity act){
        activityRef = act;
    }

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
    public void setLocation(Location location){  // use as reference to dist -notifyDataSetChanged

    }

    @Override
    public ToiletViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.toilet_view, parent, false);
        ToiletViewHolder holder = new ToiletViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ToiletViewHolder holder, int position) {
        String key = KEYS.get(position);
        Toilet item = toiletData.get(KEYS.get(position));
//        holder.textDist.setText("test");
        holder.textTitle.setText(item.value.getName());
    }

    @Override
    public int getItemCount() {
       return KEYS.size();
    }

    public class ToiletViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView textTitle;
        private TextView textDist;

        public ToiletViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDist = itemView.findViewById(R.id.textDistance);
            itemView.setOnClickListener(this);
        }
        public void onClick(View itemView){
            String key = KEYS.get(getAdapterPosition());
//            Toilet current = toiletData.get(key);
//            String LatLng = current.getLatLng();
            //custom intent to restroom
            //pass key to intent on map activity
            Intent intent = new Intent(itemView.getContext(),MapsActivity.class);
            intent.putExtra("key", key);
            activityRef.setResult(Activity.RESULT_OK, intent);
            activityRef.finish();
        }
    }
}
