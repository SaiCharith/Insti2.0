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

/**
 * This is the class which takes care of the screen which appears after the
 * authentication of the user is done . Or in other words This is the activity which
 * is intended after Main_Activity.
 * This is the Backbone of our app which displays the List of hostels in a very interactive
 * format along with the ratings likes and dislikes
 * Also we provide the features such as search and Sorting by Likes Dislikes Rating and Hostel number
 * Displays the Home Page After Login Showing All Hostels in List form
 * @author Code-Catalyst
 */
public class After_login extends AppCompatActivity {
    /**
     * Used as a storage for object of Sign out button
     */
    Button button;
    /**
     * An Object of mAuth(from Firebase) to verify if the user is signed in or not
     */
    FirebaseAuth mAuth;
    /**
     * A listener attached to mAuth to see if Authentication mode is changed or not
     */
    FirebaseAuth.AuthStateListener mAuthListener;
    /**
     * Arraylist l to hold the information of 16 hostels.
     * Elements of this are of the type objects.
     */
    private static ArrayList<objects> l=new ArrayList<objects>();//list of our Hostel Objects
    /**
     * CustomAdapter which publishes our data to the List view
     */
    private static CustomAdapter customAdapter;
    Toolbar toolbar;
    /**
     * Object to Store User Id
     */
    String uid;

    /**
     * Attaches Listener to the mAuth (Firebase Authentication object) in the Start
     */
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);//attaching listener to mAuth
    }

    /**
     * Handles the Event once this Activity is created
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * Publishing current window with our corresponding xml layout
         */
        setContentView(R.layout.activity_after_login);
        /**
         * Assigning the button by extracting by id
         */
        button=(Button) findViewById(R.id.logout);
        /**
         * Instantiating mAuth connecting to firebase
         */
        mAuth =FirebaseAuth.getInstance();
        /**
         * If user not signed in then this page is not allowed and he must be redirected to the sign in page
         */
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
        /**
         * If the user voluntarily Signs out This does so
         */
        button.setOnClickListener(new View.OnClickListener(){//for volantrary sign out of the user

            @Override
            public void onClick(View view) {
                mAuth.signOut();
            }
        });

        /**
         * get images of each hostel in a list
         */
        TypedArray imgs = getResources().obtainTypedArray(R.array.Images);
        /**
         * connecting to our online database
         */
        DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://code-catalyst-asc.firebaseio.com/Mess_Repo");
        l.clear();
        /**
         * Iterating over all hostels and populating it in list view
         */
        for(int i=0;i<16;i++){
            /**
             * calling constructor which gives hostel number as i
             */
            final objects s= new objects(i+1);
            s.Hostel_name=getResources().getStringArray(R.array.Hostel_names)[i];
            String h_name;
            if(s.hostel_no<10) h_name="Hostel0"+s.hostel_no;//handling single digit
            else h_name="Hostel"+s.hostel_no;
            DatabaseReference hostel_ref=ref.child(h_name);//penetrating deeper in the json tree
            DatabaseReference rating_ref=hostel_ref.child("Rating");//penetrating deeper in the json tree
            DatabaseReference curr_rating=rating_ref.child("Current_Rating");//penetrating deeper in the json tree
            DatabaseReference likes_ref=hostel_ref.child("Likes");//penetrating deeper in the json tree
            DatabaseReference dislikes_ref=hostel_ref.child("Dislikes");//penetrating deeper in the json tree
            /**
             * Adding listener to like reference making our list dynamic as we inform the adapter
             */
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
            /**
             * Adding listener to dislike reference making our list dynamic as we inform the adapter
             */
            dislikes_ref.addValueEventListener(new ValueEventListener(){    //Adding listener to a reference making our list dynamic as we inform the adapter
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    s.dislikes= dataSnapshot.getValue(Integer.class);
                    customAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            /**
             * Adding listener to rating reference making our list dynamic as we inform the adapter
             */
            curr_rating.addValueEventListener(new ValueEventListener(){  //Adding listener to a reference making our list dynamic as we inform the adapter
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

        /**
         * Setting up toolbar
         */

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Hostel Mess");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        toolbar.setBackgroundColor(getResources().getColor(R.color.common_google_signin_btn_text_light));

        /**
         * initializing customAdapter
         */
        customAdapter=new CustomAdapter(l,After_login.this);

        /**
         * initializing and filling it objects in listview.
         */
        ListView listView = (ListView) findViewById(R.id.hostel_display);
        listView.setAdapter(customAdapter);

        /**
         * Setting onClickListner to listview elements.
         * Here starting HostelActivity.
         */
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


    /**
     * Sorting list l and informing customAdapter.
     * @param order to sort based on different fields (like rating,like,....)
     */
    public static void sortlist(int order){
        Collections.sort(l,new Sorter(order));
        customAdapter.notifyDataSetChanged();
    }

    /**
     * Sub-class to sort list l based on likes or rating or hostel_no or dislikes.
     * This class sets the comparator and based on this comparator list is sorted.
     */
    static class Sorter implements Comparator<objects> {
        /**
         * to sort based on different fields (like rating,like,....)
         */
        int order=1;

        /**
         * Constructor which initializes order
         * @param order
         */
        Sorter(int order){
            this.order=order;
        }

        /**
         * Setting the comparator.
         * @param t1 object1
         * @param t2 object2
         * @return -1 or 0 or 1 based on t1 and t2.
         */
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

    /**
     * Creating the menubar.
     * Setting functionality to searchView to SearchResult activity by passing the searchword using Intent.putExtra().
     * @param menu gets the menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_sort,menu);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView( menu.findItem(R.id.search_bar));
        searchView.setSubmitButtonEnabled(true);
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
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


}
