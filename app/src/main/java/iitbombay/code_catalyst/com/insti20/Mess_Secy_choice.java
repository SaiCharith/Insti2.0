package iitbombay.code_catalyst.com.insti20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * It is the activity which lets the mess secy to choose whether he wants to go to the original app or wants to update the menu
 * This is an activity which is displayed only for messecys
 *
 * @author Code-Catalyst
 *
 */
public class Mess_Secy_choice extends AppCompatActivity {
    /**
     * Overriding the functionality of onBackPressed.
     * This function is executed when user presses back button.
     * Here Activity is either finished(if user is mess-secy) or completly closed using homeIntenet.
     */
    @Override
    public void onBackPressed() {

        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);   //leads to closing(minimizing) the app.
    }
    /**
     * create two objects of type button and adds a required listener that intents to the the corresponding activity
     * Here the buttons original_app and update_menu are given the functionalities to redirect to either After_login or Update_menu respectievly
     * In update_menu button hostel_no is passed to the Update_menu activity using Intent.putExtra(..,..);
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess__secy_choice);
        /**
         * Button for secy to go to update menu interface
         */
        Button update_menu= (Button) findViewById(R.id.updatemenu);
        /**
         * Button for secy to go to Original app i.e. {@link After_login}
         */
        Button original_app=(Button) findViewById(R.id.originalapp);
        original_app.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bundle bundle=getIntent().getExtras();  //when mess-secy clicks "Original App" button
                int hostel_no=bundle.getInt("hostel_no");
                Intent intent=new Intent(Mess_Secy_choice.this,After_login.class);
                intent.putExtra("hostel_no",hostel_no);
                startActivity(intent); //passing hostel number and starting Activity After_login.
            }
        });
        update_menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {       //when mess-secy clicks update_menu button
                Bundle bundle=getIntent().getExtras();
                int hostel_no=bundle.getInt("hostel_no");    //getting hostel_no from previous activity
                Intent intent = new Intent(Mess_Secy_choice.this,Update_menu.class);
                intent.putExtra("hostel_no",hostel_no);
                startActivity(intent);               //initializing Update_menu activity by passing hostel_no.
            }
        });


    }


}
