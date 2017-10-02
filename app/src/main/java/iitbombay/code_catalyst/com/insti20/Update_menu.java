package iitbombay.code_catalyst.com.insti20;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Update_menu extends AppCompatActivity {

     private class Each_day{
        ArrayList<String> Breakfast=new ArrayList<String>();
        ArrayList<String> Lunch=new ArrayList<String>();
        ArrayList<String> Tiffin=new ArrayList<String>();
        ArrayList<String> Dinner=new ArrayList<String>();
        Each_day() {


           Breakfast.add("Nothing");
            Lunch.add("Nothing");
            Tiffin.add("Nothing");
            Dinner.add("Nothing");
        }

        Each_day(String a,String b,String c,String d)
        {
           Breakfast.add(a);
            Lunch.add(b);
            Tiffin.add(c);
            Dinner.add(d);
        }
        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("Breakfast", Breakfast);
            result.put("dinner", Dinner);
            result.put("tiffin",Tiffin );
            result.put("lunch", Lunch);
            return result;
        }

    };
//    final FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference ref = database.getReference("server/saving-data/fireblog");

    DatabaseReference ref = FirebaseDatabase.getInstance()
            .getReferenceFromUrl("https://code-catalyst-asc.firebaseio.com/Mess_Repo");
    DatabaseReference mnode=ref.child("hostel2");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_menu);

        EditText brf= (EditText) findViewById(R.id.breakfast);
        EditText lun= (EditText) findViewById(R.id.lunch);
        EditText tif= (EditText) findViewById(R.id.tiffin);
        EditText dinn= (EditText) findViewById(R.id.dinner);
        Button update=(Button) findViewById(R.id.update);
        final String _brf=brf.getText().toString();
        final String _lun=lun.getText().toString();
        final String _tif=tif.getText().toString();
        final String _dinn=dinn.getText().toString();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Each_day mDay=new Each_day(_brf,_lun,_tif,_dinn);
                Map<String, Object> postValues =mDay.toMap();
                mnode.updateChildren(postValues);
            }
        });
       }
}
