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

/**
 * Created by charith on 1/10/17.
 */

public class Tab4menu extends Fragment {

    int hostel_no;
    String[] days={"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday",
    };

    public void instantiate(int i){
        hostel_no=i;
         //getting hostel_no from the activity.
    }


    //  private  static ArrayList<String> aList;
    //  private static ArrayAdapter<String> adapter;
    CustomAdapter adapter = new CustomAdapter();

    public void Temp(int i){
        hostel_no=i;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab4menu, container, false);

        ListView listview= rootView.findViewById(R.id.listview);
        //aList=new ArrayList<>(Arrays.asList(days));
        //adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_expandable_list_item_1,days);
        listview.setAdapter(adapter);

        //container.findViewById(R.id.listview);

//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long L) {
////                Intent intent = new Intent(getActivity(),display.class);
////                intent.putExtra("index",2); //passing index to the upcoming activity.
////                startActivity(intent);
//               // Toast.makeText(getActivity(),"message",Toast.LENGTH_SHORT).show();
//            }
//        });

        Button b = rootView.findViewById(R.id.day_part);




        return rootView;
    }


    class CustomAdapter extends BaseAdapter {
        LayoutInflater inflater=null;
        Context c=null;
        @Override
        public int getCount() {
            return 7;
        }



        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {


            view=getActivity().getLayoutInflater().inflate(R.layout.each_day,null);
            ImageView imageview;

            Button b = view.findViewById(R.id.day_part);
            b.setText(days[i]);

            b.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    String s=days[i];
                   // Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(),DisplayMenu.class);
                    intent.putExtra("index",hostel_no); //passing hostel no to the upcoming activity.
                    intent.putExtra("Day",days[i]); //passing day to the upcoming activity.
                    startActivity(intent);
                }
            });

            return view;
        }
    }

}
