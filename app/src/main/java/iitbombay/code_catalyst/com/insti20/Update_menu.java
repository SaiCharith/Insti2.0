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

/**
 * This class is used to Update the mess menu of a particular hostel.
 * Mess_menu is updated at the Buffer in the FirebaseDatabase
 * @author Code-Catalyst
 *
 */
public class Update_menu extends AppCompatActivity {

    /**
     * Stores hostel_no
     */
    int hostel_no;
    /**
     * Stores hostel_name
     */
    String hostel_name;
    /**
     * iterator used to iterate along days of week.
     */
    int i=0;

    /**
     * Reference to database.
     */
    DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://code-catalyst-asc.firebaseio.com/Mess_Repo");

    /**
     * Stores days of the week.
     */
    String[] days={"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

    /**
     * Sub-class to hold info updated by messsecy and to push it to the database.
     */
     private class Each_day{
        /**
         * Stores Breakfast
         */
         String Breakfast;
        /**
         * Stores Lunch
         */
         String Lunch;
        /**
         * Stores Tiffin
         */
         String Tiffin;
        /**
         * Stores Dinner
         */
         String Dinner;

        /**
         * Constructor to update
         * @param a Breakfast
         * @param b Lunch
         * @param c Tiffin
         * @param d Dinner
         */
        Each_day(String a,String b,String c,String d)
        {

            Breakfast=a;
            Lunch=b;
            Tiffin=c;
            Dinner=d;
        }

        /**
         * pairs fields and values of this class into a hashmap which is then updated at the firebasedatabase.
         * @return result(a hashmap with fields and corresponding values)
         */
        Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("Breakfast", Breakfast);
            result.put("dinner", Dinner);
            result.put("tiffin",Tiffin);
            result.put("lunch", Lunch);
            return result;
        }

    }

    /**
     * Setting hostel values which come from previous activity.
     */

    private void sethostelvalues(){

        Bundle bundle=getIntent().getExtras();
        hostel_no=bundle.getInt("hostel_no");
        if(hostel_no<10) hostel_name="Hostel0"+hostel_no;
        else hostel_name="Hostel"+hostel_no;
    }

    /**
     * Getting reference to 4 editText and 2 buttons as present in activity_update_menu.xml
     * Here set the functionality of the buttons back and update.
     * varible 'i' is used as an iterating elemnet and when user clicks update 'i' is incremented and when back is pressed 'i' is reduced by 1 along withhandling base cases.
     * In update button text from edit text is obtained and is pushed onto the firebasedatabase.
     * @param savedInstanceState
     */
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
        tv.setText(days[0]);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i==0){
                    Update_menu.super.onBackPressed();
                }
                if(i>0&&i<7) {
                    i--;
                    brf.setText("");
                    lun.setText("");
                    tif.setText("");
                    dinn.setText("");
                    tv.setText(days[i]);
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(i<7) {
                    DatabaseReference hostel = ref.child(hostel_name);
                    DatabaseReference menu = hostel.child("Mess_Menu");
                    DatabaseReference buffer = menu.child("Buffer");
                    DatabaseReference day = buffer.child(days[i]);
                    String _brf = brf.getText().toString();
                    String _lun = lun.getText().toString();
                    String _tif = tif.getText().toString();
                    String _dinn = dinn.getText().toString();

                    Each_day mDay = new Each_day(_brf, _lun, _tif, _dinn);

                    Map<String, Object> postValues = mDay.toMap();
                    day.updateChildren(postValues);
                    Toast.makeText(Update_menu.this, "Updated " + days[i] + "'s Menu", Toast.LENGTH_SHORT).show();
                    brf.setText("");
                    lun.setText("");
                    tif.setText("");
                    dinn.setText("");
                    i++;
                    if(i<7) {
                        tv.setText(days[i]);
                    }
                }

            }
        });
       }
}
