package iitbombay.code_catalyst.com.insti20;

/**
 * Created by charith on 8/10/17.
 */


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * Customadapter2 generates a listview with fields as in fill_day_menu.xml
 * In this class mess-menu is obtained from firebase-database and is populated onto the listview.
 * @author Code-Catalyst.
 */
class CustomAdapter2 extends ArrayAdapter<String> {

    /**
     * String-array of day_parts to store 4 parts of day.
     */
    private String[] day_parts={"Breakfast","lunch","tiffin","dinner"};
    /**
     * to have reference of the menu of a day
     */
    DatabaseReference Day_menu;

    /**
     * Custom constructor to initialize Day_menu and to set the context
     * As day_menu->(child of) Current -> Mess-Menu -> Hostel_Name -> Mess_Repo
     * @param context to set context
     * @param h_name to have database referece of that hostel
     * @param day to have database referece of that day
     */
    public CustomAdapter2(Context context,String h_name,String day) {
        super(context, R.layout.activity_display_menu);


        DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://code-catalyst-asc.firebaseio.com/Mess_Repo");
        DatabaseReference hostel_ref=ref.child(h_name);
        DatabaseReference Mess_menu_ref=hostel_ref.child("Mess_Menu");
        DatabaseReference Current_menu=Mess_menu_ref.child("Current");
        Day_menu=Current_menu.child(day);

    }

    /**
     * viewHolder class is used to hold the view at each instance of the listview.
     * Used for optimization and smoothscrolling.
     */
    static class viewHolder{

        /**
         * to display menu
         */
        TextView dish;
        /**
         * to display paet of the day
         * this is a non-clickable button
         */
        Button day_part;

        /**
         * Initializing viewHolder by objects in fill_day_menu.xml
         * @param v
         */
        viewHolder(View v){
            dish=v.findViewById(R.id.menu);
            day_part=v.findViewById(R.id.day_part);
        }
    }

    /**
     * gets the size of the listview
     * @return (int) 4
     */
    @Override
    public int getCount() {
        return 4;
    }

    /**
     * Method not used here
     * @param i current position
     * @return null
     */
    @Override
    public String getItem(int i) {
        return null;
    }

    /**
     * Method not used here
     * @param i current position
     * @return 0
     */
    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * Most important function of this class which actually populates the listview.
     * Initializing viewHolder only once using getTag and setTag methods.
     * Updating viewholder by setting the fields appropriately using addValueEventListner.
     * this addValueEventListner method will be called once and whenever data in the firebase-database is changed.
     *
     *
     * @param i current position
     * @param view current view
     * @param viewGroup not required
     * @return view
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        final viewHolder holder;


        if(view==null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.fill_day_menu,null);
            holder=new viewHolder(view);
            view.setTag(holder);

        }

        else{
            holder= (viewHolder) view.getTag();
        }



        DatabaseReference Days_menu_each_part=Day_menu.child(day_parts[i]);
        Days_menu_each_part.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s =  dataSnapshot.getValue(String.class);
                holder.dish.setText(s);
                notifyDataSetChanged();

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        holder.day_part.setText(day_parts[i]);


        return view;
    }
}
