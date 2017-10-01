package iitbombay.code_catalyst.com.insti20;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class DisplayMenu extends AppCompatActivity {
    private int hostel_no;
    private String day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_menu);

        Bundle bundle=getIntent().getExtras();
        hostel_no=bundle.getInt("index");
        day = bundle.getString("Day");

        Toast.makeText(this,day+hostel_no,Toast.LENGTH_SHORT).show();

    }


}
