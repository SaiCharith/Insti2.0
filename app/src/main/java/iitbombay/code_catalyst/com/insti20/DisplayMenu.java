package iitbombay.code_catalyst.com.insti20;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static java.sql.Types.NULL;

/**
 * @author Charith
 *
 *
 */
public class DisplayMenu extends AppCompatActivity {
    /*!
     * \brief stores days of week as a list.List is used as it can be passed as an arugument to adapters.
     */
    private static ArrayList<String> l=new ArrayList<String>();

    /*!
     * \brief CustomAdapter to convert the arraylist l to listview.
     *
     */
    private static CustomAdapter2 customAdapter;

    /*!
     * \brief Method impleneted after this activity is called
     * \param Bindle
     * \return void
     * \brief Layout used to show this activity is activity_display_menu.xml.
     * This function basically calls the CustomAdapter2 and inflates the ListView 'day_menu_list' with with the menu of hostel_no and of pirticular day.
     * hostel_no and Day come from previous activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_menu);

        int hostel_no;
        String day;

        Bundle bundle=getIntent().getExtras();
        hostel_no=bundle.getInt("index");
        day = bundle.getString("Day");
        TextView b= (TextView) findViewById(R.id.Day);
        b.setText(day+"'s Menu");
        Toast.makeText(this,day,Toast.LENGTH_SHORT).show();


        String h_name;

        if(hostel_no<10) h_name="Hostel0"+hostel_no;
        else h_name="Hostel"+hostel_no;

        customAdapter=new CustomAdapter2(DisplayMenu.this,h_name,day);
        ListView listView = (ListView) findViewById(R.id.day_menu_list);
        listView.setAdapter(customAdapter);

    }


}
