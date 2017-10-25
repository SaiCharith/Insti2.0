package iitbombay.code_catalyst.com.insti20;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;


public class Analysis extends AppCompatActivity {


    LineGraphSeries<DataPoint> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analysis);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://code-catalyst-asc.firebaseio.com/Mess_Repo");
        final GraphView g1=(GraphView) findViewById(R.id.graphdislikes);
        //final GraphView graphd = (GraphView)findViewById(R.id.graphdislikes);
        final GraphView graphr = (GraphView)findViewById(R.id.graphrating);
        final GraphView graph = (GraphView)findViewById(R.id.graphlike);
        series = new LineGraphSeries<DataPoint>();
            ref.addListenerForSingleValueEvent(new ValueEventListener(){
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final Integer[] y = {0};
                    final Integer[] k = {0};
                    final Float[] z = {0f};

                    for(int i = 1 ; i<17; i++) {
                        final ArrayList<Integer> likes = new ArrayList<Integer>();
                        final ArrayList<Integer> dislikes = new ArrayList<Integer>();
                        final ArrayList<Float> rating = new ArrayList<Float>();


                        String h_name;
                        if (i < 10) h_name = "Hostel0" + i;//handling single digit
                        else h_name = "Hostel" + i;

                        y[0] = dataSnapshot.child(h_name).child("Likes").getValue(Integer.class);
                        //likes.add(y[0]);
                        k[0] = dataSnapshot.child(h_name).child("Dislikes").getValue(Integer.class);
                        //dislikes.add(k);
                        //z[0] = dataSnapshot.child(h_name).child("Rating").child("Current_Rating").getValue(Float.class);
                        //rating.add(z);
//                        series.appendData(new DataPoint(i, y[0]), true, 16);
                        series.appendData(new DataPoint(i,k[0]),true,16);
                       //seriesdislike.appendData(new DataPoint(i, k[0]), true, 16);
                        //seriesrating.appendData(new DataPoint(i, z[0]), true, 16);
                        Log.d("tag" + i, String.valueOf(new DataPoint(i, k[0])));
                        if (i == 16) {
                         //  graph.addSeries(series);
                            g1.addSeries(series);
                         //  graphd.addSeries(seriesdislike);
                            //graphr.addSeries(seriesrating);
                        }
                    }
                    DataPoint[] x=new DataPoint[]{};
                    series.resetData(x);
                    for(int i = 1 ; i<17; i++) {
                        final ArrayList<Integer> likes = new ArrayList<Integer>();
                        final ArrayList<Integer> dislikes = new ArrayList<Integer>();
                        final ArrayList<Float> rating = new ArrayList<Float>();


                        String h_name;
                        if (i < 10) h_name = "Hostel0" + i;//handling single digit
                        else h_name = "Hostel" + i;

                        y[0] = dataSnapshot.child(h_name).child("Likes").getValue(Integer.class);
                        //likes.add(y[0]);
                        k[0] = dataSnapshot.child(h_name).child("Dislikes").getValue(Integer.class);
                        //dislikes.add(k);
                        //z[0] = dataSnapshot.child(h_name).child("Rating").child("Current_Rating").getValue(Float.class);
                        //rating.add(z);
//                        series.appendData(new DataPoint(i, y[0]), true, 16);
                        series.appendData(new DataPoint(i,y[0]),true,16);
                        //seriesdislike.appendData(new DataPoint(i, k[0]), true, 16);
                        //seriesrating.appendData(new DataPoint(i, z[0]), true, 16);
                        Log.d("tag" + i, String.valueOf(new DataPoint(i, y[0])));
                        if (i == 16) {
                            //  graph.addSeries(series);
                            graph.addSeries(series);
                            //  graphd.addSeries(seriesdislike);
                            //graphr.addSeries(seriesrating);
                        }
                    }
                    series.resetData(x);
                    for(int i = 1 ; i<17; i++) {
                        final ArrayList<Integer> likes = new ArrayList<Integer>();
                        final ArrayList<Integer> dislikes = new ArrayList<Integer>();
                        final ArrayList<Float> rating = new ArrayList<Float>();


                        String h_name;
                        if (i < 10) h_name = "Hostel0" + i;//handling single digit
                        else h_name = "Hostel" + i;

                        y[0] = dataSnapshot.child(h_name).child("Likes").getValue(Integer.class);
                        //likes.add(y[0]);
                        k[0] = dataSnapshot.child(h_name).child("Dislikes").getValue(Integer.class);
                        //dislikes.add(k);
                        z[0] = dataSnapshot.child(h_name).child("Rating").child("Current_Rating").getValue(Float.class);
                        //rating.add(z);
//                        series.appendData(new DataPoint(i, y[0]), true, 16);
                        series.appendData(new DataPoint(i,z[0]),true,16);
                        //seriesdislike.appendData(new DataPoint(i, k[0]), true, 16);
                        //seriesrating.appendData(new DataPoint(i, z[0]), true, 16);
                        Log.d("tag" + i, String.valueOf(new DataPoint(i, z[0])));
                        if (i == 16) {
                            //  graph.addSeries(series);
                            graphr.addSeries(series);
                            //  graphd.addSeries(seriesdislike);
                            //graphr.addSeries(seriesrating);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

//        for(int i=1;i<17;i++) {
////            Toast.makeText(this,likes.get(i),Toast.LENGTH_SHORT).show();
//             series.appendData(new 11DataPoint(i, likes.get(i)), true, 16);
//        }

        //plot(likes);
       // graph.addSeries(series);

        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(17);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(10);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);
        g1.getViewport().setMinX(0);
        g1.getViewport().setMaxX(17);
        g1.getViewport().setMinY(0);
        g1.getViewport().setMaxY(10);

        g1.getViewport().setYAxisBoundsManual(true);
        g1.getViewport().setXAxisBoundsManual(true);
        graphr.getViewport().setMinX(0);
        graphr.getViewport().setMaxX(17);
        graphr.getViewport().setMinY(0);
        graphr.getViewport().setMaxY(10);

        graphr.getViewport().setYAxisBoundsManual(true);
        graphr.getViewport().setXAxisBoundsManual(true);


    }
}




