package dmcjj.rmitpp.toiletlocator.view;

import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by A on 9/09/2017.
 */

public abstract class FirebaseAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T>
{
    private ArrayMap<String, DataSnapshot> mMap = new ArrayMap<>();

    public FirebaseAdapter(){
        FirebaseDatabase.getInstance().getReference(fireReference()).addChildEventListener(childEventListener);

    }


    private ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            mMap.put(dataSnapshot.getKey(), dataSnapshot);


        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            mMap.put(dataSnapshot.getKey(), dataSnapshot);
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            mMap.remove(dataSnapshot.getKey());
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };


    public abstract void fireBind(T holder, DataSnapshot viewHolder);
    public abstract T fireCreate(ViewGroup parent, int viewType);
    public abstract String fireReference();


    @Override
    public void onBindViewHolder(T holder, int position) {
       fireBind(holder, mMap.valueAt(position));
    }

    @Override
    public int getItemCount() {
        return mMap.size();
    }

    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        return fireCreate(parent, viewType);
    }
}
