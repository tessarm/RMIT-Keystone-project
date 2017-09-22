package dmcjj.rmitpp.toiletlocator.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

import dmcjj.rmitpp.toiletlocator.R;
import dmcjj.rmitpp.toiletlocator.model.ToiletRating;


/**
 * Created by A on 9/09/2017.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>
{
    private List<DataSnapshot> comments = new ArrayList<>();

    public void setSnapshot(DataSnapshot snapshot){
        comments.clear();
        Iterable<DataSnapshot> snapComments = snapshot.getChildren();
        for(DataSnapshot theComment : snapComments){
            comments.add(theComment);
        }
        notifyDataSetChanged();
    }


    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        DataSnapshot comment = comments.get(position);
        ToiletRating review = comment.getValue(ToiletRating.class);
        holder.textComment.setText(review.text);
        holder.ratingComment.setRating(review.rating);
    }

    @Override
    public int getItemCount() {
        if (comments == null)
            return 0;
        return comments.size();
    }

    public void clear() {
        comments.clear();
        notifyDataSetChanged();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder
    {
        private TextView textComment;
        private RatingBar ratingComment;

        public CommentViewHolder(View itemView) {
            super(itemView);
            textComment = itemView.findViewById(R.id.textComment);
            ratingComment = itemView.findViewById(R.id.rating);

        }
    }
}
