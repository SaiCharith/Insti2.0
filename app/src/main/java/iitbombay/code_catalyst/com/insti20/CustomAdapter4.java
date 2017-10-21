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
 * Created by charith on 16/10/17.
 */

public class CustomAdapter4 extends BaseAdapter implements Filterable {

    private Activity c;
    private ArrayList<searchobjects> searchlst;
    private CustomFilter filter;
    private ArrayList<searchobjects> filterList;


    CustomAdapter4(Activity ctx, ArrayList<searchobjects> l){
        c=ctx;
        searchlst=l;
        filterList=l;
    }

    @Override
    public int getCount() {
        return searchlst.size();
    }

    @Override
    public Object getItem(int i) {
        return searchlst.get(i);
    }

    @Override
    public long getItemId(int i) {
        return searchlst.indexOf(getItem(i));
    }

    private class viewHolder{

        TextView hostel_name;
        TextView result;
        TextView day;
        TextView day_part;

        viewHolder(View v){
            hostel_name=v.findViewById(R.id.hostel);
            result=v.findViewById(R.id.result);
            day=v.findViewById(R.id.day);
            day_part=v.findViewById(R.id.day_part);
        }
    }

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

    @Override
    public Filter getFilter() {
        if(filter==null){
            filter=new CustomFilter();
        }
        return filter;
    }
    class CustomFilter extends Filter{

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

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            searchlst= (ArrayList<searchobjects>) filterResults.values;
            notifyDataSetChanged();

        }
    }

}
