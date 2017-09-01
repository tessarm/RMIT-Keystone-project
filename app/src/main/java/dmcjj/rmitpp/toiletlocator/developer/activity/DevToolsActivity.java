package dmcjj.rmitpp.toiletlocator.developer.activity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

import dmcjj.rmitpp.toiletlocator.R;
import dmcjj.rmitpp.toiletlocator.database.Database;
import dmcjj.rmitpp.toiletlocator.model.Toilet;
import dmcjj.rmitpp.toiletlocator.web.OnToiletListener;
import dmcjj.rmitpp.toiletlocator.web.ToiletApi;
import dmcjj.rmitpp.toiletlocator.web.ToiletResponse;

/**
 * Created by A on 31/08/2017.
 */

public class DevToolsActivity extends AppCompatActivity
{
    private EditText editName;
    private EditText editLat;
    private EditText editLng;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devtools);

        editName = (EditText)findViewById(R.id.editName);
        editLat = (EditText)findViewById(R.id.editLat);
        editLng = (EditText)findViewById(R.id.editLng);

        Button init = (Button)findViewById(R.id.pushInitToilets);
        init.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initToiletData();
            }
        });

        Button send = (Button)findViewById(R.id.sendToDb);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToDb();
            }
        });





    }

    private void sendToDb(){
        String name = editName.getText().toString();
        double lat = Double.parseDouble(editLat.getText().toString());
        double lng = Double.parseDouble(editLng.getText().toString());

        Toilet t = Toilet.create(name, lat, lng);
        Database.putToilet(t);
    }

    private void initToiletData(){
        ToiletApi api = new ToiletApi(this);
        api.initToiletData(0, new OnToiletListener() {
            @Override
            public void onToiletResponse(int requestCode, ToiletResponse toiletResponse) {
                for(Toilet t : toiletResponse.getToiletData())
                    Database.putToilet(t);
                Toast.makeText(DevToolsActivity.this, "Toilet Data Init", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
