package iitbombay.code_catalyst.com.insti20;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.OnDisconnect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class Update_menu extends AppCompatActivity {

    int hostel_no;
    String hostel_name;
    int i=0;

    DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://code-catalyst-asc.firebaseio.com/Mess_Repo");
//    DatabaseReference hostel=ref.child("Hostel3");
//    DatabaseReference menu=hostel.child("Mess_Menu");
//    DatabaseReference buffer=menu.child("buffer");


    String[] types={"Buffer","Current"};
    String[] days={"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};



     private class Each_day{
//        ArrayList<String> Breakfast=new ArrayList<String>();
//        ArrayList<String> Lunch=new ArrayList<String>();
//        ArrayList<String> Tiffin=new ArrayList<String>();
//        ArrayList<String> Dinner=new ArrayList<String>();
         String Breakfast;
         String Lunch;
         String Tiffin;
         String Dinner;
        Each_day() {
            Breakfast="Not Available";
            Lunch="Not Available";
            Tiffin="Not Available";
            Dinner="Not Available";
        }

        Each_day(String a,String b,String c,String d)
        {

            Breakfast=a;
            Lunch=b;
            Tiffin=c;
            Dinner=d;
        }
        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("Breakfast", Breakfast);
            result.put("dinner", Dinner);
            result.put("tiffin",Tiffin);
            result.put("lunch", Lunch);
            return result;
        }

    }

    private void sethostelvalues(){

//        Bundle bundle=getIntent().getExtras();
//        hostel_no=bundle.getInt("Hostel_no");
        hostel_no=3;  //Need to update as per different hostel mess secys;
        if(hostel_no<10) hostel_name="Hostel0"+hostel_no;
        else hostel_name="Hostel"+hostel_no;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_menu);

        sethostelvalues();

        final EditText brf= (EditText) findViewById(R.id.breakfast);
        final EditText lun= (EditText) findViewById(R.id.lunch);
        final EditText tif= (EditText) findViewById(R.id.tiffin);
        final EditText dinn= (EditText) findViewById(R.id.dinner);
        Button update=(Button) findViewById(R.id.update);
        Button back=(Button) findViewById(R.id.back);
        final TextView tv= (TextView) findViewById(R.id.update_day);

//        for(int hostel_no=1;hostel_no<=16;hostel_no++) {
//            String hostel_name;
//           if(hostel_no<10) {
//              hostel_name ="0"+hostel_no;
//           }
//           else {
//              hostel_name=""+hostel_no;
//           }
//            DatabaseReference hostel = ref.child("Hostel"+hostel_name);
//            DatabaseReference rating=hostel.child("Rating");
//            DatabaseReference menu=hostel.child("Mess_Menu");
//
//
//            HashMap<String,Object> rating_childs = new HashMap<>();
//            rating_childs.put("Current_Rating",0.0);
//            rating_childs.put("No_of_people",0);
//            rating.updateChildren(rating_childs);
//
//            HashMap<String,Object> hostel_childs=new HashMap<>();
//            hostel_childs.put("Likes",0);
//            hostel_childs.put("Dislikes",0);
//            hostel.updateChildren(hostel_childs);
//
//            for(int k=0;k<2;k++){
//                DatabaseReference current=menu.child(types[k]);
//                for (int i = 0; i < 7; i++) {
//                    DatabaseReference day = current.child(days[i]);
//                    Each_day mDay = new Each_day("N/A", "N/A", "N/A", "N/A");
//                    // Toast.makeText(Update_menu.this, "Updated"+i, Toast.LENGTH_SHORT).show();
//                    Map<String, Object> postValues = mDay.toMap();
//                    day.updateChildren(postValues);
//
//                }
//            }
//        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i>0&&i<7) i--;
                brf.setText("");
                lun.setText("");
                tif.setText("");
                dinn.setText("");
                tv.setText(days[i]);
            }
        });

        tv.setText(days[i]);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    DatabaseReference hostel=ref.child(hostel_name);
                    DatabaseReference menu=hostel.child("Mess_Menu");
                    DatabaseReference buffer=menu.child("Buffer");
                    DatabaseReference day=buffer.child(days[i]);
                    String _brf = brf.getText().toString();
                    String _lun = lun.getText().toString();
                    String _tif = tif.getText().toString();
                    String _dinn = dinn.getText().toString();

                    Each_day mDay = new Each_day(_brf, _lun, _tif, _dinn);

                    Map<String, Object> postValues = mDay.toMap();
                    day.updateChildren(postValues);
                    Toast.makeText(Update_menu.this, "Updated "+days[i]+"'s Menu", Toast.LENGTH_SHORT).show();
                    brf.setText("");
                    lun.setText("");
                    tif.setText("");
                    dinn.setText("");
                    i++;
                    if(i<7) tv.setText(days[i]);

                    else {}

            }
        });
       }
}
