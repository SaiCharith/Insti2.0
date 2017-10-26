package iitbombay.code_catalyst.com.insti20;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

/**
 *  Customadapter4 converts ArrayList<searchobjects> to a listview with fields as in search_result_template.xml
 * @author Code-Catalyst.
 */

public class CustomAdapter4 extends BaseAdapter implements Filterable {

    /**
     *
     * Stores the id of current Activity/Context
     */
    private Activity c;
    /**
     * List to be displayed in the listview
     */
    private ArrayList<searchobjects> searchlst;
    /**
     * used to filter the searchlst
     */
    private CustomFilter filter;

    public ArrayList<searchobjects> getSearchlst() {
        return searchlst;
    }

    /**
     * used as temporary holder for filtered list.
     */
    private ArrayList<searchobjects> filterList;

    /**
     *
     * @param ctx to update context
     * @param l to get the list of searchebles.
     */
    CustomAdapter4(Activity ctx, ArrayList<searchobjects> l){
        c=ctx;
        searchlst=l;
        filterList=l;
    }

    /**
     * to get the number of elements to be displayed on the listview.
     * @return (int) size of listview
     */

    @Override
    public int getCount() {
        return searchlst.size();
    }

    /**
     * to get the object at ith position.
     * @param i item at ith position
     * @return (Object) object at ith position of searchlst.
     */

    @Override
    public Object getItem(int i) {
        return searchlst.get(i);
    }

    /**
     * to get the id of object at ith position.
     * @param i itemid at ith position
     * @return (Object) objectid at ith position of searchlst.
     */

    @Override
    public long getItemId(int i) {
        return searchlst.indexOf(getItem(i));
    }

    /**
     * Sub-class of Custum-Adapter4.
     * Used to hold the view of each instance of listview.
     */
    private class viewHolder{
        /**
         * To hold the hostel name.
         */
        TextView hostel_name;
        /**
         * To hold result(menu item)
         */
        TextView result;
        /**
         * to hold the day.
         */
        TextView day;
        /**
         * to hold the part of the day.
         */
        TextView day_part;

        /**
         * Constructor which initializes variables of viewHolder.
         * @param v current view
         */

        viewHolder(View v){
            hostel_name=v.findViewById(R.id.hostel);
            result=v.findViewById(R.id.result);
            day=v.findViewById(R.id.day);
            day_part=v.findViewById(R.id.day_part);
        }
    }

    /**
     * Most important method in to display the listview.
     * Optimizing the number of inflation calls using setTag and gettag methods.
     * @param i index
     * @param view veiw of list corresponding to index i
     * @param parent not very important here
     * @return view
     */
    @Override
    public View getView(int i, View view, ViewGroup parent) {

        viewHolder holder;


        if(view==null){
            LayoutInflater inflater = c.getLayoutInflater();
            view = inflater.inflate(R.layout.search_result_template,null);
            holder=new viewHolder(view);
            view.setTag(holder);

        }
        else{
            holder= (viewHolder) view.getTag();
        }

        holder.hostel_name.setText(searchlst.get(i).getHostel_name());
        holder.result.setText(searchlst.get(i).getItem());
        holder.day_part.setText(searchlst.get(i).getDay_part());
        holder.day.setText(searchlst.get(i).getDay());

        return view;
    }

    /**
     * method instantiates CustomFilter.
     * @return filter
     */
    @Override
    public Filter getFilter() {
        if(filter==null){
            filter=new CustomFilter();
        }
        return filter;
    }

    /**
     * Sub-Class of CustomAdapter4.
     * Main functionality is to filter the listview based on constraint.
     */
    class CustomFilter extends Filter{

        /**
         *
         * @param constraint string which is to be searched.
         * @return FilterResults
         *
         * Filters searchlst based presence of constraint in instance of searchlst.
         */
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results= new FilterResults();

            if(constraint!=null && constraint.length()>0){
                constraint=constraint.toString().toUpperCase();
                ArrayList<searchobjects> filters=new ArrayList<>();

                for(int i=0;i<filterList.size();i++){
                    if(filterList.get(i).getItem().toUpperCase().contains(constraint)){
                        searchobjects p = new searchobjects(filterList.get(i).getH_no(),
                                                            filterList.get(i).getItem(),
                                                            filterList.get(i).getHostel_name(),
                                                            filterList.get(i).getDay(),
                                                            filterList.get(i).getDay_part());
                        filters.add(p);
                    }
                }
                results.count=filters.size();
                results.values=filters;
            }
            else{
                results.count=filterList.size();
                results.values=filterList;
            }

            return results;
        }

        /**
         * Updates searchlst
         * @param charSequence element to be searched for
         * @param filterResults to update searchlst
         */
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            searchlst= (ArrayList<searchobjects>) filterResults.values;
            notifyDataSetChanged();

        }
    }

}
