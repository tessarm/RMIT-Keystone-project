package dmcjj.rmitpp.toiletlocator.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.google.firebase.database.DataSnapshot;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmcjj.rmitpp.toiletlocator.Database;
import dmcjj.rmitpp.toiletlocator.R;

/**
 * Created by A on 25/09/2017.
 */

public class ReviewDialog extends Dialog
{
    @BindView(R.id.rating) RatingBar ratingBar;
    @BindView(R.id.reviewText) EditText reviewText;
    @BindView(R.id.cancel) Button cancel;
    @BindView(R.id.add) Button add;

    private DataSnapshot mToilet;

    public ReviewDialog(@NonNull Context context, DataSnapshot currentToilet) {
        super(context);
        this.mToilet = currentToilet;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_review);
        ButterKnife.bind(this);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onReviewCancel(view);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onReviewAdd(view);
            }
        });

    }

    public void onReviewCancel(View v){
        dismiss();
    }

    public void onReviewAdd(View v){
        Database.putReview(mToilet.getKey(), ratingBar.getRating(), reviewText.getText().toString());
        dismiss();
    }

}
