package iitbombay.code_catalyst.com.insti20;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class DislikeGraph extends Fragment {

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
                       final Integer[] k = {0};
                for(int i = 1 ; i<17; i++) {
                    final ArrayList<Integer> likes = new ArrayList<Integer>();

                    String h_name;
                    if (i < 10) h_name = "Hostel0" + i;//handling single digit
                    else h_name = "Hostel" + i;
                      k[0] = dataSnapshot.child(h_name).child("Dislikes").getValue(Integer.class);
                    series.appendData(new DataPoint(i,k[0]),true,16);
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
