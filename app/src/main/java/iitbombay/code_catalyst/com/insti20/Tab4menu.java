package iitbombay.code_catalyst.com.insti20;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 *  This is 4th Fragment of the Hostel-activity. This fragment displays days of the week as buttons in a listview.The buttons when clicked will redirect to display_menu activity.
 *  In the parent class while initiating this Fragment the function called instantiate must be called, which instantiates the hostel_no which is required for the Display_menu activity.
 *  hostel_no and day are passed to Display_menu activity and Display_Menu activity is called by clicking on any day's button. This function is set-up holder.day.setOnclickListner.
 *  sub-class CustomAdapter fills the listview with days buttons.
 *  sub-class viewHolder used for optimization.This avoids re-initialization of buttons of viewHolder using view.getTag() and view.setTag() methods.
 *
 */

public class Tab4menu extends Fragment {

    /**
     * to store hostel-no.
     */
    int hostel_no;
    /**
     * days of the week stored as String-array.
     */
    String[] days={"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

    /**
     * CustomAdapter to convert String of days to listview of buttons.
     */

    CustomAdapter adapter = new CustomAdapter();


    /**
     * This method is called when the this Fragment gets created.
     * All the dynamic aspects to be implemnted here.
     * Listview is initialized and is filled by the adapter.
     * @param inflater to fill the fragment with tab4menu.xml
     * @param container to get the view
     * @param savedInstanceState not useful
     * @return rootView
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tab4menu, container, false);


        Bundle bundle=getActivity().getIntent().getExtras();
        hostel_no=bundle.getInt("index");

        ListView listview= rootView.findViewById(R.id.listview);
        listview.setAdapter(adapter);

        return rootView;
    }

    /**
     * viewHolder class is used to hold the view at each instance of the listview.
     */
    private static class viewHolder{
        /**
         * to hold each day as a button.
         */
        Button day;

        /**
         * constructor which initializes the button
         * @param v gets the view
         */
        viewHolder(View v){
            day=v.findViewById(R.id.day_part);
        }
    }


    /**
     * This class fills the listview with days buttons.
     * Each button is added in each call to the getView method with different i values ranging from 0-7.
     *
     */
    private class CustomAdapter extends BaseAdapter {
        /**
         *
         * @return size of listview
         */
        @Override
        public int getCount() {
            return 7;
        }

        /**
         *
         * @param i current position
         * @return null
         */
        @Override
        public Object getItem(int i) {
            return null;
        }

        /**
         * Not useful here.
         * @param i current position
         * @return ItemId
         */
        @Override
        public long getItemId(int i) {
            return 0;
        }

        /**
         * Most important function of this class which actually populates the listview.
         * Initializing viewHolder only once using getTag and setTag methods.
         * Updating viewholder by setting text and onClickListner to holder.day
         * When the button is clicked the view is redirected to Display_Menu with the relevent day and hostel using Intent.putExtra(..,..).
         *
         * @param i current position
         * @param view current view
         * @param viewGroup not required
         * @return view
         */

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {

            final viewHolder holder;


            if(view==null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                view = inflater.inflate(R.layout.each_day,null);
                holder=new viewHolder(view);
                view.setTag(holder);

            }

            else{
                holder= (viewHolder) view.getTag();
            }

            holder.day.setText(days[i]);

            holder.day.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(),DisplayMenu.class);
                    intent.putExtra("index",hostel_no);
                    intent.putExtra("Day",days[i]);
                    startActivity(intent);
                }
            });

            return view;
        }
    }

}
