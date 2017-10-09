package iitbombay.code_catalyst.com.insti20;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static java.sql.Types.NULL;

public class DisplayMenu extends AppCompatActivity {
    private int hostel_no;
    private String day;
    private static ArrayList<String> l=new ArrayList<String>();

    private static CustomAdapter2 customAdapter;
    private static String[] day_each_part={"Breakfast","lunch","tiffin","dinner"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_menu);

        Bundle bundle=getIntent().getExtras();
        hostel_no=bundle.getInt("index");
        day = bundle.getString("Day");
        TextView b= (TextView) findViewById(R.id.Day);
        b.setText(day+"'s Menu");
        Toast.makeText(this,day+hostel_no,Toast.LENGTH_SHORT).show();


        String h_name;

        if(hostel_no<10) h_name="Hostel0"+hostel_no;
        else h_name="Hostel"+hostel_no;

        DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://code-catalyst-asc.firebaseio.com/Mess_Repo");
        DatabaseReference hostel_ref=ref.child(h_name);
        DatabaseReference Mess_menu_ref=hostel_ref.child("Mess_Menu");
        DatabaseReference Current_menu=Mess_menu_ref.child("Current");
        DatabaseReference Day_menu=Current_menu.child(day);
        for(int i=0;i<4;i++){
            DatabaseReference Days_menu_each_part=Day_menu.child(day_each_part[i]);
           // final String[] s = new String[1];
            final ArrayList<String> ltemp=new ArrayList<>();
            final int[] v={1};
            Days_menu_each_part.addValueEventListener(new ValueEventListener(){
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                   String s = ""+ dataSnapshot.getValue(String.class);
                    ltemp.add(s);
                    customAdapter.notifyDataSetChanged();
                    v[0]=5;
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

          //  l.add(s[0]);
            l.add("ltemp.get(0)");

//            String s;
//            s="I am\n fine";///get from database
           // if(s[0]==null) s[0]=" ";

            Toast.makeText(this,day+h_name+v[0],Toast.LENGTH_SHORT).show();
        }
        customAdapter=new CustomAdapter2(l,DisplayMenu.this,h_name,day);
        ListView listView = (ListView) findViewById(R.id.day_menu_list);
        listView.setAdapter(customAdapter);

    }


}
