package iitbombay.code_catalyst.com.insti20;

import android.app.SearchManager;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import static java.security.AccessController.getContext;

public class After_login extends AppCompatActivity {
    Button button;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    private static ArrayList<objects> l=new ArrayList<objects>();
    private static CustomAdapter customAdapter;
    Toolbar toolbar;

    String uid;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
        button=(Button) findViewById(R.id.logout);
        mAuth =FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){

                if(firebaseAuth.getCurrentUser() == null)
                {
                    startActivity(new Intent(After_login.this,MainActivity.class));
                }
                else {
                    uid= firebaseAuth.getCurrentUser().getUid();

                }
            }

        };

        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                mAuth.signOut();
            }
        });


        TypedArray imgs = getResources().obtainTypedArray(R.array.Images);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://code-catalyst-asc.firebaseio.com/Mess_Repo");
        l.clear();
        for(int i=0;i<16;i++){

            final objects s= new objects(i+1);
            s.Hostel_name=getResources().getStringArray(R.array.Hostel_names)[i];
            String h_name;
            if(s.hostel_no<10) h_name="Hostel0"+s.hostel_no;
            else h_name="Hostel"+s.hostel_no;
            DatabaseReference hostel_ref=ref.child(h_name);
            DatabaseReference rating_ref=hostel_ref.child("Rating");
            DatabaseReference curr_rating=rating_ref.child("Current_Rating");
            DatabaseReference likes_ref=hostel_ref.child("Likes");
            DatabaseReference dislikes_ref=hostel_ref.child("Dislikes");
            likes_ref.addValueEventListener(new ValueEventListener(){
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    s.likes= dataSnapshot.getValue(Integer.class);
                    customAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            dislikes_ref.addValueEventListener(new ValueEventListener(){
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    s.dislikes= dataSnapshot.getValue(Integer.class);
                    customAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            curr_rating.addValueEventListener(new ValueEventListener(){
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    s.rating= dataSnapshot.getValue(Float.class);
                    customAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            s.image_id=imgs.getResourceId(i,-1);

            l.add(s);
        }

        toolbar= (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Hostel Mess");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        toolbar.setBackgroundColor(getResources().getColor(R.color.common_google_signin_btn_text_light));


        customAdapter=new CustomAdapter(l,After_login.this);

        ListView listView = (ListView) findViewById(R.id.hostel_display);
        listView.setAdapter(customAdapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long L) {
                Intent intent = new Intent(After_login.this,HostelActivity.class);
                intent.putExtra("index",l.get(i).hostel_no); //passing hostel no to the upcoming activity.
                intent.putExtra("uid",uid);
                startActivity(intent);
                Toast.makeText(After_login.this, l.get(i).Hostel_name, Toast.LENGTH_SHORT).show();
            }
        });


     }

    public static void sortlist(int order){
        Collections.sort(l,new Sorter(order));
        //Toast.makeText(MainActivity.this,"Sorting",)
        customAdapter.notifyDataSetChanged();
        //Toast.makeText(,"sorting",Toast.LENGTH_SHORT).show();

    }

    static class Sorter implements Comparator<objects> {
        int order=1;
        Sorter(int order){
            this.order=order;
        }

        @Override
        public int compare(objects t1, objects t2) {

            if(order==1){
                if(t1.hostel_no>t2.hostel_no) return 1;
                else if(t1.hostel_no<t2.hostel_no) return -1;
                else return 0;
            }
            else if(order==2){
                if(t1.rating>t2.rating) return -1;
                else if(t1.rating<t2.rating) return 1;
                else return 0;
            }
            else if(order==3){
                if(t1.likes>t2.likes) return -1;
                else if(t1.likes<t2.likes) return 1;
                else return 0;
            }
            else if(order==4){
                if(t1.dislikes>t2.dislikes) return 1;
                else if(t1.dislikes<t2.dislikes) return -1;
                else return 0;
            }
            return 0;
        }
    }





    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_sort,menu);
//        MenuItem item  = menu.findItem(R.id.search_bar);
         SearchView searchView = (SearchView) MenuItemCompat.getActionView( menu.findItem(R.id.search_bar));

        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
//
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(After_login.this,SearchResultActivity.class);
                intent.putExtra("searchword",query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               // customAdapter.getFilter().filter(newText);
               // adapter.getFilter().filter(newText);

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        return super.onOptionsItemSelected(item);
    }
    

}
