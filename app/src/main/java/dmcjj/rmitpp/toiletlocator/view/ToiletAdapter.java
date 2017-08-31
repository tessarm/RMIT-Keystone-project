package dmcjj.rmitpp.toiletlocator.view;

import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import dmcjj.rmitpp.toiletlocator.R;
import dmcjj.rmitpp.toiletlocator.model.Toilet;

/**
 * Created by A on 31/08/2017.
 */

public class ToiletAdapter extends RecyclerView.Adapter<ToiletAdapter.ToiletViewHolder>
{
    private List<Toilet> toiletList;
    private Location reference;

    public void setData(List<Toilet> data){
        this.toiletList = data;
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
        Toilet item = toiletList.get(position);

        holder.textTitle.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        if(toiletList == null)
            return 0;
        return toiletList.size();
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
