package iitbombay.code_catalyst.com.insti20;

import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This activity is showed up when something is searched in searchbar.
 * @author Code-Catalyst
 *
 */

public class SearchResultActivity extends AppCompatActivity {

    /**
     *  array of days is used for iterating along the days of week
     */
    private static String[] days={"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
    /**
     *  array of day_each_part is used for iterating along each part of the day.
     */
    private static String[] day_each_part={"Breakfast","lunch","tiffin","dinner"};
    /**
     * CustumAdapter4 is used to display the results in a listview
     */
    CustomAdapter4 adapter;


    /**
     * In this method all the items of the menu in the 16 hostels and 7 days are populated to the list 'searchebles'.
     * Later this is passed onto the adapter wich generates the listview.
     * xml layout file linked with this activity is 'activity_search_result'
     * Onclicking the an elemnt of listview, the view gets redirected to DisplayMenu corresponding to that day and that hostel.
     * @param savedInstanceState instance of Bundle;
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Bundle bundle=getIntent().getExtras();
        final String b=bundle.getString("searchword");
        Toast.makeText(SearchResultActivity.this,b,Toast.LENGTH_SHORT).show();  //to show what is the query to be searched for

        TextView tv = (TextView) findViewById(R.id.show_results);
        tv.setText("showing results for \""+b+"\"\n");                          //setting the heading for this Activity

        ListView listView = (ListView) findViewById(R.id.search_results);       //to show results in al listview.
        final ArrayList<searchobjects> Searchables =  new ArrayList<>();        //used as a storage element.

        DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://code-catalyst-asc.firebaseio.com/Mess_Repo");
        Searchables.clear();         //clearing the list before intialization. Required if this activity is called once again before finishing.
        for(int i=1;i<=16;i++){
            /**
             * iterating over 16 hostels
             */

            String h_name;
            if(i<10) h_name="Hostel0"+i;
            else h_name="Hostel"+i;
            DatabaseReference hostel_ref=ref.child(h_name);
            DatabaseReference mess_menu_ref=hostel_ref.child("Mess_Menu");
            DatabaseReference current_menu_ref=mess_menu_ref.child("Current");  //setting the reference to firebase database
            for(int j=0;j<7;j++){
                /**
                 * iterating over each day
                 */
                DatabaseReference day_menu_ref=current_menu_ref.child(days[j]);
                for(int k=0;k<4;k++){
                    /**
                     * iterating over each part of day
                     */
                    final searchobjects s=new searchobjects(i,"",h_name,days[j],"");
                    s.setDay_part(day_each_part[k]);
                    DatabaseReference day_part_menu_ref=day_menu_ref.child(day_each_part[k]);
                    day_part_menu_ref.addValueEventListener(new ValueEventListener(){  //this function is called once and whenever the data in day_part_menu changes.
                        @Override                                                      //Change in values of database will instantly show up because of this function
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String temp=dataSnapshot.getValue(String.class);
                            s.setItem(temp);
                            adapter.notifyDataSetChanged();                            //notifing customAdapter4 of a datachange event
                            adapter.getFilter().filter(b);                             //filtering whenever new dataset is added.
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                        Searchables.add(s);
                }

            }
        }

        adapter=new CustomAdapter4(SearchResultActivity.this, Searchables);
        listView.setAdapter(adapter);                                                 //setting the adapter here.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long L) {                 //setting up the function when user clicks on any of the element of the listview
                Intent intent = new Intent(SearchResultActivity.this,DisplayMenu.class);
                intent.putExtra("index",adapter.getSearchlst().get(i).getH_no());
                intent.putExtra("Day",adapter.getSearchlst().get(i).getDay());
                startActivity(intent);                                                //redirecting to Display_menu activity and passing hostel_no(as "index") and Day
            }
        });

    }
}
