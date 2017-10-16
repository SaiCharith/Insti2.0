package iitbombay.code_catalyst.com.insti20;

import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class SearchResultActivity extends AppCompatActivity {

//    private static String[] searchables={"country\nmywish","state","capital","small"};
    private static String[] days={"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
    private static String[] day_each_part={"Breakfast","lunch","tiffin","dinner"};
    CustomAdapter4 adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Bundle bundle=getIntent().getExtras();
        final String b=bundle.getString("searchword");
        Toast.makeText(SearchResultActivity.this,b,Toast.LENGTH_SHORT).show();

        ListView listView = (ListView) findViewById(R.id.search_results);
        ArrayList<searchobjects> Searchables =  new ArrayList<>();
//        Searchables.addAll(Arrays.asList(searchables));

        DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://code-catalyst-asc.firebaseio.com/Mess_Repo");
        Searchables.clear();
        for(int i=1;i<=16;i++){
            String h_name;
            if(i<10) h_name="Hostel0"+i;
            else h_name="Hostel"+i;
            DatabaseReference hostel_ref=ref.child(h_name);
            DatabaseReference mess_menu_ref=hostel_ref.child("Mess_Menu");
            DatabaseReference current_menu_ref=mess_menu_ref.child("Current");
            for(int j=0;j<7;j++){
                DatabaseReference day_menu_ref=current_menu_ref.child(days[j]);
                for(int k=0;k<4;k++){
                    final searchobjects s=new searchobjects("asdfghj",h_name,days[j],"");
                    s.setDay_part(day_each_part[k]);
                    DatabaseReference day_part_menu_ref=day_menu_ref.child(day_each_part[k]);
                    day_part_menu_ref.addValueEventListener(new ValueEventListener(){
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String temp=dataSnapshot.getValue(String.class);
                            s.setItem(temp);
                            adapter.notifyDataSetChanged();
                            adapter.getFilter().filter(b);
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
       // adapter.getFilter().filter(b);
        listView.setAdapter(adapter);

    }
}
