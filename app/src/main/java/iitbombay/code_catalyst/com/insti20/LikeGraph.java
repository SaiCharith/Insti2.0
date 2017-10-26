package iitbombay.code_catalyst.com.insti20;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

import iitbombay.code_catalyst.com.insti20.R;

/**
 * Created by atharvn on 26/10/17.
 */

/**
 *  This is 1st Fragment of the Tabbed-activity. This fragment displays the graph with each hostel on X-axis
 *  representend by hostel number and thier corresponding likes on Y-axis, so that we can easily analyze.
 *  We have used GraphView API for creating graphs. Number of likes for each hostel is accessed in our
 *  firebase database and each point thus formed, is appended in DataPoint series which is plotted using GraphView
 */

public class LikeGraph extends Fragment {

    /**
     * This method is called when the this Fragment gets created.
     * Get users likes for every hostel and represent them in a graph.
     * @param inflater to fill the fragment with tab4menu.xml
     * @param container to get the view
     * @param savedInstanceState not useful
     * @return rootView
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.likegraph, container, false);

        final LineGraphSeries<DataPoint> series;

            DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://code-catalyst-asc.firebaseio.com/Mess_Repo");

            final GraphView graph = (GraphView)rootView.findViewById(R.id.likeGraph);
            series = new LineGraphSeries<DataPoint>();
            ref.addListenerForSingleValueEvent(new ValueEventListener(){
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final Integer[] y = {0};

                    for(int i = 1 ; i<17; i++) {
                        final ArrayList<Integer> likes = new ArrayList<Integer>();

                        String h_name;
                        if (i < 10) h_name = "Hostel0" + i;//handling single digit
                        else h_name = "Hostel" + i;
                        y[0] = dataSnapshot.child(h_name).child("Likes").getValue(Integer.class);
                        series.appendData(new DataPoint(i,y[0]),true,16);
                        if (i == 16) {
                            graph.addSeries(series);
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            graph.getViewport().setMinX(0);
            graph.getViewport().setMaxX(17);
            graph.getViewport().setMinY(0);
            graph.getViewport().setMaxY(10);
            graph.getViewport().setYAxisBoundsManual(true);
            graph.getViewport().setXAxisBoundsManual(true);
        return rootView;
    }
}
