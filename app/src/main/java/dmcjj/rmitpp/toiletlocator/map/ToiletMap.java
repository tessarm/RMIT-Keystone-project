package dmcjj.rmitpp.toiletlocator.map;

import android.location.Location;
import android.location.LocationManager;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.RequestResult;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.model.Step;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import dmcjj.rmitpp.toiletlocator.model.ToiletValues;

/**
 * Created by A on 29/08/2017.
 */
public class ToiletMap
{
    private static LatLng DEFAULT_LOCATION = new LatLng(-37.817798, 144.968714);
    private GoogleMap googleMap;
    private ToiletValues[] mapData;
    private static int CAMERA_ZOOM = 13;
    private MyLocation myLocation;
    private boolean animateLocation;


    List<LatLng> allDirectionPoints = new ArrayList<>();

    private ToiletMap(GoogleMap googleMap, Location myStartLocation){
        this.googleMap = googleMap;
        this.myLocation = MyLocation.create();

    }

    public void update(Location newLocation){
        //if animation set


       // myLocation.set(newLocation);

        //myMarker.setPosition(latlng);
        //myMarker.setVisible(true);
    }

    public static ToiletMap createMap(GoogleMap googleMap){

        Location l = new Location(LocationManager.GPS_PROVIDER);
        return new ToiletMap(googleMap, l);
    }

    public void addMarker(MarkerOptions m){
        MarkerOptions op = new MarkerOptions();

        googleMap.addMarker(m);
    }

    public void setDefaultMarker(double lat, double lng, String msg){
        setDefaultMarker(new LatLng(lat, lng), msg);

    }
    public void setDefaultMarker(LatLng latLng, String msg){
        MarkerOptions op = new MarkerOptions();
        op.position(latLng);
        op.title(msg);
        addMarker(op);
    }

    public void setDefaultMarker(Location location, String msg){
        setDefaultMarker(location.getLatitude(), location.getLongitude(), msg);
    }
    public void setAnimateLocation(boolean b){
        this.animateLocation = b;
    }

    public void pushAllSteps(List<Step> steps)
    {
        for(int i=0;i < steps.size(); i++){
            Step step = steps.get(i);

            if(!step.isContainStepList()){

                for(LatLng l : step.getPolyline().getPointList())
                    allDirectionPoints.add(l);
            }
            else
                pushAllSteps(step.getStepList());
        }
    }


    public void onMapReady(LatLng startLocation) {
       // myLocation.set(startLocation);
       // myLocation.setVisible(true);



        GoogleDirection.withServerKey("AIzaSyBglDYQrYOhOwESWiFrEtFUQEqn-QLt0uI").
                from(startLocation).to(DEFAULT_LOCATION).execute(new DirectionCallback() {
            @Override
            public void onDirectionSuccess(Direction direction, String rawBody) {
               if(direction.getStatus().equals(RequestResult.OK)){
                   PolylineOptions ops = new PolylineOptions();

                   Route r = direction.getRouteList().get(0);


                   List<Leg> legs = r.getLegList();
                   for(Leg l : legs) {

                       List<Step> steps = l.getStepList();
                       pushAllSteps(steps);

                   }

                   for(LatLng l : allDirectionPoints){
                       MarkerOptions newOp = new MarkerOptions();
                       newOp.position(l);
                       //googleMap.addMarker(newOp);
                   }

                   ops.addAll(allDirectionPoints);

                   Polyline p = googleMap.addPolyline(ops);
               }


            }

            @Override
            public void onDirectionFailure(Throwable t) {

            }
        });

        //myMarker = googleMap.addMarker(new MarkerOptions().position(startLocation).title("My Location"));
        //myMarker.setVisible(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startLocation, CAMERA_ZOOM));

    }

}
