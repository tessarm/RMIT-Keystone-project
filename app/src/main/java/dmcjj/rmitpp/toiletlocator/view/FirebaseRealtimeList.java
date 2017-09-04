package dmcjj.rmitpp.toiletlocator.view;

import android.location.Location;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by A on 1/09/2017.
 */

public class FirebaseRealtimeList<T>
{
    public int size() {
        return 0;
    }

    public T get(int position) {
        return null;
    }

    public interface EventListener<T>{
        void onAdd(T data);
        void onModified(T data);
        void onRemoved(T data);
    }

    private final String firebaseEndpoint;
    private final List<String> KEYS = new ArrayList<>();
    private final ArrayMap<String, T> toiletData = new ArrayMap<>();
    private EventListener<T> listEventListener;

    private final Class<T> aClass;

    private ChildEventListener mChildEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            add(dataSnapshot);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            change(dataSnapshot);
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            remove(dataSnapshot);
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public static FirebaseRealtimeList newList(String firebaseEndpoint, Class<?> aClass){

        return new FirebaseRealtimeList(aClass, firebaseEndpoint);
    }

    public void connect(EventListener<T> eventListener){
        this.listEventListener = eventListener;
        FirebaseDatabase.getInstance().getReference().child(firebaseEndpoint).addChildEventListener(mChildEventListener);
    }
    private void disconnect(){
        FirebaseDatabase.getInstance().getReference().child(firebaseEndpoint).removeEventListener(mChildEventListener);
    }



    private FirebaseRealtimeList(Class<T> aClass, String firebaseEndpoint){
        this.firebaseEndpoint = firebaseEndpoint;
        this.aClass = aClass;
    }

    //private List<Toilet> toiletList;


    private void add(DataSnapshot dataToilet){
        T value = dataToilet.getValue(aClass);
        KEYS.add(dataToilet.getKey());
        toiletData.put(dataToilet.getKey(), value);
        Log.i("toiletdata", "Adding " + dataToilet.getKey());

    }
    private void change(DataSnapshot dataSnapshot){
        toiletData.put(dataSnapshot.getKey(), dataSnapshot.getValue(aClass));
    }
    private void remove(DataSnapshot dataToilet){
        KEYS.remove(dataToilet.getKey());
        toiletData.remove(dataToilet.getKey());

        Log.i("toiletdata", "Removing " + dataToilet.getKey());

    }



}
